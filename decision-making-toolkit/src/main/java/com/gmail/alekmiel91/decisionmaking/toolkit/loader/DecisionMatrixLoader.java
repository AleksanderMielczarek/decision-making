package com.gmail.alekmiel91.decisionmaking.toolkit.loader;

import com.gmail.alekmiel91.decisionmaking.toolkit.DecisionMakingException;
import com.gmail.alekmiel91.decisionmaking.toolkit.data.DecisionMatrix;
import com.gmail.alekmiel91.decisionmaking.toolkit.data.DecisionMatrixBuilder;
import com.gmail.alekmiel91.decisionmaking.toolkit.data.RawDecisionMatrix;

import java.io.File;

/**
 * @author Aleksander Mielczarek
 *         Created on 2015-04-01.
 */
public interface DecisionMatrixLoader {
    public default DecisionMatrix load(File file) throws DecisionMakingException {
        RawDecisionMatrix rawDecisionMatrix = loadRawDecisionMatrix(file);

        DecisionMatrixBuilder builder = DecisionMatrix.builder(rawDecisionMatrix.getAlternatives(),
                rawDecisionMatrix.getScenes(),
                rawDecisionMatrix.getOutputs());

        if (rawDecisionMatrix.getFactors() != null) {
            builder.withFactors(rawDecisionMatrix.getFactors());
        }

        return builder.build();
    }

    public RawDecisionMatrix loadRawDecisionMatrix(File file) throws DecisionMakingException;

    public default void save(DecisionMatrix decisionMatrix, File file) throws DecisionMakingException {
        saveRawDecisionMatrix(decisionMatrix.getRawDecisionMatrix(), file);
    }

    public void saveRawDecisionMatrix(RawDecisionMatrix rawDecisionMatrix, File file) throws DecisionMakingException;
}
