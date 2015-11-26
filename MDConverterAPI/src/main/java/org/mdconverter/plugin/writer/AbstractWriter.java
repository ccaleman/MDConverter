package org.mdconverter.plugin.writer;

import org.mdconverter.plugin.AbstractPlugin;
import org.mdconverter.plugin.InvalidParameterException;

/**
 * Abstract class which has to be implemented by custom writer plugins to be loadable by the framework
 * @see AbstractPlugin
 * Created by miso on 28.10.2015.
 */
public abstract class AbstractWriter<T> extends AbstractPlugin<T> implements Writer {

    /**
     * @see Writer#getOutput()
     * @throws InvalidParameterException
     */
    @Override
    public String getOutput() throws InvalidParameterException {
        return null;
    }
}
