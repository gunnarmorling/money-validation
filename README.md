# Money Validation

Validation support for `javax.money.MonetaryAmount` using the `javax.validation` constraints `DecimalMin` and `DecimalMax`.

## Usage

Use `DecimalMin` and `DecimalMax` on your fields of type `javax.money.MonetaryAmount`. The validators are automatically 
registered by your validation framework using SPI.

    class Model {
        @DecimalMin("0")
        public MonetaryAmount amount1;
    
        @DecimalMax(value = "0", inclusive = false)
        public MonetaryAmount amount2;
    }

## License

Copyright [2015] Zalando SE

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.