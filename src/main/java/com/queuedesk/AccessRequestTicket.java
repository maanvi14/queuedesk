package com.queuedesk;

import java.util.Objects;

// T3 Req4: A ticket for requesting access to a system.
public class AccessRequestTicket extends Ticket implements Escalatable{

    // Fields specific to access requests
    private final String systemName;
    private final String accessLevel;

    // Tracks whether the ticket has been escalated.
    private boolean escalated = false;

    // Tracks the current escalation level.
    private int escalationLevel = 0;

    // Constructor initializes common and access-specific fields.
    public AccessRequestTicket(String title, String description, String requester, Priority priority, String systemName, String accessLevel) {

        // Initialize common Ticket fields.
        super(title, description, requester, priority);

        // System name and access level cannot be null.
        this.systemName = Objects.requireNonNull(systemName, "System name cannot be null").trim();

        this.accessLevel = Objects.requireNonNull(accessLevel, "Access level cannot be null").trim();
    }

    public String getSystemName() {
        return systemName;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    //Provides an effort estimate for an access request.
    @Override
    public double estimateEffortHours() {
        return 2.0;
    }
    // Implement the Escalatable interface methods.
    @Override     
    public void escalate(){
        escalated = true;
        escalationLevel++;
    }

    @Override
    public boolean isEscalated(){
        return escalated;
    }

   @Override
    public int escalationLevel(){
        return escalationLevel;
    } 
}
