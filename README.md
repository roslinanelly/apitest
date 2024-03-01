This repository contains regression test suite on public API PETSTORE application on PET endpoint

The swagger can be found in https://petstore.swagger.io/#/

The test is built in maven framework that is written in JAVA

Test suite can be found in /src/test/java/petEndpoint/regressionTest

/src/test/resources contains the json template files for requests and expected responses for some of the test cases

Command line to run the tests with Maven is:
mvn -B test --file pom.xml
