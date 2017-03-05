/*
 * Copyright (c) 2017 Torsten Krause, Markenwerk GmbH
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.markenwerk.commons.collections.sources;

import java.util.Map;

import net.markenwerk.commons.iterators.ProtectedIterator;
import net.markenwerk.commons.iterators.ProtectingIterator;

/**
 * A {@link MapValueSource} is an {@link AbstractSource} that is backed by the
 * values of a {@link Map}.
 * 
 * @param <Value>
 *            The map value type.
 * @author Torsten Krause (tk at markenwerk dot net)
 * @since 1.0.0
 */
public final class MapValueSource<Value> extends AbstractSource<Value> {

	private final Map<?, ? extends Value> map;

	/**
	 * Creates a new {@link MapValueSource}.
	 * 
	 * @param map
	 *            The {@link Map} of payload values to be used.
	 * @throws IllegalArgumentException
	 *             If the given {@link Map} of payload values to be used is
	 *             {@literal null}.
	 */
	public MapValueSource(Map<?, ? extends Value> map) throws IllegalArgumentException {
		if (null == map) {
			throw new IllegalArgumentException("map is null");
		}
		this.map = map;
	}

	/**
	 * Returns the {@link Map} of payload values this {@link MapKeySource} has
	 * been created with.
	 * 
	 * @return The {@link Map} of payload values.
	 */
	public Map<?, ? extends Value> getMap() {
		return map;
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public ProtectedIterator<Value> iterator() {
		return new ProtectingIterator<Value>(map.values().iterator());
	}

}
