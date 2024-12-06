package library;

import java.util.UUID;

public class LanguageSystemFacade {

    private User currentUser;
    private UserList userList;
    private LanguageList languageList;
    private UnitList unitList;
    private LessonList lessonList;

    /**
     * Initializes the facade by retrieving instances of system components like
     * UserList, LanguageList, UnitList, and LessonList.
     *
     * @author Chris Wingo
     */
    public LanguageSystemFacade() {
        userList = UserList.getInstance();
        languageList = LanguageList.getInstance();
    }

    /**
     * Logs in a user
     *
     * @param username username of a user
     * @param password password of a user
     * @return true if the user logged in, false if otherwise
     */
    public User login(String username, String password) {
        if (userList.userExists(username)) {
            if (userList.login(username, password)) {
                currentUser = userList.getUser(username);
                return currentUser;
            } else {
                System.out.println("Password is incorrect, please try logging in again \n");
            }
        } else {
            System.out.println("Username does not exist in this system, please try logging in again :)\n");
        }
        return null;
    }

    /**
     * Logs out the current user.
     *
     * @return true if the user is logged out successfully, false if no user is
     * logged in.
     */
    public boolean logout() {
        return userList.logout(currentUser);
    }

    /**
     * Loads the specified language for the current user.
     *
     * @param languageName The name of the language to load.
     * @return true if the language is successfully loaded, false if the
     * language is not found.
     */
    public boolean loadLanguage(String languageName) {
        if (languageList.contains(languageName)) {
            Language language = languageList.getLanguage(languageName);
            currentUser.setLanguage(language);
            return true;
        }
        return false;
    }

    /**
     * Runs a specific unit for the current user. If unitNumber is 0, the
     * pre-assessment is run.
     *
     * @param unitNumber The number of the unit to run.
     * @return true if the unit runs successfully, false otherwise.
     */
    public boolean runUnit(int unitNumber) {
        if (unitNumber == 0) {
            return runPreAssessment();
        }
        Unit unit = currentUser.getLanguage().getUnitList().getUnit(unitNumber);
        if (unit != null) {
            Lesson lesson = unit.getLessonList().getLesson(0);  // Starts with the first lesson
            return runLesson(lesson);
        }
        return false;
    }

    /**
     * Runs the specified lesson for the current user and manages progression through lessons and units.
     * 
     * @param lesson The lesson to run for user.
     * @return true if lesson was successfully completed, false if the lesson was null, or if there are no more lesson or units. 
     */
    public boolean runLesson(Lesson lesson) {
        if (lesson == null) {
            return false;
        }
        if (lesson.run(this.currentUser)) {
            System.out.println("You have successfully passed this lesson, so you are moving onto the next one!!!\n");
            if (!getLessonList().goToNextLesson()) {
                System.out.println("This unit has no more lessons to take, which means you're moving onto the next unit!!!\n");
                if (!getUnitList().goToNextUnit()) {
                    System.out.println("You have completed your language training! Congrats!!\n");
                }
            }
            currentUser.setCurrentLessonId(getLessonList().getCurrentLesson().getId());
            currentUser.setCurrentUnitId(getUnitList().getCurrentUnit().getId());
            saveProgress();
        }
        return false;
    }

    /**
     * Runs the pre-assessment (Unit 0) for the current user.
     *
     * @return true if the pre-assessment runs successfully, false otherwise.
     */
    public boolean runPreAssessment() {
        Unit preAssessmentUnit = currentUser.getLanguage().getUnitList().getUnit(0);
        if (preAssessmentUnit == null) {
            return false;
        }
        Lesson preAssessmentLesson = preAssessmentUnit.getLessonList().getLesson(0);
        return runLesson(preAssessmentLesson);
    }

