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


import javax.money.MonetaryAmount;

final class Product {

    @Positive
    private final MonetaryAmount price;
    
    @PositiveOrZero
    private final MonetaryAmount discountedPrice;

    @NegativeOrZero
    private final MonetaryAmount discount;
    
    @Negative
    private final MonetaryAmount priceReduction;
    
    @Zero
    private final MonetaryAmount tax;

    public Product(MonetaryAmount price, MonetaryAmount discountedPrice, MonetaryAmount discount, MonetaryAmount priceReduction, MonetaryAmount tax) {
        this.price = price;
        this.discountedPrice = discountedPrice;
        this.discount = discount;
        this.priceReduction = priceReduction;
        this.tax = tax;
    }

    public MonetaryAmount getPrice() {
        return price;
    }

    public MonetaryAmount getDiscountedPrice() {
        return discountedPrice;
    }

    public MonetaryAmount getDiscount() {
        return discount;
    }

    public MonetaryAmount getPriceReduction() {
        return priceReduction;
    }

    public MonetaryAmount getTax() {
        return tax;
    }

}
