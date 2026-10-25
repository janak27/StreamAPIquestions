package Fundamentals;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Transaction {
    private int customerId;
    private double amount;

    public Transaction(int customerId, double amount) {
        this.customerId = customerId;
        this.amount = amount;
    }

    public int getCustomerId() { return customerId; }
    public double getAmount() { return amount; }
}

public class Level06 {
    public static void main(String[] args) {

        List<Transaction> tran = List.of(
                new Transaction(101, 200.0),
                new Transaction(101, 300.0),
                new Transaction(102, 100.0),
                new Transaction(102, 700.0),
                new Transaction(103, 250.0),
                new Transaction(103, 150.0),
                new Transaction(101, 100.0),
                new Transaction(104, 900.0),
                new Transaction(104, 200.0),
                new Transaction(102, 50.0)
        );

        List<Employee> list = List.of(
                new Employee(1, "Amit", "IT", 75000, 28),
                new Employee(2, "Priya", "HR", 55000, 25),
                new Employee(3, "Rahul", "IT", 90000, 32),
                new Employee(4, "Sneha", "Finance", 65000, 29),
                new Employee(5, "Vikas", "HR", 60000, 35),
                new Employee(6, "Neha", "IT", 85000, 26),
                new Employee(7, "Karan", "Finance", 70000, 31),
                new Employee(8, "Pooja", "IT", 75000, 24)
        );
        List<String> sentences = List.of(
                "Java is great, and Java is popular!",
                "Spring Boot makes Java development easier.",
                "Java and Spring Boot are widely used.",
                "Great developers write clean Java code."
        );

        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 2);
        
        // Employee test data containing different departments and salaries
        //findhighenosort(list);//Find the highest-paid employee across the entire company without sorting all employees.
        //toptwo(list);//Find the top two employees by salary in every department.
        //sortempbydepbysal(list);//Sort employees by department and then by salary in descending order.
        //grpdepgrpage(list);//Group employees by department and further group them by age range.
        //findper(list);//Find the percentage of employees in each department relative to the total workforce.
        //topspender(tran);//Given transactions, calculate the total transaction amount for each customer and return the top spender.
        //topfive(sentences);//Given a list of sentences, find the top five most frequent words, ignoring case and punctuation.
        //pairs(numbers, 7);//Find all pairs of numbers in a list whose sum equals a target value.
        //calculateSum(numbers);//Process a large list using parallel streams and explain when parallel processing can be slower or unsafe.
        //Explain and demonstrate the difference between findFirst() and findAny(), including their behavior with parallel streams.

        List<Integer> numbers2 = List.of(10, 20, 30, 40, 50);

        System.out.println(numbers2.stream()
                .filter(n -> n > 20)
                .findFirst()); // Optional[30]

        System.out.println(numbers2.stream()
                .filter(n -> n > 20)
                .findAny()); // Optional[30] in this example

        List<Integer> numbers3 = List.of(10, 20, 30, 40, 50);

        System.out.println(numbers3.parallelStream()
                .filter(n -> n > 20)
                .findFirst()); // Optional[30]

        System.out.println(numbers3.parallelStream()
                .filter(n -> n > 20)
                .findAny()); // Could be Optional[30], [40], or [50]
    }

    private static void calculateSum(List<Integer> numbers) {
        long ans = numbers.parallelStream()
                .mapToLong(Integer::longValue) // Convert to primitive long values
                .sum(); // Calculate the sum in parallel
        System.out.println(ans);
    }

    private static void pairs(List<Integer> numbers, int target) {
        List<List<Integer>> ans = IntStream.range(0, numbers.size())
                .boxed()
                .flatMap(i -> IntStream.range(i + 1, numbers.size())
                        .filter(j -> numbers.get(i) + numbers.get(j) == target)
                        .mapToObj(j -> List.of(numbers.get(i), numbers.get(j))))
                .toList();
        System.out.println(ans);
    }

    private static void topfive(List<String> sentences) {
        List<Map.Entry<String, Long>> ans = sentences.stream()
                .flatMap(sentence -> Arrays.stream(sentence.toLowerCase()
                        .replaceAll("[^a-z0-9\\s]", "") // Remove punctuation
                        .split("\\s+"))) // Split sentences into words
                .filter(word -> !word.isBlank()) // Ignore empty words
                .collect(Collectors.groupingBy(
                        Function.identity(), Collectors.counting()
                )) // Count each word's frequency
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed()) // Sort by frequency
                .limit(5) // Keep the top five words
                .toList();
        System.out.println(ans);
    }

    private static void topspender(List<Transaction> tran) {
        Map.Entry<Integer,Double> ans = tran.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getCustomerId,
                        Collectors.summingDouble(Transaction::getAmount)
                )).entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);
        System.out.println(ans);
    }

    private static void findper(List<Employee> list) {
        long total = list.size();
        Map<String, Double> ans = list.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.counting()
                ))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue() * 100.0 /  total
                ));
        System.out.println(ans);
    }

    private static void grpdepgrpage(List<Employee> list) {
        Map<String, Map<String, List<Employee>>> ans = list.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,
                        Collectors.groupingBy(emp -> {
                            int age = emp.getAge();
                            if (age < 25) return "Under 25";
                            if (age <= 35) return "25-35";
                            if (age <= 45) return "36-45";
                            return "Above 45";
                        })));

        System.out.println(ans);

    }

    private static void sortempbydepbysal(List<Employee> list) {
        List<Employee> ans = list.stream()
                .sorted(Comparator.comparing(Employee::getDepartment)
                        .thenComparing(Comparator.comparingDouble(Employee::getSalary).reversed()))
                .toList();
        System.out.println(ans);
    }

    private static void toptwo(List<Employee> list) {
        Map<String, List<Employee>> map = list.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list1 -> list1.stream()
                                        .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                                        .limit(2)
                                        .toList()
                        )
                ));
        System.out.println(map);
    }

    private static void findhighenosort(List<Employee> list) {
        Optional<Employee> emp = list.stream()
                .max(Comparator.comparingDouble(Employee::getSalary));

        System.out.println(emp);

    }
}
