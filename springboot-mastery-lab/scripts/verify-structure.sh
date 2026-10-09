#!/usr/bin/env bash
set -euo pipefail
required=(01-mvc 02-forms 03-validation 04-jstl 05-data 06-security 07-batch 08-cloud 09-advanced capstone)
for d in "${required[@]}"; do test -d "$d" || { echo "Missing $d"; exit 1; }; done
echo "Structure OK"
