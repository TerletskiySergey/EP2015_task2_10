package EPAM2015_task2_10;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Word implements SentenceElement {

    //    public static final String WORD_PATTERN = "[\\P{Cntrl}&&\\P{Punct}&&\\P{Space}]+";
    public static final String WORD_PATTERN = "([\\P{Cntrl}&&\\P{Punct}&&\\P{Space}]|')+";

    private String content;

    public Word(String input) {
        inputFilter(input);
    }

    //    Method selects from the input String-object substrings, that match WORD_PATTERN,
    //    concatenate them and assigns the value of private class-filed "content" to the concatenation result.
    //    If no match found, "content"-value is to empty-string assigned.
    private void inputFilter(String input) {
        if (input == null) {
            content = "";
            return;
        }
        StringBuilder sb = new StringBuilder();
        Matcher matcher = Pattern.compile(WORD_PATTERN).matcher(input);
        while (matcher.find()) {
            sb.append(matcher.group());
        }
        content = sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }
        return this.content.equals(((Word) other).content);
    }

    @Override
    public int hashCode() {
        return this.content.hashCode();
    }

    @Override
    public String toString() {
        return content;
    }
}