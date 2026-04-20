# Selenium Test Automation Framework

A modular, parallel-ready UI test automation framework built with Selenium WebDriver and TestNG, applying the Page Object Model (POM) pattern. Developed against [DemoQA](https://demoqa.com/) as the target application.

---

## Tech Stack

| Technology | Version |
|---|---|
| Java | 21 |
| Selenium WebDriver | 4.41.0 |
| TestNG | 7.10.2 |
| WebDriverManager | 6.3.3 |
| ExtentReports | 5.1.2 |
| Maven Surefire Plugin | 3.5.4 |

---

## Project Structure

```
selenium-test-automation-framework/
├── src/
│   ├── main/java/com/mtiengo/
│   │   ├── base/
│   │   │   └── BasePage.java            # Parent class for all page objects; exposes utility facades
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
│   │       ├── DriverManager.java       # ThreadLocal WebDriver holder
│   │       ├── CreateDriverUtility.java # Browser factory (Chrome, Firefox, Edge)
│   │       ├── ActionsUtility.java      # Selenium Actions wrappers
│   │       ├── JavaScriptUtility.java   # JS executor helpers
│   │       ├── SwitchToUtility.java     # Window/frame switching
│   │       ├── ReactSelectUtility.java  # Custom React Select dropdown handler
│   │       ├── ModalUtility.java        # Modal interaction helpers
│   │       └── ScreenShotUtility.java   # Auto-screenshot on test failure
│   └── test/java/com/mtiengo/
│       ├── tests/
│       │   ├── base/
│       │   │   ├── BaseTest.java              # setUp/tearDown with ThreadLocal page objects
│       │   │   └── ExtentReportListener.java  # TestNG listener for HTML reporting
│       │   ├── elements/
│       │   │   ├── LinksTest.java
│       │   │   └── WebTablesTest.java
│       │   └── forms/
│       │       ├── PracticeFormTest.java
│       │       ├── RadioButtonTest.java
│       │       └── CheckBoxTest.java
│       └── resources/
│           └── test-image.jpg           # File used in upload test
├── reports/                             # ExtentReports HTML output (generated at runtime)
├── screenshots/                         # Failure screenshots (generated at runtime)
├── testng.xml                           # Suite definition and parallel execution config
└── pom.xml
```

---

## Architecture

### Page Object Model
All page interactions are encapsulated in page classes under `pages/`. Each class extends `BasePage`, which provides explicit waits and delegates to utility classes through a Facade pattern — keeping page objects clean and focused on UI behavior.

### Thread-Safe Parallel Execution
`DriverManager` stores each thread's `WebDriver` in a `ThreadLocal`, ensuring full isolation between tests running in parallel. `BaseTest` follows the same pattern for `HomePage` instances. This eliminates shared state across threads.

### Driver Factory
`CreateDriverUtility` provides a `Browser` enum (`CHROME`, `FIREFOX`, `EDGE`) and instantiates the appropriate driver via WebDriverManager, which handles binary management automatically — no manual driver downloads needed.

---

## Running Tests

### Prerequisites
- Java 21+
- Maven 3.6+
- Chrome, Firefox, or Edge installed

### Run the full suite (default: Chrome, 5 parallel threads)
```bash
mvn test
```

### Run on a specific browser
```bash
mvn test -Dbrowser=firefox
mvn test -Dbrowser=edge
```

### Run with a custom thread count
Edit `testng.xml` and adjust `thread-count`:
```xml
<suite name="Selenium Suite" parallel="methods" thread-count="5">
```

### Run the smoke group only
Uncomment the `<test name="Smoke">` block in `testng.xml` and run:
```bash
mvn test
```

---

## Test Suites

### Elements — `WebTablesTest` (7 tests)
Covers CRUD operations and validation on the Web Tables widget:
- Add, edit, and delete records
- Search/filter with existing and non-existent terms
- Form validation: invalid email format, missing required fields, non-numeric age

### Elements — `LinksTest` (2 tests)
- API call links: asserts HTTP status code and message in the response body
- New-tab links: verifies correct URL in the new tab and successful return to the original

### Forms — `PracticeFormTest` (1 E2E test) `[smoke]`
Full end-to-end form submission covering text inputs, date picker, radio buttons, checkboxes, autocomplete subjects, React Select dropdowns, and file upload. Validates every field reflected in the confirmation modal using `SoftAssert`.

### Forms — `RadioButtonTest` (1 test)
Verifies mutual exclusivity of gender radio buttons across multiple sequential selections.

### Forms — `CheckBoxTest` (1 test)
Verifies idempotent checkbox toggling — checks that programmatic click and unclick reflect the correct checked state for each hobby option.

---

## Reporting

An HTML report is generated after each run at:
```
reports/extent-reports.html
```

On test failure, a timestamped screenshot is saved to:
```
screenshots/<timestamp>_<testName>.png
```

---

## Key Design Decisions

| Decision | Rationale |
|---|---|
| `ThreadLocal` for driver and page objects | Enables `parallel="methods"` in TestNG without race conditions |
| `BasePage` Facade over utility classes | Page objects delegate context-free operations without inheritance from multiple utilities |
| `Hobby` and `StateCity` enums in page classes | Type-safe, IDE-discoverable test data; eliminates magic strings in tests |
| `SoftAssert` in multi-assertion tests | Collects all failures before reporting, giving a full picture of a test run rather than stopping at the first mismatch |
| WebDriverManager | Removes manual driver binary management; resolves the correct version at runtime |
