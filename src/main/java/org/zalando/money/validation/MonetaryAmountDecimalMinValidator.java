package org.zalando.money.validation;

import org.hibernate.validator.internal.constraintvalidators.bv.DecimalMinValidatorForNumber;

import javax.validation.ConstraintValidator;
import javax.validation.constraints.DecimalMin;

public class MonetaryAmountDecimalMinValidator extends MonetaryAmountDecimalValidator<DecimalMin> {

    public MonetaryAmountDecimalMinValidator() {
        this(defaultValidator());
    }

    public MonetaryAmountDecimalMinValidator(final ConstraintValidator<DecimalMin, Number> validator) {
        super(validator);
    }

    private static ConstraintValidator<DecimalMin, Number> defaultValidator() {
        return new DecimalMinValidatorForNumber();
    }

}