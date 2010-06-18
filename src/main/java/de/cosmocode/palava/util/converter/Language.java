package de.cosmocode.palava.util.converter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.google.inject.BindingAnnotation;

import de.cosmocode.commons.Codec;

/**
 * Binding annotation for language based {@link Codec}s.
 *
 * @author Oliver Lorenz
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({
    ElementType.METHOD,
    ElementType.PARAMETER
})
@BindingAnnotation
public @interface Language {

}
