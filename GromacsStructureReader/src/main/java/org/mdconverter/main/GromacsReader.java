package org.mdconverter.main;


import org.biojava.nbio.structure.Chain;
import org.biojava.nbio.structure.PDBCrystallographicInfo;
import org.biojava.nbio.structure.PDBHeader;
import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.xtal.BravaisLattice;
import org.biojava.nbio.structure.xtal.CrystalCell;
import org.biojava.nbio.structure.xtal.SpaceGroup;
import org.mdconverter.api.plugin.InvalidInputException;
import org.mdconverter.api.plugin.InvalidParameterException;
import org.mdconverter.api.plugin.reader.AbstractReader;
import org.mdconverter.fileparser.ParseInputFile;

import javax.inject.Singleton;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Created by miso on 04.11.2015.
 */
@Singleton
public class GromacsReader extends AbstractReader {

    @Override
    public String getDescription() {
        return "GromacsSR is able to read *.gro files.";
    }

    @Override
    public Structure getMetaModel() throws InvalidParameterException, InvalidInputException {
        try {
            Structure structure = (Structure) getStructure();
            PDBHeader pdbHeader = new PDBHeader();
            pdbHeader.setTitle(ParseInputFile.getTitleFromFile(getInputFile()));
            PDBCrystallographicInfo pdbCrystallographicInfo = new PDBCrystallographicInfo();
            Optional<CrystalCell> crystlCell = ParseInputFile.getCrystlCell(getInputFile());
            if (crystlCell.isPresent()) {
                pdbCrystallographicInfo.setCrystalCell(crystlCell.get());
                pdbCrystallographicInfo.setSpaceGroup(new SpaceGroup(0, 1, 1, "P 1", "P 1", BravaisLattice.CUBIC));
                pdbHeader.setCrystallographicInfo(pdbCrystallographicInfo);
            }
            structure.setPDBHeader(pdbHeader);
            List<Chain> modelFromFile = ParseInputFile.getModelFromFile(getInputFile());
            structure.setModel(0, modelFromFile);
            return structure;
        } catch (IOException e) {
            getConsoleWriter().printErrorln(e.getMessage());
            throw new InvalidInputException(getName() + " wasn't able to parse input file!");
        }
    }

    @Override
    public String getUsage() {
        return "GromacsSR do not need any special arguments aberrant from normal usage.";
    }
}

