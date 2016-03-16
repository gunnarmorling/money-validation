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
import javax.validation.constraints.Max;
import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MonetaryAmountMaxValidatorTest {

    private final ConstraintValidator<Max, MonetaryAmount> unit = new MonetaryAmountMaxValidator();

    @Test
    public void nullIsValid() {
        unit.initialize(max(0));

        final boolean valid = unit.isValid(null, context());

        assertThat(valid, is(true));
    }

    @Test
    public void validIfLess() {
        unit.initialize(max(0));
        final boolean valid = unit.isValid(euro("-1"), context());

        assertThat(valid, is(true));
    }

    @Test
    public void invalidIfGreater() {
        unit.initialize(max(0));
        final boolean valid = unit.isValid(euro("1"), context());

        assertThat(valid, is(false));
    }

    @Test
    public void validIfInclude() {
        unit.initialize(max(0));
        final boolean valid = unit.isValid(euro("0"), context());

        assertThat(valid, is(true));
    }

    private MonetaryAmount euro(final String value) {
        return Money.of(new BigDecimal(value), "EUR");
    }

    private ConstraintValidatorContext context() {
        return mock(ConstraintValidatorContext.class);
    }

    private Max max(final long value) {
        final Max annotation = mock(Max.class);
        when(annotation.value()).thenReturn(value);
        return annotation;
    }

}
