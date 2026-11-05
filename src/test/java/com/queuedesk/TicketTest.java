package com.queuedesk;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Comparator;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class TicketTest {

    // ==========================================
    // TEST 1: Ticket stores the correct details
    // ==========================================

    @Test
    void ticketShouldStoreCorrectDetails() {

        // Arrange: create a BugTicket
        BugTicket ticket = new BugTicket(
                "Login Error",
                "Error 500 on submit",
                "Rahul",
                Priority.HIGH,
                Severity.CRITICAL,
                "Submit login form"
        );

        // Assert: check ticket fields
        assertEquals("Login Error", ticket.getTitle());
        assertEquals("Rahul", ticket.getRequester());
        assertEquals(Priority.HIGH, ticket.getPriority());
        assertEquals(Status.OPEN, ticket.getStatus());
    }


    // ==========================================
    // TEST 2: Ticket should reject blank title
    // ==========================================

    @Test
    void ticketShouldRejectBlankTitle() {

        // A blank title should cause IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {

            new BugTicket(
                    "   ",
                    "Description",
                    "Rahul",
                    Priority.HIGH,
                    Severity.CRITICAL,
                    "Steps"
            );

        });
    }


    // ==========================================
    // TEST 3: Ticket priority can be changed
    // ==========================================

    @Test
    void ticketPriorityCanBeUpdated() {

        BugTicket ticket = new BugTicket(
                "Login Error",
                "Description",
                "Rahul",
                Priority.LOW,
                Severity.MEDIUM,
                "Steps"
        );

        ticket.setPriority(Priority.CRITICAL);

        assertEquals(Priority.CRITICAL, ticket.getPriority());
    }


    // ==========================================
    // TEST 4: New tickets should have unique IDs
    // ==========================================

    @Test
    void ticketsShouldHaveDifferentIds() {

        BugTicket ticket1 = new BugTicket(
                "Bug 1",
                "Description",
                "Rahul",
                Priority.HIGH,
                Severity.HIGH,
                "Steps"
        );

        BugTicket ticket2 = new BugTicket(
                "Bug 2",
                "Description",
                "Priya",
                Priority.LOW,
                Severity.LOW,
                "Steps"
        );

        assertNotEquals(ticket1.getId(), ticket2.getId());
    }
        // ==========================================
    // TEST 5: Repository should store tickets
    // ==========================================

    @Test
    void repositoryShouldAddTickets() {

        Repository<BugTicket> repository = new Repository<>();

        BugTicket ticket = new BugTicket(
                "Login Error", "Description", "Rahul",
                Priority.HIGH, Severity.CRITICAL, "Steps"
        );

        repository.add(ticket);

        assertEquals(1, repository.all().size());
        assertEquals(ticket, repository.all().get(0));
    }


    // ==========================================
    // TEST 6: Repository should find ticket by ID
    // ==========================================

    @Test
    void repositoryShouldFindTicketById() {

        Repository<BugTicket> repository = new Repository<>();

        BugTicket ticket = new BugTicket(
                "Login Error", "Description", "Rahul",
                Priority.HIGH, Severity.CRITICAL, "Steps"
        );

        repository.add(ticket);

        BugTicket result = repository.findById(ticket.getId());

        assertEquals(ticket, result);
    }


    // ==========================================
    // TEST 7: Repository should throw exception for missing ID
    // ==========================================

    @Test
    void repositoryShouldThrowWhenTicketNotFound() {

        Repository<BugTicket> repository = new Repository<>();

        assertThrows(TicketNotFoundException.class, () -> {
            repository.findById(999999);
        });
    }


    // ==========================================
    // TEST 8: Repository all() should be unmodifiable
    // ==========================================

    @Test
    void repositoryAllShouldBeUnmodifiable() {

        Repository<BugTicket> repository = new Repository<>();

        BugTicket ticket = new BugTicket(
                "Login Error", "Description", "Rahul",
                Priority.HIGH, Severity.CRITICAL, "Steps"
        );

        repository.add(ticket);

        assertThrows(UnsupportedOperationException.class, () -> {
            repository.all().add(ticket);
        });
    }
        // ==========================================
    // TEST 9: Count tickets by priority
    // ==========================================

    @Test
    void analyticsShouldCountTicketsByPriority() {

        List<Ticket> tickets = new ArrayList<>();

        tickets.add(new BugTicket(
                "Bug 1", "Description", "Rahul",
                Priority.HIGH, Severity.HIGH, "Steps"
        ));

        tickets.add(new BugTicket(
                "Bug 2", "Description", "Priya",
                Priority.HIGH, Severity.MEDIUM, "Steps"
        ));

        tickets.add(new BugTicket(
                "Bug 3", "Description", "Aman",
                Priority.LOW, Severity.LOW, "Steps"
        ));

        TicketAnalytics analytics = new TicketAnalytics();

        Map<Priority, Long> result = analytics.countByPriority(tickets);

        assertEquals(2L, result.get(Priority.HIGH));
        assertEquals(1L, result.get(Priority.LOW));
    }


    // ==========================================
    // TEST 10: Calculate average effort
    // ==========================================

    @Test
    void analyticsShouldCalculateAverageEffort() {

        List<Ticket> tickets = new ArrayList<>();

        tickets.add(new BugTicket(
                "Bug 1", "Description", "Rahul",
                Priority.HIGH, Severity.HIGH, "Steps"
        ));

        tickets.add(new BugTicket(
                "Bug 2", "Description", "Priya",
                Priority.LOW, Severity.LOW, "Steps"
        ));

        TicketAnalytics analytics = new TicketAnalytics();

        double result = analytics.averageEffortHours(tickets);

        assertTrue(result > 0);
    }


    // ==========================================
    // TEST 11: Calculate total effort
    // ==========================================

    @Test
    void analyticsShouldCalculateTotalEffort() {

        List<Ticket> tickets = new ArrayList<>();

        tickets.add(new BugTicket(
                "Bug 1", "Description", "Rahul",
                Priority.HIGH, Severity.HIGH, "Steps"
        ));

        tickets.add(new BugTicket(
                "Bug 2", "Description", "Priya",
                Priority.LOW, Severity.LOW, "Steps"
        ));

        TicketAnalytics analytics = new TicketAnalytics();

        double result = analytics.totalEffortHours(tickets);

        assertTrue(result > 0);
    }


    // ==========================================
    // TEST 12: Find oldest open ticket
    // ==========================================

    @Test
    void analyticsShouldFindOldestOpenTicket() {

        List<Ticket> tickets = new ArrayList<>();

        BugTicket ticket = new BugTicket(
                "Bug 1", "Description", "Rahul",
                Priority.HIGH, Severity.HIGH, "Steps"
        );

        tickets.add(ticket);

        TicketAnalytics analytics = new TicketAnalytics();

        Optional<Ticket> result = analytics.oldestOpenTicket(tickets);

        assertTrue(result.isPresent());
        assertEquals(ticket.getId(), result.get().getId());
    }
        // ==========================================
    // TEST 13: Higher priority tickets come first
    // ==========================================

    @Test
    void ticketsShouldSortByPriorityDescending() {

        BugTicket highPriority = new BugTicket(
                "High priority bug", "Description", "Rahul",
                Priority.HIGH, Severity.HIGH, "Steps"
        );

        BugTicket lowPriority = new BugTicket(
                "Low priority bug", "Description", "Priya",
                Priority.LOW, Severity.LOW, "Steps"
        );

        // Negative result means highPriority comes before lowPriority
        assertTrue(highPriority.compareTo(lowPriority) < 0);
    }


    // ==========================================
    // TEST 14: Comparator should sort by assignee
    // ==========================================

    @Test
    void comparatorShouldSortTicketsByAssignee() {

        BugTicket ticketA = new BugTicket(
                "Bug A", "Description", "Rahul",
                Priority.HIGH, Severity.HIGH, "Steps"
        );

        BugTicket ticketB = new BugTicket(
                "Bug B", "Description", "Priya",
                Priority.LOW, Severity.LOW, "Steps"
        );

        ticketA.setAssignee("Zara");
        ticketB.setAssignee("Aman");

        Comparator<Ticket> comparator =
                TicketComparators.BY_ASSIGNEE_THEN_PRIORITY;

        // Aman should come before Zara alphabetically
        assertTrue(comparator.compare(ticketB, ticketA) < 0);
    }

}
