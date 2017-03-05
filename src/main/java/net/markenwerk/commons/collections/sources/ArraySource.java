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

import java.security.CodeSource;

import net.markenwerk.commons.iterators.AbstractIndexedIterator;
import net.markenwerk.commons.iterators.ProtectedBidirectionalIterator;

/**
 * An {@link ArraySource} is an {@link AbstractIndexedSource} that is backed by
 * an array.
 * 
 * @param <Payload>
 *            The payload type.
 * @author Torsten Krause (tk at markenwerk dot net)
 * @since 1.0.0
 */
public final class ArraySource<Payload> extends AbstractIndexedSource<Payload> {

	private Payload[] array;

	/**
	 * Creates a new {@link CodeSource}.
	 * 
	 * @param array
	 *            The array of payload values to be used.
	 * 
	 * @throws IllegalArgumentException
	 *             If the given array of payload values is {@literal null}.
	 */
	public ArraySource(Payload... array) throws IllegalArgumentException {
		if (null == array) {
			throw new IllegalArgumentException("The given array is null");
		}
		this.array = array;
	}

	/**
	 * Returns the array of payload values this {@link ArraySource} has been
	 * created with.
	 * 
	 * @return The array of payload values.
	 */
	public final Payload[] getArray() {
		return array;
	}

	@Override
	public int size() {
		return array.length;
	}

	@Override
	protected Payload doGet(int index) {
		return array[index];
	}

	@Override
	protected ProtectedBidirectionalIterator<Payload> doIterator(boolean reverse) {
		if (reverse) {

			return new AbstractIndexedIterator<Payload>(0, size()) {

				@Override
				protected Payload get(int index) {
					return array[size() - index - 1];
				}
			};

		} else {

			return new AbstractIndexedIterator<Payload>(0, size()) {

				@Override
				protected Payload get(int index) {
					return array[index];
				}
			};

		}
	}

}
