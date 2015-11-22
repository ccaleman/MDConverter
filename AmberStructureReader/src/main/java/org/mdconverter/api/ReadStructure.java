package org.mdconverter.api;

import org.biojava.nbio.structure.Structure;

/**
 * Created by miso on 20.11.2015.
 */
public interface ReadStructure {

    Structure readFileToStructure(String file);
}
