package org.mdconverter.api.topologystructure.model.api;

import org.mdconverter.api.topologystructure.ModelVersion;

/**
 * Created by miso on 07.01.2016. <br>
 * Is implemented by Classes which may support more than one unit per value field <br>
 * For detailed description see manual of MDConverter
 * @see ValueHolder
 */
public interface FuncType {

    /**
     * defines which unit definition of the manifest.json file will be used <br>
     * <pre>
     * "Class/Class": { <br>
     *     "1": {<br>
     *         "c1": unit,<br>
     *         "c2": unit<br>
     *     },<br>
     *     "2": {<br>
     *         "c1": unit,<br>
     *         "c2": unit<br>
     *     },<br>
     *     .<br>
     * </pre>
     * For a detailed description see the manual of MDConverter
     * @return a Integer
     * @since {@link ModelVersion#V1}
     */
    Integer getFuncType();

    /**
     * @param type a Integer
     * @since {@link ModelVersion#V1}
     */
    void setFuncType(Integer type);
}
