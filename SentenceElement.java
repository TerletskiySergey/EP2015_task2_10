package EPAM2015_task2_10;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface SentenceElement {
    /**
     * Method splits input String-object into the list of SentenceElement-objects
     * (i.e. list of Word- and PunkMark- objects), according to patterns, stored
     * in the appropriate class (Word.WORD_PATTERN and PunctMark.PUNCTUATION_MARK_PATTERN).
     * @param input Input String-object to be split.
     * @return List of SentenceElement-objects, result of split-procedure.
     */
    static List<SentenceElement> split(String input) {
        List<SentenceElement> elements = new ArrayList<>();
        Matcher pmMatcher = Pattern.compile(PunctMark.PUNCTUATION_MARK_PATTERN).matcher(input);
        Matcher wMatcher = Pattern.compile(Word.WORD_PATTERN).matcher(input);
        int startIndex = 0;
        boolean b1, b2;
        while (b1 = pmMatcher.find(startIndex) | (b2 = wMatcher.find(startIndex))) {
            elements.add(b1 ^ b2
                            ?
                            b1 ? new PunctMark(pmMatcher.group()) : new Word(wMatcher.group())
                            :
                            pmMatcher.start() < wMatcher.start() ? new PunctMark(pmMatcher.group()) : new Word(wMatcher.group())
            );
            startIndex = elements.get(elements.size() - 1).getClass() == PunctMark.class
                    ? pmMatcher.end() : wMatcher.end();
        }
        return elements;
    }
}