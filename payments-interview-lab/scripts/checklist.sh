#!/usr/bin/env bash
set -e
cat <<'EOF'
Interview Lab Checklist
[ ] Explain SOLID with examples from code
[ ] Explain Spring Bean + DI + scopes
[ ] Reproduce transactional self-invocation trap
[ ] Prove a concurrency bug with a test
[ ] Fix idempotency correctly at DB boundary
[ ] Explain synchronized limitations across instances
[ ] Replace unmanaged executor with bounded managed pool
[ ] Explain Observable vs Promise with this UI
[ ] Remove RxJS subscription leaks
[ ] Build reusable card control/validation
[ ] Make batch resumable/idempotent/chunked
[ ] Improve logs/errors without leaking sensitive data
EOF
