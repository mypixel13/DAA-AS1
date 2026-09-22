package algo;

// simple holder for the numbers we care about during a run.
// gets passed into the algorithm instead of using static/global counters,
// so different runs don't step on each other.
public class Metrics {

    public long comparisons = 0;
    public int maxDepth = 0;
    public long timeNanos = 0;

    public void compare() {
        comparisons++;
    }

    public void trackDepth(int depth) {
        if (depth > maxDepth) {
            maxDepth = depth;
        }
    }

    public void reset() {
        comparisons = 0;
        maxDepth = 0;
        timeNanos = 0;
    }

    @Override
    public String toString() {
        return "comparisons=" + comparisons + ", maxDepth=" + maxDepth + ", timeNanos=" + timeNanos;
    }
}
