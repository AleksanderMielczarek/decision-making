package com.gmail.alekmiel91.decisionmaking.console;

import com.gmail.alekmiel91.decisionmaking.toolkit.DecisionMakingException;
import com.gmail.alekmiel91.decisionmaking.toolkit.data.DecisionMatrix;
import com.gmail.alekmiel91.decisionmaking.toolkit.loader.DecisionMatrixLoader;
import com.gmail.alekmiel91.decisionmaking.toolkit.loader.YAMLDecisionMatrixLoader;

import java.io.File;

/**
 * @author Aleksander Mielczarek
 *         Created on 2015-03-25
 */
public class ConsoleDecisionMaking {

    public static void main(String[] args) throws DecisionMakingException {
        Arguments arguments = Arguments.parse(args);
        File file = arguments.getFile();
        DecisionMatrixLoader decisionMatrixLoader = new YAMLDecisionMatrixLoader();
        DecisionMatrix decisionMatrix = decisionMatrixLoader.load(file);
        System.out.println(decisionMatrix);

        arguments.getDecisionCriteria().stream()
                .map(DecisionCriteria::valueOfShortcut)
                .map(decision -> decision.makePrintableDecision(decisionMatrix))
                .forEach(System.out::println);
    }
}
