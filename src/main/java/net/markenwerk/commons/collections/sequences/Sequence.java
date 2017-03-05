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

import java.util.NoSuchElementException;

import net.markenwerk.commons.collections.sources.IndexedSource;
import net.markenwerk.commons.collections.sources.Source;
import net.markenwerk.commons.datastructures.Optional;
import net.markenwerk.commons.exceptions.ProvisioningException;
import net.markenwerk.commons.interfaces.Predicate;
import net.markenwerk.commons.interfaces.Provider;

/**
 * A {@link Sequence} is a linear data structure.
 * 
 * @param <Payload>
 *            The payload type
 * @author Torsten Krause (tk at markenwerk dot net)
 * @since 1.0.0
 */
public interface Sequence<Payload> extends IndexedSource<Payload> {

	/**
	 * Inserts the given payload value at the given index.
	 * 
	 * @param index
	 *            The index to be used.
	 * @param payload
	 *            The payload value to be inserted.
	 * @return This {@link Sequence}.
	 * 
	 * @throws IllegalArgumentException
	 *             If the given payload value is {@literal null} and this
	 *             {@link Sequence} doesn't allow a {@literal null} to be added.
	 * @throws IndexOutOfBoundsException
	 *             If the given index is negative or larger the the
	 *             {@link Sequence#size() size} of this {@link Sequence}
	 */
	public Sequence<Payload> insert(int index, Payload payload) throws IllegalArgumentException,
			IndexOutOfBoundsException;

	/**
	 * Inserts the given payload values starting at the given index.
	 * 
	 * @param index
	 *            The index to be used.
	 * @param payloads
	 *            The array of payload values to be inserted.
	 * @return This {@link Sequence}.
	 * 
	 * @throws IllegalArgumentException
	 *             If the given array of payload values is {@literal null} or if
	 *             any of the given payload values is {@literal null} and this
	 *             {@link Sequence} doesn't allow a {@literal null} to be added.
	 * @throws IndexOutOfBoundsException
	 *             If the given index is negative or larger the the
	 *             {@link Sequence#size() size} of this {@link Sequence}
	 */
	public Sequence<Payload> insertAll(int index, Payload... payloads) throws IllegalArgumentException,
			IndexOutOfBoundsException;

	/**
	 * Inserts the given payload values starting at the given index.
	 * 
	 * @param index
	 *            The index to be used.
	 * @param payloads
	 *            The {@link Iterable} of payload values to be inserted.
	 * @return This {@link Sequence}.
	 * 
	 * @throws IllegalArgumentException
	 *             If the given {@link Iterable} of payload values is
	 *             {@literal null} or if any of the given payload values is
	 *             {@literal null} and this {@link Sequence} doesn't allow a
	 *             {@literal null} to be added.
	 * @throws IndexOutOfBoundsException
	 *             If the given index is negative or larger the the
	 *             {@link Sequence#size() size} of this {@link Sequence}
	 */
	public Sequence<Payload> insertAll(int index, Iterable<? extends Payload> payloads)
			throws IllegalArgumentException, IndexOutOfBoundsException;

	/**
	 * Prepends the given payload value.
	 * 
	 * @param payload
	 *            The payload value to be prepended.
	 * @return This {@link Sequence}.
	 * 
	 * @throws IllegalArgumentException
	 *             If the given payload value is {@literal null} and this
	 *             {@link Sequence} doesn't allow a {@literal null} to be added.
	 */
	public Sequence<Payload> prepend(Payload payload) throws IllegalArgumentException;

	/**
	 * Prepends the given payload values.
	 * 
	 * @param payloads
	 *            The array of payload values to be inserted.
	 * @return This {@link Sequence}.
	 * 
	 * @throws IllegalArgumentException
	 *             If the given array of payload values is {@literal null} or if
	 *             any of the given payload values is {@literal null} and this
	 *             {@link Sequence} doesn't allow a {@literal null} to be added.
	 */
	public Sequence<Payload> prependAll(Payload... payloads) throws IllegalArgumentException;

	/**
	 * Prepends the given payload values.
	 * 
	 * @param payloads
	 *            The {@link Iterable} of payload values to be inserted.
	 * @return This {@link Sequence}.
	 * 
	 * @throws IllegalArgumentException
	 *             If the given {@link Iterable} of payload values is
	 *             {@literal null} or if any of the given payload values is
	 *             {@literal null} and this {@link Sequence} doesn't allow a
	 *             {@literal null} to be added.
	 */
	public Sequence<Payload> prependAll(Iterable<? extends Payload> payloads) throws IllegalArgumentException;

