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

package de.cosmocode.palava.util.converter;

import java.util.Locale;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;

import de.cosmocode.commons.Codec;
import de.cosmocode.commons.converter.CountryIsoConverter;
import de.cosmocode.commons.converter.LanguageIsoConverter;
import de.cosmocode.commons.converter.LocaleCountryIsoConverter;
import de.cosmocode.commons.converter.LocaleLanguageIsoConverter;

/**
 * <p> Binds the Locale based implementations of
 * {@link CountryIsoConverter} and {@link LanguageIsoConverter}.
 * </p>
 * <p> Also binds {@code Codec<Locale, String>} for both language and country
 * to the same instances.
 * The language codec converts to ISO 639-2 language code.
 * The country codec converts to ISO 3166 alpha-3 country code.
 * </p>
 * <p> Usage (in a class): <br />
 * <code>
 *   {@code @Inject @Language private Codec<Locale, String>} languageCodec; <br />
 *   {@code @Inject @Country private Codec<Locale, String>} countryCodec; <br />
 *   {@code @Inject private LanguageIsoConverter} languageConverter; <br />
 *   {@code @Inject private CountryIsoConverter} countryConverter; <br />
 * </code>
 * </p>
 *
 * @author Oliver Lorenz
 */
public final class LocaleIsoConverterModule implements Module {
    
    /**
     * Look at class description for documentation: {@link LocaleIsoConverterModule}.
     * @see LocaleIsoConverterModule
     */
    public LocaleIsoConverterModule() {
        // constructor only for documentation
    }
    
    @Override
    public void configure(Binder binder) {
        // bind the instances in Singleton scope
        binder.bind(LocaleCountryIsoConverter.class).in(Singleton.class);
        binder.bind(LocaleLanguageIsoConverter.class).in(Singleton.class);
        
        // bind the interfaces
        binder.bind(CountryIsoConverter.class).to(LocaleCountryIsoConverter.class);
        binder.bind(LanguageIsoConverter.class).to(LocaleLanguageIsoConverter.class);

        // bind the codecs (annotated)
        binder.bind(new TypeLiteral<Codec<Locale, String>>() { }).annotatedWith(Language.class).
            to(LocaleLanguageIsoConverter.class);
        binder.bind(new TypeLiteral<Codec<Locale, String>>() { }).annotatedWith(Country.class).
            to(LocaleCountryIsoConverter.class);
    }

}
