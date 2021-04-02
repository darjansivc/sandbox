# QA Sandbox application - API Test Automation

This API Test framework use RestAssured library + TestNG technologies to automate test cases from QA Sandbox application.

# Table of Contents
1. Used Technologies
2. Installation
3. Test Execution


## Used Technologies and Applications
### Used Technologies
A list of technologies used in the project:
* Java JDK: Version 11
* RestAssured: 4.3.3
* Allure TestNg report: 2.13.8
* Maven
### Used applications
* IntelliJ: IntelliJ IDEA 2020.3.1 (Community Edition) Build #IC-203.6682.168

## Operating system used for development
* macOS: 10.15.7


## Installation
* Clone project from [GitHub repository](https://github.com/darjansivc/sandbox.git)
```
https://github.com/darjansivc/sandbox.git
```
* When importing project into IntelliJ choose folder:
  ```sandbox-api-tests```
* Maven will build project using provided dependencies from POM.xml file


## Test Execution
> **Preconditions**
>
> 1. Install Maven on your local machine, so you can run test suite from a console.
> ```macOS``` users can simply run
>
```
brew install maven
``` 
>
> 2. Install Allure report by running simple command in terminal(`macOS` users)
>
 ```
brew install allure
```

Test Suite is defined in `TestNG.xml` file which you can find in `src/test/resources/` folder.


Execute automated tests by:
> 1. Open terminal and navigate to the project folder
> 2. Run command `mvn clean test`

### Report
After the run is done, a report will be generated in the `target/allure-results` folder, but to see report 
> 1. Open terminal and navigate to the project's `target` folder
> 2. Run command `allure serve`

> **NOTE:** If you want to run automated tests with your own credentials or to change environment etc., please change them in the `src/test/resources/global.properties`