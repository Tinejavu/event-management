package eventmanagementsystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Event {
    Scanner scan = new Scanner(System.in);
    Config conf = new Config();
    
    public void eventConfig() {
        int option;
        do {
            try {
                System.out.println("\n\t--- Event Menu ---");
                System.out.println("1. Add Event");
                System.out.println("2. View Events");
                System.out.println("3. Edit Event");
                System.out.println("4. Delete Event");
                System.out.println("5. Exit");

                System.out.print("\nChoose an option: ");
                option = scan.nextInt();
                scan.nextLine();

                switch (option) {
                    case 1:
                        addEvent();
                        break;
                    case 2:
                        viewEvents();
                        break;
                    case 3:
                        editEvent();
                        break;
                    case 4:
                        deleteEvent();
                        break;
                    case 5:
                        System.out.println("Exiting Event Menu.");
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scan.nextLine();
                option = -1;
            }
        } while (option != 5);
    }
    
    private void addEvent() {
        System.out.println("\n--- ADD NEW EVENT ---");
        System.out.print("Event Name: ");
        String name = scan.nextLine();
        System.out.print("Date (YYYY-MM-DD): ");
        String date = scan.nextLine();
        System.out.print("Location: ");
        String location = scan.nextLine();
        System.out.print("Capacity: ");
        int capacity = scan.nextInt();
        scan.nextLine();
        
        String sql = "INSERT INTO events (name, date, location, capacity) VALUES (?, ?, ?, ?)";
        conf.addRecord(sql, name, date, location, capacity);
    }
    
    public void viewEvents() {
        String query = "SELECT * FROM events";
        String[] Headers = {"ID", "Name", "Date", "Location", "Capacity"};
        String[] Columns = {"id", "name", "date", "location", "capacity"};
        conf.viewRecords(query, Headers, Columns);
    }
    
    private void editEvent() {
        System.out.print("\nEnter Event ID to edit: ");
        int eventId = scan.nextInt();
        scan.nextLine();
        
        if (!conf.doesIDExist("events", eventId)) {
            System.out.println("Event ID doesn't exist.");
            return;
        }
        
        System.out.print("New Event Name: ");
        String name = scan.nextLine();
        System.out.print("New Date (YYYY-MM-DD): ");
        String date = scan.nextLine();
        System.out.print("New Location: ");
        String location = scan.nextLine();
        System.out.print("New Capacity: ");
        int capacity = scan.nextInt();
        
        String sql = "UPDATE events SET name = ?, date = ?, location = ?, capacity = ? WHERE id = ?";
        conf.updateRecord(sql, name, date, location, capacity, eventId);
    }
    
    private void deleteEvent() {
        System.out.print("\nEnter Event ID to delete: ");
        int eventId = scan.nextInt();
        String sql = "DELETE FROM events WHERE id = ?";
        conf.deleteRecord(sql, eventId);
    }
}

