# backend-payment

ReSpark 중고거래 플랫폼의 MSA 결제 서비스다. 현재는 기본 개발 환경 구성 단계이며, 실제 결제 기능은 포함하지 않는다. Kafka는 사용하지 않는다.

## 주요 기능

- Spring Cloud Config Client 연동
- Eureka 서비스 등록
- MySQL(JPA) 접속 준비
- SpringDoc OpenAPI
- Actuator / Prometheus 메트릭 노출 준비
- OpenFeign 의존성 준비 (실제 호출 코드 없음)

## 기술 스택

- Java 17
- Spring Boot 4.0.10
- Spring Cloud 2025.1.5
- Gradle 9.9.2
- MySQL
- SpringDoc OpenAPI 3.0.3

## MSA 역할

- Config Server에서 `backend-payment` / `backend-payment-{profile}` 설정을 조회한다.
- Eureka에 `backend-payment` 이름으로 등록한다.
- Gateway 명시 라우트는 별도 작업이다. Discovery Locator로 `/backend-payment/**` 접근을 확인할 수 있다.

## 디렉터리 구조

```
backend-payment/
├── src/main/java/com/spark/backend_payment/
│   ├── BackendPaymentApplication.java
│   └── config/OpenApiConfig.java
├── src/main/resources/application.yaml
├── .env.example
├── payment-hom.local.yml.example
└── build.gradle
```

## 환경변수

실제 값은 `.env`에 두고 Git에 올리지 않는다. 예시는 `.env.example`을 참고한다.

| 이름 | 용도 |
|---|---|
| `SPRING_PROFILES_ACTIVE` | `local` / `dev` / `prod` |
| `CONFIG_SERVER_URL` | Config Server 주소. 기본 `http://localhost:8888` |
| `EUREKA_URL` | Eureka `defaultZone` (Config의 공통 프로파일에서 참조) |
| `DB_URL` | MySQL JDBC URL |
| `DB_USERNAME` | DB 사용자 |
| `DB_PASSWORD` | DB 비밀번호 |

### 집(홈) 로컬 오버라이드

주말 집 PC처럼 Docker 없이 `localhost`로 붙는 환경은 `payment-hom.local.yml.example`을 `payment-hom.local.yml`로 복사해 사용한다. 이 파일은 `.gitignore`에 포함된다.

## 실행 방법

1. Config Server, Eureka, MySQL을 준비한다.
2. `.env.example`을 `.env`로 복사하고 값을 채운다.
3. 집 환경이면 `payment-hom.local.yml`을 준비한다.
4. MySQL에 `payment` 스키마와 계정을 만든다 (아래 SQL 예시).
5. 실행:

```
./gradlew bootRun
```

Windows:

```
gradlew.bat bootRun
```

포트는 Config의 `server.port: 0`으로 동적 할당된다. 실제 포트는 기동 로그 또는 Eureka 콘솔에서 확인한다.

## 검증

- Config: `curl http://localhost:8888/backend-payment/local`
- Eureka: `http://localhost:8761`
- Health: `http://localhost:{port}/actuator/health`
- Prometheus: `http://localhost:{port}/actuator/prometheus`
- Swagger UI: `http://localhost:{port}/swagger-ui.html`
- API Docs: `http://localhost:{port}/v3/api-docs`
- Gateway Swagger: `http://localhost:8000/index.html` (서비스 등록 후)

## Spotless

```
./gradlew spotlessApply
./gradlew spotlessCheck
```

## MySQL 준비 예시

사용자가 직접 실행한다.

```sql
CREATE DATABASE IF NOT EXISTS payment
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

CREATE USER IF NOT EXISTS 'payment_user'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON payment.* TO 'payment_user'@'localhost';
FLUSH PRIVILEGES;
```

## 협업

브랜치는 `main`, `develop`, `feature/{이슈번호}-작업내용`을 사용한다.
