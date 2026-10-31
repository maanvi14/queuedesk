package com.queuedesk;
import java.util.List;
//Build-T4 Req6: Utility class containing generic methods for working with tickets.
public class TicketUtils {
    // Generic method = oldest(List<T> tickets) method to find the oldest ticket in a list of tickets.
    public static <T extends Ticket> T oldest(List<T> tickets){ //List<T> means the method accepts a list containing objects of type T.
        if(tickets == null || tickets.isEmpty()){ //there is no oldest ticket if list has no elements.
            return null; 
        }
        T oldestTicket = tickets.get(0); //Initially assume the first ticket is the oldest.
        for(T ticket : tickets){ //Iterates through each ticket in the list.
            if(ticket.getCreatedAt().isBefore(oldestTicket.getCreatedAt())){ //isBefore() Checks if the current ticket was created before the oldest ticket found so far.
                oldestTicket = ticket; //If so, updates the oldest ticket to the current ticket.
            }
        }
        return oldestTicket; //Returns the oldest ticket found in the list(ie return the ticket with the earliest creation time).
    }
    
}
