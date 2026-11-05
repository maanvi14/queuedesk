//Build:T3 - Req3: A ticket for requesting a new feature.

package com.queuedesk;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

// votes = how many users want this feature.
// businessJustification = why implementing it is valuable.
public class FeatureRequestTicket extends Ticket {

    // Feature-specific fields
    private int votes;
    private final String businessJustification;

    // Constructor: initializes common and feature-specific fields.
    // @JsonCreator tells Jackson to use this constructor when loading JSON.
    @JsonCreator
    public FeatureRequestTicket( @JsonProperty("title") String title, 
                                 @JsonProperty("description") String description, 
                                 @JsonProperty("requester") String requester, 
                                 @JsonProperty("priority") Priority priority, 
                                 @JsonProperty("votes") int votes, 
                                 @JsonProperty("businessJustification") String businessJustification) {

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
