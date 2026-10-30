# Priority Queue in Java

## 1. What is a Priority Queue?

- A Priority Queue is a data structure in which elements are served according to their priority rather than insertion order.
- The element with the highest priority according to the defined ordering is served first.
- It is generally implemented using a **Binary Heap**.
- Insertion and deletion take O(log n) time.
- Accessing the head element takes O(1) time.

---

## 2. Priority Queue in C++ vs Java

### C++ Syntax

```cpp
#include <queue>
using namespace std;

priority_queue<int> pq;

// Insert
pq.push(10);

// Remove highest-priority element
pq.pop();

// Access top element
pq.top();

// Check empty
pq.empty();
```

**Important:**
- C++ Priority Queue is a **Max Heap by default**.
- Largest element comes first.

### Java Syntax

```java
import java.util.PriorityQueue;

PriorityQueue<Integer> pq = new PriorityQueue<>();

// Insert
pq.offer(10);

// Remove head element
pq.poll();

// Access head element
pq.peek();

// Check empty
pq.isEmpty();
```

**Important:**
- Java PriorityQueue is a **Min Heap by default**.
- Smallest element comes first.

---

## 3. C++ vs Java – Important Syntax Changes

**Remember these conversions:**

1. C++ `push()` → Java `offer()`
2. C++ `pop()` → Java `poll()`
3. C++ `top()` → Java `peek()`
4. C++ `empty()` → Java `isEmpty()`
5. C++ `size()` → Java `size()`

**Major difference:**

- C++ gives Max Heap by default.
- Java gives Min Heap by default.
- Java allows us to define custom ordering using Comparator.

---

## 4. Types of Priority Queues in Java

### A. Min Heap (Default)

```java
PriorityQueue<Integer> pq = new PriorityQueue<>();

pq.offer(30);
pq.offer(10);
pq.offer(20);

System.out.println(pq.poll()); // 10
```

**Explanation:**
- Java uses natural ascending order by default.
- The smallest element is removed first.

### B. Max Heap

```java
PriorityQueue<Integer> pq =
    new PriorityQueue<>(Comparator.reverseOrder());

pq.offer(30);
pq.offer(10);
pq.offer(20);

System.out.println(pq.poll()); // 30
```

**Explanation:**
- `Comparator.reverseOrder()` reverses the natural ordering.
- The largest element is removed first.

---

## 5. Important PriorityQueue Methods

**1. offer(element)**
- Inserts an element into the queue.
- Similar to `push()` in C++.

**2. poll()**
- Removes and returns the head element.
- Returns null if the queue is empty.
- Similar to `pop()` in C++, but unlike C++, it also returns the removed element.

**3. peek()**
- Returns the head element without removing it.
- Similar to `top()` in C++.

**4. isEmpty()**
- Checks whether the queue is empty.

**5. size()**
- Returns the number of elements.

**6. clear()**
- Removes all elements from the queue.

---

## 6. Custom Priority Queue Using Comparator

Sometimes we don't want the default ordering.

We can define our own ordering using a Comparator.

Example: Max Heap using Comparator.

```java
PriorityQueue<Integer> pq =
    new PriorityQueue<>((a, b) -> Integer.compare(b, a));
```

**Explanation:**
- Comparator decides which element should come first.
- `Integer.compare(b, a)` reverses the natural ascending order.
- Therefore, the largest element comes first.

---

## 7. Comparable vs Comparator in PriorityQueue

### Comparable

- Defines the natural ordering of objects.
- Implemented inside the class itself.
- Uses the `compareTo()` method.

Example:

```java
public class Ticket implements Comparable<Ticket> {
    @Override
    public int compareTo(Ticket other) {
        // Define natural ordering here.
    }
}
```

### Comparator

- Defines custom ordering externally.
- Does not require changing the original class.
- Uses the `compare()` method or comparator-building methods.

Example:

```java
Comparator<Ticket> byPriority =
    Comparator.comparing(Ticket::getPriority);
```

**Remember:**
- Comparable → Natural ordering.
- Comparator → Custom ordering.

---

## 8. PriorityQueue in QueueDesk Project

We are maintaining TWO priority queues.

### Queue 1: Natural Ordering

```java
private final PriorityQueue<Ticket> naturalQueue =
    new PriorityQueue<>();
```

**How does it work?**

- Uses the `compareTo()` method implemented in `Ticket.java`.
- Higher priority tickets are served first.
- If priorities are equal, the older ticket is served first.

**Ordering sequence:**

1. CRITICAL
2. HIGH
3. MEDIUM
4. LOW

For tickets with equal priority, `createdAt` decides the order, with the older ticket first.

### Queue 2: Custom Ordering

```java
private final PriorityQueue<Ticket> customQueue =
    new PriorityQueue<>(TicketComparators.BY_ASSIGNEE_THEN_PRIORITY);
```

**How does it work?**

- Uses the Comparator defined in `TicketComparators.java`.
- First sorts by assignee in alphabetical order.
- If assignees are equal, higher priority comes first.
- Null assignees are handled using `Comparator.nullsFirst()`.

**Important:** This queue does not necessarily serve the highest-priority ticket globally first because assignee ordering comes first.

---

## 9. Adding and Removing Tickets

**offer() – Add ticket**

```java
naturalQueue.offer(ticket);
customQueue.offer(ticket);
```

- Adds the ticket to both queues.
- Each queue independently maintains its own ordering.

**poll() – Serve next ticket**

```java
Ticket next = naturalQueue.poll();
```

- Removes and returns the next ticket according to the queue's ordering.
- Returns null if the queue is empty.

**Important:** Polling one queue does not remove the corresponding ticket from the other queue.

---

## 10. Time Complexity

**1. offer() – O(log n)**
- Inserts an element and restores heap ordering.

**2. poll() – O(log n)**
- Removes the head and restores heap ordering.

**3. peek() – O(1)**
- Returns the head without removing it.

**4. isEmpty() – O(1)**
- Checks whether the queue is empty.

**5. size() – O(1)**
- Returns the number of elements.

---

## 11. Important Points to Remember

1. Java PriorityQueue is a Min Heap by default.
2. C++ priority_queue is a Max Heap by default.
3. Java uses `offer()` to insert and `poll()` to remove.
4. `peek()` reads the head without removing it.
5. Custom ordering can be defined using Comparator.
6. PriorityQueue does not maintain a fully sorted list internally.
7. Iterating over a PriorityQueue does not guarantee sorted order. Repeatedly using `poll()` gives elements in priority order.
8. Java PriorityQueue does not allow null elements.
9. Java PriorityQueue is not thread-safe by default.

---

## 12. Quick Revision – C++ to Java

**C++ → Java**

- `push()` → `offer()`
- `pop()` → `poll()`
- `top()` → `peek()`
- `empty()` → `isEmpty()`
- Default Max Heap → Default Min Heap
