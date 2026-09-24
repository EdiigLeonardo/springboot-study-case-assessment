# Phase 1 — Java Core & SOLID

Treat each item as an interview + coding exercise.

1. Explain SOLID in your own words, then identify at least 5 places in the code where a SOLID principle is violated or supported.
2. Refactor `RiskService` so adding a new risk rule does not require editing a giant conditional method.
3. Explain where Spring itself demonstrates Dependency Inversion and Open/Closed ideas.
4. Explain singleton in pure Java versus Spring singleton scope. Find mutable singleton state in this project and explain why it is dangerous.
5. Explain Java language features you would expect across Java 8/11/17: lambdas, streams, Optional, records, var, switch expressions, text blocks, sealed classes (where appropriate). Give examples.
6. Rewrite the age-30 query using a lambda; then using a method/predicate variable. Explain lazy stream operations and terminal operations.
7. Fix the `User.equals/hashCode` contract. Explain why mutable fields and generated JPA identifiers complicate equality.
8. Produce a list of unique user names case-insensitively in O(n) expected time. Preserve first-seen display casing.
9. Identify where immutability exists (`record`) and where it is missing. Propose immutable domain/value objects without breaking JPA requirements.
10. Compare checked vs unchecked exceptions for service/business errors. Create a small exception taxonomy.
11. Replace string concatenation logging with structured/parameterized logging and classify DEBUG/INFO/WARN/ERROR examples.
12. Explain when `Optional` is useful and when using it as a field or parameter can be awkward.
