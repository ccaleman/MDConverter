package org.mdconverter.api.topologystructure.model;

import java.math.BigDecimal;

/**
 * Created by miso on 04.12.2015.
 */
public class AngleRestraint extends AngleRestraintZ {

    private String ak;
    private String al;

    public AngleRestraint() {
    }

    public AngleRestraint(String ai, String aj, Integer funcType, BigDecimal c1, BigDecimal c2, BigDecimal c3, String ak, String al) {
        super(ai, aj, funcType, c1, c2, c3);
        this.ak = ak;
        this.al = al;
    }

    public String getAk() {
        return ak;
    }

    public void setAk(String ak) {
        this.ak = ak;
    }

    public String getAl() {
        return al;
    }

    public void setAl(String al) {
        this.al = al;
    }
}
