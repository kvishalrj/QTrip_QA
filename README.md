# QTrip QA

## Overview
**QTrip** is a travel website, and the purpose of this project is to design and automate test cases for the application using the **TestNG** framework.

### Project Highlights:
- Designed and automated **4+ test cases** using the **Page Object Model (POM)** design pattern in combination with **Page Factory**, **Selenium**, and **TestNG**.
- Utilized **Apache POI** to dynamically handle test data in each test case.
- Created a structured **TestNG project** to streamline the execution of automated test cases.
- Applied the **Singleton pattern** for initializing **WebDriver** to ensure resource efficiency and reduce unnecessary instantiations.
- Generated customized test reports for the TestNG project using **Extent Reports** for enhanced reporting and result visualization.

## Tools and Technologies Used:
- **Selenium WebDriver**
- **TestNG**
- **Page Object Model (POM)**
- **Page Factory**
- **Apache POI**
- **Extent Reports**
- **Java**
- **Maven/Gradle** (as applicable for project setup)
  
## Project Structure:
The project is divided into the following key modules:

1. **Page Objects**:
   - Contains the web elements and methods representing various pages of the QTrip application.

2. **Test Cases**:
   - Test classes designed following the Page Object Model with methods calling the appropriate Page Object functions.
   - Each test can handle dynamic data inputs using Apache POI.

3. **TestNG Configurations**:
   - Includes TestNG XML files for running the tests, configuring parallel execution, and managing test suites.

4. **Reports**:
   - Customized test reports generated after the test execution, providing detailed insights into the test run, including screenshots on failures.

## How to Run:
1. Clone the repository.
2. Import the project into your IDE (IntelliJ IDEA, Eclipse, etc.).
3. Ensure **Java**, **Selenium**, **TestNG**, and other dependencies are installed.
4. Execute the test suite by running the `TestNG.xml` file or using Maven/Gradle commands.

### Running Tests:
```bash
mvn test
```
or
```bash
gradle test
```

## Reporting:
After the test run is complete, you can view the detailed test report generated using **Extent Reports** under the `app` directory.

