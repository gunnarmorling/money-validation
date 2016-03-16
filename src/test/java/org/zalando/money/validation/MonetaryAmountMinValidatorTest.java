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
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MonetaryAmountMinValidatorTest {

    private final ConstraintValidator<Min, MonetaryAmount> unit = new MonetaryAmountMinValidator();

    @Test
    public void nullIsValid() {
        unit.initialize(min(0));

        final boolean valid = unit.isValid(null, context());

        assertThat(valid, is(true));
    }

    @Test
    public void invalidIfLess() {
        unit.initialize(min(0));
        final boolean valid = unit.isValid(euro("-1"), context());

        assertThat(valid, is(false));
    }

    @Test
    public void validIfGreater() {
        unit.initialize(min(0));
        final boolean valid = unit.isValid(euro("1"), context());

        assertThat(valid, is(true));
    }

    @Test
    public void validIfInclude() {
        unit.initialize(min(0));
        final boolean valid = unit.isValid(euro("0"), context());

        assertThat(valid, is(true));
    }

    private MonetaryAmount euro(final String value) {
        return Money.of(new BigDecimal(value), "EUR");
    }

    private ConstraintValidatorContext context() {
        return mock(ConstraintValidatorContext.class);
    }

    private Min min(final long value) {
        final Min annotation = mock(Min.class);
        when(annotation.value()).thenReturn(value);
        return annotation;
    }

}
