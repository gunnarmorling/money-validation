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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.money.MonetaryAmount;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.DecimalMax;
import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MonetaryAmountDecimalValidatorTest {

    @Mock
    private ConstraintValidator<DecimalMax, Number> validator;

    private MonetaryAmountDecimalValidator<DecimalMax> unit;

    @Before
    public void setUp() {
        unit = new MonetaryAmountDecimalValidator<>(validator);
    }

    @Test
    public void initializesValidator() {
        final DecimalMax annotation = decimalMax("0");

        unit.initialize(annotation);

        verify(validator).initialize(annotation);
    }

    @Test
    public void nullIsValid() {
        unit.initialize(decimalMax("0"));

        final boolean valid = unit.isValid(null, context());

        assertThat(valid, is(true));
        verify(validator, never()).isValid(any(), any());
    }

    @Test
    public void validatesOnBigDecimal() {
        final MonetaryAmount money = euro("2");
        final ConstraintValidatorContext context = context();

        unit.isValid(money, context);

        verify(validator).isValid(money.getNumber().numberValue(BigDecimal.class), context);
    }

    @Test
    public void validatesViaValidator() {
        when(validator.isValid(any(), any())).thenReturn(true);

        final boolean valid = unit.isValid(euro("2"), context());

        assertThat(valid, is(true));
    }

    @Test
    public void ignoresContext() {
        final ConstraintValidatorContext context = context();

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
