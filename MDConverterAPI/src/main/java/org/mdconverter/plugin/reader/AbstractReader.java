package org.mdconverter.plugin.reader;

import org.mdconverter.plugin.AbstractPlugin;
import org.mdconverter.plugin.InvalidParameterException;

import java.nio.file.Path;

/**
 * Abstract class which has to be implemented by custom reader plugins to be loadable by the framework
 * @see AbstractPlugin
 * Created by miso on 28.10.2015.
 */
public abstract class AbstractReader<T> extends AbstractPlugin<T> implements Reader<T> {

    /**
     * Holds the {@link Path} to the input file defined by the user
     * File exists already checked, file extension already checked
     */
    private Path inputFile;

    @Override
    public T getMetaModel() throws InvalidParameterException {
        return this.getStructure();
    }

    /**
     * @see #setInputFile(Path)
     * @param input path to defined input file
     */
    public final void setInputFile(Path input) {
        this.inputFile = input;
    }

    /**
     * provides access to the input file specified by the user
     * @return a {@link Path} to input file
     */
    protected final Path getInputFile() {
        return inputFile;
    }
}
