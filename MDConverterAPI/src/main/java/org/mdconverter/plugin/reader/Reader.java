package org.mdconverter.plugin.reader;

import org.mdconverter.plugin.InvalidParameterException;
import org.mdconverter.plugin.context.Context;

import java.nio.file.Path;

/**
 * Created by miso on 28.10.2015.
 */
public interface Reader<T> extends Context{

    void setStructure(T structure);
    void setInputFile (Path input);
    T getMetaModel() throws InvalidParameterException;
}
