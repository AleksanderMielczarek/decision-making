package com.gmail.alekmiel91.decisionmaking.toolkit.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.gmail.alekmiel91.decisionmaking.toolkit.DecisionMakingException;
import com.gmail.alekmiel91.decisionmaking.toolkit.data.RawDecisionMatrix;

import java.io.File;
import java.io.IOException;

/**
 * @author Aleksander Mielczarek
 *         Created on 2015-04-01.
 */
public class YAMLDecisionMatrixLoader implements DecisionMatrixLoader {

    private static final ObjectMapper MAPPER = new ObjectMapper(new YAMLFactory());

    @Override
    public RawDecisionMatrix loadRawDecisionMatrix(File file) throws DecisionMakingException {
        try {
            return MAPPER.readValue(file, RawDecisionMatrix.class);
        } catch (IOException e) {
            throw new DecisionMakingException(e);
        }
    }

    @Override
    public void saveRawDecisionMatrix(RawDecisionMatrix rawDecisionMatrix, File file) throws DecisionMakingException {
        try {
            MAPPER.writeValue(file, rawDecisionMatrix);
        } catch (IOException e) {
            throw new DecisionMakingException(e);
        }
    }

}
