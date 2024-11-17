package eventmanagementsystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class EventManagementSystem {
    static Config conf = new Config();
    
    static Scanner scan = new Scanner(System.in);
    static Event event = new Event();
    static Participant part = new Participant();
    
    public static void main(String[] args) {
        int choice;
        
        do {
            try {
                System.out.println("\nEvent Management System\n");
                System.out.println("1. Events");
                System.out.println("2. Participants");
                System.out.println("3. Reports");
                System.out.println("4. Exit");
                
                System.out.print("\nEnter Option: ");
                choice = scan.nextInt();
                scan.nextLine();
                System.out.println("");
                
                switch (choice) {
                    case 1:
                        event.eventConfig();
                        break;
                    case 2:
                        part.participantConfig();
                        break;
                    case 3:
                        generateReports();
                        break;
                    case 4:
                        System.out.println("Goodbye!!!");
                        break;
                    default:
                        System.out.println("Invalid Option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scan.nextLine();
                choice = -1;
            }
        } while (choice != 4);
    }
    
    static void generateReports() {
        System.out.println("\nEVENTS LIST");
        event.viewEvents();
        
        int eventId;
        do {
            System.out.print("\nEnter Event ID for detailed report: ");
            eventId = scan.nextInt();
            if (!conf.doesIDExist("events", eventId)) {
                System.out.println("Event ID doesn't exist. Try again.");
            }
        } while (!conf.doesIDExist("events", eventId));
        
        System.out.println("\n-----------------------------------------------");
        System.out.println("EVENT DETAILS");
        System.out.printf("Event ID     : %d%n", eventId);
        System.out.printf("Event Name   : %s%n", conf.getDataFromID("events", eventId, "name"));
        System.out.printf("Date         : %s%n", conf.getDataFromID("events", eventId, "date"));
        System.out.printf("Location     : %s%n", conf.getDataFromID("events", eventId, "location"));
        System.out.printf("Capacity     : %d%n", Integer.parseInt(conf.getDataFromID("events", eventId, "capacity")));
        System.out.println("-----------------------------------------------");
        
        if (conf.isTableEmpty("participants WHERE event_id = " + eventId)) {
            System.out.println("No participants registered for this event.");
        } else {
            System.out.println("Participants List: ");
            String sql = "SELECT id, name, email, contact_info FROM participants WHERE event_id = " + eventId;
            String[] Headers = {"Participant ID", "Name", "Email", "Contact Info"};
            String[] Columns = {"id", "name", "email", "contact_info"};
            conf.viewRecords(sql, Headers, Columns);
        }
        
        System.out.println("=================================================");
    }
}

