# Money Validation

[![Money magnifier](docs/money-validation.jpg)](https://pixabay.com/en/coins-handful-russia-ruble-kopek-650779/)

[![Build Status](https://img.shields.io/travis/zalando/money-validation.svg)](https://travis-ci.org/zalando/money-validation)
[![Coverage Status](https://img.shields.io/coveralls/zalando/money-validation.svg)](https://coveralls.io/r/zalando/money-validation)
[![Release](https://img.shields.io/github/release/zalando/money-validation.svg)](https://github.com/zalando/money-validation/releases)
[![Maven Central](https://img.shields.io/maven-central/v/org.zalando/money-validation.svg)](https://maven-badges.herokuapp.com/maven-central/org.zalando/money-validation)

*Money Validation* is a collection of [Bean Validation](http://beanvalidation.org/) constraint validators that add
support for [Java Money](https://github.com/JavaMoney/jsr354-api) data types when using `@Min`, `@Max`, `@DecimalMin` 
and `@DecimalMax` constraints.

## Features

# Getting started

## Requirements
- Java 7 or 8
- Any build tool using Maven Central, or direct download
- A Bean Validation 1.1 compatible implementation, e.g. [Hibernate Validator](http://hibernate.org/validator)
- Java Money 1.0

## Dependencies

Use this library:

```xml
<dependency>
    <groupId>org.zalando</groupId>
    <artifactId>money-validation</artifactId>
    <version>${money-validation.version}</version>
</dependency>
```

For ultimate flexibility, this module is compatible with the official version as well as the backport of Java Money.
The actual version will be selected by a profile based on the current JDK version.

## Using It

Use constraint annotations on your fields of type `javax.money.MonetaryAmount`. The validators are automatically 
registered by your validation framework using SPI.

```java
class Product {

    @Min(0)
    private MonetaryAmount price;
    
    @Max(0)
    private MonetaryAmount discount;
        
}
```

```java
class Product {

    @DecimalMin("0", inclusive = false)
    private MonetaryAmount price;
    
    @DecimalMax(value = "0")
    private MonetaryAmount discount;
        
}
```

## Contributing
To contribute to Money Validation, simply make a pull request and add a brief description (1-2 sentences) of your
addition or change. Please note that we aim to keep this project straightforward and focused. We are not looking to add
lots of features; we just want it to keep doing what it does, as well and as powerfully as possible.
