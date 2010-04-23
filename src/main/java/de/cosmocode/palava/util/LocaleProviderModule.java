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

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.name.Named;

import de.cosmocode.commons.Locales;
import de.cosmocode.palava.ipc.Context;

/**
 * Provides the current locale. 
 *
 * @author Willi Schoenborn
 */
public final class LocaleProviderModule implements Module {

    private static final Logger LOG = LoggerFactory.getLogger(LocaleProviderModule.class);

    @Override
    public void configure(Binder binder) {
        
    }

    /**
     * Provide the current user's locale.
     *
     * @param value the current locale value
     * @param defaultLocale the configured defaultLocale
     * @return the user's locale
     */
    @Provides
    Locale getLocale(@Named(Context.LOCALE) String value, @Named(UtilityConfig.LOCALE_DEFAULT) Locale defaultLocale) {
        if (StringUtils.isBlank(value)) {
            LOG.trace("No locale found in session, using {}", defaultLocale);
            return defaultLocale;
        } else {
            LOG.trace("Found locale {} in session", value);
            return Locales.parse(value);
        }
    }
    
}
