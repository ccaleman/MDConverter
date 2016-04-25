package org.mdconverter.ambertr.fileparser;

/**
 * Created by miso on 12.02.2016. <br>
 * Amber specific definitions
 */
public enum RadiusSet {

    BONDI("Bondi radii (bondi)"),
    AMBER6("amber6 modified Bondi radii (amber6)"),
    MBONDI("modified Bondi radii (mbondi)"),
    MBONDI2("H(N)-modified Bondi radii (mbondi2)"),
    MBONDI3("ArgH and AspGlu0 modified Bondi2 radii (mbondi3)");

    private String definition;

    RadiusSet(String definition) {
        this.definition = definition;
    }

    public static RadiusSet fromString(String text) {
        if (text != null) {
            for (RadiusSet b : RadiusSet.values()) {
                if (text.equalsIgnoreCase(b.definition)) {
                    return b;
                }
            }
        }
        return null;
    }

    public String toString() {
        return this.definition;
    }
}