	/**
	 * Appends the given payload value.
	 * 
	 * @param payload
	 *            The payload value to be appended.
	 * @return This {@link Sequence}.
	 * 
	 * @throws IllegalArgumentException
	 *             If the given payload value is {@literal null} and this
	 *             {@link Sequence} doesn't allow a {@literal null} to be added.
	 */
	public Sequence<Payload> append(Payload payload) throws IllegalArgumentException;

	/**
	 * Appends the given payload values.
	 * 
	 * @param payloads
	 *            The array of payload values to be appended.
	 * @return This {@link Sequence}.
	 * 
	 * @throws IllegalArgumentException
	 *             If the given array of payload values is {@literal null} or if
	 *             any of the given payload values is {@literal null} and this
	 *             {@link Sequence} doesn't allow a {@literal null} to be added.
	 */
	public Sequence<Payload> appendAll(Payload... payloads) throws IllegalArgumentException;

	/**
	 * Appends the given payload values.
	 * 
	 * @param payloads
	 *            The {@link Iterable} of payload values to be appended.
	 * @return This {@link Sequence}.
	 * 
	 * @throws IllegalArgumentException
	 *             If the given {@link Iterable} of payload values is
	 *             {@literal null} or if any of the given payload values is
	 *             {@literal null} and this {@link Sequence} doesn't allow a
	 *             {@literal null} to be added.
	 */
	public Sequence<Payload> appendAll(Iterable<? extends Payload> payloads) throws IllegalArgumentException;

	/**
	 * Removes the payload value at the given index.
	 * 
	 * @param index
	 *            The index to be used.
	 * @return The payload value.
	 * @throws IndexOutOfBoundsException
	 *             If the given index is negative or if the given index is
	 *             larger or equal to the {@link Sequence#size() size} of this
	 *             {@link Sequence}.
	 */
	public Payload remove(int index) throws IndexOutOfBoundsException;

	/**
	 * Removes the first payload value.
	 * 
	 * @return The payload value.
	 * @throws NoSuchElementException
	 *             If this {@link Sequence} is {@link Sequence#isEmpty() empty}.
	 */
	public Payload removeFirst() throws NoSuchElementException;

	/**
	 * Removes the first occurrence, by {@link Object#equals(Object) equality},
	 * of the given reference payload value.
	 * 
	 * @param reference
	 *            The reference payload to be removed.
	 * @return An {@link Optional} yielding the removed payload value.
	 */
	public Optional<Payload> removeFirst(Payload reference);

	/**
	 * Removes the first payload value that satisfies the given
	 * {@link Predicate}
	 * 
	 * @param predicate
	 *            The {@link Predicate} to be used.
	 * @return An {@link Optional} yielding the removed payload value.
	 * @throws IllegalArgumentException
	 *             If the given predicate is {@literal null}.
	 */
	public Optional<Payload> removeFirstMatch(Predicate<? super Payload> predicate) throws IllegalArgumentException;

	/**
	 * Removes the last payload value.
	 * 
	 * @return The payload value.
	 * @throws NoSuchElementException
	 *             If this {@link Sequence} is {@link Sequence#isEmpty() empty}.
	 */
	public Payload removeLast() throws NoSuchElementException;

	/**
	 * Removes the last occurrence, by {@link Object#equals(Object) equality},
	 * of the given payload value.
	 * 
	 * @param reference
	 *            The reference payload to be removed.
	 * @return An {@link Optional} yielding the removed payload value.
	 */
	public Optional<Payload> removeLast(Payload reference);

	/**
	 * Removes the last payload value that satisfies the given {@link Predicate}
	 * 
	 * @param predicate
	 *            The {@link Predicate} to be used.
	 * @return An {@link Optional} yielding the removed payload value.
	 * @throws IllegalArgumentException
	 *             If the given predicate is {@literal null}.
	 */
	public Optional<Payload> removeLastMatch(Predicate<? super Payload> predicate) throws IllegalArgumentException;

	/**
	 * Removes all occurrences, by {@link Object#equals(Object) equality}, of
	 * the given reference payload value.
	 * 
	 * @param reference
	 *            The reference payload to be removed.
	 * @return A {@link Source} yielding the removed payload values.
	 */
	public Source<Payload> removeAll(Payload reference);

