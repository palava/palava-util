package de.cosmocode.palava.util.qa;

import de.cosmocode.palava.ipc.Commands;
import de.cosmocode.palava.ipc.FilterModule;

/**
 * Configures the {@link DeprecationFilter} to run before all commands
 * annotated with {@link Deprecated}.
 *
 * @author Willi Schoenborn
 */
public final class DeprecationModule extends FilterModule {

    @Override
    protected void configure() {
        filter(Commands.annotatedWith(Deprecated.class)).through(DeprecationFilter.class);
    }

}
