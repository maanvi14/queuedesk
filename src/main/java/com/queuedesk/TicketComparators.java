package com.queuedesk;
/*One sorting rule cannot conveniently handle every use case. That's why we have created TicketComparators.java.
ticket1.compareTo(ticket2) --> Defines the default order: priority descending, then creation time ascending.
*/
import java.util.Comparator;
//Build-T4 Req3: Provides alternative ways to sort tickets.
public class TicketComparators{
    //Comparator 1: Sort by assignee alphabetically, then by priority from highest to lowest.
    /*Comparator<Ticket> -	A rule for comparing tickets
    BY_ASSIGNEE_THEN_PRIORITY - The name of the rule
    Ticket::getAssignee - Get the ticket's assignee
    Comparator.nullsFirst(...) - put tickets with no assignee before tickets with an assignee.
    Comparator.naturalOrder() -	Sort names alphabetically
    .thenComparing(...) - If the first comparison is tied, use another rule
    Ticket::getPriority	- Get the ticket's priority
    Priority::ordinal -	Get the priority's position in the enum
    .reversed() - Reverse the order so higher enum positions come first
    Comparator.comparingInt(Priority::ordinal).reversed()) - Compare priorities by their enum position, with higher positions coming first. */
    

    public static final Comparator<Ticket> BY_ASSIGNEE_THEN_PRIORITY = Comparator.comparing(Ticket::getAssignee, Comparator.nullsFirst(Comparator.naturalOrder())).thenComparing(Ticket::getPriority, Comparator.comparingInt(Priority::ordinal).reversed());
    
    //Comparator 2 : Sort by status first, then by ticket age(oldest creation time first).
    public static final Comparator<Ticket> BY_STATUS_THEN_AGE = Comparator.comparing(Ticket::getStatus).thenComparing(Ticket::getCreatedAt);
}
