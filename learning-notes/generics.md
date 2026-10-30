## 1. WHAT ARE GENERICS IN JAVA?
1. In java, Generics are Parameterized type introduced in Java5 to allow classes, interfaces and methods to accept data types as parameter.
So, it allows us to write classes, interfaces and methods that can work with different data types.
They provide a way to reuse code with different inputs while enforcing strict type-checking at compile time.

They use type parameters (placeholders) such as T, E, K and V.
• T – Type (General purpose)
• E – Element (Heavily used by the Java Collections Framework, like List<E>)
• K – Key (Used in maps, like Map<K, V>)
• V – Value (Used in maps)

The actual data type is specified when the class or method is used.

```java
List<String> names = new ArrayList<>();
```
* **List**: The container (a collection).
* **<String>**: The label. It tells the compiler, *"This list can ONLY hold pieces of text (Strings)."*
* **Result**: You can add `"Hello"`, but if you try to add the number `100`, the system will stop you immediately.

---

## 2. What was the scenario before Generics?
Before Java 5, containers didn't have specific labels. They were treated as generic "Object" boxes that could hold absolutely anything mixed together.

```java
// Before Generics
List list = new ArrayList();

list.add("Hello");  // A String (Text)
list.add(100);      // An Integer (Number)
list.add(20.5);     // A Double (Decimal)
```

### Why this caused massive problems:

## Problem 1: No Type Safety
Because the list accepted everything, the computer couldn't double-check your work.
A collection could accept different types of objects. If you accidentally put a number into a list meant for names, nobody would notice until the application crashed while someone was using it.

## Problem 2: Manual typecasting was required
When pulling something out of the box, Java didn't remember what it was. You had to manually tell it what data type to expect. 
This was called explicit typecasting.
If we cast an object to an incompatible type, a ClassCastException could occur at runtime.

```java
String name = (String) list.get(0);  // Works fine because "Hello" is text.
String value = (String) list.get(1); // CRASH! Throws a 'ClassCastException'.
```
* **Why the crash?** `list.get(1)` is the number `100`. You cannot force a computer to convert a mathematical number directly into a text string like that without extra steps.
Why does the second line fail?
- list.get(1) returns an Integer object.
- We are trying to convert it into a String.
- This causes a ClassCastException.

## Problem 3: More conditional checks and code complexity
To avoid crashes, developers had to write repetitive logic to constantly check the "identity" of every item before touching it:

Developers sometimes used instanceof to check the type of each object.
They then manually cast the object to the appropriate type.
More data types meant more conditions and additional code.
This made programs harder to maintain.

```java
for (Object item : list) {
    if (item instanceof String) {
        // Safe to process as text
    } else if (item instanceof Integer) {
        // Safe to process as a number
    }
}
```
## Problem 4: Code duplication
Similar classes and methods had to be written separately for different data types.
This reduced code reusability and increased maintenance effort.

## 3. How did Generics solve these problems?
Generics introduced the concept of specifying the data type while declaring a collection or creating an object.

```java
// After Generics
List<String> names = new ArrayList<>();

names.add("Hello");
names.add("Java");
// names.add(100); <-- The code won't even compile! It blocks you instantly.
```
Only String objects can be added.
The compiler checks the data type at compile time.
Incompatible data types are rejected before program execution.
We do not need manual typecasting when retrieving elements.

```java
String name = names.get(0); //we don't need (String) anymore.
```

---

## 4. Before Generics vs. After Generics (Comparison Matrix)

| Feature / Scenario | Before Generics (Old Java) | After Generics (Modern Java) |
| :--- | :--- | :--- |
| **Storage Flexibility** | Collections could store mixed, random object types together. | You must specify exactly one required type per collection. |
| **Data Retrieval** | **Manual typecasting required** (you had to force convert types manually). | **No manual typecasting needed** (Java extracts the exact type automatically). |
| **Error Timing** | Dangerous errors and crashes happened **at runtime** (while the user ran the app). | Safety errors are caught immediately **at compile time** (while you are typing). |
| **Code Inspection** | Required **more `instanceof` checks** to guess what an object was. | Requires **fewer type checks** because the data type is guaranteed. |
| **Code Maintenance**| **More code duplication** (had to copy/paste code for different data types). | **Better code reusability** (write the logic once, use it for any data type). |
| **Overall Security** | Less type safety (high risk of data corruption or hidden bugs). | Improved type safety (the system completely protects your data boundaries). |

