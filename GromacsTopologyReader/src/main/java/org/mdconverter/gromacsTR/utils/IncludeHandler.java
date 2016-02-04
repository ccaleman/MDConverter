package org.mdconverter.gromacstr.utils;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by miso on 07.12.2015.
 */
public class IncludeHandler {

    public static byte[] getFileByName(Class clazz, String name) throws URISyntaxException, IOException {
        //name = name.replace("\\", File.separator);
        String path = "top/" + name;
        Path filePath = Paths.get(clazz.getProtectionDomain().getCodeSource().getLocation().toURI());
        if (Files.isExecutable(filePath)) {  // Run with JAR file
            final JarFile jar = new JarFile(filePath.toFile());
            final Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                String entryName = jarEntry.getName();
                if (entryName.endsWith(path)) { //filter according to the path
                    InputStream stream = jar.getInputStream(jarEntry);
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = IOUtils.toByteArray(stream);
                    outputStream.write(buffer);
                    jar.close();
                    return outputStream.toByteArray();
                }
            }
            jar.close();
        }
        return null;
    }
}
