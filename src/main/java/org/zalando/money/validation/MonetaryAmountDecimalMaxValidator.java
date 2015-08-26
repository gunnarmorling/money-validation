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
