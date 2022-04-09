import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class UserInterface {
    private static BusTimetable bt;
    private static String errorMessage;
    private static String userInput;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("");
        initialise();
        mainMenu();
    }

    private static void initialise() {
        errorMessage = "";
        clearScreen();
        System.out.println("Loading Files... please wait");
        System.out.println("");
        createBusTransfers();
    }

    private static String askUserForFileLocation() {
        //TODO
        return "/Users/dylanmurray/Downloads/input files/";
    }

    private static void createBusTransfers() {
        bt = new BusTimetable(askUserForFileLocation());
    }

    private static void clearScreen() {
        //found on https://www.delftstack.com/howto/java/java-clear-console/
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void mainMenu() {
        errorMessage = "";
        boolean isFinished = false;
        while(!isFinished) {
            clearScreen();
            System.out.println("~~~~Main Menu~~~~");
            System.out.println("=================");
            System.out.println("Option 1: Shortest paths between two bus stops");
            System.out.println("Option 2: Search for bus stop by name");
            System.out.println("Option 3: Search all trips by arrival time");
            System.out.println("Option 4: Exit");
            System.out.println("");
            System.out.println(errorMessage);
            System.out.println("");
            System.out.println("Enter Option Number:");
            userInput = scanner.nextLine();
            errorMessage = "";
            switch(userInput) {
                case "1":
                    break;
                case "2":
                    searchForStopByName();
                    break;
                case "3":
                    searchAllTripsByArrivalTime();
                    break;
                case "4":
                    isFinished = true;
                    break;
                default:
                    errorMessage = "Invalid choice";
                    break;
            }
        }
    }

    private static void searchForStopByName() {
        errorMessage = "";
        boolean isFinished = false;
        while(!isFinished) {
            clearScreen();
            System.out.println("~~~~Bus Stop Search~~~~");
            System.out.println("=================");
            System.out.println("Enter bus stop name (Part there of): ");
            userInput = scanner.nextLine();
            System.out.println("");
            ArrayList<Stop> stopsFound = bt.getAllStopsOrderedByName().like(userInput);
            if (stopsFound.size() == 0) {
                System.out.println("No stops found");
            }
            else {
                stopsFound.forEach((s) -> {System.out.println(s.toString());});
            }
            System.out.println("");
            System.out.println("Do you wish to preform another search - (y/n):");
            userInput = scanner.nextLine().toUpperCase(Locale.ROOT);
            switch(userInput) {
                case "Y":
                    break;
                default:
                    isFinished = true;
                    break;
            }
        }
    }

    private static void searchAllTripsByArrivalTime() {
        errorMessage = "";
        boolean isFinished = false;
        while(!isFinished) {
            clearScreen();
            System.out.println("~~~~Arrival Time Search~~~~");
            System.out.println("=================");
            System.out.println("");
            System.out.println(errorMessage);
            System.out.println("");
            System.out.println("Enter arrival time (HH:MM:SS): ");
            userInput = scanner.nextLine();
            System.out.println("");
            LocalTime time = Utility.parseLocalTime(userInput);
            if(time == null) {
                errorMessage = "Invalid time entered";
            }
            else {
                errorMessage = "";
                bt.getTripsByArrivalTime(time).forEach((t) -> {
                    System.out.println(t.toString());
                } );
            }
        }
    }
}


