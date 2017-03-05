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
package net.markenwerk.commons.collections.stacks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import net.markenwerk.commons.collections.Nullity;
import net.markenwerk.commons.collections.sources.AbstractSource;
import net.markenwerk.commons.collections.sources.CollectionSource;
import net.markenwerk.commons.collections.sources.ListSource;
import net.markenwerk.commons.collections.sources.Source;
import net.markenwerk.commons.datastructures.Optional;
import net.markenwerk.commons.interfaces.Predicate;
import net.markenwerk.commons.iterables.ArrayIterable;
import net.markenwerk.commons.iterators.ProtectedIterator;

/**
 * An {@link AbstractStack} is an abstract base implementation of {@link Stack}.
 * 
 * <p>
 * An {@link AbstractStack} has two sets of protected methods.
 * </p>
 * 
 * <ul>
 * <li>A first set of abstract methods, prefixed with {@literal do}, must be
 * implemented by concrete implementations to perform the necessary structural
 * modifications to the underlying data structure.</li>
 * <li>A second set of methods, prefixed with {@literal on}, may be implemented
 * by concrete implementations to perform additional actions after a structural
 * modification has occurred.</li>
 * </ul>
 * 
 * @param <Payload>
 *            The payload type.
 * @author Torsten Krause (tk at markenwerk dot net)
 * @since 1.0.0
 */
public abstract class AbstractStack<Payload> extends AbstractSource<Payload> implements Stack<Payload> {

	private final Nullity nullity;

	/**
	 * Creates a new {@link AbstractStack}.
	 * 
	 * @param nullity
	 *            The {@link Nullity} to be used.
	 * @throws IllegalArgumentException
	 *             If the given {@link Nullity} is {@literal null};
	 */
	public AbstractStack(Nullity nullity) throws IllegalArgumentException {
		if (null == nullity) {
			throw new IllegalArgumentException("The given nullity is null");
		}
		this.nullity = nullity;
	}

	/**
	 * Returns the {@link Nullity} this {@link AbstractStack} has been created
	 * with.
	 * 
	 * @return The {@link Nullity}.
	 */
	public final Nullity getNullity() {
		return nullity;
	}

	@Override
	public final int size() {
		return doSize();
	}

	/**
	 * Returns the number of payload values of this {@link Stack}.
	 * 
	 * @return The number of payload values of this {@link Stack}.
	 */
	protected abstract int doSize();

	@Override
	public final Stack<Payload> push(Payload payload) throws IllegalArgumentException {
		doPush(payload, Nullity.NO_POSITION);
		return this;
	}

	@Override
	public final Stack<Payload> pushAll(Payload... payloads) throws IllegalArgumentException, IllegalStateException {
		if (null == payloads) {
			throw new IllegalArgumentException("The given array of payload values is null");
		}
		pushAll(new ArrayIterable<Payload>(payloads));
		return this;
	}

	@Override
	public final Stack<Payload> pushAll(Iterable<? extends Payload> payloads) throws IllegalArgumentException,
			IllegalStateException {
		if (null == payloads) {
			throw new IllegalArgumentException("The given iterable of payload values is null");
		}
		int position = 0;
		for (Payload payload : payloads) {
			doPush(payload, position);
			position++;
		}
		return this;
	}

	private final void doPush(Payload payload, int position) throws IllegalArgumentException {
		if (null != payload || nullity.proceedAdd("given payload value", position)) {
			doPush(payload);
			onPushed(payload);
		}
	}

	/**
	 * Pushes the given payload value as the new first value.
	 * 
	 * @param payload
	 *            The payload value to be pushed, which is guaranteed to be not
	 *            {@literal null}, unless the {@link Nullity} of this
	 *            {@link Stack} is {@link Nullity#ALLOW}.
	 */
	protected abstract void doPush(Payload payload);

	/**
	 * Called after the given payload value has been pushed as the new first
	 * value.
	 * 
	 * @param payload
	 *            The pushed payload value, which is guaranteed to be not
	 *            {@literal null}, unless the {@link Nullity} of this
	 *            {@link Stack} is {@link Nullity#ALLOW}.
	 */
	protected void onPushed(Payload payload) {
	};

