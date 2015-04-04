package com.gmail.alekmiel91.decisionmaking.toolkit.data;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Aleksander Mielczarek
 *         Created on 2015-04-04.
 */
public interface Defaultable {

    Logger LOGGER = LoggerFactory.getLogger(Defaultable.class);

    default void applyDefault() {
        applyDefaultAndLog().forEach(LOGGER::warn);
    }

    List<String> applyDefaultAndLog();
}
