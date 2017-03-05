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

import java.util.Iterator;
import java.util.NoSuchElementException;

import net.markenwerk.commons.datastructures.Optional;
import net.markenwerk.commons.exceptions.ConversionException;
import net.markenwerk.commons.interfaces.Converter;
import net.markenwerk.commons.interfaces.Predicate;
import net.markenwerk.commons.iterators.ProtectedBidirectionalIterator;

/**
 * An {@link AbstractIndexedSource} is an abstract base implementation of a
 * {@link IndexedSource}.
 * 
 * @param <Payload>
 *            The payload type.
 * @author Torsten Krause (tk at markenwerk dot net)
 * @since 1.0.0
 */
public abstract class AbstractIndexedSource<Payload> extends AbstractSource<Payload> implements IndexedSource<Payload> {

	/**
	 * Creates a new {@link AbstractIndexedSource}.
	 */
	public AbstractIndexedSource() {
	}

	@Override
	public final Payload get(int index) throws IndexOutOfBoundsException {
		if (index < 0) {
			throw new IndexOutOfBoundsException("The given index is negative: " + index);
		} else if (index >= size()) {
			throw new IndexOutOfBoundsException("The given index is loo large for size " + size() + ": " + index);
		}
		return doGet(index);
	}

	/**
	 * Returns a payload value at the given index.
	 * 
	 * @param index
	 *            The index to be used, which is guaranteed to be not negative
	 *            and not larger then or equal to the
	 *            {@link AbstractIndexedSource#size() size} of this
	 *            {@link AbstractIndexedSource}.
	 * @return The payload value.
	 */
	protected abstract Payload doGet(int index);

	@Override
	public final boolean isFirst(Payload payload) throws NoSuchElementException {
		Payload first = getFirst();
		return null == payload ? null == first : payload.equals(first);
	}

	@Override
	protected final Payload doGetFirst() {
		return get(0);
	}

	@Override
	public final boolean isLast(Payload payload) throws NoSuchElementException {
		Payload last = getLast();
		return null == payload ? null == last : payload.equals(last);
	}

	@Override
	public final Payload getLast() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException("This IndexedSource is empty");
		}
		return get(size() - 1);
	}

	@Override
	public final Optional<Payload> getLast(Payload reference) {
		return getLastMatch(createPredicate(reference));
	}

	@Override
	public final Optional<Payload> getLastMatch(Predicate<? super Payload> predicate) throws IllegalArgumentException {
		if (null == predicate) {
			throw new IllegalArgumentException("The given predicate is null");
		}
		Iterator<Payload> iterator = iterator(true);
		while (iterator.hasNext()) {
			Payload payload = iterator.next();
			if (predicate.test(payload)) {
				return new Optional<Payload>(payload);
			}
		}
		return new Optional<Payload>();
	}

	@Override
	public final Optional<Integer> firstIndexOf(Payload reference) throws NoSuchElementException {
		return firstIndexOfMatch(createPredicate(reference));
	}

	@Override
	public final Optional<Integer> firstIndexOfMatch(Predicate<? super Payload> predicate)
			throws IllegalArgumentException, NoSuchElementException {
		if (null == predicate) {
			throw new IllegalArgumentException("The given predicate is null");
		}
		return firstIndexOf(iterator(), predicate);
	}

	@Override
	public final Optional<Integer> lastIndexOf(Payload reference) throws NoSuchElementException {
		return lastIndexOfMatch(createPredicate(reference));
	}

	@Override
	public final Optional<Integer> lastIndexOfMatch(Predicate<? super Payload> predicate)
			throws IllegalArgumentException, NoSuchElementException {
		if (null == predicate) {
			throw new IllegalArgumentException("The given predicate is null");
		}
		return firstIndexOf(iterator(true), predicate).convert(new Converter<Integer, Integer>() {

			@Override
			public Integer convert(Integer index) throws ConversionException {
				return size() - index - 1;
			}

		});
	}

	@Override
	public final ProtectedBidirectionalIterator<Payload> iterator() {
		return doIterator(false);
	}

	@Override
	public final ProtectedBidirectionalIterator<Payload> iterator(boolean reverse) {
		return doIterator(reverse);
	}

	/**
	 * Returns a {@link ProtectedBidirectionalIterator}.
	 * 
	 * @param reverse
	 *            The initial index to be used. Whether to return a
	 *            {@link ProtectedBidirectionalIterator} that iterates this
	 *            indexed source in reverse.
	 * @return A {@link ProtectedBidirectionalIterator}.
	 */
	protected abstract ProtectedBidirectionalIterator<Payload> doIterator(boolean reverse);

}
