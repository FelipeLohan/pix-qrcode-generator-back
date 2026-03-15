# PIX QR Code Generator — Backend

API REST em Spring Boot que gera QR Codes estáticos no padrão **BR Code** (EMV QRCPS-MPM) do Banco Central do Brasil.

> **Frontend em produção:** [qrcodegenerator.felipelohan.com](https://qrcodegenerator.felipelohan.com)
> **Repositório do frontend:** [github.com/FelipeLohan/pix-qrcode-generator-front](https://github.com/FelipeLohan/pix-qrcode-generator-front)

---

## O que é o BR Code?

O **BR Code** é o padrão brasileiro de QR Code para pagamentos PIX, definido pelo Banco Central do Brasil com base na especificação internacional **EMVCo Merchant Presented QR Code (EMV QRCPS-MPM)**. Todo QR Code PIX nada mais é do que uma string de texto estruturada nesse formato, codificada como imagem.

### Estrutura do payload

O payload é composto por **campos TLV** (*Tag-Length-Value*), onde cada campo tem:

- **ID** — 2 dígitos que identificam o campo
- **Tamanho** — 2 dígitos com o número de caracteres do valor
- **Valor** — o conteúdo do campo

Exemplo de payload gerado por esta API:

```
000201010212
26360014BR.GOV.BCB.PIX0114exemplo@email.com
52040000530398654040000
5802BR5913Felipe Lohan6009Sao Paulo
62140510pixqrcode16304ABCD
```

Cada bloco corresponde a um campo da especificação:

| ID   | Campo                       | Exemplo                         |
|------|-----------------------------|---------------------------------|
| `00` | Versão do payload           | `01`                            |
| `01` | Método de iniciação         | `12` (QR Code estático)         |
| `26` | Merchant Account Info (PIX) | Contém o sub-campo com a chave  |
| `52` | Merchant Category Code      | `0000`                          |
| `53` | Moeda                       | `986` (BRL)                     |
| `58` | País                        | `BR`                            |
| `59` | Nome do recebedor           | Até 25 caracteres               |
| `60` | Cidade                      | Até 15 caracteres               |
| `62` | Additional Data Field       | Referência da transação         |
| `63` | CRC-16                      | Checksum de integridade         |

### CRC-16

O payload é finalizado com um **checksum CRC-16/CCITT-FALSE** calculado sobre toda a string (incluindo o prefixo `6304`). Esse código de integridade garante que o payload não foi corrompido — os aplicativos de banco validam o CRC antes de processar o pagamento. Qualquer alteração no payload, mesmo de um único caractere, invalida o checksum.

### QR Code estático vs. dinâmico

Este projeto gera exclusivamente **QR Codes estáticos**:

| Característica | Estático                        | Dinâmico                          |
|----------------|---------------------------------|-----------------------------------|
| Valor          | Livre — o pagador define        | Fixo no payload                   |
| Uso            | Cobranças abertas, doações      | Cobranças com valor exato         |
| Geração        | Sem necessidade de PSP          | Requer integração com PSP/banco   |

---

## Tecnologias

- Java 17
- Spring Boot 3.5
- ZXing 3.5.3 (geração de QR Code)
- Lombok
- Maven

---

## Como executar

**Pré-requisitos:** Java 17+

```bash
./mvnw spring-boot:run
```

A API sobe em `http://localhost:8080`.

### Com Docker

```bash
docker build -t pix-qrcode-generator .
docker run -p 8081:8081 pix-qrcode-generator
```

A API fica acessível em `http://localhost:8081`.

---

## Endpoint

### `POST /api/pix/gerar`

Gera um QR Code PIX estático e retorna a imagem em Base64.

**Request body:**

```json
{
  "chavePix": "exemplo@email.com",
  "tipoChave": "EMAIL",
  "nomeRecebedor": "Felipe Lohan",
  "cidade": "Sao Paulo"
}
```

| Campo           | Obrigatório | Descrição                                                         |
|-----------------|-------------|-------------------------------------------------------------------|
| `chavePix`      | sim         | Chave PIX válida (veja formatos aceitos abaixo)                   |
| `tipoChave`     | sim         | Tipo da chave: `CPF`, `CNPJ`, `EMAIL`, `TELEFONE` ou `EVP`       |
| `nomeRecebedor` | sim         | Nome do recebedor (máx. 25 caracteres no BR Code)                 |
| `cidade`        | sim         | Cidade do recebedor (máx. 15 caracteres no BR Code)               |

**Formatos de chave PIX aceitos:**

| Tipo        | Formato                                                           |
|-------------|-------------------------------------------------------------------|
| `CPF`       | 11 dígitos numéricos                                              |
| `CNPJ`      | 14 dígitos numéricos                                              |
| `EMAIL`     | `usuario@dominio.com`                                             |
| `TELEFONE`  | 10 ou 11 dígitos — o prefixo `+55` é adicionado automaticamente  |
| `EVP`       | UUID no formato `xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx`            |

**Response (200 OK):**

```json
{
  "qrCodeBase64": "iVBORw0KGgo..."
}
```

O valor de `qrCodeBase64` é uma imagem PNG codificada em Base64. Para visualizar, prefixe com `data:image/png;base64,`.

### Erros

| Status | Situação                                         |
|--------|--------------------------------------------------|
| `400`  | Campos obrigatórios ausentes ou chave inválida   |
| `422`  | BR Code gerado excede o limite de 512 caracteres |
| `500`  | Erro interno inesperado                          |

**Exemplo de erro:**

```json
{
  "message": "Chave PIX inválida."
}
```

---

## Testes

```bash
./mvnw test
```

Inclui testes unitários para serviços e utilitários, e um teste de integração que sobe o contexto completo do Spring.

## Coleção Bruno

O diretório `bruno/` contém uma coleção pronta com exemplos de requisições (sucesso e erros). Importe-a no [Bruno](https://www.usebruno.com/) e use o ambiente `local`.
