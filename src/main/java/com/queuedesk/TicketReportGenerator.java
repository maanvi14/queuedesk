package com.queuedesk;
import java.io.BufferedWriter; //used to write text into a file efficiently.
import java.io.IOException; //I0Exception represents errors that can occur while writing to a file, like permission issues or disk space problems.
import java.nio.file.Files; //Files provides utility methods to open and write files.
import java.nio.file.Path; //Path represents the location of a file, such as ticket_report.txt.
import java.util.List; //List is an interface used to represent an ordered collection of elements. We use List as the reference type and ArrayList as its implementation.
import java.util.Map; //Map is an interface used to represent a collection of key-value pairs. We use Map as the reference type and HashMap as its implementation.
import java.util.ArrayList;
import java.util.EnumMap; //EnumMap is a specialized Map implementation for use with enum keys. It is more efficient than HashMap when the keys are enums.

//Build-T5 Req4: Generates an end--of-day summary of all tickets.
public class TicketReportGenerator {

    //Assumption: A CRITICAL ticket is overdue if it is more than 24 hours old
    //and has not been RESOLVED or CLOSED.
    private static final long OVERDUE_HOURS = 24; //Defines the threshold for considering a critical ticket as overdue. (static - means it belongs to the class and not individual objects, final - means its value cannot be changed.)

    public static void generateReport(List<? extends Ticket> tickets, Path outputFile)
            throws IOException { //Accepts a list of tickets and an output file path, and generates a report summarizing the tickets.
                                  //Why <? extends Ticket>? It allows the method to accept a list of Ticket objects or any subclass of Ticket, providing flexibility.

        /*
         * 1. CREATE MAPS TO COUNT STATUS AND PRIORITY
         *
         * Map - Is an interface used to represent a collection of key-value pairs.
         * Eg: OPEN -> 2, CLOSED -> 3.
         *
         * EnumMap - Is a specialized Map implementation for use with enum keys.
         * Here, Status and Priority are enums.
         */

        Map<Status, Integer> statusCounts = new EnumMap<>(Status.class); //Creates a map to count tickets by their status.
        Map<Priority, Integer> priorityCounts = new EnumMap<>(Priority.class); //Creates a map to count tickets by their priority.

        //Iterates through all possible statuses.
        for (Status status : Status.values()) {
            statusCounts.put(status, 0); //Initializes the count for each status to 0.
        }

        //Iterates through all possible priorities.
        for (Priority priority : Priority.values()) {
            priorityCounts.put(priority, 0); //Initializes the count for each priority to 0.
        }


        /*
         * 2. INITIALIZE VARIABLES
         */

        Ticket oldestTicket = null; //Will hold the ticket with the earliest creation time. Initially, no oldest OPEN ticket has been found.

        List<Ticket> overdueCriticalTickets = new ArrayList<>(); //Stores all overdue CRITICAL tickets.


        /*
         * 3. PROCESS EACH TICKET
         */

        //Iterates through each ticket in the provided list.
        for (Ticket ticket : tickets) {

            Status status = ticket.getStatus(); //Retrieves the status of the current ticket.

            //Increments the count for this status in the statusCounts map.
            statusCounts.put(status, statusCounts.get(status) + 1);

            Priority priority = ticket.getPriority(); //Retrieves the priority of the current ticket.

            //Increments the count for this priority in the priorityCounts map.
            priorityCounts.put(priority, priorityCounts.get(priority) + 1);


            //Find the oldest OPEN ticket.
            if (status == Status.OPEN) { //Checks if the current ticket is OPEN.

                //If no oldest ticket has been found yet, or if the current ticket was created before the previously found oldest ticket.
                if (oldestTicket == null || ticket.getCreatedAt().isBefore(oldestTicket.getCreatedAt())) {

                    oldestTicket = ticket; //Updates oldestTicket to the current ticket.
                }
            }


            //Find overdue CRITICAL tickets.
            //Both conditions must be true:
            //1. Ticket priority must be CRITICAL.
            //2. Ticket age must exceed 24 hours.
            //3. Ticket status must not be RESOLVED or CLOSED.

            if (priority == Priority.CRITICAL &&
                    status != Status.RESOLVED &&
                    status != Status.CLOSED &&
                    ticket.ageInHours() > OVERDUE_HOURS) {

                overdueCriticalTickets.add(ticket); //Adds the matching ticket to the overdue CRITICAL tickets list.
            }
        }


        /*
         * 4. BUILD THE REPORT USING STRINGBUILDER
         *
         * StringBuilder is mutable, which means its content can be modified.
         * append() adds text to the same StringBuilder object.
         *
         * We use StringBuilder because the report contains multiple sections
         * that need to be combined into one final String.
         *
         * IMPORTANT: This is outside the ticket loop because we want to
         * generate the report only after processing all tickets.
         */

        StringBuilder report = new StringBuilder();


        //Add report heading.
        report.append("========== QUEUEDESK END-OF-DAY REPORT ==========\n\n");


        //Add status counts.
        report.append("TICKET COUNTS BY STATUS:\n");

        //Iterates through every status and adds its count to the report.
        for (Status status : Status.values()) {
            report.append(status)
                  .append(": ")
                  .append(statusCounts.get(status))
                  .append("\n");
        }


        //Add priority counts.
        report.append("\nTICKET COUNTS BY PRIORITY:\n");

        //Iterates through every priority and adds its count to the report.
        for (Priority priority : Priority.values()) {
            report.append(priority)
                  .append(": ")
                  .append(priorityCounts.get(priority))
                  .append("\n");
        }


        //Add oldest OPEN ticket information.
        report.append("\nOLDEST OPEN TICKET:\n");

        //Checks whether an OPEN ticket was found.
        if (oldestTicket != null) {

            report.append("Ticket ID: ").append(oldestTicket.getId()).append("\n");
            report.append("Title: ").append(oldestTicket.getTitle()).append("\n");
            report.append("Created At: ").append(oldestTicket.getCreatedAt()).append("\n");

        } else {

            report.append("No OPEN tickets found.\n"); //Executed when there are no OPEN tickets.
        }


        //Add overdue CRITICAL tickets information.
        report.append("\nOVERDUE CRITICAL TICKETS:\n");

        //Checks whether the overdue CRITICAL tickets list is empty.
        if (!overdueCriticalTickets.isEmpty()) {

            //Iterates through every overdue CRITICAL ticket.
            for (Ticket overdueTicket : overdueCriticalTickets) {

                report.append("Ticket ID: ").append(overdueTicket.getId()).append("\n");
                report.append("Title: ").append(overdueTicket.getTitle()).append("\n");
                report.append("Created At: ").append(overdueTicket.getCreatedAt()).append("\n");
                report.append("Age (hours): ").append(overdueTicket.ageInHours()).append("\n\n");
            }

        } else {

            report.append("No overdue CRITICAL tickets found.\n");
        }


        //Add closing line.
        report.append("\n=================================================\n");


        /*
         * 5. WRITE THE REPORT TO THE SPECIFIED OUTPUT FILE
         *
         * BufferedWriter - Used to write text into a file efficiently.
         *
         * try-with-resources - Automatically closes the BufferedWriter
         * after writing is completed, even if an exception occurs.
         */

        try (BufferedWriter writer = Files.newBufferedWriter(outputFile)) {

            writer.write(report.toString()); //Converts StringBuilder into a normal String and writes the complete report into the file.
        }
    }
}