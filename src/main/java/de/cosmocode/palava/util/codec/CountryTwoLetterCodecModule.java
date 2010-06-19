/**
 * Copyright 2010 CosmoCode GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.cosmocode.palava.util.codec;

import java.util.Locale;

import com.google.common.base.Function;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import de.cosmocode.commons.Bijection;
import de.cosmocode.commons.Codec;

/**
 * <p> Binds a {@code Codec<Locale, String>} for country
 * that converts to ISO 3166 alpha-2 country code.
 * </p>
 * <p> Also binds the same implementation to its interfaces (Function and Bijection)
 * and their inversed parts.
 * </p>
 * <p> <strong> Attention: </strong> This module is incompatible with {@link CountryThreeLetterCodecModule}.
 * </p>
 * <p> Usage (in a class): <br />
 * <code>
 *   {@code @Inject @Country private Codec<Locale, String>} countryCode; <br />
 *   {@code @Inject @Country private Bijection<Locale, String>} countryCode; <br />
 *   {@code @Inject @Country private Function<Locale, String>} countryCode; <br />
 *   <br />
 *   {@code @Inject @Country private Codec<String, Locale>} countryCode; <br />
 *   {@code @Inject @Country private Bijection<String, Locale>} countryCode; <br />
 *   {@code @Inject @Country private Function<String, Locale>} countryCode; <br />
 * </code>
 * </p>
 *
 * @since 1.1
 * @author Oliver Lorenz
 */
public class CountryTwoLetterCodecModule implements Module {

    /**
     * Look at the class JavaDoc for documentation: {@link CountryTwoLetterCodecModule}.
     */
    public CountryTwoLetterCodecModule() {
        // constructor only for JavaDoc
    }
    
    @Override
    public void configure(Binder binder) {
        // everything configured in @Provides methods
    }
    
    /**
     * Provides a Codec that encodes Locales into their two-letter country code
     * and decodes two-letter country codes into Locales.
     * @return a Locale <-> ISO 3661 alpha-2 codec
     */
    @Provides
    @Singleton
    @Country
    Codec<Locale, String> provideIso3166Alpha2Codec() {
        return new Codec<Locale, String>() {
            @Override
            public String encode(Locale input) {
                return input.getCountry();
            }
            
            @Override
            public Locale decode(String input) {
                return new Locale("", input);
            }
        };
    }
    
    /**
     * Provides a Bijection that converts Locales into their two-letter country code
     * and inversely converts two-letter country codes into Locales.
     * @param codec the underlying base implementation
     * @return a Locale <-> ISO 3661 alpha-2 bijection
     */
    @Provides
    @Singleton
    @Country
    Bijection<Locale, String> provideCountryBijection(@Country Codec<Locale, String> codec) {
        return codec;
    }
    
    /**
     * Provides a Function that converts Locales into their two-letter country code.
     * @param codec the underlying base implementation
     * @return a Locale -> ISO 3661 alpha-2 function
     */
    @Provides
    @Singleton
    @Country
    Function<Locale, String> provideCountryFunction(@Country Codec<Locale, String> codec) {
        return codec;
    }
    
    /**
     * Provides a Codec that encodes two-letter country codes into Locales 
     * and decodes Locales into their two-letter country code.
     * @param codec the underlying base implementation
     * @return a ISO 3661 alpha-2 <-> Locale codec
     */
    @Provides
    @Singleton
    @Country
    Codec<String, Locale> provideInverse(@Country Codec<Locale, String> codec) {
        return codec.inverse();
    }
    
    /**
     * Provides a Bijection that converts two-letter country codes into Locales
     * and inversely converts Locales into their two-letter country code.
     * @param codec the underlying base implementation
     * @return a ISO 3661 alpha-2 <-> Locale bijection
     */
    @Provides
    @Singleton
    @Country
    Bijection<String, Locale> provideInverseBijection(@Country Codec<String, Locale> codec) {
        return codec;
    }
    
    /**
     * Provides a Function that converts two-letter country codes into Locales.
     * @param codec the underlying base implementation
     * @return a ISO 3661 alpha-2 -> Locale function
     */
    @Provides
    @Singleton
    @Country
    Function<String, Locale> provideInverseFunction(@Country Codec<String, Locale> codec) {
        return codec;
    }

}
