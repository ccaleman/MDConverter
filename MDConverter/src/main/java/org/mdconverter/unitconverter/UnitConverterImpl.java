package org.mdconverter.unitconverter;

import org.biojava.nbio.structure.Structure;
import org.jscience.mathematics.number.Real;
import org.mdconverter.plugin.type.FileType;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.measure.quantity.Quantity;
import javax.measure.unit.Unit;
import java.util.Map;


/**
 * Created by miso on 07.11.2015.
 */
@Singleton
public class UnitConverterImpl implements UnitConverter {

    private Map<String, String> readerUnits;
    private Map<String, String> writerUnits;

    @Inject
    public UnitConverterImpl() {
    }

    public Object convertStructure(Object structure, FileType fileType) {
        if (fileType.equals(FileType.STRUCTURE)) {
            Structure struct = (Structure) structure;
            Unit<? extends Quantity> lengthX = Unit.valueOf(readerUnits.get("length"));
            Unit<? extends Quantity> lengthY = Unit.valueOf(writerUnits.get("length"));
            struct.getChains().stream()
                    .forEach(chain -> chain.getAtomGroups()
                    .forEach(group -> group.getAtoms().stream()
                    .forEach(atom -> {
                        atom.setX(getLengthFromXInY(Real.valueOf(atom.getX()), lengthX, lengthY).doubleValue());
                        atom.setY(getLengthFromXInY(Real.valueOf(atom.getY()), lengthX, lengthY).doubleValue());
                        atom.setZ(getLengthFromXInY(Real.valueOf(atom.getZ()), lengthX, lengthY).doubleValue());
                    })));
            return struct;
        } else {

        }
        return null;
    }

    public void setReaderUnits(Map<String, String> readerUnits) {
        this.readerUnits = readerUnits;
    }

    public void setWriterUnits(Map<String, String> writerUnits) {
        this.writerUnits = writerUnits;
    }

    private Real getLengthFromXInY(Real length, Unit<? extends Quantity> X, Unit<? extends Quantity> Y) {
        javax.measure.converter.UnitConverter converter = X.getConverterTo(Y);
        double convert = converter.convert(length.doubleValue());
        return Real.valueOf(convert);
    }
}
