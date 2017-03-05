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
import net.markenwerk.commons.collections.stacks.Stack;

/**
 * An {@link AbstractStackSink} is an {@link AbstractSink} that is backed by a
 * {@link Stack}.
 * 
 * @param <Payload>
 *            The payload type.
 * @author Torsten Krause (tk at markenwerk dot net)
 * @since 1.0.0
 */
public abstract class AbstractStackSink<Payload> extends AbstractSink<Payload> {

	private final Stack<Payload> stack;

	/**
	 * Creates a new {@link AbstractStackSink}.
	 * 
	 * @param nullity
	 *            The {@link Nullity} to be used.
	 * @param stack
	 *            The {@link Stack} to be used.
	 * 
	 * @throws IllegalArgumentException
	 *             If the given {@link Nullity} is {@literal null} or if the
	 *             given {@link Stack} is {@literal null}.
	 */
	public AbstractStackSink(Nullity nullity, Stack<Payload> stack) throws IllegalArgumentException {
		super(nullity);
		if (null == stack) {
			throw new IllegalArgumentException("The given stack is null");
		}
		this.stack = stack;
	}

	/**
	 * Returns the {@link Stack} of payload values this
	 * {@link AbstractStackSink} has been created with.
	 * 
	 * @return The {@link Stack} of payload values.
	 */
	public final Stack<Payload> getStack() {
		return stack;
	}

	@Override
	protected final void doAdd(Payload payload) {
		stack.push(payload);
	}

}
