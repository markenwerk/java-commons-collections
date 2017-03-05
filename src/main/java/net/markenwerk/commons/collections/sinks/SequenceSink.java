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
import net.markenwerk.commons.collections.sequences.Sequence;

/**
 * A {@link CollectionSink} is an {@link AbstractSequenceSink} that performs no
 * additional actions.
 * 
 * @param <Payload>
 *            The payload type.
 * @author Torsten Krause (tk at markenwerk dot net)
 * @since 1.0.0
 */
public final class SequenceSink<Payload> extends AbstractSequenceSink<Payload> {

	/**
	 * Creates a new {@link SequenceSink} with the {@link Nullity#ALLOW default}
	 * {@link Nullity}.
	 * 
	 * @param sequence
	 *            The {@link Sequence} to be used.
	 * 
	 * @throws IllegalArgumentException
	 *             If the given {@link Sequence} is {@literal null}.
	 */
	public SequenceSink(Sequence<Payload> sequence) throws IllegalArgumentException {
		this(Nullity.ALLOW, sequence);
	}

	/**
	 * Creates a new {@link SequenceSink}.
	 * 
	 * @param nullity
	 *            The {@link Nullity} to be used.
	 * @param sequence
	 *            The {@link Sequence} to be used.
	 * 
	 * @throws IllegalArgumentException
	 *             If the given {@link Nullity} is {@literal null} or if the
	 *             given {@link Sequence} is {@literal null}.
	 */
	public SequenceSink(Nullity nullity, Sequence<Payload> sequence) throws IllegalArgumentException {
		super(nullity, sequence);
	}

}
