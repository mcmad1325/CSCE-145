 package library;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Represents a User object holding all user data
 *
 * @author Sofia Bacha
 */
public class User {

    private UUID id;
    private Language language;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String username;
    private String password;
    private ArrayList<Language> languages;
    private ArrayList<Badge> badges;
    private UUID currentUnitId;
    private UUID currentLessonId;
    private WordList problemWordList;

    /**
     * Creates a new User object for first time user.
     *
     * @param firstName user's first name
     * @param lastName user's last name
     * @param email user's email
     * @param phoneNumber user's phone number
     * @param username user's created username
     * @param password user's created password
     * @param language language user is learning
     */
    public User(String firstName, String lastName, String email, String phoneNumber, String username, String password, Language language, UUID unitId, UUID lessonId) {
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
        this.language = language;
        this.currentUnitId = unitId;
        this.currentLessonId = lessonId;
        badges = new ArrayList<>();
        this.problemWordList = new WordList(new ArrayList<>());
    }

    /**
     * Creates a User object for dataLoader
     *
     * @param firstName user's first name
     * @param lastName user's last name
     * @param email user's email
     * @param phoneNumber user's phone number
     * @param username user's created username
     * @param password user's created password
     * @param language language user is learning
     * @param languages User's language options
     * @param badges Badges user has earned
     * @param currentUnitId Id of unit user is on
     * @param currentLessonId Id of lesson user is on
     */
    public User(UUID id, String firstName, String lastName, String email, String phoneNumber, String username, String password, ArrayList<Language> languages, ArrayList<Badge> badges, UUID currentUnitID, UUID currentLessonID, WordList problemWords) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
        this.languages = languages;
        this.badges = new ArrayList<>();
        this.currentUnitId = currentUnitID;
        this.currentLessonId = currentLessonID;
        this.problemWordList = problemWords;

