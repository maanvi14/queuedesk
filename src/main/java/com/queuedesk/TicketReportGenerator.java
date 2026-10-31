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
public class TicketReportGenerator{
    //Assumption: A critical ticket is overdue if it is more than 24 hours old and has not been RESOLVED or CLOSED.
    private static final long OVERDUE_HOURS = 24; //Defines the threshold for considering a critical ticket as overdue.(static - means it belongs to the class and not individual object)

    public static void generateReport(List<? extends Ticket> tickets, Path outputFile)
            throws IOException{//Accepts a list of tickets and an output file path, and generates a report summarizing the tickets.Why <? extends Ticket> - It allows the method to accept a list of Ticket objects or any subclass of Ticket, providing flexibility in the types of tickets that can be reported on, like BugTicket, FeatureRequestTicket, or AccessRequestTicket.
                /*1.CREATE MAPS TO COUNT STATUS AND PRIORITY 
                Map - Is an interface used to represent a collection of Key-Value pairs.Eg : OPEN -> 2, CLOSED -> 3.
                EnumMap - Is a specialized Map implementation for use with enum keys. Here, status is an enum.
                */
                Map<Status, Integer> statusCounts = new EnumMap<>(Status.class); //Creates a map to count tickets by their status.
                Map<Priority, Integer> priorityCounts = new EnumMap<>(Priority.class); //Creates a map to count tickets by their priority.
                for (Status status : Status.values()) { //Iterates through all possible statuses.
                    statusCounts.put(status, 0); //Initializes the count for each status to 0.
                }
                for (Priority priority : Priority.values()) { //Iterates through all possible priorities.
                    priorityCounts.put(priority, 0); //Initializes the count for each priority to 0.
                }
                /*2. INTIALIZE VARIABLES */

                Ticket oldestTicket = null; //Will hold the ticket with the earliest creation time, and Initially, no oldest OPEN ticket has been found.
                List<Ticket> overdueCriticalTickets = new ArrayList<>(); //Stores all overdue CRITICAL tickets.
                
                /*3. PROCESS EACH TICKET */
                for(Ticket ticket : tickets){ //Iterates through each ticket in the provided list.
                   Status status = ticket.getStatus(); //Retrieves the status of the current ticket.
                   statusCounts.put(status, statusCounts.get(status) + 1); //Increments the count for this status in the statusCounts map.
                   Priority priority = ticket.getPriority(); //Retrieves the priority of the current ticket.
                   priorityCounts.put(priority, priorityCounts.get(priority) + 1); //Increments the count for this priority in the priorityCounts map.
                   
                   //Find the oldest OPEN ticket.
                   if(status == Status.OPEN){ //Checks if the current ticket is OPEN.
                        if(oldestTicket == null || ticket.getCreatedAt().isBefore(oldestTicket.getCreatedAt())){ //If no oldest ticket has been found yet, or if the current ticket was created before the previously found oldest ticket.
                            oldestTicket = ticket; //Updates the oldestTicket to the current ticket.
                        }
                    }

                    //Find overdue CRITICAL tickets.
                    //Both conditions must be true: 1.Ticket priority must be CRITICAL. & 2.Ticket age must exceed 24 hours.
                    if(priority == Priority.CRITICAL && ticket.ageInHours() > OVERDUE_HOURS){
                        overdueCriticalTickets.add(ticket);
                    }

                    /*Build the report using StringBuilder
                    StringBuilder is mutuable, append() adds text to the same StringBuilder object.*/
                

                }

            }


    
}
