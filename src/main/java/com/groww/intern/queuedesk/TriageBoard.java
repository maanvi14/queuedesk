package com.groww.intern.queuedesk;

import java.util.List;
/*Imagine QueueDesk has three tickets:
Ticket 1 — BugTicket: Login failure, effort = 10 hours, supports escalation.
Ticket 2 — FeatureRequestTicket: Add expense report, effort = 8 hours, does not support escalation.
Ticket 3 — AccessRequestTicket: Database access, effort = 2 hours, supports escalation.

We want one method that prints all three tickets without writing separate printing code for each type. That is why printTriageBoard() exists. */
//T3 Req6: Displays different ticket types on one triage board.
public class TriageBoard {

    //Create a method called printTriageBoard that takes a list of tickets and prints them all.Why Ticket? becoz all three classes extend ticket.Accepts a list containing different types of Ticket subclasses.
    //List<Ticket> acceps a list of tickets. All 3 classes extend Ticket, so their objects can be stored in the same list.
    public static void printTriageBoard(List<Ticket> tickets) {

        //loop through tickets- for every ticket in the list, process one ticket at a time.
        for (Ticket ticket : tickets) {

            //Print the current ticket's details using its toString() method.
            System.out.println(ticket.toString());

            //Calls the effort-estimation method implemented by the actual ticket subclass. This is polymorphism: each ticket type can return a different value.
            //For example, a BugTicket with CRITICAL severity returns 10.0 hours, while an AccessRequestTicket returns 2.0 hours in our current implementation.
            System.out.println("Estimated effort: " + ticket.estimateEffortHours() + " hours");

            //Check whether this ticket supports escalation.
            //if (ticket instanceof Escalatable e) — checks whether the ticket implements Escalatable. If yes, e refers to that ticket through the interface.
            if (ticket instanceof Escalatable e) {

                //e.escalationBadge() — gets the ticket's escalation status and prints it.
                System.out.println("Escalation: " + e.escalationBadge());
            }

            //prints a separator between tickets to make the output easier to read.
            System.out.println("-------------------------");
        }
    }
}
