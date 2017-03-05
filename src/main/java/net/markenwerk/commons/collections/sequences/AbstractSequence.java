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
package net.markenwerk.commons.collections.sequences;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import net.markenwerk.commons.collections.Nullity;
import net.markenwerk.commons.collections.sources.AbstractIndexedSource;
import net.markenwerk.commons.collections.sources.ListSource;
import net.markenwerk.commons.collections.sources.Source;
import net.markenwerk.commons.datastructures.Optional;
import net.markenwerk.commons.exceptions.ConversionException;
import net.markenwerk.commons.exceptions.ProvisioningException;
import net.markenwerk.commons.interfaces.Converter;
import net.markenwerk.commons.interfaces.Predicate;
import net.markenwerk.commons.interfaces.Provider;
import net.markenwerk.commons.iterables.ArrayIterable;

/**
 * A {@link AbstractSequence} is an abstract base implementation of a
 * {@link Sequence}.
 * 
 * @param <Payload>
 *            The payload type.
 * @author Torsten Krause (tk at markenwerk dot net)
 * @since 1.0.0
 */
public abstract class AbstractSequence<Payload> extends AbstractIndexedSource<Payload> implements Sequence<Payload> {

	private final Nullity nullity;

	/**
	 * Creates a new {@link AbstractSequence}.
	 * 
	 * @param nullity
	 *            The {@link Nullity} to be used.
	 * 
	 * @throws IllegalArgumentException
	 *             If the given {@link Nullity} is {@literal null}.
	 */
	public AbstractSequence(Nullity nullity) throws IllegalArgumentException {
		if (null == nullity) {
			throw new IllegalArgumentException("The given nullity is null");
		}
		this.nullity = nullity;
	}

	/**
	 * Returns the {@link Nullity} this {@link AbstractSequence} has been
	 * created with.
	 * 
	 * @return The {@link Nullity}.
	 */
	public final Nullity getNullity() {
		return nullity;
	}

	@Override
	public final Sequence<Payload> insert(int index, Payload payload) throws IllegalArgumentException {
		doInsert(index, payload, Nullity.NO_POSITION);
		return this;
	}

	@Override
	public final Sequence<Payload> insertAll(int index, Payload... payloads) throws IllegalArgumentException,
			IllegalStateException {
		if (null == payloads) {
			throw new IllegalArgumentException("The given array of payload values is null");
		}
		insertAll(index, new ArrayIterable<Payload>(payloads));
		return this;
	}

	@Override
	public final Sequence<Payload> insertAll(int index, Iterable<? extends Payload> payloads)
			throws IllegalArgumentException, IllegalStateException {
		if (null == payloads) {
			throw new IllegalArgumentException("The given iterable of payload values is null");
		}
		int position = 0;
		for (Payload payload : payloads) {
			doInsert(index + position, payload, position);
			position++;
		}
		return this;
	}

	private final void doInsert(int index, Payload payload, int position) throws IllegalArgumentException {
		if (null != payload || nullity.proceedAdd("given payload value", position)) {
			doInsert(index, payload);
			onInserted(payload);
		}
	}

	/**
	 * Inserts the given payload value as the new first value.
	 * 
	 * @param index
	 *            The index to be used.
	 * @param payload
	 *            The payload value to be inserted, which is guaranteed to be
	 *            not {@literal null}, unless the {@link Nullity} of this
	 *            {@link Sequence} is {@link Nullity#ALLOW}.
	 */
	protected abstract void doInsert(int index, Payload payload);

	/**
	 * Called after the given payload value has been inserted.
	 * 
	 * @param payload
	 *            The inserted payload value, which is guaranteed to be not
	 *            {@literal null}, unless the {@link Nullity} of this
	 *            {@link Sequence} is {@link Nullity#ALLOW}.
	 */
	protected void onInserted(Payload payload) {
	};

	@Override
	public final Sequence<Payload> prepend(Payload payload) throws IllegalArgumentException {
		return insert(0, payload);
	}

	@Override
	public final Sequence<Payload> prependAll(Payload... payloads) throws IllegalArgumentException {
		return insertAll(0, payloads);
	}

	@Override
	public final Sequence<Payload> prependAll(Iterable<? extends Payload> payloads) throws IllegalArgumentException {
		return insertAll(0, payloads);
	}

	@Override
	public final Sequence<Payload> append(Payload payload) throws IllegalArgumentException {
		return insert(size(), payload);
	}

	@Override
	public final Sequence<Payload> appendAll(Payload... payloads) throws IllegalArgumentException {
		return insertAll(size(), payloads);
	}

	@Override
	public final Sequence<Payload> appendAll(Iterable<? extends Payload> payloads) throws IllegalArgumentException {
		return insertAll(size(), payloads);
	}

