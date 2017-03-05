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

import java.util.NoSuchElementException;

import net.markenwerk.commons.collections.sequences.Sequence;
import net.markenwerk.commons.collections.sources.IndexedSource;
import net.markenwerk.commons.collections.sources.Source;
import net.markenwerk.commons.datastructures.Optional;
import net.markenwerk.commons.interfaces.Predicate;
import net.markenwerk.commons.iterators.ProtectedIterator;

/**
 * A {@link Stack} is a linear first-in-last-out data structure.
 * 
 * @param <Payload>
 *           The payload type
 * @author Torsten Krause (tk at markenwerk dot net)
 * @since 1.0.0
 */
public interface Stack<Payload> extends Source<Payload> {

	/**
	 * Pushes the given payload value as the new first value.
	 * 
	 * @param payload
	 *           The payload value to be pushed.
	 * @return This {@link Stack}.
	 * 
	 * @throws IllegalArgumentException
	 *            If the given payload value is {@literal null} and this
	 *            {@link Sequence} doesn't allow a {@literal null} to be added.
	 */
	public Stack<Payload> push(Payload payload) throws IllegalArgumentException;

	/**
	 * {@link Stack#push(Object) Pushes} the given payload values in the given
	 * order.
	 * 
	 * <p>
	 * This is a short hand for a multiple {@link Stack#push(Object) pushes}.
	 * </p>
	 * 
	 * @param payloads
	 *           The array of payload values to be pushed.
	 * @return This {@link Stack}.
	 * 
	 * @throws IllegalArgumentException
	 *            If the given array of payload values is {@literal null} or if
	 *            one of the given payload values is {@literal null} and this
	 *            {@link Sequence} doesn't allow a {@literal null} to be added.
	 */
	public Stack<Payload> pushAll(Payload... payloads) throws IllegalArgumentException;

	/**
	 * {@link Stack#push(Object) Pushes} the given payload values in the given
	 * order.
	 * 
	 * <p>
	 * This is a short hand for a multiple {@link Stack#push(Object) pushes}.
	 * </p>
	 * 
	 * @param payloads
	 *           The {@link Iterable} of payload values to be pushed.
	 * @return This {@link Stack}.
	 * 
	 * @throws IllegalArgumentException
	 *            If the given {@link Iterable} of payload values is
	 *            {@literal null} or if one of the given payload values is
	 *            {@literal null} and this {@link Sequence} doesn't allow a
	 *            {@literal null} to be added.
	 */
	public Stack<Payload> pushAll(Iterable<? extends Payload> payloads) throws IllegalArgumentException;

	/**
	 * Removes the first (i.e. most recently {@link Stack#push(Object) pushed})
	 * payload value.
	 * 
	 * @return The removed payload value.
	 * @throws NoSuchElementException
	 *            If this {@link Stack} is {@link Stack#isEmpty() empty}.
	 */
	public Payload pop() throws NoSuchElementException;

	/**
	 * {@link Stack#pop() Removes} the given number of payload values.
	 * 
	 * @param number
	 *           The number of payload values to be popped.
	 * @return A {@link Source} yielding the removed payload values.
	 * @throws IllegalArgumentException
	 *            If the given index is negative or if the given index is larger
	 *            than the {@link Stack#size() size} of this {@link Stack} .
	 */
	public Source<Payload> popAll(int number) throws IllegalArgumentException;

	/**
	 * Removes the first (i.e. most recently {@link Stack#push(Object) pushed})
	 * payload values as long as this {@link java.util.Stack} is not empty and
	 * the first payload value satisfies the given predicate.
	 * 
	 * @param predicate
	 *           The {@link Predicate} to be used.
	 * @return A {@link Source} yielding the removed payload values.
	 * @throws IllegalArgumentException
	 *            If the given {@link Predicate} is {@literal null}.
	 */
	public Source<Payload> popAllMatches(Predicate<? super Payload> predicate) throws IllegalArgumentException;

	/**
	 * Returns the payload value at the given index, where {@literal 0} is the
	 * index of most recently {@link Stack#push(Object) pushed} value.
	 * 
	 * @param index
	 *           The index to be used.
	 * @return The payload value at the given index.
	 * @throws IndexOutOfBoundsException
	 *            If the given index is negative or if the given index is equal
	 *            to or larger than the {@link Stack#size() size} of this
	 *            {@link Stack}.
	 */
	public Payload get(int index) throws IndexOutOfBoundsException;

	/**
	 * Returns the first (i.e. most recently {@link Stack#push(Object) pushed})
	 * payload value.
	 * 
	 * @return The payload value.
	 * @throws NoSuchElementException
	 *            If this {@link IndexedSource} is {@link IndexedSource#isEmpty()
	 *            empty}.
	 */
	@Override
	public Payload getFirst() throws NoSuchElementException;

