package library;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Singleton class that manages the list of users in the system, providing
 * methods for user authentication, retrieval, and modification.
 *
 * @author Chris Wingo
 */
public class UserList {

    private static UserList userList;
    private ArrayList<User> users;

    /**
     * Private constructor to start the user list. Users can be loaded from an
     * external data source if needed.
     */
    private UserList() {
        users = DataLoader.getUserList();
    }

    /**
     * Returns the singleton instance of UserList, creating it if necessary.
     *
     * @return The singleton instance of UserList.
     */
    public static UserList getInstance() {
        if (userList == null) {
            userList = new UserList();
        }
        return userList;
    }

    /**
     * Checks if a user with the given username exists in the system.
     *
     * @param username The username to check.
     * @return true if the user exists, false otherwise.
     */
    public boolean userExists(String username) {
        if (username == null) 
            return false;
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the provided username and password match a user in the system.
     *
     * @param username The username to check.
     * @param password The password to validate.
     * @return true if the username and password are correct, false otherwise.
     */
    public boolean validPass(String username, String password) {
        if (username == null|| password == null)
            return false;
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a new user to the system if the username does not already exist.
     *
     * @param firstName The user's first name
     * @param lastName The user's last name
     * @param email The user's email
     * @param username The user's username
     * @param password The user's password
     * @param language The user's current language
     * @param unitId The user's current unit ID
     * @param lessonId The user's current lesson ID
     * @return True if the user was successfully added, false if the username
     * already exists.
     */
    public boolean addUser(String firstName, String lastName, String email, String phoneNumber, String username, String password, Language language, UUID unitId, UUID lessonId) {
        if (!userExists(username)) {
            users.add(new User(firstName, lastName, email, phoneNumber, username, password, language, unitId, lessonId));
            saveUsers();
            return true;
        }
        return false;
    }

    /**
     * Adds a new user to the system if the username does not already exist.
     * 
     * @param user The user to be added
     * @return True if the user was successfully added, false if the username
     * already exists.
     */
    public boolean addUser(User user) {
        if (user == null)
            return false;
        if (!userExists(user.getUsername())) {
            users.add(new User(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhoneNumber(), user.getUsername(), user.getPassword(), user.getLanguage(), user.getUnitId(), user.getLessonId()));
            saveUsers();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets a user by their username.
     *
     * @param username The username of the user.
     * @return The User object if found, or null if not found.
     */
    public User getUser(String username) {
        if (username == null)
            return  null;
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Gets a user by their unique ID.
     *
     * @param id The UUID of the user.
     * @return The User object if found, or null if not found.
     */
    public User getUserById(UUID id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Returns the list of all users in the system.
     *
     * @return The list of users.
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * Edits the details of a user in the system.
     *
     * @param firstName The new first name.
     * @param lastName The new last name.
     * @param email The new email address.
     * @param phoneNumber The new phone number.
     * @param username The username of the user to edit.
     * @param password The new password.
     * @param currentPassword The current password for validation.
     */
    public boolean editUser(String firstName, String lastName, String email, String phoneNumber,
            String username, String password, String currentPassword) {
        User user = getUser(username);
        if (user != null && user.checkPassword(password)) {
            user.updateUser(firstName, lastName, email, phoneNumber, password, currentPassword);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Attempts to log in a user with the provided username and password.
     *
     * @param username The username to log in.
     * @param password The password to log in.
     * @return true if the login is successful, false otherwise.
     */
    public boolean login(String username, String password) {
        User user = getUser(username);
        return user != null && user.checkPassword(password);
    }

    /**
     * Logs out the current user.
     *
     * @return true if the logout was successful.
     */
    public boolean logout(User user) {
        if (user != null) {
            user = null;
            return true;
        }
        return false;
    }

    public void add(User user) {
        users.add(user);
    }

    /**
     * Saves the list of users to an external data source.
     */
    public void saveUsers() {
        DataWriter.saveUsers();
    }
}
