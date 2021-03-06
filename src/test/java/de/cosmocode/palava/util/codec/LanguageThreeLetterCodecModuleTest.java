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

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;

import de.cosmocode.commons.Bijection;
import de.cosmocode.commons.Codec;
import de.cosmocode.commons.converter.LanguageIsoConverter;
import de.cosmocode.commons.converter.LocaleLanguageIsoConverter;
import de.cosmocode.junit.UnitProvider;

/**
 * Tests {@link LanguageThreeLetterCodecModule}.
 *
 * @author Oliver Lorenz
 */
public class LanguageThreeLetterCodecModuleTest implements UnitProvider<LanguageThreeLetterCodecModule> {
    
    private static final Locale LOCALE = Locale.GERMAN;
    private static final String CODE = "deu";
    
    @Override
    public LanguageThreeLetterCodecModule unit() {
        return new LanguageThreeLetterCodecModule();
    }

    /**
     * Tests the module and tries to inject a {@link Codec} for Locale to String.
     */
    @Test
    public void injectCodecLocaleString() {
        final Injector injector = Guice.createInjector(unit());
        final Codec<Locale, String> codec = injector.getInstance(
            Key.get(new TypeLiteral<Codec<Locale, String>>() { }, Language.class));
        Assert.assertEquals(CODE, codec.encode(LOCALE));
    }

    /**
     * Tests the module and tries to inject a {@link Bijection} for Locale to String.
     */
    @Test
    public void injectBijectionLocaleString() {
        final Injector injector = Guice.createInjector(unit());
        final Bijection<Locale, String> languageFunc = injector.getInstance(
            Key.get(new TypeLiteral<Bijection<Locale, String>>() { }, Language.class));
        Assert.assertEquals(CODE, languageFunc.apply(LOCALE));
    }

    /**
     * Tests the module and tries to inject a {@link Function} for Locale to String.
     */
    @Test
    public void injectFunctionLocaleString() {
        final Injector injector = Guice.createInjector(unit());
        final Function<Locale, String> languageFunc = injector.getInstance(
            Key.get(new TypeLiteral<Function<Locale, String>>() { }, Language.class));
        Assert.assertEquals(CODE, languageFunc.apply(LOCALE));
    }

    /**
     * Tests the module and tries to inject a Codec for String to Locale.
     */
    @Test
    public void injectCodecStringLocale() {
        final Injector injector = Guice.createInjector(unit());
        final Codec<String, Locale> codec = injector.getInstance(
            Key.get(new TypeLiteral<Codec<String, Locale>>() { }, Language.class));
        Assert.assertEquals(LOCALE, codec.encode(CODE));
    }

    /**
     * Tests the module and tries to inject a Codec for String to Locale.
     */
    @Test
    public void injectBijectionStringLocale() {
        final Injector injector = Guice.createInjector(unit());
        final Bijection<String, Locale> languageFunc = injector.getInstance(
                Key.get(new TypeLiteral<Bijection<String, Locale>>() { }, Language.class));
        Assert.assertEquals(LOCALE, languageFunc.apply(CODE));
    }

    /**
     * Tests the module and tries to inject a Codec for String to Locale.
     */
    @Test
    public void injectFunctionStringLocale() {
        final Injector injector = Guice.createInjector(unit());
        final Function<String, Locale> languageFunc = injector.getInstance(
                Key.get(new TypeLiteral<Function<String, Locale>>() { }, Language.class));
        Assert.assertEquals(LOCALE, languageFunc.apply(CODE));
    }

    /**
     * Tests whether the Singleton annotation works as expected.
     */
    @Test
    public void testSingleton() {
        final Injector injector = Guice.createInjector(unit());
        final LanguageIsoConverter converter = injector.getInstance(LanguageIsoConverter.class);
        final LocaleLanguageIsoConverter localeConverter = injector.getInstance(LocaleLanguageIsoConverter.class);
        final Codec<Locale, String> codec = injector.getInstance(
                Key.get(new TypeLiteral<Codec<Locale, String>>() { }, Language.class));
        final Bijection<Locale, String> languageBijection = injector.getInstance(
                Key.get(new TypeLiteral<Bijection<Locale, String>>() { }, Language.class));
        final Function<Locale, String> languageFunc = injector.getInstance(
                Key.get(new TypeLiteral<Function<Locale, String>>() { }, Language.class));
        
        Assert.assertSame(converter, localeConverter);
        Assert.assertSame(converter, codec);
        Assert.assertSame(converter, languageBijection);
        Assert.assertSame(converter, languageFunc);
    }

    /**
     * Tests whether the Singleton annotation works as expected on the inversed implementations.
     */
    @Test
    public void testSingletonInversed() {
        final Injector injector = Guice.createInjector(unit());
        final Codec<String, Locale> codec = injector.getInstance(
                Key.get(new TypeLiteral<Codec<String, Locale>>() { }, Language.class));
        final Bijection<String, Locale> languageBijection = injector.getInstance(
                Key.get(new TypeLiteral<Bijection<String, Locale>>() { }, Language.class));
        final Function<String, Locale> languageFunc = injector.getInstance(
                Key.get(new TypeLiteral<Function<String, Locale>>() { }, Language.class));
        
        Assert.assertSame(codec, languageBijection);
        Assert.assertSame(codec, languageFunc);
    }

}
