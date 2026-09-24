# Phase 5 — Batch, Support & Production

1. The settlement batch processes all captured payments in one transaction. Explain the failure blast radius.
2. A poison payment fails halfway. Make progress resumable without double-settling successful remote calls.
3. Design idempotency for the external clearing call.
4. Add pagination/chunking and reason about page drift while rows change status.
5. The app runs on 3 instances. What happens to `@Scheduled`? Prevent duplicate batch execution safely.
6. Distinguish retryable vs non-retryable exceptions. Add bounded retries with backoff conceptually or in code.
7. Add correlation IDs for REST and batch logs.
8. Define logs that help support without leaking PAN/IBAN/PII.
9. Add metrics: processed count, failed count, latency, queue depth/backlog, settlement lag.
10. Explain how you would debug a production incident where throughput dropped after a deployment.
11. Explain N+1 and show how you would detect it in a JPA application.
12. Create a reconciliation report that detects local SETTLED payments missing at the clearing provider.
