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

import de.cosmocode.palava.ipc.Commands;
import de.cosmocode.palava.ipc.FilterModule;
import de.cosmocode.palava.ipc.IpcCommand;

/**
 * Configures the {@link BenchmarkFilter} to run around all {@link IpcCommand}s.
 * It's strongly recommended to configure this service as your first to ensure it
 * benchmarks all your configured filters.
 *
 * @author Willi Schoenborn
 */
public final class BenchmarkFilterModule extends FilterModule {

    @Override
    protected void configure() {
        filter(Commands.any()).through(BenchmarkFilter.class);
    }

}