	@Override
	public final Payload remove(int index) throws IndexOutOfBoundsException {
		if (index < 0) {
			throw new IndexOutOfBoundsException("The given index is negative: " + index);
		} else if (index >= size()) {
			throw new IndexOutOfBoundsException("The given index is loo large for size " + size() + ": " + index);
		}
		Payload removedPayload = doRemove(index);
		onRemoved(removedPayload);
		return removedPayload;
	}

	/**
	 * Removes the payload value at the given index.
	 * 
	 * @param index
	 *            The index to be used which is guaranteed to be not negative
	 *            and not larger then or equal to the
	 *            {@link AbstractSequence#size() size} of this
	 *            {@link AbstractSequence}.
	 * @return The payload value.
	 */
	protected abstract Payload doRemove(int index);

	/**
	 * Called after the given payload value has been removed.
	 * 
	 * @param payload
	 *            The removed payload value, which is guaranteed to be not
	 *            {@literal null}, unless the {@link Nullity} of this
	 *            {@link Sequence} is {@link Nullity#ALLOW}.
	 */
	protected void onRemoved(Payload payload) {
	}

	/**
	 * Called after the given payload values have been removed.
	 * 
	 * @param payloads
	 *            The removed payload values, which are guaranteed to be not
	 *            {@literal null}, unless the {@link Nullity} of this
	 *            {@link Sequence} is {@link Nullity#ALLOW}.
	 */
	protected void onRemoved(Source<Payload> payloads) {
	}

