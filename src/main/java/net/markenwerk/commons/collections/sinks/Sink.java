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
package net.markenwerk.commons.collections.sinks;

/**
 * A {@link Sink} is write-only data structure.
 * 
 * <p>
 * A {@link Sink } is intended to be used in API interfaces where the publishing
 * component wants to expose write-only access.
 * 
 * @param <Payload>
 *            The payload type
 * @author Torsten Krause (tk at markenwerk dot net)
 * @since 1.0.0
 */
public interface Sink<Payload> {

	/**
	 * Adds the given payload value.
	 * 
	 * @param payload
	 *            The payload value to be added.
	 * @return This {@link Sink}.
	 * 
	 * @throws IllegalArgumentException
	 *             If the given payload value is {@literal null} and this
	 *             {@link Sink} doesn't allow a {@literal null} to be added.
	 */
	public Sink<Payload> add(Payload payload) throws IllegalArgumentException;

	/**
	 * Adds the given payload values.
	 * 
	 * @param payloads
	 *            The array of payload values to be added.
	 * @return This {@link Sink}.
	 * 
	 * @throws IllegalArgumentException
	 *             If the given array of payload values is {@literal null} or if
	 *             any of the given payload values is {@literal null} and this
	 *             {@link Sink} doesn't allow a {@literal null} to be added.
	 */
	public Sink<Payload> addAll(Payload... payloads) throws IllegalArgumentException;

	/**
	 * Adds the given payload values.
	 * 
	 * @param payloads
	 *            The {@link Iterable} of payload values to be added.
	 * @return This {@link Sink}.
	 * 
	 * @throws IllegalArgumentException
	 *             If the given {@link Iterable} of payload values is
	 *             {@literal null} or if any of the given payload values is
	 *             {@literal null} and this {@link Sink} doesn't allow a
	 *             {@literal null} to be added.
	 */
	public Sink<Payload> addAll(Iterable<? extends Payload> payloads) throws IllegalArgumentException;

	/**
	 * Returns the hash code of this {@link Sink}, which must not reveal any
	 * information about the payload values that have been added so far.
	 * 
	 * @return The hash code of this {@link Sink}.
	 */
	@Override
	public int hashCode();

	/**
	 * Compares the given object with this {@link Sink} for equality, which must
	 * not reveal any information about the payload values that have been added
	 * so far.
	 */
	@Override
	public boolean equals(Object object);

	/**
	 * The String {@literal []}.
	 * 
	 * @return The String {@literal []}.
	 */
	@Override
	public String toString();

}
