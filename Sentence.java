package EPAM2015_task2_10;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sentence {

    public static final String SENTENCE_PATTERN = "([\\P{Space}&&[^.!?]]|\\s)+?([.!?]+)";

    private List<SentenceElement> content;

    public Sentence(String input) {
        content = input == null ? new ArrayList<>() : SentenceElement.split(input);
    }

    public List<SentenceElement> getContent() {
        return content;
    }

    /**
     * Method calculates occurrence quantity of passed input SentenceElement-object in current Sentence
     * (i.e. in the private List of SentenceElement-objects that represent Sentence).
     *
     * @param el Input SentenceElement-object, which occurrence quantity to be calculated.
     * @return int value, that represents occurrence quantity of passed input SentenceElement-object in current Sentence.
     */
    public int occurFreq(SentenceElement el) {
        int count = 0;
        for (SentenceElement se : content) {
            if (se.equals(el)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Method calculates occurrence quantity of each SentenceElement of Sentence.
     *
     * @return Map, which uses SentenceElement of current Sentence as a key and Integer value
     * of occurrence quantity in the current Sentence as a value.
     */
    public Map<SentenceElement, Integer> occurFreq() {
        Map<SentenceElement, Integer> toReturn = new HashMap<>();
        int count;
        for (int i = 0; i < content.size(); i++) {
            if (toReturn.containsKey(content.get(i))) {
                continue;
            }
            count = 1;
            for (int j = i + 1; j < content.size(); j++) {
                if (content.get(i).equals(content.get(j))) {
                    count++;
                }
            }
            toReturn.put(content.get(i), count);
        }
        return toReturn;
    }

    @Override
    public String toString() {
        StringBuilder toReturn = new StringBuilder("");
        for (SentenceElement se : content) {
            toReturn.append(se + " ");
        }
        if (toReturn.length() > 0) {
            toReturn.setLength(toReturn.length() - 1);
        }
        return toReturn.toString();
    }
}