package com.groww.intern.queuedesk;

public class T2Demo {
    public static void main(String[] args) {

        // 1. Create a bug ticket using the full constructor
        BugTicket a = new BugTicket(
            "Login Fails",
            "Error 500 on submit",
            "Asha",
            Priority.HIGH,
            Severity.CRITICAL,
            "Submit the login form"
        );

        // 2. Create another bug ticket
        BugTicket b = new BugTicket(
            "Printer jam",
            "",
            "Kiran",
            Priority.MEDIUM,
            Severity.LOW,
            "Check the printer paper tray"
        );

        // 3. Print both tickets
        System.out.println(a);
        System.out.println(b);

        // 4. Test the status setter
        a.setStatus(Status.IN_PROGRESS);
        System.out.println("Updated status: " + a.getStatus());

        // 5. Test the title getter
        System.out.println("Ticket title: " + a.getTitle());

        // 6. Test ticket age
        System.out.println("Ticket age: " + a.ageInHours() + " hours");

        // 7. Test total tickets created
        System.out.println("Total tickets created: " + Ticket.totalCreated());

        // 8. Test effort estimation
        System.out.println("Bug effort: " + a.estimateEffortHours() + " hours");
        System.out.println("Bug effort: " + b.estimateEffortHours() + " hours");
    }
}
