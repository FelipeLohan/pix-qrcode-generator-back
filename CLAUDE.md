# CLAUDE.md

Instruções para o Claude Code neste projeto.

## Projeto

API Spring Boot que gera QR Codes PIX estáticos no padrão BR Code (EMV QRCPS-MPM).

## Comandos essenciais

```bash
# Subir a aplicação
./mvnw spring-boot:run

# Executar todos os testes
./mvnw test

# Build completo
./mvnw clean package
```

## Estrutura do projeto

```
src/main/java/com/FelipeLohan/pix_qrcode_generator/
├── config/          # WebConfig (CORS)
├── controller/      # PixController  → POST /api/pix/gerar
├── dto/             # PixRequest, PixResponse
├── exception/       # Exceções customizadas + GlobalExceptionHandler
├── service/         # BrCodeBuilderService, QrCodeImageService,
│                    # TextSanitizerService, PixService (orquestrador)
└── util/            # Crc16Util, PixKeyValidator
```

## Fluxo principal

`PixController` → `PixService` → valida chave (`PixKeyValidator`) → sanitiza texto (`TextSanitizerService`) → monta BR Code (`BrCodeBuilderService` + `Crc16Util`) → gera imagem Base64 (`QrCodeImageService`)

## Convenções

- Linguagem: português nos nomes de domínio (chavePix, nomeRecebedor, cidade), inglês no resto
- Injeção de dependência via construtor (sem `@Autowired`)
- Sem valor monetário no QR Code estático (campo `transactionAmount` omitido por padrão)
- `TextSanitizerService` trunca e remove acentos para respeitar o limite do BR Code
- Commits em inglês seguindo Conventional Commits (`feat:`, `fix:`, `chore:`, `test:`)

## Testes

- Testes unitários: `service/`, `util/`
- Teste de integração: `controller/PixControllerIntegrationTest` (sobe contexto Spring completo)
- Não mockar `BrCodeBuilderService` nos testes de integração — usar a implementação real

## O que não alterar sem discussão

- Algoritmo CRC16-CCITT em `Crc16Util` — qualquer mudança quebra a validação pelo app de banco
- Estrutura dos campos EMV em `BrCodeBuilderService` — segue especificação do Bacen
