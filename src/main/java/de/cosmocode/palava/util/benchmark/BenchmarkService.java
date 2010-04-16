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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */

package de.cosmocode.palava.util.benchmark;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import de.cosmocode.palava.ipc.IpcCall;
import de.cosmocode.palava.ipc.IpcCommand;

/**
 * A service which logs command execution time.
 *
 * @author Willi Schoenborn
 */
public interface BenchmarkService {

    /**
     * Logs the given results.
     * 
     * @param command the command being called
     * @param time the time spent executing
     * @param timeUnit the unit of time
     * @param call the incoming call
     * @param result the unmodifiable result
     */
    void log(Class<? extends IpcCommand> command, long time, TimeUnit timeUnit, 
        IpcCall call, Map<String, Object> result);
    
}