5. Why are Generics important?

1. Type Safety
   - Restricts the type of data that can be stored.
   - Detects incompatible types at compile time.
2. Code Reusability
   - Write one generic class or method and reuse it with different data types.
   - Avoids writing separate implementations for every type.
3. No Manual Typecasting
   - Retrieved values are already of the specified type.
   - Reduces casting-related errors.
4. Compile-Time Error Detection
   - Type mismatches are identified by the compiler rather than waiting for runtime.
5. Better Readability and Maintainability
   - Makes code easier to understand.
   - Clearly communicates which types a class or method accepts.
6. Reduced Code Duplication
   - A single generic implementation can handle multiple permitted types.

-- Before Generics, Java collections could hold objects without specifying their types, so developers often had to manually typecast retrieved values and handle type mismatches. Generics introduced parameterized types, allowing developers to specify the expected data type, improve type safety, reduce casting, and write reusable code.

## What is T in Generics?
- T is a type parameter.
- It acts as a placeholder for an actual data type.
- The actual type is provided when the generic class is instantiated.
- T is a convention; technically, another valid identifier can be used.
Example:
class Box<T> {

    private T value;

    public void set(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }
}


Using the class:
Box<String> box1 = new Box<>();
Box<Integer> box2 = new Box<>();


- In Box<String>, T becomes String.
- In Box<Integer>, T becomes Integer.
- The same class works with different data types.

## Bounded Type Parameters
Sometimes we don't want a generic class to accept every possible data type.

We can restrict the permitted types using the extends keyword.

Syntax:
class ClassName<T extends SomeClass> {
}


Meaning:
- T must be the specified class or one of its subclasses.
- This is called an upper-bounded type parameter.
Example from QueueDesk:
public class Repository<T extends Ticket> {
    private List<T> tickets = new ArrayList<>();
}


Here:
- T is the placeholder.
- extends Ticket restricts T to Ticket or its subclasses.
- The repository can store BugTicket, FeatureRequestTicket, etc.
- It cannot store unrelated objects such as String or Integer.

## Generic Methods
A generic method is a method that declares its own type parameter.

Syntax:
public static <T> void display(T value) {
    System.out.println(value);
}


- <T> declares the type parameter.
- T is the return/input type.
- The method can work with different data types.
Example:
display("Hello");
display(100);
display(20.5);


QueueDesk example:
public static <T extends Ticket> T oldest(List<T> tickets)


- Accepts a list of Ticket or its subclasses.
- Returns an object of the same generic type T.
- Allows the method to work with different ticket types.

## What is the difference between T and ?
- T is a named type parameter.
- ? is a wildcard representing an unknown type.
- T is used when we want to refer to the same type at multiple places.
- ? is useful when we only need to work with a collection of an unknown type.
Example:
List<T> tickets;
List<?> items;

Wildcards can also have bounds, such as ? extends Ticket and ? super Ticket. Learn these after understanding basic Generics.

## What is Type Erasure?
- Generics are primarily a compile-time feature in Java.
- During compilation, the compiler performs type checks and then removes or erases most generic type information.
- This process is called Type Erasure.
- It helps maintain compatibility with older Java code.
For example:
List<String> names = new ArrayList<>();

The runtime representation is essentially a regular List; the generic type information is not generally retained as a runtime type parameter.

12. QueueDesk – Where am I using Generics?
Code	Purpose
List<Ticket>	Stores Ticket objects
Set<String>	Stores String tags
Map<String, List<T>>	Maps an assignee to a list of tickets
Repository<T extends Ticket>	Generic repository restricted to Ticket types
<T extends Ticket> T oldest(...)	Generic method returning the oldest ticket