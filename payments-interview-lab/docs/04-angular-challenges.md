# Phase 4 — Angular / RxJS

1. Explain component, service, directive, pipe, interceptor, guard and module/standalone component roles.
2. Explain Observable vs Promise: laziness, multiple emissions, cancellation/unsubscribe, operator composition, error flow.
3. Remove deprecated/awkward Promise conversion from payment creation and keep the workflow Observable-first.
4. Find every leaking subscription in `PaymentListComponent`. Refactor with `async` pipe or `takeUntilDestroyed`.
5. Explain why `shareReplay(1)` can produce stale data here. Implement explicit refresh/cache invalidation.
6. Replace polling subscription nesting with RxJS composition (`timer`, `switchMap`, etc.). Explain why `switchMap` matters.
7. Create robust loading/error/empty states.
8. Fix the auth interceptor so it does not send invalid tokens and does not blindly attach secrets to unrelated origins.
9. Explain Reactive Forms vs Template-driven Forms for enterprise forms.
10. Build a reusable credit-card input solution used in multiple pages: masking + validation + clean value propagation. Compare directive, custom form control, validator and pipe choices.
11. Improve card validation beyond “16 digits”; isolate card-domain rules so product changes are testable.
12. Align frontend amount validation with backend validation.
13. Prevent form submission when invalid and show field-level validation errors.
14. Explain Angular Material and the trade-off between component library productivity and customization/version coupling.
15. Fix the brittle global Material CSS override and make the form responsive.
16. Refactor `UserListComponent` so presentation is not coupled to endpoint details.
17. Explain change detection at a high level and where immutable view models help.
