package org.mdconverter.pluginloader;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.inject.Injector;
import org.mdconverter.Application;
import org.mdconverter.api.consolehandler.ConsoleHandler;
import org.mdconverter.api.plugin.AbstractPlugin;
import org.mdconverter.api.plugin.PluginManifest;
import org.mdconverter.api.plugin.reader.AbstractReader;
import org.mdconverter.api.plugin.type.FileType;
import org.mdconverter.api.plugin.type.PluginType;
import org.mdconverter.api.plugin.type.ScriptType;
import org.mdconverter.api.plugin.writer.AbstractWriter;
import org.mdconverter.argumentparser.InputError;
import org.mdconverter.inject.PluginModule;
import org.mdconverter.jythonsupport.JythonObjectFactoryImpl;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by miso on 28.10.2015.
 */
@Singleton
public class PluginLoader {

    @Inject
    private Injector parentInjector;

    //Injects
    /**
     * Impl because of method {@link JythonObjectFactoryImpl#addPluginToInterpreter(String)} which only should be accessed internal and not by plugins
     */
    private final JythonObjectFactoryImpl jof;
    private final ConsoleHandler consoleHandler;

    //Fields
    private final Path structureReaderPath;
    private final Path structureWriterPath;
    private final Path topReaderPath;
    private final Path topWriterPath;
    private PluginType actual;
    private AbstractReader reader;
    private AbstractWriter writer;
    private List<PluginManifest> pluginManifests = Lists.newArrayList();
    private List<InputError> inputErrors = Lists.newArrayList();
    private PluginManifest pluginManifest;
    private PluginManifest readerPluginManifest;
    private PluginManifest writerPluginManifest;

