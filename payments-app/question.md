# Guia — Payments App (foco: entrevista Devoteam, IT Payments Team)

Cada bug mapeia a uma pergunta real que já te fizeram (ou variante muito provável). Backend sem BD de propósito — zero tempo perdido em setup, 100% no raciocínio.

## SOLID (5) — "opinião sobre SOLID / onde vês isto em frameworks"
- [ ] `PaymentService` — viola **SRP** (valida + calcula + grava + notifica, tudo numa classe)
- [ ] `PaymentProcessor.calculateFee` — viola **OCP** (switch que obriga a editar código para cada tipo novo)
- [ ] `NonRefundablePayment` — viola **LSP** (subclasse que quebra o contrato do pai)
- [ ] `PaymentGateway` — viola **ISP** (interface gorda, obriga a implementar métodos que não fazem sentido)
- [ ] `ReportService` — viola **DIP** (`new LegacyPaymentRepository()` em vez de injeção)

## Singleton / Concorrência / Threads
- [ ] `PaymentCounterService.count` — `int` normal num bean singleton, sem `synchronized`/`AtomicInteger` → race condition sob carga concorrente

## Streams / Lambdas / Coleções
- [ ] `Customer` sem `equals()`/`hashCode()` → `.distinct()` no `PaymentController.uniqueCustomers()` não remove duplicados

## Imutabilidade
- [ ] `PaymentDetails.getTags()` devolve a lista interna por referência → "imutável" só de fachada

## Logs / Exceções (contexto pagamentos = PCI)
- [ ] `PaymentLogger.charge`: loga E relança (duplica logs) + regista o **número completo do cartão** em texto simples

## Spring — anotações, transações, assincronia
- [ ] `NotificationService.sendConfirmation` — `@Async` + `void` → exceção desaparece em silêncio
- [ ] `ReportingService.auditLog` — `@Transactional(REQUIRES_NEW)` usado sem necessidade → commit independente do fluxo principal, mesmo que este dê rollback

## Angular — Observable vs Promise
- [ ] `PaymentStatusService.waitForUpdate` — `firstValueFrom` num Observable que devia emitir várias vezes (polling) → só recebe a primeira atualização, nunca mais nada

## Angular Material / CSS
- [ ] `app.config.ts` — sem `provideAnimations()` → componentes Material sem animação/quebrados
- [ ] `payment-form.component.scss` — `::ng-deep` desnecessário → estilo escapa para a app toda

## Reutilização (o cenário exato que te perguntaram: máscara de cartão)
- [ ] `onCardInput()` duplicado, copiado e colado, em `payment-form` e `payment-confirm`

## Features para dominares (fala sobre isto na entrevista)
- Refatorar a máscara de cartão para um **directive** (`[ccMask]`) ou **pipe** reutilizável — é literalmente o que te pediram para descrever.
- Implementar o padrão **Observer** para notificar múltiplos ouvintes quando um pagamento muda de estado (ligação direta à pergunta "Observables").
- Implementar um **thread pool** (`ExecutorService`/`@Async` com `Executor` configurado) para processar um lote (batch) de pagamentos em paralelo — a JD menciona "batch" explicitamente.
- Explicar de cabeça: Bean = objeto gerido pelo container Spring; `@Component`/`@Service`/`@Repository`/`@Controller`/`@Configuration`/`@Bean` — quando usar cada um.

Soluções: `answer.md`.
