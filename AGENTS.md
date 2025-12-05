# Repository Guidelines

## Project Structure & Module Organization
- Backend lives under `src/main/java/com/ssafy/nayeogi`, split into `common` (global configs & DTOs), `member`, `recommendation`, and `image` service areas. Keep new domain logic inside feature-specific packages and expose entry points via controllers.
- SQL mappers are stored in `src/main/resources/mappers`, while schema/reference data sits in `src/main/resources/scheme`. Update the mapper file name to mirror the repository interface (e.g., `MemberMapper.xml`).
- Shared configuration (Spring profiles, AWS, DB) belongs in `src/main/resources/application.properties`; override sensitive values through environment-specific property files ignored by Git.

## Build, Test, and Development Commands
- `./mvnw spring-boot:run` — boots the API with hot reload for local iteration; append `-Dspring-boot.run.profiles=local` to use local DB credentials.
- `./mvnw clean package` — compiles Java 17 sources, runs tests, and produces the runnable JAR in `target/`.
- `./mvnw test` — executes the unit/integration suite without building artifacts; use before every push.

## Coding Style & Naming Conventions
- Follow Spring Boot defaults: 4-space indentation, brace-on-same-line for methods/classes, and Lombok for boilerplate (`@Getter`, `@Builder`, etc.).
- REST controllers end with `Controller`, services with `Service`, and mapper interfaces with `Mapper`. Request/response DTOs live under `common.dto` and should be suffixed with `Request`/`Response`.
- Prefer constructor injection; keep transactional boundaries on service methods via `@Transactional`.

## Testing Guidelines
- Place tests under `src/test/java/com/ssafy/nayeogi`; mirror the production package layout. Name files `*Tests` (unit) or `*IT` (slice/integration).
- Use `@SpringBootTest` for cross-layer flows and `@MybatisTest` or mocked repositories for mapper-focused cases. Seed DB-dependent tests with scripts in `src/test/resources`.
- Aim to cover controller happy-paths, validation failures, and mapper edge cases; fail the build if critical tests are missing.

## Commit & Pull Request Guidelines
- Keep commits small with imperative summaries (e.g., `image삭제기능 추가`, `recommendation 패키지 구조 변경`). Reference related Jira/issue IDs when applicable.
- PRs must describe intent, highlight schema changes, list manual test evidence, and attach screenshots for API contract updates. Request review from at least one maintainer and ensure CI (`./mvnw test`) is green before submission.

## Security & Configuration Tips
- Do not commit credentials; rely on `${ENV}` placeholders inside `application.properties` and document required env vars in the PR.
- S3 and MySQL connectivity require IAM keys and JDBC URLs—store them in your shell profile or a local `.env` consumed by your IDE.
