import java.util.*;

/**
 * ==========================================================
 * CLASS - BookMyStayApp
 * ==========================================================
 * Combined Service for Room Allocation and Add-On Selection.
 * @version 7.0
 */
public class BookMyStayApp {

    // --- SUPPORTING MODELS ---

    static class Reservation {
        String id;
        String guestName;
        String roomType;

        public Reservation(String id, String guestName, String roomType) {
            this.id = id;
            this.guestName = guestName;
            this.roomType = roomType;
        }
    }

    static class RoomInventory {
        Map<String, Integer> availableRooms = new HashMap<>();

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

    static class Service {
        private String serviceName;
        private double cost;

        public Service(String serviceName, double cost) {
            this.serviceName = serviceName;
            this.cost = cost;
        }

        public String getServiceName() { return serviceName; }
        public double getCost() { return cost; }
    }

    // --- CORE FIELDS ---

    private Set<String> allocatedRoomIds = new HashSet<>();
    private Map<String, Set<String>> assignedRoomsByType = new HashMap<>();
    private Map<String, List<Service>> servicesByReservation = new HashMap<>();

    // --- ROOM ALLOCATION LOGIC ---

    public void allocateRoom(Reservation reservation, RoomInventory inventory) {
        if (inventory.hasRooms(reservation.roomType)) {
            String roomId = generateRoomId(reservation.roomType);
            allocatedRoomIds.add(roomId);
            assignedRoomsByType.computeIfAbsent(reservation.roomType, k -> new HashSet<>()).add(roomId);
            inventory.decrement(reservation.roomType);
            System.out.println("[Room Allocation] Assigned " + roomId + " to " + reservation.guestName);
        } else {
            System.out.println("[Room Allocation] Failed: No rooms for " + reservation.roomType);
        }
    }

    private String generateRoomId(String roomType) {
        String prefix = roomType.substring(0, 3).toUpperCase();
        int count = assignedRoomsByType.getOrDefault(roomType, new HashSet<>()).size() + 1;
        return prefix + "-" + (100 + count);
    }

    // --- ADD-ON SERVICE LOGIC ---

    public void addAddOnService(String reservationId, Service service) {
        servicesByReservation.computeIfAbsent(reservationId, k -> new ArrayList<>()).add(service);
        System.out.println("[Add-On] Added " + service.getServiceName() + " to Reservation " + reservationId);
    }

    public double calculateTotalAddOns(String reservationId) {
        return servicesByReservation.getOrDefault(reservationId, new ArrayList<>())
                .stream().mapToDouble(Service::getCost).sum();
    }

    // --- MAIN EXECUTION ---

    public static void main(String[] args) {
        BookMyStayApp app = new BookMyStayApp();
        RoomInventory inventory = new RoomInventory();
        inventory.addInventory("Deluxe", 5);

        // 1. Create a Reservation
        Reservation res = new Reservation("RES-99", "Alice Smith", "Deluxe");

        // 2. Process Allocation (Use Case 6)
        app.allocateRoom(res, inventory);

        // 3. Process Add-Ons (Use Case 7)
        app.addAddOnService(res.id, new Service("Breakfast", 20.0));
        app.addAddOnService(res.id, new Service("Late Checkout", 15.0));

        // 4. Output Summary
        System.out.println("------------------------------------");
        System.out.println("Total Add-on Charges: $" + app.calculateTotalAddOns(res.id));
    }
}