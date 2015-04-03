package com.gmail.alekmiel91.decisionmaking.toolkit;

/**
 * @author Aleksander Mielczarek
 *         Created on 2015-04-01.
 */
public class DecisionMakingException extends Exception {
    public DecisionMakingException() {
    }

    public DecisionMakingException(String message) {
        super(message);
    }

    public DecisionMakingException(String message, Throwable cause) {
        super(message, cause);
    }

    public DecisionMakingException(Throwable cause) {
        super(cause);
    }
}
