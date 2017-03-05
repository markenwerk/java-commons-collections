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
import net.markenwerk.commons.iterables.ProtectedIterable;

/**
 * A {@link Source} is a read-only data structure.
 * 
 * <p>
 * A {@link Source} is intended to be used in APIs that want to expose read-only
 * access to a collection of payload values.
 * 
 * <p>
 * It is, amongst other things, intended as a more expressive replacement for
 * {@link Collections#unmodifiableCollection(java.util.Collection)} that
 * prevents unnecessary runtime exceptions.
 * 
 * @param <Payload>
 *            The payload type
 * @author Torsten Krause (tk at markenwerk dot net)
 * @since 1.0.0
 */
public interface Source<Payload> extends ProtectedIterable<Payload> {

	/**
	 * Returns whether this {@link Source} is empty.
	 * 
	 * @return Whether this {@link Source} is empty.
	 */
	public boolean isEmpty();

	/**
	 * Returns the number of payload values of this {@link Source}.
	 * 
	 * @return The number of payload values of this {@link Source}.
	 */
	public int size();

	/**
	 * Returns the first (i.e. first payload value in an
	 * {@link Source#iterator() iterator}) payload value.
	 * 
	 * @return The payload value.
	 * @throws NoSuchElementException
	 *             If this {@link IndexedSource} is
	 *             {@link IndexedSource#isEmpty() empty}.
	 */
	public Payload getFirst() throws NoSuchElementException;

	/**
	 * Returns the first (i.e. first occurrence in an {@link Source#iterator()
	 * iterator} of this {@link Source}) occurrences, by
	 * {@link Object#equals(Object) equality}, of the given reference payload
	 * value.
	 * 
	 * @param reference
	 *            The reference payload to be used.
	 * @return An {@link Optional} yielding the payload value.
	 */
	public Optional<Payload> getFirst(Payload reference);

	/**
	 * Returns the first (i.e. first occurrence in an {@link Source#iterator()
	 * iterator} of this {@link Source}) payload value that satisfies the given
	 * {@link Predicate}.
	 * 
	 * @param predicate
	 *            The {@link Predicate} to be used.
	 * @return An {@link Optional} yielding the payload value.
	 * @throws IllegalArgumentException
	 *             If the given {@link Predicate} is {@literal null}.
	 */
	public Optional<Payload> getFirstMatch(Predicate<? super Payload> predicate) throws IllegalArgumentException;

	/**
	 * Returns all occurrences, by {@link Object#equals(Object) equality}, of
	 * the given reference payload value.
	 * 
	 * @param reference
	 *            The reference payload to be used.
	 * @return A {@link Source} yielding the payload values.
	 */
	public Source<Payload> getAll(Payload reference);

	/**
	 * Returns the payload values that satisfy the given {@link Predicate}.
	 * 
	 * @param predicate
	 *            The {@link Predicate} to be used.
	 * @return A {@link Source} yielding the payload values.
	 * @throws IllegalArgumentException
	 *             If the given {@link Predicate} is {@literal null}.
	 */
	public Source<Payload> getAllMatches(Predicate<? super Payload> predicate) throws IllegalArgumentException;

	/**
	 * Returns whether this {@link Source} contains, by
	 * {@link Object#equals(Object) equality}, the given reference payload
	 * value.
	 * 
	 * @param reference
	 *            The reference payload value to be checked.
	 * @return Whether this {@link Source} contains the given reference payload
	 *         value.
	 */
	public boolean contains(Object reference);

	/**
	 * Returns whether this {@link Source} contains a payload value that
	 * satisfies the given {@link Predicate}.
	 * 
	 * @param predicate
	 *            The {@link Predicate} to be used.
	 * @return Whether this {@link Source} contains a payload value that
	 *         satisfies the given {@link Predicate}.
	 * @throws IllegalArgumentException
	 *             if the given {@link Predicate} is {@literal null}.
	 */
	public boolean containsMatch(Predicate<? super Payload> predicate) throws IllegalArgumentException;

	/**
	 * Returns whether this {@link Source} contains, by
	 * {@link Object#equals(Object) equality}, all of the given reference
	 * payload values.
	 * 
	 * @param references
	 *            The array of reference payload values to be checked.
	 * @return Whether this {@link Source} contains all of the given payload
	 *         values.
	 * @throws IllegalArgumentException
	 *             If the given array of reference payload values is
	 *             {@literal null}.
	 */
	public boolean containsAll(Object... references) throws IllegalArgumentException;

	/**
	 * Returns whether this {@link Source} contains, by
	 * {@link Object#equals(Object) equality}, all of the given reference
	 * payload values.
	 * 
	 * @param references
	 *            The {@link Iterable} of reference payload values to be
	 *            checked.
	 * @return Whether this {@link Source} contains all of the given payload
	 *         values.
	 * @throws IllegalArgumentException
	 *             If the given {@link Iterable} of reference payload values is
	 *             {@literal null}.
	 */
	public boolean containsAll(Iterable<?> references) throws IllegalArgumentException;

}
