# Mental Model do Spring

Memoriza relações, não annotations.

## Container

`ApplicationContext` contém bean definitions → instancia dependências → aplica processors → pode criar proxies → publica lifecycle/events.

## MVC

`Filter chain -> DispatcherServlet -> HandlerMapping -> HandlerAdapter -> Controller -> Model/View -> ViewResolver -> View`.

## Data

`Controller -> Service transaction boundary -> Repository -> EntityManager/Persistence Context -> DB`.

## Security

Antes do controller, filtros constroem/consultam `SecurityContext`; autorização também deve existir no nível de serviço quando protege regras de negócio.

## Batch

`Job -> Step -> Reader -> Processor -> Writer`, com metadata persistida para restart/recovery.

## Cloud

Uma chamada remota precisa de: timeout, observabilidade, política de retry consciente de idempotência e comportamento degradado explícito.

## Regra de ouro

Quando uma annotation "não funciona", pergunta primeiro: **quem lê esta annotation, em que momento, e através de que proxy/componente?**