    /**
     * Runs the next lesson
     * @return true if the lesson was ran, false otherwise
     */
    public boolean runNextLesson() {
        Lesson lesson = this.currentUser.getCurrentLesson();

        if (lesson.run(this.currentUser)) { //User has successfully passed the lesson
            System.out.println("You have successfully passed this lesson, so you are moving onto the next one!!!\n");

            if (!getLessonList().goToNextLesson()) { // The unit has no more lessons to take, go to next unit

                System.out.println("This unit has no more lessons to take, which means you're moving onto the next unit!!!\n");
                if (!getUnitList().goToNextUnit()) { // The language has no more units to take, you completed your learning!!
                    System.out.println("You have completed your language training! Congrats!!\n");
                }
            }
        }
        currentUser.setCurrentLessonId(getUnitList().getCurrentUnit().getLessonList().getLesson(0).getId());
        currentUser.setCurrentUnitId(getUnitList().getCurrentUnit().getId());

        return false;
    }

    /**
     * Get's the unit list that the current user has for their language
     *
     * @return the user's unit list
     */
    public UnitList getUnitList() {
        return this.currentUser.getLanguage().getUnitList();
    }

    /**
     * Get's the lesson list that the current user has for their current unit
     *
     * @return the user's lesson list
     */
    public LessonList getLessonList() {
        return this.currentUser.getCurrentUnit().getLessonList();
    }

    /**
     * Saves the current user's progress.
     *
     * @return true if progress is saved successfully, false if no user is
     * logged in.
     */
    public boolean saveProgress() {
        if (currentUser != null) {
            DataWriter.saveUserProgress(currentUser);
            return true;
        }
        return false;
    }

    /**
     * Validates the username to ensure it is between 3 and 20 characters.
     *
     * @param username The username to validate.
     * @return true if the username is valid, false otherwise.
     */
    public boolean validateUsername(String username) {
        return (User.validUser(username));
    }

    /**
     * Validates the password to ensure it is at least 8 characters long,
     * contains at least one uppercase letter, one number, and one special
     * character.
     *
     * @param password The password to validate.
     * @return true if the password is valid, false otherwise.
     */
    public boolean validatePassword(String password) {
        return (User.validPass(password));
    }

    /**
     * Checks if a username already exists
     *
     * @param username username testing to see if it exists
     * @return true if the username exists, false otherwise
     */
    public boolean usernameExists(String username) {
        return User.isUser(username);
    }

    /**
     * Method for creating a user
     *
     * @param firstName first name of user
     * @param lastName last name of user
     * @param email email of the user
     * @param phoneNumber phone number of the user
     * @param username username of the user
     * @param password password of the user
     * @param language language the user is taking
     * @return true if user creation was successfull, false otherwise
     */
    public boolean createUser(String firstName, String lastName, String email, String phoneNumber, String username, String password, Language language) {
        if (!validatePassword(password)) { //Invalid password
            System.out.println("Password is invalid, make sure it is...\n1. Longer than 8 characters"
                  + "\n2. Has at least one uppercase letter"
                + "\n3. Has at least one digit"
              + "\n4. Has at least one special character\n");
            return false;
        }

        if (!validateUsername(username)) { //Invalid username
            System.out.println("Username is invalid, make sure that it is between 3 and 20 characters, and does not have any invalid characters\n");
            return false;
        }
        Unit unit = language.getUnitList().getUnit(0);
        UUID unitId = unit.getId();

        Lesson lesson = unit.getLessonList().getLesson(0);
        UUID lessonId = lesson.getId();

        if (userList.addUser(firstName, lastName, email, phoneNumber, username, password, language, unitId, lessonId)) { //Username already taken
            System.out.println("Successfully created a new account, time for some learning!\n");
            return true;
        } else {
            System.out.println("Username has already been taken, please create a newer, more unique username\n");
            return true;
        }
    }

    /**
     * Get's the problem word list
     * @return The problem word list
     */
    public WordList getProblemWordList() {
        if (currentUser != null) {
            return currentUser.getProblemWordList();
        } else {
            return null;
        }
    }
}
