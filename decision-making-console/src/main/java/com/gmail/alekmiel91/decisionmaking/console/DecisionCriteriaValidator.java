package com.gmail.alekmiel91.decisionmaking.console;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

import java.text.MessageFormat;

/**
 * @author Aleksander Mielczarek
 *         Created on 2015-04-03.
 */
public class DecisionCriteriaValidator implements IParameterValidator {
    @Override
    public void validate(String name, String value) throws ParameterException {
        if (!DecisionCriteria.containsShortcut(value)) {
            throw new ParameterException(MessageFormat.format(Context.INSTANCE.getResources().getString("error.wrong.decision.criteria.shortcut"), value));
        }
    }
}
