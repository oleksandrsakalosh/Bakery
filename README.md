# Sakalosh Oleksandr

![Logo](https://github.com/oleksandrsakalosh/Bakery/blob/main/src/main/java/data/logo.jpg)
# Bakery system

## Short project information:	

This project is being developed in Java in IntelliJ IDEA using Maven libraries.

The system will help manage bakery business. There are 3 classes for different types of users: admin, baker and seller. 
Admin sees all workers sign in system, can delete them, sees sells, sets salaries, taxes and mark-up. Baker adds new products and ingredients for them, adds new baked products in stock. 
Seller can sell products from stock. Program generate prices according to the costs of ingridients and mark-up. 
Program has database for products, ingredients and workers.
Also you can register in system as baker or seller, but your password have to be at least 4 characters long, must have at least 1 letter and 1 number.

## Environment

- IntelliJ IDEA Educational Edition, version: 2020.3.2 Build id: IE-203.7717.75
- JDK Java 8(1.8.0_281)
- Scene Builder
- Maven(com.gembox.spreadsheet version: 1.1.1256)

## Certain implementations

My project have:

### Hierarchy
Inheritance, polymorphism, applied interface as well as correct encapsulation are applied in these hierarchies.

![System hierarchy](https://github.com/oleksandrsakalosh/Bakery/blob/main/Documentation/System%20hierarchy.png)
![Account hierarchy](https://github.com/oleksandrsakalosh/Bakery/blob/main/Documentation/Account%20hierarchy.png)

You can see the inheritance between the BakerySystem and the more specific AccountSystem class. 
The polymorphism can be seen in the getLogin method, which overlaps every class in the tree.

![Method getLogin](https://github.com/oleksandrsakalosh/Bakery/blob/main/Documentation/getLogin.png)

### Aggregation
You can see aggregation in AlertSystem. 

![AlertSystem](https://github.com/oleksandrsakalosh/Bakery/blob/main/Documentation/AlertSystem.png)

When user enter wrong information, system creates new AlertSystem class and pass text to show.

![Creating alert](https://github.com/oleksandrsakalosh/Bakery/blob/main/Documentation/Creating%20AlertSystem.png)

### Serializaation
I have implemented serialization in LogicSystem.

![LogicSystem](https://github.com/oleksandrsakalosh/Bakery/blob/main/Documentation/LogicSystem.png)

Settings being deserialized when program starts and serialized when admin changing it.

![Deserialization](https://github.com/oleksandrsakalosh/Bakery/blob/main/Documentation/Deserialization.png)
![Serialization](https://github.com/oleksandrsakalosh/Bakery/blob/main/Documentation/Serialization.png)

## Installation
Make sure you have right version of Java and downloaded librarices.

You can register in system as:
- Baker - login: Bob, password: a1234
- Seller - login: David, password: pass0
- Admin - login: admin, password: 0000

## Project Skeleton
UML Class Diagram:

![Diagram](https://github.com/oleksandrsakalosh/Bakery/blob/main/Documentation/Main%20Diagram.png)

Database Schema:

![Database](https://github.com/oleksandrsakalosh/Bakery/blob/main/Documentation/Invoice%20database.png)


