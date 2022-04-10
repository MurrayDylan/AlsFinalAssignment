import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.io.*;

import static java.time.temporal.ChronoUnit.MILLIS;

public class UserInterface {
    private static BusTimetable bt;
    private static String errorMessage;
    private static String userInput;
    private static Scanner scanner = new Scanner(System.in);
    private static String fileLocation;

    public static void main(String[] args) {
        initialise();
        mainMenu();
    }

    private static void initialise() {
        errorMessage = "";
        clearScreen();
        createBusTransfers();
    }

    private static String askUserForFileLocation() {
        while (true) {
            System.out.println("\nPlease enter the location of folder that contains files:");
            System.out.println("    -stop_times.txt\n    -stops.txt\n    -transfers.txt");
            fileLocation = scanner.nextLine();
            clearScreen();
            if (containsFiles("stops.txt") && containsFiles("transfers.txt") && containsFiles("stop_times.txt")) {
                clearScreen();
                System.out.println("\nLoading Files... please wait");
                System.out.println("");
                return fileLocation;
            } else {
                System.out.println("Error not all files found!\n");
            }
        }
    }

    private static void createBusTransfers() {
        bt = new BusTimetable(askUserForFileLocation());
    }

    private static boolean containsFiles(String fileName) {
        File file = new File(fileLocation + fileName);
        if (file.isFile()) {
            System.out.println(fileName + " Found");
            return true;
        } else {
            System.out.println(fileName + " Not Found");
            return false;
        }
    }

    private static void clearScreen() {
        //found on https://www.delftstack.com/howto/java/java-clear-console/
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void mainMenu() {
        errorMessage = "";
        boolean isFinished = false;
        while (!isFinished) {
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
            switch (userInput) {
                case "1":
                    searchForQuickestBetweenTwoStops();
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
                    errorMessage = "Invalid choice please enter a valid option number";
                    break;
            }
        }
    }

    private static void searchForQuickestBetweenTwoStops() {
        errorMessage = "";
        String userInputFirstStop = "";
        String userInputSecondStop = "";
        boolean isFinished = false;
        while (!isFinished) {
            boolean isVerifying = true;
            clearScreen();
            System.out.println("~~~~Shortest Path Between Two Stops~~~~");
            System.out.println("=================");
            while (isVerifying) {
                System.out.println("Enter first bus stop ID: ");
                userInputFirstStop = scanner.nextLine();
                if (bt.getAllStopsOrderedById().contains(userInputFirstStop)) {
                    isVerifying = false;
                } else {
                    System.out.println("Error: Bus stop 1 not found, please type in a valid Stop ID");
                }
            }
            isVerifying = true;
            while (isVerifying) {
                System.out.println("Enter second bus stop ID: ");
                userInputSecondStop = scanner.nextLine();
                if (bt.getAllStopsOrderedById().contains(userInputSecondStop)) {
                    isVerifying = false;
                } else {
                    System.out.println("Error: Bus stop 2 not found, please type in a valid Stop ID");
                }
            }
            LocalTime startTime = LocalTime.now();
            Stop from = bt.getAllStopsOrderedById().get(userInputFirstStop);
            Stop to = bt.getAllStopsOrderedById().get(userInputSecondStop);
            ArrayList<Stop> quickestRoute = bt.AStarSearch(from, to);
            LocalTime endTime = LocalTime.now();
            if (quickestRoute.isEmpty()) {
                System.out.println("No route found in " + MILLIS.between(startTime, endTime) + " milliseconds!");
            } else {
                System.out.println("\n");
                for (Stop s : quickestRoute) {
                    System.out.println(" - " + s.getStopId().toString() + " : " + s.getStopName() + ((s.getPathFindingEdge() != null) ? " (" + s.getPathFindingEdge().getWeight() + ")" : ""));
                }
                System.out.println("");
                System.out.println("Fastest route found in " + MILLIS.between(startTime, endTime) + " milliseconds!");
                System.out.println("Total stops on journey: " + quickestRoute.size());
                System.out.println("Total Cost is " + to.getActualCost());
            }
            System.out.println("");
            System.out.println("Do you wish to preform another search - (y/n):");
            userInput = scanner.nextLine();
            switch (userInput.toUpperCase(Locale.ROOT)) {
                case ("Y"):
                    break;
                default:
                    isFinished = true;
                    break;
            }
        }
    }

    private static void searchForStopByName() {
        errorMessage = "";
        boolean isFinished = false;
        while (!isFinished) {
            clearScreen();
            System.out.println("~~~~Bus Stop Search~~~~");
            System.out.println("=================");
            System.out.println("Enter bus stop name (Part there of): ");
            userInput = scanner.nextLine();
            System.out.println("");
            LocalTime startTime = LocalTime.now();
            ArrayList<Stop> stopsFound = bt.getAllStopsOrderedByName().like(userInput);
            stopsFound.forEach((s) -> {
                System.out.println(s.toString());
            });
            LocalTime endTime = LocalTime.now();
            System.out.println("Results Found: " + stopsFound.size() + " stops in " + MILLIS.between(startTime, endTime) + " millisecond(s)!");
            System.out.println("");
            System.out.println("Do you wish to preform another search - (y/n):");
            userInput = scanner.nextLine().toUpperCase(Locale.ROOT);
            switch (userInput) {
                case "Y":
                    break;
                default:
                    errorMessage = "";
                    isFinished = true;
                    break;
            }
        }
    }

    private static void searchAllTripsByArrivalTime() {
        errorMessage = "";
        boolean isFinished = false;
        while (!isFinished) {
            clearScreen();
            System.out.println("~~~~Arrival Time Search~~~~");
            System.out.println("================= \n");
            System.out.println("Enter arrival time (HH:MM:SS): ");
            userInput = scanner.nextLine();
            System.out.println("");
            LocalTime time = Utility.parseLocalTime(userInput);
            // should output error if the user does not type in a valid time, or one that lies outside of the 24 hours in the day
            if (time == null || time.toSecondOfDay() >= 86400) {
                errorMessage = "Invalid time entered, please enter a time between 00:00:00 and 23:59:59, in format HH:MM:SS";
                System.out.println(errorMessage + "\n");
            } else {
                errorMessage = "";
                LocalTime startTime = LocalTime.now();
                ArrayList<Trip> trips = bt.getTripsByArrivalTime(time);
                trips.forEach((t) -> {
                    System.out.println(t.toString());
                });
                LocalTime endTime = LocalTime.now();
                System.out.println("\nResults found: " + trips.size() + " in " + MILLIS.between(startTime, endTime) + " milliseconds!");
            }
            System.out.println("Do you wish to preform another search - (y/n):");
            userInput = scanner.nextLine().toUpperCase(Locale.ROOT);
            switch (userInput) {
                case "Y":
                    break;
                default:
                    errorMessage = "";
                    isFinished = true;
                    break;
            }
        }
    }
}


