package de.cosmocode.palava.util.converter;

import com.google.inject.Binder;
import com.google.inject.Module;

import de.cosmocode.commons.converter.CountryIsoConverter;
import de.cosmocode.commons.converter.LanguageIsoConverter;
import de.cosmocode.commons.converter.LocaleCountryIsoConverter;
import de.cosmocode.commons.converter.LocaleLanguageIsoConverter;

/**
 * Binds the Locale based implementations of
 * {@link CountryIsoConverter} and {@link LanguageIsoConverter}.
 *
 * @author Oliver Lorenz
 */
public final class LocaleIsoConverterModule implements Module {
    
    @Override
    public void configure(Binder binder) {
        binder.bind(CountryIsoConverter.class).to(LocaleCountryIsoConverter.class);
        binder.bind(LanguageIsoConverter.class).to(LocaleLanguageIsoConverter.class);
    }

}
