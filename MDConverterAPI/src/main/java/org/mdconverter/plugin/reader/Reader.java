package org.mdconverter.plugin.reader;

import org.biojava.nbio.structure.Structure;
import org.mdconverter.plugin.InvalidParameterException;
import org.mdconverter.plugin.context.Context;

import java.nio.file.Path;

/**
 * This interface defines the explicit methods for reader plugins
 * @param <T> can either be a {@link Structure} or a TOPOLOGY STRUCTURE
 * Created by miso on 28.10.2015.
 */
//TODO: replace TOPOLOGY STRUCTURE with link
public interface Reader<T> {

    /**
     * Allows framework to set the input file defined by user
     * File exists already checked, file extension already checked
     * @param input path to defined input file
     */
    void setInputFile (Path input);

    /**
     * This method is called by the framework to get the generated structure from the plugin
     * @see Context#getUsage()
     * @return the structure which can either be {@link Structure} or a TOPOLOGY STRUCTURE
     * @throws InvalidParameterException if plugin is missing parameter
     */
    T getMetaModel() throws InvalidParameterException;
}
