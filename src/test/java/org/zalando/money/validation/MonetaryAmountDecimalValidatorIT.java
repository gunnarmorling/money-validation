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


import org.javamoney.moneta.Money;
import org.junit.Test;

import javax.money.MonetaryAmount;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class MonetaryAmountDecimalValidatorIT {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private static boolean ofInvalid(final ConstraintViolation<Model> violation, final Object value) {
        return violation.getInvalidValue().equals(value);
    }

    private static MonetaryAmount euro(final String value) {
        return Money.of(new BigDecimal(value), "EUR");
    }

    /**
     * If there are no constraint validators registered this test would fail with a
     * {@link javax.validation.UnexpectedTypeException}.
     */
    @Test
    public void validatorsAreRegistered() {
        validator.validate(new Model());
    }

    @Test
    public void violatingDecimalMin() {
        final Model model = new Model();
        model.amount1 = euro("-2");

        final Set<ConstraintViolation<Model>> violations = validator.validate(model);
        assertThat(violations, hasSize(1));

        final ConstraintViolation<Model> violation1 = violations.stream()
                .filter(new Predicate<ConstraintViolation<Model>>() {
                    @Override
                    public boolean test(ConstraintViolation<Model> v) {
                        return ofInvalid(v, model.amount1);
                    }
                })
                .findFirst().orElseThrow(new Supplier<AssertionError>() {
                    @Override
                    public AssertionError get() {
                        return new AssertionError("Expected violation");
                    }
                });

        final DecimalMin decimalMin = (DecimalMin) violation1.getConstraintDescriptor().getAnnotation();
        
        assertThat(decimalMin.value(), is("0"));
        assertThat(decimalMin.inclusive(), is(true));
    }

    @Test
    public void violatingDecimalMax() {
        final Model model = new Model();
        model.amount2 = euro("2");

        final Set<ConstraintViolation<Model>> violations = validator.validate(model);
        assertThat(violations, hasSize(1));

        final ConstraintViolation<Model> violation2 = violations.stream()
                .filter(new Predicate<ConstraintViolation<Model>>() {
                    @Override
                    public boolean test(ConstraintViolation<Model> v) {
                        return ofInvalid(v, model.amount2);
                    }
                })
                .findFirst().orElseThrow(new Supplier<AssertionError>() {
                    @Override
                    public AssertionError get() {
                        return new AssertionError("Expected violation");
                    }
                });

        final DecimalMax decimalMax = (DecimalMax) violation2.getConstraintDescriptor().getAnnotation();
        
        assertThat(decimalMax.value(), is("0"));
        assertThat(decimalMax.inclusive(), is(false));
    }

    @Test
    public void noViolationsOnValidValue() {
        final Model model = new Model();
        model.amount1 = euro("2");
        model.amount2 = euro("-2");

        final Set<ConstraintViolation<Model>> violations = validator.validate(model);
        assertThat(violations, hasSize(0));
    }

}
