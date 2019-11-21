#Cameltest5

## Description.

Test of Camel using Spring boot (auto configuration) and Docker. 
* Producer: 
* XML message (customers and orders) as input to ArtemisMQ.

* Consumer:
* Validation against schema customerorder.xsd.
* Unmarshall from XML to Object structure.
* Save to database (customers and orders).
* Marshall from Object structure to XML message.
* split message on element `<customerorder>` 
* Stax parsing id for each customer.
* Creating separate output files each outputfile gets a unique filename based on id of customer.

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
