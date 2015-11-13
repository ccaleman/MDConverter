package org.mdconverter.plugin.reader;

import org.biojava.nbio.structure.Structure;
import org.mdconverter.plugin.context.Context;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * Created by miso on 28.10.2015.
 */
public interface Reader extends Context{

    void setArguments(Map<String, String> args);
    void setUnspecifiedArguments(List<String> args);
    void setStructure(Structure structure);
    void setInputFile (Path input);
    Structure getMetaModel();
}
