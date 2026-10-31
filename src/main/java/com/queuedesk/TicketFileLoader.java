package com.queuedesk;
//java.io contains classes usd for input and output operations.
import java.io.BufferedReader;//BufferedReader is used to read text from a file one line at a time.
import java.io.IOException; //IOException represents errors that can occur while reading a file.
import java.nio.file.Files; //Files provides utility methods to open and read files.
import java.nio.file.Path; // Path represents the location of a file, such as tickets.txt.
import java.util.ArrayList; //ArrayList is a resizable collection used to store the records read from the file.
import java.util.List; //List is an interface used to represent an ordered collection of elements.We use List as the reference type and ArrayList as its implementation.

//Build-T5 Req3: TicketFileLoader class to read tickets from a file(tickets.txt) and seperating each line into individual fields.
public class TicketFileLoader {
    // Accepts the file path and returns the fields read from the file.
    public static List<String[]> load(Path file)
            throws IOException, InvalidTicketDataException {

        // Stores the fields of every line read from the file.
        List<String[]> records = new ArrayList<>();

        // Keeps track of the current line number.
        int lineNumber = 0;

        // try-with-resources automatically closes the reader after use.
        try (BufferedReader reader = Files.newBufferedReader(file)) {

            String line;

            // Reads the file one line at a time until the end is reached.
            while ((line = reader.readLine()) != null) {

                lineNumber++;

                try {
                    // Splits the line wherever the pipe (|) symbol appears.
                    String[] fields = line.split("\\|");

                    // Removes extra spaces from the beginning and end of each field.
                    for (int i = 0; i < fields.length; i++) {
                        fields[i] = fields[i].trim();
                    }

                    // Adds the processed fields to the records list.
                    records.add(fields);

                } 
                catch (RuntimeException e) {

                    // Wraps parsing errors in our custom checked exception.
                    throw new InvalidTicketDataException(
                            "Invalid data on line " + lineNumber + ": " + e.getMessage()
                    );
                }
            }
        }

        // Returns all the processed records.
        return records;
    }
   
}
