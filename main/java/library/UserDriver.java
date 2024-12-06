package library;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

import narration.Narrator;

/**
 * @author Madeleine McBride and Lea Frost
 * 
 * The {@code UserDriver} class is the main entry point for the Language Learning System.
 * 
 * It provides a command-line interface for user registration, login, language selection,
 * and learning progression, with functionalities supported by a {@code LanguageSystemFacade}.
 * The class manages user input, displays menus, and handles user actions through its methods.
 */
public class UserDriver {
    /** Facade to handle system operations related to languages and user management. */
    private static LanguageSystemFacade facade = new LanguageSystemFacade();

    /** The currently logged-in user, if any. */
    private static User currentUser;

    /** Scanner to process user input from the console. */
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Main method to initialize and start the Language Learning System.
     * It displays a menu and processes user choices in a loop until the exit option is selected.
     * 
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        initializeSystem();
        boolean exit = false;

        while (!exit) {
            displayMainMenu();
            int choice = getChoice();
            switch (choice) {
                case 1 -> loginUser();
                case 2 -> registerUser();
                case 3 -> {
                    if (currentUser != null) {
                        chooseLanguage();
                    } else {
                        System.out.println("Please log in first.");
                    }
                }
                case 4 -> viewProblemWords();
                case 5 -> logoutUser();
                case 6 -> exit = true;
                
                default -> System.out.println("Invalid choice. Please try again.");
            }
            DataWriter.saveUsers();
        }
        System.out.println("Exiting the Language Learning System. Goodbye!");
    }

    /**
     * Displays the main menu options for the Language Learning System.
     */
    private static void displayMainMenu() {
        System.out.println("\n=== Language Learning System ===");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Choose Language & Start Learning");
        System.out.println("4. View Problem Words/Progress");
        System.out.println("5. Logout");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    /**
     * Retrieves the user's choice from the console input and handles invalid input.
     * 
     * @return the user's choice as an integer, or -1 if the input is invalid
     */
    private static int getChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return -1;
        }
    }

    /**
     * Registers a new user by prompting for user details such as username, password,
     * and primary language. It checks for existing usernames and sets the initial
     * learning unit and lesson for the user.
     */
    private static void registerUser() {
        System.out.print("Enter a username: ");
        String username = scanner.nextLine();

       if (facade.usernameExists(username)) {
            System.out.println("Username already exists. Please choose another.");
            return;
        }

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your phone number: ");
        String phoneNumber = scanner.nextLine();

        Language selectedLanguage = choosePrimaryLanguage();
        if (selectedLanguage == null) {
            System.out.println("No valid language selected. Registration aborted.");
            return;
        }

        if (facade.createUser(firstName, lastName, email, phoneNumber, username, password, selectedLanguage)) {
            System.out.println("Registration successful! You can now log in.");
        } else {
            System.out.println("Registration failed. Please try again.");
        }

        UUID initialUnitId = selectedLanguage.getUnitList().getUnits().get(0).getId();
        UUID initialLessonId = selectedLanguage.getUnitList().getUnits().get(0).getLessonList().getLessons().get(0).getId();
        boolean added = UserList.getInstance().addUser(firstName, lastName, email, phoneNumber, username, password, selectedLanguage, initialUnitId, initialLessonId);
        if (added) {
            System.out.println("Registration successful! You can now log in.");
        } else {
            System.out.println("Registration failed. Username may already exist.");
        } 
    }

    /**
     * Logs in an existing user by verifying their username and password.
     * Sets the {@code currentUser} if login is successful.
     */
    private static void loginUser() {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        if (facade.login(username, password) != null) {
            currentUser = facade.login(username, password);
            System.out.println("Login successful! Welcome, " + currentUser.getUsername() + ".");
        } else {
            System.out.println("Login failed. Please check your username and password.");
        }
    }

    /**
     * Logs out the currently logged-in user, if any, and resets the {@code currentUser}.
     */
    private static void logoutUser() {
        if (facade.logout()) {
            currentUser = null;
            System.out.println("You have been logged out.");
        } else {
            System.out.println("No user is currently logged in.");
        }
    }

    /**
     * Allows the logged-in user to select a language for learning.
     * Starts the learning process upon successful language selection.
     */
    private static void chooseLanguage() {
        if (currentUser == null) {
            System.out.println("Please log in first.");
            return;
        }

        ArrayList<Language> languages = LanguageList.getInstance().getLanguages();
        if (languages == null || languages.isEmpty()) {
            System.out.println("No languages available.");
            return;
        }

        System.out.println("\n=== Choose a Language ===");
        for (int i = 0; i < languages.size(); i++) {
            System.out.println((i + 1) + ". " + languages.get(i).getLanguageName());
        }
        System.out.print("Enter the number of the language you want to learn: ");
        int choice = getChoice();

        if (choice < 1 || choice > languages.size()) {
            System.out.println("Invalid choice.");
            return;
        }
        
        Language selectedLanguage = languages.get(choice - 1);
        if (facade.loadLanguage(selectedLanguage.getLanguageName())) {
            startLearning();
        } else {
            System.out.println("Failed to load the selected language.");
        }
    }

    /**
     * Allows the user to choose a primary language during registration.
     * 
     * @return the selected {@code Language}, or {@code null} if no valid choice is made
     */
    private static Language choosePrimaryLanguage() {
        ArrayList<Language> languages = LanguageList.getInstance().getLanguages();
        if (languages == null || languages.isEmpty()) {
            System.out.println("No languages available.");
            return null;
        }

        System.out.println("\n=== Choose a Primary Language ===");
        for (int i = 0; i < languages.size(); i++) {
            System.out.println((i + 1) + ". " + languages.get(i).getLanguageName());
        }
        System.out.print("Enter the number of the language you want to learn: ");
        int choice = getChoice();

        if (choice < 1 || choice > languages.size()) {
            System.out.println("Invalid choice.");
            return null;
        }

        return languages.get(choice - 1);
    }

    /**
     * Displays words the user is struggling with, along with the current unit and lesson.
     */
    private static void viewProblemWords() {
        WordList userProblemWordList = facade.getProblemWordList();
        ArrayList<Word> words = userProblemWordList.getWords();

        System.out.println();
        System.out.println("Current Unit: " + currentUser.getCurrentUnit().getUnitName());
        System.out.println("Current Lesson: " + currentUser.getCurrentLesson().getLessonName());
        System.out.println();


        System.out.println("Here are the words you are struggling with:");
        for (Word word : words){
            System.out.println(word.getForeignWord());
        }

    }

    /**
     * Starts the learning session for the current user, progressing through lessons
     * in the selected language. Saves user progress after each lesson.
     */
    private static void startLearning() {
        System.out.println("\nStarting lessons...");
        if (facade.runNextLesson()) {
            System.out.println("Congratulations on completing the lesson!");
        } else {
            System.out.println("No further lessons available.");
        }
        if (currentUser == null) {
            System.out.println("No user is currently logged in.");
            return;
        }
        System.out.println("\nYou have selected: " + currentUser.getLanguage().getLanguageName());

        while (true) {
            Lesson currentLesson = currentUser.getCurrentLesson();
            if (currentLesson == null) {
                System.out.println("No lessons available.");
                break;
            }
            System.out.println("Starting Lesson: " + currentLesson.getLessonName());
            currentUser.runLesson();
            UserList.getInstance().saveUsers();
            if (currentUser.getCurrentLesson() == null) {
                System.out.println("Unit completed!");
                break;
            }
        }
    }

    /**
     * Initializes the Language Learning System, loading necessary data and resources.
     */
    private static void initializeSystem() {
        Narrator.playSound("");
        System.out.println("Initializing system...");
        UserList.getInstance();
        LanguageList.getInstance();
    }
}
