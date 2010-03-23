package de.cosmocode.palava.util.qa;

import java.util.Map;

import com.google.inject.Singleton;

import de.cosmocode.palava.ipc.IpcCall;
import de.cosmocode.palava.ipc.IpcCallFilter;
import de.cosmocode.palava.ipc.IpcCallFilterChain;
import de.cosmocode.palava.ipc.IpcCommand;
import de.cosmocode.palava.ipc.IpcCommandExecutionException;

/**
 * A filter which always fails with an {@link UnsupportedOperationException}.
 *
 * @author Willi Schoenborn
 */
@Singleton
final class DeprecationFilter implements IpcCallFilter {

    @Override
    public Map<String, Object> filter(IpcCall call, IpcCommand command, IpcCallFilterChain chain)
        throws IpcCommandExecutionException {
        
        throw new UnsupportedOperationException(String.format("%s is deprecated", command));
    }

}
