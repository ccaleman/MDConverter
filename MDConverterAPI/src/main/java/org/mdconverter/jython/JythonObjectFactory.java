package org.mdconverter.jython;

/**
 * This interface provides access to the Python interpreter
 * to add Python scripts from a jar-file and load specific modules from them
 * Created by miso on 25.11.2015.
 */
public interface JythonObjectFactory {

    /**
     * @param interfaceType the expected Java interface which is implemented in Python
     * @param moduleName the module name of the Python implementation
     * @return the requested module as Java class (needs to be casted to expected Java interface)
     */
    Object createObject(Object interfaceType, String moduleName);
}
