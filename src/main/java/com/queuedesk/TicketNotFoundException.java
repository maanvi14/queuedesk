package com.queuedesk;
/*
Build-T5 Req2: TicketNotFoundException extends RunTimeException.
Ticket ID not found
Custom Unchecked Exception used when a requested ticket cannot be found in the repository.
Extending RuntimeException makes this an unchecked exception.
Therefore, the caller is not required to handle it using try-catch block or declare it using the throws keyword.
*/
public class TicketNotFoundException extends RuntimeException {
    public TicketNotFoundException(String message){ //a constructor that accepts an error message as input ie String message.
        super(message); // calls the parent RuntimeException class constructor and passes the error message to it. This allows us to retrieve the message later using getMessage().
    }
}
