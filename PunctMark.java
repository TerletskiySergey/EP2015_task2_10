package EPAM2015_task2_10;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PunctMark implements SentenceElement {

    public static final String PUNCTUATION_MARK_PATTERN = "[\\p{Punct}&&[^']]";

    private String content;

    public PunctMark(String input) {
        inputFilter(input);
    }

    //    Method checks the input String-object for compliance to PUNCTUATION_MARK_PATTERN.
    //    If matches, the value of private class-filed "content" assigns to the input-string,
    //    otherwise "content"-value is to empty-string assigned.
    private void inputFilter(String input) {
        if (input == null) {
            content = "";
            return;
        }
        Matcher matcher = Pattern.compile(PUNCTUATION_MARK_PATTERN).matcher(input);
        if (matcher.matches()) {
            content = input;
        } else {
            content = "";
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }
        return this.content.equals(((PunctMark) other).content);
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
