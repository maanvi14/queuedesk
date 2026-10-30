package com.groww.intern.queuedesk;

import java.util.ArrayList;
import java.util.List;

//Build T3 Demo: Test the triage board with different ticket types.
public class T3Demo1{
    public static void main(String[] args){
    
        //Create a BugTicket, parameters: title, description, requester, priority, severity, stepsToReproduce
        BugTicket bug = new BugTicket("Login failure", "User cannot log in", "Maanvi", Priority.HIGH, Severity.CRITICAL, "Enter valid credentials and click Login");

        //Create a FeatureRequestTicket, parameters: title, description, requester, priority, votes, businessjustification 
        FeatureRequestTicket feature = new FeatureRequestTicket("Add expense reports", "Allow users to download monthly reports", "Asha", Priority.MEDIUM, 25, "Helps users track their spending");

        //Create an AccessRequestTicket, parameters: title, description, requester, priority, systemName, accessLevel
        AccessRequestTicket access = new AccessRequestTicket("Database access", "Request read-only access", "Rahul", Priority.HIGH, "Production Database", "Read-only");

        //Escalate the bug ticket and access request.
        bug.escalate();
        access.escalate();

        //Create one list that can hold all three types of tickets. Since all three classes extend Ticket, we can store them in a List<Ticket>.
        List<Ticket> tickets = new ArrayList<>();
        //Add the tickets to the list.
        tickets.add(bug);
        tickets.add(feature);
        tickets.add(access);

        //Print the triage board with all tickets.
        TriageBoard.printTriageBoard(tickets);
    }
}
/*
// T4Demo.java — CREATE the list
List<Ticket> tickets = new ArrayList<>();
tickets.add(bug);
tickets.add(feature);
tickets.add(access);

// TriageBoard.java — RECEIVE and PROCESS the list
public static void printTriageBoard(List<Ticket> tickets) {
    for (Ticket ticket : tickets) {
        System.out.println(ticket);
    }
} */