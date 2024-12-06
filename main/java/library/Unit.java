package library;

import java.util.ArrayList;
import java.util.UUID;

/**
 * @author Madeleine McBride Represents a unit containing multiple lessons.
 */
public class Unit {

    private String unitName;
    private int unitNumber;
    private LessonList lessonList;
    private UUID id;
    private UnitList unitList;

    /**
     * Constructs a Unit with the specified name and number.
     *
     * @param unitName the name of the unit
     * @param unitNumber the number of the unit
     * @param unitList the list of units to which this unit belongs
     */
    public Unit(UUID id, String unitName, int unitNumber, LessonList lessonList, UnitList unitList) {
        this.id = id;
        this.unitName = unitName;
        this.unitNumber = unitNumber;
        this.lessonList = lessonList;
        this.unitList = unitList;
    }

    /**
     * Returns the ID of the unit.
     *
     * @return UUID
     */
    public UUID getId() {
        return id;
    }

    /**
     * Return the name of the unit
     *  
     * @return The name of the unit
     */
    public String getUnitName() {
        return unitName;
    }

    public LessonList getLessonList() {
        return lessonList;
    }

    /**
     * Returns the unit number.
     *
     * @return the unit number
     */
    public int getUnitNumber() {
        return unitNumber;
    }

    /**
     * Returns a string representation of the unit.
     *
     * @return a string representation of the unit
     */
    @Override
    public String toString() {
        return "Unit Name: " + unitName + " Unit Number: " + unitNumber;
    }

    /**
     * Moves to the next unit.
     *
     * @return the next unit or null if there are no more units
     */
    public Unit nextUnit() {
        return unitList.nextUnit(this);
    }

    /**
     * Restarts the current unit by resetting it to the first lesson.
     *
     * @return the current unit restarted
     */
    public Unit restartUnit() {
        lessonList.restartLessons();
        return this;
    }

    /**
     * Moves to the next lesson in the unit.
     *
     * @param currentLesson the current lesson
     * @return the next lesson
     */
    public Lesson nextLesson(Lesson currentLesson) {
        return lessonList.nextLesson(currentLesson);
    }

    /**
     * Adds a lesson to the unit.
     *
     * @param lesson the lesson to be added
     */
    public void addLesson(Lesson lesson) {
        lessonList.addLesson(lesson);
    }

    /**
     * Removes a lesson from the unit.
     *
     * @param lesson the lesson to be removed
     */
    public void removeLesson(Lesson lesson) {
        lessonList.removeLesson(lesson);
    }

    /**
     * Returns the list of lessons in the unit.
     *
     * @return the list of lessons
     */
    public ArrayList<Lesson> getLessons() {
        return lessonList.getLessons();
    }
}
