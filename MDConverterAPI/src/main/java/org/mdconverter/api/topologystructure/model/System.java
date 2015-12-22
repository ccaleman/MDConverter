package org.mdconverter.api.topologystructure.model;

/**
 * Created by miso on 04.12.2015.
 */
public class System {

    private String name;

    public System() {
    }

    public System(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
