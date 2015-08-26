package org.zalando.money.validation;

import org.hibernate.validator.internal.constraintvalidators.bv.DecimalMaxValidatorForNumber;

import javax.validation.ConstraintValidator;
import javax.validation.constraints.DecimalMax;

public class MonetaryAmountDecimalMaxValidator extends MonetaryAmountDecimalValidator<DecimalMax> {

    public MonetaryAmountDecimalMaxValidator() {
        this(defaultValidator());
    }

    public MonetaryAmountDecimalMaxValidator(final ConstraintValidator<DecimalMax, Number> validator) {
        super(validator);
    }

    private static ConstraintValidator<DecimalMax, Number> defaultValidator() {
        return new DecimalMaxValidatorForNumber();
    }

}
