package org.mdconverter.api.topologystructure.model;

import java.math.BigDecimal;

/**
 * Created by miso on 27.12.2015.
 */
public class PairNB extends Pair {

    public PairNB() {
    }

    public PairNB(String ai, String aj, int funcType, BigDecimal c1, BigDecimal c2, BigDecimal c3, BigDecimal c4) {
        super(ai, aj, funcType, c1, c2);
        super.setC3(c3);
        super.setC4(c4);
    }
}
