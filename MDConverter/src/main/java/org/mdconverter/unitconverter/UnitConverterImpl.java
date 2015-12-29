package org.mdconverter.unitconverter;

import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.xtal.CrystalCell;
import org.jscience.mathematics.number.Real;
import org.mdconverter.api.plugin.type.FileType;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.measure.quantity.Quantity;
import javax.measure.unit.Unit;
import java.math.BigDecimal;
import java.util.Map;


/**
 * Created by miso on 07.11.2015.
 */
@Singleton
public class UnitConverterImpl implements UnitConverter {

    private Map<String, Map<String, Map<String, String>>> readerUnits;
    private Map<String, Map<String, Map<String, String>>> writerUnits;

    @Inject
    public UnitConverterImpl() {
    }

    public Object convertStructure(Object structure, FileType fileType) {
        Unit<? extends Quantity> lengthX = Unit.valueOf(readerUnits.get("global").get("standard").get("length"));
        Unit<? extends Quantity> lengthY = Unit.valueOf(writerUnits.get("global").get("standard").get("length"));
        if (fileType.equals(FileType.STRUCTURE)) {
            Structure struct = (Structure) structure;
            if (struct.nrModels() >= 1) {
                struct.getChains().stream()
                        .forEach(chain -> chain.getAtomGroups()
                                .forEach(group -> group.getAtoms().stream()
                                        .forEach(atom -> {
                                            atom.setX(convertFromXInY(Real.valueOf(atom.getX()), lengthX, lengthY).doubleValue());
                                            atom.setY(convertFromXInY(Real.valueOf(atom.getY()), lengthX, lengthY).doubleValue());
                                            atom.setZ(convertFromXInY(Real.valueOf(atom.getZ()), lengthX, lengthY).doubleValue());
                                        })));
            }
            CrystalCell crystalCell = struct.getCrystallographicInfo().getCrystalCell();
            if (crystalCell != null) {
                crystalCell.setA(convertFromXInY(Real.valueOf(crystalCell.getA()), lengthX, lengthY).doubleValue());
                crystalCell.setB(convertFromXInY(Real.valueOf(crystalCell.getB()), lengthX, lengthY).doubleValue());
                crystalCell.setC(convertFromXInY(Real.valueOf(crystalCell.getC()), lengthX, lengthY).doubleValue());
            }
            return struct;
        } else {
            test();
        }
        return null;
    }

    public void setReaderUnits(Map<String, Map<String, Map<String, String>>> readerUnits) {
        this.readerUnits = readerUnits;
    }

    public void setWriterUnits(Map<String, Map<String, Map<String, String>>> writerUnits) {
        this.writerUnits = writerUnits;
    }

    private Real convertFromXInY(Real length, Unit<? extends Quantity> X, Unit<? extends Quantity> Y) {
        javax.measure.converter.UnitConverter converter = X.getConverterTo(Y);
        double convert = converter.convert(length.doubleValue());
        return Real.valueOf(convert);
    }

    private void test() {
        Unit kJ = Unit.valueOf("kJ");
        Unit mol = Unit.valueOf("mol");
        Unit nm = Unit.valueOf("nm").pow(2);
        Unit<? extends Quantity> unit = Unit.valueOf("kJ/mol/nm^2");
        Unit<? extends Quantity> unit2 = Unit.valueOf("J/mol/m^2");
        javax.measure.converter.UnitConverter to = unit.getConverterTo(unit2);
        BigDecimal decimal = new BigDecimal(0.09572);

        double convert = to.convert(decimal.floatValue());
        System.out.println(unit.toString());
    }
}
