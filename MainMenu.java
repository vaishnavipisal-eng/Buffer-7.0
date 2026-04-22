package aircraft_management;

import java.util.ArrayList;
import java.util.Scanner;

public class MainMenu {
    static ArrayList<Aircraft> fleet = FileHelper.loadFleet();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- Aircraft Maintenance System ---");
            System.out.println("1. Add Aircraft");
            System.out.println("2. Add Part to Aircraft");
            System.out.println("3. Search Aircraft");
            System.out.println("4. Show Urgent Repairs");
            System.out.println("5. Delete Aircraft");
            System.out.println("6. Save & Exit");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addAircraft();
                case 2 -> addPart();
                case 3 -> searchAircraft();
                case 4 -> showUrgentRepairs();
                case 5 -> deleteAircraft();
                case 6 -> {
                    FileHelper.saveFleet(fleet);
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    static void addAircraft() {
        System.out.print("Enter Aircraft ID: ");
        String id = sc.nextLine();
        System.out.print("Enter Model: ");
        String model = sc.nextLine();
        System.out.print("Enter Airline: ");
        String airline = sc.nextLine();
        System.out.print("Enter Flying Hours: ");
        int hours = sc.nextInt();
        System.out.print("Enter Fuel Level (0-100): ");
        int fuel = sc.nextInt();
        System.out.print("Enter Engine Temperature (°C): ");
        int engTemp = sc.nextInt();
        System.out.print("Enter Total Landings: ");
        int landings = sc.nextInt();
        System.out.print("Engine Fault? (1=Yes, 0=No): ");
        boolean fault = sc.nextInt() == 1;
        System.out.print("Days Since Last Maintenance: ");
        int days = sc.nextInt();
        sc.nextLine();

        Aircraft a = new Aircraft(id, model, airline, hours, fuel, engTemp, landings, fault, days);
        fleet.add(a);
        FileHelper.saveFleet(fleet);
        System.out.println("Aircraft added successfully!");	
    }

    static void addPart() {
        System.out.print("Enter Aircraft ID: ");
        String id = sc.nextLine();
        Aircraft found = null;
        for (Aircraft a : fleet) {
            if (a.id.equalsIgnoreCase(id)) {
                found = a;
                break;
            }
        }
        if (found == null) {
            System.out.println("Aircraft not found!");
            return;
        }
        System.out.print("Enter Part Name: ");
        String pname = sc.nextLine();
        System.out.print("Enter Next Repair Date (e.g. 2026-08-01): ");
        String date = sc.nextLine();
        found.addPart(pname, date);
        FileHelper.saveFleet(fleet);
        System.out.println("Part added successfully!");
    }

    static void searchAircraft() {
        System.out.print("Enter search term (ID/Model/Airline): ");
        String term = sc.nextLine().toLowerCase();
        boolean foundAny = false;
        for (Aircraft a : fleet) {
            if (a.id.toLowerCase().contains(term) ||
                a.model.toLowerCase().contains(term) ||
                a.airline.toLowerCase().contains(term)) {
                System.out.println(a);
                a.showParts();
                foundAny = true;
            }
        }
        if (!foundAny) System.out.println("No aircraft found.");
    }

    static void showUrgentRepairs() {
        System.out.println("\n--- Urgent Repairs ---");
        boolean any = false;
        for (Aircraft a : fleet) {
            for (Part p : a.parts) {
                if (p.status == 1) {
                    System.out.println("Aircraft: " + a.id + " | Part: " + p.name + " | Repair by: " + p.nextRepairDate);
                    any = true;
                }
            }
        }
        if (!any) System.out.println("No urgent repairs.");
    }

    static void deleteAircraft() {
        System.out.print("Enter Aircraft ID to delete: ");
        String id = sc.nextLine();
        boolean removed = fleet.removeIf(a -> a.id.equalsIgnoreCase(id));
        if (removed) {
            FileHelper.saveFleet(fleet);
            System.out.println("Aircraft deleted.");
        } else {
            System.out.println("Aircraft not found.");
        }
    }
}