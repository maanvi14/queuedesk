## 1. Exception Handling in Java

An **exception** is an event that interrupts the normal flow of a
program. 
For example: 
- A file does not exist. 
- A line in `tickets.txt` contains an invalid priority. 
- A requested ticket ID cannot be found.

Java uses exceptions so that errors can be handled separately from the
normal program logic.

## `try`, `catch`, and `finally`

- `try`: contains code that might cause an exception.
- `catch`: handles an exception thrown from the associated `try` block.
- `finally`: runs whether an exception occurs or not (with a few exceptional JVM termination cases). It is commonly used for cleanup when resources are not managed by try-with-resources.

``` java
try{
    int number = Integer.parseInt("abc");
}
catch(NumberFormatException e){
    System.out.println("The value is not a valid number.");
}
finally {
    System.out.println("Parsing attempt finished.");
}
```

### Checked Exceptions vs Unchecked Exceptions

Java exceptions are commonly discussed in two groups:

# Differences Between Checked and Unchecked Exceptions in Java

| Feature | Checked Exception | Unchecked Exception |
| :--- | :--- | :--- |
| **Checked by Compiler** | Yes, at compile time | No, checked at runtime |
| **Handling Requirement** | Must be handled (`try-catch`) or declared (`throws`) | Optional to handle or declare |
| **Inheritance Hierarchy** | Subclass of `Exception` (excluding `RuntimeException`) | Subclass of `RuntimeException` |
| **Common Causes** | External or recoverable issues (e.g., missing files, broken network links) | Programming logic flaws or bugs (e.g., null pointers, division by zero) |
| **Examples** | `IOException`, `SQLException`, `ClassNotFoundException` | `NullPointerException`, `ArithmeticException`, `ArrayIndexOutOfBoundsException` |


## Checked exceptions

- Checked by the compiler.
- A method must either handle the exception with `try-catch` or declare it using `throws`.
- Often represent conditions the caller may reasonably be expected to handle.
-   Example: `IOException`.

``` java
public void readFile() throws IOException {
    // File-reading code that may throw IOException.
}
```

If a method declares `throws IOException`, it is telling the caller that this operation may fail with an I/O-related problem.

#### Unchecked exceptions

- Subclasses of `RuntimeException` (and `Error`, which is a separate category).
- The compiler does not require callers to catch or declare them.
- Often represent invalid arguments, invalid state, or programmingmistakes.
- Example: `IllegalArgumentException`, `NullPointerException`.

``` java
public Ticket findTicket(int id) {
    // May throw an unchecked exception if the ticket is not found.
    throw new IllegalArgumentException("Ticket not found: " + id);
}
```
## 2. Custom Exceptions

A **custom exception** is an exception class created for an application's own error conditions. It gives the error a meaningful name and can carry extra information.

### Custom checked exception

Extend `Exception`:

``` java
public class InvalidTicketDataException extends Exception {

    public InvalidTicketDataException(String message) {
        super(message);
    }
}
```

-   `extends Exception` makes it checked.
-   `super(message)` passes the message to the parent `Exception` class.
-   The caller must catch it or declare it with `throws`.

For Build T5, the exception should include the offending line number in its message, so the user can identify the bad line in `tickets.txt`.

### Custom unchecked exception

Extend `RuntimeException`:

``` java
public class TicketNotFoundException extends RuntimeException {

    public TicketNotFoundException(String message) {
        super(message);
    }
}
```

-   `extends RuntimeException` makes it unchecked.
-   Callers are not required to catch or declare it.
-   It can be used by `Repository.findById(int)` when no ticket matches.

### When to use each in this project

- Use `InvalidTicketDataException` for malformed input while loading a file. This is a checked exception because loading code should explicitly account for invalid file data.
- Use `TicketNotFoundException` when a requested ticket ID does not exist. This is unchecked, as specified in Build T5.

# Differences Between Custom Checked and Custom Unchecked Exceptions in Java

| Feature | Custom Checked Exception | Custom Unchecked Exception |
| :--- | :--- | :--- |
| **Base Class to Extend** | `java.lang.Exception` | `java.lang.RuntimeException` |
| **When to Use** | For predictable but unavoidable external errors that the caller **must** recover from. | For business logic violations, invalid program states, or errors caused by bad arguments. |
| **Compiler Enforcement** | Forces the calling method to handle it via `try-catch` or declare it via `throws`. | Does not force the caller to handle or declare it. |
| **Example Scenario** | Insufficient funds during a bank transfer (caller must decide to retry or notify user). | Invalid age format passed into a user profile validator (indicates a logic or validation bug). |

## Code Templates

### Custom Checked Exception
```java
public class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
```

