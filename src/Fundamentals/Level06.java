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

    @Override
    public String toString() {
        return "Transaction{customerId=" + customerId + ", amount=" + amount + "}";
    }
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

        // Q1: Find the highest-paid employee across the company without sorting all employees.
        // findhighenosort(list);

        // Q2: Find the top two employees by salary in every department.
        // toptwo(list);

        // Q3: Sort employees by department and then by salary in descending order.
        // sortempbydepbysal(list);

        // Q4: Group employees by department and further group them by age range.
        // grpdepgrpage(list);

        // Q5: Find the percentage of employees in each department relative to the total workforce.
        // findper(list);

        // Q6: Calculate the total transaction amount for each customer and return the top spender.
        // topspender(tran);

        // Q7: Find the top five most frequent words, ignoring case and punctuation.
        // topfive(sentences);

        // Q8: Find all pairs of numbers whose sum equals the target value.
        // pairs(numbers, 7);

        // Q9: Calculate the sum of a list using parallel streams and understand when parallelism helps.
        // calculateSum(numbers);

        // Q10: Demonstrate findFirst() versus findAny(), including parallel stream behavior.
        List<Integer> numbers2 = List.of(10, 20, 30, 40, 50);

        System.out.println(numbers2.stream()
                .filter(n -> n > 20)
                .findFirst()); // Optional[30]: Returns the first matching element in encounter order

        System.out.println(numbers2.stream()
                .filter(n -> n > 20)
                .findAny()); // Usually Optional[30] for this sequential stream

        List<Integer> numbers3 = List.of(10, 20, 30, 40, 50);

        System.out.println(numbers3.parallelStream()
                .filter(n -> n > 20)
                .findFirst()); // Optional[30]: Still respects encounter order

        System.out.println(numbers3.parallelStream()
                .filter(n -> n > 20)
                .findAny()); // May return any matching element, such as 30, 40, or 50
    }

    private static void calculateSum(List<Integer> numbers) {
        long ans = numbers.parallelStream()
                .mapToLong(Integer::longValue) // Convert Integer values to primitive long
                .sum(); // Calculate the sum using parallel processing

        System.out.println(ans); // Output: 23
    }

    private static void pairs(List<Integer> numbers, int target) {
        List<List<Integer>> ans = IntStream.range(0, numbers.size())
                .boxed()
                .flatMap(i -> IntStream.range(i + 1, numbers.size())
                        .filter(j -> numbers.get(i) + numbers.get(j) == target) // Check pairs at different indices
                        .mapToObj(j -> List.of(numbers.get(i), numbers.get(j)))) // Create each matching pair
                .toList();

        System.out.println(ans); // Output: [[1, 6], [3, 4], [5, 2]]
    }

    private static void topfive(List<String> sentences) {
        List<Map.Entry<String, Long>> ans = sentences.stream()
                .flatMap(sentence -> Arrays.stream(sentence.toLowerCase()
                        .replaceAll("[^a-z0-9\\s]", "") // Remove punctuation
                        .split("\\s+"))) // Split sentences into words
                .filter(word -> !word.isBlank()) // Ignore empty words
                .collect(Collectors.groupingBy(
                        Function.identity(), Collectors.counting() // Count occurrences of each word
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey())) // Sort by frequency, then alphabetically
                .limit(5) // Keep the five most frequent words
                .toList();

        System.out.println(ans);
    }

    private static void topspender(List<Transaction> tran) {
        Map.Entry<Integer, Double> ans = tran.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getCustomerId,
                        Collectors.summingDouble(Transaction::getAmount) // Calculate each customer's total
                ))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue()) // Find the customer with the highest total
                .orElse(null);

        System.out.println(ans); // Output: 104=1100.0
    }

    private static void findper(List<Employee> list) {
        long total = list.size(); // Get the total number of employees

        Map<String, Double> ans = list.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.counting() // Count employees in each department
                ))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue() * 100.0 / total // Convert department count to percentage
                ));

        System.out.println(ans); // Output: IT=50.0, HR=25.0, Finance=25.0 (order may vary)
    }

    private static void grpdepgrpage(List<Employee> list) {
        Map<String, Map<String, List<Employee>>> ans = list.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment, // Group employees by department first
                        Collectors.groupingBy(emp -> {
                            int age = emp.getAge();

                            if (age < 25) return "Under 25"; // Employees younger than 25
                            if (age <= 35) return "25-35"; // Employees aged 25 through 35
                            if (age <= 45) return "36-45"; // Employees aged 36 through 45
                            return "Above 45"; // Employees older than 45
                        })
                ));

        System.out.println(ans);
    }

    private static void sortempbydepbysal(List<Employee> list) {
        List<Employee> ans = list.stream()
                .sorted(Comparator.comparing(Employee::getDepartment) // Sort departments alphabetically
                        .thenComparing(Comparator.comparingDouble(Employee::getSalary).reversed())) // Salary descending within each department
                .toList();

        System.out.println(ans);
    }

    private static void toptwo(List<Employee> list) {
        Map<String, List<Employee>> ans = list.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment, // Group employees by department
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                employees -> employees.stream()
                                        .sorted(Comparator.comparingDouble(Employee::getSalary).reversed()) // Sort salaries descending
                                        .limit(2) // Keep the top two employees in each department
                                        .toList()
                        )
                ));

        System.out.println(ans);
    }

    private static void findhighenosort(List<Employee> list) {
        Optional<Employee> emp = list.stream()
                .max(Comparator.comparingDouble(Employee::getSalary)); // Find the maximum salary without sorting

        System.out.println(emp);
    }
}