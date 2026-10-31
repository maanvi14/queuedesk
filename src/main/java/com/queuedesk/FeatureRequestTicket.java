//Build:T3 - Req3: A ticket for requesting a new feature.

package com.queuedesk;

import java.util.Objects;

// votes = how many users want this feature.
// businessJustification = why implementing it is valuable.
public class FeatureRequestTicket extends Ticket {

    // Feature-specific fields
    private int votes;
    private final String businessJustification;

    // Constructor: initializes common and feature-specific fields.
    public FeatureRequestTicket(String title, String description, String requester, Priority priority, int votes, String businessJustification) {

        // Initialize the common Ticket fields.
        super(title, description, requester, priority);

        // Votes cannot be negative.
        if (votes < 0) {
            throw new IllegalArgumentException("Votes cannot be negative");
        }
        this.votes = votes;

        // Business justification cannot be null.
        this.businessJustification = Objects.requireNonNull(businessJustification, "Business justification cannot be null").trim();
    }

    public int getVotes() {
        return votes;
    }

    // Updates votes, ensuring the value is not negative.
    public void setVotes(int votes) {
        if (votes < 0) {
            throw new IllegalArgumentException("Votes cannot be negative");
        }
        this.votes = votes;
    }

    public String getBusinessJustification() {
        return businessJustification;
    }

    // Provides an effort estimate for a feature request.
    @Override
    public double estimateEffortHours() {
        return 8.0;
    }
}
