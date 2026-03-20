import java.util.LinkedList;
import java.util.Queue;

/**
 * =============================================================================
 * CLASS - Reservation
 * =============================================================================
 * @version 5.0
 */
class Reservation {
    /** Name of the guest making the booking. */
    private String guestName;
    /** Requested room type. */
    private String roomType;

    /**
     * Creates a new booking request.
     */
    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    /** @return guest name */
    public String getGuestName() { return guestName; }

    /** @return requested room type */
    public String getRoomType() { return roomType; }
}

/**
 * =============================================================================
 * CLASS - BookingRequestQueue
 * =============================================================================
 * Use Case 5: Booking Request (FIFO)
 * Description: Manages booking requests using a queue to ensure fair allocation.
 * @version 5.0
 */
class BookingRequestQueue {
    /** Queue that stores booking requests. */
    private Queue<Reservation> requestQueue;

    /** Initializes an empty booking queue. */
    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    /** Adds a booking request to the queue. */
    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
    }

    /** Retrieves and removes the next booking request from the queue. */
    public Reservation getNextRequest() {
        return requestQueue.poll();
    }

    /** Checks whether there are pending booking requests. */
    public boolean hasPendingRequests() {
        return !requestQueue.isEmpty();
    }
}

/**
 * =============================================================================
 * MAIN CLASS - BookMyStayApp
 * =============================================================================
 * Use Case 5: Booking Request (First-Come-First-Served)
 * Description: Demonstrates how booking requests are accepted and queued.
 * @version 5.0
 */
public class BookMyStayApp {

    public static void main(String[] args) {
        // Display application header
        System.out.println("--- Booking Request Queue ---");

        // Initialize booking queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Create booking requests
        Reservation r1 = new Reservation("Abhi", "Single");
        Reservation r2 = new Reservation("Subha", "Double");
        Reservation r3 = new Reservation("Vanmathi", "Suite");

        // Add requests to the queue
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Display queued booking requests in FIFO order
        System.out.println("Processing requests in order:");
        while (bookingQueue.hasPendingRequests()) {
            Reservation next = bookingQueue.getNextRequest();
            System.out.println("Processing: " + next.getGuestName() +
                    " for a " + next.getRoomType() + " room.");
        }

        System.out.println("\nAll pending requests have been processed.");
    }
}