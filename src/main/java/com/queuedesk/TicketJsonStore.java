package com.queuedesk;

import com.fasterxml.jackson.databind.ObjectMapper; // ObjectMapper is a class provided by the Jackson library that allows us to convert Java objects to JSON and vice versa. It provides methods for reading and writing JSON data, as well as configuring serialization and deserialization settings
import com.fasterxml.jackson.databind.SerializationFeature; //SerializationFeature is an enumeration provided by the Jackson library that defines various features and options for controlling the serialization process. It allows us to customize how Java objects are converted to JSON.
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule; //JavaTimeModule is a module provided by the Jackson library that adds support for serializing and deserializing Java 8 date and time types, such as LocalDate, LocalDateTime, and ZonedDateTime. It enables proper handling of these types when converting to and from JSON.

import java.io.IOException; //used to handle input/output exceptions that may occur during file operations, such as reading from or writing to a JSON file. It allows us to catch and handle these exceptions appropriately.
import java.nio.file.Path; //Path is a class provided by the java.nio.file package that represents a file system path. It allows us to work with file and directory paths in a platform-independent manner. We can use Path to specify the location of the JSON file where tickets will be stored or retrieved.
import java.util.List; //List is an interface provided by the java.util package that represents an ordered collection of elements. It allows us to store and manipulate a list of tickets in memory. We can use List to hold the tickets loaded from the JSON file or to prepare a list of tickets to be saved to the JSON file.
import com.fasterxml.jackson.core.type.TypeReference; //TypeReference is a class provided by the Jackson library that allows us to specify the type of a generic collection when deserializing JSON data. It helps Jackson understand the specific type of objects contained within a collection, such as List<Ticket>, during the deserialization process.

//Build-T7 - Req2: TicketJsonStore class is responsible for storing and retrieving tickets in JSON format. It uses the Jackson library to serialize and deserialize ticket objects to and from JSON. The class provides methods to save tickets to a JSON file and load tickets from a JSON file. It also handles any exceptions that may occur during the serialization or deserialization process, such as InvalidTicketDataException and TicketNotFoundException.
public class TicketJsonStore {
    private final ObjectMapper mapper = new ObjectMapper() //create and configure the ObjectMapper only once. We will reuse this mapper for saving and loading tickets.
    .registerModule(new JavaTimeModule()) //register the JavaTimeModule to handle Java 8 date and time types.
    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) //disable the feature that writes dates as timestamps. Instead, dates will be serialized in a more human-readable format (ISO-8601).
    .enable(SerializationFeature.INDENT_OUTPUT); //enable the feature that indents the JSON output for better readability.
    
    // Build-T7 Req3: Saves a list of Ticket objects into a JSON file.
    public void save(List<Ticket> tickets, Path file)
        throws InvalidTicketDataException {
            try {

            // Explicitly tell Jackson that the list contains Ticket objects. This ensures @JsonTypeInfo is applied to each ticket subclass.
            mapper.writerFor(new TypeReference<List<Ticket>>() {})
            .writeValue(file.toFile(), tickets);

            } 
            catch (IOException e){
                // Wrap the original IOException in our custom exception.
                // This keeps Jackson-specific errors inside TicketJsonStore.
                throw new InvalidTicketDataException(
                    "Failed to save tickets", e);
            }
    }
    // Build-T7 Req4: Loads tickets from a JSON file and converts them back into Java objects.
   public List<Ticket> load(Path file)
        throws InvalidTicketDataException {

        try{
            // Read the JSON file and deserialize it into a List<Ticket>.
           // TypeReference tells Jackson the exact generic type we expect.
           return mapper.readValue(
                file.toFile(),
                new TypeReference<List<Ticket>>() {}
            );

        } 
        catch (IOException e) {
            // Wrap the original IOException in our custom exception.
           // Callers only need to know about InvalidTicketDataException.
           throw new InvalidTicketDataException(
            "Failed to load tickets", e);
        }
    }
    /*Req5: Exception handling boundary, Req5 is already satisfied by the save() and load() methods you implemented.*/

}
/*
Above performed is Method of chaining = 
new ObjectMapper()
    .registerModule(new JavaTimeModule())
    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    .enable(SerializationFeature.INDENT_OUTPUT);

rather than :
ObjectMapper mapper = new ObjectMapper();
mapper.registerModule(new JavaTimeModule());
mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
mapper.enable(SerializationFeature.INDENT_OUTPUT);

ObjectMapper - class provided by jackson library and mapper = reference variable , new ObjectMapper() = creates an ObjectMaper instance.
Jackson Methods -
1. registerModule() - is a method of ObjectMapper class that allows us to register additional modules to customize the behavior of the ObjectMapper. In this case, we are registering the JavaTimeModule to handle Java 8 date and time types.
new JavaTimeModule() - creates a javaTimeModule object. 
JavaTimeModule provides support for Java 8 date/time classes such as LocalDateTime.QueueDesk's ticket class contains createdAt field.Registering this module allows jackson to handle that field during json conversion. 

2.disable() - is a method of ObjectMapper class that allows us to disable specific serialization features. In this case, we are disabling the WRITE_DATES_AS_TIMESTAMPS feature, which means that dates will be serialized in a more human-readable format (ISO-8601) instead of as timestamps.
.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

3.enable() - is a method of ObjectMapper class that allows us to enable specific serialization features. In this case, we are enabling the INDENT_OUTPUT feature, which means that the JSON output will be indented for better readability.
.enable(SerializationFeature.INDENT_OUTPUT)

This enables pretty-printing of JSON.

Without indentation:
{"id":101,"title":"Login page crashes"}


With indentation:
{
  "id": 101,
  "title": "Login page crashes"
}
*/

