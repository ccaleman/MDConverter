package org.mdconverter.argumentparser.argumentdefinition;

import com.beust.jcommander.IValueValidator;
import com.beust.jcommander.ParameterException;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by miso on 12.11.2015.
 */
public class InputFileCheck implements IValueValidator<Path> {

    @Override
    /**
     * validates if the given file exists
     * @throws ParameterException if not
     */
    public void validate(String s, Path path) throws ParameterException {
        if (Files.notExists(path)) {
            throw new ParameterException(String.format("Given path \"%s\" for parameter " + s + " is not valid.", path));
        }
    }
}
