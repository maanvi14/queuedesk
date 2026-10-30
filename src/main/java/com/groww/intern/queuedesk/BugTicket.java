package com.groww.intern.queuedesk;

import java.util.Objects;

//Build-T3 Req2: BugTicket Subclass; extends Ticet means BugTicket inherits all the fields and methods of Ticket, Also added it's own severity and stepsToReproduce fields. The constructor initializes all the fields, including those inherited from Ticket. The estimateEffortHours() method is implemented to provide a specific effort estimation for bug tickets based on their severity.
public class BugTicket extends Ticket implements Escalatable{
    //bug specific fields
    private final Severity severity;
    private final String stepsToReproduce;

    // Tracks whether the ticket has been escalated.
    private boolean escalated = false;

    // Tracks the current escalation level.
    private int escalationLevel = 0;

    // Constructor: initializes both common Ticket data and bug-specific data.
    public BugTicket(String title, String description, String requester, Priority priority, Severity severity, String stepsToReproduce){

        //calls the parent class constructor, must be first statement in this constructor.
        super(title, description, requester, priority);

        this.severity = Objects.requireNonNull(severity, "Severity");
        // If steps are null, store an empty string; otherwise, remove extra spaces.
        this.stepsToReproduce = stepsToReproduce == null? "" : stepsToReproduce.trim();
    }
    //our fields are private, so we need to use getters and setters to access them.
    public Severity getSeverity(){
        return severity;
    }
    public String getStepsToReproduce(){
        return stepsToReproduce;
    }

    //implement the abstract method from Ticket to estimate effort based on severity.
    @Override
    public double estimateEffortHours(){
        switch(severity){
            case LOW:
                return 1.0;
            case MEDIUM:
                return 3.0;
            case HIGH:
                return 6.0;
            case CRITICAL:
                return 10.0;
            default:
                throw new IllegalStateException("Unknown severity");
        }
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