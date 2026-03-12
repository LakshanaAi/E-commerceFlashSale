import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * UseCase1AmazonPrimeDay
 * Simulates Amazon Prime Day flash sale inventory handling.
 */

class PrimeDayInventory {

    // productId -> stock
    private HashMap<String, Integer> stock = new HashMap<>();

    // productId -> waiting list (FIFO)
    private HashMap<String, LinkedHashMap<Integer, Integer>> waitingList = new HashMap<>();

    // Add product
    public void addProduct(String productId, int quantity) {
        stock.put(productId, quantity);
        waitingList.put(productId, new LinkedHashMap<>());
    }

    // Check stock
    public int checkStock(String productId) {
        return stock.getOrDefault(productId, 0);
    }

    // Purchase item
    public synchronized void purchaseItem(String productId, int userId) {

        int currentStock = stock.getOrDefault(productId, 0);

        if (currentStock > 0) {

            stock.put(productId, currentStock - 1);

            System.out.println(
                    "purchaseItem(\"" + productId + "\", userId=" + userId +
                            ") → Success, " + (currentStock - 1) + " units remaining"
            );

        } else {

            LinkedHashMap<Integer, Integer> queue = waitingList.get(productId);
            int position = queue.size() + 1;
            queue.put(userId, position);

            System.out.println(
                    "purchaseItem(\"" + productId + "\", userId=" + userId +
                            ") → Added to waiting list, position #" + position
            );
        }
    }
}

public class EcommerceFlashSale {

    public static void main(String[] args) {

        PrimeDayInventory inventory = new PrimeDayInventory();

        String product = "IPHONE15_256GB";

        // Initial stock for Prime Day
        inventory.addProduct(product, 5);

        System.out.println("===== Amazon Prime Day Flash Sale =====");

        System.out.println(
                "checkStock(\"" + product + "\") → " +
                        inventory.checkStock(product) + " units available\n"
        );

        // Simulated customers purchasing
        inventory.purchaseItem(product, 1001);
        inventory.purchaseItem(product, 1002);
        inventory.purchaseItem(product, 1003);
        inventory.purchaseItem(product, 1004);
        inventory.purchaseItem(product, 1005);

        // Stock finished
        inventory.purchaseItem(product, 1006);
        inventory.purchaseItem(product, 1007);
    }
}
