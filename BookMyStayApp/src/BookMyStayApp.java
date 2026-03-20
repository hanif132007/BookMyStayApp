import java.util.LinkedList;
import java.util.Queue;

// ==========================================
// HELPER CLASSES TO SUPPORT THE SIMULATION
// ==========================================

class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

class BookingRequestQueue {
    private final Queue<Reservation> requests = new LinkedList<>();

    public void addRequest(Reservation r) { requests.add(r); }
    public boolean isEmpty() { return requests.isEmpty(); }
    public Reservation getNextRequest() { return requests.poll(); }
}

class RoomInventory {
    // Starting counts to result in the requested "Remaining Inventory"
    int single = 5;
    int doubleRooms = 3;
    int suite = 2;

    public void printRemaining() {
        System.out.println("\nRemaining Inventory:");
        System.out.println("Single: " + single);
        System.out.println("Double: " + doubleRooms);
        System.out.println("Suite: " + suite);
    }
}

class RoomAllocationService {
    private int sCount = 1, dCount = 1, stCount = 1;

    public void allocateRoom(Reservation res, RoomInventory inventory) {
        String roomId = "";
        if (res.roomType.equalsIgnoreCase("Single") && inventory.single > 0) {
            roomId = "Single-" + (sCount++);
            inventory.single--;
        } else if (res.roomType.equalsIgnoreCase("Double") && inventory.doubleRooms > 0) {
            roomId = "Double-" + (dCount++);
            inventory.doubleRooms--;
        } else if (res.roomType.equalsIgnoreCase("Suite") && inventory.suite > 0) {
            roomId = "Suite-" + (stCount++);
            inventory.suite--;
        }

        if (!roomId.isEmpty()) {
            System.out.println("Booking confirmed for Guest: " + res.guestName + ", Room ID: " + roomId);
        }
    }
}

// ==========================================
// CORE CONCURRENT PROCESSOR
// ==========================================

class ConcurrentBookingProcessor implements Runnable {
    private final BookingRequestQueue bookingQueue;
    private final RoomInventory inventory;
    private final RoomAllocationService allocationService;

    public ConcurrentBookingProcessor(BookingRequestQueue q, RoomInventory i, RoomAllocationService s) {
        this.bookingQueue = q;
        this.inventory = i;
        this.allocationService = s;
    }

    @Override
    public void run() {
        while (true) {
            Reservation reservation = null;
            synchronized (bookingQueue) {
                if (!bookingQueue.isEmpty()) {
                    reservation = bookingQueue.getNextRequest();
                } else {
                    break;
                }
            }

            if (reservation != null) {
                synchronized (inventory) {
                    allocationService.allocateRoom(reservation, inventory);
                }
            }
        }
    }
}

// ==========================================
// MAIN APPLICATION ENTRY POINT
// ==========================================

public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Concurrent Booking Simulation");

        // 1. Setup Shared Resources
        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService allocationService = new RoomAllocationService();

        // 2. Add the specific guests from your requirement
        bookingQueue.addRequest(new Reservation("Abhi", "Single"));
        bookingQueue.addRequest(new Reservation("Vanmathi", "Double"));
        bookingQueue.addRequest(new Reservation("Kural", "Suite"));
        bookingQueue.addRequest(new Reservation("Subha", "Single"));

        // 3. Initialize Threads
        Thread t1 = new Thread(new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService));
        Thread t2 = new Thread(new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService));

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 4. Show final state
        inventory.printRemaining();
    }
}