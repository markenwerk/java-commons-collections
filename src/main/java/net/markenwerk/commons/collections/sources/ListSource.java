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
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import net.markenwerk.commons.iterators.AbstractProtectedBidirectionalIterator;
import net.markenwerk.commons.iterators.ProtectedBidirectionalIterator;

/**
 * An {@link ListSource} is an {@link AbstractIndexedSource} that is backed by a
 * {@link List}.
 * 
 * @param <Payload>
 *            The payload type.
 * @author Torsten Krause (tk at markenwerk dot net)
 * @since 1.0.0
 */
public final class ListSource<Payload> extends AbstractIndexedSource<Payload> {

	private List<Payload> list;

	/**
	 * Creates a new {@link CodeSource}.
	 * 
	 * @param list
	 *            The {@link List} of payload values to be used.
	 * 
	 * @throws IllegalArgumentException
	 *             If the given {@link List} of payload values is
	 *             {@literal null}.
	 */
	public ListSource(List<Payload> list) throws IllegalArgumentException {
		if (null == list) {
			throw new IllegalArgumentException("The given list is null");
		}
		this.list = list;
	}

	/**
	 * Returns the {@link List} of payload values this {@link ListSource} has
	 * been created with.
	 * 
	 * @return The {@link List} of payload values.
	 */
	public final List<Payload> getList() {
		return list;
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	protected Payload doGet(int index) {
		return list.get(index);
	}

	@Override
	protected ProtectedBidirectionalIterator<Payload> doIterator(boolean reverse) {
		if (reverse) {

			return new AbstractProtectedBidirectionalIterator<Payload>() {

				private final ListIterator<Payload> iterator = list.listIterator(list.size());

				@Override
				public boolean hasNext() {
					return iterator.hasPrevious();
				}

				@Override
				public Payload next() {
					return iterator.previous();
				}

				@Override
				public boolean hasPrevious() {
					return iterator.hasNext();
				}

				@Override
				public Payload previous() throws NoSuchElementException {
					return iterator.next();
				}

			};

		} else {

			return new AbstractProtectedBidirectionalIterator<Payload>() {

				private final ListIterator<Payload> iterator = list.listIterator();

				@Override
				public boolean hasNext() {
					return iterator.hasNext();
				}

				@Override
				public Payload next() {
					return iterator.next();
				}

				@Override
				public boolean hasPrevious() {
					return iterator.hasPrevious();
				}

				@Override
				public Payload previous() throws NoSuchElementException {
					return iterator.previous();
				}

			};

		}
	}

}
