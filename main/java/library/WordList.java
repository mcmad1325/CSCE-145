package library;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class for the list of words
 * @author Cody Miller
 */
public class WordList {
    private ArrayList<Word> words;

    /**
     * Constructor for the WordList.java
     * @param words
     */
    public WordList(ArrayList<Word> words) {
        this.words = words;
    }
 
    /**
     * Adds a word to the wordlist
     * @param word word to add
     */
    public void addWord(Word word) {
        this.words.add(word);
    }
    
    /**
     * Removes a word from the wordlist
     * @param word word to remove
     */
    public void removeWord(Word word) {
        this.words.remove(word);
    }

    /**
     * Gets a random word from the wordlist
     * @return the random word
     */
    public Word getRandomWord() {
        Random rand = new Random();
        return words.get(rand.nextInt(getWordCount()));
    }

    /**
     * Accessor method for the wordlist
     * @return the word listt
     */
    public ArrayList<Word> getWords() {
        return words;
    }

    /**
     * Returns the number of words in the list
     * @return the number of words in the list
     */
    public int getWordCount() {
        return words.size();
    }

    /**
     * Get's the word list
     * @return this
     */
    public WordList getWordList() {
        return this;
    }
}
