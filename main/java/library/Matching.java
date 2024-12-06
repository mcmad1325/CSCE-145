package library;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import narration.Narrator;

/**
 * Matching class is a type of question where the user will be given English
 * words on the left side of the screen, and foreign words on the right, and
 * will have to connect the foreign words to the english words
 *
 * @author Cody Miller
 */
public class Matching implements Question {

    private HashMap<String, String> answers;
    private ArrayList<String> englishWords;
    private ArrayList<String> foreignWords;
    private ArrayList<Word> originalWords;

    /**
     * Constructor for the Matching class, creates a random question based off
     * of list of testable words
     *
     * @param words ArrayList of the lesson's quizzable words
     */
    public Matching(ArrayList<Word> words) {
        englishWords = new ArrayList<>();
        foreignWords = new ArrayList<>();
        answers = new HashMap<>();
        originalWords = new ArrayList<>();

        Collections.shuffle(words);

        for (int i = 3; i >= 0; i--) { //Populating the answers, english, and foreign word lists/maps
            Word word = words.get(i);
            englishWords.add(word.getTranslatedWord());
            foreignWords.add(word.getForeignWord());
            answers.put(word.getTranslatedWord(), word.getForeignWord());
            originalWords.add(word);
        }

        Collections.shuffle(englishWords);
        Collections.shuffle(foreignWords);
    }

    /**
     * Constructor for the Matching class, creates a random question based off on the seed
     *
     * @param words ArrayList of the lesson's quizzable words
     * @param seed random seed
     */
    public Matching(ArrayList<Word> words, int seed) {
        Random rand = new Random(seed);

        englishWords = new ArrayList<>();
        foreignWords = new ArrayList<>();
        answers = new HashMap<>();
        originalWords = new ArrayList<>();

        Collections.shuffle(words,rand);

        for (int i = 3; i >= 0; i--) { //Populating the answers, english, and foreign word lists/maps
            Word word = words.get(i);
            englishWords.add(word.getTranslatedWord());
            foreignWords.add(word.getForeignWord());
            answers.put(word.getTranslatedWord(), word.getForeignWord());
            originalWords.add(word);
        }

        Collections.shuffle(englishWords,rand);
        Collections.shuffle(foreignWords,rand);

        System.out.println("Correct Answers::: ");
        for(int i = 0; i<4; i++) {
            System.out.println(englishWords.get(i));
        }
    }

    /**
     * Checks if the user's answer was correct or not
     *
     * @param answers the user's answers
     * @return returns true if the answers are correct, false otherwise
     */
    public boolean checkAnswer(HashMap<String, String> answers) {
        for(int i = 0; i<4; i++) {
            String answersKey = this.answers.keySet().toArray()[i].toString();
            if(!answers.get(answersKey).equals(this.answers.get(answersKey)));
                return false;
        }
        return true;
    }

    /**
     * Get method for the question's answers
     *
     * @return the answer string
     */
    public HashMap<String, String> getAnswers() {
        return answers;
    }

    /**
     * Get method for the English words list
     *
     * @return the english words arraylist
     */
    public ArrayList<String> getEnglishWords() {
        return englishWords;
    }

    /**
     * Get method for the foreign words list
     *
     * @return the foregin words arraylist
     */
    public ArrayList<String> getForeignWords() {
        return foreignWords;
    }

    /**
     * toString method for Matching
     *
     * @return toString for the question, seperated by commas, for example...
     * "perro,enchilada,futbol,dinero,enchilada,money,soccer,dog"
     */
    @Override
    public String toString() {
        String returnString = "";
        for (String word : foreignWords) {
            returnString += "," + word;
        }
        for (String word : englishWords) {
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

        // Display and narrate words
        
        for(int i = 0; i<englishWords.size(); i++) {
            System.out.println(englishWords.get(i)+"\t\t\t"+foreignWords.get(i));
            Narrator.playSound(foreignWords.get(i));
        }

        System.out.println();

        // Get user answer and tell them whether they got it right
        for (int i = 0; i < englishWords.size(); i++) {
            boolean correct = answerPart(i, user);

            // Answer incorrect
            if (!correct) {
                System.out.println("\nYou got that match wrong! The right answer was " + answers.get(englishWords.get(i)) + "\n");
                return false;
            }
        }

        // Answer correct
        System.out.println("\nYou got these matches correct!!!!! You're amazing!\n");
        return true;
    }

    /**
     * Helper method for run
     *
     * @param cycle integer current cycle number
     * @return true if you get the first answer part right, and false if not
     */
    private boolean answerPart(int cycle, User user) {
        System.out.print(englishWords.get(cycle) + " -> ");

        Scanner scan = new Scanner(System.in);
        String userAnswer = scan.nextLine();

        if (!answers.get(englishWords.get(cycle)).equalsIgnoreCase(userAnswer)) {
            user.addProblemWord(originalWords.get(cycle));
            return false;
        }
        return true;
    }

    /**
     * Test version of answerPart
     *
     * @param cycle integer current cycle number
     * @return true if you get the first answer part right, and false if not
     */
    public boolean answerPart(int cycle, User user, String userAnswer) {
        System.out.print(englishWords.get(cycle) + " -> ");

        if (!answers.get(englishWords.get(cycle)).equalsIgnoreCase(userAnswer)) {
            user.addProblemWord(originalWords.get(cycle));
            return false;
        }
        return true;
    }

}
