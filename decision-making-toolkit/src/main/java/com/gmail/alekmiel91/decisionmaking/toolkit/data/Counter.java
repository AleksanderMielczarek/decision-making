package com.gmail.alekmiel91.decisionmaking.toolkit.data;

import java.util.Iterator;

/**
 * @author Aleksander Mielczarek
 *         Created on 2015-04-06.
 */
public interface Counter<T> extends Iterator<T> {
    @Override
    default boolean hasNext() {
        return true;
    }

    void reset();
}
