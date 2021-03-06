package org.mdconverter.ambersr.api;

import org.biojava.nbio.structure.Structure;

/**
 * Created by miso on 20.11.2015. <br>
 * Interface which has to be implemented by Python script
 */
public interface ReadStructure {

    Structure readFileToStructure(String file, Structure structure);
}
