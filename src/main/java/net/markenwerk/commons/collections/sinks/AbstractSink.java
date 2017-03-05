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

import net.markenwerk.commons.collections.Nullity;
import net.markenwerk.commons.iterables.ArrayIterable;

/**
 * An {@link AbstractSink} is an abstract base implementation of a {@link Sink}.
 * 
 * @param <Payload>
 *            The payload type.
 * @author Torsten Krause (tk at markenwerk dot net)
 * @since 1.0.0
 */
public abstract class AbstractSink<Payload> implements Sink<Payload> {

	private final Nullity nullity;

	/**
	 * Creates a new {@link AbstractSink}.
	 * 
	 * @param nullity
	 *            The intended {@link Nullity} of
	 *            {@link AbstractSink#doAdd(Object) added} {@literal null}
	 *            -values
	 * @throws IllegalArgumentException
	 *             If the given {@link Nullity} is {@literal null}.
	 */
	public AbstractSink(Nullity nullity) throws IllegalArgumentException {
		if (null == nullity) {
			throw new IllegalArgumentException("The given nullity is null");
		}
		this.nullity = nullity;
	}

	/**
	 * Returns the {@link Nullity} this {@link AbstractSink} has been created
	 * with.
	 * 
	 * @return The {@link Nullity}.
	 */
	public final Nullity getNullity() {
		return nullity;
	}

	@Override
	public final Sink<Payload> add(Payload payload) throws IllegalArgumentException, IllegalStateException {
		doAdd(payload, Nullity.NO_POSITION);
		return this;
	}

	@Override
	public final Sink<Payload> addAll(Payload... payloads) throws IllegalArgumentException, IllegalStateException {
		if (null == payloads) {
			throw new IllegalArgumentException("The given array of payload values is null");
		}
		addAll(new ArrayIterable<Payload>(payloads));
		return this;
	}

	@Override
	public final Sink<Payload> addAll(Iterable<? extends Payload> payloads) throws IllegalArgumentException,
			IllegalStateException {
		if (null == payloads) {
			throw new IllegalArgumentException("The given iterable of payload values is null");
		}
		int position = 0;
		for (Payload payload : payloads) {
			doAdd(payload, position);
			position++;
		}
		return this;
	}

	private void doAdd(Payload payload, int position) {
		if (null != payload || nullity.proceedAdd("given payload value", position)) {
			doAdd(payload);
			onAdded(payload);
		}
	}

	/**
	 * Adds the given payload value to this {@link Sink}.
	 * 
	 * @param payload
	 *            The payload to be added, which is guaranteed to be not
	 *            {@literal null}, unless the {@link Nullity} of this
	 *            {@link Sink} is {@link Nullity#ALLOW}.
	 */
	protected abstract void doAdd(Payload payload);

	/**
	 * Called after the given payload value has been added to this {@link Sink}.
	 * 
	 * @param payload
	 *            The added payload, which is guaranteed to be not
	 *            {@literal null} , unless the {@link Nullity} of this
	 *            {@link Sink} is {@link Nullity#ALLOW}.
	 */
	protected void onAdded(Payload payload) {
	}

	@Override
	public final int hashCode() {
		return super.hashCode();
	}

	@Override
	public final boolean equals(Object object) {
		return super.equals(object);
	}

	@Override
	public final String toString() {
		return "[]";
	}

}
