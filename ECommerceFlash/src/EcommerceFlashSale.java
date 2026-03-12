import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * UseCase2ConcertTicketBooking
 * Simulates a concert ticket booking system with limited seats.
 */

class ConcertInventory {

    // concertId -> ticket count
    private HashMap<String, Integer> tickets = new HashMap<>();

    // concertId -> waiting list (FIFO)
    private HashMap<String, LinkedHashMap<Integer, Integer>> waitingList = new HashMap<>();

    // Add concert tickets
    public void addConcert(String concertId, int seats) {
        tickets.put(concertId, seats);
        waitingList.put(concertId, new LinkedHashMap<>());
    }

    // Check available tickets
    public int checkTickets(String concertId) {
        return tickets.getOrDefault(concertId, 0);
    }

    // Book ticket
    public synchronized void bookTicket(String concertId, int userId) {

        int available = tickets.getOrDefault(concertId, 0);

        if (available > 0) {

            tickets.put(concertId, available - 1);

            System.out.println(
                    "bookTicket(\"" + concertId + "\", userId=" + userId +
                            ") → Success, " + (available - 1) + " tickets remaining"
            );

        } else {

            LinkedHashMap<Integer, Integer> queue = waitingList.get(concertId);

            int position = queue.size() + 1;

            queue.put(userId, position);

            System.out.println(
                    "bookTicket(\"" + concertId + "\", userId=" + userId +
                            ") → Added to waiting list, position #" + position
            );
        }
    }
}

public class EcommerceFlashSale {

    public static void main(String[] args) {

        ConcertInventory inventory = new ConcertInventory();

        String concert = "AR_RAHMAN_LIVE";

        // Initial ticket count
        inventory.addConcert(concert, 3);

        System.out.println("===== Concert Ticket Booking System =====");

        System.out.println(
                "checkTickets(\"" + concert + "\") → " +
                        inventory.checkTickets(concert) + " tickets available\n"
        );

        // Simulated booking requests
        inventory.bookTicket(concert, 501);
        inventory.bookTicket(concert, 502);
        inventory.bookTicket(concert, 503);

        // Tickets sold out
        inventory.bookTicket(concert, 504);
        inventory.bookTicket(concert, 505);
    }
}
