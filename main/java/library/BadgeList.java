package library;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Singleton class that manages a collection of badges.
 * @author Madeleine McBride
 */
public class BadgeList {
    // Static instance of the BadgeList (eager initialization)
    private static BadgeList instance = new BadgeList();
    
    // List to store Badge objects
    private ArrayList<Badge> badges;

    // Private constructor to prevent external instantiation
    private BadgeList() {
        badges = new ArrayList<>();
    }

    /**
     * Returns the single instance of BadgeList.
     *
     * @return the single instance of BadgeList
     */
    public static BadgeList getInstance() {
        return instance;
    }

    /**
     * Adds a badge to the list.
     *
     * @param badge the badge to be added
     */
    public void addBadge(Badge badge) {
        // Check if the badge's UUID already exists in the list
        for (Badge existingBadge : badges) {
            if (existingBadge.getID().equals(badge.getID())) {
                return; // Do not add if a badge with the same UUID is already present
            }
        }
        badges.add(badge); // Add badge if it is not a duplicate
    }

    /**
     * Retrieves a badge by its UUID.
     *
     * @param id the UUID of the badge
     * @return the badge with the specified UUID, or null if not found
     */
    public Badge getBadgeByUUID(UUID id) {
        for (Badge badge : badges) {
            if (badge.getID().equals(id)) {
                return badge;
            }
        }
        return null;
    }

    /**
     * Displays all badges in the list.
     */
    public void displayBadges() {
        for (Badge badge : badges) {
            badge.displayBadge();
        }
    }

    /**
     * Returns the list of badges (optional if you want access to the list directly).
     *
     * @return the list of badges
     */
    public ArrayList<Badge> getBadges() {
        return badges;
    }
}

