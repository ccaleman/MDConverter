package org.mdconverter.argumentparser;

import com.beust.jcommander.internal.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mdconverter.api.consolehandler.ConsoleHandler;
import org.mdconverter.api.plugin.PluginManifest;
import org.mdconverter.api.plugin.type.FileType;
import org.mdconverter.api.plugin.type.PluginType;
import org.mdconverter.consolehandler.ConsoleHandlerImpl;
import org.mdconverter.pluginloader.LoaderInput;
import org.mdconverter.pluginloader.PluginLoader;
import org.mdconverter.pluginloader.PluginMisconfigurationException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by miso on 19.11.2015.
 */
public class InputErrorHandlerTest {

    private InputErrorHandler errorHandler;
    private LoaderInput reader;
    private LoaderInput writer;
    private ConsoleHandler mockedCH;
    private PluginLoader mockPL;

    @Before
    public void setUp() throws Exception {
        generateLIReader();
        generateLIWriter();
        mockedCH = mock(ConsoleHandlerImpl.class);
        mockCW();
        mockPL = mock(PluginLoader.class);
        mockPL();
        errorHandler = new InputErrorHandler(mockedCH, mockPL);
    }

    @Test(expected = RuntimeException.class)
    public void testHandleErrorsWrongInputErrorSequence() throws Exception {
        when(mockedCH.getIntInput()).thenReturn(0);
        errorHandler.handleErrors(Lists.newArrayList(InputError.NO_READER, InputError.NO_FILETYPE), reader, writer);
    }

    @Test
    public void testHandleErrorsNoFileType() throws Exception {
        List<LoaderInput> loaderInputs = errorHandler.handleErrors(Lists.newArrayList(InputError.NO_FILETYPE), reader, writer);
        assertEquals(2, loaderInputs.size());
        assertEquals("Reader", loaderInputs.get(0).getPluginName());
        assertEquals("Writer", loaderInputs.get(1).getPluginName());
        assertEquals(FileType.STRUCTURE, reader.getFileType());
        assertEquals(FileType.STRUCTURE, writer.getFileType());
    }

    @Test
    public void testHandleErrorsNoReaderFound() throws Exception {
        List<LoaderInput> loaderInputs = errorHandler.handleErrors(Lists.newArrayList(InputError.NO_READER), reader, writer);
        assertEquals(2, loaderInputs.size());
        assertEquals("InputErrorReturned", loaderInputs.get(0).getPluginName());
        assertEquals("Writer", loaderInputs.get(1).getPluginName());
        assertEquals("InputErrorReturned", reader.getPluginName());
    }

    @Test
    public void testHandleErrorsNoWriterFound() throws Exception {
        List<LoaderInput> loaderInputs = errorHandler.handleErrors(Lists.newArrayList(InputError.NO_WRITER), reader, writer);
        assertEquals(2, loaderInputs.size());
        assertEquals("Reader", loaderInputs.get(0).getPluginName());
        assertEquals("InputErrorReturned", loaderInputs.get(1).getPluginName());
        assertEquals("InputErrorReturned", writer.getPluginName());
    }

    private void mockCW() {
        when(mockedCH.getIntInput()).thenReturn(1);
    }

    private void mockPL() throws IOException, PluginMisconfigurationException, URISyntaxException {
        PluginManifest mockPM = mock(PluginManifest.class);
        when(mockPM.getPluginName()).thenReturn("InputErrorReturned");
        when(mockPL.getPluginManifestsByLoaderInput(reader)).thenReturn(Lists.newArrayList(mockPM));
        when(mockPL.getPluginManifestsByLoaderInput(writer)).thenReturn(Lists.newArrayList(mockPM));
    }

    private void generateLIReader() {
        reader = new LoaderInput();
        reader.setFileType(FileType.TOPOLOGY);
        reader.setPluginName("Reader");
        reader.setPluginType(PluginType.READER);
    }

    private void generateLIWriter() {
        writer = new LoaderInput();
        writer.setFileType(FileType.TOPOLOGY);
        writer.setPluginName("Writer");
        writer.setPluginType(PluginType.WRITER);
    }
}