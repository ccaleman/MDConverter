package org.mdconverter.plugin.writer;

import org.mdconverter.plugin.InvalidParameterException;
import org.mdconverter.plugin.context.Context;

import java.util.List;
import java.util.Map;

/**
 * Created by miso on 28.10.2015.
 */
public interface Writer<T> extends Context {

    void setArguments(Map<String, String> args);
    void setUnspecifiedArguments(List<String> args);
    void setStructure(T structure);
    String getOutput() throws InvalidParameterException;
}
