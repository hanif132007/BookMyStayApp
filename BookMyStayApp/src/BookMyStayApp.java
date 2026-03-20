import java.util.*;

/**
 * CLASS - CancellationService
 * Use Case 10: Booking Cancellation & Inventory Rollback
 */
class CancellationService {
    private Stack<String> releasedRoomIds = new Stack<>();
    private Map<String, String> reservationRoomTypeMap = new HashMap<>();

    public void registerBooking(String reservationId, String roomType) {
        reservationRoomTypeMap.put(reservationId, roomType);
    }

    public void cancelBooking(String reservationId, RoomInventory inventory) {
        System.out.println("Booking Cancellation");

        if (reservationRoomTypeMap.containsKey(reservationId)) {
            String roomType = reservationRoomTypeMap.get(reservationId);

            // Perform the rollback logic
            inventory.restoreRoom(roomType);
            releasedRoomIds.push(reservationId);
            reservationRoomTypeMap.remove(reservationId);

            System.out.println("Booking cancelled successfully. Inventory restored for room type: " + roomType);
        }
    }

    public void showRollbackHistory() {
        System.out.println("\nRollback History (Most Recent First):");
        // Iterating backwards through the stack to show most recent first
        for (int i = releasedRoomIds.size() - 1; i >= 0; i--) {
            System.out.println("Released Reservation ID: " + releasedRoomIds.get(i));
        }
    }
}

class RoomInventory {
    private int singleRoomCount = 5; // Starting count

    public void restoreRoom(String roomType) {
        if (roomType.equals("Single")) {
            singleRoomCount++;
        }
    }

    public void displayStatus() {
        System.out.println("\nUpdated Single Room Availability: " + singleRoomCount);
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        CancellationService service = new CancellationService();
        RoomInventory inventory = new RoomInventory();

        // Setup the data to match your example
        service.registerBooking("Single-1", "Single");

        // Execution
        service.cancelBooking("Single-1", inventory);
        service.showRollbackHistory();
        inventory.displayStatus();
    }
}