package org.mdconverter.api.topologystructure.model.impl;

import org.mdconverter.api.topologystructure.ModelVersion;

import java.math.BigDecimal;

/**
 * Created by miso on 27.12.2015.
 */
public class PairNB extends PairImpl {

    //Constructors

    /**
     * default constructor
     *
     * @since {@link ModelVersion#V1}
     */
    public PairNB() {
    }

    /**
     * constructor for four convertible values
     * @since {@link ModelVersion#V1}
     */
    public PairNB(String ai, String aj, Integer funcType, BigDecimal c1, BigDecimal c2, BigDecimal c3, BigDecimal c4) {
        super(ai, aj, funcType, c1, c2);
        super.setC3(c3);
        super.setC4(c4);
    }
}
