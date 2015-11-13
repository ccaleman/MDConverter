package org.mdconverter.unitconverter;

import org.jscience.mathematics.number.Real;

import javax.measure.quantity.Length;
import javax.measure.unit.Unit;

/**
 * Created by miso on 04.11.2015.
 */
public interface UnitConverter {

    Real getLengthInAngstrom(Real length, Unit<Length> unit);

    Real getLengthFromAngstrom(Real length, Unit<Length> unit);
}