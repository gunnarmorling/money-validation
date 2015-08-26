package org.zalando.money.validation;

/*
 * #%L
 * money-validation
 * %%
 * Copyright (C) 2015 Zalando SE
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

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
