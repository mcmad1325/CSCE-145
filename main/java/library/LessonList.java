package library;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Represents a list of lessons. This class provides methods to add, remove, and
 * manage lessons within the list. It also keeps track of the current lesson
 * index for easy navigation.
 *
 * @author Madeleine McBride and Cody Miller
 */
public class LessonList {

    private ArrayList<Lesson> lessons;
    private int currentLessonIndex;

    /**
     * Constructs a LessonList with the specified list of lessons.
     *
     * @param lessons the initial list of lessons
     */
    public LessonList(ArrayList<Lesson> lessons) {
        this.lessons = lessons;
        this.currentLessonIndex = 0;
    }

    public Lesson getLessonById(UUID LessonID) {
        for (Lesson lesson : lessons) {
            if (lesson.getId().equals(LessonID)) {
                return lesson;
            }
        }
        return null;
    }

    /**
     * Adds a lesson to the lesson list.
     *
     * @param lesson the lesson to add
     */
    public void addLesson(Lesson lesson) {
        this.lessons.add(lesson);
    }

    /**
     * Removes a lesson from the lesson list.
     *
     * @param lesson the lesson to remove
     */
    public void removeLesson(Lesson lesson) {
        this.lessons.remove(lesson);
    }

    /**
     * Restarts the lesson list by setting the current lesson index to 0.
     */
    public void restartLessons() {
        this.currentLessonIndex = 0;
    }

    /**
     * Returns the current lesson based on the current lesson index.
     *
     * @return the current lesson, or null if the list is empty or the index is
     * out of bounds
     */
    public Lesson getCurrentLesson() {
        if (!lessons.isEmpty() && currentLessonIndex < lessons.size()) {
            return lessons.get(currentLessonIndex);
        }
        return null;
    }

    /**
     * Returns the lesson at the specified index.
     *
     * @param index the index of the lesson to return
     * @return the lesson at the specified index, or null if the index is out of
     * bounds
     */
    public Lesson getLesson(int index) {
        if (index >= 0 && index < lessons.size()) {
            return lessons.get(index);
        }
        return null;
    }

    /**
     * Returns the list of all lessons.
     *
     * @return the list of lessons
     */
    public ArrayList<Lesson> getLessons() {
        return lessons;
    }

    /**
     * Returns the next lesson following the specified current lesson.
     *
     * @param currentLesson the current lesson
     * @return the next lesson, or null if the current lesson is the last one or
     * not found
     */
    public Lesson nextLesson(Lesson currentLesson) {
        int index = lessons.indexOf(currentLesson);
        if (index != -1 && index < lessons.size() - 1) {
            return lessons.get(index + 1);
        }
        return null;
    }

    /**
     * Goes to the next lesson, following the specified current lesson.
     *
     * @return true if successfully went to next lesson, false if it was the
     * last lesson
     */
    public boolean goToNextLesson() {
        int index = lessons.indexOf(currentLessonIndex);
        if (index != -1 && index < lessons.size() - 1) {
            currentLessonIndex++;
            return true;
        }
        return false;
    }

    /**
     * Returns the number of lessons in the list.
     *
     * @return the lesson count
     */
    public int getLessonCount() {
        return lessons.size();
    }

    public int getCurrentLessonIndex() {
        return currentLessonIndex;
    }

    /**
     * Displays all lessons in the list. This method prints each lesson to the
     * console.
     */
    public void displayLessons() {
        for (Lesson lesson : lessons) {
            System.out.println(lesson.toString());
        }
    }
}
