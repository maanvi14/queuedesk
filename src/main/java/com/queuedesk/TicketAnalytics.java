package com.queuedesk;
import java.util.List; //Import List to accept a collection of tickets.
import java.util.Map; //Import Map to store key-value pairs for analytics.
import java.util.Optional; //Import Optional to handle potential null values in analytics as some search operations may not find a ticket.
import java.util.Comparator; //Import Comparator to compare tickets based on their creation date.
import java.util.stream.Collectors; //Import Collectors for grouping, counting and joining stream results.

//BUILD-T6 TicketAnalytics class is responsible for analyzing ticket data and providing insights based on various criteria such as status, priority, and creation date. It utilizes Java's Stream API to process collections of tickets efficiently.
public class TicketAnalytics {

    /*Req1: countByPriority() - Count how many tickets belong to each priority.(Grouping and counting)
    Map<Priority, Long> here Priority is the key and Long(count) is the value.
    Priority Long
    HIGH   -> 1
    MEDIUM -> 2
    LOW    -> 1
    countByPriority - Method name, List<Ticket> - Input:A list of Ticket objects.
    */
    public Map<Priority, Long> countByPriority(List<Ticket> tickets){
        //1.STREAM CREATION: convert the ticket list into streams ie. List<Ticket> -> Stream<Ticket>
        return tickets.stream()

        //2.TERMINAL OPERATION(collect) , Collect the processed Stream into a Map.
       // After processing the stream, we want to collect the result into a data structure, here we want the final result to be a Map , .collect(..)
        .collect(Collectors.groupingBy(

            //group tickets based on their priority.
            //equivalent to: ticket -> ticket.getPriority()
            Ticket::getPriority, 
            
            //Count the number of tickets in each priority group.
            Collectors.counting()));
    }

    /*Req2: groupByAssignee() - Group tickts according to their assignee.
    Map<String, List<Ticket>>
    Assignee(String)  Tickets(List<Ticket>)
    Ayush               T1,T2
    Maanvi              T3
    */
    public Map<String, List<Ticket>> groupByAssignee(List<Ticket> tickets){
        //1.STREAM CREATION: convert the ticket list into streams ie. List<Ticket> -> Stream<Ticket>
        return tickets.stream()

        //2.TERMINAL OPERATION(collect) , Collect the processed Stream into a Map.
       // After processing the stream, we want to collect the result into a data structure, here we want the final result to be a Map , .collect(..)
        .collect(Collectors.groupingBy(

            //group tickets based on their assignee.
            //equivalent to: ticket -> ticket.getAssignee()
            Ticket::getAssignee));
    }

    //Req3: averageEffortHours() - Calculate the average estimated effort of all tickets.
    public double averageEffortHours(List<Ticket> tickets){
        //1.STREAM CREATION: convert the ticket list into streams ie. List<Ticket> -> Stream<Ticket>
        return tickets.stream()

        //2.mapToDouble - Convert each ticket to its estimated effort in hours.
        .mapToDouble(Ticket::estimateEffortHours)
        //3. Calculate the average of the estimated effort hours.
        .average()
        //4. Handle the case where there are no tickets to avoid division by zero.
        .orElse(0.0);
    }

    //Req4: totalEffortHours() - Calculate total effort using reduce(), compare this with the T5 approach.
    public double totalEffortHours(List<Ticket> tickets){
        //1.STREAM CREATION: convert the ticket list into streams ie. List<Ticket> -> Stream<Ticket>
        return tickets.stream()

        //2.mapToDouble - Convert each ticket to its estimated effort in hours.
        .map(Ticket::estimateEffortHours)
        //3. Use reduce to sum up all the estimated effort hours. double::sum is a method reference that takes two double values and returns their sum.
        .reduce(0.0, Double::sum, Double::sum);
    }

    //Req5: oldestOpenTicket() - Find the oldest ticket whose status is OPEN.
    public Optional<Ticket> oldestOpenTicket(List<Ticket> tickets){
        //1.STREAM CREATION: convert the ticket list into streams ie. List<Ticket> -> Stream<Ticket>
        return tickets.stream()

        //2.Filter the stream to include only tickets that are OPEN.
        .filter(ticket -> ticket.getStatus() == Status.OPEN)

        //3.Find the ticket with the earliest creation date among the filtered tickets.
        .min(Comparator.comparing(Ticket::getCreatedAt));
    }
    
    //Req7 : tagLine() - Collct all ticket tags, remove duplicates , sort and join them.
    public String tagLine(List<Ticket> tickets){
        //1.STREAM CREATION: convert the ticket list into streams ie. List<Ticket> -> Stream<Ticket>
        return tickets.stream()

        //2.flatMap - Flatten the stream of lists of tags into a single stream of tags.
        .flatMap(ticket -> ticket.getTags().stream())

        //3.distinct - Remove duplicate tags from the stream.
        .distinct()

        //4.sorted - Sort the tags in natural order (alphabetically).
        .sorted()

        //5.collect - Join the sorted, distinct tags into a single string, separated by commas.
        .collect(Collectors.joining(", "));
    }
}
