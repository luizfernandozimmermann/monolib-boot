# Monolib

Monolib is a plug-and-play Spring Boot library designed to accelerate monolithic application development.

It provides a pre-configured infrastructure layer including authentication, session management, JPA integration, base entities, translation support, and optional ready-to-use REST endpoints.

It is not a framework replacement. It is an infrastructure layer built on top of Spring Boot.

---

## Table of Contents

* [Overview](#monolib)
* [Modules](#modules)
* [Installation](#installation)
* [Authentication Model](#authentication-model)
* [Getting Started](#getting-started)
  * [Authentication](#authentication)
  * [Web-Ready Endpoints](#web-ready-endpoints)
  * [Entities](#entities)
  * [Repository Abstractions](#repository-abstractions)
  * [Web Layer](#web-layer-abstractions)
  * [Permissions](#permissions)
  * [Entity CRUD Base Handlers](#entity-crud-base-handlers)
* [Translations](#translations)
* [Database](#database)
* [Overriding Behavior](#overriding-behavior)
* [Intended Use](#intended-use)
* [License](#license)

---

## Modules

* **Core** – Shared interfaces and base models (no implementations)
* **Auth** – Custom token-based authentication and session management
* **Data** – JPA/Hibernate integration and persistence abstractions
* **Web** – Web-layer annotations and request handling abstractions
* **Starter** – Aggregates Core, Auth, Data, and Web
* **Web-Ready** – Provides default authentication and CRUD endpoints

---

## Installation

### Starter (recommended)

```xml
<dependency>
    <groupId>dev.luizzimmermann</groupId>
    <artifactId>monolib-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Optional: Prebuilt Endpoints

```xml
<dependency>
    <groupId>dev.luizzimmermann</groupId>
    <artifactId>monolib-web-ready</artifactId>
    <version>1.0.0</version>
</dependency>
```

Monolib auto-configures:

* Authentication and session management
* JPA/Hibernate integration
* Translation infrastructure
* Base entities

#### Required configuration

Provide the following properties in `application.yml` or `application.properties` (data and auth modules):

```properties
# data module
monolib.data.url=jdbc:postgresql://localhost:5432/yourdb
monolib.data.user=${DB_USER:}
monolib.data.password=${DB_PASS:}
monolib.data.driver=org.postgresql.Driver

# auth module
monolib.auth.session-token-expiration-time=900
monolib.auth.session-token=change-this-secret
monolib.auth.password-token=change-this-secret
```

#### Application entry point

A minimal Spring Boot starter class looks like:

```java
@ServiceApplication
public class Application {
    public static void main(String[] args) {
        ApplicationStarter.start(Application.class, args);
    }
}
```

With those in place your app will start with Monolib components ready to go.

---

## Authentication Model

Monolib exposes a simple session-based authentication API through the `AuthenticationCoreService` facade. Internally a session contains an access token and refresh token; tokens are hashed before persistence and never stored raw.

Typical operations are:

```java
Session authenticate(String accessToken);
Session signIn(String email, String password);
Session changePassword(User user, String oldPassword, String newPassword);
Session createFirstUser(String email, String password, String username);
Session refreshSession(String refreshToken);
void signOut(Session session);
```

The web-ready module wires handlers that call this service and automatically manage cookies for you. Access tokens are short-lived (15 minutes by default) and refresh tokens are persisted until revoked.

Authentication checks occur on every request, validating the token against a non-expired, non-revoked session. No JWTs are involved.

---

## Getting Started

### Authentication

Use `AuthenticationCoreService` from your own handlers or services to manage user sessions. The web‑ready module provides ready‑made endpoints and automatically sets/clears cookies for you.

### Web-Ready Endpoints

When you include **monolib-web-ready** the following REST endpoints are made available out of the box (all under the `/authentication` base path unless stated otherwise):

* `POST /authentication/signIn` – create session by email/password
* `POST /authentication/refreshSession` – rotate tokens using refresh cookie
* `POST /authentication/signOut` – invalidate current session
* `GET /authentication/getSessionInfo` – return session metadata
* `POST /authentication/createFirstUser` – bootstrap first user (enabled when `monolib.web.starter.bootstrap=true`)

In addition, a default CRUD surface is exposed for the built‑in user record at `record/user`:

* `POST /record/user` – create user
* `GET /record/user` – list/paginate users
* `GET /record/user/{id}` – fetch single user
* `PUT /record/user/{id}` – update user
* `DELETE /record/user/{id}` – remove user

Each of these handlers applies the usual permission checks and can be overridden or replaced by defining your own beans.



### Entities

Define your domain models by extending one of the provided bases (`EntityBase` or `LogicalEntityBase`). Fields may be annotated with `@Field` to control access and update behaviour; auditing and ID management are handled automatically.

Example:

```java
@Entity
public class MyEntity extends EntityBase {
    @Field(accessible = true)
    private String name;
}
```

If you prefer to expose DTOs, subclass `EntityBaseDto` (or `LogicalEntityBaseDto`) and provide a constructor that takes the entity.

---

### Repository Abstractions

Repositories follow standard Spring Data JPA patterns:

```java
@Repository
public interface UserRepository extends BaseRepository<User> {
}
```

Monolib handles base configuration and entity scanning.

---

### Web Layer Abstractions

Monolib provides custom annotations for request handling that wrap Spring MVC and make controllers declarative and consistent. The two primary controller annotations are:

* `@Handler` – general-purpose REST controller. You can specify a base path, required permissions, and whether it represents an entity-based controller.
* `@EntityHandler` – specialization of `@Handler` configured with `ControllerType.ENTITY`. Use it when your controller exposes CRUD operations for a persistent entity.

Each request method uses the following annotations to indicate the HTTP verb and security requirements:

* `@PostRequest` – maps to HTTP POST
* `@PutRequest` – maps to HTTP PUT
* `@GetRequest` – maps to HTTP GET
* `@DeleteRequest` – maps to HTTP DELETE

All of the above annotations are meta‑annotated with `@ApiRequest`, which itself is a thin wrapper around `@RequestMapping`. They support attributes such as `path`, `permissions`, `authenticate`, and `authenticateFirstSession`.

#### Example handler

```java
@Handler(path = "/users")
public class UserHandler {
    @GetRequest(path = "/info", permissions = {"USER_READ"})
    public UserDto info() {
        // ...
    }
}
```

You can turn authentication on or off per-method and supply custom permission strings. At runtime an interceptor inspects the current session and ensures the user has the required permissions, returning HTTP 403 when checks fail. The annotations themselves simply delegate to Spring’s request mapping infrastructure.

---
### Permissions

Permissions are derived automatically from your handler annotations when the application starts. Each method-level annotation (`@PostRequest`, `@GetRequest`, etc.) brings along any permission strings you declare, and the framework adds sensible defaults (e.g. turning a bare resource name into `resource:read` for GET requests). Entity handlers additionally produce CRUD-style permissions based on their base path.

The generated collection is stored through the `PermissionCoreService` and can be combined with a JSON seed file placed at `classpath:permissions/roles_and_permissions.json`. This seed lets you establish initial roles (such as `ADMIN`) without hardcoding permissions in both code and storage.

Runtime enforcement is handled by an interceptor that checks the current session against the required permissions on every request and returns HTTP 403 if validation fails.

---
### Entity CRUD Base Handlers

To avoid boilerplate for standard create/read/update/delete endpoints, Monolib provides abstract base controllers in the \`monolib.web.entity.handler\` package. Extend one class per operation and annotate it with `@EntityHandler` to automatically expose a working REST endpoint:

* `CreateEntityHandler<E, D>` – `POST /` accepting a DTO body
* `ReadEntityHandler<E, D>` – `GET /` for paging and `GET /{id}` for single item
* `UpdateEntityHandler<E, D>` – `PUT /{id}`
* `DeleteEntityHandler<E, D>` – `DELETE /{id}`

Each handler is a thin subclass over the corresponding data controller and includes transactional annotations. You may override protected hooks such as `validate` to add custom business rules.

#### CRUD example (multiple classes)

```java
@EntityHandler(path = "record/user")
public class UserCreateHandler extends CreateEntityHandler<UserEntity, UserDto> {}

@EntityHandler(path = "record/user")
public class UserReadHandler extends ReadEntityHandler<UserEntity, UserDto> {}

@EntityHandler(path = "record/user")
public class UserUpdateHandler extends UpdateEntityHandler<UserEntity, UserDto> {
    @Override
    protected void validate(UserEntity before, UserEntity after) {
        // custom validation logic
    }
}

@EntityHandler(path = "record/user")
public class UserDeleteHandler extends DeleteEntityHandler<UserEntity, UserDto> {
    @Override
    protected void validate(UserEntity entity) {
        // check permissions
    }
}
```

You can also implement your own behavior in a single class by extending one handler and defining additional methods with `@GetRequest`, `@PostRequest`, etc., but splitting per operation keeps concerns separated and matches the conventions used by the web-ready module.

---

## Translations

Recommended structure:

```
translations/messages_pt_BR.properties
translations/messages_en_US.properties
...
```

---

## Database

* Built on JPA + Hibernate
* No database vendor dependency
* Migration tested with PostgreSQL
* Single-schema architecture

Configure your datasource via `application.yml` or `application.properties`.

---

## Intended Use

* Monolithic Spring applications
* Projects requiring standardized infrastructure
* Rapid bootstrap without repetitive setup

---

## License

Define your license (MIT / Apache 2.0 recommended for open-source).

---
