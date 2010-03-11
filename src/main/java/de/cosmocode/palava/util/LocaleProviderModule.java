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

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.name.Named;

import de.cosmocode.commons.Locales;
import de.cosmocode.palava.ipc.Context;
import de.cosmocode.palava.ipc.IpcSessionScoped;

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
    @IpcSessionScoped
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
