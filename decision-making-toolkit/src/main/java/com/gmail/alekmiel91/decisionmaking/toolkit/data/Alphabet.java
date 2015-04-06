package com.gmail.alekmiel91.decisionmaking.toolkit.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Aleksander Mielczarek
 *         Created on 2015-04-05.
 */
public class Alphabet implements Counter<String> {

    private static final char FIRST_LETTER = 'A';
    private static final char LAST_LETTER = 'Z';

    private static final List<String> ALPHABET;

    static {
        ALPHABET = new ArrayList<>();
        for (char letter = FIRST_LETTER; letter <= LAST_LETTER; letter++) {
            ALPHABET.add(String.valueOf(letter));
        }
    }

    private Iterator<String> alphabetIterator = ALPHABET.iterator();
    private long counter = 0;

    @Override
    public String next() {
        if (!alphabetIterator.hasNext()) {
            alphabetIterator = ALPHABET.iterator();
            counter++;
        }

        if (counter == 0) {
            return alphabetIterator.next();
        }

        return alphabetIterator.next() + counter;
    }

    @Override
    public void reset() {
        alphabetIterator = ALPHABET.iterator();
        counter = 0;
    }
}
