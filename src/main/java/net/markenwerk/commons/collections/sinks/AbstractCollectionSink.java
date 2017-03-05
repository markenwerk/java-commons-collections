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

import java.util.Collection;

import net.markenwerk.commons.collections.Nullity;

/**
 * An {@link AbstractCollectionSink} is an {@link AbstractSink} that is backed
 * by a {@link Collection}.
 * 
 * @param <Payload>
 *            The payload type.
 * @author Torsten Krause (tk at markenwerk dot net)
 * @since 1.0.0
 */
public abstract class AbstractCollectionSink<Payload> extends AbstractSink<Payload> {

	private final Collection<Payload> collection;

	/**
	 * Creates a new {@link AbstractCollectionSink}.
	 * 
	 * @param nullity
	 *            The {@link Nullity} to be used.
	 * @param collection
	 *            The {@link Collection} to be used.
	 * 
	 * @throws IllegalArgumentException
	 *             If the given {@link Nullity} is {@literal null} or if the
	 *             given {@link Collection} is {@literal null}.
	 */
	public AbstractCollectionSink(Nullity nullity, Collection<Payload> collection) throws IllegalArgumentException {
		super(nullity);
		if (null == collection) {
			throw new IllegalArgumentException("The given collection is null");
		}
		this.collection = collection;
	}

	/**
	 * Returns the {@link Collection} of payload values this
	 * {@link AbstractCollectionSink} has been created with.
	 * 
	 * @return The {@link Collection} of payload values.
	 */
	public final Collection<Payload> getCollection() {
		return collection;
	}

	@Override
	protected final void doAdd(Payload payload) {
		collection.add(payload);
	}

}
