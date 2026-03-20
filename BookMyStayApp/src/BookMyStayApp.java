import java.util.HashMap;
import java.util.Map;

/**
 * --- BASE MODELS ---
 */
abstract class Room {
    protected int numberOfBeds;
    protected int squareFeet;
    protected double pricePerNight;

    public Room(int numberOfBeds, int squareFeet, double pricePerNight) {
        this.numberOfBeds = numberOfBeds;
        this.squareFeet = squareFeet;
        this.pricePerNight = pricePerNight;
    }

    public void displayRoomDetails() {
        System.out.println("Beds: " + numberOfBeds + " | Size: " + squareFeet + " sq ft | Price: $" + pricePerNight);
    }
}

class SingleRoom extends Room { public SingleRoom() { super(1, 250, 1500.0); } }
class DoubleRoom extends Room { public DoubleRoom() { super(2, 400, 2500.0); } }
class SuiteRoom  extends Room { public SuiteRoom()  { super(3, 750, 5000.0); } }

/**
 * --- INVENTORY MANAGEMENT ---
 */
class RoomInventory {
    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 1);
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }
}

/**
 * =============================================================================
 * SERVICE CLASS - RoomSearchService
 * =============================================================================
 * @version 4.0
 */
class RoomSearchService {
    /**
     * Displays available rooms along with their details and pricing.
     * This method performs read-only access to inventory and room data.
     */
    public void searchAvailableRooms(RoomInventory inventory, Room singleRoom, Room doubleRoom, Room suiteRoom) {
        Map<String, Integer> availability = inventory.getRoomAvailability();

        System.out.println("--- SEARCH RESULTS ---");

        // Check and display Single Room availability
        if (availability.get("Single") > 0) {
            System.out.print("Single Room (" + availability.get("Single") + " left): ");
            singleRoom.displayRoomDetails();
        }

        // Check and display Double Room availability
        if (availability.get("Double") > 0) {
            System.out.print("Double Room (" + availability.get("Double") + " left): ");
            doubleRoom.displayRoomDetails();
        }

        // Check and display Suite Room availability
        if (availability.get("Suite") > 0) {
            System.out.print("Suite Room  (" + availability.get("Suite") + " left): ");
            suiteRoom.displayRoomDetails();
        }
    }
}

/**
 * =============================================================================
 * MAIN CLASS - BookMyStayApp
 * =============================================================================
 * Use Case 4: Room Search & Availability Check
 * Description: Demonstrates read-only access for guests to view available rooms.
 * @version 4.0
 */
public class BookMyStayApp {

    public static void main(String[] args) {
        // 1. Initialize data
        RoomInventory inventory = new RoomInventory();
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // 2. Initialize service
        RoomSearchService searchService = new RoomSearchService();

        // 3. Perform search
        searchService.searchAvailableRooms(inventory, single, doubleRoom, suite);
    }
}