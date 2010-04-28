package de.cosmocode.palava.util.qa;

import com.google.inject.Binder;
import com.google.inject.Module;

/**
 * A module which installs all quality assurance modules.
 *
 * @author Willi Schoenborn
 */
public final class QualityAssuranceModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.install(new DeprecationFilterModule());
        binder.install(new DescriptionFilterModule());
    }

}