	@Override
	public final Payload pop() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException("This stack is empty");
		}
		Payload payload = doPop();
		onPopped(payload);
		return payload;
	}

	@Override
	public final Source<Payload> popAll(int number) throws IllegalArgumentException {
		if (number < 0) {
			throw new IllegalArgumentException("The given number is negative: " + number);
		} else if (number > size()) {
			throw new IllegalArgumentException("The given number is loo large for size " + size() + ": " + number);
		}
		List<Payload> payloads = new ArrayList<Payload>(number);
		for (int i = 0; i < number; i++) {
			payloads.add(pop());
		}
		return new CollectionSource<Payload>(payloads);
	}

	@Override
	public final Source<Payload> popAllMatches(Predicate<? super Payload> predicate) throws IllegalArgumentException {
		if (null == predicate) {
			throw new IllegalArgumentException("The given predicate is null");
		}
		List<Payload> payloads = new LinkedList<Payload>();
		while (predicate.test(getFirst())) {
			payloads.add(pop());
		}
		return new ListSource<Payload>(payloads);
	}

	/**
	 * Removes the first (i.e. most recently {@link Stack#push(Object) pushed})
	 * payload value.
	 * 
	 * <p>
	 * Only called if this {@link AbstractStack} is not empty.
	 * </p>
	 * 
	 * @return The removed payload value.
	 */
	protected abstract Payload doPop();

	/**
	 * Called after the first (i.e. most recently {@link Stack#push(Object)
	 * pushed}) payload value has been removed.
	 * 
	 * @param payload
	 *            The popped payload value, which is guaranteed to be not
	 *            {@literal null}, unless the {@link Nullity} of this
	 *            {@link Stack} is {@link Nullity#ALLOW}.
	 */
	protected void onPopped(Payload payload) {
	};

	@Override
	public final Payload replace(Payload replacement) throws IllegalArgumentException, NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException("This stack is empty");
		}
		if (null != replacement || nullity.proceedReplace("given replacement value", Nullity.NO_POSITION)) {
			Payload replaced = doReplace(replacement);
			onPopped(replaced);
			onPushed(replacement);
			return replaced;
		} else {
			throw new IllegalArgumentException("The given replacement payload value is null");
		}
	}

	/**
	 * Replaces the payload value at the given index with the given replacement
	 * payload value.
	 * 
	 * @param replacement
	 *            The replacement payload value to be used.
	 * @return The replaced payload value.
	 */
	protected abstract Payload doReplace(Payload replacement);

	@Override
	public final Payload get(int index) throws IndexOutOfBoundsException {
		if (index < 0) {
			throw new IndexOutOfBoundsException("The given index is negative: " + index);
		} else if (index >= size()) {
			throw new IndexOutOfBoundsException("The given index is loo large for size " + size() + ": " + index);
		}
		Iterator<Payload> iterator = iterator();
		for (int i = 0; i < index; i++) {
			iterator.next();
		}
		return iterator.next();
	}

	/**
	 * Returns the first (i.e. most recently {@link Stack#push(Object) pushed})
	 * payload value.
	 * 
	 * <p>
	 * Only called if this {@link AbstractStack} is not empty.
	 * </p>
	 * 
	 * @return The first payload value.
	 */
	protected abstract Payload doGetFirst();

	@Override
	public final Source<Payload> clear() {
		Source<Payload> iterable = doClear();
		onCleared(iterable);
		return iterable;
	}

	/**
	 * Removes all payload value.
	 * 
	 * @return An {@link Iterable} that yields all removed payload values, even
	 *         after further structural modifications have been made to this
	 *         {@link AbstractStack}.
	 */
	protected abstract Source<Payload> doClear();

	/**
	 * Called after all payload values have been removed.
	 * 
	 * @param iterable
	 *            An {@link Iterable} that yields all removed payload values.
	 */
	protected void onCleared(Source<Payload> iterable) {
	};

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
	public final ProtectedIterator<Payload> iterator() {
		return doIterator();
	}

	/**
	 * Returns a fail-fast {@link ProtectedIterator} that starts with the
	 * payload value at the given first index, where {@literal 0} is the index
	 * of most recently {@link Stack#push(Object) pushed} value.
	 * 
	 * @return A fail-fast {@link ProtectedIterator}.
	 */
	protected abstract ProtectedIterator<Payload> doIterator();

	public final boolean equals(Object object) {
		if (null == object) {
			return false;
		} else if (this == object) {
			return true;
		} else if (!(object instanceof Stack)) {
			return false;
		}
		final Stack<?> other = (Stack<?>) object;
		final Iterator<Payload> iterator = iterator();
		final Iterator<?> otherIterator = other.iterator();
		while (iterator.hasNext() && otherIterator.hasNext()) {
			Payload payload = iterator.next();
			Object otherPayload = otherIterator.next();
			if (null == payload && null != otherPayload) {
				return false;
			} else if (!payload.equals(otherPayload)) {
				return false;
			}
		}
		return !otherIterator.hasNext();
	}

	public final int hashCode() {
		int hashCode = 1;
		Iterator<Payload> iterator = iterator();
		while (iterator.hasNext()) {
			Payload payload = iterator.next();
			hashCode = 31 * hashCode + (null == payload ? 0 : payload.hashCode());
		}
		return hashCode;
	}

}
