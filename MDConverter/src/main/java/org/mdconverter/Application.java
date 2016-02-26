package org.mdconverter;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.mdconverter.inject.MDConverter;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by miso on 22.10.2015.
 */
public class Application {

    public static void main(String[] args) {
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream is = classloader.getResourceAsStream("config.properties");
            Properties prop = new Properties();
            prop.load(is);
            is.close();
            Injector injector = Guice.createInjector(new MDConverter(prop));
            MDConverterMain instance = injector.getInstance(MDConverterMain.class);
            instance.init(args);
        } catch (Exception e) {
            //TODO: remove from release
            //e.printStackTrace();
        }
    }
}
