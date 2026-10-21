package Fundamentals;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LevelThree {

    public static void main(String[] args) {

        String s = "JanakBabanAvhad";
        String s1 = "JanakAvhad";
        String s2 = "AvhadJanak";
        String sentence = " Bharat is iss my  maiyy ccouct country";

        // Uncomment one method at a time to practise individual Stream operations
        // countfreq(s);                       // Q1: Count the frequency of each character
        // nonrepeating(s);                    // Q2: Find the first non-repeating character
        // firstrepeating(s);                  // Q3: Find the first repeating character
        // allrepeating(s);                    // Q4: Find all duplicate characters
        // mostrepeating(s);                   // Q5: Find the character(s) with the highest frequency
        // mostfreq(s);                        // Q6: Find the most frequently occurring character(s)
        // checkanagrams(s1, s2);              // Q7: Check whether two strings are anagrams
        // longestword(sentence);              // Q8: Find the longest word in a sentence
        // shortstrin(sentence);               // Q9: Find the shortest word in a sentence
        // groupalistwords(sentence);          // Q10: Group words by their first character
        eachlength(sentence);                 // Q11: Group words by their length
    }

    // Q1: Count the frequency of each character in a string
    private static void countfreq(String s) {
        Map<Character, Long> freq = s.chars()                 // Convert the string into an IntStream of character values
                .mapToObj(c -> (char) c)                     // Convert each integer value into a Character
                .collect(Collectors.groupingBy(              // Group identical characters and calculate their frequencies
                        Function.identity(), LinkedHashMap::new, Collectors.counting()
                ));

        System.out.println("Character frequency: " + freq);  // Print frequencies while preserving first-appearance order
    }

    // Q2: Find the first non-repeating character in a string
    private static void nonrepeating(String s) {
        Map<Character, Long> freq = s.chars()                 // Convert the string into a stream of character values
                .mapToObj(c -> (char) c)                     // Convert each integer value into a Character
                .collect(Collectors.groupingBy(              // Count the occurrences of each character
                        Function.identity(), LinkedHashMap::new, Collectors.counting()
                ));

        Optional<Character> ans = s.chars()                   // Stream characters again in their original order
                .mapToObj(c -> (char) c)                     // Convert each value into a Character
                .filter(c -> freq.get(c) == 1)               // Keep characters that occur exactly once
                .findFirst();                                // Return the first non-repeating character, if present

        System.out.println("First non-repeating character: " + ans.orElse(null)); // Print null if none exists
    }

    // Q3: Find the first repeating character in a string
    private static void firstrepeating(String s) {
        Map<Character, Long> freq = s.chars()                 // Convert the string into a stream of character values
                .mapToObj(c -> (char) c)                     // Convert each integer value into a Character
                .collect(Collectors.groupingBy(              // Count the occurrences of each character
                        Function.identity(), LinkedHashMap::new, Collectors.counting()
                ));

        Optional<Character> ans = s.chars()                   // Stream characters in their original order
                .mapToObj(c -> (char) c)                     // Convert each value into a Character
                .filter(c -> freq.get(c) > 1)                // Keep characters that occur more than once
                .findFirst();                                // Return the first repeating character, if present

        System.out.println("First repeating character: " + ans.orElse(null)); // Print null if none exists
    }

    // Q4: Find all duplicate characters in a string without repeating them in the result
    private static void allrepeating(String s) {
        Map<Character, Long> freq = s.chars()                 // Convert the string into a stream of character values
                .mapToObj(c -> (char) c)                     // Convert each integer value into a Character
                .collect(Collectors.groupingBy(              // Count the occurrences of each character
                        Function.identity(), LinkedHashMap::new, Collectors.counting()
                ));

        List<Character> ans = s.chars()                      // Stream characters in their original order
                .mapToObj(c -> (char) c)                     // Convert each value into a Character
                .filter(c -> freq.get(c) > 1)                // Keep characters that appear more than once
                .distinct()                                  // Include each duplicate character only once
                .toList();                                   // Collect the result into an unmodifiable List

        System.out.println("Duplicate characters: " + ans);   // Print duplicate characters in first-appearance order
    }

    // Q5: Find the character(s) with the highest frequency
    private static void mostrepeating(String s) {
        Map<Character, Long> freq = s.chars()                 // Convert the string into a stream of character values
                .mapToObj(c -> (char) c)                     // Convert each integer value into a Character
                .collect(Collectors.groupingBy(              // Count the occurrences of each character
                        Function.identity(), LinkedHashMap::new, Collectors.counting()
                ));

        long max = freq.values().stream()                     // Create a Stream of character frequencies
                .mapToLong(Long::longValue)                  // Convert Long values into a primitive LongStream
                .max()                                       // Find the highest frequency
                .orElse(0);                                  // Use zero if the string is empty

        List<Character> ans = freq.entrySet().stream()        // Stream character-frequency entries
                .filter(e -> e.getValue() == max)            // Keep characters whose frequency equals the maximum
                .map(Map.Entry::getKey)                      // Extract the matching characters
                .toList();                                   // Collect the result into an unmodifiable List

        System.out.println("Most repeating characters: " + ans + " with frequency " + max); // Print the maximum frequency
    }

    // Q6: Find the most frequently occurring character(s) in a string
    private static void mostfreq(String s) {
        Map<Character, Long> freq = s.chars()                 // Convert the string into a stream of character values
                .mapToObj(c -> (char) c)                     // Convert each integer value into a Character
                .collect(Collectors.groupingBy(              // Count the occurrences of each character
                        Function.identity(), LinkedHashMap::new, Collectors.counting()
                ));

        long max = freq.values().stream()                     // Create a Stream of character frequencies
                .mapToLong(Long::longValue)                  // Convert frequencies into primitive long values
                .max()                                       // Find the highest frequency
                .orElse(0);                                  // Use zero if the string is empty

        List<Character> ans = freq.entrySet().stream()        // Stream character-frequency entries
                .filter(e -> e.getValue() == max)            // Keep characters having the maximum frequency
                .map(Map.Entry::getKey)                      // Extract the character keys
                .toList();                                   // Collect the matching characters into a List

        System.out.println("Most frequent characters: " + ans + " with frequency " + max); // Print matching characters and frequency
    }

    // Q7: Check whether two strings are anagrams using Streams
    private static void checkanagrams(String s1, String s2) {
        String str1 = s1.replaceAll("\\s+", "").toLowerCase(); // Remove whitespace and normalize case in the first string
        String str2 = s2.replaceAll("\\s+", "").toLowerCase(); // Remove whitespace and normalize case in the second string

        Map<Character, Long> freq1 = str1.chars()             // Convert the first normalized string into character values
                .mapToObj(c -> (char) c)                     // Convert each integer value into a Character
                .collect(Collectors.groupingBy(              // Build a frequency map for the first string
                        Function.identity(), LinkedHashMap::new, Collectors.counting()
                ));

        Map<Character, Long> freq2 = str2.chars()             // Convert the second normalized string into character values
                .mapToObj(c -> (char) c)                     // Convert each integer value into a Character
                .collect(Collectors.groupingBy(              // Build a frequency map for the second string
                        Function.identity(), LinkedHashMap::new, Collectors.counting()
                ));

        System.out.println("Are anagrams: " + freq1.equals(freq2)); // Equal frequency maps mean the strings are anagrams
    }

    // Q8: Find the longest word in a sentence
    private static void longestword(String sentence) {
        Optional<String> ans = Arrays.stream(sentence.trim().split("\\s+")) // Split the sentence using one or more whitespace characters
                .filter(word -> !word.isEmpty())              // Ignore empty strings
                .max(Comparator.comparingInt(String::length)); // Find the word with the greatest length

        System.out.println("Longest word: " + ans.orElse(null)); // Print null if no word exists
    }

    // Q9: Find the shortest word in a sentence
    private static void shortstrin(String sentence) {
        Optional<String> ans = Arrays.stream(sentence.trim().split("\\s+")) // Split the sentence into words
                .filter(word -> !word.isEmpty())              // Ignore empty strings
                .min(Comparator.comparingInt(String::length)); // Find the word with the smallest length

        System.out.println("Shortest word: " + ans.orElse(null)); // Print null if no word exists
    }

    // Q10: Group words by their first character
    private static void groupalistwords(String sentence) {
        List<String> words = Arrays.stream(sentence.trim().split("\\s+")) // Split the sentence into individual words
                .filter(word -> !word.isEmpty())              // Remove empty strings before accessing characters
                .toList();                                    // Collect words into an unmodifiable List

        Map<Character, List<String>> grouped = words.stream() // Create a Stream from the words
                .collect(Collectors.groupingBy(               // Group words using their first character
                        word -> word.charAt(0), LinkedHashMap::new, Collectors.toList()
                ));

        System.out.println("Words grouped by first character: " + grouped); // Print each character and its words
    }

    // Q11: Group words by their length
    private static void eachlength(String sentence) {
        List<String> words = Arrays.stream(sentence.trim().split("\\s+")) // Split the sentence into individual words
                .filter(word -> !word.isEmpty())              // Remove empty strings before grouping
                .toList();                                    // Collect words into an unmodifiable List

        Map<Integer, List<String>> grouped = words.stream()   // Create a Stream from the words
                .collect(Collectors.groupingBy(               // Group words according to their character count
                        String::length, LinkedHashMap::new, Collectors.toList()
                ));

        System.out.println("Words grouped by length: " + grouped); // Print each length and its corresponding words
    }
}