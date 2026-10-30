package com.groww.intern.queuedesk;

public class T3Demo{
    public static void main(String[] args) {
        BugTicket bug = new BugTicket("Login failure", "User cannot log in", "Maanvi", Priority.HIGH, Severity.CRITICAL, "Enter valid credentials and click Login");

        System.out.println(bug);
        System.out.println("Severity: " + bug.getSeverity());
        System.out.println("Steps: " + bug.getStepsToReproduce());
        System.out.println("Estimated effort: " + bug.estimateEffortHours() + " hours");
    }
}