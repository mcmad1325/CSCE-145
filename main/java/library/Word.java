package library;
import java.util.UUID;

/**
 * @author Madeleine McBride
 * Represents a word in our language learning application.
 */
public class Word {
    
    private UUID id;
    private String text;
    private String translation;
    private String partOfSpeech;
    private String exampleSentence;
    /**
     * Constructs a Word with the specified text, translation, part of speech, and example sentence.
     *
     * @param id              The UUID of the language
     * @param text            the text of the word in the foreign language
     * @param translation     the translation of the word in the user's native language
     * @param partOfSpeech    the part of speech of the word (e.g., noun, verb, adjective)
     * @param exampleSentence an example sentence using the word
     */
    public Word(UUID id, String text, String translation, String partOfSpeech, String exampleSentence) {
        this.id = id;
        this.text = text;
        this.translation = translation;
        this.partOfSpeech = partOfSpeech;
        this.exampleSentence = exampleSentence;
    }

    /**
     * Constructs a Word with the specified text and translation only.
     *
     * @param text            the text of the word in the foreign language
     * @param translation     the translation of the word in the user's native language
     */
    public Word(String text, String translation) {
        this.text = text;
        this.translation = translation;
        this.id = null;
        this.partOfSpeech = null;
        this.exampleSentence = null;
    }

    /**
     * Returns the UUID of the word.
     *
     * @return the UUID of the word
     */
    public UUID getId() {
        return id;
    }

    /**
     * Returns the text of the word in the foreign language.
     *
     * @return the text of the word in the foreign language
     */
    public String getForeignWord() {
        return text;
    }

    /**
     * Returns the translation of the word in the user's native language.
     *
     * @return the translation of the word in the user's native language
     */
    public String getTranslatedWord() {
        return translation;
    }

    /**
     * Returns the part of the speech of the word.
     * 
     * @return The part of speech
     */
    public String getPartofSpeech() {
        return partOfSpeech;
    }

    /**
     * Returns the part of the speech of the word.
     * 
     * @return The part of speech
     */
    public String getExampleSentence() {
        if(exampleSentence == null) {
            return null;
        }
        /*
        else if(!exampleSentence.contains(text)) {
            return "Example sentence must contain foreign word.";
        }
            */
        else{
            return exampleSentence;
        }
    }

    /**
     * Returns a string representation of the word, including its text, translation, part of speech, example sentence, and language.
     *
     * @return a string representation of the word
     */
    @Override
    public String toString() {
        return text + " - " + translation + " - " + partOfSpeech + " - " + exampleSentence;
    }
}
