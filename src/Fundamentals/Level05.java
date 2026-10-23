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
        );

        List<Product> order2 = List.of(
                new Product(3, "Keyboard"),
                new Product(4, "Monitor")
        );

        List<Order> orders = List.of(
                new Order(101, order1),
                new Order(102, order2)
        );
        

        List<List<Integer>> list1 = List.of(
                List.of(1, 2, 3),
                List.of(4, 5),
                List.of(6, 7, 8)
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
        List<Integer> numbers = List.of(1, 2, 2, 3, 3, 3, 4, 4, 4, 4, 5);
        List<Integer> numbers2 = List.of(1, 2, 3, 4, 5, 7);

        Map<String, Integer> map1 = Map.of("Java", 10, "Python", 20);
        Map<String, Integer> map2 = Map.of("Java", 15, "Python", 5, "SQL", 8);

        //flatlist(list1); //Flatten a List<List<Integer>> into a single list using flatMap().
        //flatprodutsorders(orders);//Flatten a list of orders into a list of all products contained in those orders.
        //mapempid(list);//Convert a list of employees into a Map<Integer, Employee> keyed by employee ID.
        //mapempid(list);//Handle duplicate keys safely when converting a list into a Map using toMap().
        //allsalary(list);//Find the sum of employee salaries using mapToInt() and explain why primitive streams are useful.
        //top3freq(numbers);//Find the three most frequently occurring elements in a list.
        //findmissing(numbers2 , 5);//Find the missing number from a list containing distinct integers in the range 1 to N.
        //mergemap(map1, map2);//Merge two maps by summing values for matching keys.
        //longconse(numbers2);//Find the longest consecutive sequence of integers in a list.
        sumUsingCustomCollect(numbers2);//Implement custom aggregation using collect() and a mutable result container.
    }

    private static void sumUsingCustomCollect(List<Integer> numbers) {
        int ans = numbers.stream()
                .collect(
                        StringBuilder::new, // Create a mutable result container
                        (sb, n) -> sb.append(n).append(" "), // Accumulate each number
                        (sb1, sb2) -> sb1.append(sb2) // Combine containers
                )
                .toString()
                .trim()
                .length(); // Example result: length of the aggregated string
        System.out.println(ans);
    }

    private static void longconse(List<Integer> numbers2) {
        List<Integer> ans = numbers2.stream()
                .filter(n -> !numbers2.contains(n - 1)) // Identify the start of each sequence
                .map(n -> {
                    List<Integer> sequence = new ArrayList<>();
                    while (numbers2.contains(n)) {
                        sequence.add(n++); // Build the consecutive sequence
                    }
                    return sequence;
                })
                .max(Comparator.comparingInt(List::size)) // Select the longest sequence
                .orElse(Collections.emptyList()); // Return an empty list if input is empty
        System.out.println(ans);
    }

    private static void mergemap(Map<String, Integer> map1, Map<String, Integer> map2) {
        Map<String, Integer> ans = Stream.concat(map1.entrySet().stream() , map2.entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        Integer::sum,
                        LinkedHashMap::new));
        System.out.println(ans);
    }

    private static void findmissing(List<Integer> numbers2, int i) {
        int allsum = i * (i+1) /2;
        int actusum = numbers2.stream()
                .mapToInt(Integer::intValue)
                .sum();

        int ans = allsum - actusum;
        System.out.println(ans);
    }

    private static void top3freq(List<Integer> numbers) {
        List<Integer> ans = numbers.stream()
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new,Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .toList();
        System.out.println(ans);
    }

    private static void allsalary(List<Employee> list) {
        Double ans = list.stream()
                .mapToDouble(Employee::getSalary)
                .sum();
        System.out.println(ans);
    }

    private static void mapempid(List<Employee> list) {
        Map<Integer, Employee> map = list.stream()
                .collect(Collectors.toMap(
                        Employee::getId,
                        Function.identity(),
                        (existing, replacement) -> existing
                ));
        System.out.println(map);
    }

    private static void flatprodutsorders(List<Order> orders) {
        List<Product> products = orders.stream()
                .flatMap(order -> order.getProducts().stream())
                .toList();
        products.forEach(product -> System.out.println(product.getName()));
    }

    private static void flatlist(List<List<Integer>> list1) {
        List<Integer> ans = list1.stream()
                .flatMap(List::stream)
                .toList();
        System.out.println(ans);

    }
}
