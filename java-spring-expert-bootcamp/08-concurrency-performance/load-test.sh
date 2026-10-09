#!/usr/bin/env bash
set -euo pipefail
URL=${1:-http://localhost:8080/api/stock/1/reserve}
for i in $(seq 1 200); do curl -s -X POST "$URL" -H 'Content-Type: application/json' -d '{"quantity":1}' >/dev/null & done
wait
echo "done"
