import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ServerRunnable implements Runnable {

    private Socket socket;
    private Connection connection = Database.connectDb();
    private String query;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private ObjectOutputStream oos;

    public ServerRunnable(Socket sk) {
        socket = sk;
    }

    @Override
    public void run() {
        try (OutputStream outputStream = socket.getOutputStream();
             InputStream inputStream = socket.getInputStream();
             PrintWriter printWriter = new PrintWriter(outputStream, true);
             Scanner scanner = new Scanner(inputStream)) {

            System.out.println("Successfully connected to client!");
            while (scanner.hasNextLine()) {
                String inputLine = scanner.nextLine();
                switch (inputLine) {
                    case "ADD" -> addStaff(scanner, printWriter);
                    case "LISTALL" -> getAllBookings();
                    case "LISTPT" -> listPT(scanner, printWriter);
                    case "LISTCLIENT" -> listClient(scanner, printWriter);
                    case "LISTDAY" -> listDay(scanner, printWriter);
                    case "UPDATE" -> updateBooking(scanner, printWriter);
                    case "DELETE" -> deleteBooking(scanner, printWriter);
                    default -> printWriter.println("Invalid command!");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addStaff(Scanner scanner, PrintWriter printWriter) {
        try {
            query = "INSERT INTO Booking (BookingID, BookingDate, StartTime, ClientID, SpecialismID, TrainerID) VALUES(?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, scanner.nextLine());
            preparedStatement.setString(2, scanner.nextLine());
            preparedStatement.setString(3, scanner.nextLine());
            preparedStatement.setString(4, scanner.nextLine());
            preparedStatement.setString(5, scanner.nextLine());
            preparedStatement.setString(6, scanner.nextLine());
            int a = preparedStatement.executeUpdate();

            printWriter.println(a > 0 ? "Details successfully added!" : "Error! Data not added!");
        } catch (Exception e) {
            printWriter.println(e);
        }
    }

    public ArrayList<Booking> getAllBookings() throws Exception {
        ArrayList<Booking> bookingArrayList = new ArrayList<>();
        query = "SELECT * FROM Booking";
        preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Booking booking = new Booking();
            booking.setBookingID(resultSet.getString("BookingID"));
            booking.setBookingDate(resultSet.getString("BookingDate"));
            booking.setStartTime(resultSet.getString("StartTime"));
            booking.setClientID(resultSet.getString("ClientID"));
            booking.setSpecialismID(resultSet.getString("SpecialismID"));
            booking.setTrainerID(resultSet.getString("TrainerID"));
            bookingArrayList.add(booking);
        }
        oos = new ObjectOutputStream(this.socket.getOutputStream());
        oos.writeObject(bookingArrayList);

        return bookingArrayList;
    }

    public void listPT(Scanner scanner, PrintWriter printWriter) {
        try {
            ArrayList<Booking> bookingArrayList = new ArrayList<>();
            query = "SELECT * FROM Booking WHERE TrainerID=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, scanner.nextLine());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Booking booking = new Booking();
                booking.setBookingID(resultSet.getString("BookingID"));
                booking.setBookingDate(resultSet.getString("BookingDate"));
                booking.setStartTime(resultSet.getString("StartTime"));
                booking.setClientID(resultSet.getString("ClientID"));
                booking.setSpecialismID(resultSet.getString("SpecialismID"));
                booking.setTrainerID(resultSet.getString("TrainerID"));
                bookingArrayList.add(booking);
            }
            oos = new ObjectOutputStream(this.socket.getOutputStream());
            oos.writeObject(bookingArrayList);
        } catch (Exception ex) {
            printWriter.println(ex);
        }
    }

    public void listClient(Scanner scanner, PrintWriter printWriter) {
        try {
            ArrayList<Booking> bookingArrayList = new ArrayList<>();
            query = "SELECT * FROM Booking WHERE ClientID=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, scanner.nextLine());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Booking booking = new Booking();
                booking.setBookingID(resultSet.getString("BookingID"));
                booking.setBookingDate(resultSet.getString("BookingDate"));
                booking.setStartTime(resultSet.getString("StartTime"));
                booking.setClientID(resultSet.getString("ClientID"));
                booking.setSpecialismID(resultSet.getString("SpecialismID"));
                booking.setTrainerID(resultSet.getString("TrainerID"));
                bookingArrayList.add(booking);
            }
            oos = new ObjectOutputStream(this.socket.getOutputStream());
            oos.writeObject(bookingArrayList);
        } catch (Exception ex) {
            printWriter.println(ex);
        }
    }

    public void listDay(Scanner scanner, PrintWriter printWriter) {
        try {
            ArrayList<Booking> bookingArrayList = new ArrayList<>();
            query = "SELECT * FROM Booking WHERE BookingDate=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, scanner.nextLine());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Booking booking = new Booking();
                booking.setBookingID(resultSet.getString("BookingID"));
                booking.setBookingDate(resultSet.getString("BookingDate"));
                booking.setStartTime(resultSet.getString("StartTime"));
                booking.setClientID(resultSet.getString("ClientID"));
                booking.setSpecialismID(resultSet.getString("SpecialismID"));
                booking.setTrainerID(resultSet.getString("TrainerID"));
                bookingArrayList.add(booking);
            }
            oos = new ObjectOutputStream(this.socket.getOutputStream());
            oos.writeObject(bookingArrayList);
        } catch (Exception ex) {
            printWriter.println(ex);
        }
    }

    public void updateBooking(Scanner scanner, PrintWriter printWriter) {
        try {
            String bookingDetail = scanner.nextLine();
            query = "UPDATE Booking " + " SET " + bookingDetail + " = ?" + " WHERE BookingID=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, scanner.nextLine());
            preparedStatement.setString(2, scanner.nextLine());
            int a = preparedStatement.executeUpdate();

            printWriter.println(a > 0 ? "Details successfully updated!" : "Error! Please check details again.");
        } catch (Exception e) {
            printWriter.println(e);
        }
    }

    public void deleteBooking(Scanner scanner, PrintWriter printWriter) {
        try {
            query = "DELETE FROM Booking" + " WHERE BookingID = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, scanner.nextLine());
            int a = preparedStatement.executeUpdate();

            printWriter.println(a > 0 ? "Details successfully deleted!" : "Error! No such record found!");
        } catch (Exception e) {
            printWriter.println(e);
        }
    }

}
