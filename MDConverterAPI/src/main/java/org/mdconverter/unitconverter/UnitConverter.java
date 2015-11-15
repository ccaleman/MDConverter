package org.mdconverter.unitconverter;

import org.biojava.nbio.structure.Structure;
import org.jscience.mathematics.number.Real;
import org.mdconverter.plugin.type.FileType;
import org.mdconverter.plugin.type.PluginType;

import javax.measure.quantity.Length;
import javax.measure.unit.Unit;

/**
 * Created by miso on 04.11.2015.
 */
public interface UnitConverter {

    Structure convertStructure(Structure structure, FileType fileType);
}