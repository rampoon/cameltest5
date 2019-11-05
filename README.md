# Cameltest5

## Description.

Test of Camel using Spring boot (auto configuration) and Docker. 
* ArtemisMQ as input
* Adding a header
* Logging headers (key, value) to logfile
* split message on element `<order>` 
* creating separate output files
* each outputfile gets a unique filename
* Unittests using ActiveProfiles

## How to build locally
```
cd to directory where pom.xml (root pom) is locaded
mvn clean package spring-boot:repackage
```

## How to run locally
```
cd to taget directory
java -jar cameltest5-1.0.0-SNAPSHOT.jar --spring.profiles.active=artemismq
```
