# QA Sandbox application - UI Test Automation

This UI Test framework use Selenium + TestNG technologies to automate test cases from QA Sandbox application.

# Table of Contents
1. Used Technologies
2. Installation
3. Test Execution


## Used Technologies and Applications
### Used Technologies
A list of technologies used in the project:
* Java JDK: Version 11
* Selenium: 3.141.59
* TestNG: 7.4.0
* Extent Reports: 2.41.2
### Used applications
* IntelliJ: IntelliJ IDEA 2020.3.1 (Community Edition) Build #IC-203.6682.168
* Chrome: version 89.0.4389.114

## Operating system used for development
* macOS: 10.15.7


## Installation
* Clone project from [GitHub repository](https://github.com/darjansivc/sandbox.git)
```
https://github.com/darjansivc/sandbox.git
```
* When importing project into IntelliJ choose folder:
  ```sandbox-ui-tests```
* Maven will build project using provided dependencies from POM.xml file

> **Note:**
>
> ```chromedriver``` for macOS and Microsoft Windows has been added to the project under ```src/test/java/base/drivers``` .
>  ```TestBase.java``` will detect the operating system and it will switch drivers automatically.
>
> f you are a macOS user you may experience difficulty during code run. If ```"chromedriver" cannot be opened because the developer cannot be verified"``` message show up everything and everything you need is to Open `terminal`, navigate to folder with macOS chromedriver. In our case `src/test/java/base/drivers/mac_drivers/chromedriver` and run command:
```
xattr -d com.apple.quarantine chromedriver
```
## Test Execution
Test Suite is defined in `TestNG.xml` file which you can find in `src/main/resources/` folder.

Execute automated tests by right mouse clicking on `TestNG.xml` file and pressing `Run`.

### Report
After the run is done, a report will be generated in the `extent-reports` folder in the root of the project.

> **NOTE:** If you want to run automated tests with your own credentials, to change environment etc., please change them in the `src/main/resources/global.properties`
