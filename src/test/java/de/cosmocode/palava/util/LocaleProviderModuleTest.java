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

package de.cosmocode.palava.util;

import java.util.Locale;

import org.junit.Test;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Module;
import com.google.inject.name.Names;

import de.cosmocode.junit.UnitProvider;
import de.cosmocode.palava.core.DefaultRegistryModule;
import de.cosmocode.palava.ipc.Context;
import de.cosmocode.palava.ipc.IpcScopeModule;

/**
 * Tests {@link LocaleProviderModule}.
 *
 * @author Willi Schoenborn
 */
public final class LocaleProviderModuleTest implements UnitProvider<LocaleProviderModule> {

    @Override
    public LocaleProviderModule unit() {
        return new LocaleProviderModule();
    }
    
    /**
     * Tests {@link Guice#createInjector(Module...)} with a custom locale binding.
     */
    @Test
    public void createInjector() {
        Guice.createInjector(
            unit(),
            new IpcScopeModule(),
            new DefaultRegistryModule(),
            new Module() {
                
                @Override
                public void configure(Binder binder) {
                    binder.bind(Locale.class).annotatedWith(
                        Names.named(UtilityConfig.LOCALE_DEFAULT)).toInstance(Locale.ENGLISH);
                    binder.bind(String.class).annotatedWith(Names.named(Context.LOCALE)).toInstance("de");
                }
                
            }
        );
    }

}
