package org.mdconverter.jythonsupport;

import org.mdconverter.api.jython.JythonObjectFactory;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Jython Object Factory using PySystemState
 */
@Singleton
public class JythonObjectFactoryImpl implements JythonObjectFactory {

    //Injects
    private PythonInterpreter interpreter;

    @Inject
    public JythonObjectFactoryImpl(PythonInterpreter interpreter) {
        this.interpreter = interpreter;
    }

    /**
     * Loads the given jar-File to the Jython interpreter
     *
     * @param pluginJarPath path to the Python plugin
     */
    public void addPluginToInterpreter(String pluginJarPath) {
        PySystemState sys = interpreter.getSystemState();
        sys.path.insert(0, new PyString(pluginJarPath));
    }

    /**
     * The createObject() method is responsible for the actual creation of the
     * Jython object into Java bytecode.
     */
    @Override
    public Object createObject(Object interfaceType, String moduleName, String pathToPackage) {
        if (interpreter == null) {
            throw new RuntimeException("You have to add the pluginJar --> call 'addPluginToInterpreter' first.");
        }
        Object javaInt = null;
        interpreter.exec("from " + pathToPackage + moduleName + " import " + moduleName);
        PyObject pyObject = interpreter.get(moduleName);

        try {
            // Create a new object reference of the Jython module and
            // store into PyObject.
            PyObject newObj = pyObject.__call__();
            // Call __tojava__ method on the new object along with the interface name
            // to create the java bytecode
            javaInt = newObj.__tojava__(Class.forName(interfaceType.toString().substring(
                    interfaceType.toString().indexOf(" ") + 1,
                    interfaceType.toString().length())));
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return javaInt;
    }
}