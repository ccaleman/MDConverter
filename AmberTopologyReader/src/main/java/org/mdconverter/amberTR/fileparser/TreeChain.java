package org.mdconverter.ambertr.fileparser;

/**
 * Created by miso on 12.02.2016. <br>
 * Amber specific definitions
 */
public enum TreeChain {
    M,
    S,
    E,
    BLA,
    THREE;

    public static TreeChain fromString(String x) {
        try {
            return TreeChain.valueOf(x);
        } catch (Exception ignored) {
            switch (x) {
                case "3":
                    return TreeChain.THREE;
                default:
                    return null;
            }
        }
    }
}
