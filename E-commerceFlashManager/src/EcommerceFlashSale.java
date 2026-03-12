import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * UseCase3LimitedEditionLaunch
 * Simulates a limited edition product launch with restricted stock.
 */

class LimitedEditionInventory {

    // productId -> stock count
    private HashMap<String, Integer> stock = new HashMap<>();

    // productId -> waiting list (FIFO)
    private HashMap<String, LinkedHashMap<Integer, Integer>> waitingList = new HashMap<>();

    // Add product
    public void addProduct(String productId, int quantity) {
        stock.put(productId, quantity);
        waitingList.put(productId, new LinkedHashMap<>());
    }

    // Check stock availability
    public int checkStock(String productId) {
        return stock.getOrDefault(productId, 0);
    }

    // Purchase product
    public synchronized void purchaseProduct(String productId, int userId) {

        int available = stock.getOrDefault(productId, 0);

        if (available > 0) {

            stock.put(productId, available - 1);

            System.out.println(
                    "purchaseProduct(\"" + productId + "\", userId=" + userId +
                            ") → Success, " + (available - 1) + " units remaining"
            );

        } else {

            LinkedHashMap<Integer, Integer> queue = waitingList.get(productId);

            int position = queue.size() + 1;

            queue.put(userId, position);

            System.out.println(
                    "purchaseProduct(\"" + productId + "\", userId=" + userId +
                            ") → Added to waiting list, position #" + position
            );
        }
    }
}

public class EcommerceFlashSale {

    public static void main(String[] args) {

        LimitedEditionInventory inventory = new LimitedEditionInventory();

        String product = "LIMITED_SNEAKER_X";

        // Limited stock for product launch
        inventory.addProduct(product, 3);

        System.out.println("===== Limited Edition Product Launch =====");

        System.out.println(
                "checkStock(\"" + product + "\") → " +
                        inventory.checkStock(product) + " units available\n"
        );

        // Simulated purchase attempts
        inventory.purchaseProduct(product, 701);
        inventory.purchaseProduct(product, 702);
        inventory.purchaseProduct(product, 703);

        // Stock finished
        inventory.purchaseProduct(product, 704);
        inventory.purchaseProduct(product, 705);
    }
}
