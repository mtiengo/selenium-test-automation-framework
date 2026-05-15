# Test Automation Framework

A modular, parallel-ready test automation framework covering **UI**, **BDD**, and **API** layers. UI tests use Selenium WebDriver with the Page Object Model; BDD scenarios run via Cucumber on top of the same page objects; API tests use RestAssured. The whole suite runs in CI on every push and PR via GitHub Actions.

Target applications: [DemoQA](https://demoqa.com/) (UI/BDD) and [DummyJSON](https://dummyjson.com/) (API).

---

## Tech Stack

| Technology | Version |
|---|---|
| Java | 21 |
| Selenium WebDriver | 4.41.0 |
| TestNG | 7.10.2 |
| Cucumber (java + testng) | 7.34.3 |
| RestAssured | 6.0.0 |
| AssertJ | 3.26.3 |
| WebDriverManager | 6.3.4 |
| ExtentReports | 5.1.2 |
| extentreports-cucumber7-adapter | 1.14.0 |
| Maven Surefire Plugin | 3.5.4 |

---

## Project Structure

```
selenium-test-automation-framework/
├── .github/workflows/
│   └── test.yml                          # CI matrix: TestNG + Cucumber on every push/PR
├── src/
│   ├── main/java/com/mtiengo/
│   │   ├── base/
│   │   │   └── BasePage.java             # Parent for page objects; exposes utility facades
│   │   ├── pages/
│   │   │   ├── HomePage.java
│   │   │   ├── elements/
│   │   │   │   ├── ElementsPage.java
│   │   │   │   ├── LinksPage.java
│   │   │   │   └── WebTablesPage.java
│   │   │   └── forms/
│   │   │       ├── FormsPage.java
│   │   │       └── PracticeFormPage.java
│   │   └── utilities/
│   │       ├── DriverManager.java        # ThreadLocal WebDriver holder
│   │       ├── CreateDriverUtility.java  # Browser factory (Chrome, Firefox, Edge) with headless mode
│   │       ├── ActionsUtility.java       # Selenium Actions wrappers
│   │       ├── JavaScriptUtility.java    # JS executor helpers
│   │       ├── SwitchToUtility.java      # Window/frame switching
│   │       ├── GetUtility.java           # Element value/attribute/URL retrieval
│   │       ├── ReactSelectUtility.java   # React Select dropdown handler
│   │       ├── ModalUtility.java         # Modal interaction helpers
│   │       └── ScreenShotUtility.java    # Auto-screenshot on failure
│   └── test/
│       ├── java/com/mtiengo/
│       │   ├── tests/                    # TestNG UI tests
│       │   │   ├── base/
│       │   │   │   ├── BaseTest.java
│       │   │   │   └── ExtentReportListener.java
│       │   │   ├── elements/
│       │   │   │   ├── LinksTest.java
│       │   │   │   └── WebTablesTest.java
│       │   │   └── forms/
│       │   │       ├── PracticeFormTest.java
│       │   │       ├── RadioButtonTest.java
│       │   │       └── CheckBoxTest.java
│       │   ├── bdd/                      # Cucumber BDD layer
│       │   │   ├── runners/
│       │   │   │   └── CucumberRunnerTest.java
│       │   │   ├── hooks/
│       │   │   │   └── CucumberHooks.java     # Thread-local driver lifecycle (mirrors BaseTest)
│       │   │   └── stepdefinitions/
│       │   │       ├── HelloSteps.java
│       │   │       └── WebTablesSteps.java
│       │   └── api/                      # RestAssured API tests
│       │       ├── base/
│       │       │   └── ApiBaseTest.java       # RestAssured config + logging filters
│       │       ├── models/
│       │       │   └── Product.java           # Jackson-deserializable record
│       │       └── tests/
│       │           ├── HealthCheckTest.java
│       │           ├── GetProductTest.java
│       │           └── CreateProductTest.java
│       └── resources/
│           ├── features/                 # Gherkin feature files
│           │   ├── hello.feature
│           │   └── web_tables.feature
│           ├── testng-cucumber.xml       # Suite for Cucumber profile
│           └── test-image.jpg
├── reports/                              # ExtentReports HTML (generated at runtime)
├── screenshots/                          # Failure screenshots (generated at runtime)
├── target/cucumber-reports/              # Cucumber HTML + JSON (generated at runtime)
├── testng.xml                            # Default suite (UI + API)
└── pom.xml
```

---

## Architecture

### Page Object Model
All UI interactions are encapsulated in page classes under `pages/`. Each class extends `BasePage`, which provides explicit waits and delegates to utility classes through a Facade pattern — keeping page objects clean and focused on UI behavior.

### Thread-Safe Parallel Execution
`DriverManager` stores each thread's `WebDriver` in a `ThreadLocal`, ensuring isolation between tests running in parallel. Both `BaseTest` (for TestNG) and `CucumberHooks` (for BDD) follow the same pattern, so scenarios and tests can be safely interleaved across threads without shared state. The Cucumber runner enables parallel scenarios via `@DataProvider(parallel = true)`.

### Driver Factory with Headless Support
`CreateDriverUtility` exposes a `Browser` enum (`CHROME`, `FIREFOX`, `EDGE`) and instantiates the appropriate driver via WebDriverManager. Headless mode is toggled by the `-Dheadless=true` system property and applies the right flags per browser (Chrome/Edge use `--headless=new`). In headless mode, window size is set to 1920×1080 via the WebDriver API to work around `--window-size` flag inconsistencies.

### BDD Layer
Cucumber scenarios reuse the same page objects as TestNG tests. `CucumberHooks` provides the same lifecycle guarantees as `BaseTest` (driver setup, screenshot on failure, teardown). Reports are wired through the ExtentReports Cucumber 7 adapter so BDD runs end up in the same `reports/` output as TestNG runs.

### API Layer
`ApiBaseTest` configures RestAssured globally: base URI (overridable via `-Dapi.baseUri`), a Jackson `ObjectMapper` with `FAIL_ON_UNKNOWN_PROPERTIES=false`, and full request/response logging filters. Tests use Hamcrest matchers on the response body and AssertJ on extracted record instances for richer assertions.

---

## Running Tests

### Prerequisites
- Java 21+
- Maven 3.6+
- Chrome, Firefox, or Edge installed (for non-headless UI runs)

### Maven profiles

| Profile | Suite XML | What it runs |
|---|---|---|
| `testng` (default) | `testng.xml` | UI TestNG + API tests |
| `cucumber` | `src/test/resources/testng-cucumber.xml` | Cucumber BDD scenarios |
| `all` | both | Everything |

### Common commands

```bash
# Default: TestNG suite (UI + API), Chrome, 5 parallel threads
mvn test

# Cucumber BDD suite
mvn test -P cucumber

# Everything (TestNG + Cucumber)
mvn test -P all

# Headless mode (used in CI)
mvn test -Dheadless=true

# Specific browser
mvn test -Dbrowser=firefox
mvn test -Dbrowser=edge

# Override API base URI (e.g., a staging mirror of DummyJSON)
mvn test -Dapi.baseUri=https://staging.example.com

# Run a single test class
mvn test -Dtest=HealthCheckTest,GetProductTest,CreateProductTest
```

### Adjust parallelism
Edit `testng.xml`:
```xml
<suite name="Selenium Suite" parallel="methods" thread-count="5">
```

---

## Test Suites

### UI — `WebTablesTest` (7 tests)
CRUD and validation on the DemoQA Web Tables widget: add, edit, delete; search with hits and misses; form validation for invalid email, missing fields, and non-numeric age.

### UI — `LinksTest` (2 tests)
API-call links (assert HTTP status code and response body) and new-tab links (verify URL and return navigation).

### UI — `PracticeFormTest` (1 E2E) `[smoke]`
End-to-end form submission covering text inputs, date picker, radio buttons, checkboxes, autocomplete subjects, React Select dropdowns, and file upload. Every field reflected in the confirmation modal is verified via `SoftAssert`.

### UI — `RadioButtonTest` (1 test)
Mutual exclusivity of gender radio buttons across sequential selections.

### UI — `CheckBoxTest` (1 test)
Idempotent checkbox toggling — programmatic click/unclick produces the correct checked state per hobby.

### BDD — `web_tables.feature` (3 scenarios)
- Add a new record (smoke)
- Search returns a matching record
- Delete a record the test created (with `Given a record exists` precondition step)

### BDD — `hello.feature` (1 scenario)
Sanity check that the Cucumber + TestNG wiring executes end to end.

### API — `HealthCheckTest` (1 test)
`GET /test` on DummyJSON; asserts `status=ok` and `method=GET`.

### API — `GetProductTest` (2 tests)
- `GET /products` — paginated list shape (`products[]`, `total`, `limit`)
- `GET /products/{id}` — single product schema with type-aware matchers

### API — `CreateProductTest` (1 test)
`POST /products/add` with a full `Product` payload. Hamcrest asserts on the response body, then deserializes the JSON root into a `Product` record and runs AssertJ checks on the extracted instance.

---

## CI/CD

GitHub Actions runs the full suite on every push to `main` and every PR via `.github/workflows/test.yml`. The job uses a matrix to run TestNG and Cucumber profiles in parallel on `ubuntu-latest`, both in headless mode.

Artifacts uploaded on every run:
- **ExtentReport HTML** — `extent-report-{testng|cucumber}`
- **Failure screenshots** (only on failure) — `failure-screenshots-{testng|cucumber}`

The workflow can also be triggered manually via `workflow_dispatch`.

---

## Reporting

| Output | Path |
|---|---|
| ExtentReports HTML (TestNG + Cucumber, unified) | `reports/extent-reports.html` |
| Cucumber native HTML | `target/cucumber-reports/cucumber-html-report.html` |
| Cucumber JSON (for downstream tooling) | `target/cucumber-reports/cucumber.json` |
| Failure screenshots | `screenshots/<timestamp>_<testName>.png` |

---

## Key Design Decisions

| Decision | Rationale |
|---|---|
| `ThreadLocal` for driver and page objects | Enables `parallel="methods"` (TestNG) and parallel scenarios (Cucumber) without race conditions |
| `BasePage` Facade over utility classes | Page objects delegate context-free operations without inheritance from multiple utilities |
| Shared page objects across TestNG and Cucumber | One source of truth for UI interaction; BDD layer adds Gherkin on top, no duplicated locators |
| `Hobby` and `StateCity` enums in page classes | Type-safe, IDE-discoverable test data; eliminates magic strings in tests |
| `SoftAssert` in multi-assertion UI tests | Collects all failures before reporting — full picture per run rather than first-failure exit |
| Hamcrest + AssertJ in API tests | Hamcrest for fluent inline body matchers; AssertJ for richer post-extraction assertions on records |
| Records (not POJOs) for API models | Concise, immutable, native Jackson 3 deserialization support |
| Maven profiles for suite selection | Single command (`mvn test -P …`) picks TestNG, Cucumber, or both — same model used by the CI matrix |
| Headless via system property | One flag (`-Dheadless=true`) flips all three browsers; CI uses it, local dev doesn't have to |
| WebDriverManager | No manual driver binaries; correct version resolved at runtime |
