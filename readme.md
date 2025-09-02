
## Nano Link
Este projeto é um encurtador de link, rápido, simples e escalável.

## Índice
- [Visão Geral](#visão-geral)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Instalação](#instalação-via-repositório)
- [Execução](#execução)
- [Endpoints da API](#endpoints-da-api)

## Visão Geral
Este projeto consiste em uma API RESTful desenvolvida em Java com Spring Boot. A API é capaz de receber transações e retornar estatísticas sobre essas transações, seguindo as melhores práticas de desenvolvimento e design de software.

## Tecnologias Utilizadas
- [Java 17+](https://www.oracle.com/br/java/technologies/downloads/)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Maven](https://maven.apache.org/)
- [MongoDB](https://www.mongodb.com/)

## Instalação via Repositório
1. Clone o repositório:

```bash
git clone https://github.com/patrickmarinho/NanoLink
```

2. Instale as dependências com o Maven

## Execução
1. Inicie a aplicação com o Maven

2. A API vai estar acessível em http://localhost:8080/v1/urls

## Endpoints da API

#### Encurtar URL:

```http
  POST /
```
Recebe os parâmetros do body no seguinte formato JSON:

```json
{
    "originalURL": "https://teste.com"
}
```

| Parâmetro         | Tipo          | Descrição                                           |
|-------------------|---------------|-----------------------------------------------------|
| `originalURL`     | `String`      | **Obrigatório**. Url original, não pode estar vazio.|

Como resposta a API pode retornar:
- `201 Created`
    - A Url foi aceita, validada, registrada e foi devolvido a url encurtada.

#### Buscar Estatísticas da URL Encurtada:

```http
  GET /{url-curta}/stats
```
Retorna a quantidade de clicks que a url teve.
