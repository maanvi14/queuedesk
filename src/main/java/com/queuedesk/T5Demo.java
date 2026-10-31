package com.queuedesk;

// Used to represent file paths.
import java.nio.file.Path;

// List stores multiple ticket objects.
import java.util.List;


// Build-T5: Demonstrates loading, exception handling, and report generation.
public class T5Demo {

    public static void main(String[] args) {

        /*
         * STEP 1: LOAD TICKETS FROM tickets.txt
         *
         * TicketFileLoader.load() reads the file line by line.
         * It returns a List<String[]> containing the fields of each line.
         */

        Path inputFile = Path.of("tickets.txt");

        try{
            // Load the ticket records from the input file.
            List<String[]> records = TicketFileLoader.load(inputFile);

            // Display how many records were loaded.
            System.out.println("Tickets loaded successfully: " + records.size());

        } 
        catch (InvalidTicketDataException e) {
            // Handles invalid data found while parsing the file.
            // getMessage() displays the error message, including the line number.
            System.out.println("Invalid ticket data: " + e.getMessage());

        } 
        catch (java.io.IOException e) {
            // Handles file-related errors such as missing files or access issues.
            System.out.println("Unable to read tickets file: " + e.getMessage());
        }


        /*
         * STEP 2: DEMONSTRATE TICKETNOTFOUNDEXCEPTION
         *
         * Repository stores Ticket objects.
         * findById() throws TicketNotFoundException if the requested ID
         * does not exist in the repository.
         */

        Repository<Ticket> repository = new Repository<>();

        // Add a ticket to the repository.
        Ticket ticket = new FeatureRequestTicket(
                "Login improvement",
                "Improve the login experience",
                "Aman",
                Priority.HIGH,
                5,
                "Improve user experience"
        );

        repository.add(ticket);

        try {

            // Search for a ticket that does not exist.
            repository.findById(9999);

        } 
        catch (TicketNotFoundException e) {

            // Catch the unchecked exception and display a clean message.
            System.out.println("Repository error: " + e.getMessage());
        }


        /*
         * STEP 3: GENERATE THE END-OF-DAY REPORT
         *
         * TicketReportGenerator generates a summary containing:
         * 1. Counts by status.
         * 2. Counts by priority.
         * 3. The oldest OPEN ticket.
         * 4. Every overdue CRITICAL ticket.
         *
         * The report is saved using BufferedWriter.
         */

        // Specify the location of the output report.
        Path outputFile = Path.of("ticket_report.txt");

        try {

            // Generate and save the report.
            TicketReportGenerator.generateReport(
                    repository.all(),
                    outputFile
            );

            System.out.println("End-of-day report generated successfully!");
            System.out.println("Report saved at: " + outputFile.toAbsolutePath());

        } 
        catch (java.io.IOException e) {

            // Handle errors that occur while writing the report.
            System.out.println("Unable to generate report: " + e.getMessage());
        }
    }
}