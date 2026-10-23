package Fundamentals;

import java.util.*;
import java.util.stream.Collectors;

class Employee {
    private int id;
    private String name;
    private String department;
    private double salary;
    private int age;

    public Employee(int id, String name, String department, double salary, int age) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.age = age;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getSalary() { return salary; }
    public int getAge() { return age; }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", salary=" + salary +
                ", age=" + age +
                '}';
    }
}

public class Level04 {

    public static void main(String[] args) {

        List<Employee> list = List.of(
                new Employee(1, "Amit", "IT", 75000, 28),
                new Employee(2, "Priya", "HR", 55000, 25),
                new Employee(3, "Rahul", "IT", 90000, 32),
                new Employee(4, "Sneha", "Finance", 65000, 29),
                new Employee(5, "Vikas", "HR", 60000, 35),
                new Employee(6, "Neha", "IT", 85000, 26),
                new Employee(7, "Karan", "Finance", 70000, 31),
                new Employee(8, "Pooja", "IT", 75000, 24)
        ); // Employee test data containing multiple departments and duplicate salaries

        // Uncomment one method at a time to practise individual Stream operations
        // groupthem(list);              // Q1: Group employees by department using groupingBy()
        // countbydept(list);            // Q2: Count employees in each department using counting()
        // getavg(list);                 // Q3: Calculate the average salary of each department
        // highestpaid(list);            // Q4: Find the highest-paid employee in each department
        // lowestpaid(list);             // Q5: Find the lowest-paid employee in each department
        // secondhighest(list);          // Q6: Find the second-highest-paid employee in each department
        // totalsalary(list);            // Q7: Calculate the total salary expenditure for each department
        // partby60K(list);              // Q8: Partition employees by whether salary exceeds ₹60,000
        // deptwithhighsalary(list);     // Q9: Find the department with the highest average salary
        //findnames(list);                // Q10: Get employee names by department as Map<String, List<String>>
    }

    // Q1: Group employees by department using groupingBy()
    private static void groupthem(List<Employee> list) {
        Map<String, List<Employee>> map = list.stream()       // Create a Stream of Employee objects
                .collect(Collectors.groupingBy(Employee::getDepartment)); // Group employees using their department

        map.forEach((department, employeeList) ->
                System.out.println(department + " -> " + employeeList)); // Print each department and its employees
    }

    // Q2: Count the number of employees in each department
    private static void countbydept(List<Employee> list) {
        Map<String, Long> map = list.stream()                 // Create a Stream of Employee objects
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,              // Group employees by department
                        Collectors.counting()                 // Count employees in each department
                ));

        map.forEach((department, count) ->
                System.out.println(department + " -> " + count)); // Print each department and its employee count
    }

    // Q3: Calculate the average salary of each department
    private static void getavg(List<Employee> list) {
        Map<String, Double> map = list.stream()               // Create a Stream of Employee objects
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,              // Group employees by department
                        Collectors.averagingDouble(Employee::getSalary) // Calculate the average salary in each group
                ));

        map.forEach((department, salary) ->
                System.out.println(department + " -> " + salary)); // Print each department and its average salary
    }

    // Q4: Find the highest-paid employee in each department
    private static void highestpaid(List<Employee> list) {
        Map<String, Optional<Employee>> map = list.stream()   // Create a Stream of Employee objects
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,              // Group employees by department
                        Collectors.maxBy(Comparator.comparingDouble(Employee::getSalary)) // Find the highest salary in each group
                ));

        map.forEach((department, employee) ->
                System.out.println("Highest paid in " + department + " -> " + employee.orElse(null))); // Print the highest-paid employee
    }

    // Q5: Find the lowest-paid employee in each department
    private static void lowestpaid(List<Employee> list) {
        Map<String, Optional<Employee>> map = list.stream()   // Create a Stream of Employee objects
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,              // Group employees by department
                        Collectors.minBy(Comparator.comparingDouble(Employee::getSalary)) // Find the lowest salary in each group
                ));

        map.forEach((department, employee) ->
                System.out.println("Lowest paid in " + department + " -> " + employee.orElse(null))); // Print the lowest-paid employee
    }

    // Q6: Find the second-highest-paid employee in each department
    private static void secondhighest(List<Employee> list) {
        Map<String, Optional<Employee>> map = list.stream()   // Create a Stream of Employee objects
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,              // Group employees by department
                        Collectors.collectingAndThen(         // Apply an additional operation after collecting each group
                                Collectors.toList(),          // Collect each department's employees into a List
                                employees -> employees.stream() // Create a Stream for the current department
                                        .sorted(Comparator.comparingDouble(Employee::getSalary).reversed()) // Sort salaries from highest to lowest
                                        .skip(1)              // Skip the highest-paid employee
                                        .findFirst()          // Get the next employee, if present
                        )
                ));

        map.forEach((department, employee) ->
                System.out.println("Second highest in " + department + " -> " + employee.orElse(null))); // Print null if the department has fewer than two employees
    }

    // Q7: Calculate the total salary expenditure for each department
    private static void totalsalary(List<Employee> list) {
        Map<String, Double> map = list.stream()                // Create a Stream of Employee objects
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,              // Group employees by department
                        Collectors.summingDouble(Employee::getSalary) // Add all salaries within each department
                ));

        map.forEach((department, salary) ->
                System.out.println(department + " -> " + salary)); // Print each department's total salary expenditure
    }

    // Q8: Partition employees based on whether their salary exceeds ₹60,000
    private static void partby60K(List<Employee> list) {
        Map<Boolean, List<Employee>> map = list.stream()      // Create a Stream of Employee objects
                .collect(Collectors.partitioningBy(
                        employee -> employee.getSalary() > 60000 // true: salary exceeds ₹60,000; false: salary is ₹60,000 or less
                ));

        System.out.println("Salary above ₹60,000: " + map.get(true)); // Print employees earning more than ₹60,000
        System.out.println("Salary ₹60,000 or below: " + map.get(false)); // Print employees earning ₹60,000 or less
    }

    // Q9: Find the department with the highest average salary
    private static void deptwithhighsalary(List<Employee> list) {
        Map.Entry<String, Double> result = list.stream()     // Create a Stream of Employee objects
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,              // Group employees by department
                        Collectors.averagingDouble(Employee::getSalary) // Calculate the average salary for each department
                ))
                .entrySet()                                  // Convert the Map into a Stream of department-average entries
                .stream()
                .max(Map.Entry.comparingByValue())            // Find the department with the greatest average salary
                .orElseThrow();                              // Throw an exception if no department exists

        System.out.println("Department with highest average salary: " +
                result.getKey() + " -> " + result.getValue()); // Print the department and its average salary
    }

    // Q10: Get employee names in each department as Map<String, List<String>>
    private static void findnames(List<Employee> list) {
        Map<String, List<String>> map = list.stream()         // Create a Stream of Employee objects
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,              // Group employees by department
                        Collectors.mapping(
                                Employee::getName,            // Transform each Employee object into its name
                                Collectors.toList()           // Collect names into a List<String>
                        )
                ));

        map.forEach((department, names) ->
                System.out.println(department + " -> " + names)); // Print each department and its employee names
    }
}