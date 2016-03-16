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

import com.google.common.collect.Iterables;
import org.javamoney.moneta.Money;
import org.junit.Test;

import javax.money.MonetaryAmount;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;

public class MonetaryAmountDecimalValidatorTest {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private static MonetaryAmount euro(final String value) {
        return Money.of(new BigDecimal(value), "EUR");
    }
    
    private void validate(final Product product, final MonetaryAmount invalid,
            final String message) {

        final Set<ConstraintViolation<Product>> violations = validator.validate(product);
        final ConstraintViolation<Product> violation = Iterables.getOnlyElement(violations);

        assertThat(violation.getInvalidValue(), is(sameInstance((Object) invalid)));
        assertThat(violation.getMessage(), is(message));
    }

    /**
     * If there are no constraint validators registered this test would fail with a
     * {@link javax.validation.UnexpectedTypeException}.
     */
    @Test
    public void validatorsAreRegistered() {
        validator.validate(new Product(null, null, null, null, null));
    }

    @Test
    public void violatingPositive() {
        final Product product = new Product(euro("0"), null, null, null, null);

        validate(product, product.getPrice(), "must be greater than 0");
    }
    
    @Test
    public void violatingPositiveOrZero() {
        final Product product = new Product(null, euro("-1"), null, null, null);

        validate(product, product.getDiscountedPrice(), "must be greater than or equal to 0");
    }
    
    @Test
    public void violatingNegativeOrZero() {
        final Product product = new Product(null, null, euro("2"), null, null);

        validate(product, product.getDiscount(), "must be less than or equal to 0");
    }

    @Test
    public void violatingNegative() {
        final Product product = new Product(null, null, null, euro("1"), null);

        validate(product, product.getPriceReduction(), "must be less than 0");
    }
    
    @Test
    public void violatingZero() {
        final Product product = new Product(null, null, null, null, euro("1"));

        validate(product, product.getTax(), "must be 0");
    }

    @Test
    public void noViolationsOnValidValue() {
        final Product product = new Product(euro("2"), euro("0"), euro("0"), euro("-2"), euro("0"));

        final Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertThat(violations, is(empty()));
    }

}
