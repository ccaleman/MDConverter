package org.mdconverter.main;


import com.google.inject.Inject;
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

/**
 * Created by miso on 04.11.2015.
 */
@Singleton
public class GromacsReader extends AbstractReader {

    @Inject
    private ParseInputFile inputParser;

    @Override
    public String getDescription() {
        return "GromacsSR is able to read *.gro files.";
    }

    @Override
    public Structure getMetaModel() throws InvalidParameterException, InvalidInputException {
        try {
            Structure structure = (Structure) getStructure();
            PDBHeader pdbHeader = new PDBHeader();
            pdbHeader.setTitle(inputParser.getTitleFromFile(getInputFile()));
            PDBCrystallographicInfo pdbCrystallographicInfo = new PDBCrystallographicInfo();
            List<Chain> modelFromFile = inputParser.getModelFromFile(getInputFile());
            structure.setModel(0, modelFromFile);

            pdbCrystallographicInfo.setSpaceGroup(new SpaceGroup(0, 1, 1, "P 1", "P 1", BravaisLattice.CUBIC));
            CrystalCell crystalCell = pdbCrystallographicInfo.getSpaceGroup().getBravLattice().getExampleUnitCell();
            //call always after getModelFromFile --> box calculation
            inputParser.getCrystlCell(getInputFile(), crystalCell);
            pdbCrystallographicInfo.setCrystalCell(crystalCell);
            pdbHeader.setCrystallographicInfo(pdbCrystallographicInfo);

            structure.setPDBHeader(pdbHeader);
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

