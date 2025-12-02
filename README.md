# 📘 School Management API

**Uma API REST** construída com **Spring Boot 3**, **Java 17** e **Spring Security OAuth2** para **gestão escolar completa** – desde matrícula até boletins, financeiro e documentos.

---

## 📦 O que o sistema faz

- **Autenticação via Google / GitHub** (OAuth2) – **sem senha própria**
- **Gestão de alunos, professores, responsáveis, funcionários**
- **Matrículas, turmas, disciplinas, horários**
- **Lançamento de notas e frequência**
- **Geração automática de boletins**
- **Planos de pagamento e controle de mensalidades**
- **Upload de documentos** (atestados, transferências, etc.)
- **Notificações** (e-mail / SMS / push) – _pronto para integrar_
- **Auditoria completa** (LGPD) – _quem alterou o quê e quando_

---

## 🧱 Tecnologias & Dependências

| Tecnologia | Versão | Uso |
|------------|--------|-----|
| **Java** | 17 | Linguagem |
| **Spring Boot** | 3.5.8 | Framework base |
| **Spring Security** | 6.5.7 | Autenticação OAuth2 + autorização |
| **Spring Data JPA** | 3.2.x | Persistência |
| **PostgreSQL** | 15+ | Banco de dados |
| **Flyway** | 9+ | Migrações de banco |
| **JWT** | 0.12.5 | Token próprio (opcional) |
| **SpringDoc OpenAPI** | 2.6.0 | Swagger UI |
| **Maven** | 3.9+ | Build |
| **Docker & Docker Compose** | _latest_ | Containerização |
| **AWS SDK v2** | 2.29+ | S3 (upload de arquivos) |
| **Firebase Admin** | 9.2+ | Push notifications (opcional) |

---

## 🚀 Como rodar localmente

### 1. Clone o repositório
```bash
git clone https://github.com/seu-usuario/school-management-api.git
cd school-management-api
```

### 2. Configure as variáveis de ambiente
Crie um arquivo `.env` na raiz:
```env
DB_URL=jdbc:postgresql://localhost:5432/school
DB_USER=postgres
DB_PASS=postgres
GOOGLE_CLIENT_ID=seu-client-id
GOOGLE_CLIENT_SECRET=seu-client-secret
JWT_SECRET=uma-chave-de-256-bits-aqui
AWS_ACCESS_KEY_ID=xxx
AWS_SECRET_ACCESS_KEY=xxx
AWS_S3_BUCKET=school-documents
```

### 3. Suba o banco (Docker)
```bash
docker-compose up -d
```

### 4. Compile e execute
```bash
./mvnw clean install
./mvnw spring-boot:run
```

### 5. Acesse
- **API**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html

---

## 📊 Diagrama de Entidades (simplificado)

```mermaid
erDiagram
    PERSON ||--o{ USER : has
    PERSON ||--o{ STUDENT : is
    PERSON ||--o{ TEACHER : is
    PERSON ||--o{ GUARDIAN : is
    PERSON ||--o{ EMPLOYEE : is
    STUDENT ||--o{ ENROLLMENT : enrolls
    TEACHER ||--o{ CLASS : teaches
    SCHOOL_TERM ||--o{ CLASS : contains
    DISCIPLINE ||--o{ CLASS : composes
    COURSE ||--o{ DISCIPLINE : has
    ENROLLMENT ||--o{ GRADE : receives
    ENROLLMENT ||--o{ ATTENDANCE : has
    ENROLLMENT ||--o{ REPORT_CARD : generates
    STUDENT ||--o{ PAYMENT_PLAN : owns
    PAYMENT_PLAN ||--o{ FEE : contains
    PERSON ||--o{ DOCUMENT : uploads
    PERSON ||--o{ ADDRESS : lives
    PERSON ||--o{ NOTIFICATION : receives
    USER ||--o{ AUDIT_LOG : generates
```

---

## 🔐 Autenticação & Autorização

- **Login via Google / GitHub** (OAuth2) – **sem senha**
- **Sessão HTTP** (cookie `JSESSIONID`) ou **JWT** (configurável)
- **Roles**:
  - `STUDENT`, `GUARDIAN`, `TEACHER`, `EMPLOYEE`, `FINANCIAL`, `ADMIN`
- **Swagger UI** com **botão “Authorize”** para testes

---

## 📁 Endpoints principais (exemplos)

| Método | Endpoint | Descrição | Role mínima |
|--------|----------|-----------|-------------|
| `GET` | `/api/auth/me` | Dados do usuário logado | autenticado |
| `POST` | `/api/persons` | Cadastrar pessoa | ADMIN |
| `GET` | `/api/students/{id}/report-cards` | Boletins do aluno | STUDENT, GUARDIAN |
| `POST` | `/api/payment-plans` | Criar plano de pagamento | FINANCIAL |
| `PATCH` | `/api/fees/{id}/pay` | Pagar parcela | FINANCIAL |
| `POST` | `/api/documents` | Upload de arquivo | SECRETARY |
| `GET` | `/swagger-ui.html` | Documentação interativa | — |

---

## 🧪 Testes

- **Unitários**: JUnit 5 + Mockito
- **Integração**: TestContainers + PostgreSQL
- **Cobertura**: JaCoCo (mínimo 80 %)

Executar:
```bash
./mvnw test
```

---

## 🚀 Deploy

### Docker (pronto)
```bash
docker build -t school-api .
docker run -p 8080:8080 --env-file .env school-api
```

### Kubernetes (exemplo)
Arquivos YAML em `/k8s` (deployment, service, config-map, secret).

---

## 📄 Licença
**MIT** – livre para uso comercial e educacional.

---

## 🤝 Contribuições
1. Faça um **fork**
2. Crie sua **branch** (`git checkout -b feature/nova-funcionalidade`)
3. **Commit** suas mudanças (`git commit -m 'Add nova funcionalidade'`)
4. **Push** para a branch (`git push origin feature/nova-funcionalidade`)
5. Abra um **Pull Request**

---

**Desenvolvido com ❤️ e Java**  
**Happy coding!**