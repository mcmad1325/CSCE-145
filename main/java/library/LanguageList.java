package library;
import java.util.ArrayList;

/**
 * Language List class
 * Singleton
 * Stores the languages offered, constant among all users
 * @author Lea Frost
 */

public class LanguageList {

    private ArrayList<Language> languages;
    private static LanguageList languageList;

    /**
     * Loads language list from data loader (language.json)
     */
    private LanguageList() {
        languages = DataLoader.getLanguages();
    }

    /**
     * Creates instance of language list
     * 
     * @return Language list
     */
    public static LanguageList getInstance() {
        if (languageList == null)
            languageList = new LanguageList();
        return languageList;
    }

    /**
     * Returns a list of all languages
     * @return Arraylist of all languages
     */
    public ArrayList<Language> getLanguages() {
        return languages;
    }

    /**
     * Returns the language given the language name
     * 
     * @param languageName The language name
     * @return The language if it exists in the list
     */
    public Language getLanguage(String languageName) {
        for (Language language : languages) {
            if (language.getLanguageName().equals(languageName)) {
                return language;
            }
        }
        return null;
    }

    /**
     * Returns the language given the language enum
     * 
     * @param languageEnum The language enum
     * @return The language if it exists in the list
     */
    public Language getLanguageByEnum(LanguagesEnum languageEnum) {
        for (Language language : languages) 
            if (language.getLanguageEnum().equals(languageEnum)) 
                return language;
        return null;
    }

    /**
     * Checks if a language exists in the list
     * 
     * @param languageName The name of the language
     * @return True if it's in the list, false if not
     */
    public boolean contains(String languageName){
        for (Language language : languages)
            if (language.getLanguageName().equals(languageName))
                return true;
        return false;
    }
}
