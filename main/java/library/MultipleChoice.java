package library;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

import narration.Narrator;

/**
 * MultipleChoice class is a type of question where the user will be given a
 * question and four to five possible answers to choose from for that question
 *
 * @author Cody Miller
 */
public class MultipleChoice implements Question {

    private Word word;
    private String answer;
    private String question;
    private ArrayList<String> choices;
    private int correctIndex;

    /**
     * Constructor for the MultipleChoice class, creates a random question based
     * off of list of testable words
     *
     * @param words ArrayList of the lesson's quizzable words
     */
    public MultipleChoice(ArrayList<Word> words) {
        this.choices = new ArrayList<>();

        Collections.shuffle(words);

        this.word = words.get(0);
        this.answer = words.get(0).getForeignWord();
        this.question = words.get(0).getExampleSentence();
        this.choices.add(answer);

        for (int i = 1; i < 4; i++) { //Populating the answers, english, and foreign word lists/maps
            choices.add(words.get(i).getForeignWord());
        }

        Collections.shuffle(this.choices);
        correctIndex = choices.indexOf(answer);
    }

    /**
     * Constructor for the MultipleChoice class, creates a random question based on the seed
     *
     * @param words ArrayList of the lesson's quizzable words
     * @param seed random seed
     */
    public MultipleChoice(ArrayList<Word> words, int seed) {
        Random rand = new Random(seed);

        this.choices = new ArrayList<>();

        Collections.shuffle(words,rand);

        this.word = words.get(0);
        this.answer = words.get(0).getForeignWord();
        this.question = words.get(0).getExampleSentence();
        this.choices.add(answer);

        for (int i = 1; i < 4; i++) { //Populating the answers, english, and foreign word lists/maps
            choices.add(words.get(i).getForeignWord());
        }

        Collections.shuffle(this.choices,rand);
        correctIndex = choices.indexOf(answer);
    }

    /**
     * Checks if the user's answer was correct or not
     *
     * @param answer the user's answer
     * @return returns true if the answer is correct, false otherwise
     */
    public boolean checkAnswer(String answer) {
        return (this.answer.equalsIgnoreCase(answer) || this.correctIndex == (Integer.parseInt(answer)-1));
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
     * Get method for the answer choices
     *
     * @return the answer choice arraylist
     */
    public ArrayList<String> getChoices() {
        return choices;
    }

    /**
     * toString method for WordBank
     *
     * @return toString for the question, seperated by commas, for example...
     * "Dog,Which is a valid pet?,perro,enchilada,dinero,futbol"
     */
    @Override
    public String toString() {
        String returnString = answer + "," + question;
        for (String word : choices) {
            returnString += "," + word;
        }
        return returnString;
    }

    /**
     * Returns the enumeration of the question type
     *
     * @return question type
     */
    @Override
    public QuestionType getQuestionType() {
        return QuestionType.MATCHING;
    }

    /**
     * Runs the question in the terminal to ask the user for their answer
     *
     * @return true if they get the question right, false if they get the
     * question wrong
     */
    @Override
    public boolean run(User user) {
        System.out.println(question + "\n");
        Narrator.playSound(question);

        // Display and narrate choices
        System.out.println("Choices:");
        for (int i = 0; i < choices.size(); i++) {
            System.out.println(i + 1 + ". " + choices.get(i));
            Narrator.playSound(choices.get(i));
        }

        System.out.println();

        // Get user answer and check
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter you answer:\n>");
        String userAnswer = scan.nextLine();
        boolean correct = checkAnswer(userAnswer);

        // Tell user whether they got it right
        if (correct) {
            System.out.println("\nYou're right!!! Nice job!!\n");
        } else {
            System.out.println("\nYou're wrong... the right answer was " + answer + "\n");
            user.addProblemWord(word);
        }

        return correct;
    }

    /**
     * Variation of run method, but without collecting user input, used for testing purposes.
     * Also removed narrator so that it doesn't take so long for testing
     *
     * @return true if they get the question right, false if they get the
     * question wrong
     */
    public boolean run(User user,String userAnswer) {
        System.out.println(question + "\n");

        // Display and narrate choices
        System.out.println("Choices:");
        for (int i = 0; i < choices.size(); i++) {
            System.out.println(i + 1 + ". " + choices.get(i));
        }

        System.out.println();

        boolean correct = checkAnswer(userAnswer);

        // Tell user whether they got it right
        if (correct) {
            System.out.println("\nYou're right!!! Nice job!!\n");
        } else {
            System.out.println("\nYou're wrong... the right answer was " + answer + "\n");
            user.addProblemWord(word);
        }

        return correct;
    }

}
