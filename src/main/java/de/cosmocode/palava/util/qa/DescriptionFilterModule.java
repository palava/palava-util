package de.cosmocode.palava.util.qa;

import com.google.common.base.Predicates;

import de.cosmocode.palava.ipc.Commands;
import de.cosmocode.palava.ipc.FilterModule;
import de.cosmocode.palava.ipc.IpcCommand.Description;

/**
 * Installs the {@link DescriptionFilter}.
 *
 * @author Willi Schoenborn
 */
public final class DescriptionFilterModule extends FilterModule {

    @Override
    protected void configure() {
        filter(Predicates.not(Commands.annotatedWith(Description.class))).through(DescriptionFilter.class);
    }

}
