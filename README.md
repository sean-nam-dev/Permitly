# Permitly

Permitly is an Android app built with Kotlin using **MVVM + Clean Architecture**. The project emphasizes **readable, testable, and maintainable code**, with fully tested UI, ViewModels, and business logic.

## Platform

* **Android** (Kotlin)
* Future roadmap includes **Kotlin Multiplatform (KMP)** support

## Tech Stack

* **Language:** Kotlin
* **UI:** Jetpack Compose
* **Navigation:** Compose Navigation
* **Architecture:** MVVM + Clean Architecture
* **Dependency Injection:** Hilt
* **Asynchronous:** Kotlin Coroutines

## Architecture

The app follows **Clean Architecture principles** combined with **MVVM**:

```
permitly
│
├── app
│
├── presentation
│
├── data
│
└── domain
```

### Key points:

* UI is **state-driven** and reacts to immutable state updates
* ViewModels contain no Android UI dependencies beyond lifecycle awareness
* Business logic is isolated and fully testable
* Clear separation between layers

Although the project is currently **single-module**, it is structured for **easy migration to multi-module or KMP**.

## UI Layer

* Fully built with **Jetpack Compose**
* Unidirectional data flow (UDF)
* Explicit UI state models
* Minimal logic inside composables
* Navigation handled via **Compose Navigation**, scoped per screen

## Testing Strategy

### Test types covered:

* **Unit tests** (domain & ViewModels)
* **UI tests** (Compose UI tests)
* **Integration-style tests** where applicable

### Libraries:

* JUnit
* Mockito
* Compose UI Test

### Approach:

* **TDD** wherever possible
* Tests define expected behavior before implementation
* UI and ViewModel logic are tested independently

## CI

* Continuous Integration runs automatically on PRs
* Ensures tests pass before merging

## Design Goals

* High test coverage
* Clear architectural boundaries
* Easy refactoring and feature evolution
