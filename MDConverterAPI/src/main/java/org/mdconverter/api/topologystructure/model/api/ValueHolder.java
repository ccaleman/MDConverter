package org.mdconverter.api.topologystructure.model.api;

import org.mdconverter.api.topologystructure.ModelVersion;

import java.math.BigDecimal;

/**
 * Created by miso on 07.01.2016. <br>
 * {@link ValueHolder} holds the convertible values of the Implementation
 * An implementation of {@link ValueHolder} may holds at least one convertible value and max. up to six convertible values
 */
public abstract class ValueHolder {

    /**
     * @param c1 a BigDecimal
     * @throws UnsupportedOperationException
     * @since {@link ModelVersion#V1}
     */
    public void setC1(BigDecimal c1) {
        throw new UnsupportedOperationException();
    }

    /**
     * @param c2 a BigDecimal
     * @throws UnsupportedOperationException
     * @since {@link ModelVersion#V1}
     */
    public void setC2(BigDecimal c2) {
        throw new UnsupportedOperationException();
    }

    /**
     * @param c3 a BigDecimal
     * @throws UnsupportedOperationException
     * @since {@link ModelVersion#V1}
     */
    public void setC3(BigDecimal c3) {
        throw new UnsupportedOperationException();
    }

    /**
     * @param c4 a BigDecimal
     * @throws UnsupportedOperationException
     * @since {@link ModelVersion#V1}
     */
    public void setC4(BigDecimal c4) {
        throw new UnsupportedOperationException();
    }

    /**
     * @param c5 a BigDecimal
     * @throws UnsupportedOperationException
     * @since {@link ModelVersion#V1}
     */
    public void setC5(BigDecimal c5) {
        throw new UnsupportedOperationException();
    }

    /**
     * @param c6 a BigDecimal
     * @throws UnsupportedOperationException
     * @since {@link ModelVersion#V1}
     */
    public void setC6(BigDecimal c6) {
        throw new UnsupportedOperationException();
    }

    /**
     * @return a BigDecimal
     * @throws UnsupportedOperationException
     * @since {@link ModelVersion#V1}
     */
    public BigDecimal getC1() {
        throw new UnsupportedOperationException();
    }

    /**
     * @return a BigDecimal
     * @throws UnsupportedOperationException
     * @since {@link ModelVersion#V1}
     */
    public BigDecimal getC2() {
        throw new UnsupportedOperationException();
    }

    /**
     * @return a BigDecimal
     * @throws UnsupportedOperationException
     * @since {@link ModelVersion#V1}
     */
    public BigDecimal getC3() {
        throw new UnsupportedOperationException();
    }

    /**
     * @return a BigDecimal
     * @throws UnsupportedOperationException
     * @since {@link ModelVersion#V1}
     */
    public BigDecimal getC4() {
        throw new UnsupportedOperationException();
    }

    /**
     * @return a BigDecimal
     * @throws UnsupportedOperationException
     * @since {@link ModelVersion#V1}
     */
    public BigDecimal getC5() {
        throw new UnsupportedOperationException();
    }

    /**
     * @return a BigDecimal
     * @throws UnsupportedOperationException
     * @since {@link ModelVersion#V1}
     */
    public BigDecimal getC6() {
        throw new UnsupportedOperationException();
    }
}
