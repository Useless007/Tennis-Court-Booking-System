package notuse;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class Booking {
    String memberId;
    String memberName;
    String courtName;
    double price;
    String date;
    List<String> times;

    public Booking(String memberId, String memberName, String courtName, double price, String date,
            List<String> times) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.courtName = courtName;
        this.price = price;
        this.date = date;
        this.times = times;
    }
}

public class BookingUtils {

    public static void main(String[] args) {
        List<Booking> bookings = parseBookings("courtbooking.txt");
        if (checkForOverlappingBookings(bookings)) {
            System.out.println("Error: There are overlapping bookings.");
        } else {
            System.out.println("No overlapping bookings found.");
        }
    }

    public static List<Booking> parseBookings(String filePath) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            List<Booking> bookings = new ArrayList<>();
            String memberId = "";
            String memberName = "";
            String courtInfo = "";
            double price = 0.0;
            String date = "";
            List<String> times = new ArrayList<>();

            for (String line : lines) {
                if (line.startsWith("Member ID:")) {
                    memberId = line.split(": ")[1];
                } else if (line.startsWith("Member Name:")) {
                    memberName = line.split(": ")[1];
                } else if (line.startsWith("Court Info:")) {
                    courtInfo = line.split(": ")[1].split(", ")[0];
                } else if (line.startsWith("Price:")) {
                    price = Double.parseDouble(line.split(": ")[1]);
                } else if (line.startsWith("Date Booking:")) {
                    date = line.split(": ")[1];
                } else if (line.startsWith("Time:")) {
                    times = List.of(line.substring(line.indexOf("[") + 1, line.indexOf("]")).split(", "));
                    bookings.add(new Booking(memberId, memberName, courtInfo, price, date, times));
                    times = new ArrayList<>(); // Reset for next booking
                }
            }

            return bookings;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static boolean checkForOverlappingBookings(List<Booking> bookings) {
        for (int i = 0; i < bookings.size(); i++) {
            for (int j = i + 1; j < bookings.size(); j++) {
                Booking booking1 = bookings.get(i);
                Booking booking2 = bookings.get(j);
                if (booking1.courtName.equals(booking2.courtName) && booking1.date.equals(booking2.date)) {
                    for (String time1 : booking1.times) {
                        for (String time2 : booking2.times) {
                            if (time1.equals(time2)) {
                                return true; // Found overlapping time
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
