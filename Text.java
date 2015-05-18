package EPAM2015_task2_10;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Text {

    public class SentenceIterator implements Iterator<EPAM2015_task2_10.Sentence> {

        Scanner sc;
        String next;
        int expectedModCount;

        SentenceIterator() throws FileNotFoundException {
            this.sc = strSrc != null ? new Scanner(strSrc) : new Scanner(fileSrc);
            this.next = this.sc.findWithinHorizon(Sentence.SENTENCE_PATTERN, 0);
            this.expectedModCount = modCount;
        }

        @Override
        public boolean hasNext() {
            return this.next != null;
        }

        @Override
        public Sentence next() {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            if (this.next == null) {
                throw new NoSuchElementException();
            }
            Sentence toReturn = new Sentence(this.next);
            this.next = this.sc.findWithinHorizon(Sentence.SENTENCE_PATTERN, 0);
            return toReturn;
        }
    }

    private String strSrc;
    private File fileSrc;
    private int modCount;

    public Text(String strSrc) {
        this.strSrc = strSrc;
    }

    public Text(File fileSrc) {
        this.fileSrc = fileSrc;
    }

    /**
     * Method returns Sentence-object by the index in source-text. Indexation begins from 0.
     *
     * @param index int value, that indicates index of sentence in source-text to be extracted.
     */
    public Sentence getSentence(int index) throws FileNotFoundException {
        if (index < 0) {
            throw new IllegalArgumentException();
        }
        int sentCounter = 0;
        SentenceIterator iter = new SentenceIterator();
        while (iter.hasNext()) {
            if (sentCounter == index) {
                return iter.next();
            }
            iter.next();
            sentCounter++;
        }
        return null;
    }

    /**
     * Method calculates occurrence quantity of each SentenceElement of source-text.
     *
     * @return Map, which uses SentenceElement of source-text as a key and Integer value
     * of occurrence quantity in the source-text as a value.
     */
    public Map<SentenceElement, Integer> occurFreq() throws FileNotFoundException {
        SentenceIterator iter = new SentenceIterator();
        Map<SentenceElement, Integer> toReturn = new HashMap<>();
        while (iter.hasNext()) {
            Map<SentenceElement, Integer> curSentMap = iter.next().occurFreq();
            for (SentenceElement key : curSentMap.keySet()) {
                Integer oldFreq = toReturn.get(key);
                Integer newFreq = oldFreq == null ? curSentMap.get(key) : oldFreq + curSentMap.get(key);
                toReturn.put(key, newFreq);
            }
        }
        iter.sc.close();
        return toReturn;
    }

    /**
     * Method calculates occurrence quantity in the source-text of SentenceElement-objects from the input list
     * that passes to the method.
     *
     * @return Map, which uses SentenceElement of input list as a key and Integer value
     * of occurrence quantity in the source-text as a value.
     */
    public Map<SentenceElement, Integer> occurFreq(List<SentenceElement> elements)
            throws FileNotFoundException {
        nullCheck(elements);
        Set<SentenceElement> elemSet = new HashSet<>(elements);
        SentenceIterator iter = new SentenceIterator();
        Map<SentenceElement, Integer> toReturn = new HashMap<>();
        while (iter.hasNext()) {
            Sentence curSent = iter.next();
            for (SentenceElement elem : elemSet) {
                Integer oldFreq = toReturn.get(elem);
                Integer newFreq = oldFreq == null
                        ? curSent.occurFreq(elem) : oldFreq + curSent.occurFreq(elem);
                toReturn.put(elem, newFreq);
            }
        }
        iter.sc.close();
        return toReturn;
    }

    /**
     * Method calculates occurrence quantity of all SentenceElement-objects of the source-text in every sentence of the source-text.
     *
     * @return Map, that uses SentenceElement-object from source-text as a key,
     * and an Integer-array as a value. Each Integer value of array represents quantity of
     * occurrences of correspondent SentenceElement in Sentence, which ordinal number in source-text
     * equals to Integer-value index in array.
     */
    public Map<SentenceElement, Integer[]> occurFreqPerSentence()
            throws FileNotFoundException {
        SentenceIterator iter = new SentenceIterator();
        Map<SentenceElement, Integer[]> toReturn = new HashMap<>();
        int sentCounter = 0;
        while (iter.hasNext()) {
            for (SentenceElement elem : toReturn.keySet()) {
                Integer[] curElemFreqArray = toReturn.get(elem);
                Integer[] newElemFreqArray = Arrays.copyOf(curElemFreqArray, curElemFreqArray.length + 1);
                newElemFreqArray[newElemFreqArray.length - 1] = 0;
                toReturn.replace(elem, newElemFreqArray);
            }
            Map<SentenceElement, Integer> curSentMap = iter.next().occurFreq();
            for (SentenceElement elem : curSentMap.keySet()) {
                Integer[] curElemFreqArray = toReturn.get(elem);
                if (curElemFreqArray != null) {
                    Integer newFreq = curSentMap.get(elem);
                    curElemFreqArray[curElemFreqArray.length - 1] = newFreq;
                } else {
                    Integer[] array = new Integer[sentCounter + 1];
                    Arrays.fill(array, 0);
                    array[sentCounter] = curSentMap.get(elem);
                    toReturn.put(elem, array);
                }
            }
            sentCounter++;
        }
        return toReturn;
    }

    /**
     * Method calculates occurrence quantity of every SentenceElement-object from the input list in every sentence of the source-text.
     *
     * @param elements List of SentenceElement-objects, which frequency in every sentence of source-text to be defined.
     * @return Map, that uses SentenceElement-object from input list of elements as a key,
     * and an Integer-array as a value. Each Integer value of array represents quantity of
     * occurrences of correspondent SentenceElement in Sentence, which ordinal number in source-text
     * equals to Integer-value index in array.
     */
    public Map<SentenceElement, Integer[]> occurFreqPerSentence(List<SentenceElement> elements)
            throws FileNotFoundException {
        nullCheck(elements);
        Set<SentenceElement> elemSet = new HashSet<>(elements);
        SentenceIterator iter = new SentenceIterator();
        Map<SentenceElement, Integer[]> toReturn = new HashMap<>();
        while (iter.hasNext()) {
            Sentence curSent = iter.next();
            for (SentenceElement elem : elemSet) {
                if (toReturn.containsKey(elem)) {
                    Integer[] oldFreqArray = toReturn.get(elem);
                    Integer[] newFreqArray = Arrays.copyOf(oldFreqArray, oldFreqArray.length + 1);
                    newFreqArray[newFreqArray.length - 1] = curSent.occurFreq(elem);
                    toReturn.replace(elem, newFreqArray);
                } else {
                    toReturn.put(elem, new Integer[]{curSent.occurFreq(elem)});
                }
            }
        }
        return toReturn;
    }

    /**
     * Method sets input String-object as a source.
     *
     * @param strSrc String-object, that is set as a source.
     */
    public void setSource(String strSrc) {
        this.strSrc = strSrc;
        this.fileSrc = null;
        modCount++;
    }

    /**
     * Method sets input File-object as a source.
     *
     * @param fileSrc File-object, that is set as a source.
     */
    public void setSource(File fileSrc) {
        this.fileSrc = fileSrc;
        this.strSrc = null;
        modCount++;
    }

    /**
     * Method sorts input List of SentenceElement-objects according to the occurrence quantity
     * of these objects in the source-text.
     *
     * @param elements List of SentenceElement-objects to be sorted.
     */
    public void sortByOccurFreq(List<SentenceElement> elements) throws FileNotFoundException {
        Map<SentenceElement, Integer> freqMap = occurFreq(elements);
        sortByOccurFreq(elements, freqMap);
    }

    /**
     * Method sorts input List of SentenceElement-objects according to the occurrence quantity
     * of these objects stored in Map, that passes to the method as a second parameter.
     *
     * @param elements List of SentenceElement-objects to be sorted.
     * @param freqMap  Map, which uses SentenceElement-objects from input list (first parameter) as a key and Integer value
     *                 of occurrence quantity as a value. Second parameter is a result of method: occurFreqPerSentence(List<SentenceElement> elements).
     */
    public void sortByOccurFreq(List<SentenceElement> elements, Map<SentenceElement, Integer> freqMap) {
        nullCheck(elements);
        Collections.sort(elements, (o1, o2) -> freqMap.get(o2) - freqMap.get(o1));
    }

    private void nullCheck(Object toCheck) {
        if (toCheck == null) {
            throw new IllegalArgumentException();
        }
    }
}