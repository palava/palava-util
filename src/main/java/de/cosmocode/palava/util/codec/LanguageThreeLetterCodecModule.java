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
import de.cosmocode.commons.converter.LanguageIsoConverter;
import de.cosmocode.commons.converter.LocaleLanguageIsoConverter;

/**
 * <p> Binds a {@code Codec<Locale, String>} for language
 * that converts to ISO 639-2 language code (three letter language code).
 * </p>
 * <p> Also binds the same implementation to its interfaces (Function and Bijection)
 * and their inversed parts.
 * </p>
 * <p> <strong> Attention: </strong> This module is incompatible with {@link LanguageTwoLetterCodecModule}.
 * </p>
 * <p> Usage (in a class): <br />
 * <code>
 *   {@code @Inject @Language private Codec<Locale, String>} languageCodec; <br />
 *   {@code @Inject @Language private Bijection<Locale, String>} languageCodec; <br />
 *   {@code @Inject @Language private Function<Locale, String>} languageCodec; <br />
 *   <br />
 *   {@code @Inject @Language private Codec<String, Locale>} languageCodec; <br />
 *   {@code @Inject @Language private Bijection<String, Locale>} languageCodec; <br />
 *   {@code @Inject @Language private Function<String, Locale>} languageCodec; <br />
 *   <br />
 *   {@code @Inject private LanguageIsoConverter} languageConverter;
 * </code>
 * </p>
 *
 * @since 1.1
 * @author Oliver Lorenz
 */
public class LanguageThreeLetterCodecModule implements Module {
    
    /**
     * Look at the class JavaDoc for documentation: {@link LanguageThreeLetterCodecModule}.
     */
    public LanguageThreeLetterCodecModule() {
        // constructor only for JavaDoc
    }
    
    @Override
    public void configure(Binder binder) {
        // two steps to allow usage of the instance for the provides methods too
        binder.bind(LocaleLanguageIsoConverter.class).in(Singleton.class);
        binder.bind(LanguageIsoConverter.class).to(LocaleLanguageIsoConverter.class);
    }
    
    /**
     * Provides a Codec that encodes Locales into their three-letter language code
     * and decodes three-letter language codes into Locales.
     * @param converter the base implementation
     * @return a Locale <-> ISO 639-2 codec
     */
    @Provides
    @Singleton
    @Language
    Codec<Locale, String> provideLanguageCodec(LocaleLanguageIsoConverter converter) {
        return converter;
    }
    
    /**
     * Provides a Bijection that converts Locales into their three-letter language code
     * and inversely converts three-letter language codes into Locales.
     * @param codec the underlying base implementation
     * @return a Locale <-> ISO 639-2 bijection
     */
    @Provides
    @Singleton
    @Language
    Bijection<Locale, String> provideLanguageBijection(@Language Codec<Locale, String> codec) {
        return codec;
    }
    
    /**
     * Provides a Function that converts Locales into their three-letter language code.
     * @param codec the underlying base implementation
     * @return a Locale -> ISO 639-2 function
     */
    @Provides
    @Singleton
    @Language
    Function<Locale, String> provideLanguageFunction(@Language Codec<Locale, String> codec) {
        return codec;
    }
    
    /**
     * Provides a Codec that encodes three-letter language codes into Locales 
     * and decodes Locales into their three-letter language code.
     * @param codec the underlying base implementation
     * @return a ISO 639-2 <-> Locale codec
     */
    @Provides
    @Singleton
    @Language
    Codec<String, Locale> provideInverse(@Language Codec<Locale, String> codec) {
        return codec.inverse();
    }
    
    /**
     * Provides a Bijection that converts three-letter language codes into Locales 
     * and inversely converts Locales into their three-letter language code.
     * @param codec the underlying base implementation
     * @return a ISO 639-2 <-> Locale bijection
     */
    @Provides
    @Singleton
    @Language
    Bijection<String, Locale> provideInverseBijection(@Language Codec<String, Locale> codec) {
        return codec;
    }
    
    /**
     * Provides a Function that converts three-letter language codes into Locales.
     * @param codec the underlying base implementation
     * @return a ISO 639-2 -> Locale function
     */
    @Provides
    @Singleton
    @Language
    Function<String, Locale> provideInverseFunction(@Language Codec<String, Locale> codec) {
        return codec;
    }

}
