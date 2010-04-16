/**
 * palava - a java-php-bridge
 * Copyright (C) 2007-2010  CosmoCode GmbH
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
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
import de.cosmocode.palava.ipc.IpcModule;

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
            new IpcModule(),
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
