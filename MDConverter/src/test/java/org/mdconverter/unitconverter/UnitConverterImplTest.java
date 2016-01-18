package org.mdconverter.unitconverter;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.biojava.nbio.structure.*;
import org.junit.Before;
import org.junit.Test;
import org.mdconverter.api.consolewriter.ConsoleWriter;
import org.mdconverter.api.plugin.type.FileType;
import org.mdconverter.consolewriter.ConsoleWriterImpl;

import javax.measure.converter.ConversionException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * Created by miso on 19.11.2015.
 */
public class UnitConverterImplTest {

    private UnitConverterImpl unitConverter;
    private Structure struct;
    private ConsoleWriter mockedCW;

    @Before
    public void setUp() throws Exception {
        this.struct = new StructureImpl();
        Chain chain = new ChainImpl();
        Group group = new AminoAcidImpl();
        Atom atom = new AtomImpl();
        mockedCW = mock(ConsoleWriterImpl.class);
        atom.setX(1);
        atom.setY(12);
        atom.setZ(123);
        group.setAtoms(Lists.newArrayList(atom));
        chain.setAtomGroups(Lists.newArrayList(group));
        struct.addChain(chain);
        unitConverter = new UnitConverterImpl(mockedCW);
    }

    @Test
    public void testConvertStructure() throws Exception {
        unitConverter.setReaderUnits(ImmutableMap.of("global", ImmutableMap.of("standard", ImmutableMap.of("length", "nm"))));
        unitConverter.setWriterUnits(ImmutableMap.of("global", ImmutableMap.of("standard", ImmutableMap.of("length", "Å"))));
        Structure structure = (Structure) unitConverter.convertStructure(this.struct, FileType.STRUCTURE);
        assertEquals(10, structure.getChain(0).getAtomGroup(0).getAtom(0).getX(), 0);
        assertEquals(120, structure.getChain(0).getAtomGroup(0).getAtom(0).getY(), 0);
        assertEquals(1230, structure.getChain(0).getAtomGroup(0).getAtom(0).getZ(), 0);
    }

    @Test(expected=ConversionException.class)
    public void testConvertStructureError() throws Exception {
        unitConverter.setReaderUnits(ImmutableMap.of("global", ImmutableMap.of("standard", ImmutableMap.of("length", "nm"))));
        unitConverter.setWriterUnits(ImmutableMap.of("global", ImmutableMap.of("standard", ImmutableMap.of("length", "ps"))));
        unitConverter.convertStructure(this.struct, FileType.STRUCTURE);
    }

}