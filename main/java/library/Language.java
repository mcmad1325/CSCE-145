package library;
import java.util.ArrayList;

/**
 * Represents a language
 * 
 * @author Lea Frost
 */
public class Language {

    private String languageName;
    private UnitList unitList;
    private LanguagesEnum language;

    /**
     * Constructs the language
     *
     * @param id The UUID of the language
     * @param languageName The name of the language
     * @param unitList The unit list of the language
     */
    public Language(LanguagesEnum language, String languageName, UnitList unitList) {
        this.languageName = languageName;
        this.unitList = unitList;
        this.language = language;
    }

    /**
     * Returns the name of the language
     *
     * @return The name of the language
     */
    public String getLanguageName() {
        return languageName;
    }

    /**
     * Returns the language enum
     * 
     * @return The language enum
     */
    public LanguagesEnum getLanguageEnum() {
        return language;
    }

    /**
     * Returns the unit list of the language
     *
     * @return The unit list of the language
     */
    public UnitList getUnitList() {
        return this.unitList;
    }

    /**
     * Returns an arraylist of the units of the language
     *
     * @return The arraylist of units
     */
    public ArrayList<Unit> getUnits() {
        return unitList.getUnits();
    }

}
