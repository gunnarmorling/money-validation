package org.zalando.money.validation;

import javax.money.MonetaryAmount;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.math.BigDecimal;

public class MonetaryAmountDecimalValidator<A extends Annotation> implements ConstraintValidator<A, MonetaryAmount> {

    private final ConstraintValidator<A, Number> validator;

    public MonetaryAmountDecimalValidator(final ConstraintValidator<A, Number> validator) {
        this.validator = validator;
    }

    @Override
    public void initialize(final A decimalMin) {
        validator.initialize(decimalMin);
    }

    @Override
    public boolean isValid(final MonetaryAmount monetaryAmount, final ConstraintValidatorContext context) {
        return monetaryAmount == null || validator.isValid(monetaryAmount.getNumber().numberValue(BigDecimal.class), context);
    }
}
