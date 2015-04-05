package com.gmail.alekmiel91.decisionmaking.console;

import com.gmail.alekmiel91.decisionmaking.toolkit.data.DecisionMatrix;
import com.gmail.alekmiel91.decisionmaking.toolkit.maker.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Aleksander Mielczarek
 *         Created on 2015-04-03.
 */
@RequiredArgsConstructor
@Getter
public enum DecisionCriteria implements DecisionMaker {
    MAXI_MAX(new MaxiMaxDecisionMaker(), "decision.maxi.max", "mma"),
    MAXI_MIN(new MaxiMinDecisionMaker(), "decision.maxi.min", "mmi"),
    HURWICZ(new HurwiczDecisionMaker(), "decision.hurwicz", "hur"),
    SAVAGE(new SavageDecisionMaker(), "decision.savage", "sav"),
    LAPLACE(new LaplaceDecisionMaker(), "decision.laplace", "lap"),
    MAXIMUM_EXPECTED_VALUE(new MaximumExpectedValueDecisionMaker(), "decision.maximum.expected.value", "mev"),
    MAXIMUM_EXPECTED_REGRET(new MinimumExpectedRegretDecisionMaker(), "decision.maximum.expected.regret", "mer"),
    BAYESIAN(new BayesianDecisionMaker(), "decision.bayesian", "bay");

    private final DecisionMaker decisionMaker;
    private final String key;
    private final String shortcut;

    @Override
    public Set<String> makeDecision(DecisionMatrix decisionMatrix) {
        return decisionMaker.makeDecision(decisionMatrix);
    }

    public String makePrintableDecision(DecisionMatrix decisionMatrix) {
        return Context.INSTANCE.getResources().getString(key) + ": " + makeDecision(decisionMatrix).stream()
                .collect(Collectors.joining(", "));
    }

    private static final Map<String, DecisionCriteria> SHORTCUT_DECISION_CRITERIA_MAP;

    static {
        SHORTCUT_DECISION_CRITERIA_MAP = Arrays.stream(values()).collect(Collectors.toMap(DecisionCriteria::getShortcut, decision -> decision));
    }

    public static DecisionCriteria valueOfShortcut(String shortcut) {
        return SHORTCUT_DECISION_CRITERIA_MAP.get(shortcut);
    }

    public static boolean containsShortcut(String shortcut) {
        return SHORTCUT_DECISION_CRITERIA_MAP.containsKey(shortcut);
    }
}
