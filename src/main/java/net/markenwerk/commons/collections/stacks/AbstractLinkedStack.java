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

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

import net.markenwerk.commons.collections.Nullity;
import net.markenwerk.commons.collections.sources.AbstractSource;
import net.markenwerk.commons.collections.sources.Source;
import net.markenwerk.commons.interfaces.Check;
import net.markenwerk.commons.iterators.AbstractProtectedIterator;
import net.markenwerk.commons.iterators.ProtectedIterator;

/**
 * An {@link AbstractLinkedStack} is an {@link AbstractStack} that is backed by
 * linked elements.
 * 
 * <p>
 * An {@link AbstractLinkedStack} is by itself not thread safe and must be
 * synchronized externally in a multithreaded use case.
 * 
 * @param <Payload>
 *            The payload type.
 * @author Torsten Krause (tk at markenwerk dot net)
 * @since 1.0.0
 */
public abstract class AbstractLinkedStack<Payload> extends AbstractStack<Payload> {

	private static class Link<Payload> {

		private final Payload payload;

		private final Link<Payload> next;

		private Link(Payload payload, Link<Payload> next) {
			this.payload = payload;
			this.next = next;
		}

	}

	private final Link<Payload> last = new Link<Payload>(null, null);

	private Link<Payload> first = last;

	private int size;

	private int incarnation;

	/**
	 * Creates a new {@link AbstractLinkedStack}.
	 * 
	 * @param nullity
	 *            The {@link Nullity} to be used.
	 * @throws IllegalArgumentException
	 *             If the given {@link Nullity} is {@literal null};
	 */
	public AbstractLinkedStack(Nullity nullity) throws IllegalArgumentException {
		super(nullity);
	}

	@Override
	protected final int doSize() {
		return size;
	}

	@Override
	protected final void doPush(Payload payload) {
		incarnation = +1;
		first = new Link<Payload>(payload, first);
		size += 1;
	}

	@Override
	protected final Payload doReplace(Payload replacement) {
		incarnation += 1;
		Link<Payload> removedLink = first;
		first = new Link<Payload>(replacement, removedLink.next);
		return removedLink.payload;
	}

	@Override
	protected final Payload doPop() {
		incarnation = +1;
		Link<Payload> removedLink = first;
		first = removedLink.next;
		size -= 1;
		return removedLink.payload;
	}

	@Override
	protected final Payload doGetFirst() {
		return first.payload;
	}

	@Override
	protected final Source<Payload> doClear() {
		incarnation += 1;
		Source<Payload> source = new AbstractSource<Payload>() {

			final Link<Payload> first = AbstractLinkedStack.this.first;

			final int size = AbstractLinkedStack.this.size();

			@Override
			public int size() {
				return size;
			}

			@Override
			public ProtectedIterator<Payload> iterator() {
				return doIterator(first, new Check() {

					@Override
					public boolean test() {
						return false;
					}

				});
			}

		};
		first = last;
		size = 0;
		return source;
	}

	@Override
	protected final ProtectedIterator<Payload> doIterator() {
		return doIterator(first, new Check() {

			private final int expectedIncarnation = AbstractLinkedStack.this.incarnation;

			@Override
			public boolean test() {
				return expectedIncarnation != incarnation;
			}

		});
	}

	private ProtectedIterator<Payload> doIterator(final Link<Payload> first, final Check modificationCheck) {
		return new AbstractProtectedIterator<Payload>() {

			private Link<Payload> current = first;

			@Override
			public boolean hasNext() {
				return current != last;
			}

			@Override
			public Payload next() {
				if (modificationCheck.test()) {
					throw new ConcurrentModificationException("Stack has been modified while since the last iteration");
				} else if (!hasNext()) {
					throw new NoSuchElementException("This iterator has no next element");
				}
				Payload payload = current.payload;
				current = current.next;
				return payload;
			}
		};
	}

}
