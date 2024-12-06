package library;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * The DataLoader class loads the data from the JSON files and converts them
 * into corresponding objects (User, Language, etc.).
 */
public class DataLoader extends DataConstants {

    /**
     * Loads the users from the JSON file and returns an ArrayList of User 
     * objects.
     *
     * @return An ArrayList of User objects loaded from the JSON file.
     */
    public static ArrayList<User> getUserList() {
        ArrayList<User> users = new ArrayList<User>();
        BufferedReader reader = getReaderFromFile(USERS_FILE_NAME, USERS_FILE_NAME_JUNIT);

        try {
            JSONArray usersJSON = (JSONArray) new JSONParser().parse(reader);

            for (Object user : usersJSON) {
                ArrayList<Language> languages = new ArrayList<Language>();
                ArrayList<Badge> badges = new ArrayList<Badge>();
                ArrayList<Word> problemWords = new ArrayList<Word>();

                JSONObject userJSON = (JSONObject) user;
                String firstName = (String) userJSON.get(USERS_FIRST_NAME);
                String lastName = (String) userJSON.get(USERS_LAST_NAME);
                String email = (String) userJSON.get(USERS_EMAIL);
                String phoneNumber = (String) userJSON.get(USERS_PHONE_NUMBER);
                String username = (String) userJSON.get(USERS_USERNAME);
                String password = (String) userJSON.get(USERS_PASSWORD);
                UUID UserID = UUID.fromString((String) userJSON.get(USERS_UUID));
                UUID currentLessonID = UUID.fromString((String) userJSON.get(USERS_CURRENT_LESSON_ID));
                UUID currentUnitID = UUID.fromString((String) userJSON.get(USERS_CURRENT_UNIT_ID));

                // Create problem word list
                JSONArray wordsJSON = (JSONArray) userJSON.get(USERS_PROBLEM_WORDS);
                for (Object word : wordsJSON) {
                    JSONObject wordJSON = (JSONObject) word;
                    UUID wordID = UUID.fromString((String) wordJSON.get(USERS_WORD_ID));
                    String text = (String) wordJSON.get(USERS_WORD_TEXT);
                    String translation = (String) wordJSON.get(USERS_WORD_TRANSLATION);
                    String partOfSpeech = (String) wordJSON.get(USERS_WORD_PART_OF_SPEECH);
                    String exampleSentence = (String) wordJSON.get(USERS_WORD_EXAMPLE_SENTENCE);
                    // Add language to list by accessing language from languagelist via UUID
                    problemWords.add((new Word(wordID, text, translation, partOfSpeech, exampleSentence)));
                }

                // Create languages
                JSONArray languagesJSON = (JSONArray) userJSON.get(USERS_LANGUAGES);
                for (Object language : languagesJSON) {
                    JSONObject languageJSON = (JSONObject) language;
                    // Add language to list by accessing language from languagelist via UUID
                    languages.add(LanguageList.getInstance().getLanguageByEnum(LanguagesEnum.valueOf((String) languageJSON.get(LANGUAGE_ENUM))));
                }

                // Create badges list
                JSONArray badgesJSON = (JSONArray) userJSON.get(USERS_BADGES);
                for (Object badge : badgesJSON) {
                    JSONObject badgeJSON = (JSONObject) badge;
                    // Add badge by accessing badge via UUID
                    badges.add(Badge.getBadgeByUUID(UUID.fromString((String) badgeJSON.get(USERS_BADGE_ID))));
                    //System.out.println(UUID.fromString((String)badgeJSON.get(USERS_BADGE_ID)));
                    //System.out.println(Badge.getBadgeByUUID(UUID.fromString((String)badgeJSON.get(USERS_BADGE_ID))));
                }

                // Add user to user arraylist
                users.add(new User(UserID, firstName, lastName, email, phoneNumber, username, password, languages, badges, currentUnitID, currentLessonID, new WordList(problemWords)));
            }
            reader.close();
            return users;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Loads the languages from the JSON file and returns an ArrayList of
     * Language objects.
     *
     * @return An ArrayList of Language objects loaded from the JSON file.
     */
    public static ArrayList<Language> getLanguages() {

        ArrayList<Language> languages = new ArrayList<Language>();
        BufferedReader reader = getReaderFromFile(LANGUAGE_FILE_NAME, LANGUAGE_FILE_NAME_JUNIT);

        try {

            // JSON File
            JSONArray languagesJSON = (JSONArray) new JSONParser().parse(reader);

            // Language JSON
            for (Object language : languagesJSON) {

                // Initalize unit list
                ArrayList<Unit> units = new ArrayList<Unit>();

                // Get lanaugage information
                JSONObject languageJSON = (JSONObject) language;
                String languageName = (String) languageJSON.get(LANGUAGE_NAME);
                LanguagesEnum languageEnum = LanguagesEnum.valueOf((String) languageJSON.get(LANGUAGE_ENUM));

                // Units JSON
                JSONArray unitsJSON = (JSONArray) languageJSON.get(LANGUAGE_UNIT_LIST);
                for (Object unit : unitsJSON) {
                    // Initialize lesson list 
                    ArrayList<Lesson> lessons = new ArrayList<Lesson>();

                    // Get unit information
                    JSONObject unitJSON = (JSONObject) unit;
                    UUID unitID = UUID.fromString((String) unitJSON.get(UNIT_ID));
                    String unitName = (String) unitJSON.get(UNIT_NAME);
                    int unitNumber = Integer.parseInt((String) unitJSON.get(UNIT_NUMBER));

                    // Lesson JSON
                    JSONArray lessonsJSON = (JSONArray) unitJSON.get(UNIT_LESSON_LIST);
                    for (Object lesson : lessonsJSON) {
                        ArrayList<Word> words = new ArrayList<Word>();

                        // Get lesson information
                        JSONObject lessonJSON = (JSONObject) lesson;
                        UUID lessonID = UUID.fromString((String) lessonJSON.get(LESSON_ID));
                        String lessonName = (String) lessonJSON.get(LESSON_NAME);
                        int lessonNumber = Integer.parseInt((String) lessonJSON.get(LESSON_NUMBER));

                        // Word JSON
                        JSONArray wordsJSON = (JSONArray) lessonJSON.get(LESSON_WORD_LIST);
                        for (Object word : wordsJSON) {

                            // Get word information
                            JSONObject wordJSON = (JSONObject) word;
                            UUID wordID = UUID.fromString((String) wordJSON.get(WORD_ID));
                            String text = (String) wordJSON.get(WORD_TEXT);
                            String translation = (String) wordJSON.get(WORD_TRANSLATION);
                            String partOfSpeech = (String) wordJSON.get(WORD_PART_OF_SPEECH);
                            String exampleSentence = (String) wordJSON.get(WORD_EXAMPLE_SENTENCE);
                            words.add(new Word(wordID, text, translation, partOfSpeech, exampleSentence));
                        }

                        // Add lesson to lesson list
                        lessons.add(new Lesson(lessonID, lessonName, lessonNumber, new WordList(words)));
                    }

                    // Add unit to unit list
                    units.add(new Unit(unitID, unitName, unitNumber, new LessonList(lessons), new UnitList(units)));
                }

                // Add language to language list
                languages.add(new Language(languageEnum, languageName, new UnitList(units)));
            }

            return languages;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Tester
    public static void main(String[] args) {

        // Display language info
        ArrayList<Language> languageList = getLanguages();

        for (Language language : languageList) {
            System.out.println("Language Name: " + language.getLanguageName());
            System.out.println("Language ID: " + language.getLanguageEnum());
            System.out.println();

            ArrayList<Unit> units = language.getUnits();
            for (Unit unit : units) {
                System.out.println("Unit Name: " + unit.getUnitName());
                System.out.println("Unit Number: " + unit.getUnitNumber());
                System.out.println("Unit ID: " + unit.getId());
                System.out.println();

                ArrayList<Lesson> lessons = unit.getLessons();
                for (Lesson lesson : lessons) {
                    System.out.println("Lesson Name: " + lesson.getLessonName());
                    System.out.println("Lesson Number: " + lesson.getLessonNumber());
                    System.out.println("Lesson ID: " + lesson.getId());
                    System.out.println();

                    ArrayList<Word> words = lesson.getWords();
                    for (Word word : words) {
                        System.out.println("Word ID: " + word.getId());
                        System.out.println("Word text: " + word.getForeignWord());
                        System.out.println("Word translation: " + word.getTranslatedWord());
                        System.out.println("Word part: " + word.getPartofSpeech());
                        System.out.println("Word sentence: " + word.getExampleSentence());
                        System.out.println();
                    }
                }
            }
        }

        // Display user info
        ArrayList<User> userList = getUserList();

        for (User user : userList) {
            System.out.println("First Name: " + user.getFirstName());
            System.out.println("Last Name: " + user.getLastName());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Username: " + user.getUsername());
            System.out.println("Phone Number: " + user.getPhoneNumber());
            System.out.println("Password: " + user.getPassword());
            System.out.println("User ID: " + user.getId());
            System.out.println("Unit ID: " + user.getUnitId());
            System.out.println("Lesson ID: " + user.getLessonId());
            System.out.print("Languages: ");
            for (Language language : user.getLanguages()) {
                System.out.print(language.getLanguageName() + " ");
            }
            System.out.println();
            System.out.print("Badges: ");
            for (Badge badge : user.getBadges()) {
                System.out.print(badge + " ");
            }
            System.out.println("\n");
        }
    }

    private static BufferedReader getReaderFromFile(String fileName, String jsonFileName){
		try {
			if(isJUnitTest()){
				InputStream inputStream = DataLoader.class.getResourceAsStream(jsonFileName);
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				return new BufferedReader(inputStreamReader);
			} else {
				FileReader reader = new FileReader(fileName);
				return new BufferedReader(reader);
			}
		} catch(Exception e){
			System.out.println("Can't load");
			return null;
		}
			
	}

}
