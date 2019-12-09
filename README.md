# GUI Test Automation Framework - JAVA/TESTNG
 GUI Test Automation Framework with TestNG , Data Driven and Extent Reporter

## How to run the project
1. Clone or download the project from github repository
2. Change directory to the project directory

## Executing the tests
To run the tests , type `mvn test` on the command line for default browser selection

As per `config.properties` settings, the tests will run Headless Chrome for the purpuse of CI/CD implementation.

## Test Report 
The Extent test results will be recorded in the `Reports` directory as a HTML Report 

## CircleCI Integration
Any changes to code triggers circleci pipeline and test is executed over docker image
