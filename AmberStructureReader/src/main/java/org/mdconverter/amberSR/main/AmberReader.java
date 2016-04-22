package org.mdconverter.ambersr.main;

import org.biojava.nbio.structure.Structure;
import org.mdconverter.ambersr.api.ReadStructure;
import org.mdconverter.api.jython.JythonObjectFactory;
import org.mdconverter.api.plugin.InvalidInputException;
import org.mdconverter.api.plugin.InvalidParameterException;
import org.mdconverter.api.plugin.reader.AbstractReader;

/**
 * Created by miso on 19.11.2015.
 */
public class AmberReader extends AbstractReader {

    @Override
    public String getDescription() {
        return super.getDescription();
    }

    @Override
    public Structure getMetaModel() throws InvalidParameterException, InvalidInputException {
        return LoadJythonScripts();
    }

    @Override
    public String getUsage() {
        return super.getUsage();
    }

    /**
     * @return a Strcuture read in by a Python script
     * @throws InvalidInputException
     */
    private Structure LoadJythonScripts() throws InvalidInputException {
        //get the object factory from the framework
        JythonObjectFactory jof = getJythonObjectFactory();
        //generate a Java implementation out of the Python script which implements the defined interface ReadStructure.class
        ReadStructure readFile = (ReadStructure) jof.createObject(ReadStructure.class, "ReadFile", "Lib.processfile.");
        //get the Structure from the Java implementation
        Structure structure = readFile.readFileToStructure(getInputFile().toString(), (Structure) getStructure());
        //inform the user about progress
        getConsoleHandler().printInfoln(structure.getName() + " successfully read in");
        return structure;
    }
}
