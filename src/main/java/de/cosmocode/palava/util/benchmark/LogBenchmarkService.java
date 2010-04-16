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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.inject.name.Named;

import de.cosmocode.palava.ipc.IpcCall;
import de.cosmocode.palava.ipc.IpcCommand;

/**
 * Default implementation of the {@link BenchmarkService} which logs all
 * command executions using its {@link Logger}.
 *
 * @author Willi Schoenborn
 */
final class LogBenchmarkService implements BenchmarkService {

    private Logger log = LoggerFactory.getLogger(LogBenchmarkService.class);
    
    public void setLog(@Named(LogBenchmarkServiceConfig.CATEGORY) Logger log) {
        this.log = Preconditions.checkNotNull(log, "Log");
    }
    
    @Override
    public void log(Class<? extends IpcCommand> command, long time, TimeUnit timeUnit, IpcCall call,
        Map<String, Object> result) {

        log.debug("{} took {} {}s to process {}", new Object[] {
            command, time, timeUnit.name().toLowerCase(), call
        });
    }
    
}
