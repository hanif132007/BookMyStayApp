/**
 * =============================================================================
 * ABSTRACT CLASS - Room
 * =============================================================================
 * @version 2.1
 */
abstract class Room {
    /** Number of beds available in the room. */
    protected int numberOfBeds;

    /** Total size of the room in square feet. */
    protected int squareFeet;

    /** Price charged per night for this room type. */
    protected double pricePerNight;

    /**
     * Constructor used by child classes to initialize common room attributes.
     */
    public Room(int numberOfBeds, int squareFeet, double pricePerNight) {
        this.numberOfBeds = numberOfBeds;
        this.squareFeet = squareFeet;
        this.pricePerNight = pricePerNight;
    }

    /** Displays room details. */
    public void displayRoomDetails() {
        System.out.println("Beds: " + numberOfBeds +
                " | Size: " + squareFeet + " sq ft" +
                " | Price: $" + pricePerNight);
    }
}

/**
 * CLASS - SingleRoom
 */
class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 250, 1500.0);
    }
}

/**
 * CLASS - DoubleRoom
 */
class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 400, 2500.0);
    }
}

/**
 * CLASS - SuiteRoom
 */
class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, 750, 5000.0);
    }
}

/**
 * =============================================================================
 * MAIN CLASS - BookMyStayApp
 * =============================================================================
 * Use Case 2: Basic Room Types & Static Availability
 * Description: Demonstrates room initialization using domain models.
 * @version 2.1
 */
public class BookMyStayApp {

    /**
     * Application entry point.
     */
    public static void main(String[] args) {
        System.out.println("--- Initializing Room Types ---");

        // Creating instances of the specialized rooms
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Displaying details for each
        System.out.print("Single Room: ");
        single.displayRoomDetails();

        System.out.print("Double Room: ");
        doubleRoom.displayRoomDetails();

        System.out.print("Suite Room:  ");
        suite.displayRoomDetails();

        // Static availability demonstration (as mentioned in Use Case 2 description)
        int singleAvailability = 5;
        System.out.println("\nStatic Availability for Single Rooms: " + singleAvailability);
    }
}