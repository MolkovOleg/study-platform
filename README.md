# Study Platform Service

REST‑backend для учебной платформы. Поддерживает работу с курсами, модулями и уроками, регистрацию пользователей и ролей (преподаватель, студент, администратор), записи на курсы, задания с решениями, тесты/квизы и отзывы.

## Технологии
- Java 21 / Spring Boot 3.5
- Spring Data JPA, Bean Validation
- PostgreSQL (prod) / H2 (тесты)
- Liquibase отсутствует, схема создаётся Hibernate (`ddl-auto`)

## Подготовка окружения
| Компонент | Версия по умолчанию |
|-----------|---------------------|
| Java      | 21 (Temurin)        |
| Maven     | wrapper `./mvnw`    |
| PostgreSQL| 16+                 |

Переменные подключения задаются в `application.yml` или через ENV:

```bash
DB_URL=jdbc:postgresql://localhost:5432/study-platform-db
DB_USERNAME=postgres
DB_PASSWORD=your_password
```

## Запуск локально (без Docker)
```bash
# 1. Собрать и прогнать тесты
./mvnw clean verify

# 2. Запустить приложение
./mvnw spring-boot:run
```

По умолчанию сервис слушает `http://localhost:8080`. Базовые данные подгружаются из `src/main/resources/data.sql`.

## Запуск через Docker
```bash
docker compose up --build
```

В составе `docker-compose.yml`:
- `db` — PostgreSQL 16 с постоянным volume `postgres_data`
- `app` — образ из локального `Dockerfile`, порт `8080`

При необходимости измените пароли/опции JVM в `docker-compose.yml` перед деплойментом.

## Тесты
- Интеграционные сценарии: `CourseIntegrationTest`, `EnrollmentReviewIntegrationTest`, `QuizIntegrationTest`
- Профиль `test` использует H2 и не подтягивает `data.sql`

## Структура API
Контроллеры находятся в пакете `com.molkovor.studyplatform.controller`. Ниже кратко описаны основные:

| Контроллер | Базовый URL | Основные эндпоинты |
|------------|-------------|--------------------|
| `CourseRestController` | `/api/courses` | `POST /` – создать курс, `POST /{id}/modules` – добавить модуль, `POST /modules/{id}/lessons` – добавить урок, `GET /{id}` – получить курс (в т.ч. вместе с модулями/уроками), `PATCH /{id}` – обновление, `DELETE /{id}` – безопасное/принудительное удаление |
| `EnrollmentRestController` | `/api/enrollments` | `POST /` – записать студента на курс, `DELETE /{enrollmentId}` – отписать, `GET /course/{courseId}` – студенты курса, `GET /student/{studentId}` – курсы студента |
| `AssignmentRestController` | `/api/assignments` | `POST /lessons/{lessonId}` – создать задание, `GET /lessons/{lessonId}` – список заданий урока |
| `SubmissionRestController`* | `/api/submissions` | `POST /assignments/{assignmentId}` – отправить решение, `PATCH /{id}` – оценить/добавить комментарий, `GET /assignment/{assignmentId}` и `/student/{studentId}` – выборки решений |
| `QuizRestController` | `/api/quizzes` | `POST /modules/{moduleId}` – создать квиз, `POST /{quizId}/questions` – добавить вопрос/ответы, `POST /{quizId}/take` – сдача теста, `PATCH /submissions/{submissionId}` – ручная оценка, `GET /{quizId}/results`, `/student/{studentId}` – результаты |
| `ReviewRestController` | `/api/reviews` | `POST /courses/{courseId}` – оставить/обновить отзыв, `GET /courses/{courseId}` – отзывы по курсу, `GET /students/{studentId}` – отзывы студента |
| `UserRestController` | `/api/users` | `POST /` – регистрация, `PATCH /{id}` – обновление профиля, `GET /{id}` и `/` – получение по id и списком, `DELETE /{id}` – удаление |

`*` Текущая логика по управлению отправками инкапсулирована в `SubmissionService`; при необходимости REST-слой легко добавляется по аналогии с остальными контроллерами.

DTO находятся в `com.molkovor.studyplatform.dto`. Все входные данные валидируются аннотациями Bean Validation.

## Структура проекта
```
study-platform/
├── src/
│   ├── main/
│   │   ├── java/com/molkovor/studyplatform/
│   │   │   ├── controller/         # REST-контроллеры
│   │   │   ├── dto/                # DTO-запросы/ответы
│   │   │   ├── entity/             # JPA-сущности (course, quiz, study, user, etc.)
│   │   │   ├── exception/          # GlobalExceptionHandler и кастомные исключения
│   │   │   ├── repository/         # Spring Data JPA репозитории
│   │   │   ├── service/            # Бизнес-логика
│   │   │   └── StudyPlatformApplication.java
│   │   └── resources/
│   │       ├── application.yml     # основные настройки
│   │       └── data.sql            # базовые данные
│   └── test/
│       ├── java/com/molkovor/studyplatform/integration/
│       │   ├── CourseIntegrationTest.java
│       │   ├── EnrollmentReviewIntegrationTest.java
│       │   └── QuizIntegrationTest.java
│       └── resources/application-test.yml
├── Dockerfile
├── docker-compose.yml
├── README.md
└── pom.xml
```

