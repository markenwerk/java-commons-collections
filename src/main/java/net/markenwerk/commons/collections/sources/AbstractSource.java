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

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import net.markenwerk.commons.datastructures.Optional;
import net.markenwerk.commons.interfaces.Predicate;
import net.markenwerk.commons.iterables.ArrayIterable;
import net.markenwerk.commons.iterables.ObjectIterable;

/**
 * An {@link AbstractSource} is an abstract base implementation of a
 * {@link Source}.
 * 
 * @param <Payload>
 *            The payload type.
 * @author Torsten Krause (tk at markenwerk dot net)
 * @since 1.0.0
 */
public abstract class AbstractSource<Payload> implements Source<Payload> {

	@Override
	public final boolean isEmpty() {
		return 0 == size();
	}

	@Override
	public final Payload getFirst() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException("This source is empty");
		}
		return doGetFirst();
	}

	/**
	 * Returns the first (i.e. first payload value in an
	 * {@link Source#iterator() iterator}) payload value, which is guaranteed to
	 * exist.
	 * 
	 * @return The payload value.
	 */
	protected Payload doGetFirst() {
		return iterator().next();
	}

	@Override
	public final Optional<Payload> getFirst(Payload reference) {
		return getFirstMatch(createPredicate(reference));
	}

	@Override
	public final Optional<Payload> getFirstMatch(Predicate<? super Payload> predicate) throws IllegalArgumentException {
		if (null == predicate) {
			throw new IllegalArgumentException("The given predicate is null");
		}
		Iterator<Payload> iterator = iterator();
		while (iterator.hasNext()) {
			Payload payload = iterator.next();
			if (predicate.test(payload)) {
				return new Optional<Payload>(payload);
			}
		}
		return new Optional<Payload>();
	}

	@Override
	public final Source<Payload> getAll(Payload payload) {
		return getAllMatches(createPredicate(payload));
	}

	@Override
	public final Source<Payload> getAllMatches(Predicate<? super Payload> predicate) throws IllegalArgumentException {
		if (null == predicate) {
			throw new IllegalArgumentException("The given predicate is null");
		}
		List<Payload> list = new LinkedList<Payload>();
		for (Payload payload : this) {
			if (predicate.test(payload)) {
				list.add(payload);
			}
		}
		return new CollectionSource<Payload>(list);
	}

	@Override
	public final boolean contains(Object reference) {
		return containsAll(new ObjectIterable<Object>(reference));
	}

	@Override
	public final boolean containsMatch(Predicate<? super Payload> predicate) throws IllegalArgumentException {
		return getFirstMatch(predicate).hasValue();
	}

	@Override
	public final boolean containsAll(Object... references) throws IllegalArgumentException {
		if (null == references) {
			throw new IllegalArgumentException("The given array of reference values is null");
		}
		return containsAll(new ArrayIterable<Object>(references));
	}

	@Override
	public final boolean containsAll(Iterable<?> references) {
		if (null == references) {
			throw new IllegalArgumentException("The given iterable of reference values is null");
		}
		Set<Object> set = new HashSet<Object>();
		for (Object reference : references) {
			set.add(reference);
		}
		for (Payload payload : this) {
			set.remove(payload);
		}
		return set.isEmpty();
	}

	@Override
	public final String toString() {
		Iterator<Payload> iterator = iterator();
		if (!iterator.hasNext()) {
			return "[]";
		} else {
			StringBuilder builder = new StringBuilder();
			builder.append('[');
			Payload first = iterator.next();
			builder.append(this == first ? "(this Source)" : first);
			while (iterator.hasNext()) {
				builder.append(", ");
				Payload following = iterator.next();
				builder.append(this == following ? "(this Source)" : following);
			}
			return builder.append(']').toString();
		}
	}

	/**
	 * Creates and returns a {@link Predicate} that is satisfied, if the test
	 * subject is equal to the given payload value.
	 * 
	 * @param payload
	 *            The payload value to be used.
	 * @return The {@link Predicate}.
	 */
	protected final Predicate<Payload> createPredicate(final Payload payload) {
		return new Predicate<Payload>() {

			@Override
			public boolean test(Payload subject) {
				return null == payload ? null == subject : payload.equals(subject);
			}

		};
	}

	/**
	 * Returns the index of the first payload value that satisfies the give
	 * {@link Predicate}.
	 * 
	 * @param iterator
	 *            The {@link Iterator} to be used, which must not be
	 *            {@literal null}.
	 * @param predicate
	 *            The {@link Predicate} to be used, which must not be
	 *            {@literal null}.
	 * @return The index of the first payload value that satisfies the give
	 *         {@literal -1}, if no such payload value exists.
	 */
	protected static <Payload> Optional<Integer> firstIndexOf(Iterator<? extends Payload> iterator,
			Predicate<? super Payload> predicate) {
		int index = 0;
		while (iterator.hasNext()) {
			Payload payload = iterator.next();
			if (predicate.test(payload)) {
				return new Optional<Integer>(index);
			}
			index += 1;
		}
		return new Optional<Integer>();
	}

}