### Custom Unchecked Exception
```java
public class InvalidAgeException extends RuntimeException {
    public InvalidAgeException(String message) {
        super(message);
    }
}
```


------------------------------------------------------------------------

## 3. `throws` vs `throw`

These keywords have different purposes:

-   `throw` actually throws an exception object.
-   `throws` declares in a method signature that the method may pass an
    exception to its caller.

``` java
// 'throws' declares the possible exception.
public void loadTickets() throws InvalidTicketDataException {
    // 'throw' throws a specific exception object.
    throw new InvalidTicketDataException("Invalid ticket data on line 4");
}
```

Remember: - `throw` = perform the throw. - `throws` = declare the
possibility in the method signature.

# Differences Between throw and throws Keywords in Java

| Feature | `throw` | `throws` |
| :--- | :--- | :--- |
| **Purpose** | Used to explicitly trigger and throw a specific exception. | Used to declare that a method might throw one or more exceptions. |
| **Location** | Used inside a method body or block. | Used in the method signature (header). |
| **Syntax Target** | Followed by an exception *instance/object* (using `new`). | Followed by exception *class names* (comma-separated if multiple). |
| **Count** | Can only throw **one** exception object at a time. | Can declare **multiple** exception classes simultaneously. |
| **Execution** | It is an active execution statement that interrupts program flow. | It is a compile-time declaration with no runtime execution impact. |


------------------------------------------------------------------------

## 4. Try-with-resources

The try-with-resources statement automatically closes resources when the `try` block finishes, whether it completes normally or exits abruptly due to an exception. 

## Key Characteristics

* **Automatic Resource Management:** Eliminates the need for explicit `finally` blocks solely dedicated to closing resources.
* **Requirements:** Any resource declared within the parentheses must implement the `java.lang.AutoCloseable` or `java.io.Closeable` interface.
* **Scope:** The declared resources are scoped strictly to the `try` block.

## Common Use Cases

* **File Streams:** Managing raw byte or character flows like `FileInputStream` or `FileOutputStream`.
* **Buffered I/O:** Reading and writing text efficiently using `BufferedReader` and `BufferedWriter`.
* **Database Connections:** Ensuring `Connection`, `Statement`, and `ResultSet` objects are released to the connection pool.

## Practical Examples

### Reading a File
```java
try (BufferedReader reader = Files.newBufferedReader(path)) {
    String line;
    while ((line = reader.readLine()) != null) {
        System.out.println(line);
    }
}
```

### Writing a File
```java
try (BufferedWriter writer = Files.newBufferedWriter(path)) {
    writer.write("Ticket report");
    writer.newLine();
}
```

## System Implementation (QueueDesk Build T5)

* **Ticket Loading:** The `TicketFileLoader.load(Path file)` component reads `tickets.txt` line by line using a `BufferedReader` enclosed in a try-with-resources block.
* **Report Generation:** The report generation component writes output metrics using a `BufferedWriter` inside a try-with-resources block.
* **Outcome:** Prevents file descriptor leaks by ensuring reader and writer handles are reliably closed under all execution paths.

------------------------------------------------------------------------

## 5. String Handling

A Java `String` represents text.

### Strings are immutable

**Immutable** means that once a `String` object is created, its contents cannot be changed. String methods that appear to modify text return a new `String`.

``` java
String title = "  Login Error  ";

String cleanedTitle = title.trim();

System.out.println(title);        // Original string remains unchanged.
System.out.println(cleanedTitle); // Trimmed string.
```

### Useful String methods

- `trim()` --- removes leading and trailing whitespace characters covered by `trim()`.
- `isBlank()` --- returns `true` if the string is empty or contains only whitespace.
- `isEmpty()` --- returns `true` only if the string has zero characters.
-  `split("\\|")` --- splits a string around the pipe (`|`) delimiter. The backslash is escaped because the argument is a Java string and the pipe has a special meaning in regular expressions.
- `substring(begin, end)` --- returns part of a string. The start index is included; the end index is excluded.
- `length()` --- returns the number of characters in the string.

### Example: splitting a pipe-delimited line

Suppose a file line looks like this:

``` text
101|Login Error|HIGH|OPEN|Rahul
```

Split it using:

``` java
String[] fields = line.split("\\|");

for (String field : fields) {
    System.out.println(field.trim());
}
```

The fields are separated at each pipe character. Calling `trim()`
removes surrounding whitespace from each field.

### Safe substring / truncation

Do not assume that a string is long enough for a requested substring.
Check its length first.

``` java
String description = ticket.getDescription();

int maxLength = 40;

String displayText;

if (description.length() > maxLength) {
    displayText = description.substring(0, maxLength) + "...";
} else {
    displayText = description;
}
```

-   `substring(0, maxLength)` takes characters from index `0` up to, but
    not including, `maxLength`.
