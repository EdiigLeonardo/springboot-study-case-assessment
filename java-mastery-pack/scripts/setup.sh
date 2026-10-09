#!/usr/bin/env bash
set -euo pipefail

echo "==> Verificando ambiente Java (Java 17+)..."
JAVA_VER=$(java -version 2>&1 | head -1 | cut -d'"' -f2 | sed 's/^1\.//' | cut -d'.' -f1)

if [ "$JAVA_VER" -lt 17 ]; then
    echo "Erro: Java 17 ou superior é obrigatório. Versão encontrada: $JAVA_VER"
    exit 1
fi

echo "==> Compilando módulos e testes..."
mkdir -p bin
find . -name "*.java" > sources.txt
javac -d bin @sources.txt
rm sources.txt

echo "==> Executando testes com asserções ativadas..."
java -ea -cp bin com.mastery.arrays.TwoPointerReversalTest
java -ea -cp bin com.mastery.stream.OrderPipelineTest
java -ea -cp bin com.mastery.search.CustomBinarySearchTest

echo "==> Todos os módulos compilados e validados com sucesso!"
echo "Para executar o benchmark:"
echo "  java -cp bin com.mastery.list.ListBenchmarkRunner"
