package library;
import java.util.ArrayList;
import java.util.UUID;

/**
 * @author Madeleine McBride
 * Represents a badge that can be awarded to a user in a language learning application.
 */
public class Badge {
    private String name;
    private String description;
    private UUID id;
    public static ArrayList<Badge> badges = new ArrayList<>();

    /**
     * Constructs a Badge with the specified name and description.
     * Generates a unique identifier for the badge.
     *
     * @param name        the name of the badge
     * @param description the description of the badge
     */
    public Badge(String name, String description) {
        this.name = name;
        this.description = description;
        this.id = UUID.randomUUID(); // Generate a unique ID for the badge
    }

    /**
     * Adds a badge to the collection of badges.
     *
     * @param badge the badge to be added
     */
    public static void addBadge(Badge badge) {
        // Check for existing badge with the same UUID
        for (Badge existingBadge : badges) {
            if (existingBadge.getID().equals(badge.getID())) {
                return; // If a duplicate badge is found, do not add
            }
        }
        badges.add(badge); // Add if it's not a duplicate
    }

    /**
     * Returns the name of the badge.
     *
     * @return the name of the badge
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the description of the badge.
     *
     * @return the description of the badge
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the unique identifier of the badge.
     *
     * @return the UUID of the badge
     */
    public UUID getID() {
        return id;
    }

    public static Badge getBadgeByUUID(UUID id) {
        for (Badge badge : badges){
            if (badge.getID().equals(id))
                return badge;
        }
        return null;
    }

    /**
     * Displays the badge's name and description.
     */
    public void displayBadge() {
        System.out.print("Badge: " + this.name + " - " + this.description + "\n");
    }

    /**
     * Displays all badges in the collection.
     */
    public static void displayBadges() {
        for (Badge badge : badges) {
            badge.displayBadge();
        }
    }

    /*public static void main(String[] args) {
        Badge badge1 = new Badge("First Badge", "Awarded for completing the first lesson.");
        Badge badge2 = new Badge("Second Badge", "Awarded for completing the second lesson.");
        Badge badge3 = new Badge("Third Badge", "Awarded for completing the third lesson.");

        Badge.addBadge(badge1);
        Badge.addBadge(badge2);
        Badge.addBadge(badge3);

        Badge.displayBadges();
    }*/
}

