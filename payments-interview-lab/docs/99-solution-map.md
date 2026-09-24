# SPOILER MAP — Use only when blocked

This is not a full solution. It tells you where the highest-value defects live.

- `User.equals/hashCode`: equality contract changes depending on persistence state; hash code can disagree with equality.
- `UserService.uniqueNamesCaseInsensitiveSlow`: O(n²), use normalized-key set/map while preserving display value.
- `RiskService`: SRP/OCP violation; extract independent rules/strategies and inject a collection.
- `AppConfig.settlementDateFormat`: `SimpleDateFormat` is not thread-safe and bean is singleton.
- `AppConfig.globalCache`: unsynchronized mutable global state.
- `PaymentService.create`: idempotency pre-check races. Enforce uniqueness at DB boundary and handle conflict.
- `PaymentService.create -> authorizeInternal`: internal call does not cross proxy; annotation on called method does not create a new proxy boundary.
- account balance: read-modify-write needs explicit concurrency strategy; `@Version` can detect lost updates but caller must handle conflicts.
- `capture synchronized`: JVM-wide singleton monitor serializes unrelated payments and fails as a cross-instance lock.
- unmanaged executor: lifecycle/backpressure/observability problems; configure a bounded bean.
- nested `submit().get()`: consumes pool threads while waiting for same pool, can starve/deadlock under pressure.
- global exception handler: all errors => 500, leaks internals and logs expected business failures noisily.
- settlement batch: one giant transaction + remote side effect creates rollback/idempotency mismatch.
- scheduled job: multiple app instances will each run it unless coordinated.
- swallowed interrupt: restore interrupt flag or propagate/cancel appropriately.
- frontend `shareReplay(1)`: reload does not create a fresh HTTP call because the same cached observable is reused.
- frontend subscriptions: initial load/capture/reload create unmanaged subscriptions.
- `toPromise`: avoid; prefer Observable composition or `firstValueFrom` only at explicit Promise boundary.
- card directive: direct DOM mutation bypasses clean ControlValueAccessor semantics and cursor/input edge cases.
- card validator: domain logic is incomplete and duplicated assumptions are likely.
- frontend amount min differs from backend minimum.
- global Material CSS selector is brittle and fixed width is non-responsive.
- interceptor sends `Bearer null` and should scope credential attachment.
