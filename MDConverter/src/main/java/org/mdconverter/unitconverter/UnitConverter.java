package org.mdconverter.unitconverter;

import org.mdconverter.api.plugin.type.FileType;

/**
 *
 * Created by miso on 04.11.2015.
 */
public interface UnitConverter {

    Object convertStructure(Object structure, FileType fileType);
}