package org.mdconverter.inject;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import org.mdconverter.api.consolewriter.ConsoleWriter;
import org.mdconverter.api.jython.JythonObjectFactory;
import org.mdconverter.argumentparser.ArgumentParser;
import org.mdconverter.argumentparser.InputErrorHandler;
import org.mdconverter.classloader.PluginLoader;
import org.mdconverter.consolewriter.ConsoleWriterImpl;
import org.mdconverter.jythonsupport.JythonObjectFactoryImpl;
import org.mdconverter.unitconverter.UnitConverter;
import org.mdconverter.unitconverter.UnitConverterImpl;

import java.io.OutputStream;
import java.util.Properties;

/**
 * Created by miso on 28.10.2015.
 */
public class MDConverter extends AbstractModule{

    private Properties properties;

    public MDConverter(Properties properties) {
        this.properties = properties;
    }

    @Override
    protected void configure() {
        Names.bindProperties(binder(), getProperties());
        bind(OutputStream.class).annotatedWith(Names.named("INFO")).toInstance(System.out);
        bind(OutputStream.class).annotatedWith(Names.named("ERROR")).toInstance(System.err);
        bind(ConsoleWriter.class).to(ConsoleWriterImpl.class);
        bind(UnitConverter.class).to(UnitConverterImpl.class);
        bind(JythonObjectFactory.class).to(JythonObjectFactoryImpl.class);
        bind(ArgumentParser.class);
        bind(PluginLoader.class);
        bind(InputErrorHandler.class);
    }

    public Properties getProperties() {
        return properties;
    }
}
