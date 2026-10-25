package Fundamentals;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Product {
    private int id;
    private String name;

    public Product(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Product{id=" + id + ", name='" + name + "'}"; // Makes Product objects readable when printed
    }
}

class Order {
    private int id;
    private List<Product> products;

    public Order(int id, List<Product> products) {
        this.id = id;
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }
}

public class Level05 {

    public static void main(String[] args) {

        List<Product> order1 = List.of(
                new Product(1, "Laptop"),
                new Product(2, "Mouse")
        ); // Products belonging to the first order

        List<Product> order2 = List.of(
                new Product(3, "Keyboard"),
                new Product(4, "Monitor")
        ); // Products belonging to the second order

        List<Order> orders = List.of(
                new Order(101, order1),
                new Order(102, order2)
        ); // Test data containing multiple orders and their products

        List<List<Integer>> list1 = List.of(
                List.of(1, 2, 3),
                List.of(4, 5),
                List.of(6, 7, 8)
        ); // Nested list for practising flatMap()

        List<Employee> list = List.of(
                new Employee(1, "Amit", "IT", 75000, 28),
                new Employee(2, "Priya", "HR", 55000, 25),
                new Employee(3, "Rahul", "IT", 90000, 32),
                new Employee(4, "Sneha", "Finance", 65000, 29),
                new Employee(5, "Vikas", "HR", 60000, 35),
                new Employee(6, "Neha", "IT", 85000, 26),
                new Employee(7, "Karan", "Finance", 70000, 31),
                new Employee(8, "Pooja", "IT", 75000, 24)
        ); // Employee test data containing different departments and salaries

        List<Integer> numbers = List.of(1, 2, 2, 3, 3, 3, 4, 4, 4, 4, 5); // Numbers with different frequencies
        List<Integer> numbers2 = List.of(1, 2, 3, 4, 5, 7); // Distinct numbers with 6 missing from the range

        Map<String, Integer> map1 = Map.of("Java", 10, "Python", 20); // First map for merging
        Map<String, Integer> map2 = Map.of("Java", 15, "Python", 5, "SQL", 8); // Second map with overlapping keys

        // Uncomment one method at a time to practise individual Stream operations
        // flatlist(list1);                    // Q1: Flatten a nested list using flatMap()
        // flatprodutsorders(orders);          // Q2: Flatten orders into a single list of products
        // mapempid(list);                     // Q3: Convert employees into a Map keyed by employee ID
        // mapempid(list);                     // Q4: Handle duplicate keys safely when collecting into a Map
        // allsalary(list);                    // Q5: Calculate total employee salaries using mapToDouble()
        // top3freq(numbers);                  // Q6: Find the three most frequently occurring elements
        // findmissing(numbers2, 7);           // Q7: Find the missing number from the range 1 to N
        // mergemap(map1, map2);               // Q8: Merge two maps by summing values for matching keys
        // longconse(numbers2);                // Q9: Find the longest consecutive sequence
        sumUsingCustomCollect(numbers2);      // Q10: Aggregate numbers using collect() and a mutable container
    }

    // Q1: Flatten a List<List<Integer>> into a single List<Integer> using flatMap()
    private static void flatlist(List<List<Integer>> list1) {
        List<Integer> ans = list1.stream()                    // Create a Stream containing multiple lists
                .flatMap(List::stream)                        // Convert each inner list into a stream and flatten them
                .collect(Collectors.toList());                // Collect all integers into one List

        System.out.println("Flattened list: " + ans);          // Print the single-level list
    }

    // Q2: Flatten a list of orders into a list containing all products
    private static void flatprodutsorders(List<Order> orders) {
        List<Product> products = orders.stream()               // Create a Stream of Order objects
                .flatMap(order -> order.getProducts().stream()) // Convert each order's products into a stream and flatten them
                .collect(Collectors.toList());                 // Collect all products into one List

        products.forEach(product ->
                System.out.println(product.getName()));        // Print the name of every product across all orders
    }

    // Q3 and Q4: Convert employees into a Map keyed by ID and handle duplicate keys safely
    private static void mapempid(List<Employee> list) {
        Map<Integer, Employee> map = list.stream()             // Create a Stream of Employee objects
                .collect(Collectors.toMap(
                        Employee::getId,                      // Use employee ID as the Map key
                        Function.identity(),                  // Store the complete Employee object as the value
                        (existing, replacement) -> existing,  // Keep the existing employee if duplicate IDs occur
                        LinkedHashMap::new                     // Preserve employee encounter order in the resulting Map
                ));

        System.out.println("Employees by ID: " + map);         // Print the employee lookup Map
    }

