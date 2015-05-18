package EPAM2015_task2_10;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Text text = new Text(new File("src/EPAM2015_task2_10/resources/srcText"));
        List<SentenceElement> list = new ArrayList<>();
        list.add(new Word("in"));
        list.add(new Word("on"));
        list.add(new Word("to"));
        list.add(new Word("was"));
        list.add(new Word("he"));
        list.add(new Word("the"));

        System.out.println("OCCURRENCE FREQUENCY IN SENTENCES:");
        Map<SentenceElement, Integer[]> sentMap = text.occurFreqPerSentence(list);
        for (SentenceElement el : sentMap.keySet()) {
            System.out.printf("%3s: %s\n", el, Arrays.toString(sentMap.get(el)));
        }

        System.out.println("\nOCCURRENCE FREQUENCY IN TEXT:");
        Map<SentenceElement, Integer> textMap = text.occurFreq(list);
        for (SentenceElement el : textMap.keySet()) {
            System.out.printf("%3s: %2s times\n", el, textMap.get(el));
        }

        System.out.println("\nLIST BEFORE SORT:");
        System.out.println(list);

        System.out.println("\nLIST AFTER SORT:");
        text.sortByOccurFreq(list);
        System.out.println(list);
    }
}
