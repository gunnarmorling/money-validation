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
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.DecimalMax;
import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class MonetaryAmountDecimalMaxValidatorTest {

    private final MonetaryAmountDecimalMaxValidator unit = new MonetaryAmountDecimalMaxValidator();

    @Test
    public void nullIsValid() {
        unit.initialize(decimalMax("0"));

        final boolean valid = unit.isValid(null, context());

        assertThat(valid, is(true));
    }

    @Test
    public void validIfLess() {
        unit.initialize(decimalMax("0", true));
        final boolean valid = unit.isValid(euro("-1"), context());

        assertThat(valid, is(true));
    }

    @Test
    public void invalidIfGreater() {
        unit.initialize(decimalMax("0", true));
        final boolean valid = unit.isValid(euro("1"), context());

        assertThat(valid, is(false));
    }

    @Test
    public void validIfInclude() {
        unit.initialize(decimalMax("0", true));
        final boolean valid = unit.isValid(euro("0"), context());

        assertThat(valid, is(true));
    }

    @Test
    public void invalidIfNotInclude() {
        unit.initialize(decimalMax("0", false));
        final boolean valid = unit.isValid(euro("0"), context());

        assertThat(valid, is(false));
    }

    @Test
    public void validIfLessAndNotIncluded() {
        unit.initialize(decimalMax("0", false));
        final boolean valid = unit.isValid(euro("-1"), context());

        assertThat(valid, is(true));
    }

    @Test
    public void ignoresContext() {
        final ConstraintValidatorContext context = context();

        unit.initialize(decimalMax("0"));
        unit.isValid(euro("2"), context);

        verifyNoMoreInteractions(context);
    }

    private MonetaryAmount euro(final String value) {
        return Money.of(new BigDecimal(value), "EUR");
    }

    private ConstraintValidatorContext context() {
        return mock(ConstraintValidatorContext.class);
    }

    private DecimalMax decimalMax(final String value) {
        return decimalMax(value, true);
    }

    private DecimalMax decimalMax(final String value, final boolean inclusive) {
        DecimalMax annotation = mock(DecimalMax.class);
        when(annotation.value()).thenReturn(value);
        when(annotation.inclusive()).thenReturn(inclusive);
        return annotation;
    }

}
