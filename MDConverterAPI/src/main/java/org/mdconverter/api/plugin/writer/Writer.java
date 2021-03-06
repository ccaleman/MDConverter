package org.mdconverter.api.plugin.writer;

import org.mdconverter.api.plugin.InvalidInputException;
import org.mdconverter.api.plugin.InvalidParameterException;
import org.mdconverter.api.plugin.context.Context;

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
     * @throws InvalidInputException if the given {@link org.biojava.nbio.structure.Structure} is incomplete
     */
    String getOutput() throws InvalidParameterException, InvalidInputException;
}
