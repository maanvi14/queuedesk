//An interface defines what a class must be able to do, and not how it does it.In this case, the Escalatable interface defines the behavior of tickets that can be escalated. Any class that implements this interface must provide implementations for the methods defined in the interface.
package com.queuedesk;

// T3 Req5: Defines the behavior of tickets that can be escalated.
public interface Escalatable {

    // Escalate the ticket to a higher level.
    void escalate();

    // Check whether the ticket has been escalated.
    boolean isEscalated();

    // Return the current escalation level.
    int escalationLevel();

    // Default method shared by classes implementing this interface.
    default String escalationBadge() {
        if (isEscalated()) {
            return "[ESCALATED - Level " + escalationLevel() + "]";
        }
        return "[NOT ESCALATED]";
    }
}
