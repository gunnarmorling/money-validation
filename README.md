# Money Validation

[![Money magnifier](docs/money-validation.jpg)](https://pixabay.com/en/coins-handful-russia-ruble-kopek-650779/)

[![Build Status](https://img.shields.io/travis/zalando/money-validation.svg)](https://travis-ci.org/zalando/money-validation)
[![Coverage Status](https://img.shields.io/coveralls/zalando/money-validation.svg)](https://coveralls.io/r/zalando/money-validation)
[![Javadoc](https://javadoc-emblem.rhcloud.com/doc/org.zalando/money-validation/badge.svg)](http://www.javadoc.io/doc/org.zalando/money-validation)
[![Release](https://img.shields.io/github/release/zalando/money-validation.svg)](https://github.com/zalando/money-validation/releases)
[![Maven Central](https://img.shields.io/maven-central/v/org.zalando/money-validation.svg)](https://maven-badges.herokuapp.com/maven-central/org.zalando/money-validation)

*Money Validation* is a collection of [Bean Validation](http://beanvalidation.org/) constraint validators that add
support for [Java Money](https://github.com/JavaMoney/jsr354-api) data types.

## Features

- enables you to validate monetary amounts
- uses existing, standardized constraints
- offers additional, more expressive custom constraints
- can be use with any Bean Validation implementation

## Dependencies

- Java 7 or 8
- Any build tool using Maven Central, or direct download
- A Bean Validation 1.1 compatible implementation, e.g. [Hibernate Validator](http://hibernate.org/validator)
- Java Money 1.0

## Installation

Add the following dependency to your project:

```xml
<dependency>
    <groupId>org.zalando</groupId>
    <artifactId>money-validation</artifactId>
    <version>${money-validation.version}</version>
</dependency>
```

For ultimate flexibility, this module is compatible with the official version as well as the backport of Java Money.
The actual version will be selected by a profile based on the current JDK version.

The validators are automatically registered by your validation framework using SPI.

## Usage

Use constraint annotations on your fields of type `javax.money.MonetaryAmount` with any of the following constraints:

| Official | Constraint        | Condition  |
|----------|-------------------|------------|
| yes      | `@Min`            | `x <= n`   |
| yes      | `@Max`            | `x <= n`   |
| yes      | `@DecimalMin`     | `x </<= n` |
| yes      | `@DecimalMax`     | `x >/>= n` |
| no       | `@Positive`       | `x > 0`    |
| no       | `@PositiveOrZero` | `x >= 0`   |
| no       | `@Negative`       | `x < 0`    |
| no       | `@NegativeOrZero` | `x <= 0`   |
| no       | `@Zero`           | `x = 0`    |

```java
class Product {

    @Positive
    private MonetaryAmount price;
    
    @NegativeOrZero
    private MonetaryAmount discount;
        
}
```

## Getting help

If you have questions, concerns, bug reports, etc, please file an issue in this repository's Issue Tracker.

## Getting involved

To contribute to Money Validation, simply make a pull request and add a brief description (1-2 sentences) of your
addition or change. Please note that we aim to keep this project straightforward and focused. We are not looking to add
lots of features; we just want it to keep doing what it does, as well and as powerfully as possible. For more details
check the [contribution guidelines](CONTRIBUTING.md).

