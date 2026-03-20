import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

// ==========================================
// 1. CUSTOM EXCEPTION
// ==========================================
/**
 * Use Case 9: Error Handling & Validation
 * This custom exception represents invalid booking scenarios.
 */
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// ==========================================
// 2. SUPPORTING DOMAIN CLASSES
// ==========================================
class RoomInventory {
    private List<String> availableRooms = List.of("Single", "Double", "Suite");

    public boolean isRoomAvailable(String roomType) {
        return availableRooms.contains(roomType);
    }
}

class BookingRequestQueue {
    public void addRequest(String name, String room) {
        System.out.println("Processing: Adding " + name + " (" + room + ") to the processing queue...");
    }
}

// ==========================================
// 3. RESERVATION VALIDATOR
// ==========================================
class ReservationValidator {
    /**
     * Validates booking input. Centralizing rules avoids duplication.
     * @throws InvalidBookingException if validation fails
     */
    public void validate(String guestName, String roomType, RoomInventory inventory)
            throws InvalidBookingException {

        // Rule 1: Name cannot be empty
        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        // Rule 2: Room type must exist in inventory
        if (!inventory.isRoomAvailable(roomType)) {
            throw new InvalidBookingException("Room type '" + roomType + "' is not available or does not exist.");
        }
    }
}

// ==========================================
// 4. MAIN APPLICATION CLASS
// ==========================================
public class BookMyStayApp {

    public static void main(String[] args) {
        // Display application header
        System.out.println("=== Booking Validation System ===");

        Scanner scanner = new Scanner(System.in);

        // Initialize required components
        RoomInventory inventory = new RoomInventory();
        ReservationValidator validator = new ReservationValidator();
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        try {
            // Collect User Input
            System.out.print("Enter Guest Name: ");
            String guestName = scanner.nextLine();

            System.out.print("Enter Room Type (Single/Double/Suite): ");
            String roomType = scanner.nextLine();

            // Perform Centralized Validation
            validator.validate(guestName, roomType, inventory);

            // Logic if validation passes
            bookingQueue.addRequest(guestName, roomType);
            System.out.println("Success: Your booking has been queued!");

        } catch (InvalidBookingException e) {
            // Handle domain-specific validation errors gracefully
            System.out.println("Booking failed: " + e.getMessage());

        } catch (Exception e) {
            // Catch-all for unexpected system errors
            System.out.println("A system error occurred: " + e.getMessage());

        } finally {
            // Resource cleanup
            scanner.close();
            System.out.println("Session closed.");
        }
    }
}