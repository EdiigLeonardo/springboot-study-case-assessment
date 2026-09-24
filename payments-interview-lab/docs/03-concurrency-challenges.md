# Phase 3 — Threads, Concurrency & Performance

1. Explain process vs thread, thread safety, race condition, visibility and atomicity.
2. Write the disabled duplicate-reference concurrency test.
3. Write the disabled balance test with many concurrent debits. Define invariants before running it.
4. Explain whether `synchronized` solves the duplicate-payment problem in a single JVM. Then explain why it does not solve a multi-instance deployment.
5. `capture()` is synchronized. Measure/argue its throughput consequences and redesign lock scope.
6. Explain optimistic locking (`@Version`) and where retries are appropriate.
7. Compare optimistic locking, pessimistic DB locking, database constraints, and distributed locks for this domain.
8. Find misuse of `ExecutorService`; configure a bounded Spring-managed executor with an explicit rejection policy.
9. Explain CPU-bound vs I/O-bound pool sizing. Why is “100 threads is faster” false?
10. Eliminate nested blocking (`submit(...).get()`) in `expensiveMerchantAggregation`.
11. Explain interruption. Fix the swallowed `InterruptedException`.
12. Find a non-thread-safe JDK class exposed as a singleton bean.
13. Design a performance test for 10k payments and identify memory, DB and lock bottlenecks.
14. Replace O(n²) case-insensitive deduplication with a suitable set/map approach.
15. Explain sync vs async from caller semantics, not just “one waits and one does not”.