    @Inject
    /**
     * ensure the right paths are set for the plugin folders
     */
    public PluginLoader(ConsoleHandler consoleHandler, JythonObjectFactoryImpl jof,
                        @Named("location.reader.structure") String structureReaderPath,
                        @Named("location.writer.structure") String structureWriterPath,
                        @Named("location.reader.topology") String topReaderPath,
                        @Named("location.writer.topology") String topWriterPath) {
        this.consoleHandler = consoleHandler;
        this.jof = jof;
        URL url = Application.class.getProtectionDomain().getCodeSource().getLocation();
        String s = "";
        try {
            s = Paths.get(url.toURI()).toString();
            s = s.substring(0, s.lastIndexOf(File.separator));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        this.structureReaderPath = Paths.get(s + structureReaderPath);
        this.structureWriterPath = Paths.get(s + structureWriterPath);
        this.topReaderPath = Paths.get(s + topReaderPath);
        this.topWriterPath = Paths.get(s + topWriterPath);
        if (ensureDirectories()) {
            throw new RuntimeException("Could not create plugin folders.");
        }
        consoleHandler.printInfoln(this.structureReaderPath.toAbsolutePath().toString());
        consoleHandler.printInfoln(this.structureWriterPath.toAbsolutePath().toString());
        consoleHandler.printInfoln(this.topReaderPath.toAbsolutePath().toString());
        consoleHandler.printInfoln(this.topWriterPath.toAbsolutePath().toString());
    }

    /**
     * searches for the requested {@link PluginType} and {@link FileType} and returns a List of matching {@link PluginManifest}
     *
     * @param input defines the requested {@link PluginType} and {@link FileType}
     * @return List of {@link PluginManifest}
     * @throws IOException |PluginMisconfigurationException | URISyntaxException
     */
    public List<PluginManifest> getPluginManifestsByLoaderInput(LoaderInput input) throws IOException, PluginMisconfigurationException, URISyntaxException {
        pluginManifests.clear();
        if (input.getPluginType().equals(PluginType.READER))
            searchForPlugin(input, getJarFilePaths(input), AbstractReader.class);
        else
            searchForPlugin(input, getJarFilePaths(input), AbstractWriter.class);
        return pluginManifests;
    }

    /**
     * Tries to load the requested plugin, if not found a {@link InputError} will be added
     * @param loaderInput defines which plugin should be loaded
     * @throws IOException | PluginMisconfigurationException | URISyntaxException
     */
    public void loadPlugin(LoaderInput loaderInput) throws IOException, PluginMisconfigurationException, URISyntaxException {
        List<URI> jarFiles = getJarFilePaths(loaderInput);
        if (actual.equals(PluginType.READER) && reader == null) {
            AbstractReader abstractReader = searchForPlugin(loaderInput, jarFiles, AbstractReader.class);
            if (abstractReader != null) {
                abstractReader.setPluginManifest(readerPluginManifest);
                reader = abstractReader;
                consoleHandler.printInfoln("Found reader plugin");
            } else {
                consoleHandler.printErrorln("Didn't found reader plugin");
                inputErrors.add(InputError.NO_READER);
            }
        } else if (actual.equals(PluginType.WRITER) && writer == null) {
            AbstractWriter abstractWriter = searchForPlugin(loaderInput, jarFiles, AbstractWriter.class);
            if (abstractWriter != null) {
                abstractWriter.setPluginManifest(writerPluginManifest);
                writer = abstractWriter;
                consoleHandler.printInfoln("Found writer plugin");
            } else {
                consoleHandler.printErrorln("Didn't found writer plugin");
                inputErrors.add(InputError.NO_WRITER);
            }
        } else if (writer != null && reader != null && !writer.getPluginManifest().getFileType().equals(reader.getPluginManifest().getFileType())) {
            throw new PluginMisconfigurationException(reader.getPluginManifest(), "Defined plugins are implemented for different meta models.");
        } else {
            throw new PluginMisconfigurationException(actual.equals(PluginType.READER) ? readerPluginManifest : writerPluginManifest, String.format("It is not possible to load two %s at once!", actual));
        }
        /*else if (script) {
            consoleHandler.printErrorln("Script aren't supported yet!");
            //TODO: implement script loader (differ between script languages)
        }*/
    }

    /**
     * @return the requested ReaderPlugin
     */
    public AbstractReader getReader() {
        return reader;
    }

    /**
     * @return the requested WriterPlugin
     */
    public AbstractWriter getWriter() {
        return writer;
    }

    /**
     * @return a List of {@link InputError} occurred during plugin loading
     */
    public List<InputError> getInputErrors() {
        return inputErrors;
    }


    /**
     * checks if all plugin folders exists and creates them if necessary
     * @return false if everything was fine
     */
    private boolean ensureDirectories() {
        boolean error = false;
        if (Files.notExists(this.structureReaderPath)) {
            error = !new File(this.structureReaderPath.toString()).mkdir();
        }
        if (Files.notExists(this.structureWriterPath)) {
            error = !new File(this.structureWriterPath.toString()).mkdir();
        }
        if (Files.notExists(this.topReaderPath)) {
            error = !new File(this.topReaderPath.toString()).mkdir();
        }
        if (Files.notExists(this.topWriterPath)) {
            error = !new File(this.topWriterPath.toString()).mkdir();
        }
        return error;
    }

    /**
     * loads all jar-Files for the defined {@link PluginType} and {@link FileType}
     * @param input defines the {@link PluginType} and {@link FileType}
     * @return a List of jar-Files
     * @throws IOException
     */
    private List<URI> getJarFilePaths(LoaderInput input) throws IOException {
        Path dir;
        dir = getPathFromLoaderInput(input);
        List<URI> jarFiles = Lists.newArrayList();
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(dir)) {
            ds.forEach(path -> {
                if (path.getFileName().toString().endsWith(".jar")) jarFiles.add(path.toUri());
            });
        }
        return jarFiles;
    }

    /**
     * Search for a plugin defined in the {@link LoaderInput}
     * @param loaderInput the LoaderInput for the requested plugin
     * @param jarFiles a List of URIs to plugins
     * @param clazz specific {@link AbstractPlugin} implementation
     * @param <T> the Class which should be returned
     * @return either a {@link AbstractReader} or a {@link AbstractWriter} or null if not found
     * @throws IOException | PluginMisconfigurationException | URISyntaxException
     */
    private <T> T searchForPlugin(LoaderInput loaderInput, List<URI> jarFiles, Class<T> clazz) throws IOException, PluginMisconfigurationException, URISyntaxException {
        Class<T> abstractPlugin = null;
        Injector injector = parentInjector.createChildInjector(new PluginModule());
        for (URI jarFile : jarFiles) {
            pluginManifest =  scanJarFile(jarFile);
            if (pluginManifest != null) {
                pluginManifests.add(pluginManifest);
                if (pluginManifest.getPluginName().equalsIgnoreCase(loaderInput.getPluginName())) {
                    if (pluginManifest.getPluginType().equals(PluginType.READER)) {
                        this.readerPluginManifest = pluginManifest;
                    } else {
                        this.writerPluginManifest = pluginManifest;
                    }
                    abstractPlugin = extractPluginClass(pluginManifest);
                    if (pluginManifest.getScriptType().equals(ScriptType.JYTHON)) {
                        jof.addPluginToInterpreter(jarFile.getPath());
                    }
                } else if (pluginManifest.isScript() && !pluginManifest.getScriptType().equals(ScriptType.JYTHON)) {
                    throw new RuntimeException("Script support isn't implemented yet!!!!");
                }
            }
        }
        if (abstractPlugin != null) {
            return injector.getInstance(abstractPlugin);
        }
        return null;
    }

