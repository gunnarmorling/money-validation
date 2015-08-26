package org.zalando.money.validation;

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
