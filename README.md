This repository contains regression test suite on public API PETSTORE application on PET endpoint

The swagger can be found in https://petstore.swagger.io/#/

The test is built in maven framework that is written in JAVA

Test suite can be found in /src/main/java/petEndpoint/regressionTest

/src/main/resources contains the json template files for requests and expected responses for some of the test cases

Command line to run the tests with Maven is:

mvn -B test --file pom.xml

GitHub Action set up to execute these tests but for some reason currently doesn't run the test fully.