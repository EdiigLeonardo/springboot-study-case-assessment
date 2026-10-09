# Spring Batch


Cria `batch-worker`: todas as noites recebe um CSV de reconciliação de pagamentos com milhões de linhas.

## Implementa

- Job + Step.
- chunk-oriented processing.
- `ItemReader`, `ItemProcessor`, `ItemWriter`.
- JobParameters que impedem execução duplicada acidental.
- restartability.
- skip de registos inválidos com limite.
- retry de falhas transitórias.
- listener para métricas e relatório final.
- persistência do JobRepository.
- particionamento ou paralelização **apenas depois de medir**.
- idempotência no writer.

## Incident drills

1. A app morre depois de processar 42%.
2. O mesmo ficheiro é entregue duas vezes.
3. A DB fica indisponível por 30 segundos.
4. Uma linha contém dados semanticamente inválidos.
5. O negócio exige reprocessar apenas os erros.

## Defesa

Explica job instance, job execution, step execution, commit interval, restart, retry, skip e idempotência.
