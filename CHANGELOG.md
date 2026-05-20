## [1.0.1](https://github.com/arkitik/flotale/compare/v1.0.0...v1.0.1) (2026-05-20)


### Bug Fixes

* **JPA:** fix jpa action repository ([40a9cca](https://github.com/arkitik/flotale/commit/40a9cca8b0e0461e57b9d7d6466f325d4a3a4fcb))

# 1.0.0 (2026-05-14)


### Bug Fixes

* add workflowExist method to FlotaleDomainEngine and implement WorkflowExistByKeyRole for workflow existence checks ([4ec9aa2](https://github.com/arkitik/flotale/commit/4ec9aa295a9d934c684020384c9e7c212e6fd3c1))
* handle invalid form validation errors ([3054b1f](https://github.com/arkitik/flotale/commit/3054b1f9261b69d201f54088f31fab9e5d154c64))
* implement executeInTransaction method for transactional command execution ([7469b52](https://github.com/arkitik/flotale/commit/7469b52b2d1602e50aa8c95f3e76214e12c69f8f))


### Features

* Add CI workflows for compile checks and release process ([72ca552](https://github.com/arkitik/flotale/commit/72ca55237ad730b4ead8e6ba0f7dba726605aca2))
* add element audit functionality to track action history for elements ([4c5bd08](https://github.com/arkitik/flotale/commit/4c5bd0810cd9c881d4f935e169b8c764e4ed8ec4))
* add elementType to ElementDomain and actionType + ActionFormProvider to actions ([76b0bfe](https://github.com/arkitik/flotale/commit/76b0bfe282f0315d7c4a18bc902cd3c7d4ef37c6))
* add system user roles to FlotaleJwtProperties and implement action execution validators for system and user roles ([677c8d8](https://github.com/arkitik/flotale/commit/677c8d88addba5be1f944d68956bb988db075969))
* enhance action domain with additional properties and implement user token data handling ([5054756](https://github.com/arkitik/flotale/commit/5054756c7645008bf0d3fac785ec2fd8967ee6f2))
* enhance element flow with execution data and audit tracking ([f3779c5](https://github.com/arkitik/flotale/commit/f3779c58461ac34e45c28e991b70ab82cf6a71b6))
* initial implementation ([381bca2](https://github.com/arkitik/flotale/commit/381bca2a5351ba82ae4de819de70eda02a5e129f))
* Introduce DTOs for workflow, stage, task, and action, and update related queries and configurations to support JPA and kotlin-exposed ([63cf265](https://github.com/arkitik/flotale/commit/63cf2653a68adde00c10bf94b0759c37f058e8e4))
* Introduce FlotaleUserData interface and enhance the workflow builder with a build method ([ed83dea](https://github.com/arkitik/flotale/commit/ed83dea7a6bf0811b6eec39c02b6bff32e58d0c8))
* refactor action execution to use ExecuteActionData DTO and implement transactional execution ([df0bd03](https://github.com/arkitik/flotale/commit/df0bd0336ea4e2a006b0ee4d9ccff45cea62196f))
