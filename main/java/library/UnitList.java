package library;
import java.util.ArrayList;
import java.util.UUID;

/**
 * @author Madeleine McBride
 * Represents a list of units.
 */
public class UnitList {

    private ArrayList<Unit> units;
    private int currentUnitIndex;

    /**
     * Constructs a unit list given an arraylist of units.
     */
    public UnitList(ArrayList<Unit> units) {
        this.units = units;
        this.currentUnitIndex = 0;
    }

    /**
     * Returns a unit based on its unit number.
     *
     * @param unitNumber the number of the unit
     * @return the unit corresponding to the unit number
     */
    public Unit getUnit(int unitNumber) {
        for (Unit unit : units) {
            if (unit.getUnitNumber() == unitNumber) {
                return unit;
            }
        }
        return null;
    }

    /**
     * Returns the current unit
     *
     * @return the unit corresponding to the current unit
    **/
    public Unit getCurrentUnit() {
        return units.get(currentUnitIndex);
    }

    /**
     * Returns the next unit based on the current unit number.
     *
     * @param currentUnit the current unit
     * @return the next unit or null if there are no more units
     */
    public Unit nextUnit(Unit currentUnit) {
        int index = units.indexOf(currentUnit);
        if (index != -1 && index < units.size() - 1) {
            return units.get(index + 1);
        }
        return null;
    }

    /**
     * Returns the next unit based on the current unit number.
     *
     * @return the true if it went to next unit, false if there are no more units left
     */
    public boolean goToNextUnit() {
        
        if (currentUnitIndex < units.size() - 1) {
            currentUnitIndex++;
            return true;
        }
        return false;
    }

    /**
     * Get the unit by id number
     * @param UnitId The id number of unit
     * @return the unit that matches the given id number
     */
    public Unit getUnitById(UUID UnitId) {
        for (Unit unit : units)
            if (unit.getId().equals(UnitId))
                return unit;
        return null;
    }

    /**
     * Get the unit's number by the id number
     * @param UnitId The id number of unit
     * @return the unit number that matches the given id number
     */
    public int getUnitNumberById(UUID UnitId) {
        for (int i = 0; i<units.size(); i++) {
            if(units.get(i).getId().equals(UnitId))
                return i;
        }
        return -1;
    }

    /**
     * Restarts the current unit by resetting it to the first lesson.
     *
     * @param currentUnit the current unit
     * @return the current unit restarted
     */
    public Unit restartUnit(Unit currentUnit) {
        currentUnit.restartUnit();
        return currentUnit;
    }

    /**
     * Returns the list of units.
     *
     * @return the list of units
     */
    public ArrayList<Unit> getUnits() {
        return units;
    }

    /**
     * Adds a unit to the unit list.
     *
     * @param unit the unit to be added
     */
    public void addUnit(Unit unit) {
        units.add(unit);
    }

    /**
     * Removes a unit from the unit list.
     *
     * @param unit the unit to be removed
     */
    public void removeUnit(Unit unit) {
        units.remove(unit);
    }
}
