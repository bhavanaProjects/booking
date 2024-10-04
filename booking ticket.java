import java.util.Scanner;

public class TrainReservation {
    static final int ROWS = 12;
    static final int COLUMNS = 7;
    static String[][] seats = new String[ROWS][COLUMNS];
    
    public static void main(String[] args) {
        // Initialize all seats to be available
        initializeSeats();

        Scanner scanner = new Scanner(System.in);
        boolean continueBooking = true;

        while (continueBooking) {
            System.out.println("Enter the number of seats to book (1 to 7): ");
            int seatsToBook = scanner.nextInt();

            if (seatsToBook < 1 || seatsToBook > 7) {
                System.out.println("Invalid number of seats. Please book between 1 and 7 seats.");
                continue;
            }

            if (!bookSeats(seatsToBook)) {
                System.out.println("Sorry, not enough seats available.");
                continueBooking = false;
            }

            displaySeats();

            System.out.println("Do you want to book more seats? (yes/no): ");
            String response = scanner.next();
            continueBooking = response.equalsIgnoreCase("yes");
        }
        
        scanner.close();
    }

    // Initialize seat arrangement
    private static void initializeSeats() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (i == ROWS - 1 && j >= 3) {
                    seats[i][j] = "-"; // The last row only has 3 seats
                } else {
                    seats[i][j] = "O"; // 'O' represents available seat
                }
            }
        }
    }

    // Book seats for the user
    private static boolean bookSeats(int numSeats) {
        // Try booking seats in the same row first
        for (int i = 0; i < ROWS; i++) {
            int availableSeats = 0;

            // Count available seats in the current row
            for (int j = 0; j < COLUMNS; j++) {
                if (seats[i][j].equals("O")) {
                    availableSeats++;
                }
            }

            // If the current row has enough seats, book them
            if (availableSeats >= numSeats) {
                int seatsBooked = 0;
                for (int j = 0; j < COLUMNS; j++) {
                    if (seats[i][j].equals("O") && seatsBooked < numSeats) {
                        seats[i][j] = "X"; // 'X' represents booked seat
                        seatsBooked++;
                    }
                }
                return true;
            }
        }

        // If no single row can accommodate, book nearby seats
        return bookNearbySeats(numSeats);
    }

    // Book nearby seats across rows
    private static boolean bookNearbySeats(int numSeats) {
        int seatsBooked = 0;
        for (int i = 0; i < ROWS && seatsBooked < numSeats; i++) {
            for (int j = 0; j < COLUMNS && seatsBooked < numSeats; j++) {
                if (seats[i][j].equals("O")) {
                    seats[i][j] = "X";
                    seatsBooked++;
                }
            }
        }

        return seatsBooked == numSeats;
    }

    // Display seat availability and status
    private static void displaySeats() {
        System.out.println("\nCurrent Seat Arrangement:");
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                System.out.print(seats[i][j] + " ");
            }
            System.out.println();
        }
    }
}
