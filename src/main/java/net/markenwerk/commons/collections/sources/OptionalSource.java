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

import net.markenwerk.commons.datastructures.Optional;
import net.markenwerk.commons.iterators.OptionalIterator;
import net.markenwerk.commons.iterators.ProtectedBidirectionalIterator;

/**
 * A {@link OptionalSource} is an {@link AbstractSource} that is backed by an
 * {@link Optional}.
 * 
 * @param <Payload>
 *           The payload type.
 * @author Torsten Krause (tk at markenwerk dot net)
 * @since 1.0.0
 */
public final class OptionalSource<Payload> extends AbstractSource<Payload> {

	private final Optional<Payload> optional;

	/**
	 * Creates a new {@link OptionalSource}.
	 * 
	 * @param optional
	 *           The {@link Optional} to be used.
	 * @throws IllegalArgumentException
	 *            If the given optional is {@literal null}.
	 */
	public OptionalSource(Optional<Payload> optional) throws IllegalArgumentException {
		if (null == optional) {
			throw new IllegalArgumentException("The given optional is null");
		}
		this.optional = optional;
	}

	/**
	 * Returns the {@link Optional} this {@link OptionalSource} has been created
	 * with.
	 * 
	 * @return The payload {@link Object}.
	 */
	public Optional<Payload> getOptional() {
		return optional;
	}

	@Override
	public int size() {
		return optional.hasValue() ? 1 : 0;
	}

	@Override
	protected Payload doGetFirst() {
		return optional.getValue();
	}

	@Override
	public ProtectedBidirectionalIterator<Payload> iterator() {
		return new OptionalIterator<Payload>(optional);
	}

}
