# JotaNunes - Backend de Especificações Técnicas

> API REST para gestão completa e geração automatizada de documentos de especificação técnica para empreendimentos da construtora Jota Nunes.

![Azure](https://img.shields.io/badge/azure-%230072C6.svg?style=for-the-badge&logo=microsoftazure&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)

## 📋 Sobre o Projeto

Este sistema foi desenvolvido para modernizar o processo de criação de especificações técnicas da construtora Jota Nunes. A aplicação gerencia todo o ciclo de vida das especificações — desde a criação e edição até a revisão final — e culmina na geração automatizada de documentos DOCX padronizados.

Atualmente, a aplicação está implantada e rodando em produção utilizando **Azure Container Apps**, garantindo escalabilidade e facilidade de gerenciamento em nuvem.

## 🚀 Funcionalidades Principais

* **Gestão de Empreendimentos:** Cadastro e acompanhamento de obras.
* **Catálogo de Especificações:** Base de dados unificada de materiais, marcas e ambientes.
* **Fluxo de Revisão:** Sistema para aprovação e versionamento das especificações técnicas.
* **Geração de Documentos (Engine Híbrida):** Utiliza um microsserviço interno em Node.js para processar templates `.docx` complexos com alta fidelidade.
* **Segurança:** Autenticação via JWT e controle de acesso baseado em roles (RBAC).

## 🛠️ Arquitetura e Tecnologias

O projeto adota uma arquitetura de microsserviços focada no backend, onde o Spring Boot atua como orquestrador principal.

* **Core:** Java 21, Spring Boot 3
* **Banco de Dados:** PostgreSQL (com Flyway para migrations)
* **Document Engine:** Node.js 20 + Docxtemplater (integrado via processo local no container)
* **Infraestrutura:** Docker, Azure Container Apps
* **Documentação:** OpenAPI (Swagger UI)

## 📦 Instalação e Execução Local

### Pré-requisitos
* Java 21+
* Docker (recomendado)

### Passos
1.  Clone o repositório:
    ```bash
    git clone [https://github.com/seu-usuario/jotanunes-backend.git](https://github.com/seu-usuario/jotanunes-backend.git)
    ```
2.  Configure as variáveis de ambiente no `application.yml` ou via docker-compose para conectar ao seu banco PostgreSQL local.
3.  Suba a aplicação via Docker (que já configura o Java e o Node.js necessários):
    ```bash
    docker build -t jotanunes-api .
    docker run -p 8080:8080 jotanunes-api
    ```

## 📑 Documentação da API

A API completa pode ser testada e visualizada através do Swagger UI.
* **Ambiente de Produção (Azure):** [Acessar Documentação Online](https://api-jotanunes-serverless.graybay-87632aed.eastus.azurecontainerapps.io/swagger-ui/index.html)

---
*Projeto desenvolvido para fins educacionais.*
