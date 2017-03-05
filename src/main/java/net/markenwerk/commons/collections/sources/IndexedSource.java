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

import java.util.Collections;
import java.util.NoSuchElementException;

import net.markenwerk.commons.datastructures.Optional;
import net.markenwerk.commons.interfaces.Predicate;
import net.markenwerk.commons.iterables.ProtectedBidirectionalIterable;
import net.markenwerk.commons.iterators.ProtectedBidirectionalIterator;

/**
 * A {@link Source} is a linear read-only data structure.
 * 
 * <p>
 * A {@link Source} is intended to be used in APIs that want to expose read-only
 * access to a collection of payload values.
 * 
 * <p>
 * It is, amongst other things, intended as a more expressive replacement for
 * {@link Collections#unmodifiableList(java.util.List)} that prevents
 * unnecessary runtime exceptions.
 * 
 * @param <Payload>
 *            The payload type
 * @author Torsten Krause (tk at markenwerk dot net)
 * @since 1.0.0
 */
public interface IndexedSource<Payload> extends Source<Payload>, ProtectedBidirectionalIterable<Payload> {

	/**
	 * Returns the payload value at the given index.
	 * 
	 * @param index
	 *            The index to be used.
	 * @return The payload value.
	 * @throws IndexOutOfBoundsException
	 *             If the given index is negative or if the given index is
	 *             larger or equal to the {@link IndexedSource#size() size} of
	 *             this {@link IndexedSource}.
	 */
	public Payload get(int index) throws IndexOutOfBoundsException;

	/**
	 * Returns whether the given payload value is the
	 * {@link IndexedSource#getFirst() first} payload value.
	 * 
	 * @param payload
	 *            The payload value to be used.
	 * @return Whether the given payload value is the first payload value.
	 * @throws NoSuchElementException
	 *             If the given payload value is not contained in this
	 *             {@link IndexedSource}.
	 */
	public boolean isFirst(Payload payload) throws NoSuchElementException;

	/**
	 * Returns the first payload value.
	 * 
	 * @return The payload value.
	 * @throws NoSuchElementException
	 *             If this {@link IndexedSource} is
	 *             {@link IndexedSource#isEmpty() empty}.
	 */
	@Override
	public Payload getFirst() throws NoSuchElementException;

	/**
	 * Returns the first occurrences, by {@link Object#equals(Object) equality},
	 * of the given reference payload value.
	 * 
	 * @param reference
	 *            The reference payload to be used.
	 * @return An {@link Optional} yielding the payload value.
	 */
	@Override
	public Optional<Payload> getFirst(Payload reference);

	/**
	 * Returns the first payload value that satisfies the given
	 * {@link Predicate} .
	 * 
	 * @param predicate
	 *            The {@link Predicate} to be used.
	 * @return An {@link Optional} yielding the payload value.
	 * @throws IllegalArgumentException
	 *             If the given {@link Predicate} is {@literal null}.
	 */
	@Override
	public Optional<Payload> getFirstMatch(Predicate<? super Payload> predicate) throws IllegalArgumentException;

	/**
	 * Returns whether the given payload value is the
	 * {@link IndexedSource#getLast() last} payload value.
	 * 
	 * @param payload
	 *            The payload value to be used.
	 * @return Whether the given payload value is the first payload value.
	 * @throws NoSuchElementException
	 *             If the given payload value is not contained in this
	 *             {@link IndexedSource}.
	 */
	public boolean isLast(Payload payload) throws NoSuchElementException;

	/**
	 * Returns the last payload value.
	 * 
	 * @return The payload value.
	 * @throws NoSuchElementException
	 *             If this {@link IndexedSource} is
	 *             {@link IndexedSource#isEmpty() empty}.
	 */
	public Payload getLast() throws NoSuchElementException;

	/**
	 * Returns the first occurrences, by {@link Object#equals(Object) equality},
	 * of the given reference payload value.
	 * 
	 * @param reference
	 *            The reference payload to be used.
	 * @return An {@link Optional} yielding the payload value.
	 */
	public Optional<Payload> getLast(Payload reference);

	/**
	 * Returns the last payload value that satisfies the given {@link Predicate}
	 * .
	 * 
	 * @param predicate
	 *            The {@link Predicate} to be used.
	 * @return An {@link Optional} yielding the payload value.
	 * @throws IllegalArgumentException
	 *             If the given {@link Predicate} is {@literal null}.
	 */
	public Optional<Payload> getLastMatch(Predicate<? super Payload> predicate) throws IllegalArgumentException;

	/**
	 * Returns index, of the first occurrence, by
	 * {@literal Object#equals(Object) equality}, of the given reference payload
	 * value.
	 * 
	 * @param payload
	 *            The payload value to be used.
	 * @return An {@link Optional} yielding the index.
	 */
	public Optional<Integer> firstIndexOf(Payload payload);

	/**
	 * Returns index, of the first occurrence of a payload value that satisfies
	 * the given {@link Predicate}.
	 * 
	 * @param predicate
	 *            The {@link Predicate} value to be used.
	 * @return An {@link Optional} yielding the index.
	 * 
	 * @throws IllegalArgumentException
	 *             If the given {@link Predicate} is {@literal null}.
	 */
	public Optional<Integer> firstIndexOfMatch(Predicate<? super Payload> predicate) throws IllegalArgumentException;

	/**
	 * Returns index, of the last occurrence, by
	 * {@literal Object#equals(Object) equality}, of the given reference payload
	 * value.
	 * 
	 * @param payload
	 *            The payload value to be used.
	 * @return An {@link Optional} yielding the index.
	 */
	public Optional<Integer> lastIndexOf(Payload payload);

	/**
	 * Returns index, of the last occurrence of a payload value that satisfies
	 * the given {@link Predicate}.
	 * 
	 * @param predicate
	 *            The {@link Predicate} value to be used.
	 * @return An {@link Optional} yielding the index.
	 * 
	 * @throws IllegalArgumentException
	 *             If the given {@link Predicate} is {@literal null}.
	 */
	public Optional<Integer> lastIndexOfMatch(Predicate<? super Payload> predicate) throws IllegalArgumentException;

	/**
	 * Returns a {@link ProtectedBidirectionalIterator}.
	 * 
	 * @param reverse
	 *            Whether to return a {@link ProtectedBidirectionalIterator}
	 *            that iterates this indexed source in reverse.
	 * @return A {@link ProtectedBidirectionalIterator}.
	 */
	public ProtectedBidirectionalIterator<Payload> iterator(boolean reverse);

}