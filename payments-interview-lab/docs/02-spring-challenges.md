# Phase 2 — Spring / Spring Boot

1. Explain IoC and Dependency Injection using `PaymentService` as the example.
2. Explain `@Component`, `@Service`, `@Repository`, `@RestController`, `@Configuration`, `@Bean` and when stereotype choice matters.
3. What is a Spring Bean? What is its default scope? What are lifecycle/proxy implications?
4. Find two unsafe singleton beans.
5. Explain constructor injection versus field injection and why constructor injection is generally easier to test.
6. Explain what `@Transactional` actually does through proxies.
7. Demonstrate the self-invocation problem in `PaymentService.create -> authorizeInternal`.
8. Create a scenario where the transaction commits but asynchronous notification fails. Decide what the product should do.
9. Explain default rollback behavior for runtime vs checked exceptions.
10. Split transaction boundaries so a poison batch item cannot roll back unrelated successful items.
11. Add idempotency that remains correct under concurrent requests. A pre-check is not enough.
12. Map validation/business/not-found errors to appropriate HTTP statuses without leaking internals.
13. Add pagination to `/api/payments` and explain why `findAll()` is dangerous at scale.
14. Explain Spring's singleton scope versus the GoF Singleton pattern.
15. Explain why `@Async` only works when invocation crosses a Spring proxy and how executor configuration should be owned by the application context.
