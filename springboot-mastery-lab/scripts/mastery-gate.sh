#!/usr/bin/env bash
set -euo pipefail
cat <<'EOF'
MASTERy GATE
============
[ ] All tests green
[ ] No controller contains business rules
[ ] No entity is used directly as an unsafe form command object
[ ] Transaction boundaries are explicit and justified
[ ] Security is enforced server-side
[ ] Batch is restartable + idempotent
[ ] Remote calls have explicit timeouts
[ ] Critical paths expose metrics/traces
[ ] You can explain proxy/self-invocation behavior
[ ] You can debug one incident without adding random annotations
EOF
