# MVC


Constrói a primeira versão de um **Portal de Pedidos B2B** server-side.

## Tens de implementar

- `DispatcherServlet` mental model: identifica todos os componentes que participam numa request.
- Controllers para listar, ver, criar e cancelar pedidos.
- `@RequestMapping`, `@GetMapping`, `@PostMapping`, path variables e query params.
- Model attributes e view names.
- `HandlerInterceptor` para correlation-id.
- `@ControllerAdvice` com páginas de erro 404/409/500.
- PRG (Post/Redirect/Get) para evitar re-submissão.
- Flash attributes para mensagens transitórias.
- Testes com `MockMvc`.

## Restrições

Não uses REST para esta missão. O objetivo é perceber MVC server-side.

## Falhas para provocar

1. Retorna um view name inexistente e explica quem falha.
2. Cria duas rotas ambíguas e interpreta o erro.
3. Lança uma exception de domínio e verifica o `ControllerAdvice`.
4. Remove o interceptor e prova por teste que o correlation-id desapareceu.

## Critério de conclusão

Consegues desenhar, de memória, o fluxo interno de uma request até ao JSP e explicar `HandlerMapping`, `HandlerAdapter`, `ViewResolver` e `Model`.
