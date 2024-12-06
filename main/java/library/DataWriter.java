package library;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * The DataWriter class is responsible for writing the data to JSON files. Its
 * methods are used to save a list of users to a JSON file, save a list of
 * languages, units and lessons to a JSON file and save the users progress. It
 * uses the org.json.simple library to create the JSON objects used for the
 * files.
 *
 * @author Sofia Bacha
 */
public class DataWriter extends DataConstants {

    /**
     * Saves a list of users to a JSON file.
     */
    public static void saveUsers() {
        UserList userList = UserList.getInstance();
        ArrayList<User> users = userList.getUsers();
        JSONArray jsonUsers = new JSONArray();

        // Creating json objects
        for (User user : users) {
            jsonUsers.add(getUserJSON(user));
        }

        // Write json file
        try  {
            String path = getFileWritingPath(USERS_FILE_NAME, USERS_FILE_NAME_JUNIT);
			FileWriter writer = new FileWriter(path);
			
			writer.write(jsonUsers.toJSONString());
			writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Uses a given User object to create a JSONObject.
     *
     * @param user the User object
     * @return the created JSONObject
     */
    public static JSONObject getUserJSON(User user) {
        JSONObject userDetails = new JSONObject();
        userDetails.put(USERS_FIRST_NAME, user.getFirstName());
        userDetails.put(USERS_LAST_NAME, user.getLastName());
        userDetails.put(USERS_EMAIL, user.getEmail());
        userDetails.put(USERS_PHONE_NUMBER, user.getPhoneNumber());
        userDetails.put(USERS_USERNAME, user.getUsername());
        userDetails.put(USERS_PASSWORD, user.getPassword());
        userDetails.put(USERS_UUID, user.getId().toString());
        userDetails.put(USERS_CURRENT_UNIT_ID, user.getUnitId().toString());
        userDetails.put(USERS_CURRENT_LESSON_ID, user.getLessonId().toString());

        JSONArray languagesArray = new JSONArray();
        JSONObject languageObject = new JSONObject();
        languageObject.put("languageEnum", user.getLanguage().getLanguageEnum().name());
        languagesArray.add(languageObject);
        userDetails.put("languages", languagesArray);

        JSONArray badgesArray = new JSONArray();
        for (Badge badge : user.getBadges()) {
            JSONObject badgeObject = new JSONObject();
            badgeObject.put("badgeID", badge.getID().toString());
            badgesArray.add(badgeObject);
        }
        userDetails.put("badges", badgesArray);

        JSONArray problemWordsArray = new JSONArray();
        for (Word problemWord : user.getProblemWordList().getWords()) {
            JSONObject wordObject = new JSONObject();
            wordObject.put("id", problemWord.getId().toString());
            wordObject.put("text", problemWord.getForeignWord());
            wordObject.put("translation", problemWord.getTranslatedWord());
            wordObject.put("partOfSpeech", problemWord.getPartofSpeech());
            wordObject.put("exampleSentence", problemWord.getExampleSentence());
            problemWordsArray.add(wordObject);
        }
        userDetails.put("problemWords", problemWordsArray);

        return userDetails;
    }

    /**
     * Saves the list of languages to a JSON file.
     */
    public static void saveLanguage() {
        LanguageList languageList = LanguageList.getInstance();
        ArrayList<Language> languages = languageList.getLanguages();
        JSONArray jsonLangauges = new JSONArray();

        for (Language language : languages) {
            jsonLangauges.add(getLanguageJSON(language));
        }

        try (FileWriter file = new FileWriter(LANGUAGE_FILE_NAME)) {
            file.write(jsonLangauges.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Uses given Language object to create a JSONObject.
     *
     * @param language the Language object
     * @return the created JSONObject
     */
    public static JSONObject getLanguageJSON(Language language) {
        JSONObject languageDetails = new JSONObject();
        languageDetails.put(LANGUAGE_NAME, language.getLanguageName());
        languageDetails.put(LANGUAGE_ENUM, language.getLanguageEnum());

        JSONArray unitArray = new JSONArray();
        ArrayList<Unit> units = language.getUnitList().getUnits();
        for (Unit unit : units) {
            JSONObject unitJSON = new JSONObject();
            unitJSON.put(USERS_CURRENT_UNIT_ID, unit.getUnitNumber());
            unitJSON.put(UNIT_NAME, unit.toString());

            JSONArray lessonArray = new JSONArray();
            ArrayList<Lesson> lessons = unit.getLessons();
            for (Lesson lesson : lessons) {
                JSONObject lessonJSON = new JSONObject();
                lessonJSON.put(USERS_CURRENT_LESSON_ID, lesson.getId().toString());
                lessonJSON.put(LESSON_NUMBER, lesson.getLessonNumber());
                lessonArray.add(lessonJSON);
            }
            unitJSON.put(UNIT_LESSON_LIST, lessonArray);
            unitArray.add(unitJSON);
        }
        languageDetails.put(LANGUAGE_UNIT_LIST, unitArray);
        return languageDetails;
    }

    /**
     * Saves the current progress for a specific user to a JSON file.
     *
     * @param user The user whose progress needs to be saved.
     */
    public static void saveUserProgress(User user) {
        JSONObject userDetails = new JSONObject();
        userDetails.put(USERS_UUID, user.getId().toString());
        userDetails.put(USERS_CURRENT_UNIT_ID, user.getUnitId().toString());
        userDetails.put(USERS_CURRENT_LESSON_ID, user.getLessonId().toString());

        // Assuming DataLoader.getUserList() returns an ArrayList<User>
        ArrayList<User> userList = DataLoader.getUserList();
        JSONArray JSONUsers = new JSONArray();

        // Convert ArrayList<User> to JSONArray
        for (User u : userList) {
            JSONObject jsonUser = new JSONObject();
            jsonUser.put(USERS_UUID, u.getId().toString());
            jsonUser.put(USERS_CURRENT_UNIT_ID, u.getUnitId().toString());
            jsonUser.put(USERS_CURRENT_LESSON_ID, u.getLessonId().toString());
            JSONUsers.add(jsonUser);
        }

        boolean userUpdated = false;
        for (int i = 0; i < JSONUsers.size(); i++) {
            JSONObject existingUser = (JSONObject) JSONUsers.get(i);
            if (existingUser.get(USERS_UUID).equals(user.getId().toString())) {
                JSONUsers.set(i, userDetails);
                userUpdated = true;
                break;
            }
        }

        if (!userUpdated) {
            JSONUsers.add(userDetails);
        }
        
        // Write json file
        try {
            String path = getFileWritingPath(USERS_FILE_NAME, USERS_FILE_NAME_JUNIT);
			FileWriter writer = new FileWriter(path);
			
			writer.write(JSONUsers.toJSONString());
			writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getFileWritingPath(String PATH_NAME, String JUNIT_PATH_NAME) {
		try {
			if(isJUnitTest()){
				URI url = DataWriter.class.getResource(JUNIT_PATH_NAME).toURI();
				return url.getPath();
			} else {
				return PATH_NAME;
			}
		} catch(Exception e){
			System.out.println("Difficulty getting resource path");
			return "";
		}
	}

}
