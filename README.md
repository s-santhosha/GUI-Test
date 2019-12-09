# GUI Test Automation Framework - JAVA/TESTNG
 GUI Test Automation Framework with TestNG , Data Driven and Extent Reporter

## How to run the project
1. Clone or download the project from github repository
2. Change directory to the project directory

## Executing the tests
To run the tests , type `mvn test` on the command line for default browser selection
Or
To run the tests with specific browser 

By default, the tests will run using Chrome Headless for the purpuse of CI/CD implementation.

In order to "turn off" the headless mode, use `mvn verify -Ddriver=chrome -Dheadless.mode=false`

To run them in Firefox by overriding the `driver` system property, e.g.

```json
$ mvn verify -Ddriver=firefox
```
## Test Report 
The Extent test results will be recorded in the `Reports` directory as a HTML Report 

## CircleCI Integration
Any changes to code triggers circleci pipeline and test is executed over docker image