    /**
     * @param loaderInput a {@link LoaderInput}
     * @return a path to the plugin folder depending on the {@link LoaderInput} configuration
     */
    private Path getPathFromLoaderInput(LoaderInput loaderInput) {
        Path dir;
        if (loaderInput.getFileType().equals(FileType.STRUCTURE) && loaderInput.getPluginType().equals(PluginType.READER)) {
            dir = structureReaderPath.toAbsolutePath();
            actual = PluginType.READER;
        } else if (loaderInput.getFileType().equals(FileType.TOPOLOGY) && loaderInput.getPluginType().equals(PluginType.READER)) {
            dir = topReaderPath.toAbsolutePath();
            actual = PluginType.READER;
        } else if ((loaderInput.getFileType().equals(FileType.STRUCTURE) && loaderInput.getPluginType().equals(PluginType.WRITER))) {
            dir = structureWriterPath.toAbsolutePath();
            actual = PluginType.WRITER;
        } else {
            dir = topWriterPath.toAbsolutePath();
            actual = PluginType.WRITER;
        }
        return dir;
    }

    /**
     * @param uriToJarFile
     * @return a {@link PluginManifest} if found or null
     * @throws IOException
     */
    private PluginManifest scanJarFile(URI uriToJarFile) throws IOException {
        JarFile jarFile = new JarFile(new File(uriToJarFile));
        PluginManifest manifest = scanContent(jarFile);
        if (manifest != null) {
            loadClassLoader(uriToJarFile.toURL());
        }
        return manifest;
    }

    /**
     * loads jarFile into the ClassPath to allow access to the jarFile
     * @param pluginPath path to jarFile
     */
    private void loadClassLoader(URL pluginPath) {
        try {
            final Class[] parameters = new Class[]{URL.class};
            URLClassLoader systemClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
            Method method = URLClassLoader.class.getDeclaredMethod("addURL", parameters);
            method.setAccessible(true);
            method.invoke(systemClassLoader, pluginPath);
        } catch (Throwable t) {
            String message = "Error, could not add URL to system classloader";
            throw new RuntimeException(message);
        }
    }

    /**
     * checks the given jarFile for a file named "manifest.json" to ensure its a MDConverter plugin
     * @param jarFile
     * @return a {@link PluginManifest} if found or null
     */
    private PluginManifest scanContent(JarFile jarFile) {
        Enumeration<JarEntry> entries = jarFile.entries();
        PluginManifest pluginManifest = null;
        while (entries.hasMoreElements()) {
            JarEntry jarEntry = entries.nextElement();
            String name = jarEntry.getName();
            if (name.endsWith("manifest.json")) {
                try {
                    String manifestString = CharStreams.toString(new InputStreamReader(jarFile.getInputStream(jarEntry), Charsets.UTF_8));
                    pluginManifest = new Gson().fromJson(manifestString, PluginManifest.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return pluginManifest;
    }

    /**
     * casts the loaded plugin either to a {@link AbstractReader} or a {@link AbstractWriter}
     * @param pluginManifest
     * @param <T> either a {@link AbstractReader} or a {@link AbstractWriter}
     * @return either a {@link AbstractReader} or a {@link AbstractWriter}
     * @throws PluginMisconfigurationException
     */
    private <T> Class<T> extractPluginClass(PluginManifest pluginManifest) throws PluginMisconfigurationException {
        return (Class<T>) extractClass(pluginManifest);
    }

    /**
     * get the main class of the requested plugin
     * @param pluginManifest
     * @return the main class of the plugin (implements the {@link AbstractPlugin})
     * @throws PluginMisconfigurationException
     */
    private Class<?> extractClass(PluginManifest pluginManifest) throws PluginMisconfigurationException {
        try {
            return ClassLoader.getSystemClassLoader().loadClass(pluginManifest.getClassName());
        } catch (ClassNotFoundException e) {
            throw new PluginMisconfigurationException(pluginManifest, e);
        }
    }

    /**
     * UNUSED
     *
     * @param <T>
     * @return
     * @throws PluginMisconfigurationException
     */
    private <T> T extractPlugin() throws PluginMisconfigurationException {
        try {
            String className = pluginManifest.getClassName();
            Class<?> aClass = ClassLoader.getSystemClassLoader().loadClass(className);
            Constructor<?> constructor = aClass.getConstructor();
            return (T) constructor.newInstance(pluginManifest);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new PluginMisconfigurationException(pluginManifest, e);
        }
    }

}
