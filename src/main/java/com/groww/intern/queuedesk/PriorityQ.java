package com.groww.intern.queuedesk;
import java.util.PriorityQueue;
//Build-T4 Req5: Manages two ticket triage queues using different ordering strategies.
public class PriorityQ {

    //Queue1: Natural ordering using Comparable.
    private final java.util.PriorityQueue<Ticket> naturalOrderQueue = new java.util.PriorityQueue<>(); //Creates a priority queue that orders tickets based on their natural ordering defined in the Ticket class, ie  Our natural ordering: higher priority first, then older createdAt first.
    
    //Queue2: Custom ordering using Comparator.
    private final java.util.PriorityQueue<Ticket> customOrderQueue = new java.util.PriorityQueue<>(TicketComparators.BY_ASSIGNEE_THEN_PRIORITY); //Creates a priority queue that orders tickets based on a custom comparator(BY_ASSIGNEE_THEN_PRIORITY comparator) defined in the TicketComparators class, ie  Our custom ordering: First sorts by assignee (alphabetically), then by priority (higher first).

    //Adds the same ticket to both queues.
    public void offer(Ticket ticket){
        naturalOrderQueue.offer(ticket);
        customOrderQueue.offer(ticket);
    }

    //removes and returns the next ticket according to natural ordering, returns null if the queue is empty.
    public Ticket pollNaturalOrder(){
        return naturalOrderQueue.poll();
    }

    //removes and returns the next ticket according to custom ordering, returns null if the queue is empty.
    public Ticket pollCustomOrder(){
        return customOrderQueue.poll();
    }

    //checks whether the natural order queue is empty.
    public boolean isNaturalOrderQueueEmpty(){
        return naturalOrderQueue.isEmpty();
    }

    //checks whether the custom order queue is empty.
    public boolean isCustomOrderQueueEmpty(){
        return customOrderQueue.isEmpty();
    }

}