	/**
	 * Removes the payload values that satisfy the given {@link Predicate}.
	 * 
	 * @param predicate
	 *            The {@link Predicate} to be used.
	 * @return A {@link Source} yielding the removed payload values.
	 * @throws IllegalArgumentException
	 *             If the given {@link Predicate} is {@literal null}.
	 */
	public Source<Payload> removeAllMatches(Predicate<? super Payload> predicate) throws IllegalArgumentException;

	/**
	 * Removes all but the given payload value.
	 * 
	 * @param reference
	 *            The reference payload values to be retained.
	 * @return A {@link Source} yielding the removed payload values.
	 */
	public Source<Payload> retainAll(Payload reference);

	/**
	 * Removes all but the payload values that satisfy the given
	 * {@link Predicate}.
	 * 
	 * @param predicate
	 *            The {@link Predicate} to be used.
	 * @return A {@link Source} yielding the removed payload values.
	 * @throws IllegalArgumentException
	 *             If the given {@link Predicate} is {@literal null}.
	 */
	public Source<Payload> retainAllMatches(Predicate<? super Payload> predicate) throws IllegalArgumentException;

	/**
	 * Removes all payload values.
	 * 
	 * @return A {@link Source} yielding the removed payload values.
	 */
	public Source<Payload> clear();

	/**
	 * Replaces the payload value at the given index with the given replacement
	 * payload value.
	 * 
	 * @param index
	 *            The index to be used.
	 * @param replacement
	 *            The replacement payload value to be used.
	 * @return The replaced payload value.
	 * @throws IllegalArgumentException
	 *             If the given replacement payload value is {@literal null} and
	 *             this {@link IndexedSource} doesn't allow a {@literal null} to
	 *             be added.
	 * @throws IndexOutOfBoundsException
	 *             If the given index is negative or if the given index is
	 *             larger or equal to the {@link IndexedSource#size() size} of
	 *             this {@link IndexedSource}.
	 */
	public Payload replace(int index, Payload replacement) throws IllegalArgumentException, IndexOutOfBoundsException;

	/**
	 * Replaces the first payload value with the given replacement payload
	 * value.
	 * 
	 * @param replacement
	 *            The replacement payload value to be used.
	 * @return The replaced payload value.
	 * @throws IllegalArgumentException
	 *             If the given replacement payload value is {@literal null} and
	 *             this {@link IndexedSource} doesn't allow a {@literal null} to
	 *             be added.
	 * @throws NoSuchElementException
	 *             If this {@link IndexedSource} is
	 *             {@link IndexedSource#isEmpty() empty}.
	 */
	public Payload replaceFirst(Payload replacement) throws IllegalArgumentException, NoSuchElementException;

	/**
	 * Replaces the first occurrence of the given reference payload value with
	 * the given replacement payload value.
	 * 
	 * @param reference
	 *            The reference payload value to be used.
	 * @param replacement
	 *            The replacement payload to be used.
	 * @return An {@link Optional} yielding the removed payload value, if any.
	 * @throws IllegalArgumentException
	 *             If the given replacement payload value is {@literal null} and
	 *             this {@link IndexedSource} doesn't allow a {@literal null} to
	 *             be added.
	 */
	public Optional<Payload> replaceFirst(Payload reference, Payload replacement) throws IllegalArgumentException;

	/**
	 * Replaces the first payload value that satisfy the given {@link Predicate}
	 * with the given replacement payload value.
	 * 
	 * @param predicate
	 *            The {@link Predicate} to be used.
	 * @param replacement
	 *            The replacement payload to be used.
	 * @return An {@link Optional} yielding the removed payload value, if any.
	 * @throws IllegalArgumentException
	 *             If the give {@link Predicate} is {@literal null} or if the
	 *             given replacement payload value is {@literal null} and this
	 *             {@link IndexedSource} doesn't allow a {@literal null} to be
	 *             added.
	 */
	public Optional<Payload> replaceFirstMatch(Predicate<? super Payload> predicate, Payload replacement)
			throws IllegalArgumentException;

	/**
	 * Replaces the last payload value with the given replacement payload value.
	 * 
	 * @param replacement
	 *            The replacement payload value to be used.
	 * @return The replaced payload value.
	 * @throws IllegalArgumentException
	 *             If the given replacement payload value is {@literal null} and
	 *             this {@link IndexedSource} doesn't allow a {@literal null} to
	 *             be added.
	 * @throws NoSuchElementException
	 *             If this {@link IndexedSource} is
	 *             {@link IndexedSource#isEmpty() empty}.
	 */
	public Payload replaceLast(Payload replacement) throws IllegalArgumentException, NoSuchElementException;

