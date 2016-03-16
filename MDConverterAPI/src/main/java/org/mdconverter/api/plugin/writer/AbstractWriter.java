package org.mdconverter.api.plugin.writer;

import org.mdconverter.api.plugin.AbstractPlugin;
import org.mdconverter.api.plugin.InvalidInputException;
import org.mdconverter.api.plugin.InvalidParameterException;

/**
 * Abstract class which has to be implemented by custom writer plugins to be loadable by the framework
 * @see AbstractPlugin
 * Created by miso on 28.10.2015.
 */
public abstract class AbstractWriter<T> extends AbstractPlugin<T> implements Writer {

    /**
     * @see Writer#getOutput()
     * @throws InvalidParameterException | InvalidInputException
     */
    @Override
    public String getOutput() throws InvalidParameterException, InvalidInputException {
        return null;
    }
}
