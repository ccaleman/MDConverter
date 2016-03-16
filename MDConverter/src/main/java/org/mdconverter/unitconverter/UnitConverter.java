package org.mdconverter.unitconverter;

import org.mdconverter.api.plugin.PluginManifest;
import org.mdconverter.api.plugin.type.FileType;
import org.mdconverter.api.topologystructure.model.TopologyStructure;

/**
 *
 * Created by miso on 04.11.2015.
 */
public interface UnitConverter {

    /**
     * converts the given structure according to the defined {@link FileType} <br>
     * the UnitConverter gets the measurement units from the {@link PluginManifest} and converts directly from Reader to Writer
     *
     * @param structure created structure by the ReaderPlugin (units from ReaderPlugin)
     * @param fileType  defines which structure are given {@link TopologyStructure} or a Structure
     * @return the converted structure (units from WriterPlugin)
     */
    Object convertStructure(Object structure, FileType fileType);
}