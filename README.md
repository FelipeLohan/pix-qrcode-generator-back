# PIX QR Code Generator

API REST em Spring Boot que gera QR Codes estáticos no padrão **BR Code** (EMV QRCPS-MPM) do Banco Central do Brasil.

## Tecnologias

- Java 17
- Spring Boot 3.5
- ZXing 3.5.3 (geração de QR Code)
- Lombok
- Maven

## Como executar

**Pré-requisitos:** Java 17+

```bash
./mvnw spring-boot:run
```

A API sobe em `http://localhost:8080`.

## Endpoint

### `POST /api/pix/gerar`

Gera um QR Code PIX estático e retorna a imagem em Base64.

**Request body:**

```json
{
  "chavePix": "exemplo@email.com",
  "nomeRecebedor": "Felipe Oliveira",
  "cidade": "Sao Paulo"
}
```

| Campo           | Obrigatório | Descrição                                         |
|-----------------|-------------|---------------------------------------------------|
| `chavePix`      | sim         | Chave PIX válida (veja formatos aceitos abaixo)   |
| `nomeRecebedor` | sim         | Nome do recebedor (máx. 25 caracteres no BR Code) |
| `cidade`        | sim         | Cidade do recebedor (máx. 15 caracteres no BR Code)|

**Formatos de chave PIX aceitos:**

| Tipo    | Formato                                      |
|---------|----------------------------------------------|
| CPF     | 11 dígitos numéricos                         |
| CNPJ    | 14 dígitos numéricos                         |
| E-mail  | `usuario@dominio.com`                        |
| Telefone| `+55` seguido de 10 ou 11 dígitos            |
| EVP     | UUID no formato `xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx` |

**Response (200 OK):**

```json
{
  "qrCodeBase64": "iVBORw0KGgo..."
}
```

O valor de `qrCodeBase64` é uma imagem PNG codificada em Base64. Para visualizar, prefixe com `data:image/png;base64,`.

### Erros

| Status | Situação                                      |
|--------|-----------------------------------------------|
| `400`  | Campos obrigatórios ausentes ou chave inválida|
| `422`  | BR Code gerado excede o limite de 512 caracteres|
| `500`  | Erro interno inesperado                       |

**Exemplo de erro:**

```json
{
  "message": "Chave PIX inválida."
}
```

## Testes

```bash
./mvnw test
```

## Coleção Bruno

O diretório `bruno/` contém uma coleção pronta com exemplos de requisições (sucesso e erros). Importe-a no [Bruno](https://www.usebruno.com/) e use o ambiente `local`.
