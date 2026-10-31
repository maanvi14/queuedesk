package com.queuedesk;
/*
 * Build-T5 Req1: InvalidTicketDataException extends Exception.
Invalid file data
 * This exception will be thrown when a line in ticket.txt contains invalid ticket data, for eg:
 * 1. wrong field count - Wrong number of fields.
 * 2. an unparseable Priority - Invalid priority such as URGENT when it is not a valid enum value.
 * 3. Invalid ticket information while parsing. 
 * 
 * Custom Checked Exception used when a ticket record contains invalid data while reading tickets.txt.
 * Extending exception makes this a checked exception.
 * Therefore, the caller must either handle it using try-catch block or declare it using the throws keyword.
 */
public class InvalidTicketDataException extends Exception {
    public InvalidTicketDataException(String message){ //a constructor that accepts an error message as input ie String message.
        super(message); //calls the parent exception class constructor and passes the error message to it. This allows us to retrieve the message later using getMessage().
    }
}