        this.language = languages.get(0);
    }

    /**
     * Returns the first name of the user.
     *
     * @return user first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the last name of the user.
     *
     * @return user last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the email of the user.
     *
     * @return user email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the phone number of the user.
     *
     * @return user phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Returns the usernam of the user.
     *
     * @return users username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the password of the user.
     *
     * @return user password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the UUID assigned to the user.
     *
     * @return user UUID.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Returns the language enumeration user is learning.
     *
     * @return language.
     */
    public Language getLanguage() {
        return language;
    }

    /**
     * Sets the language for the user
     *
     * @param language The language user wants to learn
     */
    void setLanguage(Language language) {
        this.language = language;
    }

    /**
     * Returns the UUID of the current unit user is on.
     *
     * @return users current unit UUID.
     */
    public UUID getUnitId() {
        return currentUnitId;
    }

    /**
     * Returns the UUID of the lesson user is on.
     *
     * @return users current lessson UUID.
     */
    public UUID getLessonId() {
        return currentLessonId;
    }

    /**
     * Returns list of all language options for user.
     *
     * @return ArrayList of languages.
     */
    public ArrayList<Language> getLanguages() {
        return languages;
    }

    /**
     * Returns the badges user has earned.
     *
     * @return ArrayList of all user badges.
     */
    public ArrayList<Badge> getBadges() {
        return badges;
    }

    /**
     * Adds a problem word to the list of user's problem words
     * @param word to add the list of problem words
     */
    public void addProblemWord(Word word) {
        if (!problemWordList.getWords().contains(word)) {
            problemWordList.addWord(word);
        }
    }

    /**
     * Returns the list of words user has struggled with.
     * 
     * @return A WordList containing words the user struggles with.
     */
    public WordList getProblemWordList() {
        return problemWordList;
    }

    /**
     * Returns the user's current unit
     *
     * @return The user's current unit
     */
    public Unit getCurrentUnit() {
        return language.getUnitList().getUnitById(currentUnitId);
    }

    /**
     * Returns the user's current lesson
     *
     * @return The user's current lesson
     */
    public Lesson getCurrentLesson() {
        return getCurrentUnit().getLessonList().getLessonById(currentLessonId);
    }

    /**
     * Tests if a user already exsts by testing username
     *
     * @param username user's username
     * @return true if username alreay exists, false otherwise
     */
    public static boolean isUser(String username) {
        return UserList.getInstance().getUser(username) != null;
    }

    /**
     * Tests if entered password matches saved user password.
     *
     * @param password Entered password.
     * @return true if passwords match, false otherwise.
     */
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    /**
     * Tests if inputted username is valid (follows requirements).
     *
     * @param username username being tested.
     * @return true is the username follows requirements, false otherwise.
     */
    public static boolean validUser(String username) {
        return username != null && username.length() >= 3 && username.length() <= 20 && username.matches("[a-zA-Z0-9.]+");
    }

    /**
     * Tests if inputted password is valid (follows requirements).
     *
     * @param password password being tested.
     * @return true if password follows requirements, false otherwise
     */
    public static boolean validPass(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        boolean hasUppercase = !password.equals(password.toLowerCase());
        boolean hasNumber = password.matches(".*\\d.*");
        boolean hasSpecialChar = password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");

        return hasUppercase && hasNumber && hasSpecialChar;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " (" + username + ")";
    }

    /**
     * Adds badge object to list of badges user has earned
     *
     * @param badge Badge object to add
     */
    public void addBadge(Badge badge) {
        this.badges.add(badge);
    }

    /**
     * Removes Badge object from list of badges user has earned
     *
     * @param badge Badge object to remove
     */
    public void removeBadge(Badge badge) {
        this.badges.remove(badge);
    }

    /**
     * Displays all badges user has earned by calling method in badge class.
     */
    public void displayBadges() {
        for (Badge badge : badges) {
            badge.displayBadge();
        }
    }

    /**
     * Moves the user to the next lesson. Changes the currentLessonId to the
     * next lesson's ID.
     */
    public boolean moveToNextLesson() {
        Lesson nextLesson = getCurrentUnit().getLessonList().nextLesson(getCurrentLesson());
        if (nextLesson == null)
            return false;
        currentLessonId = nextLesson.getId();
        return true;
    }

    /**
     * Moves the user to the next unit. Changes the currentUnitId to the next
     * unit's ID.
     */
    public boolean moveToNextUnit() {
        Unit nextUnit = language.getUnitList().nextUnit(getCurrentUnit()); 
        if (nextUnit == null)
            return false;
        currentUnitId = nextUnit.getId();
        return true;
    }

    /**
     * Runs the user's current lesson. If the user passes the lesson, move to
     * the next lesson.
     */
    public void runLesson() {
        Lesson lesson = getCurrentLesson();

        //User passes lesson, move to next lesson
        if (lesson != null && lesson.run(this)) {
            System.out.println("hi");
            // If there are no more lessons, move to next unit
            if(!moveToNextLesson()) {
                System.out.println("bye");
                // If there are no more units, user is done
                if (!moveToNextUnit()) {
                    System.out.println("You have finished the last unit!");
                }
            }
        }
    }

    /**
     * Sets the Lesson user is currently on by UUID
     *
     * @param lessonId UUID of Lesson user is on
     */
    public void setCurrentLessonId(UUID lessonId) {
        this.currentLessonId = lessonId;
    }

    /**
     * Sets the Unit user is currently on by UUID
     *
     * @param lessonId UUID of Unit user is on
     */
    public void setCurrentUnitId(UUID unitId) {
        this.currentUnitId = unitId;
    }

    /**
     * Used to update/change information for user. User needs to enter correct
     * password to update any information.
     */
    public void updateUser(String firstName, String lastName, String email, String phoneNumber, String password, String currentPassword) {
        if (checkPassword(currentPassword)) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.phoneNumber = phoneNumber;
            this.password = password;
        }
    }
}
