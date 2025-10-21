<div align="center">
<img src="https://img.shields.io/badge/Status-Concluído-blue?style=for-the-badge&logo=headspace&logoColor=blue&color=blue" alt="istudy-repo-status" />

<a href="/README.md">
  <img src="https://img.shields.io/badge/README-English-ff0000?style=for-the-badge" alt="English readme link" />
</a>
</div>

<div align="center">
  <img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java" />
  <img src="https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white" alt="Spring" />
  <img src="https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white" alt="Docker" />
  <img src="https://img.shields.io/badge/Apache_Kafka-231F20?style=for-the-badge&logo=apache-kafka&logoColor=white" alt="Apache Kafka" />
  <img src="https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white" alt="PostgreSQL" />
  <img src="https://img.shields.io/badge/MongoDB-%234ea94b.svg?style=for-the-badge&logo=mongodb&logoColor=white" alt="MongoDB" />
</div>

</br>

# Order System

Este repositório contém o código do projeto desenvolvido durante o curso ["Arquitetura de Microsserviços: Padrão Saga Orquestrado"](https://www.udemy.com/course/arquitetura-de-microsservicos-padrao-saga-orquestrado/) da Udemy.

Para fins de estudos sobre conceitos de padrões de desenvolvimento em arquitetura de microsserviços e formas de se lidar com **transações distribuidas** e sua **consistência de dados**, foi desenvolvido um sistema de pedidos utilizando o **padrão Saga Orquestrado com Java 17 e Spring Boot 3**.

![Order system flowchart](docs/images/order-system-fluxogram.jpg)
(Exemplo de transação distribuída de um sistemas de pedidos. Fonte: [Baeldung](https://www.baeldung.com/wp-content/uploads/sites/4/2021/04/distributed-transaction.png))

## O que é o padrão Saga?

O padrão Saga tem como objetivo garantir que, em casos de falha no fluxo de uma transação distribuída, todas as alterações sejam desfeitas na sequência que foram realizadas. Ele pode ser desenvolvido a partir de duas abordagens distintas: Orquestrado e Coreografado.

**Padrão Saga Coreografado**: Cada microsserviço que faz parte da transação publica um evento que é processado pelo próximo microsserviço.

**Padrão Saga Orquestrado (Utilizado neste projeto)**: Um único orquestrador é responsável por gerenciar o status geral da transação.

</br>

## 🕹 Navegação

* [Funcionalidades](#-funcionalidades)
* [Estrutura do Projeto](#-estrutura-do-projeto)
* [Arquitetura do Sistema](#-arquitetura-do-sistema)
* [Tecnologias Utilizadas](#-tecnologias-utilizadas)
* [Requisitos](#-requisitos)
* [Guia de Instalação](#-guia-de-instalação)
* [Endpoints](#-endpoints)
* [Topicos](#-topicos)
* [Contato](#-contato)

</br>

## ⚙️ Funcionalidades

- Geração de pedidos;
- Validação de produtos informados em um pedido;
- Simulação de pagamentos de pedidos;
- Validação de disponibilidade e baixa no estoque dos produtos de um pedido;
Idempotência: O sistema impede a publicação de eventos com um `orderId` ou `transactionId` já existente. Isso garante que o mesmo evento não seja processado mais de uma vez.

</br>

# 🧩 Estrutura do Projeto

- `order-service`: microsserviço responsável apenas por gerar um pedido inicial, e receber uma notificação. Aqui que teremos endpoints REST para inciar o processo e recuperar os dados dos eventos. O banco de dados utilizado é o MongoDB.
- `orchestrator-service`: microsserviço responsável por orquestrar todo o fluxo de execução da Saga, ele que saberá qual microsserviço foi executado e em qual estado, e para qual será o próximo microsserviço a ser enviado, este microsserviço também irá salvar o processo dos eventos. Este serviço não possui banco de dados.
- `product-validation-service`: microsserviço responsável por validar se o produto informado no pedido existe e está válido. Este microsserviço guardará a validação de um produto para o ID de um pedido. O banco de dados utilizado é o PostgreSQL.
- `payment-service`: microsserviço responsável por realizar um pagamento com base nos valores unitários e quantidades informadas no pedido. Este microsserviço guardará a informação de pagamento de um pedido. O banco de dados utilizado é o PostgreSQL.
- `inventory-service`: microsserviço responsável por realizar a baixa do estoque dos produtos de um pedido. Este microsserviço guardará a informação da baixa de um produto para o ID de um pedido. O banco de dados utilizado será o PostgreSQL.
- `docs`: Esta pasta contém recursos utilizados na documentação do `README.md`, como imagens.

</br>

# 🧠 Arquitetura do Sistema
![Order system architecture](docs/images/order_system.png)

## Fluxograma
![Order system fluxograma](docs/images/order_system_flowchart.png)

- Acesse a visualização completa do fluxograma [aqui](https://whimsical.com/order-system-fluxogram-LUyp7k8U2xNJmfnW6jPcup)

</br>

# 🛠️ Tecnologias Utilizadas

* **Java 17**: Linguagem de programação de alto nível e orientada a objetos amplamente utilizada para construir aplicações do lado do servidor, serviços web e aplicações Android.

* **Spring Boot 3**: Framework que simplifica o desenvolvimento de aplicações Java, oferecendo recursos integrados para injeção de dependência, configuração e suporte a microsserviços.

* **Apache Kafka**: Uma plataforma distribuída e de código aberto que atua como um intermediário de mensagens, permitindo a publicação, assinatura, armazenamento e processamento de fluxos de dados em tempo real de forma escalável e resiliente.

* **Swagger UI**: Uma ferramenta de código aberto que gera documentação de API interativa a partir de uma especificação OpenAPI, permitindo que os desenvolvedores visualizem, explorem e testem endpoints diretamente no navegador.

* **Redpanda Console**: Uma interface de usuário web de código aberto para gerenciar e monitorar clusters Kafka ou Redpanda, fornecendo insights em tempo real sobre tópicos, grupos de consumidores e mensagens. Ela simplifica a depuração, a inspeção de payloads de mensagens e a observação da integridade do sistema por meio de um painel intuitivo e interativo.

* **Jakarta Bean Validation**: Framework padrão para declaração e validação de restrições em objetos Java através de anotações, garantindo regras de negócio e validações de entrada de forma limpa e declarativa.

* **JPA**: API de persistência Java que fornece mapeamento objeto-relacional (ORM) para gerenciar dados relacionais em aplicações Java.

* **Lombok**: Biblioteca Java que reduz código boilerplate gerando métodos comuns como getters, setters e construtores via anotações.

## Databases  & Tools
* **MongoDB**: Banco de dados NoSQL voltado para performance e flexibilidade, armazenando dados em documentos JSON.

* **PostgreSQL**: Banco de dados relacional open-source robusto e confiável, conhecido por sua extensibilidade e conformidade com SQL.

* **Docker**: Plataforma que automatiza o deploy de aplicações em contêineres leves, garantindo consistência entre ambientes.

</br>

# 🧰 Requisitos

Para executar este projeto, é necessário ter:

* Docker
* Git

</br>

# 🚀 Guia de Instalação

1. **Clonar o repositório**

```bash
git clone https://github.com/ABeatrizSC/order-system.git
```

2. **Acessar o diretório do projeto**

```bash
cd order-system
```

3. **Construir e iniciar os contêineres**

```bash
docker-compose up --build
```

</br>

# 🔗 Endpoints

- É possível acessar e testar todos os endpoints diretamente do navegador através da URL do Swagger: [http://localhost:3000/swagger-ui.html](http://localhost:3000/swagger-ui.html)

![Swagger screenshot](docs/images/swagger-screenshot.jpg)

## 1. ORDER-SERVICE

- Microsserviço responsável por gerar um pedido e visualizar eventos.

### **POST** `/api/orders`

- Gera um novo pedido

#### Corpo da requisição
- `OrderRequest`

```json
{
  "products": [
    {
      "product": {
        "code": "COMIC_BOOKS", //ENUM = [COMIC_BOOKS, BOOKS, MOVIES, MUSIC]
        "unitValue": 15.50
      },
      "quantity": 3
    },
    {
      "product": {
        "code": "BOOKS",
        "unitValue": 9.90
      },
      "quantity": 1
    }
  ]
}
```

#### Resposta de sucesso
- `Order`

```json
{
  "id": "64429e987a8b646915b3735f",
  "products": [
    {
      "product": {
        "code": "COMIC_BOOKS",
        "unitValue": 15.5
      },
      "quantity": 3
    },
    {
      "product": {
        "code": "BOOKS",
        "unitValue": 9.9
      },
      "quantity": 1
    }
  ],
  "createdAt": "2023-04-21T14:32:56.335943085",
  "transactionId": "1682087576536_99d2ca6c-f074-41a6-92e0-21700148b519"
}
```

</br>

### **GET** `/api/events/all`

- Retorna informações de todos os eventos da saga

#### Resposta de sucesso
- `List<Event>`

```json
[
    {
    "id": "64429e9a7a8b646915b37360",
    "transactionId": "1682087576536_99d2ca6c-f074-41a6-92e0-21700148b519",
    "orderId": "64429e987a8b646915b3735f",
    "payload": {
        "id": "64429e987a8b646915b3735f",
        "products": [
        {
            "product": {
            "code": "COMIC_BOOKS",
            "unitValue": 15.5
            },
            "quantity": 3
        },
        {
            "product": {
            "code": "BOOKS",
            "unitValue": 9.9
            },
            "quantity": 1
        }
        ],
        "totalAmount": 56.40,
        "totalItems": 4,
        "createdAt": "2023-04-21T14:32:56.335943085",
        "transactionId": "1682087576536_99d2ca6c-f074-41a6-92e0-21700148b519"
    },
    "source": "ORCHESTRATOR",
    "status": "SUCCESS",
    "eventHistory": [
        {
          "source": "ORCHESTRATOR",
          "status": "SUCCESS",
          "message": "Saga started!",
          "createdAt": "2023-04-21T14:32:56.78770516"
        },
        {
          "source": "PRODUCT_VALIDATION_SERVICE",
          "status": "SUCCESS",
          "message": "Products are validated successfully!",
          "createdAt": "2023-04-21T14:32:57.169378616"
        },
        {
          "source": "PAYMENT_SERVICE",
          "status": "SUCCESS",
          "message": "Payment realized successfully!",
          "createdAt": "2023-04-21T14:32:57.617624655"
        },
        {
          "source": "INVENTORY_SERVICE",
          "status": "SUCCESS",
          "message": "Inventory updated successfully!",
          "createdAt": "2023-04-21T14:32:58.139176809"
        },
        {
          "source": "ORCHESTRATOR",
          "status": "SUCCESS",
          "message": "Saga finished successfully!",
          "createdAt": "2023-04-21T14:32:58.248630293"
        }
    ],
    "createdAt": "2023-04-21T14:32:58.28"
    },
    // ...
]
```

</br>

### **GET** `/api/events?orderId={id}`
### **GET** `/api/events?transactionId={id}`

- Recupera dados específicos de um evento da saga pelo seu `orderId` ou `transactionId`:

#### Resposta de sucesso
- `Event`

```json
{
  "id": "64429e9a7a8b646915b37360",
  "transactionId": "1682087576536_99d2ca6c-f074-41a6-92e0-21700148b519",
  "orderId": "64429e987a8b646915b3735f",
  "payload": {
    "id": "64429e987a8b646915b3735f",
    "products": [
      {
        "product": {
          "code": "COMIC_BOOKS",
          "unitValue": 15.5
        },
        "quantity": 3
      },
      {
        "product": {
          "code": "BOOKS",
          "unitValue": 9.9
        },
        "quantity": 1
      }
    ],
    "totalAmount": 56.40,
    "totalItems": 4,
    "createdAt": "2023-04-21T14:32:56.335943085",
    "transactionId": "1682087576536_99d2ca6c-f074-41a6-92e0-21700148b519"
  },
  "source": "ORCHESTRATOR",
  "status": "SUCCESS",
  "eventHistory": [
    {
      "source": "ORCHESTRATOR",
      "status": "SUCCESS",
      "message": "Saga started!",
      "createdAt": "2023-04-21T14:32:56.78770516"
    },
    {
      "source": "PRODUCT_VALIDATION_SERVICE",
      "status": "SUCCESS",
      "message": "Products are validated successfully!",
      "createdAt": "2023-04-21T14:32:57.169378616"
    },
    {
      "source": "PAYMENT_SERVICE",
      "status": "SUCCESS",
      "message": "Payment realized successfully!",
      "createdAt": "2023-04-21T14:32:57.617624655"
    },
    {
      "source": "INVENTORY_SERVICE",
      "status": "SUCCESS",
      "message": "Inventory updated successfully!",
      "createdAt": "2023-04-21T14:32:58.139176809"
    },
    {
      "source": "ORCHESTRATOR",
      "status": "SUCCESS",
      "message": "Saga finished successfully!",
      "createdAt": "2023-04-21T14:32:58.248630293"
    }
  ],
  "createdAt": "2023-04-21T14:32:58.28"
}
```

</br>

## Mensagens de Erro

Todos os erros tratados possuem o seguinte formato:

```json
{
  "status": 400,
  "message": "Descrição do erro."
}
```

| Campo     | Tipo    | Descrição                 |
| --------- | ------- | ------------------------- |
| `status`  | Integer | Código HTTP do erro       |
| `message` | String  | Mensagem descritiva do erro       |

</br>

# 📥 Topicos
- Você também pode acessar o Redpanda Console para visualizar todo o fluxo de tópicos e publicar novos eventos no sistema a partir da URL: [http://localhost:8081](http://localhost:8081).

![Redpanda console screenshot](/docs/images/redpanda-console-screenshot.png)

</br>

# 📞 Contato

- GitHub: [ABeatrizSC](https://github.com/ABeatrizSC)
- Linkedin: [Ana Beatriz Santucci Carmoni](www.linkedin.com/in/ana-carmoni)
- Email: [anabscarmoni@gmail.com](mailto:anabscarmoni@gmail.com)

</br>

---
</br>

**Repositório original do curso:** [github.com/vhnegrisoli](https://github.com/vhnegrisoli/curso-udemy-microsservicos-padrao-saga-orquestrado/)