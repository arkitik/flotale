# Flotale Workflow Engine

[![Latest Release](https://img.shields.io/github/v/release/arkitik/flotale)](https://github.com/arkitik/flotale/releases/latest)

A modular, embeddable workflow engine for Kotlin/Spring Boot applications. Flotale manages the full lifecycle of any
business entity — orders, tickets, applications, requests — through configurable multi-stage workflows, with built-in
audit trails, role-based action control, and a ready-to-use REST API.

---

## Table of Contents

- [Benefits](#benefits)
- [Requirements](#requirements)
- [Architecture Overview](#architecture-overview)
- [Getting Started](#getting-started)
- [Plugin Reference](#plugin-reference)
    - [JWT Authentication Plugin](#jwt-authentication-plugin)
    - [Jackson Serializer Plugin](#jackson-serializer-plugin)
- [Registering a Workflow](#registering-a-workflow)
- [Executing Workflow Operations](#executing-workflow-operations)
- [API Reference](#api-reference)
- [Extension Points](#extension-points)
    - [ActionExecutor — Custom Action Logic](#actionexecutor--custom-action-logic)
    - [ActionExecutionValidator — Access Control](#actionexecutionvalidator--access-control)
    - [ElementTaskBroadcaster — Task Lifecycle Events](#elementtaskbroadcaster--task-lifecycle-events)
    - [ActionFormProvider — Dynamic Forms](#actionformprovider--dynamic-forms)
- [Error Handling](#error-handling)
- [Database Tables](#database-tables)
- [Constraints & Limitations](#constraints--limitations)
- [Module Structure](#module-structure)

---

## Benefits

| Benefit                                  | Description                                                                                                                                       |
|------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------|
| **Zero-boilerplate workflow management** | Register entire workflow graphs (stages, tasks, actions) via a type-safe Kotlin DSL — no XML, no UI required.                                     |
| **Dual ORM support**                     | Ships with both Spring Data JPA and Kotlin Exposed adapters. Pick the one that matches your stack.                                                |
| **Auto-wired Spring beans**              | Drop in a starter dependency — `FlotaleDomainEngine` and `FlotaleWorkflowEngine` beans appear in your context automatically. No `@Import` needed. |
| **Pluggable extension points**           | Attach custom business logic, access-control rules, notifications, and form validation without touching engine internals.                         |
| **Full audit trail**                     | Every action execution is persisted with the actor, timestamp, transition path, and any submitted form data.                                      |
| **Ready-to-use REST API**                | Include `flotale-api-element` to get three production-ready HTTP endpoints out of the box.                                                        |
| **JWT security out of the box**          | Add `flotale-plugin-jwt` for automatic `Authorization` header parsing, claims mapping, and role-based action guards.                              |

---

## Requirements

| Requirement                             | Minimum Version |
|-----------------------------------------|-----------------|
| JDK                                     | **21**          |
| Kotlin                                  | **2.3.20**      |
| Spring Boot                             | **4.0.6**       |
| Radix                                   | **v3.1.1**      |
| Kotlin Exposed _(Exposed starter only)_ | **1.2.0**       |

> **JDK 21 is required.** The Kotlin compiler target is set to `21`; the library will not run on earlier JVM versions.

> **Spring Boot 4.x only.** Spring Boot 4 uses the Jakarta EE 9+ namespace (`jakarta.*`). This library is not
> compatible with Spring Boot 2.x or 3.x.

---

## Architecture Overview

```
Your Application
      │
      ├─ FlotaleDomainEngine   ← define & manage workflow structure
      │
      └─ FlotaleWorkflowEngine ← drive elements through the workflow
              │
              ├── ActionExecutionValidator  (who can run an action?)
              ├── ActionExecutor            (what happens when it runs?)
              ├── ElementTaskBroadcaster    (notify on task enter/exit)
              └── ActionFormProvider        (supply & validate forms)
                        │
              ┌─────────▼──────────┐
              │   Core Domain SDK   │  (workflow / stage / task / action / element)
              └─────────┬──────────┘
                        │
              ┌─────────▼──────────┐
              │  JPA  │  Exposed   │  (your choice of ORM adapter)
              └─────────┬──────────┘
                        │
                  Your Database
```

**Module responsibilities:**

- `flotale-engine-core-sdk` — Public API: `FlotaleDomainEngine`, `FlotaleWorkflowEngine`, all DTOs, and the Kotlin
  workflow DSL.
- `flotale-engine-core-function` — Extension point interfaces (`ActionExecutor`, `ActionExecutionValidator`,
  `ElementTaskBroadcaster`, `ActionFormProvider`).
- `flotale-starter-jpa` / `flotale-starter-exposed` — Auto-configuration that wires the engine to your database via
  Spring Data JPA or Kotlin Exposed respectively.
- `flotale-plugin-jwt` — Auto-configured JWT token converter and role-based execution validators.
- `flotale-plugin-serializer-jackson` — Auto-configured Jackson serializer for form data persistence.
- `flotale-api-element` — Spring MVC REST controller exposing three element workflow endpoints.
- `flotale-migrator` — Liquibase changelogs for the eight engine tables.

---

## Getting Started

### Step 1 — Declare the version property

In your `pom.xml` properties block:

```xml

<properties>
    <flotale.version>1.0.0</flotale.version> <!-- replace with the target release -->
</properties>
```

### Step 2 — Import the BOM

```xml

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>io.arkitik.flotale</groupId>
            <artifactId>flotale-dependencies</artifactId>
            <version>${flotale.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

### Step 3 — Add the starter for your ORM

**JPA (Spring Data)**

```xml

<dependency>
    <groupId>io.arkitik.flotale</groupId>
    <artifactId>flotale-starter-jpa</artifactId>
</dependency>
```

**Kotlin Exposed**

```xml

<dependency>
    <groupId>io.arkitik.flotale</groupId>
    <artifactId>flotale-starter-exposed</artifactId>
</dependency>
```

> Only one starter should be on the classpath at a time. Both register a `FlotaleTransactionalExecutor` bean
> (`@ConditionalOnMissingBean`), so they will not conflict if both are present, but using a single one is recommended.

### Step 4 — Run the database migrations

Add `flotale-migrator` and run your Liquibase migration. All eight engine tables are created automatically.

```xml

<dependency>
    <groupId>io.arkitik.flotale</groupId>
    <artifactId>flotale-migrator</artifactId>
</dependency>
```

### Step 5 — Inject the engines and register your workflow

```kotlin
@Component
class MyWorkflowInitializer(
    private val domainEngine: FlotaleDomainEngine,
) : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        if (domainEngine.workflowExist("purchase-order")) return

        domainEngine persistWorkflow {
            addWorkflow {
                workflowKey = "purchase-order"
                workflowName = "Purchase Order Workflow"

                initialStage {
                    stageKey = "draft"
                    stageName = "Draft"

                    initialTask {
                        taskKey = "pending-review"
                        taskName = "Pending Review"

                        addAction {
                            actionKey = "submit"
                            actionName = "Submit for Review"
                            actionDestinationTask = "under-review"
                            actionColor = ActionColor.primary()
                            successExecutionMessage = "Order submitted successfully."
                        }
                    }
                }

                addStage {
                    stageKey = "review"
                    stageName = "Review"

                    initialTask {
                        taskKey = "under-review"
                        taskName = "Under Review"

                        addAction {
                            actionKey = "approve"
                            actionName = "Approve"
                            actionDestinationTask = "approved"
                            actionColor = ActionColor.accent()
                        }
                        addAction {
                            actionKey = "reject"
                            actionName = "Reject"
                            actionDestinationTask = "rejected"
                            actionColor = ActionColor.warn()
                        }
                    }

                    addTask {
                        taskKey = "approved"
                        taskName = "Approved"
                        terminal = true
                    }

                    addTask {
                        taskKey = "rejected"
                        taskName = "Rejected"
                        terminal = true
                    }
                }
            }
        }
    }
}
```

---

## Plugin Reference

### JWT Authentication Plugin

**Use when** your application uses JWT bearer tokens for authentication and you want `FlotaleUserTokenData` resolved
automatically from the `Authorization` header in every controller method.

```xml

<dependency>
    <groupId>io.arkitik.flotale</groupId>
    <artifactId>flotale-plugin-jwt</artifactId>
</dependency>
```

The plugin auto-configures:

- A `FlotaleTokenConverter` bean that parses and verifies JWTs (HMAC secret or RSA public key).
- A Spring MVC `HandlerMethodArgumentResolver` that injects `FlotaleUserTokenData` into controller parameters.
- A default `ActionExecutionValidator.ValidatorUnit` that enforces user and system-role access control.

**Configuration (`application.yml`):**

```yaml
flotale:
  jwt:
    secret: "your-hmac-secret"           # HMAC mode
    # OR
    public-key:
      algorithm: RSA
      content: classpath:keys/public.pem  # RSA mode
    claims:
      userId: sub
      username: preferred_username
      email: email
      roles: roles
    default-validator: true              # enables built-in role validators
    system-user-roles: # roles that are treated as system actors
      - SYSTEM
      - SERVICE_ACCOUNT
```

**Verification modes:**

| Mode     | Configuration            | Description                                                             |
|----------|--------------------------|-------------------------------------------------------------------------|
| HMAC     | `flotale.jwt.secret`     | Verifies signature using a shared secret                                |
| RSA      | `flotale.jwt.public-key` | Verifies signature using a PEM public key                               |
| Unsigned | neither set              | Passes the token through without signature check — for development only |

**Providing a custom token converter:**

Declare a `FlotaleTokenConverter` bean in your context and the default implementation is skipped (
`@ConditionalOnMissingBean`):

```kotlin
@Bean
fun myTokenConverter(): FlotaleTokenConverter = FlotaleTokenConverter { authorizationHeader ->
        // parse header and return FlotaleUserTokenData or throw
    }
```

---

### Jackson Serializer Plugin

**Use when** you use form-based actions and want form submission data (the `executionData` blob) serialized and
deserialized using Jackson. This plugin is required when executing form actions — without it the engine cannot
persist form data.

```xml

<dependency>
    <groupId>io.arkitik.flotale</groupId>
    <artifactId>flotale-plugin-serializer-jackson</artifactId>
</dependency>
```

The plugin auto-configures an `ActionDataSerializer` bean backed by the application's existing `ObjectMapper`.
No additional configuration is needed.

---

## Registering a Workflow

### Kotlin DSL (recommended)

Use the `persistWorkflow` infix extension on `FlotaleDomainEngine`. All operations run in a single transaction:

```kotlin
domainEngine persistWorkflow {
    addWorkflow {
        workflowKey = "leave-request"
        workflowName = "Employee Leave Request"

        initialStage {
            stageKey = "submission"
            stageName = "Submission"

            initialTask {
                taskKey = "draft"
                taskName = "Draft"

                addFormAction {                        // form-based action
                    actionKey = "submit"
                    actionName = "Submit"
                    actionDestinationTask = "pending-approval"
                    actionHint = "Fill in all required fields before submitting."
                    successExecutionMessage = "Leave request submitted."
                }
            }
        }

        addStage {
            stageKey = "approval"
            stageName = "Approval"

            initialTask {
                taskKey = "pending-approval"
                taskName = "Pending Approval"

                addAction {
                    actionKey = "approve"
                    actionName = "Approve"
                    actionDestinationTask = "approved"
                    actionColor = ActionColor.accent()
                }
                addAction {
                    actionKey = "reject"
                    actionName = "Reject"
                    actionDestinationTask = "rejected"
                    actionColor = ActionColor.warn()
                    failedExecutionMessage = "Request rejected by manager."
                }
            }

            addTask { taskKey = "approved"; taskName = "Approved"; terminal = true }
            addTask { taskKey = "rejected"; taskName = "Rejected"; terminal = true }
        }
    }
}
```

### Programmatic API

```kotlin
domainEngine.executeInTransaction {
    domainEngine.registerWorkflow(WorkflowData(key = "leave-request", name = "Employee Leave Request"))
    domainEngine.registerStage("leave-request", StageData("submission", "Submission"), initialStage = true)
    domainEngine.registerTask("submission", TaskData("draft", "Draft", terminal = false), initialTask = true)
    domainEngine.addAction(
        "draft", ActionData(
            key = "submit", name = "Submit",
            destinationTaskKey = "pending-approval",
            formAction = false,
        )
    )
}
```

### Validating a workflow

```kotlin
when (val result = domainEngine.validateWorkflow("leave-request")) {
    is WorkflowValidationResult.Companion.Valid -> println("Workflow is valid")
    is WorkflowValidationResult.Companion.Invalid -> result.errors.forEach {
        println("${it.key}: ${it.reason}")
    }
}
```

---

## Executing Workflow Operations

Inject `FlotaleWorkflowEngine` wherever you need to drive elements through the workflow.

```kotlin
@Service
class LeaveRequestService(
    private val workflowEngine: FlotaleWorkflowEngine,
) {

    /** Called when a new leave request is created in your system. */
    fun onCreate(requestId: String, createdBy: FlotaleUserTokenData) {
        workflowEngine.initiateElement(
            workflowKey = "leave-request",
            elementKey = requestId,
            elementType = "LEAVE_REQUEST",
            addedBy = createdBy,
        )
    }

    /** Returns the current state and available actions for the requesting user. */
    fun getState(requestId: String, requestedBy: FlotaleUserTokenData): ElementDetails =
        workflowEngine.elementDetails(
            elementKey = requestId,
            elementType = "LEAVE_REQUEST",
            requestedBy = requestedBy,
        )

    /** Executes an action (e.g. "approve") on the element. */
    fun executeAction(requestId: String, actionKey: String, actor: FlotaleUserTokenData) {
        workflowEngine.executeAction(
            actionKey = actionKey,
            elementKey = requestId,
            elementType = "LEAVE_REQUEST",
            executedBy = actor,
        )
    }

    /** Executes a form-based action with submitted form data. */
    fun submitForm(requestId: String, actor: FlotaleUserTokenData, formData: Map<String, Any>) {
        workflowEngine.executeAction(
            actionKey = "submit",
            elementKey = requestId,
            elementType = "LEAVE_REQUEST",
            executedBy = actor,
            formData = formData,
        )
    }

    /** Checks if a workflow has already been initiated for this element. */
    fun isInitiated(requestId: String): Boolean =
        workflowEngine.elementExist(requestId, "LEAVE_REQUEST")

    /** Returns the full chronological action history of the element. */
    fun getAudit(requestId: String, user: FlotaleUserTokenData): List<ElementAuditEntry> =
        workflowEngine.elementAudit(
            elementKey = requestId,
            elementType = "LEAVE_REQUEST",
            requestedBy = user,
            ascending = true,
        )
}
```

**`FlotaleUserTokenData.system`** is available for system-initiated operations that do not belong to a human user:

```kotlin
workflowEngine.initiateElement("leave-request", requestId, "LEAVE_REQUEST", FlotaleUserTokenData.system)
```

---

## API Reference

Base path: `flotale/api/v1/workflow/elements/{elementType}/{elementKey}`

All endpoints require an `Authorization: Bearer <token>` header. Requests and responses use `application/json`.

Include the `flotale-api-element` module to expose these endpoints:

```xml

<dependency>
    <groupId>io.arkitik.flotale</groupId>
    <artifactId>flotale-api-element</artifactId>
</dependency>
```

---

### GET `/flotale/api/v1/workflow/elements/{elementType}/{elementKey}`

Returns the current workflow state of an element, including the active task and the list of actions the
authenticated user is permitted to execute.

**Path parameters:**

| Name          | Type   | Required | Description                                    |
|---------------|--------|----------|------------------------------------------------|
| `elementType` | string | ✅        | The type of the element (e.g. `LEAVE_REQUEST`) |
| `elementKey`  | string | ✅        | The unique business key of the element         |

**Response — 200 OK:**

```json
{
  "elementKey": "req-001",
  "elementType": "LEAVE_REQUEST",
  "workflow": {
    "key": "leave-request",
    "name": "Employee Leave Request"
  },
  "stage": {
    "key": "approval",
    "name": "Approval"
  },
  "task": {
    "key": "pending-approval",
    "name": "Pending Approval"
  },
  "actions": [
    {
      "key": "approve",
      "name": "Approve",
      "actionMessage": null,
      "actionColor": "accent",
      "actionHint": null,
      "actionOutlined": false,
      "successExecutionMessage": null,
      "failedExecutionMessage": null,
      "formAction": false,
      "form": null
    }
  ]
}
```

| Status | Description                                |
|--------|--------------------------------------------|
| `200`  | Element details returned                   |
| `401`  | Missing or invalid `Authorization` token   |
| `404`  | Element not found / workflow not initiated |

**curl:**

```bash
curl -X GET "http://localhost:8080/flotale/api/v1/workflow/elements/LEAVE_REQUEST/req-001" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..."
```

---

### POST `/flotale/api/v1/workflow/elements/{elementType}/{elementKey}/actions/{actionKey}`

Executes an action on the element, transitions it to the destination task, and returns the updated element state.

**Path parameters:**

| Name          | Type   | Required | Description                            |
|---------------|--------|----------|----------------------------------------|
| `elementType` | string | ✅        | The type of the element                |
| `elementKey`  | string | ✅        | The unique business key of the element |
| `actionKey`   | string | ✅        | The key of the action to execute       |

**Request body** (optional — only for form-based actions):

```json
{
  "startDate": "2026-06-01",
  "endDate": "2026-06-05",
  "reason": "Annual leave"
}
```

**Response — 200 OK:** Same as `GET` element details (updated state after transition).

| Status | Description                                            |
|--------|--------------------------------------------------------|
| `200`  | Action executed; updated element state returned        |
| `400`  | Form validation failed                                 |
| `401`  | Missing or invalid `Authorization` token               |
| `422`  | Action cannot be executed (wrong state, access denied) |

**curl:**

```bash
curl -X POST "http://localhost:8080/flotale/api/v1/workflow/elements/LEAVE_REQUEST/req-001/actions/approve" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..." \
  -H "Content-Type: application/json"
```

---

### GET `/flotale/api/v1/workflow/elements/{elementType}/{elementKey}/audit`

Returns the complete chronological history of every action executed on the element.

**Path parameters:**

| Name          | Type   | Required | Description                            |
|---------------|--------|----------|----------------------------------------|
| `elementType` | string | ✅        | The type of the element                |
| `elementKey`  | string | ✅        | The unique business key of the element |

**Query parameters:**

| Name        | Type    | Required | Default | Description                                               |
|-------------|---------|----------|---------|-----------------------------------------------------------|
| `ascending` | boolean | ❌        | `true`  | Sort order: `true` = oldest first, `false` = newest first |

**Response — 200 OK:**

```json
{
  "audits": [
    {
      "actionKey": "submit",
      "actionName": "Submit",
      "actionColor": "primary",
      "actionOutlined": false,
      "fromTask": {
        "taskKey": "draft",
        "taskName": "Draft",
        "terminal": false
      },
      "toTask": {
        "taskKey": "pending-approval",
        "taskName": "Pending Approval",
        "terminal": false
      },
      "executedBy": "john.doe",
      "executedAt": "2026-05-14T09:15:00",
      "executionData": {
        "startDate": "2026-06-01",
        "endDate": "2026-06-05",
        "reason": "Annual leave"
      }
    }
  ]
}
```

| Status | Description                                                   |
|--------|---------------------------------------------------------------|
| `200`  | Audit trail returned (empty `audits` array if no actions yet) |
| `401`  | Missing or invalid `Authorization` token                      |
| `404`  | Element not found                                             |

**curl:**

```bash
curl -X GET "http://localhost:8080/flotale/api/v1/workflow/elements/LEAVE_REQUEST/req-001/audit?ascending=true" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..."
```

---

<details>
<summary>OpenAPI 3.1 Specification</summary>

```yaml
openapi: "3.1.0"
info:
  title: Flotale Workflow Engine API
  version: "1.0.0"
  description: REST API for driving elements through Flotale-managed workflows.

servers:
  - url: http://localhost:8080

security:
  - bearerAuth: [ ]

paths:
  /flotale/api/v1/workflow/elements/{elementType}/{elementKey}:
    get:
      summary: Get current element workflow state
      operationId: elementDetails
      tags: [ Elements ]
      parameters:
        - name: elementType
          in: path
          required: true
          schema: { type: string }
        - name: elementKey
          in: path
          required: true
          schema: { type: string }
      responses:
        "200":
          description: Element details
          content:
            application/json:
              schema: { $ref: "#/components/schemas/ElementDetails" }
        "401": { description: Unauthorized }
        "404": { description: Element not found }

  /flotale/api/v1/workflow/elements/{elementType}/{elementKey}/actions/{actionKey}:
    post:
      summary: Execute an action on an element
      operationId: executeAction
      tags: [ Elements ]
      parameters:
        - name: elementType
          in: path
          required: true
          schema: { type: string }
        - name: elementKey
          in: path
          required: true
          schema: { type: string }
        - name: actionKey
          in: path
          required: true
          schema: { type: string }
      requestBody:
        required: false
        content:
          application/json:
            schema:
              type: object
              additionalProperties: true
              description: Form data for form-based actions
      responses:
        "200":
          description: Action executed; updated element state
          content:
            application/json:
              schema: { $ref: "#/components/schemas/ElementDetails" }
        "400": { description: Form validation failed }
        "401": { description: Unauthorized }
        "422": { description: Action cannot be executed }

  /flotale/api/v1/workflow/elements/{elementType}/{elementKey}/audit:
    get:
      summary: Get element action audit trail
      operationId: elementAudit
      tags: [ Elements ]
      parameters:
        - name: elementType
          in: path
          required: true
          schema: { type: string }
        - name: elementKey
          in: path
          required: true
          schema: { type: string }
        - name: ascending
          in: query
          required: false
          schema: { type: boolean, default: true }
      responses:
        "200":
          description: Audit trail
          content:
            application/json:
              schema: { $ref: "#/components/schemas/ElementAuditData" }
        "401": { description: Unauthorized }
        "404": { description: Element not found }

components:
  securitySchemes:
    bearerAuth:
      type: http
      scheme: bearer
      bearerFormat: JWT

  schemas:
    ReferenceData:
      type: object
      required: [ key, name ]
      properties:
        key: { type: string }
        name: { type: string }

    ActionForm:
      type: object
      required: [ fields ]
      properties:
        fields:
          type: array
          items: { $ref: "#/components/schemas/ActionFormField" }

    ActionFormField:
      type: object
      required: [ fieldKey, fieldLabel, fieldType, fieldOrder, fieldRequired, fieldReadOnly ]
      properties:
        fieldKey: { type: string }
        fieldLabel: { type: string }
        fieldType: { type: string }
        fieldOrder: { type: integer }
        fieldRequired: { type: boolean }
        fieldReadOnly: { type: boolean }
        fieldHidden: { type: boolean, default: false }
        fieldDefaultValue: { }
        fieldOptions:
          type: array
          nullable: true
          items: { $ref: "#/components/schemas/ActionFormFieldOption" }

    ActionFormFieldOption:
      type: object
      required: [ key, value ]
      properties:
        key: { type: string }
        value: { type: string }

    ActionDetails:
      type: object
      required: [ key, name, actionColor, actionOutlined, formAction ]
      properties:
        key: { type: string }
        name: { type: string }
        actionMessage: { type: string, nullable: true }
        actionColor: { type: string }
        actionHint: { type: string, nullable: true }
        actionOutlined: { type: boolean }
        successExecutionMessage:{ type:
          string, nullable: true }
        failedExecutionMessage: { type: string, nullable: true }
        formAction: { type: boolean }
        form:
          nullable: true
          $ref: "#/components/schemas/ActionForm"

    ElementDetails:
      type: object
      required: [ elementKey, elementType, workflow, stage, task, actions ]
      properties:
        elementKey: { type: string }
        elementType: { type: string }
        workflow: { $ref: "#/components/schemas/ReferenceData" }
        stage: { $ref: "#/components/schemas/ReferenceData" }
        task: { $ref: "#/components/schemas/ReferenceData" }
        actions:
          type: array
          items: { $ref: "#/components/schemas/ActionDetails" }

    TaskAuditEntry:
      type: object
      required: [ taskKey, taskName, terminal ]
      properties:
        taskKey: { type: string }
        taskName: { type: string }
        terminal: { type: boolean }

    ElementAuditEntry:
      type: object
      required: [ actionKey, actionName, actionColor, actionOutlined, fromTask, toTask, executedBy, executedAt ]
      properties:
        actionKey: { type: string }
        actionName: { type: string }
        actionColor: { type: string }
        actionOutlined: { type: boolean }
        fromTask: { $ref: "#/components/schemas/TaskAuditEntry" }
        toTask: { $ref: "#/components/schemas/TaskAuditEntry" }
        executedBy: { type: string }
        executedAt: { type: string, format: date-time }
        executionData:
          type: object
          nullable: true
          additionalProperties: true

    ElementAuditData:
      type: object
      required: [ audits ]
      properties:
        audits:
          type: array
          items: { $ref: "#/components/schemas/ElementAuditEntry" }
```

</details>

---

## Extension Points

All extension points follow the same pattern: implement the `*Unit` inner interface, declare it as a `@Bean`, and the
engine's `SpringEngineBeanStore` discovers it automatically via the application context. No registration code needed.

---

### ActionExecutor — Custom Action Logic

Implement `ActionExecutor.ExecutorUnit` to run custom business logic when an action is executed.

```kotlin
@Component
class SendNotificationExecutorUnit : ActionExecutor.ExecutorUnit {

    override fun isSupported(actionData: ExecuteActionData): Boolean =
        actionData.actionKey == "approve"

    override fun executeAction(actionData: ExecuteActionData) {
        // send email, publish event, call external API, etc.
        println("Sending approval notification for element ${actionData.elementKey}")
    }
}
```

Multiple `ExecutorUnit` beans can be registered. The engine calls every unit whose `isSupported` returns `true`.

---

### ActionExecutionValidator — Access Control

Implement `ActionExecutionValidator.ValidatorUnit` to control which users may execute a given action.

```kotlin
@Component
class ManagerOnlyValidatorUnit : ActionExecutionValidator.ValidatorUnit {

    override fun isSupported(actionData: ExecuteActionData.Companion.Standard): Boolean =
        actionData.actionKey in listOf("approve", "reject")

    override fun canExecute(actionData: ExecuteActionData.Companion.Standard): Boolean =
        actionData.actor.roles.contains("MANAGER")
}
```

An action is permitted only when **all** matching `ValidatorUnit` beans return `true`.

---

### ElementTaskBroadcaster — Task Lifecycle Events

Implement `EnteringBroadcasterUnit` or `ExitingBroadcasterUnit` to react when an element enters or leaves a task.

```kotlin
@Component
class AuditLogBroadcasterUnit : ElementTaskBroadcaster.EnteringBroadcasterUnit {

    override fun isSupported(
        elementKey: String, elementType: String,
        taskKey: String, executedBy: FlotaleUserTokenData,
    ): Boolean = true  // fire for every task entry

    override fun elementEnter(
        elementKey: String, elementType: String,
        taskKey: String, executedBy: FlotaleUserTokenData,
    ) {
        println("Element $elementKey ($elementType) entered task $taskKey by ${executedBy.username}")
    }
}
```

---

### ActionFormProvider — Dynamic Forms

Implement `ActionFormProvider.FormProviderUnit` to supply a dynamic form schema for a form-based action and to
validate submissions.

```kotlin
@Component
class LeaveSubmitFormProviderUnit : ActionFormProvider.FormProviderUnit {

    override fun isSupported(userAction: ExecuteActionData): Boolean =
        userAction.actionKey == "submit"

    override fun provideForm(userAction: ExecuteActionData): ActionForm = ActionForm(
        fields = listOf(
            ActionFormField("startDate", "Start Date", "DATE", 1, true, false),
            ActionFormField("endDate", "End Date", "DATE", 2, true, false),
            ActionFormField("reason", "Reason", "TEXT", 3, true, false),
        )
    )

    override fun validateForm(userAction: ExecuteActionData.Companion.Form): FormValidationResult {
        val data = userAction.formData
        val errors = mutableListOf<ErrorResponse>()
        if (data["startDate"] == null) errors += ErrorResponse("startDate", "Start date is required")
        if (data["endDate"] == null) errors += ErrorResponse("endDate", "End date is required")
        return if (errors.isEmpty()) FormValidationResult.valid() else FormValidationResult.invalid(errors)
    }
}
```

---

## Error Handling

| Scenario                    | HTTP Status | Engine behaviour                                                                    |
|-----------------------------|-------------|-------------------------------------------------------------------------------------|
| Element not found           | `404`       | `findElementByReference` throws a radix `notFound` exception                        |
| Action not on current task  | `422`       | `executeAction` / `validateExecuteAction` throws `unprocessableEntity`              |
| Action blocked by validator | `422`       | Same as above — `ACTION_CANT_BE_EXECUTED`                                           |
| Form validation failed      | `400`       | `executeAction` throws `badRequest` with field error list                           |
| Invalid/expired JWT         | `401`       | `FlotaleUserTokenDataArgumentResolver` returns 401 before the controller is reached |

---

## Database Tables

Flotale creates and manages the following tables. All tables are owned by the engine — do not write to them directly.

| Table                   | Description                                     |
|-------------------------|-------------------------------------------------|
| `flotale_workflow`      | Workflow definitions                            |
| `flotale_stage`         | Stages within workflows                         |
| `flotale_stage_initial` | Initial-stage markers                           |
| `flotale_task`          | Tasks within stages                             |
| `flotale_task_initial`  | Initial-task markers                            |
| `flotale_action`        | Actions (transitions) between tasks             |
| `flotale_element`       | Active elements being tracked through workflows |
| `flotale_element_flow`  | Audit log of every action execution             |

---

## Constraints & Limitations

- **Kotlin stdlib on classpath** — Flotale is written in Kotlin 2.3.20. The Kotlin stdlib is pulled in transitively and
  will be present in any consuming application.
- **Spring Boot 4.x / Jakarta EE 9+ only** — the `jakarta.*` namespace is used throughout. Spring Boot 2.x and 3.x are
  not supported.
- **Single ORM per deployment** — JPA and Exposed adapters both register the same store beans. Running both starters
  together is technically possible (beans are `@ConditionalOnMissingBean`) but untested.
- **No built-in field-level encryption** — `executionData` (form submissions) is stored as a plain binary blob. Encrypt
  sensitive form values at the application layer before passing them as `formData`.
- **Workflow structure is mutable at runtime** — `deleteWorkflow`, `deleteStage`, `deleteTask`, and `deleteAction`remove
  structures immediately. Deleting a task that has active elements will leave those elements in an inconsistent state.
  Guard destructive operations with `elementExist` checks.
- **`elementKey` + `elementType` must be globally unique per workflow** — there is no namespacing by workflow key at the
  element level. Use a composite identifier (e.g. `"ORD-{orderId}"`) if the same business ID might be used across
  multiple workflows.

---

## Module Structure

```
flotale
├── flotale-dependencies            BOM — import this to manage all Flotale versions
│
├── flotale-protocol                Shared protocol interfaces
│   ├── flotale-protocol-user       FlotaleUserTokenData, FlotaleTokenConverter
│   ├── flotale-protocol-user-converter  Token converter interface
│   ├── flotale-protocol-form       ActionForm, ActionFormField
│   └── flotale-protocol-transactional  FlotaleTransactionalExecutor
│
├── flotale-engine                  Core engine modules
│   └── flotale-engine-core
│       ├── flotale-engine-core-sdk         PUBLIC API — FlotaleDomainEngine, FlotaleWorkflowEngine, DTOs, DSL
│       ├── flotale-engine-core-function    Extension point interfaces
│       ├── flotale-engine-core-operation   Engine implementations (internal)
│       └── flotale-engine-core-port        Spring @Configuration wiring (internal)
│
├── flotale-core                    Aggregate domain/entity/store/adapter modules (internal)
│   ├── flotale-core-workflow
│   ├── flotale-core-stage / flotale-core-stage-initial
│   ├── flotale-core-task  / flotale-core-task-initial
│   ├── flotale-core-action
│   ├── flotale-core-element
│   └── flotale-core-element-flow
│
├── flotale-starter                 Auto-configuration starters
│   ├── flotale-starter-core        Shared starter configuration
│   ├── flotale-starter-jpa         ✅ Add this for Spring Data JPA
│   └── flotale-starter-exposed     ✅ Add this for Kotlin Exposed
│
├── flotale-plugin                  Optional feature plugins
│   ├── flotale-plugin-jwt          ✅ JWT token parsing + role-based validators
│   └── flotale-plugin-serializer
│       └── flotale-plugin-serializer-jackson  ✅ Jackson form data serializer
│
├── flotale-api
│   └── flotale-api-element         ✅ REST controllers (3 endpoints)
│
├── flotale-migrator                Liquibase changelogs for 8 engine tables
│
├── flotale-deploy                  ⚠ Deployment module — not for library consumers
└── flotale-test                    ⚠ Internal integration tests — not for library consumers
```
