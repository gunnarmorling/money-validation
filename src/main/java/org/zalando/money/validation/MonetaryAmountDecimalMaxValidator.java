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
import javax.validation.constraints.DecimalMax;
import java.math.BigDecimal;

public class MonetaryAmountDecimalMaxValidator implements ConstraintValidator<DecimalMax, MonetaryAmount> {

    private BigDecimal maxValue;

    private boolean inclusive;

    @Override
    public void initialize(final DecimalMax annotation) {
        this.maxValue = new BigDecimal(annotation.value());
        this.inclusive = annotation.inclusive();
    }

    @Override
    public boolean isValid(final MonetaryAmount value, final ConstraintValidatorContext context) {
        // null values are valid
        if (value == null) {
            return true;
        }

        final BigDecimal amount = value.getNumber().numberValueExact(BigDecimal.class);
        int comparisonResult = amount.compareTo(maxValue);
        return inclusive ? comparisonResult <= 0 : comparisonResult < 0;
    }
}