	/**
	 * Replaces last occurrence of the the given reference payload value with
	 * the given replacement payload value.
	 * 
	 * @param reference
	 *            The replacement reference payload value to be used.
	 * @param replacement
	 *            The payload to be used.
	 * @return An {@link Optional} yielding the removed payload value, if any.
	 * @throws IllegalArgumentException
	 *             If the given replacement payload value is {@literal null} and
	 *             this {@link IndexedSource} doesn't allow a {@literal null} to
	 *             be added.
	 */
	public Optional<Payload> replaceLast(Payload reference, Payload replacement) throws IllegalArgumentException;

	/**
	 * Replaces the last payload value that satisfy the given {@link Predicate}
	 * with the given replacement payload value.
	 * 
	 * @param predicate
	 *            The {@link Predicate} to be used.
	 * @param replacement
	 *            The replacement payload to be used.
	 * @return An {@link Optional} yielding the removed payload value, if any.
	 * @throws IllegalArgumentException
	 *             If the give {@link Predicate} is {@literal null} or if the
	 *             given replacement payload value is {@literal null} and this
	 *             {@link IndexedSource} doesn't allow a {@literal null} to be
	 *             added.
	 */
	public Optional<Payload> replaceLastMatch(Predicate<? super Payload> predicate, Payload replacement)
			throws IllegalArgumentException;

	/**
	 * Replaces all occurrences of the the given reference payload value with
	 * payload values yielded by the given {@link Provider}.
	 * 
	 * @param reference
	 *            The reference payload value to be used.
	 * @param provider
	 *            The {@link Provider} to be used.
	 * @return A {@link Source} yielding the replaced payload values.
	 * @throws IllegalArgumentException
	 *             If the given {@link Provider} is {@literal null}.
	 * @throws ProvisioningException
	 *             If the given Provider yields {@literal null} and this
	 *             {@link IndexedSource} doesn't allow a {@literal null} to be
	 *             added.
	 */
	public Source<Payload> replaceAll(Payload reference, Provider<? extends Payload> provider)
			throws IllegalArgumentException, ProvisioningException;

	/**
	 * Replaces all payload values that satisfy the given {@link Predicate} with
	 * payload values yielded by the given {@link Provider}.
	 * 
	 * @param predicate
	 *            The {@link Predicate} to be used.
	 * @param provider
	 *            The {@link Provider} to be used.
	 * @return A {@link Source} yielding the replaced payload values.
	 * @throws IllegalArgumentException
	 *             If the give {@link Predicate} is {@literal null} or if the
	 *             given {@link Provider} is {@literal null}.
	 * @throws ProvisioningException
	 *             If the given Provider yields {@literal null} and this
	 *             {@link IndexedSource} doesn't allow a {@literal null} to be
	 *             added.
	 */
	public Source<Payload> replaceAllMatches(Predicate<? super Payload> predicate, Provider<? extends Payload> provider)
			throws IllegalArgumentException, ProvisioningException;

	/**
	 * Returns the hash code of this {@link Sequence}.
	 * 
	 * <p>
	 * The hash code of a {@link Sequence} is defined to be the result of the
	 * following calculation:
	 * 
	 * <pre>
	 * int hashCode = 1;
	 * Iterator&lt;Payload&gt; iterator = sequence.iterator();
	 * while (iterator.hasNext()) {
	 * 	Payload payload = iterator.next();
	 * 	hashCode = 31 * hashCode + (null == payload ? 0 : payload.hashCode());
	 * }
	 * </pre>
	 * 
	 * @return The hash code of this {@link Sequence}.
	 */
	@Override
	public int hashCode();

	/**
	 * Compares the given object with this {@link Sequence} for equality.
	 * 
	 * <p>
	 * Returns {@literal true} if and only if the given object is also a
	 * {@link Sequence}, both {@link Sequence Sequences} have the same size, and
	 * all corresponding pairs of elements in the two {@link Sequence Sequences}
	 * are {@link Object#equals(Object) equal}.
	 * 
	 * @param object
	 *            The object to be used.
	 * @return If the given object is equal to this {@link Sequence}.
	 */
	@Override
	public boolean equals(Object object);

}