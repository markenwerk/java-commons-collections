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

import java.security.CodeSource;
import java.util.LinkedList;
import java.util.List;

import net.markenwerk.commons.collections.Nullity;

/**
 * A {@link ListSequence} is an {@link AbstractSequence} that is backed by a
 * {@link List}.
 * 
 * @param <Payload>
 *            The payload type.
 * @author Torsten Krause (tk at markenwerk dot net)
 * @since 1.0.0
 */
public final class ListSequence<Payload> extends AbstractListSequence<Payload> {

	/**
	 * Creates a new {@link CodeSource} with a new {@link LinkedList} and the
	 * {@link Nullity#ALLOW default} {@link Nullity}.
	 */
	public ListSequence() {
		this(Nullity.ALLOW, new LinkedList<Payload>());
	}

	/**
	 * Creates a new {@link CodeSource} with the {@link Nullity#ALLOW default}
	 * {@link Nullity}.
	 * 
	 * @param list
	 *            The {@link List} to be used.
	 * 
	 * @throws IllegalArgumentException
	 *             If the given {@link List} is {@literal null}.
	 */
	public ListSequence(List<Payload> list) throws IllegalArgumentException {
		this(Nullity.ALLOW, list);
	}

	/**
	 * Creates a new {@link CodeSource} with a new {@link LinkedList}.
	 * 
	 * @param nullity
	 *            The {@link Nullity} to be used.
	 * 
	 * @throws IllegalArgumentException
	 *             If the given {@link Nullity} is {@literal null}.
	 */
	public ListSequence(Nullity nullity) throws IllegalArgumentException {
		this(nullity, new LinkedList<Payload>());
	}

	/**
	 * Creates a new {@link CodeSource}.
	 * 
	 * @param nullity
	 *            The {@link Nullity} to be used.
	 * @param list
	 *            The {@link List} to be used.
	 * 
	 * @throws IllegalArgumentException
	 *             If the given {@link Nullity} is {@literal null} or if the
	 *             given {@link List} is {@literal null}.
	 */
	public ListSequence(Nullity nullity, List<Payload> list) throws IllegalArgumentException {
		super(nullity, list);
	}

}
