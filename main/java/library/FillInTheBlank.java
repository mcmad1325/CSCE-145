package library;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import narration.Narrator;

/**
 * FillInTheBlank class is a type of question where the user will be given a
 * sentence with an empty space, and has to fill in the blank with the correct
 * answer
 *
 * @author Cody Miller
 */
public class FillInTheBlank implements Question {

    private String answer;
    private String question;
    private Word answerWord;

    /**
     * Constructor for the FillInTheBlank class, creates a random question based
     * off of list of testable words
     *
     * @param words ArrayList of the lesson's quizzable words
     */
    public FillInTheBlank(ArrayList<Word> words) {

        Random rand = new Random();
        this.answerWord = words.get(rand.nextInt(words.size()));

        if(answerWord != null) {
            System.out.println("Hint: " + answerWord.getTranslatedWord());
            
            this.answer = answerWord.getForeignWord();
            this.question = answerWord.getExampleSentence();
        }
    }

    /**
     * Constructor for the FillInTheBlank class, creates a random question based on the seed
     *
     * @param words ArrayList of the lesson's quizzable words
     * @param seed random seed
     */
    public FillInTheBlank(ArrayList<Word> words, int seed) {
        Random rand = new Random(seed);
        this.answerWord = words.get(rand.nextInt(words.size()));

        if(answerWord != null) {
            System.out.println("Hint: " + answerWord.getTranslatedWord());

            this.answer = answerWord.getForeignWord();
            this.question = answerWord.getExampleSentence();
        }
    }

    /**
     * Checks if the user's answer was correct or not
     *
     * @param answer the user's answer
     * @return returns true if the answer is correct, false otherwise
     */
    public boolean checkAnswer(String answer) {
        return this.answer.equalsIgnoreCase(answer);
    }

    /**
     * Get method for the question's answer
     *
     * @return the answer string
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Get method for the question
     *
     * @return the question string
     */
    public String getQuestion() {
        return question;
    }

    /**
     * toString method for WordBank
     *
     * @return toString for the question, seperated by commas, for example...
     * "perro,Yo _____ un perro"
     */
    @Override
    public String toString() {
        return answer + "," + question;
    }

    /**
     * Returns the enumeration of the question type
     *
     * @return question type
     */
    @Override
    public QuestionType getQuestionType() {
        return QuestionType.FILL_IN_THE_BLANK;
    }

    /**
     * Runs the question in the terminal to ask the user for their answer
     *
     * @return true if they get the question right, false if they get the
     * question wrong
     */
    @Override
    public boolean run(User user) {
        // Display and narrate question
        System.out.println(question);
        Narrator.playSound(question);

        System.out.print("Enter your answer:\n>");

        // Get user asnwer and check
        Scanner scan = new Scanner(System.in);
        String userAnswer = scan.nextLine();
        boolean correct = checkAnswer(userAnswer);

        // Tell user whether they got it right
        if (correct) {
            System.out.println("\nYou're right!!! Nice job!!\n");
        } else {
            System.out.println("\nYou're wrong... the right answer was " + answer + "\n");
            user.addProblemWord(answerWord);
        }

        return correct;
    }

    /**
     * Runs the question, except without asking for user input here
     * There is also no narrator for test time purposes
     *
     * @return true if they get the question right, false if they get the
     * question wrong
     */
    public boolean run(User user, String userAnswer) {
        // Display question
        System.out.println(question);

        System.out.print("Enter your answer:\n>");

        // Get user asnwer and check
        boolean correct = checkAnswer(userAnswer);

        // Tell user whether they got it right
        if (correct) {
            System.out.println("\nYou're right!!! Nice job!!\n");
        } else {
            System.out.println("\nYou're wrong... the right answer was " + answer + "\n");
            user.addProblemWord(answerWord);
        }

        return correct;
    }

}
