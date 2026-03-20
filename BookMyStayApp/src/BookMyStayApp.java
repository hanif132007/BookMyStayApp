import java.util.*;

/**
 * ==========================================================
 * CLASS - BookMyStayApp
 * ==========================================================
 * This class combines Room Allocation, Add-On Services,
 * and Booking History/Reporting to match the specified output.
 */
public class BookMyStayApp {

    // --- SUPPORTING MODELS ---

    static class Reservation {
        String guestName;
        String roomType;

        public Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }
    }

    static class RoomInventory {
        private Map<String, Integer> availableRooms = new HashMap<>();

        public void addInventory(String type, int count) {
            availableRooms.put(type, count);
        }

        public boolean hasRooms(String type) {
            return availableRooms.getOrDefault(type, 0) > 0;
        }

        public void decrement(String type) {
            availableRooms.put(type, availableRooms.get(type) - 1);
        }
    }

    // --- CORE FIELDS ---

    private List<Reservation> confirmedReservations = new ArrayList<>();
    private RoomInventory inventory = new RoomInventory();

    public BookMyStayApp() {
        // Initializing inventory as per standard hotel setup
        inventory.addInventory("Single", 10);
        inventory.addInventory("Double", 10);
        inventory.addInventory("Suite", 10);
    }

    /**
     * Allocates a room and adds the reservation to history.
     */
    public void allocateRoom(String name, String type) {
        if (inventory.hasRooms(type)) {
            Reservation res = new Reservation(name, type);
            confirmedReservations.add(res);
            inventory.decrement(type);
        }
    }

    /**
     * USE CASE 8: GENERATE REPORT
     * Produces the exact output format from the screenshots.
     */
    public void generateReport() {
        System.out.println("Booking History and Reporting\n");
        System.out.println("Booking History Report");

        for (Reservation res : confirmedReservations) {
            System.out.println("Guest: " + res.guestName + ", Room Type: " + res.roomType);
        }
    }

    // --- MAIN ENTRY POINT ---

    public static void main(String[] args) {
        BookMyStayApp app = new BookMyStayApp();

        // Adding the specific guests from the requirement images
        app.allocateRoom("Abhi", "Single");
        app.allocateRoom("Subha", "Double");
        app.allocateRoom("Vanmathi", "Suite");

        // Generate the output
        app.generateReport();
    }
}