package com.queuedesk;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

// Build-T7 Req6: Demonstrates one-time migration from tickets.txt to tickets.json.
public class Main {

    public static void main(String[] args)
            throws IOException, InvalidTicketDataException {

        // Paths to the old pipe-delimited file and the new JSON file.
        Path oldFile = Path.of("tickets.txt");
        Path jsonFile = Path.of("tickets.json");

        // Create the JSON store that handles saving and loading tickets.
        TicketJsonStore jsonStore = new TicketJsonStore();

        List<Ticket> tickets;

        // If tickets.json already exists, use JSON from now on.
        if (Files.exists(jsonFile)) {

            System.out.println("Loading tickets from JSON...");

            tickets = jsonStore.load(jsonFile);

        } else {

            // First run: JSON does not exist, so migrate old ticket data.
            System.out.println("Migrating tickets.txt to tickets.json...");

            // T4 loader reads the old pipe-delimited records.
            List<String[]> records = TicketFileLoader.load(oldFile);

            // Convert the old records into actual Ticket objects.
            tickets = convertRecords(records);

            // Save the converted objects into the new JSON file.
            jsonStore.save(tickets, jsonFile);

            System.out.println("Migration completed!");
        }

        // Display tickets loaded from either source.
        System.out.println("\n========== LOADED TICKETS ==========");

        for (Ticket ticket : tickets) {
            System.out.println(ticket);
        }
    }

    // Converts old pipe-delimited records into Ticket subclass objects.
    private static List<Ticket> convertRecords(List<String[]> records)
            throws InvalidTicketDataException {

        // List that will contain the converted Ticket objects.
        List<Ticket> tickets = new ArrayList<>();

        // Process each record from the old file.
        for (String[] fields : records) {

            try {
                // fields[0] identifies the ticket type.
                String type = fields[0].toUpperCase();

                // Create the correct subclass based on the record type.
                switch (type) {

                    case "BUG":
                        tickets.add(new BugTicket(
                                fields[1],                  // title
                                fields[2],                  // description
                                fields[3],                  // requester
                                Priority.valueOf(fields[4]), // priority
                                Severity.valueOf(fields[5]), // severity
                                fields[6]                   // steps
                        ));
                        break;

                    case "FEATURE":
                        tickets.add(new FeatureRequestTicket(
                                fields[1],                  // title
                                fields[2],                  // description
                                fields[3],                  // requester
                                Priority.valueOf(fields[4]), // priority
                                Integer.parseInt(fields[5]), // votes
                                fields[6]                   // business justification
                        ));
                        break;

                    case "ACCESS":
                        tickets.add(new AccessRequestTicket(
                                fields[1],                  // title
                                fields[2],                  // description
                                fields[3],                  // requester
                                Priority.valueOf(fields[4]), // priority
                                fields[5],                  // system name
                                fields[6]                   // access level
                        ));
                        break;

                    default:
                        // Reject unknown ticket types in the old file.
                        throw new InvalidTicketDataException(
                                "Unknown ticket type: " + type);
                }

            } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {

                // Handles invalid enum values, invalid votes, or missing fields.
                throw new InvalidTicketDataException(
                        "Invalid ticket record in old file: " + e.getMessage());
            }
        }

        // Return the converted ticket objects.
        return tickets;
    }
}