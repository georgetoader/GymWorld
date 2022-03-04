import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Client {

    public static void COMMANDS() {
        System.out.println("\nOptions:\t\t\t\t\tCommand:"
                + "\nList all commands.  \t\tHELP"
                + "\nAdd booking.  \t\t\t\tADD <BookingID> <BookingDate> <StartTime> <ClientID> <SpecialismID> <TrainerID>"
                + "\nList all bookings  \t\t\tLISTALL"
                + "\nList personal trainer.  \tLISTPT <TrainerID>"
                + "\nList client bookings.  \t\tLISTCLIENT <ClientID>"
                + "\nList booking date.  \t\tLISTDAY <BookingDate>"
                + "\nUpdate booking.  \t\t\tUPDATE <BookingDetail> <NewValue> <BookingID>"
                + "\nDelete booking. \t\t\tDELETE <BookingID>"
                + "\nExit.  \t\t\t\t\t\tEXIT");
    }

    public static void main(String[] args) {
        String hostName = "localhost";
        int port = 5555;

        try (Socket socket = new Socket(hostName, port);
             InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream();
             PrintWriter printWriter = new PrintWriter(outputStream, true);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Successfully connected to port " + port);
            COMMANDS();

            while (true) {
                System.out.print(">> Option: ");
                String userInput = stdIn.readLine();
                String[] user = userInput.split("\\s+");
                switch (user[0]) {
                    case "HELP" -> COMMANDS();
                    case "ADD" -> ADD(user, printWriter, bufferedReader);
                    case "LISTALL" -> LISTALL(user, inputStream, printWriter);
                    case "LISTPT" -> LISTPT(user, printWriter, inputStream);
                    case "LISTCLIENT" -> LISTCLIENT(user, printWriter, inputStream);
                    case "LISTDAY" -> LISTDAY(user, printWriter, inputStream);
                    case "UPDATE" -> UPDATEBOOKING(user, printWriter, bufferedReader);
                    case "DELETE" -> DELETEBOOKING(user, printWriter, bufferedReader);
                    case "EXIT" -> {
                        System.out.println("Exiting...");
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid Command!");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void ADD(String[] args, PrintWriter printWriter, BufferedReader bufferedReader) throws Exception {
        if (args.length != 7) {
            System.err.println("Usage: ADD <BookingID> <BookingDate> <StartTime> <ClientID> <SpecialismID> <TrainerID>");
        } else {
            for (int i = 0; i < 7; i++) {
                printWriter.println(args[i]);
            }
            String message = bufferedReader.readLine();
            System.out.println(message);
        }
    }

    public static void LISTALL(String[] args, InputStream inputStream, PrintWriter printWriter) throws Exception {
        printWriter.println(args[0]);
        if (args.length != 1) {
            System.err.println("Usage: LISTALL");
        } else {
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            ArrayList<Booking> bookingArrayList = (ArrayList<Booking>) objectInputStream.readObject();
            for (Booking booking : bookingArrayList) {
                System.out.println("-------\nBookingID : " + booking.getBookingID()
                        + "\nBooking Date: " + booking.getBookingDate()
                        + "\nStart time: " + booking.getStartTime()
                        + "\nClientID: " + booking.getClientID()
                        + "\nSpecialismID: " + booking.getSpecialismID()
                        + "\nTrainerID: " + booking.getTrainerID());
            }
        }
    }

    public static void LISTPT(String[] args, PrintWriter printWriter, InputStream inputStream) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: LISTPT <TrainerID>");
        } else {
            printWriter.println(args[0]);
            printWriter.println(args[1]);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            ArrayList<Booking> bookingArrayList = (ArrayList<Booking>) objectInputStream.readObject();
            for(Booking booking: bookingArrayList) {
                System.out.println("-------\nBookingID : " + booking.getBookingID()
                        + "\nBooking Date: " + booking.getBookingDate()
                        + "\nStart time: " + booking.getStartTime()
                        + "\nClientID: " + booking.getClientID()
                        + "\nSpecialismID: " + booking.getSpecialismID()
                        + "\nTrainerID: " + booking.getTrainerID());
            }
        }
    }

    public static void LISTCLIENT(String[] args, PrintWriter printWriter, InputStream inputStream) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: LISTCLIENT <ClientID>");
        } else {
            printWriter.println(args[0]);
            printWriter.println(args[1]);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            ArrayList<Booking> bookingArrayList = (ArrayList<Booking>) objectInputStream.readObject();
            for(Booking booking: bookingArrayList) {
                System.out.println("-------\nBookingID : " + booking.getBookingID()
                        + "\nBooking Date: " + booking.getBookingDate()
                        + "\nStart time: " + booking.getStartTime()
                        + "\nClientID: " + booking.getClientID()
                        + "\nSpecialismID: " + booking.getSpecialismID()
                        + "\nTrainerID: " + booking.getTrainerID());
            }
        }
    }

    public static void LISTDAY(String[] args, PrintWriter printWriter, InputStream inputStream) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: LISTDAY <BookingDate>");
        } else {
            printWriter.println(args[0]);
            printWriter.println(args[1]);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            ArrayList<Booking> bookingArrayList = (ArrayList<Booking>) objectInputStream.readObject();
            for(Booking booking: bookingArrayList) {
                System.out.println("-------\nBookingID : " + booking.getBookingID()
                        + "\nBooking Date: " + booking.getBookingDate()
                        + "\nStart time: " + booking.getStartTime()
                        + "\nClientID: " + booking.getClientID()
                        + "\nSpecialismID: " + booking.getSpecialismID()
                        + "\nTrainerID: " + booking.getTrainerID());
            }
        }
    }

    public static void UPDATEBOOKING(String[] args, PrintWriter printWriter, BufferedReader bufferedReader) throws Exception {
        if (args.length != 4) {
            System.err.println("Usage: UPDATE <BookingDetail> <NewValue> <BookingID>");
        } else {
            for (int i = 0; i < 4; i++) {
                printWriter.println(args[i]);
            }
            String message = bufferedReader.readLine();
            System.out.println(message);
        }
    }

    public static void DELETEBOOKING(String[] args, PrintWriter printWriter, BufferedReader bufferedReader) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: DELETE <BookingID>");
        } else {
            printWriter.println(args[0]);
            printWriter.println(args[1]);
            String message = bufferedReader.readLine();
            System.out.println(message);
        }
    }
}
