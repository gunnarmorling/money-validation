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
import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hobsoft.hamcrest.compose.ComposeMatchers.hasFeature;

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
                .filter(v -> ofInvalid(v, model.amount1))
                .findFirst().orElseThrow(() -> new AssertionError("Expected violation"));

        final Annotation annotation1 = violation1.getConstraintDescriptor().getAnnotation();
        assertThat(annotation1.annotationType(), is(DecimalMin.class));
        assertThat((DecimalMin) annotation1, allOf(
                hasFeature("value", DecimalMin::value, is("0")),
                hasFeature("inclusive", DecimalMin::inclusive, is(true))
        ));
    }

    @Test
    public void violatingDecimalMax() {
        final Model model = new Model();
        model.amount2 = euro("2");

        final Set<ConstraintViolation<Model>> violations = validator.validate(model);
        assertThat(violations, hasSize(1));

        final ConstraintViolation<Model> violation2 = violations.stream()
                .filter(v -> ofInvalid(v, model.amount2))
                .findFirst().orElseThrow(() -> new AssertionError("Expected violation"));

        final Annotation annotation2 = violation2.getConstraintDescriptor().getAnnotation();
        assertThat(annotation2.annotationType(), is(DecimalMax.class));
        assertThat((DecimalMax) annotation2, allOf(
                hasFeature("value", DecimalMax::value, is("0")),
                hasFeature("inclusive", DecimalMax::inclusive, is(false))
        ));
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
