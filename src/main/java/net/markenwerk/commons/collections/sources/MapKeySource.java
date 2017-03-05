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
 * A {@link MapKeySource} is an {@link AbstractSource} that is backed by the
 * keys of a {@link Map}.
 * 
 * @param <Key>
 *            The map key type.
 * @author Torsten Krause (tk at markenwerk dot net)
 * @since 1.0.0
 */
public final class MapKeySource<Key> extends AbstractSource<Key> {

	private final Map<? extends Key, ?> map;

	/**
	 * Creates a new {@link MapKeySource}.
	 * 
	 * @param map
	 *            The {@link Map} of payload keys to be used.
	 * @throws IllegalArgumentException
	 *             If the given {@link Map} of payload keys is {@literal null}.
	 */
	public MapKeySource(Map<? extends Key, ?> map) throws IllegalArgumentException {
		if (null == map) {
			throw new IllegalArgumentException("The given map of payload keys is null");
		}
		this.map = map;
	}

	/**
	 * Returns the {@link Map} of payload keys this {@link MapKeySource} has
	 * been created with.
	 * 
	 * @return The {@link Map} of payload keys.
	 */
	public Map<? extends Key, ?> getMap() {
		return map;
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public ProtectedIterator<Key> iterator() {
		return new ProtectingIterator<Key>(map.keySet().iterator());
	}

}
