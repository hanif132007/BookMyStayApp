import java.util.HashMap;
import java.util.Map;

/**
 * =============================================================================
 * CLASS - RoomInventory
 * =============================================================================
 * Use Case 3: Centralized Room Inventory Management
 * * Description:
 * This class acts as the single source of truth for room availability
 * in the hotel.
 * * Room pricing and characteristics are obtained from Room objects,
 * not duplicated here.
 * * This avoids multiple sources of truth and keeps responsibilities
 * clearly separated.
 * * @version 3.1
 */
class RoomInventory {

    /**
     * Stores available room count for each room type.
     * Key   -> Room type name
     * Value -> Available room count
     */
    private Map<String, Integer> roomAvailability;

    /**
     * Constructor initializes the inventory with default availability values.
     */
    public RoomInventory() {
        roomAvailability = new HashMap<>();
        initializeInventory();
    }

    /**
     * Initializes room availability data.
     * This method centralizes inventory setup instead of using scattered variables.
     */
    private void initializeInventory() {
        // Example default values
        roomAvailability.put("Standard", 10);
        roomAvailability.put("Deluxe", 5);
        roomAvailability.put("Suite", 2);
    }

    /**
     * Returns the current availability map.
     * @return map of room type to available count
     */
    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }

    /**
     * Updates availability for a specific room type.
     * @param roomType the room type to update
     * @param count new availability count
     */
    public void updateAvailability(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }
}

/**
 * =============================================================================
 * MAIN CLASS - BookMyStayApp
 * =============================================================================
 * Use Case 3: Centralized Room Inventory Management
 * * Description:
 * This class demonstrates how room availability is managed using a
 * centralized inventory.
 * * Room objects are used to retrieve pricing and room characteristics.
 * No booking or search logic is introduced here.
 * * @version 3.1
 */
public class BookMyStayApp {

    /**
     * Application entry point.
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        // Instantiate the centralized inventory
        RoomInventory inventory = new RoomInventory();

        System.out.println("--- Initial Room Inventory ---");
        inventory.getRoomAvailability().forEach((type, count) ->
                System.out.println(type + ": " + count + " rooms available"));

        // Demonstrate an update
        System.out.println("\nUpdating Deluxe room inventory...");
        inventory.updateAvailability("Deluxe", 4);

        System.out.println("Current Deluxe Count: " + inventory.getRoomAvailability().get("Deluxe"));
    }
}
