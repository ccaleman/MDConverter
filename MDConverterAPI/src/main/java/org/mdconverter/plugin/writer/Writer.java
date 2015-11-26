package org.mdconverter.plugin.writer;

import org.mdconverter.plugin.InvalidParameterException;
import org.mdconverter.plugin.context.Context;

/**
 * This interface defines the explicit methods for writer plugins
 * Created by miso on 28.10.2015.
 */
public interface Writer {

    /**
     * This method is called by the framework to get the requested input file as converted format from the plugin
     * @see Context#getUsage()
     * @return the {@link String} holding the requested file in plugin format
     * @throws InvalidParameterException if plugin is missing parameter
     */
    String getOutput() throws InvalidParameterException;
}
