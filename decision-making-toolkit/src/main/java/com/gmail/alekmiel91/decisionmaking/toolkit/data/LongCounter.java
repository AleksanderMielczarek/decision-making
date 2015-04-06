package com.gmail.alekmiel91.decisionmaking.toolkit.data;

/**
 * @author Aleksander Mielczarek
 *         Created on 2015-04-06.
 */
public class LongCounter implements Counter<Long> {
    private Long counter = 1l;

    @Override
    public void reset() {
        counter = 1l;
    }

    @Override
    public Long next() {
        return counter++;
    }
}
