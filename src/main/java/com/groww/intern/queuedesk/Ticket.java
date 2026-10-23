/*
REQ 1: CREATE THE FIELDS 
A field is a variable declared inside a class that stores an object's data.
in java, these variables inside a class are called 'fields'.
id           -> int            -> Unique ticket ID
title        -> String         -> Short summary of the issue
description  -> String         -> Details of the issue
requester    -> String         -> Person who raised the ticket
priority     -> Priority       -> LOW, MEDIUM, HIGH, CRITICAL
status       -> Status         -> OPEN, IN_PROGRESS, RESOLVED, CLOSED
createdAt    -> LocalDateTime  -> Date and time the ticket was created
Priority.java and Status.java are enum that defines the fixed sets of allowed values. 
enum(short for enumeration) is a Java data type used when a variable should have one value from a fixed set of predefined options. think of it like a dropdown menu : u can select one of the available options but can't select a random value.
Example:
enum Priority { LOW, MEDIUM, HIGH, CRITICAL }
enum Status { OPEN, IN_PROGRESS, RESOLVED, CLOSED }
So, instead of using a string, we create an enum. : 1.Restrict values to predefined options, 2.Prevent invalid values and many typing mistakes, hence -> makes code more reliable.

REQ-2 : GENERATE TICKET IDs AUTOMATICALLY
private static int nextID = 1;

static -> One shared variable for the entire Ticket class.
nextId -> Stores the next ID to assign.
nextId++ -> Assigns the current ID and then increments it.

when creating a ticket, this.id = nextId++; this assigns the current counter value to the new ticket and then increments the counter.
this.id means the id belonging to the current ticket.
Example: First ticket gets ID 1, second gets ID 2, third gets ID 3.

REQ 3: CONSTRUCTORS
Constructor = special method that initializes an object.
It runs automatically when we use 'new'.

1. Full constructor:
Accepts title, description, requester and priority.
Automatically generates ID, sets status to OPEN, and records the current creation time.

Ticket a = new Ticket(
    "Login fails",
    "Error 500 on submit",
    "asha",
    Priority.HIGH
);

2. Short constructor:
Takes only title and requester.
Accepts only title and requester.
Defaults: description = "", priority = MEDIUM.

Ticket b = new Ticket("Printer jam", "kiran");

this(...) = calls another constructor in the same class. It avoids repeating initialization code. 
It must be the first statement in the constructor.

* The full constructor initializes everything; the short constructor supplies defaults and reuses the full constructor through this(...).

REQ 4: private AND final
private -> Prevents direct access to a field from outside the class.
final -> Prevents reassignment after initialization.

id, title, requester and createdAt are final.
description, priority and status can be changed.

REQ 5: GETTERS AND SETTERS
Getter -> Returns/acess a field's value.
Setter -> Updates/modifies a field's value.

Getters are provided for all fields.
Setters are provided only for description, priority and status.

REQ 6: AGE OF A TICKET
LocalDateTime.now() -> Gets the current local date and time.
Duration.between() -> Calculates elapsed time.
toHours() -> Returns elapsed whole hours.

ageInHours() returns how many whole hours have passed since creation.

REQ 7: COUNT TOTAL TICKETS
Ticket.totalCreated() -> Returns the total tickets created in this program run.
static -> The method belongs to the class, not an individual ticket.
Implementation: nextId - 1.
TicketsCreated	nextId	nextId - 1
        0	        1	0
        1	        2	1
        2	        3	2
        3	        4	3 

REQ 8: VALIDATION
title == null || title.isBlank()
Rejects a missing or blank title.

Objects.requireNonNull()
Rejects null values for required fields.

trim() -> Removes leading and trailing spaces.

REQ 9: toString()
Provides a readable text representation of a ticket.
Ticket 
ID - 1
Title - Login Issue
Priority - HIGH 
Status - OPEN
Requester - Asha

Your Java object already contains all this information.
What is the problem?
When we write: System.out.println(t1);


You want Java to print the ticket details in a readable format.

So we tell Java how to display the ticket using toString().
public String toString() {
    return "#" + id + " [" + priority + "/" + status
            + "] " + title + " (" + requester + ")";
}

This produces:
#1 [HIGH/OPEN] Login issue (Asha)
*/
package com.groww.intern.queuedesk;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Ticket {
    private static int nextId = 1; //shared by all tickets 

    private final int id;
    private final String title;
    private String description;
    private final String requester;
    private Priority priority;
    private Status status;
    private final LocalDateTime createdAt;

    //full constructor 
    /*can be called using Ticket t1 = new Ticket("Login issue", "Error 500", "Asha", Priority.HIGH); */
    public Ticket(String title, String description, String requester, Priority priority){
        this.id = nextId++;
        this.title = Objects.requireNonNull(title, "Title"); //syntax: Objects.requireNonNull(variableToCheck, "Error message if it is null");
        this.description = description == null? "" : description.trim(); //description is optinal while title, requester and priority are treated as required fields, this uses a ternary operator which is short of if-else. If description is null, store an empty string "".Otherwise, store the description after trimming spaces.
        this.requester = Objects.requireNonNull(requester, "Requester").trim();
        this.priority = Objects.requireNonNull(priority, "Priority");
        this.status = Status.OPEN;
        this.createdAt = LocalDateTime.now();

        if(this.title.isBlank()){ //here, we are validating the title if it is empty or contains only spaces, java throws an error instead of accepting the ticket.
            throw new IllegalArgumentException("Title cannot be blank"); //Q:why not use isEmpty()? if we use "" -> return true but if "  " -> return false. isBlank() returns true for both "" and "   ".
        }
    }

    //short constructor 
    /*suppose u wnat to create a ticket but don't want to provide a description or priority.
    Ticket t2 = new Ticket("printer issue", "kiran");
    the short constructor supplies the value automatically for the missing fields. like Description : "" , Priority : Priority.MEDIUM
    this(..) -->It calls another constructor in the same class — in this case, the full constructor. So, rather than repeating all the initialization code, Java reuses the full constructor.
    this(...) must be the first statement inside the constructor. */

    public Ticket(String title, String requester){
        this(title, "", requester, Priority.MEDIUM);
    }

    //Getters 
    public int getId(){
        return id;
    }
    public String getTitle(){
        return title;
    }
    public String getDescription(){
        return description;
    }
    public Priority getPriority(){
        return priority;
    }
    public Status getStatus(){
        return status;
    }
    public LocalDateTime getCreatedAt(){
        return createdAt;
    }

    //Setters - only for description, priority and status. id, title, requester and createdAt are final and cannot be changed after initialization.
    public void setDescription(String description){
        this.description = description == null? "" : description.trim();
    }
    public void setPriority(Priority priority){
        this.priority = Objects.requireNonNull(priority);
    }
    public void setStatus(Status status){
        this.status = Objects.requireNonNull(status); //eg: t1.setStatus(Status.IN_PROGRESS);
    }

    //returns the age of the ticket in hours, calculated as the difference between the current time and the ticket's creation time.
    public long ageInHours(){ 
        return Duration.between(createdAt, LocalDateTime.now()).toHours();
    }

    //How many tickets have been created? we can use for eg : System.out.println(Ticket.totalCreated());
    public static int totalCreated(){ 
        return nextId - 1; //nextId is incremented after each ticket creation, so the total number of tickets created is nextId - 1.
    }

    //java already knows how to print an object, but it's default output isn't useful to us, so we write our own toString() method to provide a more meaningful representation of the ticket object.
    @Override
    public String toString(){
        return "#" + id + " [" + priority + "/" + status + "]" + title + " (" + requester + ")";
    }
}