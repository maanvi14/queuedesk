package com.groww.intern.queuedesk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

// Build T4: Demo class to test Repository and Triage Queue functionality.
public class T4Demo {

    public static void main(String[] args) {

        // --------------------------------------------------
        // 1. CREATE TICKETS
        // --------------------------------------------------

        //Creating different types of tickets. Each subclass has its own additional fields.

        Ticket t1 = new BugTicket("Login Error", "User cannot log in", "Rahul", Priority.HIGH, Severity.HIGH, "Enter credentials and click login");

        Ticket t2 = new FeatureRequestTicket("Dark Mode", "Add dark mode to the application", "Priya", Priority.MEDIUM, 25, "Improve user experience");

        Ticket t3 = new AccessRequestTicket("Database Access", "Request access to the database", "Aman", Priority.CRITICAL, "Production Database", "Read Only");

        Ticket t4 = new BugTicket("Payment Failure", "Payment is not processing", "Neha", Priority.HIGH, Severity.CRITICAL, "Try making a payment using UPI");

        // --------------------------------------------------
        // 2. TEST ASSIGNEE AND TAGS
        // --------------------------------------------------

        //Assigning tickets to different team members.
        t1.setAssignee("Amit");
        t2.setAssignee("Riya");
        t3.setAssignee("Amit");

        //t4 is intentionally left unassigned.This helps test how the comparator handles null assignees.

        //Adding tags to tickets.
        t1.addTag("login");
        t1.addTag("bug");

        t2.addTag("UI");
        t3.addTag("access");
        t4.addTag("payment");

        //Store all tickets in one list.
        List<Ticket> tickets = new ArrayList<>();

        Collections.addAll(tickets, t1, t2, t3, t4);

        System.out.println("\n========== ALL TICKETS ==========");

        for (Ticket ticket : tickets) {
            System.out.println(ticket);
        }

        // --------------------------------------------------
        // 3. TEST COMPARABLE - NATURAL ORDERING
        // --------------------------------------------------

        //Collections.sort() uses the compareTo() method
        //implemented inside Ticket.
        //
        //Ordering:
        //1. Higher priority first.
        //2. Older ticket first when priorities are equal.

        Collections.sort(tickets);

        System.out.println("\n========== NATURAL ORDER ==========");

        for (Ticket ticket : tickets) {
            System.out.println(ticket);
        }

        // --------------------------------------------------
        // 4. TEST CUSTOM COMPARATORS
        // --------------------------------------------------

        //Comparator 1:
        //Sort tickets by assignee first, then by priority.
        //Null assignees are placed first.

        tickets.sort(TicketComparators.BY_ASSIGNEE_THEN_PRIORITY);

        System.out.println("\n========== ASSIGNEE THEN PRIORITY ==========");

        for (Ticket ticket : tickets) {
            System.out.println(ticket);
        }

        //Comparator 2:
        //Sort tickets by status first, then by creation time.

        tickets.sort(TicketComparators.BY_STATUS_THEN_AGE);

        System.out.println("\n========== STATUS THEN AGE ==========");

        for (Ticket ticket : tickets) {
            System.out.println(ticket);
        }

        // --------------------------------------------------
        // 5. TEST GENERIC REPOSITORY
        // --------------------------------------------------

        //Repository<Ticket> can store Ticket objects
        //as well as objects of its subclasses.

        Repository<Ticket> repository = new Repository<>();

        //Adding all tickets to the repository.
        for (Ticket ticket : tickets) {
            repository.add(ticket);
        }

        //Testing all(): returns an unmodifiable view
        //of the tickets stored in the repository.

        System.out.println("\n========== REPOSITORY: ALL TICKETS ==========");

        for (Ticket ticket : repository.all()) {
            System.out.println(ticket);
        }

        //Testing findById(): searches for a ticket using its ID.

        System.out.println("\n========== FIND TICKET BY ID ==========");

        Ticket foundTicket = repository.findById(t1.getId());

        System.out.println(foundTicket);

        //Testing groupByAssignee():
        //Groups tickets according to their assigned team member.

        System.out.println("\n========== GROUP BY ASSIGNEE ==========");

        Map<String, List<Ticket>> groupedTickets =
                repository.groupByAssignee();

        for (Map.Entry<String, List<Ticket>> entry : groupedTickets.entrySet()) {

            System.out.println("Assignee: " + entry.getKey());

            for (Ticket ticket : entry.getValue()) {
                System.out.println("  " + ticket);
            }
        }

        // --------------------------------------------------
        // 6. TEST PRIORITY QUEUES
        // --------------------------------------------------

        //PriorityQ maintains two separate priority queues:
        //1. Natural ordering using Ticket.compareTo().
        //2. Custom ordering using TicketComparators.

        PriorityQ priorityQueue = new PriorityQ();

        //Adding each ticket to both queues.
        for (Ticket ticket : tickets) {
            priorityQueue.offer(ticket);
        }

        //Polling the natural-order queue.
        //poll() removes and returns the next ticket.

        System.out.println("\n========== NATURAL ORDER QUEUE ==========");

        while (!priorityQueue.isNaturalOrderQueueEmpty()) {
            System.out.println(priorityQueue.pollNaturalOrder());
        }

        //Polling the custom-order queue independently.

        System.out.println("\n========== CUSTOM ORDER QUEUE ==========");

        while (!priorityQueue.isCustomOrderQueueEmpty()) {
            System.out.println(priorityQueue.pollCustomOrder());
        }

        // --------------------------------------------------
        // 7. TEST GENERIC METHOD - OLDEST TICKET
        // --------------------------------------------------

        //TicketUtils.oldest() finds the ticket with the earliest createdAt value.

        Ticket oldestTicket = TicketUtils.oldest(tickets);

        System.out.println("\n========== OLDEST TICKET ==========");
        System.out.println(oldestTicket);

        // --------------------------------------------------
        // END OF T4 DEMO
        // --------------------------------------------------

        System.out.println("\n========== T4 DEMO COMPLETED ==========");

    }
}