	@Override
	public final Payload removeFirst() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException("This sequence is empty");
		}
		return remove(0);
	}

	@Override
	public final Optional<Payload> removeFirst(Payload reference) throws IllegalArgumentException {
		return removeFirstMatch(createPredicate(reference));
	}

	@Override
	public final Optional<Payload> removeFirstMatch(Predicate<? super Payload> predicate)
			throws IllegalArgumentException {
		return firstIndexOfMatch(predicate).convert(new Converter<Integer, Payload>() {

			@Override
			public Payload convert(Integer index) throws ConversionException {
				return doRemove(index);
			}

		});
	}

	@Override
	public final Payload removeLast() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException("This sequence is empty");
		}
		return remove(size() - 1);
	}

	@Override
	public final Optional<Payload> removeLast(Payload reference) throws IllegalArgumentException {
		return removeLastMatch(createPredicate(reference));
	}

	@Override
	public final Optional<Payload> removeLastMatch(Predicate<? super Payload> predicate)
			throws IllegalArgumentException {
		return lastIndexOfMatch(predicate).convert(new Converter<Integer, Payload>() {

			@Override
			public Payload convert(Integer index) throws ConversionException {
				return doRemove(index);
			}

		});
	}

	@Override
	public final Source<Payload> removeAll(Payload reference) {
		return removeAllMatches(createPredicate(reference));
	}

	@Override
	public final Source<Payload> removeAllMatches(Predicate<? super Payload> predicate) throws IllegalArgumentException {
		if (null == predicate) {
			throw new IllegalArgumentException("The given predicate is null");
		}
		Source<Payload> removedPayloads = doRemoveAll(predicate, true);
		onRemoved(removedPayloads);
		return removedPayloads;
	}

	/**
	 * Removes the payload values that satisfy the given {@link Predicate}.
	 * 
	 * @param predicate
	 *            The {@link Predicate} to be used, which is guaranteed to be
	 *            not {@literal null}.
	 * @param satisfying
	 *            Whether the {@link Predicate} must be satisfied or not.
	 * @return A {@link Source} yielding the removed payload values.}.
	 */
	protected abstract Source<Payload> doRemoveAll(Predicate<? super Payload> predicate, boolean satisfying);

	@Override
	public final Source<Payload> retainAll(Payload reference) {
		return retainAllMatches(createPredicate(reference));
	}

	@Override
	public final Source<Payload> retainAllMatches(Predicate<? super Payload> predicate) throws IllegalArgumentException {
		if (null == predicate) {
			throw new IllegalArgumentException("The given predicate is null");
		}
		return doRemoveAll(predicate, false);
	}

	@Override
	public final Source<Payload> clear() {
		Source<Payload> removedPayloads = doClear();
		onRemoved(removedPayloads);
		return removedPayloads;
	}

	/**
	 * Removes all payload values.
	 * 
	 * @return A {@link Source} yielding the removed payload values.
	 */
	protected abstract Source<Payload> doClear();

	@Override
	public final Payload replace(int index, Payload replacement) throws IllegalArgumentException,
			IndexOutOfBoundsException {
		if (index < 0) {
			throw new IndexOutOfBoundsException("The given index is negative: " + index);
		} else if (index >= size()) {
			throw new IndexOutOfBoundsException("The given index is loo large for size " + size() + ": " + index);
		}
		if (null != replacement || nullity.proceedReplace("given replacement value", Nullity.NO_POSITION)) {
			Payload removedPayload = doReplace(index, replacement);
			onRemoved(removedPayload);
			onInserted(replacement);
			return removedPayload;
		} else {
			throw new AssertionError("Nullity returned false for null value replacement");
		}
	}

	/**
	 * Replaces the payload value at the given index with the given replacement
	 * payload value.
	 * 
	 * @param index
	 *            The index to be used which is guaranteed to be not negative
	 *            and not larger then or equal to the
	 *            {@link AbstractSequence#size() size} of this
	 *            {@link AbstractSequence}.
	 * @param replacement
	 *            The replacement payload value to be used.
	 * @return The replaced payload value.
	 */
	protected abstract Payload doReplace(int index, Payload replacement);

	@Override
	public final Payload replaceFirst(Payload replacement) throws IllegalArgumentException, NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException("This sequence is empty");
		}
		return replace(0, replacement);
	}

	@Override
	public final Optional<Payload> replaceFirst(Payload reference, Payload replacement) throws IllegalArgumentException {
		return replaceFirstMatch(createPredicate(reference), replacement);
	}

	@Override
	public final Optional<Payload> replaceFirstMatch(Predicate<? super Payload> predicate, final Payload replacement)
			throws IllegalArgumentException {
		return firstIndexOfMatch(predicate).convert(new Converter<Integer, Payload>() {

			@Override
			public Payload convert(Integer index) throws ConversionException {
				return doReplace(index, replacement);
			}

		});
	}

	@Override
	public final Payload replaceLast(Payload replacement) throws IllegalArgumentException, NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException("This sequence is empty");
		}
		return replace(size() - 1, replacement);
	}

	@Override
	public final Optional<Payload> replaceLast(Payload reference, Payload replacement) throws IllegalArgumentException {
		return replaceLastMatch(createPredicate(reference), replacement);
	}

	@Override
	public Optional<Payload> replaceLastMatch(Predicate<? super Payload> predicate, final Payload replacement)
			throws IllegalArgumentException {
		return lastIndexOfMatch(predicate).convert(new Converter<Integer, Payload>() {

			@Override
			public Payload convert(Integer index) throws ConversionException {
				return doReplace(index, replacement);
			}

		});
	}

	@Override
	public final Source<Payload> replaceAll(Payload reference, Provider<? extends Payload> provider)
			throws IllegalArgumentException, ProvisioningException {
		return replaceAllMatches(createPredicate(reference), provider);
	}

	@Override
	public final Source<Payload> replaceAllMatches(Predicate<? super Payload> predicate,
			Provider<? extends Payload> provider) throws IllegalArgumentException, ProvisioningException {
		if (null == predicate) {
			throw new IllegalArgumentException("The given perdicate is null");
		} else if (null == provider) {
			throw new IllegalArgumentException("The given provider is null");
		}
		return doReplaceAll(predicate, provider);
	}

	private Source<Payload> doReplaceAll(Predicate<? super Payload> predicate, Provider<? extends Payload> provider) {
		final List<Payload> removedPayloads = new LinkedList<Payload>();
		for (int i = 0, n = size(); i < n; i++) {
			if (predicate.test(get(i))) {
				Payload payload = provider.provide();
				if (null != payload || nullity.proceedReplace("provided replacement value", Nullity.NO_POSITION)) {
					Payload removedPayload = doReplace(i, payload);
					removedPayloads.add(removedPayload);
					onRemoved(removedPayload);
					onInserted(payload);
				} else {
					throw new AssertionError("Nullity returned false for null value replacement");
				}
			}
		}
		return new ListSource<Payload>(removedPayloads);
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

	public final boolean equals(Object object) {
		if (null == object) {
			return false;
		} else if (this == object) {
			return true;
		} else if (!(object instanceof Sequence)) {
			return false;
		}
		final Sequence<?> other = (Sequence<?>) object;
		final Iterator<Payload> iterator = iterator();
		final Iterator<?> otherIterator = other.iterator();
		while (iterator.hasNext() && otherIterator.hasNext()) {
			Payload payload = iterator.next();
			Object otherPayload = otherIterator.next();
			if (null == payload) {
				if (null != otherPayload) {
					return false;
				}
			} else {
				if (!payload.equals(otherPayload)) {
					return false;
				}
			}
		}
		return !(iterator.hasNext() || otherIterator.hasNext());
	}

}
