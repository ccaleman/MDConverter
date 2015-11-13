package org.mdconverter.plugin.writer;

import org.biojava.nbio.structure.Structure;
import org.mdconverter.plugin.context.Context;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by miso on 28.10.2015.
 */
public interface Writer extends Context {

    void setArguments(Map<String, String> args);
    void setUnspecifiedArguments(List<String> args);
    void setStructure(Structure structure);
    String getOutput();
}
