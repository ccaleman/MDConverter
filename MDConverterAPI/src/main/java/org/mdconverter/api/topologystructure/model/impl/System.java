package org.mdconverter.api.topologystructure.model.impl;

import org.mdconverter.api.topologystructure.ModelVersion;

/**
 * Created by miso on 04.12.2015.
 */
public class System {

    //Fields
    private String name;

    //Constructors

    /**
     * default constructor
     *
     * @since {@link ModelVersion#V1}
     */
    public System() {
    }

    /**
     * constructor
     * @since {@link ModelVersion#V1}
     */
    public System(String name) {
        this.name = name;
    }

    //Getters & Setters

    /**
     * defines the name of the system
     * @return a String
     * @since {@link ModelVersion#V1}
     */
    public String getName() {
        return name;
    }

    /**
     * sets the name of the system
     * @param name a String
     * @since {@link ModelVersion#V1}
     */
    public void setName(String name) {
        this.name = name;
    }
}
