package com.queuedesk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//Build-T4 Req4: Repository class to store tickets and provide methods.
public class Repository<T extends Ticket> {//bounded type parameter , it restricts T so that this repository can only be used for the Ticket class or any subclass of Ticket class.
    private final List<T> tickets = new ArrayList<>();
   /*private as we don't want other classes to directly access or modify our internal ticket list.
   Instead, they should use the methods we provide:
   - add() to add tickets.
   - all() to view tickets.
   - findById() to search.
   - groupByAssignee() to group.
    This is an example of Encapsulation.
    List is an interface in java, it represents an ordered collection of elements , <T> means the list stores objects oof the type represented by T.
    new ArrayList<>() --> creates an empty list in memory.
    
    private final List<T> tickets = new ArrayList<>(); creates a private, initially empty, dynamically resizable list to store tickets of type T; private provides encapsulation, final prevents reassignment of the list reference, and List<T> ensures type safety.*/

    //Req1: add(T ticket) method to add a ticket to the repository.
    public void add(T ticket){ //accepts a ticket and adds it our internal list ie tickets.
        tickets.add(ticket);
    }
    
    //Req2: all() method to return all tickets in the repository.
    public List<T> all(){
        return Collections.unmodifiableList(tickets); //returns an unmodifiable view of the tickets list, preventing external modification.
    }

    //Req3: findById(int id) method to find a ticket by its ID.
    public T findById(int id){
        for(T ticket : tickets){ //Iterates through each ticket in the tickets list.
            if(ticket.getId() == id){ //Checks if the ticket's ID matches the provided ID.
                return ticket; //If a match is found, returns the ticket.
            }
        }
        //return null; //If no ticket with the given ID is found, returns null.
        //Build T5 Req2: Throw TicketNotFoundException if the ticket with the given ID is not found.So, throw a custom unchecked exception instead of returning null.
        throw new TicketNotFoundException("Ticket with ID" + id + "was not found.");
    }

    //Req4: groupByAssignee() method to group tickets by their assignee.
    //key = assignee(name-string), value = list of tickets assigned to that assignee.

    public Map<String, List<T>> groupByAssignee(){
        Map<String, List<T>> groupedTickets = new HashMap<>(); //Creates an empty HashMap to store the grouped tickets.
        for(T ticket : tickets){ //Iterates through each ticket in the tickets list.
            String assignee = ticket.getAssignee(); //Retrieves the assignee of the current ticket.
            // groupedTickets.computeIfAbsent(assignee, k -> new ArrayList<>()).add(ticket); //If the assignee is not already a key in the map, creates a new list for that assignee and adds the ticket to it.
        
            //If the assignee is not already a key in the map, creates a new list for that assignee and adds the ticket to it.
            if(!groupedTickets.containsKey(assignee)){ //Checks if the assignee is already a key in the map.
                groupedTickets.put(assignee, new ArrayList<>()); //If not, creates a new list for that assignee.
            }
            groupedTickets.get(assignee).add(ticket); //Adds the current ticket to the list of tickets for that assignee.
        }
        return groupedTickets; //Returns the map of grouped tickets.
    }
    
    //Build-T6 - Req-6:Find a ticket by ID and return Optional instead of throwing an exception.

    public Optional<T> findByIdSafe(int id) {
        // 1. Convert the existing ticket list into a Stream.
       // 2. filter() keeps only the ticket whose ID matches the given ID.
       // 3. findFirst() returns the first matching ticket as an Optional.
      // 4. If no ticket matches, it returns Optional.empty().
       return tickets.stream()
            .filter(ticket -> ticket.getId() == id)
            .findFirst();
    }

}
