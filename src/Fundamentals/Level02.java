
package Fundamentals;

import java.util.*;
import java.util.stream.Collectors;

public class Level02 {

    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(
                -1, 0, 1, -2, -2, -3, -4, 0, 1, 2, 3, 4,
                5, 6, 7, 8, 9, 10, 0, 10, 9, 8
        ); // Test data containing positive, negative, zero, and duplicate values

        List<Integer> list1 = Arrays.asList(11, 12, 13, 10, 9, 8); // Second list for comparison

        List<String> list2 = Arrays.asList(
                "Java", "Spring", "Kafka", "Microservices"
        ); // String test data

        List<Employee> employees = List.of(
                new Employee(1, "Amit", "IT", 75000, 28),
                new Employee(2, "Priya", "HR", 55000, 25),
                new Employee(3, "Rahul", "IT", 90000, 32),
                new Employee(4, "Sneha", "Finance", 65000, 29),
                new Employee(5, "Vikas", "HR", 60000, 35),
                new Employee(6, "Neha", "IT", 85000, 26),
                new Employee(7, "Karan", "Finance", 70000, 31),
                new Employee(8, "Pooja", "IT", 75000, 24)
        );// Duplicate salary tests whether distinct() is applied correctly

        secondHighest(list);                    // Q1: Find the second-highest distinct number
        thirdHighestSalary(employees);          // Q2: Find the third-highest distinct salary
        topThreeHighest(list);                  // Q3: Find the top three distinct numbers
        numbersWithOne(list);                   // Q4: Find numbers whose string starts with "1"
        separate(list);                         // Q5: Separate positive, negative, and zero values
        commonNumbers(list, list1);             // Q6: Find unique common elements
        noCommon(list, list1);                  // Q7: Find elements unique to each list
        firstEven(list1);                       // Q8: Find the first even number
        allThings(list);                        // Q9: Check allMatch, anyMatch, and noneMatch
        settingList(list);                      // Q10: Convert a list into a Set
    }

    // Q1: Find the second-highest DISTINCT number
    private static void secondHighest(List<Integer> list) {
        Optional<Integer> ans = list.stream()                // Create a stream from the list
                .distinct()                                 // Remove duplicate numbers before ranking
                .sorted(Comparator.reverseOrder())          // Sort from highest to lowest
                .skip(1)                                    // Skip the highest distinct number
                .findFirst();                               // Get the next number, if present

        System.out.println("Second highest: " + ans.orElse(null)); // Empty Optional means fewer than two distinct numbers
    }

    // Q2: Find the third-highest DISTINCT employee salary
    private static void thirdHighestSalary(List<Employee> employees) {
        Optional<Double> ans = employees.stream()          // Stream Employee objects
                .map(Employee::getSalary)                   // Transform each Employee into its salary
                .distinct()                                 // Duplicate salaries count only once
                .sorted(Comparator.reverseOrder())          // Rank salaries from highest to lowest
                .skip(2)                                    // Skip the first and second highest salaries
                .findFirst();                               // Get the third-highest salary, if present

        System.out.println("Third-highest salary: " + ans.orElse(null)); // Null if fewer than three distinct salaries
    }

    // Q3: Find the top three highest DISTINCT numbers
    private static void topThreeHighest(List<Integer> list) {
        List<Integer> ans = list.stream()                   // Create a stream of numbers
                .distinct()                                 // Ensure each number appears only once
                .sorted(Comparator.reverseOrder())          // Put the largest number first
                .limit(3)                                   // Keep at most three numbers
                .collect(Collectors.toList());              // Collect results into a List

        System.out.println("Top three: " + ans);            // Fewer than three distinct numbers are allowed
    }

    // Q4: Find numbers whose string representation starts with digit "1"
    private static void numbersWithOne(List<Integer> list) {
        List<Integer> ans = list.stream()                   // Keep the result as integers
                .filter(n -> String.valueOf(n).startsWith("1")) // Convert each number to text and check its first character
                .collect(Collectors.toList());              // Collect matching numbers

        System.out.println("Numbers starting with 1: " + ans); // Example: 1, 10, 100, and 123 match
    }

    // Q5: Separate positive numbers, negative numbers, and zeros
    private static void separate(List<Integer> list) {
        List<Integer> positive = list.stream()
                .filter(n -> n > 0)                         // Keep numbers greater than zero
                .collect(Collectors.toList());              // Collect positive numbers

        List<Integer> negative = list.stream()
                .filter(n -> n < 0)                         // Keep numbers less than zero
                .collect(Collectors.toList());              // Collect negative numbers

        List<Integer> zeros = list.stream()
                .filter(n -> n == 0)                        // Keep only zero values
                .collect(Collectors.toList());              // Preserve repeated zeros

        System.out.println("Positive: " + positive);
        System.out.println("Negative: " + negative);
        System.out.println("Zeros: " + zeros);

        Map<String, List<Integer>> grouped = list.stream()
                .collect(Collectors.groupingBy(n ->        // Group each number by its category
                        n > 0 ? "Positive" : n < 0 ? "Negative" : "Zero"));

        System.out.println("Grouped: " + grouped);          // Map key iteration order is not guaranteed
    }

    // Q6: Find DISTINCT elements common to both lists
    private static void commonNumbers(List<Integer> list, List<Integer> list1) {
        Set<Integer> lookup = new HashSet<>(list1);         // Build a set for average O(1) membership checks

        List<Integer> ans = list.stream()
                .filter(lookup::contains)                   // Keep numbers present in the second list
                .distinct()                                 // Return each common value only once
                .collect(Collectors.toList());              // Preserve encounter order from the first list

        System.out.println("Common elements: " + ans);
    }

    // Q7: Find elements present in one list but absent from the other
    private static void noCommon(List<Integer> list, List<Integer> list1) {
        Set<Integer> lookup1 = new HashSet<>(list1);        // Fast lookup for values in the second list
        Set<Integer> lookup = new HashSet<>(list);          // Fast lookup for values in the first list

        List<Integer> onlyInFirst = list.stream()
                .filter(n -> !lookup1.contains(n))          // Keep values absent from the second list
                .distinct()                                 // Return unique difference values
                .collect(Collectors.toList());

        List<Integer> onlyInSecond = list1.stream()
                .filter(n -> !lookup.contains(n))           // Keep values absent from the first list
                .distinct()                                 // Return unique difference values
                .collect(Collectors.toList());

        System.out.println("Only in first list: " + onlyInFirst);
        System.out.println("Only in second list: " + onlyInSecond);
    }

    // Q8: Find the first even number using findFirst()
    private static void firstEven(List<Integer> list) {
        Optional<Integer> ans = list.stream()
                .filter(n -> n % 2 == 0)                    // Keep even numbers only
                .findFirst();                               // Return the first match in encounter order

        System.out.println("First even: " + ans.orElse(null)); // Null if no even number exists
    }

    // Q9: Check whether ALL, ANY, or NONE of the numbers satisfy a condition
    private static void allThings(List<Integer> list) {
        boolean allEven = list.stream()
                .allMatch(n -> n % 2 == 0);                 // True only if every number is even

        boolean anyOdd = list.stream()
                .anyMatch(n -> n % 2 != 0);                 // True if at least one number is odd

        boolean noNegative = list.stream()
                .noneMatch(n -> n < 0);                     // True if no negative number exists

        System.out.println("All even: " + allEven);
        System.out.println("Any odd: " + anyOdd);
        System.out.println("No negative: " + noNegative);
    }

    // Q10: Convert a List into a Set to remove duplicates
    private static void settingList(List<Integer> list) {
        Set<Integer> ans = list.stream()
                .collect(Collectors.toCollection(LinkedHashSet::new)); // Remove duplicates and preserve encounter order

        System.out.println("Unique elements: " + ans);
    }
}