-   The length check prevents an invalid substring range.
-   This is useful when displaying long ticket descriptions on the
    triage board.

**Build T5 reminder:** Revisit the T-01 setters and use `trim()`, blank
checks, and safe `substring()` truncation for long descriptions.

------------------------------------------------------------------------

## 6. StringBuilder

`StringBuilder` is a mutable sequence of characters. Unlike `String`,
its contents can be changed without creating a new string for every
append operation.

It is useful when constructing text in multiple steps, especially inside
loops.

### Example using String concatenation

``` java
String report = "";

for (Ticket ticket : tickets) {
    report = report + ticket + "\n";
}
```

This repeatedly creates new `String` objects as the report grows.

### Example using StringBuilder

``` java
StringBuilder report = new StringBuilder();

for (Ticket ticket : tickets) {
    report.append(ticket);
    report.append("\n");
}

String finalReport = report.toString();
```

-   `new StringBuilder()` creates a mutable text builder.
-   `append()` adds text to the end.
-   `toString()` converts the completed content into a `String`.

### Useful StringBuilder methods

-   `append(value)` --- adds text to the end.
-   `append('\n')` --- adds a newline.
-   `toString()` --- returns the final text as a `String`.
-   `length()` --- returns the current number of characters.

### QueueDesk use

Build T5's report generator uses `StringBuilder` to assemble an
end-of-day summary containing: - Ticket counts grouped by status. -
Ticket counts grouped by priority. - The oldest open ticket. - Every
overdue `CRITICAL` ticket.

After assembling the report, convert it to a string if needed, then
write it using a `BufferedWriter`.

------------------------------------------------------------------------

## 7. Quick revision

1.  **Checked exception:** Compiler requires handling or declaration.
    Example: `IOException`, `InvalidTicketDataException`.
2.  **Unchecked exception:** Compiler does not require handling or
    declaration. Example: `NullPointerException`,
    `TicketNotFoundException`.
3.  **Custom exception:** An application-defined exception class.
4.  **`throw`:** Throws an exception object.
5.  **`throws`:** Declares possible exceptions in a method signature.
6.  **Try-with-resources:** Automatically closes resources such as
    readers and writers.
7.  **`trim()`:** Removes leading and trailing whitespace covered by the
    method.
8.  **`isBlank()`:** Checks whether a string is empty or contains only
    whitespace.
9.  **`split("\\|")`:** Splits a pipe-delimited line.
10. **`substring(start, end)`:** Extracts part of a string; `end` is
    excluded.
11. **`String`:** Immutable text.
12. **`StringBuilder`:** Mutable text builder, useful for assembling
    reports.

------------------------------------------------------------------------

# Build T5 --- Loading, Saving, and Reporting

## Requirements from the project brief

### 1. Custom exceptions

-   Create `InvalidTicketDataException extends Exception` (checked).
-   It is thrown while parsing a bad line in `tickets.txt`, such as:
    -   Wrong field count.
    -   An unparseable `Priority`.
-   Include the offending line number in the message.
-   Create `TicketNotFoundException extends RuntimeException`
    (unchecked).
-   `Repository.findById(int)` throws it when no ticket matches.
-   The console application should catch it once at the top level and
    print a clean error message instead of a stack trace.

### 2. Ticket file loader

Create `TicketFileLoader.load(Path file)`.

-   Read `tickets.txt` line by line.
-   Use `BufferedReader` inside try-with-resources.
-   Split each line using `String.split("\\|")`.
-   Apply `trim()` to each field.
-   Wrap parsing failures in `InvalidTicketDataException`.
-   Include the line number in the exception message.

### 3. Report generator

-   Generate an end-of-day summary.
-   Count tickets per status.
-   Count tickets per priority.
-   Include the oldest open ticket.
-   Include every overdue `CRITICAL` ticket.
-   Use `StringBuilder` to assemble the report.
-   Write the report using `BufferedWriter` inside try-with-resources.

### 4. Revisit T-01 setters

-   Use `trim()` to remove surrounding whitespace.
-   Use blank checks to reject empty or whitespace-only values where
    appropriate.
-   Use safe `substring()` truncation for long descriptions on the
    triage board.

## Suggested implementation order

1.  Learn and implement the two custom exception classes.
2.  Update `Repository.findById(int)` to throw
    `TicketNotFoundException`.
3.  Implement `TicketFileLoader` and test valid and invalid input lines.
4.  Implement the report generator and test its counts and ticket
    sections.
5.  Revisit the T-01 setters and triage board display truncation.
6.  Create `T5Demo.java` to demonstrate loading, error handling, saving,
    and reporting.

**Start with the exception classes.** They establish the error-handling
behavior that the loader and repository will use.
