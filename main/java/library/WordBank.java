package library;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

import narration.Narrator;

/**
 * WordBank class is a type of question where the user will be given a sentence
 * with an empty space, and has to choose a word from the bank that correctly
 * fills the space
 *
 * @author Cody Miller
 */
public class WordBank implements Question {

    private String answer;
    private String question;
    private ArrayList<Word> wordBank;

    /**
     * Constructor for the WordrBank class, creates a random question based off
     * of list of testable words
     *
     * @param words ArrayList of the lesson's quizzable words
     */
    public WordBank(ArrayList<Word> words) {
        Random rand = new Random();

        Collections.shuffle(words);

        Word answerWord = words.get(0);
        this.answer = answerWord.getForeignWord();
        this.question = answerWord.getExampleSentence();
        this.wordBank = words;

        for (int i = wordBank.size() - 1; i >= 0; i--) {
            if (answerWord.getPartofSpeech().equalsIgnoreCase(wordBank.get(i).getPartofSpeech())) {
                wordBank.remove(i);
            }
        }

        wordBank.add(answerWord);

        Collections.shuffle(this.wordBank);
    }

    /**
     * Constructor for the WordBank class, creates a random question based off of a seed
     *
     * @param words ArrayList of the lesson's quizzable words
     */
    public WordBank(ArrayList<Word> words, int seed) {
        Random rand = new Random(seed);

        Collections.shuffle(words,rand);

        Word answerWord = words.get(0);
        this.answer = answerWord.getForeignWord();
        this.question = answerWord.getExampleSentence();
        this.wordBank = words;

        for (int i = wordBank.size() - 1; i >= 0; i--) {
            if (answerWord.getPartofSpeech().equalsIgnoreCase(wordBank.get(i).getPartofSpeech())) {
                wordBank.remove(i);
            }
        }

        wordBank.add(answerWord);

        Collections.shuffle(this.wordBank,rand);
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
     * Get method for the wordbank
     *
     * @return the word bank arraylist
     */
    public ArrayList<Word> getWordBank() {
        return wordBank;
    }

    /**
     * toString method for WordBank
     *
     * @return toString for the question, seperated by commas, for example...
     * "perro,I own a pet _____,perro,enchilada,dinero,futbol"
     */
    @Override
    public String toString() {
        String returnString = answer + "," + question;
        for (Word word : wordBank) {
            returnString += "," + word.getForeignWord();
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
        return QuestionType.WORD_BANK;
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

        // Display and narrate word bank words
        System.out.println("Word choices:");
        for (int i = 0; i < wordBank.size(); i++) {
            System.out.println(wordBank.get(i).getForeignWord());
            Narrator.playSound(wordBank.get(i).getForeignWord());
        }

        System.out.println();

        // Get user answer and check
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter your answer:\n>");
        String userAnswer = scan.nextLine();
        boolean correct = checkAnswer(userAnswer);

        // Tell user whether they got it right
        if (correct) {
            System.out.println("\nYou're right!!! Nice job!!\n");
        } else {
            System.out.println("\nYou're wrong... the right answer was " + answer + "\n");
            Word problemWord = null;
            for (Word word : wordBank) {
                if (word.getForeignWord().equalsIgnoreCase(answer)) {
                    problemWord = word;
                    break; // Stop once we've found the word
                }
            }
            if (problemWord != null) {
                user.addProblemWord(problemWord);
            }
        }

        return correct;
    }

    /**
     * Runs the question without the terminal used, for testing purposes
     * Narrator functionality is also removed for test efficiency
     *
     * @return true if they get the question right, false if they get the
     * question wrong
     */
    public boolean run(User user,String userAnswer) {
        System.out.println(question + "\n");

        // Display and narrate word bank words
        System.out.println("Word choices:");
        for (int i = 0; i < wordBank.size(); i++) {
            System.out.println(wordBank.get(i).getForeignWord());
        }

        System.out.println();

        boolean correct = checkAnswer(userAnswer);

        // Tell user whether they got it right
        if (correct) {
            System.out.println("\nYou're right!!! Nice job!!\n");
        } else {
            System.out.println("\nYou're wrong... the right answer was " + answer + "\n");
            Word problemWord = null;
            for (Word word : wordBank) {
                if (word.getForeignWord().equalsIgnoreCase(answer)) {
                    problemWord = word;
                    break; // Stop once we've found the word
                }
            }
            if (problemWord != null) {
                user.addProblemWord(problemWord);
            }
        }

        return correct;
    }

}
