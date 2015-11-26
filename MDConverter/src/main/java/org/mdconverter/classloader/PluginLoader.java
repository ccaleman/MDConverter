package org.mdconverter.classloader;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.inject.Injector;
import org.mdconverter.Application;
import org.mdconverter.api.consolewriter.ConsoleWriter;
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

    private final ConsoleWriter consoleWriter;

    /**
     * Impl because of method {@link JythonObjectFactoryImpl#addPluginToInterpreter(String)} which only should be accessed internal and not by plugins
     */
    private final JythonObjectFactoryImpl jof;
    private final Path structureReaderPath;
    private final Path structureWriterPath;
    private final Path topReaderPath;
    private final Path topWriterPath;
    private PluginType actual;
    private List<PluginManifest> pluginManifests = Lists.newArrayList();

    private AbstractReader reader;
    private AbstractWriter writer;
    private List<InputError> inputErrors = Lists.newArrayList();

    @Inject
    private Injector parentInjector;
    private PluginManifest pluginManifest;
    private PluginManifest readerPluginManifest;
    private PluginManifest writerPluginManifest;

    @Inject
    public PluginLoader(ConsoleWriter consoleWriter, JythonObjectFactoryImpl jof,
                        @Named("location.reader.structure") String structureReaderPath,
                        @Named("location.writer.structure") String structureWriterPath,
                        @Named("location.reader.topology") String topReaderPath,
                        @Named("location.writer.topology") String topWriterPath) {
        this.consoleWriter = consoleWriter;
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
        consoleWriter.printInfoln(this.structureReaderPath.toAbsolutePath().toString());
        consoleWriter.printInfoln(this.structureWriterPath.toAbsolutePath().toString());
        consoleWriter.printInfoln(this.topReaderPath.toAbsolutePath().toString());
        consoleWriter.printInfoln(this.topWriterPath.toAbsolutePath().toString());
    }

    public List<PluginManifest> getPluginManifestsByLoaderInput(LoaderInput input) throws IOException, PluginMisconfigurationException, URISyntaxException {
        pluginManifests.clear();
        if (input.getPluginType().equals(PluginType.READER))
            searchForPlugin(input, getJarFilePaths(input), AbstractReader.class);
        else
            searchForPlugin(input, getJarFilePaths(input), AbstractWriter.class);
        return pluginManifests;
    }

    public void loadPlugin(LoaderInput loaderInput) throws IOException, PluginMisconfigurationException, URISyntaxException {
        List<URI> jarFiles = getJarFilePaths(loaderInput);
        if (actual.equals(PluginType.READER)) {
            AbstractReader abstractReader = searchForPlugin(loaderInput, jarFiles, AbstractReader.class);
            if (abstractReader != null) {
                abstractReader.setPluginManifest(readerPluginManifest);
                reader = abstractReader;
                consoleWriter.printInfoln("Found reader plugin");
            } else {
                consoleWriter.printErrorln("Didn't found reader plugin");
                inputErrors.add(InputError.NO_READER);
            }
        } else if (actual.equals(PluginType.WRITER)) {
            AbstractWriter abstractWriter = searchForPlugin(loaderInput, jarFiles, AbstractWriter.class);
            if (abstractWriter != null) {
                abstractWriter.setPluginManifest(writerPluginManifest);
                writer = abstractWriter;
                consoleWriter.printInfoln("Found writer plugin");
            } else {
                consoleWriter.printErrorln("Didn't found writer plugin");
                inputErrors.add(InputError.NO_WRITER);
            }
        }

        /*else if (script) {
            consoleWriter.printErrorln("Script aren't supported yet!");
            //TODO: implement script loader (differ between script languages?)
        }*/
    }

    public AbstractReader getReader() {
        return reader;
    }

    public AbstractWriter getWriter() {
        return writer;
    }

    public List<InputError> getInputErrors() {
        return inputErrors;
    }

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

    private PluginManifest scanJarFile(URI uriToJarFile) throws IOException {
        JarFile jarFile = new JarFile(new File(uriToJarFile));
        loadClassLoader(uriToJarFile.toURL());
        return scanContent(jarFile);
    }

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

    private <T> Class<T> extractPluginClass(PluginManifest pluginManifest) throws PluginMisconfigurationException {
        return (Class<T>) extractClass(pluginManifest);
    }

    private Class<?> extractClass(PluginManifest pluginManifest) throws PluginMisconfigurationException {
        try {
            return ClassLoader.getSystemClassLoader().loadClass(pluginManifest.getClassName());
        } catch (ClassNotFoundException e) {
            throw new PluginMisconfigurationException(pluginManifest, e);
        }
    }
}
