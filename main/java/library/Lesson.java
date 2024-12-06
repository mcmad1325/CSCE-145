package library;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import narration.Narrator;

import java.util.Scanner;

/**
 * Represents a language lesson, containing questions, words, and progress
 * tracking.
 *
 * @author Chris Wingo
 */
public class Lesson {

    private String lessonName;
    private int lessonNum;
    private UUID id;
    private WordList wordList;
    private int correctAnswerCount;
    private int wrongAnswerCount;
    private static final int REQUIRED_CORRECT_ANSWERS = 4;
    private static final int MAX_WRONG_ANSWERS = 1;

    /**
     * Constructs a lesson with a name, number, and word list.
     *
     * @param lessonName The name of the lesson.
     * @param lessonNum The lesson's number.
     * @param wordList The list of words associated with the lesson.
     */
    public Lesson(String lessonName, int lessonNum, WordList wordList) {
        this.lessonName = lessonName;
        this.lessonNum = lessonNum;
        this.wordList = wordList;
        this.correctAnswerCount = 0;
        this.wrongAnswerCount = 0;
    }

    /**
     * Constructs a lesson with a unique ID, name, number, and word list.
     *
     * @param id The unique ID of the lesson.
     * @param lessonName The name of the lesson.
     * @param lessonNum The lesson's number.
     * @param wordList The list of words associated with the lesson.
     */
    public Lesson(UUID id, String lessonName, int lessonNum, WordList wordList) {
        this.id = id;
        this.lessonName = lessonName;
        this.lessonNum = lessonNum;
        this.wordList = wordList;
        this.correctAnswerCount = 0;
        this.wrongAnswerCount = 0;
    }

    /**
     * Returns the lesson's unique ID.
     *
     * @return The lesson's UUID.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Returns the lesson number.
     *
     * @return The lesson number.
     */
    public int getLessonNumber() {
        return lessonNum;
    }

    /**
     * Returns the lesson name.
     *
     * @return The lesson name.
     */
    public String getLessonName() {
        return lessonName;
    }

    /**
     * Returns the list of words associated with the lesson.
     *
     * @return The list of words.
     */
    public ArrayList<Word> getWords() {
        return wordList.getWords();
    }

    /**
     * Returns a random question from the lesson.
     *
     * @return A random question, or null if no questions are available.
     */
    public boolean runRandomQuestion(User user) {
        Random rand = new Random();
        Question question;

        question = switch (rand.nextInt(4)) {
            case 0 ->
                new FillInTheBlank(wordList.getWords());
            case 1 ->
                new Matching(wordList.getWords());
            case 2 ->
                new MultipleChoice(wordList.getWords());
            default ->
                new WordBank(wordList.getWords());
        };

        return question.run(user);
    }

    /**
     * Checks if the user can move to the next lesson based on correct answers.
     *
     * @return true if the user can move to the next lesson, false otherwise.
     */
    public boolean canMoveToNextLesson() {
        return correctAnswerCount >= REQUIRED_CORRECT_ANSWERS;
    }

    /**
     * Checks if the user should fail current lesson based on wrong answers.
     *
     * @return true if the user failed the next lesson, false otherwise.
     */
    public boolean shouldFailCurrentLesson() {
        return wrongAnswerCount > MAX_WRONG_ANSWERS;
    }

    /**
     * Resets the user's progress in the lesson.
     */
    private void resetProgress() {
        correctAnswerCount = 0;
        wrongAnswerCount = 0;
    }

    /**
     * Returns the number of correct answers.
     *
     * @return The correct answer count.
     */
    public int getCorrectAnswerCount() {
        return correctAnswerCount;
    }

    /**
     * Returns the number of wrong answers.
     *
     * @return The wrong answer count.
     */
    public int getWrongAnswerCount() {
        return wrongAnswerCount;
    }

    /**
     * Provides a string representation of the lesson.
     *
     * @return A string with the lesson name, number, and question count.
     */
    @Override
    public String toString() {
        return "Lesson Name: " + lessonName + "\nLesson Number: " + lessonNum;
    }

    /**
     * Allows the user to learn the lesson's words.
     * Flashcards, user views the word then the translation.
     * Press enter to view the next word.
     */
    public void learn() {
        Scanner scan = new Scanner(System.in);

        System.out.println();
        System.out.println("Flashcards - Press enter to view the translation");
        System.out.println();

        for (Word word : getWords()) { // Iterate through the words
            System.out.print(word.getForeignWord()); // Display foreign word
            Narrator.playSound(word.getForeignWord());
            scan.nextLine(); // User presses enter to view translated word
            System.out.print(word.getTranslatedWord()); // Display translation
            scan.nextLine();
            System.out.println();
        }
    }

    /**
     * Method for running a lesson Generates random questions Runs each question
     * Updates correct/incorrect
     *
     * @return True if the user can progress to next lesson, false otherwise
     */
    public boolean run(User user) {
        int questionNum = 1;

        System.out.println("Welcome to " + lessonName + "!");

        learn(); // Allows the user to learn

        resetProgress(); // Reset progress


        while (true) { // Run questions
            System.out.println("Question " + questionNum + ": ");
            boolean correctQuestion = runRandomQuestion(user);

            if (correctQuestion) {
                correctAnswerCount++;

                //Checks if the lesson is over, by having enough correct answers
                if (canMoveToNextLesson()) {
                    System.out.println("You scored " + correctAnswerCount + "/" + questionNum);
                    System.out.println();
                    return true;
                }
            } else {
                wrongAnswerCount++;

                //Checks if the lesson is over, by having too many wrong answers
                if (shouldFailCurrentLesson()) {
                    System.out.println("You scored " + correctAnswerCount + "/" + questionNum);
                    System.out.println("You did not move on to the next lesson");
                    return false;
                }
            }
            questionNum++;
        }
    }
}
