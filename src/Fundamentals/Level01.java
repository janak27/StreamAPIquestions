package Fundamentals;

import java.util.*;
import java.util.stream.Collectors;

public class Level01 {

    public static void main(String[] args) {

        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 0, 10, 9, 8
        )); // Integer test data containing duplicates and zero

        ArrayList<String> list1 = new ArrayList<>(Arrays.asList(
                "Java", "Spring", "Kafka", "Microservices"
        )); // String test data for transformation and filtering

        even_number(list);                       // Q1: Filter even numbers using filter()
        square_number(list);                    // Q2: Square every number using map()
        convertToUppercase(list1);              // Q3: Convert strings to uppercase using map()
        lengreater5(list1);                     // Q4: Filter strings by length using filter()
        countStringsLengthGreaterThan5(list1);  // Q5: Count matching strings using count()
        sumofall(list);                         // Q6: Calculate the sum using reduce()
        findMaxMin(list);                       // Q7: Find maximum and minimum using max() and min()
        removeDuplicates(list);                 // Q8: Remove duplicates using distinct()
        sortNumbers(list);                      // Q9: Sort numbers using sorted()
        joinStrings(list1);                     // Q10: Join strings using Collectors.joining()
        stringLengths(list1);                   // Q11: Find string lengths using map()
    }

    // Q1: Find all even numbers using filter()
    static void even_number(ArrayList<Integer> list) {
        List<Integer> ans = list.stream()                       // Create a sequential Stream from the list
                .filter(n -> n % 2 == 0)                        // Keep numbers divisible by 2 with no remainder
                .collect(Collectors.toList());                  // Collect matching numbers into a new List

        System.out.println("Even numbers: " + ans);              // Print even numbers, including repeated values
    }

    // Q2: Find the square of every number using map()
    static void square_number(ArrayList<Integer> list) {
        List<Integer> ans = list.stream()                       // Create a Stream from the list
                .map(n -> n * n)                                // Transform each number into its square
                .collect(Collectors.toList());                  // Collect transformed values into a new List

        System.out.println("Squared numbers: " + ans);           // Print the squared numbers
    }

    // Q3: Convert every string to uppercase using map()
    static void convertToUppercase(ArrayList<String> list) {
        List<String> ans = list.stream()                        // Create a Stream from the string list
                .map(String::toUpperCase)                       // Transform each string into uppercase
                .collect(Collectors.toList());                  // Collect transformed strings into a new List

        System.out.println("Uppercase strings: " + ans);         // Print the uppercase strings
    }

    // Q4: Find strings containing more than five characters using filter()
    static void lengreater5(ArrayList<String> list) {
        List<String> ans = list.stream()                        // Create a Stream from the string list
                .filter(s -> s.length() > 5)                    // Keep strings whose length exceeds five
                .collect(Collectors.toList());                  // Collect matching strings into a new List

        System.out.println("Strings longer than 5: " + ans);     // Print strings with more than five characters
    }

    // Q5: Count strings containing more than five characters using count()
    static void countStringsLengthGreaterThan5(ArrayList<String> list) {
        long count = list.stream()                              // Create a Stream from the string list
                .filter(s -> s.length() > 5)                    // Keep strings whose length exceeds five
                .count();                                       // Count matching elements and return a long

        System.out.println("Count: " + count);                   // Print the number of matching strings
    }

    // Q6: Calculate the sum of all numbers using reduce()
    static void sumofall(ArrayList<Integer> list) {
        int sum = list.stream()                                 // Create a Stream from the integer list
                .reduce(0, (a, b) -> a + b);                   // Start with 0 and accumulate each number into the sum

        System.out.println("Sum: " + sum);                      // Print the total sum
    }

    // Q7: Find the maximum and minimum numbers using max() and min()
    static void findMaxMin(ArrayList<Integer> list) {
        int max = list.stream()                                 // Create a Stream to find the maximum value
                .max(Integer::compareTo)                       // Compare elements and return the maximum as an Optional
                .orElseThrow();                                 // Return the value or throw if the list is empty

        int min = list.stream()                                 // Create another Stream to find the minimum value
                .min(Integer::compareTo)                       // Compare elements and return the minimum as an Optional
                .orElseThrow();                                 // Return the value or throw if the list is empty

        System.out.println("Maximum: " + max);                  // Print the largest number
        System.out.println("Minimum: " + min);                  // Print the smallest number
    }

    // Q8: Remove duplicate numbers while preserving encounter order using distinct()
    static void removeDuplicates(ArrayList<Integer> list) {
        List<Integer> ans = list.stream()                       // Create an ordered Stream from the list
                .distinct()                                     // Keep only the first occurrence of each number
                .collect(Collectors.toList());                  // Collect unique numbers into a new List

        System.out.println("Unique numbers: " + ans);            // Print numbers without duplicates
    }

    // Q9: Sort numbers in ascending and descending order using sorted()
    static void sortNumbers(ArrayList<Integer> list) {
        List<Integer> ascending = list.stream()                 // Create a Stream for ascending order
                .sorted()                                       // Sort using natural order (smallest to largest)
                .collect(Collectors.toList());                  // Collect sorted numbers into a new List

        List<Integer> descending = list.stream()                // Create a separate Stream for descending order
                .sorted(Comparator.reverseOrder())              // Sort from largest to smallest
                .collect(Collectors.toList());                  // Collect sorted numbers into a new List

        System.out.println("Ascending: " + ascending);          // Print numbers in ascending order
        System.out.println("Descending: " + descending);        // Print numbers in descending order
    }

    // Q10: Join all strings into a single comma-separated string using joining()
    static void joinStrings(ArrayList<String> list) {
        String ans = list.stream()                              // Create a Stream from the string list
                .collect(Collectors.joining(","));              // Combine strings using a comma separator

        System.out.println("Joined strings: " + ans);            // Print the combined string
    }

    // Q11: Find the length of every string using map()
    static void stringLengths(ArrayList<String> list) {
        List<Integer> ans = list.stream()                       // Create a Stream from the string list
                .map(String::length)                            // Transform each string into its character count
                .collect(Collectors.toList());                  // Collect lengths into a new List of Integers

        System.out.println("String lengths: " + ans);            // Print the length of each string
    }
}