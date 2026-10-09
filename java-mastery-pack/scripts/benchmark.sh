#!/usr/bin/env bash
set -euo pipefail

if [ ! -d "bin" ]; then
    echo "Executando setup.sh primeiro..."
    ./scripts/setup.sh
fi

echo "==> Executando Benchmark..."
java -cp bin com.mastery.list.ListBenchmarkRunner
