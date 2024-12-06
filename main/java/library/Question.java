package library;

/**
 * Question class that is over WordBank.java, Matching.java, etc.
 *
 * @author Cody Miller
 */
public interface Question {

    public String toString();

    public QuestionType getQuestionType();

    public boolean run(User user);
}