    // Q5: Calculate the sum of employee salaries using a primitive stream
    private static void allsalary(List<Employee> list) {
        double ans = list.stream()                             // Create a Stream of Employee objects
                .mapToDouble(Employee::getSalary)              // Convert salaries into a primitive DoubleStream
                .sum();                                        // Calculate the total without boxing each salary into Double

        System.out.println("Total salary: " + ans);             // Print the total salary expenditure
    }

    // Q6: Find the three most frequently occurring elements in a list
    private static void top3freq(List<Integer> numbers) {
        List<Integer> ans = numbers.stream()                   // Create a Stream of integers
                .collect(Collectors.groupingBy(
                        Function.identity(),                   // Use each number as its own grouping key
                        LinkedHashMap::new,                    // Preserve the first-appearance order of distinct numbers
                        Collectors.counting()                  // Count how many times each number occurs
                ))
                .entrySet()                                    // Convert the frequency Map into a Stream of entries
                .stream()
                .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed() // Sort from highest frequency to lowest
                        .thenComparing(Map.Entry.comparingByKey())) // Break frequency ties by ascending number
                .limit(3)                                      // Keep at most three entries
                .map(Map.Entry::getKey)                        // Extract the numbers from the frequency entries
                .collect(Collectors.toList());                // Collect the top three numbers into a List

        System.out.println("Top three frequent elements: " + ans); // Print the most frequent numbers
    }

    // Q7: Find the missing number from a list containing distinct integers in the range 1 to N
    private static void findmissing(List<Integer> numbers2, int n) {
        int expectedSum = n * (n + 1) / 2;                     // Calculate the sum of all integers from 1 to N

        int actualSum = numbers2.stream()                      // Create a Stream of the provided numbers
                .mapToInt(Integer::intValue)                   // Convert Integer objects into primitive int values
                .sum();                                        // Calculate the sum of the available numbers

        int ans = expectedSum - actualSum;                     // Subtract the actual sum to obtain the missing number

        System.out.println("Missing number: " + ans);          // Print the missing number
    }

    // Q8: Merge two maps by summing values for matching keys
    private static void mergemap(Map<String, Integer> map1, Map<String, Integer> map2) {
        Map<String, Integer> ans = Stream.concat(             // Combine the entry streams from both maps
                        map1.entrySet().stream(),
                        map2.entrySet().stream()
                )
                .collect(Collectors.toMap(
                        Map.Entry::getKey,                    // Use each entry's key as the resulting Map key
                        Map.Entry::getValue,                  // Use each entry's value as the initial value
                        Integer::sum,                         // Add values when the same key occurs in both maps
                        LinkedHashMap::new                    // Store merged entries in insertion order
                ));

        System.out.println("Merged map: " + ans);              // Print the merged Map
    }

    // Q9: Find the longest consecutive sequence of integers in a list
    private static void longconse(List<Integer> numbers2) {
        Set<Integer> lookup = new HashSet<>(numbers2);         // Build a set for efficient membership checks

        List<Integer> ans = lookup.stream()                   // Process unique numbers to avoid duplicate sequences
                .filter(n -> !lookup.contains(n - 1))         // Identify sequence starts with no preceding integer
                .map(n -> {                                   // Build a consecutive sequence starting at each candidate
                    List<Integer> sequence = new ArrayList<>();
                    int current = n;

                    while (lookup.contains(current)) {        // Continue while the next consecutive number exists
                        sequence.add(current++);              // Add the current number and advance to the next
                    }

                    return sequence;                          // Return the sequence starting at this number
                })
                .max(Comparator.comparingInt(List::size))     // Select the sequence containing the most numbers
                .orElse(Collections.emptyList());             // Return an empty list if the input contains no numbers

        System.out.println("Longest consecutive sequence: " + ans); // Print the longest sequence
    }

    // Q10: Implement custom aggregation using collect() and a mutable result container
    private static void sumUsingCustomCollect(List<Integer> numbers) {
        int ans = numbers.stream()                             // Create a Stream of integers
                .collect(
                        () -> new int[1],                     // Create a mutable one-element array to hold the running sum
                        (sum, n) -> sum[0] += n,              // Accumulate each number into the running sum
                        (sum1, sum2) -> sum1[0] += sum2[0]    // Combine partial sums when stream partitions are merged
                )[0];                                         // Extract the final sum from the array

        System.out.println("Sum using custom collect: " + ans); // Print the aggregated sum
    }
}