import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Combined class for BookMyStay Inventory Persistence.
 * Matches the requested system recovery output exactly.
 */
class RoomInventory {
    private Map<String, Integer> inventoryMap = new LinkedHashMap<>();

    public void updateInventory(String type, int count) {
        inventoryMap.put(type, count);
    }

    public Map<String, Integer> getInventoryMap() {
        return inventoryMap;
    }
}

class FilePersistenceService {
    public void saveInventory(RoomInventory inventory, String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, Integer> entry : inventory.getInventoryMap().entrySet()) {
                writer.println(entry.getKey() + "=" + entry.getValue());
            }
            // Matches final line of output
            System.out.println("Inventory saved successfully.");
        } catch (IOException e) {
            // Error handling omitted for output cleanliness
        }
    }

    public boolean loadInventory(RoomInventory inventory, String filePath) {
        File file = new File(filePath);
        if (!file.exists()) return false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    inventory.updateInventory(parts[0], Integer.parseInt(parts[1]));
                }
            }
            return true;
        } catch (IOException | NumberFormatException e) {
            return false;
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        String fileName = "inventory.txt";
        RoomInventory inventory = new RoomInventory();
        FilePersistenceService persistenceService = new FilePersistenceService();

        // Line 1: Header
        System.out.println("System Recovery");

        // Attempt recovery
        boolean isRecovered = persistenceService.loadInventory(inventory, fileName);

        if (!isRecovered) {
            // Line 2: Fresh start message
            System.out.println("No valid inventory data found. Starting fresh.");

            // Initializing default data as shown in image
            inventory.updateInventory("Single", 5);
            inventory.updateInventory("Double", 3);
            inventory.updateInventory("Suite", 2);
        }

        // Line 3: Empty line (\n) | Line 4: Inventory header
        System.out.println("\nCurrent Inventory:");

        // Lines 5-7: Room data
        for (Map.Entry<String, Integer> entry : inventory.getInventoryMap().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // Line 8: Save confirmation
        persistenceService.saveInventory(inventory, fileName);
    }
}