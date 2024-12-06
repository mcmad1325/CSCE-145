package library;

public abstract class DataConstants {

    protected static final String USERS_FILE_NAME = "src/main/java/data/user.json";
    protected static final String USERS_FILE_NAME_JUNIT = "/data/user.json";
    protected static final String USERS = "users";
    protected static final String USERS_FIRST_NAME = "firstName";
    protected static final String USERS_LAST_NAME = "lastName";
    protected static final String USERS_EMAIL = "email";
    protected static final String USERS_PHONE_NUMBER = "phoneNumber";
    protected static final String USERS_USERNAME = "username";
    protected static final String USERS_PASSWORD = "password";
    protected static final String USERS_UUID = "uuid";
    protected static final String USERS_PROGRESS = "progress";
    protected static final String USERS_CURRENT_UNIT_ID = "currentUnitID";
    protected static final String USERS_CURRENT_LESSON_ID = "currentLessonID";
    protected static final String USERS_LANGUAGES = "languages";
    protected static final String USERS_BADGES = "badges";
    protected static final String USERS_BADGE_ID = "badgeID";
    protected static final String USERS_PROBLEM_WORDS = "problemWords";
    protected static final String USERS_WORD_ID = "id";
    protected static final String USERS_WORD_TEXT = "text";
    protected static final String USERS_WORD_TRANSLATION = "translation";
    protected static final String USERS_WORD_PART_OF_SPEECH = "partOfSpeech";
    protected static final String USERS_WORD_EXAMPLE_SENTENCE = "exampleSentence";

    protected static final String LANGUAGE_FILE_NAME = "src/main/java/data/language.json";
    protected static final String LANGUAGE_FILE_NAME_JUNIT = "/data/language.json";
    protected static final String LANGUAGE_ENUM = "languageEnum";
    protected static final String LANGUAGE_NAME = "languageName";
    protected static final String LANGUAGE_UNIT_LIST = "unitList";

    protected static final String UNIT_FILE_NAME = "json/unit.json";
    protected static final String UNIT_ID = "unitID";
    protected static final String UNIT_NAME = "unitName";
    protected static final String UNIT_NUMBER = "unitNumber";
    protected static final String UNIT_LESSON_LIST = "lessonList";

    protected static final String LESSON_ID = "lessonID";
    protected static final String LESSON_NAME = "lessonName";
    protected static final String LESSON_NUMBER = "lessonNumber";
    protected static final String LESSON_WORD_LIST = "wordList";

    protected static final String WORD_ID = "wordID";
    protected static final String WORD_TEXT = "wordText";
    protected static final String WORD_TRANSLATION = "wordTranslation";
    protected static final String WORD_PART_OF_SPEECH = "wordPartOfSpeech";
    protected static final String WORD_EXAMPLE_SENTENCE = "wordExampleSentence";

    public static boolean isJUnitTest() {  
		for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
		  if (element.getClassName().startsWith("org.junit")) {
			return true;
		  }           
		}
		return false;
	  }
}
