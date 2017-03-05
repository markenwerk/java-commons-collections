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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import net.markenwerk.commons.collections.Nullity;
import net.markenwerk.commons.collections.sources.CollectionSource;
import net.markenwerk.commons.collections.sources.ListSource;
import net.markenwerk.commons.collections.sources.Source;
import net.markenwerk.commons.interfaces.Predicate;
import net.markenwerk.commons.iterators.AbstractProtectedBidirectionalIterator;
import net.markenwerk.commons.iterators.ProtectedBidirectionalIterator;

/**
 * A {@link AbstractListSequence} is an {@link AbstractSequence} that is backed
 * by a {@link List}.
 * 
 * @param <Payload>
 *            The payload type.
 * @author Torsten Krause (tk at markenwerk dot net)
 * @since 1.0.0
 */
public abstract class AbstractListSequence<Payload> extends AbstractSequence<Payload> {

	private final List<Payload> list;

	/**
	 * Creates a new {@link CodeSource}.
	 * 
	 * @param nullity
	 *            The {@link Nullity} to be used.
	 * @param list
	 *            The {@link List} to be used.
	 * 
	 * @throws IllegalArgumentException
	 *             If the given {@link Nullity} is {@literal null} of if the
	 *             given {@link List} is {@literal null}.
	 */
	public AbstractListSequence(Nullity nullity, List<Payload> list) {
		super(nullity);
		if (null == list) {
			throw new IllegalArgumentException("The given list is null");
		}
		this.list = list;
	}

	/**
	 * Returns the {@link List} of payload values this
	 * {@link AbstractListSequence} has been created with.
	 * 
	 * @return The {@link List} of payload values.
	 */
	public final List<Payload> getList() {
		return list;
	}

	@Override
	public final int size() {
		return list.size();
	}

	@Override
	protected final void doInsert(int index, Payload payload) {
		list.add(index, payload);
	}

	@Override
	protected final Payload doGet(int index) {
		return list.get(index);
	}

	@Override
	protected final Payload doRemove(int index) {
		return list.remove(index);
	}

	@Override
	protected final Source<Payload> doRemoveAll(Predicate<? super Payload> predicate, final boolean satisfying) {
		ListIterator<Payload> iterator = list.listIterator();
		List<Payload> removedPayloads = new LinkedList<Payload>();
		while (iterator.hasNext()) {
			Payload payload = iterator.next();
			if (predicate.test(payload) == satisfying) {
				removedPayloads.add(payload);
				iterator.remove();
			}
		}
		return new CollectionSource<Payload>(removedPayloads);
	}

	@Override
	protected final Source<Payload> doClear() {
		List<Payload> removedPayloads = new ArrayList<Payload>(list);
		list.clear();
		return new ListSource<Payload>(removedPayloads);
	}

	@Override
	protected final Payload doReplace(int index, Payload payload) {
		return list.set(index, payload);
	}

	@Override
	protected ProtectedBidirectionalIterator<Payload> doIterator(boolean reverse) {
		if (reverse) {

			return new AbstractProtectedBidirectionalIterator<Payload>() {

				private final ListIterator<Payload> iterator = list.listIterator(list.size());

				@Override
				public boolean hasNext() {
					return iterator.hasPrevious();
				}

				@Override
				public Payload next() {
					return iterator.previous();
				}

				@Override
				public boolean hasPrevious() {
					return iterator.hasNext();
				}

				@Override
				public Payload previous() throws NoSuchElementException {
					return iterator.next();
				}

			};

		} else {

			return new AbstractProtectedBidirectionalIterator<Payload>() {

				private final ListIterator<Payload> iterator = list.listIterator();

				@Override
				public boolean hasNext() {
					return iterator.hasNext();
				}

				@Override
				public Payload next() {
					return iterator.next();
				}

				@Override
				public boolean hasPrevious() {
					return iterator.hasPrevious();
				}

				@Override
				public Payload previous() throws NoSuchElementException {
					return iterator.previous();
				}

			};

		}
	}

}
