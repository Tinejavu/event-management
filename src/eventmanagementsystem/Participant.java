package eventmanagementsystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Participant {
    Scanner scan = new Scanner(System.in);
    Config conf = new Config();
    
    public void participantConfig() {
        int option;
        do {
            try {
                System.out.println("\nParticipant Menu");
                System.out.println("1. Add Participant");
                System.out.println("2. View Participants");
                System.out.println("3. Edit Participant");
                System.out.println("4. Delete Participant");
                System.out.println("5. Exit");

                System.out.print("\nChoose an option: ");
                option = scan.nextInt();
                scan.nextLine();

                switch (option) {
                    case 1:
                        addParticipant();
                        break;
                    case 2:
                        viewParticipants();
                        break;
                    case 3:
                        editParticipant();
                        break;
                    case 4:
                        deleteParticipant();
                        break;
                    case 5:
                        System.out.println("Exiting Participant Menu.");
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
    
    private void addParticipant() {
        Event ev = new Event();
        
        ev.viewEvents();
        
        System.out.print("Event ID: ");
        int eventId = scan.nextInt();
        scan.nextLine();
        if (!conf.doesIDExist("events", eventId)) {
            System.out.println("Event ID doesn't exist. Cannot register participant.");
            return;
        }
        
        System.out.print("Full Name: ");
        String name = scan.nextLine();
        System.out.print("Email: ");
        String email = scan.nextLine();
        System.out.print("Contact Info: ");
        String contactInfo = scan.nextLine();
        
        String sql = "INSERT INTO participants (event_id, name, email, contact_info) VALUES (?, ?, ?, ?)";
        conf.addRecord(sql, eventId, name, email, contactInfo);
        
        int capacity = Integer.parseInt(conf.getDataFromID("events", eventId, "capacity")) - 1;
        String updCapacity = "UPDATE events SET capacity = ? WHERE id = ?";
        
        conf.updateRecord(updCapacity, capacity, eventId);
    }
    
    public void viewParticipants() {
        String query = "SELECT * FROM participants";
        String[] Headers = {"ID", "Event ID", "Name", "Email", "Contact Info"};
        String[] Columns = {"id", "event_id", "name", "email", "contact_info"};
        conf.viewRecords(query, Headers, Columns);
    }
    
    private void editParticipant() {
        System.out.print("\nEnter Participant ID to edit: ");
        int participantId = scan.nextInt();
        scan.nextLine();
        
        if (!conf.doesIDExist("participants", participantId)) {
            System.out.println("Participant ID doesn't exist.");
            return;
        }
        
        System.out.print("New Event ID: ");
        int eventId = scan.nextInt();
        scan.nextLine();
        if (!conf.doesIDExist("events", eventId)) {
            System.out.println("Event ID doesn't exist. Cannot update participant.");
            return;
        }
        
        System.out.print("New Full Name: ");
        String name = scan.nextLine();
        System.out.print("New Email: ");
        String email = scan.nextLine();
        System.out.print("New Contact Info: ");
        String contactInfo = scan.nextLine();
        
        String sql = "UPDATE participants SET event_id = ?, name = ?, email = ?, contact_info = ? WHERE id = ?";
        conf.updateRecord(sql, eventId, name, email, contactInfo, participantId);
    }
    
    private void deleteParticipant() {
        System.out.print("\nEnter Participant ID to delete: ");
        int participantId = scan.nextInt();
        String sql = "DELETE FROM participants WHERE id = ?";
        conf.deleteRecord(sql, participantId);
    }
}

