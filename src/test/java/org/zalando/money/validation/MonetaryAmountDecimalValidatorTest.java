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

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import org.javamoney.moneta.Money;
import org.junit.Test;

import javax.money.MonetaryAmount;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class MonetaryAmountDecimalValidatorTest {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private static Predicate<ConstraintViolation<Model>> rejected(final Object value) {
        return new Predicate<ConstraintViolation<Model>>() {

            @Override
            public boolean apply(final ConstraintViolation<Model> violation) {
                return Objects.equals(violation.getInvalidValue(), value);
            }
        };
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

        final ConstraintViolation<Model> violation = FluentIterable.from(violations)
                .firstMatch(rejected(model.amount1))
                .get();

        final DecimalMin decimalMin = (DecimalMin) violation.getConstraintDescriptor().getAnnotation();
        
        assertThat(decimalMin.value(), is("0"));
        assertThat(decimalMin.inclusive(), is(true));
    }

    @Test
    public void violatingDecimalMax() {
        final Model model = new Model();
        model.amount2 = euro("2");

        final Set<ConstraintViolation<Model>> violations = validator.validate(model);
        assertThat(violations, hasSize(1));

        final ConstraintViolation<Model> violation = FluentIterable.from(violations)
                .firstMatch(rejected(model.amount2))
                .get();

        final DecimalMax decimalMax = (DecimalMax) violation.getConstraintDescriptor().getAnnotation();
        
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
