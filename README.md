# 💳 Desafio Fullstack Integrado — Luan de Souza Santos

[![Build](https://github.com/luanProjeto/desafio-beneficios-luan/actions/workflows/ci.yml/badge.svg)](https://github.com/luanProjeto/desafio-beneficios-luan/actions)

Este repositório contém a solução base para o desafio em camadas: **DB**, **EJB**, **Backend (Spring Boot)** e **Frontend (Angular)**.

## 🚀 Como começar

1. Suba o PostgreSQL (ou use seu próprio):  
   ```bash
   docker compose up -d
   ```

2. Execute os scripts:
   ```bash
   psql -h localhost -U postgres -d beneficios -f db/schema.sql
   psql -h localhost -U postgres -d beneficios -f db/seed.sql
   ```

3. Compile e rode os módulos:
   ```bash
   mvn -q -DskipTests -pl ejb-module,backend-module -am clean install
   cd backend-module && mvn spring-boot:run
   ```

4. Frontend (skeleton Angular):
   ```bash
   cd frontend
   npm install
   npm start
   ```

Swagger: http://localhost:8080/swagger-ui/index.html

## 🧩 Correção aplicada no EJB
- Validação de saldo insuficiente
- Optimistic Locking com `@Version`
- Transação `@TransactionAttribute(REQUIRED)`

## 📦 Estrutura
```
db/
ejb-module/
backend-module/
frontend/
docs/
.github/workflows/
```

---

> Obs.: A integração EJB no Spring Boot está preparada via **JNDI** (exemplo de lookup no `BeneficioEjbClient`). Caso não haja servidor EJB disponível no ambiente local, o backend possui um **fallback** (somente para desenvolvimento). Ajuste as propriedades conforme seu servidor (WildFly/GlassFish/Payara).


## 🎬 Demo (GIF)
Inclua um GIF curto mostrando: *listar → transferir → feedback*.
Coloque o arquivo em `docs/demo.gif` e referencie abaixo:

![Demo](docs/demo.gif)


### 🧪 Teste rápido via curl
```
chmod +x docs/test-transferencia.sh
./docs/test-transferencia.sh
```


### 🇧🇷 Validação PT-BR
Mensagens de validação em português configuradas via `messages.properties` e `@Valid`.