	/**
	 * Returns the first (i.e. most recently {@link Stack#push(Object) pushed})
	 * occurrences, by {@link Object#equals(Object) equality}, of the given
	 * reference payload value.
	 * 
	 * @param reference
	 *           The reference payload to be used.
	 * @return An {@link Optional} yielding the payload value.
	 */
	@Override
	public Optional<Payload> getFirst(Payload reference);

	/**
	 * Returns the first i.e. most recently {@link Stack#push(Object) pushed})
	 * payload value that satisfies the given {@link Predicate}.
	 * 
	 * @param predicate
	 *           The {@link Predicate} to be used.
	 * @return An {@link Optional} yielding the payload value.
	 * @throws IllegalArgumentException
	 *            If the given {@link Predicate} is {@literal null}.
	 */
	@Override
	public Optional<Payload> getFirstMatch(Predicate<? super Payload> predicate) throws IllegalArgumentException;

	/**
	 * Replaces the first (i.e. most recently {@link Stack#push(Object) pushed})
	 * payload value with the given payload value.
	 * 
	 * <p>
	 * This is a short hand for a {@link Stack#pop() pop} and a
	 * {@link Stack#push(Object) push}.
	 * </p>
	 * 
	 * @param payload
	 *           The payload value to be used.
	 * @return The replaced payload value.
	 * @throws IllegalArgumentException
	 *            If the given payload value is {@literal null} and this
	 *            {@link Sequence} doesn't allow a {@literal null} to be added.
	 * @throws NoSuchElementException
	 *            If this {@link Stack} is {@link Stack#isEmpty() empty}.
	 */
	public Payload replace(Payload payload) throws IllegalArgumentException, NoSuchElementException;

	/**
	 * Removes all payload values.
	 * 
	 * <p>
	 * This may be a bulk operation and not a short hand for a multiple
	 * {@link Stack#push(Object) pops}.
	 * </p>
	 * 
	 * @return A {@link Source} yielding the removed payload values.
	 */
	public Source<Payload> clear();

	/**
	 * Returns the index of the first occurrence, by {@link Object#equals(Object)
	 * equality}, of the given reference payload value, where {@literal 0} is the
	 * index of most recently {@link Stack#push(Object) pushed} value.
	 * 
	 * @param reference
	 *           The reference payload value to be used.
	 * @return An {@link Optional} yielding the index.
	 */
	public Optional<Integer> firstIndexOf(Payload reference);

	/**
	 * Returns the index of the first occurrence of a reference payload value
	 * that satisfies the given {@link Predicate}, where {@literal 0} is the
	 * index of most recently {@link Stack#push(Object) pushed} value.
	 * 
	 * @param predicate
	 *           The {@link Predicate} value to be used.
	 * @return An {@link Optional} yielding the index.
	 * 
	 * @throws IllegalArgumentException
	 *            If the given {@link Predicate} is null.
	 */
	public Optional<Integer> firstIndexOfMatch(Predicate<? super Payload> predicate) throws IllegalArgumentException;

	/**
	 * Returns a fail-fast {@link ProtectedIterator} that starts with the first
	 * (i.e. most recently {@link Stack#push(Object) pushed}) payload value.
	 * 
	 * @return A fail-fast {@link ProtectedIterator}.
	 */
	@Override
	public ProtectedIterator<Payload> iterator();

	/**
	 * Returns the hash code of this {@link Stack}.
	 * 
	 * <p>
	 * The hash code of a {@link Stack} is defined to be the result of the
	 * following calculation:
	 * 
	 * <pre>
	 * int hashCode = 1;
	 * Iterator&lt;Payload&gt; iterator = stack.iterator();
	 * while (iterator.hasNext()) {
	 * 	Payload payload = iterator.next();
	 * 	hashCode = 31 * hashCode + (null == payload ? 0 : payload.hashCode());
	 * }
	 * </pre>
	 * 
	 * @return The hash code of this {@link Stack}.
	 */
	@Override
	public int hashCode();

	/**
	 * Compares the given object with this {@link Stack} for equality.
	 * 
	 * <p>
	 * Returns {@literal true} if and only if the given object is also a
	 * {@link Stack}, both {@link Stack Stacks} have the same size, and all
	 * corresponding pairs of elements in the two {@link Stack Stacks} are
	 * {@link Object#equals(Object) equal}.
	 * 
	 * @param object
	 *           The object to be used.
	 * @return If the given object is equal to this {@link Stack}.
	 */
	@Override
	public boolean equals(Object object);

}
