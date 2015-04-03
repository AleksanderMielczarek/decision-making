package com.gmail.alekmiel91.decisionmaking.console;

import lombok.Getter;

import java.util.ResourceBundle;

/**
 * @author Aleksander Mielczarek
 *         Created on 2015-04-03.
 */
@Getter
public enum Context {
    INSTANCE,;

    private final ResourceBundle resources = ResourceBundle.getBundle("bundle");
}
