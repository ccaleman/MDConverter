package org.mdconverter.api.plugin.reader;

import org.biojava.nbio.structure.Structure;
import org.mdconverter.api.plugin.InvalidInputException;
import org.mdconverter.api.plugin.InvalidParameterException;
import org.mdconverter.api.plugin.context.Context;
import org.mdconverter.api.topologystructure.TopologyStructure;

import java.nio.file.Path;

/**
 * This interface defines the explicit methods for reader plugins
 * @param <T> can either be a {@link Structure} or a {@link TopologyStructure}
 * Created by miso on 28.10.2015.
 */
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
     * @return the structure which can either be {@link Structure} or a {@link TopologyStructure}
     * @throws InvalidParameterException if plugin is missing parameter
     * @throws InvalidInputException if the given input file is not correct
     */
    T getMetaModel() throws InvalidParameterException, InvalidInputException;
}
