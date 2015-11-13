package org.mdconverter.unitconverter;

import org.jscience.mathematics.number.Real;
import org.jscience.physics.amount.Amount;
import org.mdconverter.consolewriter.ConsoleWriter;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.measure.converter.*;
import javax.measure.quantity.*;
import javax.measure.unit.NonSI;
import javax.measure.unit.SystemOfUnits;
import javax.measure.unit.Unit;

import java.io.Console;
import java.math.BigDecimal;

import static javax.measure.unit.SI.KILOGRAM;
import static javax.measure.unit.SI.AMPERE;
import static javax.measure.unit.SI.MICRO;


/**
 * Created by miso on 07.11.2015.
 */
public class UnitConverter {

    public static Real getLengthInAngstrom(Real length, Unit<Length> unit) {
        javax.measure.converter.UnitConverter converter = unit.getConverterTo(NonSI.ANGSTROM);
        double convert = converter.convert(length.doubleValue());
        return Real.valueOf(convert);
    }

    public static Real getLengthFromAngstrom(Real length, Unit<Length> unit) {
        javax.measure.converter.UnitConverter converter = NonSI.ANGSTROM.getConverterTo(unit);
        double convert = converter.convert(length.doubleValue());
        return Real.valueOf(convert);
    }
}
