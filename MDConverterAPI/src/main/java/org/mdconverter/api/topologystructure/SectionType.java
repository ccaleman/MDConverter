package org.mdconverter.api.topologystructure;

/**
 * Created by miso on 23.12.2015. <br>
 * Defines the SectionType (multiple section are possible) <br>
 * If unclear which to use then use {@link SectionType#STRUCTUREDATA} and all data should be used by the plugins
 */
public enum SectionType {

    FORCEFIELD,
    STRUCTUREDATA;
}
