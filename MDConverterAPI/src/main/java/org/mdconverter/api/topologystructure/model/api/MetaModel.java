package org.mdconverter.api.topologystructure.model.api;

import org.mdconverter.api.topologystructure.ModelVersion;
import org.mdconverter.api.topologystructure.model.TopologyStructure;

/**
 * Created by miso on 03.02.2016. <br>
 * adds versioning to the {@link TopologyStructure} and allows compatibility checks for plugins
 */
public interface MetaModel {

    /**
     * @return the actual {@link ModelVersion} of the {@link TopologyStructure}
     * @since {@link ModelVersion#V1}
     */
    ModelVersion getVersion();
}
