
package com.queuedesk;

import java.util.List; //Import List to accept a collection of tickets.
import java.util.Map; //Import Map to store key-value pairs for analytics.

public class T6Demo {

    public static void main(String[] args) {

        // ---------------------------------------------------------
        // STEP 1: CREATE TICKET OBJECTS
        // ---------------------------------------------------------

        // Create different types of tickets using their constructors.
        BugTicket t1 = new BugTicket(
                "Login Issue",
                "Unable to login",
                "Ayush",
                Priority.HIGH,
                Severity.HIGH,
                "Enter valid credentials"
        );

        FeatureRequestTicket t2 = new FeatureRequestTicket(
                "Dark Mode",
                "Add dark mode feature",
                "Maanvi",
                Priority.MEDIUM,
                10,
                "Improve user experience"
        );

        AccessRequestTicket t3 = new AccessRequestTicket(
                "Database Access",
                "Need database access",
                "Ayush",
                Priority.LOW,
                "MySQL",
                "Read"
        );

        // ---------------------------------------------------------
        // STEP 2: ASSIGN TICKETS AND ADD TAGS
        // ---------------------------------------------------------

        // Assign tickets to different team members.
        t1.setAssignee("Ayush");
        t2.setAssignee("Maanvi");
        t3.setAssignee("Ayush");

        // Add tags to each ticket.
        t1.addTag("bug");
        t1.addTag("urgent");

        t2.addTag("feature");
        t2.addTag("enhancement");

        t3.addTag("database");
        t3.addTag("urgent");

        // Store all tickets in a List.
        List<Ticket> tickets = List.of(t1, t2, t3);


        // ---------------------------------------------------------
        // STEP 3: CREATE TICKET ANALYTICS OBJECT
        // ---------------------------------------------------------

        TicketAnalytics analytics = new TicketAnalytics();


        // ---------------------------------------------------------
        // REQ 1: countByPriority()
        // Count the number of tickets belonging to each priority.
        // ---------------------------------------------------------

        System.out.println("\n--- REQ 1: COUNT BY PRIORITY ---");

        Map<Priority, Long> priorityCounts =
                analytics.countByPriority(tickets);

        System.out.println(priorityCounts);


        // ---------------------------------------------------------
        // REQ 2: groupByAssignee()
        // Group tickets according to their assigned person.
        // ---------------------------------------------------------

        System.out.println("\n--- REQ 2: GROUP BY ASSIGNEE ---");

        Map<String, List<Ticket>> assigneeGroups =
                analytics.groupByAssignee(tickets);

        // Display each assignee and their assigned tickets.
        for (Map.Entry<String, List<Ticket>> entry : assigneeGroups.entrySet()) {

            // getKey() returns the assignee name.
            // getValue() returns the List of tickets assigned to them.
            System.out.println(
                    entry.getKey() + " -> " + entry.getValue()
            );
        }


        // ---------------------------------------------------------
        // REQ 3: averageEffortHours()
        // Calculate the average estimated effort of all tickets.
        // ---------------------------------------------------------

        System.out.println("\n--- REQ 3: AVERAGE EFFORT HOURS ---");

        double averageEffort = analytics.averageEffortHours(tickets);

        System.out.println("Average effort: " + averageEffort + " hours");


        // ---------------------------------------------------------
        // REQ 4: totalEffortHours()
        // Calculate total effort using reduce().
        // ---------------------------------------------------------

        System.out.println("\n--- REQ 4: TOTAL EFFORT HOURS ---");

        double totalEffort = analytics.totalEffortHours(tickets);

        System.out.println("Total effort: " + totalEffort + " hours");


        // ---------------------------------------------------------
        // REQ 5: oldestOpenTicket()
        // Find the oldest ticket having OPEN status.
        // ---------------------------------------------------------

        System.out.println("\n--- REQ 5: OLDEST OPEN TICKET ---");

        analytics.oldestOpenTicket(tickets)
                .ifPresentOrElse(

                        // If an OPEN ticket exists, display its details.
                        ticket -> System.out.println(
                                "Oldest OPEN ticket: " + ticket
                        ),

                        // If no OPEN ticket exists, display this message.
                        () -> System.out.println("No OPEN tickets found.")
                );


        // ---------------------------------------------------------
        // REQ 6: findByIdSafe()
        // Find a ticket using its ID and return Optional.
        // ---------------------------------------------------------

        System.out.println("\n--- REQ 6: SAFE TICKET LOOKUP ---");

        // Create a Repository to store the tickets.
        Repository<Ticket> repository = new Repository<>();

        // Add all three tickets to the repository.
        repository.add(t1);
        repository.add(t2);
        repository.add(t3);

        // Search for an existing ticket using its ID.
        repository.findByIdSafe(t1.getId())
                .ifPresentOrElse(

                        // Execute when the ticket is found.
                        ticket -> System.out.println(
                                "Ticket found: " + ticket
                        ),

                        // Execute when the ticket is not found.
                        () -> System.out.println("Ticket not found.")
                );

        // Search for a ticket ID that does not exist.
        repository.findByIdSafe(-1)
                .ifPresentOrElse(

                        ticket -> System.out.println(
                                "Ticket found: " + ticket
                        ),

                        () -> System.out.println(
                                "No ticket exists with ID -1."
                        )
                );


        // ---------------------------------------------------------
        // REQ 7: tagLine()
        // Collect tags, remove duplicates, sort and join them.
        // ---------------------------------------------------------

        System.out.println("\n--- REQ 7: TICKET TAG LINE ---");

        String tags = analytics.tagLine(tickets);

        System.out.println("All tags: " + tags);

    }
}
