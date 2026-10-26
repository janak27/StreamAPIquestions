# Java Streams API — 60 Interview Practice Questions

A structured collection of Java Streams API coding problems designed for Java developer interviews targeting **2–4 years of experience**.

This repository focuses on practical problem-solving using Java 8+ Streams, including filtering, mapping, reduction, collectors, grouping, data transformation, and advanced stream operations.

The questions are organized into six progressive levels, from fundamentals to interview-level challenges, with an emphasis on learning distinct concepts rather than repeating similar problems.

## Progress

| Level | Topics | Status    |
|---|---|-----------|
| Level 1 | Fundamentals | Completed |
| Level 2 | Filtering, Sorting and Collectors | Completed |
| Level 3 | Strings and Frequency Problems | Completed |
| Level 4 | Grouping and Employee Problems | Completed |
| Level 5 | Advanced Streams and Data Transformation | Completed   |
| Level 6 | Interview-Level Challenges | Completed   |

## Topics Covered

- **Stream Fundamentals:** `filter()`, `map()`, `reduce()`, `count()`, `min()` and `max()`
- **Filtering and Sorting:** `distinct()`, `sorted()`, `findFirst()`, `anyMatch()`, `allMatch()` and `noneMatch()`
- **Collectors:** `toList()`, `toSet()`, `joining()`, `groupingBy()`, `partitioningBy()` and `toMap()`
- **String Problems:** Character frequency, duplicate characters, anagrams and word processing
- **Employee Data Processing:** Department-wise grouping, salary calculations and employee rankings
- **Advanced Transformations:** `flatMap()`, primitive streams and custom aggregation
- **Interview Challenges:** Frequency analysis, map merging, parallel streams and transaction processing

## Repository Structure

```text
streamapiquestions/
├── src/                     # Java source files
│   └── Fundamentals/
│       ├── LevelOne.java
│       ├── LevelTwo.java
│       └── LevelThree.java
└── README.md
```

*Adjust the structure above if your actual package or filenames differ.*

## Learning Roadmap

### Level 1 — Fundamentals

Practice basic stream operations, including filtering even numbers, mapping values, converting strings, calculating sums, finding minimum and maximum values, removing duplicates, sorting, joining strings, and transforming lists.

### Level 2 — Filtering, Sorting and Collectors

Solve problems involving distinct rankings, employee salaries, number classification, list comparisons, conditional matching, and collection transformations.

### Level 3 — Strings and Frequency Problems

Work with character frequencies, repeating and non-repeating characters, duplicate detection, anagrams, longest and shortest strings, grouping words, and word-length analysis.

### Level 4 — Grouping and Employee Problems

Apply Streams to employee datasets using `groupingBy()`, `counting()`, `averagingDouble()`, `maxBy()`, `minBy()`, `summingDouble()` and `partitioningBy()`.

### Level 5 — Advanced Streams and Data Transformation

Explore nested collection flattening with `flatMap()`, map creation, duplicate-key handling, primitive streams, frequency rankings, map merging, consecutive sequences, and custom aggregation.

### Level 6 — Interview-Level Challenges

Practice multi-level grouping, employee rankings, percentage calculations, transaction analysis, word-frequency ranking, pair-sum problems, parallel stream considerations, and `findFirst()` versus `findAny()`.

## How to Practice

1. Start with Level 1 and progress sequentially.
2. Solve each problem independently before checking a solution.
3. Prefer readable, maintainable stream pipelines over unnecessarily complex one-liners.
4. Understand the purpose of every intermediate and terminal operation.
5. Consider edge cases such as empty lists, duplicate values, nulls, and ties.
6. For advanced questions, understand time complexity, ordering guarantees, and when a traditional loop may be more appropriate.

## Prerequisites

- Java 8 or later
- Core Java collections and generics
- Lambda expressions and functional interfaces
- Basic understanding of `List`, `Set`, `Map` and comparator logic

## Goal

Build confidence in solving Java Streams API problems frequently encountered in Java backend and full-stack developer interviews, progressing from fundamental operations to practical data-processing challenges.

This repository is a work in progress. Levels 1–3 are completed, with Levels 4–6 planned.

## Contributions and Feedback

Suggestions for additional non-repetitive questions, edge cases, and cleaner solutions are welcome.

If this repository helps with your Java interview preparation, consider giving it a star.

---

**Author:** [Janak Avhad](https://github.com/janak27)

**GitHub:** [github.com/janak27](https://github.com/janak27)
