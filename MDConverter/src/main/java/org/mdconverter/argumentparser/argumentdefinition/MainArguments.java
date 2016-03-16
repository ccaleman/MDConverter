package org.mdconverter.argumentparser.argumentdefinition;

import com.beust.jcommander.DynamicParameter;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.converters.PathConverter;
import com.beust.jcommander.internal.Lists;
import com.beust.jcommander.internal.Maps;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * Created by miso on 23.10.2015. <br>
 * Defines the possible arguments for the MDConverter framework
 */
@Parameters(commandDescription = "Application options")
public class MainArguments {

    @Parameter(names = {"--help", "-h", "-?"}, description = "Show usage", help = true)
    private boolean help = false;

    @Parameter(description = "usage of unspecified options will stop the application")
    private List<String> unknown = Lists.newArrayList();

    @Parameter(names = "-R", arity = 1, description = "FileReader you want to use")
    private String fileReader = null;

    @Parameter(names = "-S", arity = 0, description = "Define for structure type conversion\n" +
            "\t\tonly required if topology type is not defined")
    private boolean structure = false;

    @Parameter(names = "-T", arity = 0, description = "Define for topology type conversion\n" +
            "\t\tonly required if structure type is not defined")
    private boolean topology = false;

    @Parameter(names = "-W", arity = 1, description = "FileWriter you want to use")
    private String fileWriter = null;

    @Parameter(names = {"-i", "-in"}, arity = 1, required = true, description = "Input file",
            validateValueWith = InputFileCheck.class,
            converter = PathConverter.class)
    private Path inputFile = null;

    @Parameter(names = {"-o","-out"}, arity = 1, description="output goes to this file\n" +
            "\t\tif not defined, output goes to console", converter = PathConverter.class)
    private Path outputFile = null;

    @DynamicParameter(names = "-r", description = "Dynamic parameters for the FileReader", assignment = ":")
    private Map<String, String> readerParams = Maps.newHashMap();

    @DynamicParameter(names = "-w", description = "Dynamic parameters for the FileWriter", assignment = ":")
    private Map<String, String> writerParams = Maps.newHashMap();

    public boolean isHelp() {
        return help;
    }

    public List<String> getUnknown() {
        return unknown;
    }

    public Path getInputFile() {
        return inputFile;
    }

    public Path getOutputFile() {
        return outputFile;
    }

    public String getFileReader() {
        return fileReader;
    }

    public boolean isStructure() {
        return structure;
    }

    public boolean isTopology() {
        return topology;
    }

    public Map<String, String> getReaderParams() {
        return readerParams;
    }

    public Map<String, String> getWriterParams() {
        return writerParams;
    }

    public String getFileWriter() {
        return fileWriter;
    }
}


