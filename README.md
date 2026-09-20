# DAA Assignment 1 — MergeSort / QuickSort / QuickSelect

## Структура

```
src/main/java/algo/
  Metrics.java        — счётчик сравнений, глубины рекурсии, времени
  InsertionSort.java  — вставками, используется как cutoff в MergeSort (<=15 элементов)
  MergeSort.java       — буфер выделяется один раз в верхнем вызове
  QuickSort.java       — random pivot, 3-way partition, рекурсия в меньшую часть
  QuickSelect.java      — тот же partition, идём только в нужную сторону
src/main/java/bench/
  Benchmark.java        — гоняет всё на n = 1k/10k/100k/1kk, 3 типа входа, 5 прогонов, медиана
src/test/java/algo/
  SortTest.java
  QuickSelectTest.java
```

## Как собрать и запустить

Нужен JDK 17+ и Maven.

Тесты:
```
mvn test
```

Бенчмарк (пишет `results.csv` в корень проекта):
```
mvn compile exec:java
```

Или без Maven, руками:
```
javac -d out $(find src/main/java -name "*.java")
java -cp out bench.Benchmark
```

## Что дальше

- `results.csv` можно закинуть в Python/Excel и построить графики (time vs n, depth vs n, ratio vs n) для отчёта.
- Отчёт (REPORT.md) с асимптотикой, рекуррентными соотношениями и разбором графиков — отдельно, шаблон могу накидать следующим сообщением.
- Git: завести ветки `feature/mergesort`, `feature/quicksort`, `feature/select`, `feature/metrics`, в конце смёржить в `main` и тегнуть `v1.0`.
