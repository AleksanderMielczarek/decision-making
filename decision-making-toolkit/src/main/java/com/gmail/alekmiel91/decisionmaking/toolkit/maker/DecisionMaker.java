package com.gmail.alekmiel91.decisionmaking.toolkit.maker;

import com.gmail.alekmiel91.decisionmaking.toolkit.data.DecisionMatrix;

import java.util.Set;

/**
 * @author Aleksander Mielczarek
 *         Created on 2015-04-01.
 */
public interface DecisionMaker {
    Set<String> makeDecision(DecisionMatrix decisionMatrix);
}
