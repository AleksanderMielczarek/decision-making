package com.gmail.alekmiel91.decisionmaking.console;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Aleksander Mielczarek
 *         Created on 2015-04-03.
 */
@Parameters(resourceBundle = "bundle")
@Getter
@Setter
public class Arguments {

    @Parameter(names = {"-file", "-f"}, descriptionKey = "arguments.description.file", required = true)
    private File file;

    @Parameter(names = {"-decisions", "-dc"}, descriptionKey = "arguments.description.decision.criteria", variableArity = true,
            validateWith = DecisionCriteriaValidator.class)
    private List<String> decisionCriteria = Arrays.stream(DecisionCriteria.values())
            .map(DecisionCriteria::getShortcut)
            .collect(Collectors.toList());

    @Parameter(names = {"-help", "-h"}, descriptionKey = "arguments.description.help", help = true)
    private boolean help;

    public static Arguments parse(String[] args) {
        Arguments arguments = new Arguments();
        JCommander jCommander = new JCommander(arguments);

        try {
            jCommander.parse(args);
        } catch (ParameterException e) {
            e.printStackTrace();
            jCommander.usage();
            System.exit(1);
        }

        if (arguments.isHelp()) {
            jCommander.usage();
            System.exit(0);
        }

        return arguments;
    }
}
