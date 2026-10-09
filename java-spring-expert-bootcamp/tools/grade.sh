#!/usr/bin/env bash
set -euo pipefail
ROOT=$(cd "$(dirname "$0")/.." && pwd)
for d in "$ROOT"/*; do
  if [[ -f "$d/pom.xml" ]]; then
    echo "==> $(basename "$d")"
    (cd "$d" && mvn -q test)
  fi
done
