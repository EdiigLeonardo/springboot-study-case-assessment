# Respostas — Payments App

## SOLID
1. Dividir `PaymentService` em `PaymentValidator`, `FeeCalculator`, `PaymentRepository`, `NotificationService` — cada um com uma razão para mudar.
2. Trocar o `switch` por uma interface `FeeStrategy` + uma implementação por tipo (`CreditCardFeeStrategy`, ...), escolhida por um `Map<String, FeeStrategy>` injetado — adicionar um tipo novo = nova classe, zero edições.
3. `NonRefundablePayment` não devia estender `RefundablePayment` — usar composição, ou uma interface `Refundable` que só as classes reembolsáveis implementam.
4. Dividir em `Chargeable`, `Refundable`, `Chargebackable` — cada implementação só assume o que de facto suporta.
5. Injetar `PaymentRepository` (interface) no construtor de `ReportService`, nunca `new` de uma classe concreta.

## Concorrência
6. `AtomicInteger count` (`count.incrementAndGet()`), ou `synchronized` no método se precisares de mais que um passo atómico.

## Streams / Coleções
7. Implementar `equals()`/`hashCode()` em `Customer` (baseado em `name`, ou melhor, num id).

## Imutabilidade
8. `return List.copyOf(tags);` no getter (ou guardar já com `List.copyOf(tags)` no construtor).

## Logs / Exceções
9. Logar só UMA vez (no ponto onde a exceção é finalmente tratada, não em todos os níveis) e nunca incluir o número completo do cartão — mascarar (`**** **** **** 1234`) ou nem logar o valor.

## Spring
10. Configurar um `AsyncUncaughtExceptionHandler` (via `AsyncConfigurer`), ou trocar o retorno para `CompletableFuture<Void>` para poderes tratar o erro no chamador.
11. Só usar `REQUIRES_NEW` quando o audit log deve mesmo sobreviver a um rollback do resto (ex.: "registei que tentei" independentemente do resultado) — caso contrário, usar a propagação default (`REQUIRED`) para fazer parte da mesma transação.

## Angular
12. Usar `.subscribe()` diretamente (mantendo o Observable "vivo"), ou `toObservable`/um `Subject` próprio — nunca converter para Promise quando esperas mais do que um valor.
13. Adicionar `provideAnimations()` (ou `provideNoopAnimations()` em testes) aos `providers` do `app.config.ts`.
14. Remover o `::ng-deep`; se precisares de estilizar um elemento filho de um componente de terceiros, usa `:host ::ng-deep` com um seletor bem específico, ou preferes `ViewEncapsulation.None` só nesse componente isolado.

## Reutilização
15. Criar `CardMaskDirective` (`@Directive({selector:'[ccMask]'})`) com a lógica de `onCardInput`, e usar `<input ccMask />` nos dois componentes — uma fonte da verdade.
