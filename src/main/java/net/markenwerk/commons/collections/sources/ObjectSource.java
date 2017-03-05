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

import java.util.NoSuchElementException;

import net.markenwerk.commons.iterators.ObjectIterator;
import net.markenwerk.commons.iterators.ProtectedIterator;

/**
 * A {@link ObjectSource} is an {@link AbstractSource} that is backed by a
 * single payload value.
 * 
 * @param <Payload>
 *           The payload type.
 * @author Torsten Krause (tk at markenwerk dot net)
 * @since 1.0.0
 */
public final class ObjectSource<Payload> extends AbstractSource<Payload> {

	private final Payload value;

	private final boolean ignoreNull;

	/**
	 * Creates a new {@link ObjectSource} for the given payload object.
	 * 
	 * @param value
	 *           The payload value to be used.
	 */
	public ObjectSource(Payload value) {
		this(value, false);
	}

	/**
	 * Creates a new {@link ObjectSource} for the given payload object.
	 * 
	 * @param value
	 *           The payload value to be used.
	 * @param ignoreNull
	 *           Whether to ignore {@literal null} values.
	 */
	public ObjectSource(Payload value, boolean ignoreNull) {
		this.value = value;
		this.ignoreNull = ignoreNull;
	}

	/**
	 * Returns whether this {@link ObjectSource} has a value.
	 * 
	 * @return Whether this {@link ObjectSource} has a value.
	 */
	public boolean hasValue() {
		return ignoreNull ? null != value : true;

	}

	/**
	 * Returns the payload value this {@link ObjectSource} has been created with.
	 * 
	 * @return The payload Object.
	 * 
	 * @throws NoSuchElementException
	 *            If this {@link ObjectSource} has no value.
	 */
	public Payload getValue() throws NoSuchElementException {
		if (hasValue()) {
			return value;
		} else {
			throw new NoSuchElementException("This object source has no value");
		}

	}

	@Override
	public int size() {
		return hasValue() ? 1 : 0;
	}

	@Override
	protected Payload doGetFirst() {
		return value;
	}

	@Override
	public ProtectedIterator<Payload> iterator() {
		return new ObjectIterator<Payload>(value, ignoreNull);
	}

}
