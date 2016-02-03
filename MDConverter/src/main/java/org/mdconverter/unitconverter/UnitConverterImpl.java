package org.mdconverter.unitconverter;

import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.xtal.CrystalCell;
import org.jscience.mathematics.number.Real;
import org.mdconverter.api.consolewriter.ConsoleWriter;
import org.mdconverter.api.plugin.type.FileType;
import org.mdconverter.api.topologystructure.model.TopologyStructure;
import org.mdconverter.api.topologystructure.model.impl.*;
import org.mdconverter.unitconverter.topologyhelper.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.measure.quantity.Quantity;
import javax.measure.unit.Unit;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Created by miso on 07.11.2015.
 */
@Singleton
public class UnitConverterImpl implements UnitConverter {

    private Map<String, Map<String, Map<String, String>>> readerUnits;
    private Map<String, Map<String, Map<String, String>>> writerUnits;

    private ConsoleWriter cw;

    @Inject
    public UnitConverterImpl(ConsoleWriter cw) {
        this.cw = cw;
    }

    public Object convertStructure(Object structure, FileType fileType) {
        cw.printInfoln("Started with unit conversion");
        if (fileType.equals(FileType.STRUCTURE)) {
            Unit<? extends Quantity> lengthX = Unit.valueOf(readerUnits.get("global").get("standard").get("length"));
            Unit<? extends Quantity> lengthY = Unit.valueOf(writerUnits.get("global").get("standard").get("length"));
            Structure struct = (Structure) structure;
            if (struct.nrModels() >= 1) {
                struct.getChains().stream()
                        .forEach(chain -> chain.getAtomGroups()
                                .forEach(group -> group.getAtoms().stream()
                                        .forEach(atom -> {
                                            atom.setX(Convert.convertFromXInY(Real.valueOf(atom.getX()), lengthX, lengthY).doubleValue());
                                            atom.setY(Convert.convertFromXInY(Real.valueOf(atom.getY()), lengthX, lengthY).doubleValue());
                                            atom.setZ(Convert.convertFromXInY(Real.valueOf(atom.getZ()), lengthX, lengthY).doubleValue());
                                        })));
            }
            CrystalCell crystalCell = struct.getCrystallographicInfo().getCrystalCell();
            if (crystalCell != null) {
                crystalCell.setA(Convert.convertFromXInY(Real.valueOf(crystalCell.getA()), lengthX, lengthY).doubleValue());
                crystalCell.setB(Convert.convertFromXInY(Real.valueOf(crystalCell.getB()), lengthX, lengthY).doubleValue());
                crystalCell.setC(Convert.convertFromXInY(Real.valueOf(crystalCell.getC()), lengthX, lengthY).doubleValue());
            }
            cw.printInfoln("Finished with unit conversion");
            return struct;
        } else {
            TopologyStructure struct = (TopologyStructure) structure;
            new AtomTypeH(readerUnits, writerUnits, struct.getDef()).convert(struct.getAtomTypes().stream().map(a -> ((AtomTypeImpl) a)).collect(Collectors.toList()));
            new AngleH(readerUnits, writerUnits, struct.getDef()).convert(struct.getAngleTypes().stream().map(a -> ((AngleImpl) a)).collect(Collectors.toList()));
            new BondH(readerUnits, writerUnits, struct.getDef()).convert(struct.getBondTypes().stream().map(a -> ((BondImpl) a)).collect(Collectors.toList()));
            new ConstraintH(readerUnits, writerUnits, struct.getDef()).convert(struct.getConstraintTypes().stream().map(a -> ((ConstraintImpl) a)).collect(Collectors.toList()));
            new DihedralH(readerUnits, writerUnits, struct.getDef()).convert(struct.getDihedralTypes().stream().map(a -> ((DihedralImpl) a)).collect(Collectors.toList()));
            new NonBondParamH(readerUnits, writerUnits, struct.getDef()).convert(struct.getNonBondParams().stream().map(a -> ((NonBondParamImpl) a)).collect(Collectors.toList()));
            new PairH(readerUnits, writerUnits, struct.getDef()).convert(struct.getPairTypes().stream().map(a -> ((PairImpl) a)).collect(Collectors.toList()));
            struct.getSections().forEach(section -> {
                new AngleH(readerUnits, writerUnits, struct.getDef()).convert(section.getAngles().stream().map(a -> ((AngleImpl) a)).collect(Collectors.toList()));
                new AngleRestraintH(readerUnits, writerUnits, struct.getDef()).convert(section.getAngleRestraints());
                new AtomH(readerUnits, writerUnits, struct.getDef()).convert(section.getAtoms().stream().map(a -> ((AtomImpl) a)).collect(Collectors.toList()));
                new BondH(readerUnits, writerUnits, struct.getDef()).convert(section.getBonds().stream().map(a -> ((BondImpl) a)).collect(Collectors.toList()));
                new ConstraintH(readerUnits, writerUnits, struct.getDef()).convert(section.getConstraints().stream().map(a -> ((ConstraintImpl) a)).collect(Collectors.toList()));
                new DihedralH(readerUnits, writerUnits, struct.getDef()).convert(section.getDihedrals().stream().map(a -> ((DihedralImpl) a)).collect(Collectors.toList()));
                new DihedralRestraintH(readerUnits, writerUnits, struct.getDef()).convert(section.getDihedralRestraints().stream().map(a -> ((DihedralRestraintImpl) a)).collect(Collectors.toList()));
                new OrientationRestraintH(readerUnits, writerUnits, struct.getDef()).convert(section.getOrientationRestraints().stream().map(a -> ((OrientationRestraintImpl) a)).collect(Collectors.toList()));
                new PairH(readerUnits, writerUnits, struct.getDef()).convert(section.getPairs().stream().map(a -> ((PairImpl) a)).collect(Collectors.toList()));
                new PairH(readerUnits, writerUnits, struct.getDef()).convert(section.getPairsNB().stream().map(a -> ((PairNB) a)).collect(Collectors.toList()));
                new PositionRestraintH(readerUnits, writerUnits, struct.getDef()).convert(section.getPositionRestraints().stream().map(a -> ((PositionRestraintImpl) a)).collect(Collectors.toList()));
                new SettleH(readerUnits, writerUnits, struct.getDef()).convert(section.getSettles().stream().map(a -> ((SettleImpl) a)).collect(Collectors.toList()));
            });
            cw.printInfoln("Finished with unit conversion");
            return struct;
        }
    }

    public void setReaderUnits(Map<String, Map<String, Map<String, String>>> readerUnits) {
        this.readerUnits = readerUnits;
    }

    public void setWriterUnits(Map<String, Map<String, Map<String, String>>> writerUnits) {
        this.writerUnits = writerUnits;
    }
}
