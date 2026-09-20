# Report — Assignment 1 (MergeSort, QuickSort, QuickSelect)

## 1. Asymptotic bounds

| Algorithm | Best | Average | Worst | Why |
|---|---|---|---|---|
| Insertion Sort | Θ(n) | Θ(n²) | Θ(n²) | best = already sorted, worst = reverse sorted |
| MergeSort | Θ(n log n) | Θ(n log n) | Θ(n log n) | always splits in half no matter what the input is |
| QuickSort | Ω(n) | Θ(n log n) | O(n log n) (basically always, pivot is random) | pivot is random so split is balanced on average, sorted array isn't a special bad case anymore |
| QuickSelect | Ω(n) | Θ(n) | O(n) (basically always) | same idea, but we only go into one side after partition, not both |

## 2. Recurrences

**MergeSort**

T(n) = 2T(n/2) + Θ(n) → a=2, b=2, f(n)=Θ(n)

log_b(a) = log2(2) = 1, and f(n) = Θ(n^1), so this is Master Theorem case 2.

**T(n) = Θ(n log n)**

**QuickSort** (balanced split assumption)

T(n) = 2T(n/2) + Θ(n) — same as MergeSort → **Θ(n log n)**

Random pivot is why we can even assume "balanced split" here. If we always picked a[0] as
pivot, a sorted array would split 1 vs n-1 every time and we'd get Θ(n²). Random pivot means
on average the split isn't that unbalanced, so on average we still get n log n.

**QuickSelect** (balanced split assumption)

T(n) = T(n/2) + Θ(n) → a=1, b=2, f(n)=Θ(n)

log_b(a) = log2(1) = 0, f(n) = Θ(n^0) which is bigger, so this is Master Theorem case 3.

**T(n) = Θ(n)**

Difference from QuickSort: after partitioning we throw away the side that doesn't have k in
it and only recurse into one side. So total work is n + n/2 + n/4 + ... which adds up to
about 2n, not n log n.

## 3. Benchmark

Ran on n = 1000 / 10 000 / 100 000 / 1 000 000, 3 kinds of input, median of 5 runs each.
results.csv is in the project, plots are in /plots.

### Time vs n

![time vs n](../plots/time_vs_n.png)

Time grows roughly the way it should — pretty much a straight line on the log-log plot for
all three. QuickSort on duplicates is way faster than on random, because of the 3-way
partition (once it sees a bunch of equal elements it just skips them instead of splitting
again).

### Depth vs n

![depth vs n](../plots/depth_vs_n.png)

This is basically the important part of the assignment — QuickSort depth on sorted input
stayed around 12-13 even at n = 1 000 000, instead of blowing up to ~n like it would with a
normal (non-random) pivot. That's the whole point of doing random pivot + recursing into the
smaller side first.

### Ratio vs n (checking Θ)

![ratio vs n](../plots/ratio_vs_n.png)

comparisons / (n·log2 n) for the sorts, comparisons / n for QuickSelect:

| algorithm / input | n=1000 | n=10000 | n=100000 | n=1000000 |
|---|---|---|---|---|
| MergeSort / random | 1.04 | 0.96 | 0.99 | 1.01 |
| MergeSort / sorted | 0.40 | 0.45 | 0.45 | 0.45 |
| MergeSort / duplicates | 0.98 | 0.92 | 0.94 | 0.96 |
| QuickSort / random | 1.20 | 1.26 | 1.22 | 1.25 |
| QuickSort / sorted | 1.21 | 1.19 | 1.23 | 1.33 |
| QuickSort / duplicates | 0.31 | 0.25 | 0.19 | 0.17 |
| QuickSelect / random | 5.58 | 3.44 | 3.18 | 4.49 |
| QuickSelect / sorted | 2.26 | 3.18 | 3.48 | 3.20 |
| QuickSelect / duplicates | 1.68 | 1.00 | 2.20 | 1.60 |

Most of these numbers stay pretty flat as n grows, which is what you'd expect if it's really
Θ(n log n) (or Θ(n) for QuickSelect) — roughly c1 ≈ 0.9, c2 ≈ 1.3, n0 ≈ 1000 covers most of
the sort rows.

The one row that doesn't stay flat is QuickSort / duplicates — it keeps going down
(0.31 → 0.17 instead of staying around 1). That means it's doing fewer comparisons than
n log n would need, closer to just n. Makes sense: only 10 different values in that input, so
the 3-way partition eats most of the array into the "equal" bucket on basically the first
call and there's barely anything left to recurse on.

QuickSelect numbers jump around more (1 to 5.6ish) and don't really flatten out cleanly, but
they don't grow either — they stay in the same rough range across n going from 1000 all the
way to 1 000 000. If it were n log n instead of n, this ratio should've grown by like 10x over
that range, and it didn't, so it still looks like Θ(n) — just noisier because it's a
randomized algorithm and I only ran it once per row (not averaged over many random pivots).

## 4. Discussion

The numbers mostly match what the theory says. MergeSort and QuickSort are both around
n log n, QuickSort doesn't fall apart on sorted input because of the random pivot, and
QuickSelect grows close to linear like it's supposed to.

Some reasons it's not a perfectly clean line:
- JVM warm-up — first run or two of a JVM process is slower because the JIT hasn't compiled
  the hot code yet, even though I took the median of 5 runs it's not a huge sample.
- GC — MergeSort allocates a buffer array, so if garbage collection kicks in during a run it
  can add a random spike to that one run.
- CPU cache — at n = 1 000 000 the array doesn't fit in cache anymore, so memory access
  patterns start mattering more, and MergeSort/QuickSort access memory differently (merge is
  more sequential, partition swaps things around more).
- cutoff = 15 for insertion sort — this is probably why MergeSort/sorted has a way lower
  ratio than MergeSort/random, since insertion sort does barely any work on already-sorted
  small chunks.

Basically the biggest and clearest result is QuickSort/duplicates — the 3-way partition was
built exactly for this case (lots of equal values) and the ratio plot actually shows it
working, going down instead of staying flat like the others.
