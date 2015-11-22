package org.mdconverter.main;

import org.biojava.nbio.structure.Structure;
import org.mdconverter.api.ReadStructure;
import org.mdconverter.jythonsupport.JythonObjectFactory;
import org.mdconverter.plugin.InvalidParameterException;
import org.mdconverter.plugin.reader.AbstractReader;

import java.net.URISyntaxException;
import java.nio.file.Paths;

/**
 * Created by miso on 19.11.2015.
 */
public class AmberReader extends AbstractReader {

    @Override
    public String getDescription() {
        return super.getDescription();
    }

    @Override
    public Structure getMetaModel() throws InvalidParameterException {
        return LoadJythonScripts();
    }

    @Override
    public String getUsage() {
        return super.getUsage();
    }

    private Structure LoadJythonScripts() {

        JythonObjectFactory factory = null;
        try {
            ReadStructure readstructureimpl = (ReadStructure) JythonObjectFactory.createObject(ReadStructure.class, "readstructureimpl", Paths.get(getClass().getProtectionDomain().getCodeSource().getLocation().toURI()).toString());
            Structure structure = readstructureimpl.readFileToStructure(getInputFile().toString());
            getConsoleWriter().printErrorln(structure.getName());
            return structure;

            //factory = new JythonObjectFactory(
            //        ReadStructure.class, "readstructureimpl", "readstructureimpl", Paths.get(getClass().getProtectionDomain().getCodeSource().getLocation().toURI()).toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        //ReadStructure struct = (ReadStructure) factory.createObject();
        //Structure structure = struct.readFileToStructure(getInputFile());
        //getConsoleWriter().printErrorln(structure.getName());
        return null;
    }
}
