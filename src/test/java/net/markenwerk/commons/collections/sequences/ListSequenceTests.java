/*
 * Copyright (c) 2016 Torsten Krause, Markenwerk GmbH
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

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Test;

import net.markenwerk.commons.collections.Nullity;
import net.markenwerk.commons.collections.sources.IndexedSource;
import net.markenwerk.commons.collections.sources.Source;
import net.markenwerk.commons.datastructures.Optional;
import net.markenwerk.commons.exceptions.ProvisioningException;
import net.markenwerk.commons.interfaces.Predicate;
import net.markenwerk.commons.interfaces.Provider;
import net.markenwerk.commons.iterables.ArrayIterable;
import net.markenwerk.commons.iterables.EmptyIterable;
import net.markenwerk.commons.iterators.BidirectionalIterator;

@SuppressWarnings("javadoc")
public class ListSequenceTests {

	private static final Object[] ARRAY = new Object[] { new Object(), new Object() };

	private static final List<Object> LIST = Collections.unmodifiableList(Arrays.asList(ARRAY));

	private static final List<Object> EMPTY = Collections.unmodifiableList(new LinkedList<Object>());

	/* LIST SEQUENCE */

	@Test(expected = IllegalArgumentException.class)
	public void create_nullNullity() {

		new ListSequence<Object>((Nullity) null);

	}

	@Test(expected = IllegalArgumentException.class)
	public void create_nullList() {

		new ListSequence<Object>((List<Object>) null);

	}

	@Test
	public void getNullity() {

		ListSequence<Object> source = new ListSequence<Object>(Nullity.ALLOW);

		Assert.assertSame(Nullity.ALLOW, source.getNullity());

	}

	@Test
	public void getList() {

		ListSequence<Object> source = new ListSequence<Object>(LIST);

		Assert.assertSame(LIST, source.getList());

	}

	/* SOURCE */

	@Test
	public void isEmpty_empty() {

		Source<Object> source = new ListSequence<Object>(EMPTY);

		Assert.assertTrue(source.isEmpty());

	}

	@Test
	public void isEmpty_nonEmpty() {

		Source<Object> source = new ListSequence<Object>(LIST);

		Assert.assertFalse(source.isEmpty());

	}

	@Test
	public void size_empty() {

		Source<Object> source = new ListSequence<Object>(EMPTY);

		Assert.assertEquals(0, source.size());

	}

	@Test
	public void size_nonEmpty() {

		Source<Object> source = new ListSequence<Object>(LIST);

		Assert.assertEquals(2, source.size());

	}

	@Test(expected = NoSuchElementException.class)
	public void getFirst_empty() {

		Source<Object> source = new ListSequence<Object>(EMPTY);

		source.getFirst();

	}

	@Test
	public void getFirst_nonEmpty() {

		Source<Object> source = new ListSequence<Object>(LIST);

		Assert.assertEquals(ARRAY[0], source.getFirst());

	}

	@Test(expected = IllegalArgumentException.class)
	public void getFirstMatch_nullPredicate() {

		Source<Object> source = new ListSequence<Object>(LIST);

		source.getFirstMatch(null);

	}

	@Test
	public void getFirst_unmatchedReference() {

		Source<Object> source = new ListSequence<Object>(LIST);

		Assert.assertFalse(source.getFirst(new Object()).hasValue());

	}

	@Test
	public void getFirst_matchedReference() {

		Source<Object> source = new ListSequence<Object>(LIST);

		Assert.assertSame(ARRAY[1], source.getFirst(ARRAY[1]).getValue());

	}

	@Test(expected = IllegalArgumentException.class)
	public void getAllMatches_nullPredicate() {

		Source<Object> source = new ListSequence<Object>(LIST);

		source.getAllMatches(null);

	}

	@Test
	public void getFirstMatch_unmatchedPredicate() {

		Source<Object> source = new ListSequence<Object>(LIST);

		Assert.assertFalse(source.getFirstMatch(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return false;
			}

		}).hasValue());

	}

	@Test
	public void getFirstMatch_matchedPredicate() {

		Source<Object> source = new ListSequence<Object>(LIST);

		Assert.assertSame(ARRAY[1], source.getFirstMatch(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return subject == ARRAY[1];
			}

		}).getValue());

	}

	@Test
	public void getAll_unmatchedReference() {

		Source<Object> source = new ListSequence<Object>(LIST);

		Assert.assertTrue(source.getAll(new Object()).isEmpty());

	}

	@Test
	public void getAll_matchedReference() {

		Source<Object> source = new ListSequence<Object>(LIST);

		Source<Object> matches = source.getAll(ARRAY[1]);
		Assert.assertSame(1, matches.size());
		Assert.assertTrue(matches.containsAll(ARRAY[1]));

	}

	@Test
	public void getAllMatches_unmatchedPredicate() {

		Source<Object> source = new ListSequence<Object>(LIST);

		Assert.assertTrue(source.getAllMatches(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return false;
			}

		}).isEmpty());

	}

	@Test
	public void getAllMatches_matchedPredicate() {

		Source<Object> source = new ListSequence<Object>(LIST);

		Source<Object> matches = source.getAllMatches(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return subject == ARRAY[1];
			}

		});
		Assert.assertSame(1, matches.size());
		Assert.assertTrue(matches.containsAll(ARRAY[1]));

	}

	@Test
	public void contains_uncontainedNull() {

		Source<Object> source = new ListSequence<Object>(LIST);

		Assert.assertFalse(source.contains(null));

	}

	@Test
	public void contains_containedNull() {

		Source<Object> source = new ListSequence<Object>(Arrays.asList(ARRAY[0], ARRAY[1], null));

		Assert.assertTrue(source.contains(null));

	}

	@Test
	public void contains_uncontainedObject() {

		Source<Object> source = new ListSequence<Object>(LIST);

		Assert.assertFalse(source.contains(new Object()));

	}

	@Test
	public void contains_containedObject() {

		Source<Object> source = new ListSequence<Object>(LIST);

		Assert.assertTrue(source.containsAll(ARRAY[1], ARRAY[0]));

	}

	@Test(expected = IllegalArgumentException.class)
	public void containsAll_nullArray() {

		new ListSequence<Object>(EMPTY).containsAll((Object[]) null);
	}

	@Test
	public void containsAll_emptyArray() {

		Source<Object> source = new ListSequence<Object>(LIST);

		Assert.assertTrue(source.containsAll());

	}

	@Test
	public void containsAll_allContainedArray() {

		Source<Object> source = new ListSequence<Object>(LIST);

		Assert.assertTrue(source.containsAll(ARRAY));

	}

	@Test
	public void containsAll_notAllContainedArray() {

		Source<Object> source = new ListSequence<Object>(LIST);

		Assert.assertFalse(source.containsAll(ARRAY[0], new Object()));

	}

	@Test(expected = IllegalArgumentException.class)
	public void containsAll_nullIterable() {

		new ListSequence<Object>(EMPTY).containsAll((Iterable<Object>) null);
	}

	@Test
	public void containsAll_emptyIterable() {

		Source<Object> source = new ListSequence<Object>(LIST);

		Assert.assertTrue(source.containsAll(new EmptyIterable<Object>()));

	}

	@Test
	public void containsAll_allContainedIterable() {

		Source<Object> source = new ListSequence<Object>(LIST);

		Assert.assertTrue(source.containsAll(new ArrayIterable<Object>(ARRAY)));

	}

	@Test
	public void containsAll_notAllContainedIterable() {

		Source<Object> source = new ListSequence<Object>(LIST);

		Assert.assertFalse(source.containsAll(new ArrayIterable<Object>(ARRAY[0], new Object())));

	}

	@Test
	public void iterator_iterate() {

		Source<Object> source = new ListSequence<Object>(LIST);
		Iterator<Object> iterator = source.iterator();

		Assert.assertTrue(iterator.hasNext());
		Assert.assertSame(ARRAY[0], iterator.next());
		Assert.assertTrue(iterator.hasNext());
		Assert.assertSame(ARRAY[1], iterator.next());
		Assert.assertFalse(iterator.hasNext());

	}

	@Test(expected = UnsupportedOperationException.class)
	public void iterator_remove() {

		Source<Object> source = new ListSequence<Object>(LIST);
		Iterator<Object> iterator = source.iterator();

		iterator.remove();

	}

	/* INDEXED SOURCE */

	@Test(expected = IndexOutOfBoundsException.class)
	public void get_negativeIndex() {

		IndexedSource<Object> source = new ListSequence<Object>(LIST);

		source.get(-1);

	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void get_tooLargeIndex() {

		IndexedSource<Object> source = new ListSequence<Object>(LIST);

		source.get(2);

	}

	@Test
	public void get_first() {

		IndexedSource<Object> source = new ListSequence<Object>(LIST);

		Assert.assertSame(ARRAY[0], source.get(0));

	}

	@Test
	public void get_last() {

		IndexedSource<Object> source = new ListSequence<Object>(LIST);

		Assert.assertSame(ARRAY[1], source.get(1));

	}

	@Test(expected = NoSuchElementException.class)
	public void getLast_empty() {

		IndexedSource<Object> source = new ListSequence<Object>(EMPTY);

		source.getLast();

	}

	@Test
	public void getLast_nonEmpty() {

		IndexedSource<Object> source = new ListSequence<Object>(LIST);

		Assert.assertEquals(ARRAY[1], source.getLast());

	}

	@Test
	public void getLast_unmatchedReference() {

		IndexedSource<Object> source = new ListSequence<Object>(LIST);

		Assert.assertFalse(source.getLast(new Object()).hasValue());

	}

	@Test
	public void getLast_matchedReference() {

		IndexedSource<Object> source = new ListSequence<Object>(LIST);

		Assert.assertSame(ARRAY[0], source.getLast(ARRAY[0]).getValue());

	}

	@Test(expected = IllegalArgumentException.class)
	public void getLastMatch_nullPredicate() {

		IndexedSource<Object> source = new ListSequence<Object>(LIST);

		source.getLastMatch(null);

	}

	@Test
	public void getLastMatch_unmatchedPredicate() {

		IndexedSource<Object> source = new ListSequence<Object>(LIST);

		Assert.assertFalse(source.getLastMatch(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return false;
			}

		}).hasValue());

	}

	@Test
	public void getLastMatch_matchedPredicate() {

		IndexedSource<Object> source = new ListSequence<Object>(LIST);

		Assert.assertSame(ARRAY[0], source.getLastMatch(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return subject == ARRAY[0];
			}

		}).getValue());

	}

	@Test
	public void firstIndexOf_unmatchedReference() {

		IndexedSource<Object> source = new ListSequence<Object>(LIST);

		Assert.assertFalse(source.firstIndexOf(new Object()).hasValue());

	}

	@Test
	public void firstIndexOf_matchedReference() {

		IndexedSource<Object> source = new ListSequence<Object>(LIST);

		Assert.assertEquals(Integer.valueOf(1), source.firstIndexOf(ARRAY[1]).getValue());

	}

	@Test(expected = IllegalArgumentException.class)
	public void firstIndexOfMatch_nullPredicate() {

		IndexedSource<Object> source = new ListSequence<Object>(LIST);

		source.firstIndexOfMatch(null);

	}

	@Test
	public void firstIndexOfMatch_unmatchedPredicate() {

		IndexedSource<Object> source = new ListSequence<Object>(LIST);

		Assert.assertFalse(source.firstIndexOfMatch(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return false;
			}

		}).hasValue());

	}

	@Test
	public void firstIndexOfMatch_matchedPredicate() {

		IndexedSource<Object> source = new ListSequence<Object>(LIST);

		Assert.assertEquals(Integer.valueOf(1), source.firstIndexOfMatch(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return subject == ARRAY[1];
			}

		}).getValue());

	}

	@Test
	public void lastIndexOf_unmatchedReference() {

		IndexedSource<Object> source = new ListSequence<Object>(LIST);

		Assert.assertFalse(source.lastIndexOf(new Object()).hasValue());

	}

	@Test
	public void lastIndexOf_matchedReference() {

		IndexedSource<Object> source = new ListSequence<Object>(LIST);

		Assert.assertEquals(Integer.valueOf(0), source.lastIndexOf(ARRAY[0]).getValue());

	}

	@Test(expected = IllegalArgumentException.class)
	public void lastIndexOfMatch_nullPredicate() {

		IndexedSource<Object> source = new ListSequence<Object>(LIST);

		source.lastIndexOfMatch(null);

	}

	@Test
	public void lastIndexOfMatch_unmatchedPredicate() {

		IndexedSource<Object> source = new ListSequence<Object>(LIST);

		Assert.assertFalse(source.lastIndexOfMatch(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return false;
			}

		}).hasValue());

	}

	@Test
	public void lastIndexOfMatch_matchedPredicate() {

		IndexedSource<Object> source = new ListSequence<Object>(LIST);

		Assert.assertEquals(Integer.valueOf(0), source.lastIndexOfMatch(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return subject == ARRAY[0];
			}

		}).getValue());

	}

	@Test(expected = NoSuchElementException.class)
	public void isFirst_empty() {

		IndexedSource<Object> source = new ListSequence<Object>(EMPTY);

		source.isFirst(new Object());

	}

	@Test
	public void isFirst_unmatchedReference() {

		IndexedSource<Object> source = new ListSequence<Object>(LIST);

		Assert.assertFalse(source.isFirst(new Object()));

	}

	@Test
	public void isFirst_matchedReference() {

		IndexedSource<Object> source = new ListSequence<Object>(LIST);

		Assert.assertTrue(source.isFirst(ARRAY[0]));

	}

	@Test(expected = NoSuchElementException.class)
	public void isLast_empty() {

		IndexedSource<Object> source = new ListSequence<Object>(EMPTY);

		source.isLast(new Object());

	}

	@Test
	public void isLast_unmatchedReference() {

		IndexedSource<Object> source = new ListSequence<Object>(LIST);

		Assert.assertFalse(source.isLast(new Object()));

	}

	@Test
	public void isLast_matchedReference() {

		IndexedSource<Object> source = new ListSequence<Object>(LIST);

		Assert.assertTrue(source.isLast(ARRAY[1]));

	}

	@Test
	public void iterator_nonReverse() {

		IndexedSource<Object> source = new ListSequence<Object>(LIST);
		BidirectionalIterator<Object> iterator = source.iterator(false);

		Assert.assertTrue(iterator.hasNext());
		Assert.assertSame(ARRAY[0], iterator.next());
		Assert.assertTrue(iterator.hasNext());
		Assert.assertSame(ARRAY[1], iterator.next());
		Assert.assertFalse(iterator.hasNext());

		Assert.assertTrue(iterator.hasPrevious());
		Assert.assertSame(ARRAY[1], iterator.previous());
		Assert.assertTrue(iterator.hasPrevious());
		Assert.assertSame(ARRAY[0], iterator.previous());
		Assert.assertFalse(iterator.hasPrevious());

	}

	@Test
	public void iterator_reverse() {

		IndexedSource<Object> source = new ListSequence<Object>(LIST);
		BidirectionalIterator<Object> iterator = source.iterator(true);

		Assert.assertTrue(iterator.hasNext());
		Assert.assertSame(ARRAY[1], iterator.next());
		Assert.assertTrue(iterator.hasNext());
		Assert.assertSame(ARRAY[0], iterator.next());
		Assert.assertFalse(iterator.hasNext());

		Assert.assertTrue(iterator.hasPrevious());
		Assert.assertSame(ARRAY[0], iterator.previous());
		Assert.assertTrue(iterator.hasPrevious());
		Assert.assertSame(ARRAY[1], iterator.previous());
		Assert.assertFalse(iterator.hasPrevious());

	}

	/* SEQUENCE */

	@Test(expected = IndexOutOfBoundsException.class)
	public void insert_negativeIndex() {

		Object object = new Object();
		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		sequence.insert(-1, object);

	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void insert_tooLargeIndex() {

		Object object = new Object();
		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		sequence.insert(3, object);

	}

	@Test
	public void insert_begin() {

		Object object = new Object();
		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		sequence.insert(0, object);

		Assert.assertSame(object, sequence.get(0));
		Assert.assertSame(ARRAY[0], sequence.get(1));
		Assert.assertSame(ARRAY[1], sequence.get(2));

	}

	@Test
	public void insert_middle() {

		Object object = new Object();
		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		sequence.insert(1, object);

		Assert.assertSame(ARRAY[0], sequence.get(0));
		Assert.assertSame(object, sequence.get(1));
		Assert.assertSame(ARRAY[1], sequence.get(2));

	}

	@Test
	public void insert_end() {

		Object object = new Object();
		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		sequence.insert(2, object);

		Assert.assertSame(ARRAY[0], sequence.get(0));
		Assert.assertSame(ARRAY[1], sequence.get(1));
		Assert.assertSame(object, sequence.get(2));

	}

	@Test
	public void insert_allowNull() {

		Sequence<Object> sequence = new ListSequence<Object>(Nullity.ALLOW);

		sequence.insert(0, null);

		Assert.assertFalse(sequence.isEmpty());
		Assert.assertNull(sequence.getFirst());

	}

	@Test
	public void insert_ignoreNull() {

		Sequence<Object> sequence = new ListSequence<Object>(Nullity.IGNORE);

		sequence.insert(0, null);

		Assert.assertTrue(sequence.isEmpty());

	}

	@Test(expected = IllegalArgumentException.class)
	public void insert_rejectNull() {

		Sequence<Object> sequence = new ListSequence<Object>(Nullity.REJECT);

		sequence.insert(0, null);

	}

	@Test(expected = IllegalArgumentException.class)
	public void insertAll_array_nullArray() {

		Sequence<Object> sequence = new ListSequence<Object>();

		sequence.insertAll(0, (Object[]) null);

	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void insertAll_array_negativeIndex() {

		Object object = new Object();
		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		sequence.insertAll(-1, new Object[] { object });

	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void insertAll_array_tooLargeIndex() {

		Object object = new Object();
		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		sequence.insertAll(3, new Object[] { object });

	}

	@Test
	public void insertAll_array_begin() {

		Object first = new Object();
		Object second = new Object();
		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		sequence.insertAll(0, new Object[] { first, second });

		Assert.assertSame(first, sequence.get(0));
		Assert.assertSame(second, sequence.get(1));
		Assert.assertSame(ARRAY[0], sequence.get(2));
		Assert.assertSame(ARRAY[1], sequence.get(3));

	}

	@Test
	public void insertAll_array_middle() {

		Object first = new Object();
		Object second = new Object();
		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		sequence.insertAll(1, new Object[] { first, second });

		Assert.assertSame(ARRAY[0], sequence.get(0));
		Assert.assertSame(first, sequence.get(1));
		Assert.assertSame(second, sequence.get(2));
		Assert.assertSame(ARRAY[1], sequence.get(3));

	}

	@Test
	public void insertAll_array_end() {

		Object first = new Object();
		Object second = new Object();
		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		sequence.insertAll(2, new Object[] { first, second });

		Assert.assertSame(ARRAY[0], sequence.get(0));
		Assert.assertSame(ARRAY[1], sequence.get(1));
		Assert.assertSame(first, sequence.get(2));
		Assert.assertSame(second, sequence.get(3));

	}

	@Test
	public void insertAll_array_allowNull() {

		Sequence<Object> sequence = new ListSequence<Object>(Nullity.ALLOW);

		sequence.insertAll(0, new Object[] { null });

		Assert.assertFalse(sequence.isEmpty());
		Assert.assertNull(sequence.getFirst());

	}

	@Test
	public void insertAll_array_ignoreNull() {

		Sequence<Object> sequence = new ListSequence<Object>(Nullity.IGNORE);

		sequence.insertAll(0, new Object[] { null });

		Assert.assertTrue(sequence.isEmpty());

	}

	@Test(expected = IllegalArgumentException.class)
	public void insertAll_array_rejectNull() {

		Sequence<Object> sequence = new ListSequence<Object>(Nullity.REJECT);

		sequence.insertAll(0, new Object[] { null });

	}

	@Test(expected = IllegalArgumentException.class)
	public void insertAll_iterable_nullArray() {

		Sequence<Object> sequence = new ListSequence<Object>();

		sequence.insertAll(0, (Iterable<Object>) null);

	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void insertAll_iterable_negativeIndex() {

		Object object = new Object();
		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		sequence.insertAll(-1, new ArrayIterable<Object>(object));

	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void insertAll_iterable_tooLargeIndex() {

		Object object = new Object();
		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		sequence.insertAll(3, new ArrayIterable<Object>(object));

	}

	@Test
	public void insertAll_iterable_begin() {

		Object first = new Object();
		Object second = new Object();
		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		sequence.insertAll(0, new ArrayIterable<Object>(first, second));

		Assert.assertSame(first, sequence.get(0));
		Assert.assertSame(second, sequence.get(1));
		Assert.assertSame(ARRAY[0], sequence.get(2));
		Assert.assertSame(ARRAY[1], sequence.get(3));

	}

	@Test
	public void insertAll_iterable_middle() {

		Object first = new Object();
		Object second = new Object();
		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		sequence.insertAll(1, new ArrayIterable<Object>(first, second));

		Assert.assertSame(ARRAY[0], sequence.get(0));
		Assert.assertSame(first, sequence.get(1));
		Assert.assertSame(second, sequence.get(2));
		Assert.assertSame(ARRAY[1], sequence.get(3));

	}

	@Test
	public void insertAll_iterable_end() {

		Object first = new Object();
		Object second = new Object();
		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		sequence.insertAll(2, new ArrayIterable<Object>(first, second));

		Assert.assertSame(ARRAY[0], sequence.get(0));
		Assert.assertSame(ARRAY[1], sequence.get(1));
		Assert.assertSame(first, sequence.get(2));
		Assert.assertSame(second, sequence.get(3));

	}

	@Test
	public void insertAll_iterable_allowNull() {

		Sequence<Object> sequence = new ListSequence<Object>(Nullity.ALLOW);

		sequence.insertAll(0, new ArrayIterable<Object>((Object) null));

		Assert.assertFalse(sequence.isEmpty());
		Assert.assertNull(sequence.getFirst());

	}

	@Test
	public void insertAll_iterable_ignoreNull() {

		Sequence<Object> sequence = new ListSequence<Object>(Nullity.IGNORE);

		sequence.insertAll(0, new ArrayIterable<Object>((Object) null));

		Assert.assertTrue(sequence.isEmpty());

	}

	@Test(expected = IllegalArgumentException.class)
	public void insertAll_iterable_rejectNull() {

		Sequence<Object> sequence = new ListSequence<Object>(Nullity.REJECT);

		sequence.insertAll(0, new ArrayIterable<Object>((Object) null));

	}

	@Test
	public void prepend_begin() {

		Object object = new Object();
		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		sequence.prepend(object);

		Assert.assertSame(object, sequence.get(0));
		Assert.assertSame(ARRAY[0], sequence.get(1));
		Assert.assertSame(ARRAY[1], sequence.get(2));

	}

	@Test
	public void prepend_allowNull() {

		Sequence<Object> sequence = new ListSequence<Object>(Nullity.ALLOW);

		sequence.prepend(null);

		Assert.assertFalse(sequence.isEmpty());
		Assert.assertNull(sequence.getFirst());

	}

	@Test
	public void prepend_ignoreNull() {

		Sequence<Object> sequence = new ListSequence<Object>(Nullity.IGNORE);

		sequence.prepend(null);

		Assert.assertTrue(sequence.isEmpty());

	}

	@Test(expected = IllegalArgumentException.class)
	public void prepend_rejectNull() {

		Sequence<Object> sequence = new ListSequence<Object>(Nullity.REJECT);

		sequence.prepend(null);

	}

	@Test(expected = IllegalArgumentException.class)
	public void prependAll_array_nullArray() {

		Sequence<Object> sequence = new ListSequence<Object>();

		sequence.prependAll((Object[]) null);

	}

	@Test
	public void prependAll_array_begin() {

		Object first = new Object();
		Object second = new Object();
		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		sequence.prependAll(new Object[] { first, second });

		Assert.assertSame(first, sequence.get(0));
		Assert.assertSame(second, sequence.get(1));
		Assert.assertSame(ARRAY[0], sequence.get(2));
		Assert.assertSame(ARRAY[1], sequence.get(3));

	}

	@Test
	public void prependAll_array_allowNull() {

		Sequence<Object> sequence = new ListSequence<Object>(Nullity.ALLOW);

		sequence.prependAll(new Object[] { null });

		Assert.assertFalse(sequence.isEmpty());
		Assert.assertNull(sequence.getFirst());

	}

	@Test
	public void prependAll_array_ignoreNull() {

		Sequence<Object> sequence = new ListSequence<Object>(Nullity.IGNORE);

		sequence.prependAll(new Object[] { null });

		Assert.assertTrue(sequence.isEmpty());

	}

	@Test(expected = IllegalArgumentException.class)
	public void prependAll_array_rejectNull() {

		Sequence<Object> sequence = new ListSequence<Object>(Nullity.REJECT);

		sequence.prependAll(new Object[] { null });

	}

	@Test(expected = IllegalArgumentException.class)
	public void prependAll_iterable_nullArray() {

		Sequence<Object> sequence = new ListSequence<Object>();

		sequence.prependAll((Iterable<Object>) null);

	}

	@Test
	public void prependAll_iterable_begin() {

		Object first = new Object();
		Object second = new Object();
		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		sequence.prependAll(new ArrayIterable<Object>(first, second));

		Assert.assertSame(first, sequence.get(0));
		Assert.assertSame(second, sequence.get(1));
		Assert.assertSame(ARRAY[0], sequence.get(2));
		Assert.assertSame(ARRAY[1], sequence.get(3));

	}

	@Test
	public void prependAll_iterable_allowNull() {

		Sequence<Object> sequence = new ListSequence<Object>(Nullity.ALLOW);

		sequence.prependAll(new ArrayIterable<Object>((Object) null));

		Assert.assertFalse(sequence.isEmpty());
		Assert.assertNull(sequence.getFirst());

	}

	@Test
	public void prependAll_iterable_ignoreNull() {

		Sequence<Object> sequence = new ListSequence<Object>(Nullity.IGNORE);

		sequence.prependAll(new ArrayIterable<Object>((Object) null));

		Assert.assertTrue(sequence.isEmpty());

	}

	@Test(expected = IllegalArgumentException.class)
	public void prependAll_iterable_rejectNull() {

		Sequence<Object> sequence = new ListSequence<Object>(Nullity.REJECT);

		sequence.prependAll(new ArrayIterable<Object>((Object) null));

	}

	@Test
	public void append_end() {

		Object object = new Object();
		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		sequence.append(object);

		Assert.assertSame(ARRAY[0], sequence.get(0));
		Assert.assertSame(ARRAY[1], sequence.get(1));
		Assert.assertSame(object, sequence.get(2));

	}

	@Test
	public void append_allowNull() {

		Sequence<Object> sequence = new ListSequence<Object>(Nullity.ALLOW);

		sequence.append(null);

		Assert.assertFalse(sequence.isEmpty());
		Assert.assertNull(sequence.getFirst());

	}

	@Test
	public void append_ignoreNull() {

		Sequence<Object> sequence = new ListSequence<Object>(Nullity.IGNORE);

		sequence.append(null);

		Assert.assertTrue(sequence.isEmpty());

	}

	@Test(expected = IllegalArgumentException.class)
	public void append_rejectNull() {

		Sequence<Object> sequence = new ListSequence<Object>(Nullity.REJECT);

		sequence.append(null);

	}

	@Test(expected = IllegalArgumentException.class)
	public void appendAll_array_nullArray() {

		Sequence<Object> sequence = new ListSequence<Object>();

		sequence.appendAll((Object[]) null);

	}

	@Test
	public void appendAll_array_end() {

		Object first = new Object();
		Object second = new Object();
		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		sequence.appendAll(new Object[] { first, second });

		Assert.assertSame(ARRAY[0], sequence.get(0));
		Assert.assertSame(ARRAY[1], sequence.get(1));
		Assert.assertSame(first, sequence.get(2));
		Assert.assertSame(second, sequence.get(3));

	}

	@Test
	public void appendAll_array_allowNull() {

		Sequence<Object> sequence = new ListSequence<Object>(Nullity.ALLOW);

		sequence.appendAll(new Object[] { null });

		Assert.assertFalse(sequence.isEmpty());
		Assert.assertNull(sequence.getFirst());

	}

	@Test
	public void appendAll_array_ignoreNull() {

		Sequence<Object> sequence = new ListSequence<Object>(Nullity.IGNORE);

		sequence.appendAll(new Object[] { null });

		Assert.assertTrue(sequence.isEmpty());

	}

	@Test(expected = IllegalArgumentException.class)
	public void appendAll_array_rejectNull() {

		Sequence<Object> sequence = new ListSequence<Object>(Nullity.REJECT);

		sequence.appendAll(new Object[] { null });

	}

	@Test(expected = IllegalArgumentException.class)
	public void appendAll_iterable_nullArray() {

		Sequence<Object> sequence = new ListSequence<Object>();

		sequence.appendAll((Iterable<Object>) null);

	}

	@Test
	public void appendAll_iterable_end() {

		Object first = new Object();
		Object second = new Object();
		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		sequence.appendAll(new ArrayIterable<Object>(first, second));

		Assert.assertSame(ARRAY[0], sequence.get(0));
		Assert.assertSame(ARRAY[1], sequence.get(1));
		Assert.assertSame(first, sequence.get(2));
		Assert.assertSame(second, sequence.get(3));

	}

	@Test
	public void appendAll_iterable_allowNull() {

		Sequence<Object> sequence = new ListSequence<Object>(Nullity.ALLOW);

		sequence.appendAll(new ArrayIterable<Object>((Object) null));

		Assert.assertFalse(sequence.isEmpty());
		Assert.assertNull(sequence.getFirst());

	}

	@Test
	public void appendAll_iterable_ignoreNull() {

		Sequence<Object> sequence = new ListSequence<Object>(Nullity.IGNORE);

		sequence.appendAll(new ArrayIterable<Object>((Object) null));

		Assert.assertTrue(sequence.isEmpty());

	}

	@Test(expected = IllegalArgumentException.class)
	public void appendAll_iterable_rejectNull() {

		Sequence<Object> sequence = new ListSequence<Object>(Nullity.REJECT);

		sequence.appendAll(new ArrayIterable<Object>((Object) null));

	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void remove_negativeIndex() {

		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		sequence.remove(-1);

	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void remove_tooLargeIndex() {

		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		sequence.remove(2);

	}

	@Test
	public void remove_first() {

		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		Object removed = sequence.remove(0);

		Assert.assertEquals(1, sequence.size());
		Assert.assertSame(ARRAY[1], sequence.getFirst());

		Assert.assertSame(ARRAY[0], removed);

	}

	@Test
	public void remove_last() {

		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		Object removed = sequence.remove(1);

		Assert.assertEquals(1, sequence.size());
		Assert.assertSame(ARRAY[0], sequence.getFirst());

		Assert.assertSame(ARRAY[1], removed);

	}

	@Test(expected = NoSuchElementException.class)
	public void removeFirst_empty() {

		Sequence<Object> sequence = new ListSequence<Object>();

		sequence.removeFirst();

	}

	@Test
	public void removeFirst_nonEmpty() {

		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		Object removed = sequence.removeFirst();

		Assert.assertEquals(1, sequence.size());
		Assert.assertSame(ARRAY[1], sequence.getFirst());

		Assert.assertSame(ARRAY[0], removed);

	}

	@Test
	public void removeFirst_unmatchedReference() {

		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		Optional<Object> removed = sequence.removeFirst(new Object());

		Assert.assertEquals(2, sequence.size());

		Assert.assertFalse(removed.hasValue());

	}

	@Test
	public void removeFirst_matchedReference() {

		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		Optional<Object> removed = sequence.removeFirst(ARRAY[1]);

		Assert.assertEquals(1, sequence.size());
		Assert.assertSame(ARRAY[0], sequence.getFirst());

		Assert.assertSame(ARRAY[1], removed.getValue());

	}

	@Test(expected = IllegalArgumentException.class)
	public void removeFirstMatch_nullPredicate() {

		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		sequence.removeFirstMatch(null);

	}

	@Test
	public void removeFirstMatch_unmatchedPredicate() {

		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		Optional<Object> removed = sequence.removeFirstMatch(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return false;
			}

		});

		Assert.assertEquals(2, sequence.size());

		Assert.assertFalse(removed.hasValue());

	}

	@Test
	public void removeFirstMatch_matchedPredicate() {

		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		Optional<Object> removed = sequence.removeFirstMatch(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return subject == ARRAY[1];
			}

		});

		Assert.assertEquals(1, sequence.size());
		Assert.assertSame(ARRAY[0], sequence.getFirst());

		Assert.assertSame(ARRAY[1], removed.getValue());

	}

	@Test(expected = NoSuchElementException.class)
	public void removeLast_empty() {

		Sequence<Object> sequence = new ListSequence<Object>();

		sequence.removeLast();

	}

	@Test
	public void removeLast_nonEmpty() {

		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		Object removed = sequence.removeLast();

		Assert.assertEquals(1, sequence.size());
		Assert.assertSame(ARRAY[0], sequence.getFirst());

		Assert.assertSame(ARRAY[1], removed);

	}

	@Test
	public void removeLast_unmatchedReference() {

		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		Optional<Object> removed = sequence.removeLast(new Object());

		Assert.assertEquals(2, sequence.size());

		Assert.assertFalse(removed.hasValue());

	}

	@Test
	public void removeLast_matchedReference() {

		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		Optional<Object> removed = sequence.removeLast(ARRAY[0]);

		Assert.assertEquals(1, sequence.size());
		Assert.assertSame(ARRAY[1], sequence.getFirst());

		Assert.assertSame(ARRAY[0], removed.getValue());

	}

	@Test(expected = IllegalArgumentException.class)
	public void removeLastMatch_nullPredicate() {

		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		sequence.removeLastMatch(null);

	}

	@Test
	public void removeLastMatch_unmatchedPredicate() {

		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		Optional<Object> removed = sequence.removeLastMatch(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return false;
			}

		});

		Assert.assertEquals(2, sequence.size());

		Assert.assertFalse(removed.hasValue());

	}

	@Test
	public void removeLastMatch_matchedPredicate() {

		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		Optional<Object> removed = sequence.removeLastMatch(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return subject == ARRAY[0];
			}

		});

		Assert.assertEquals(1, sequence.size());
		Assert.assertSame(ARRAY[1], sequence.getFirst());

		Assert.assertSame(ARRAY[0], removed.getValue());

	}

	@Test
	public void removeAll_unmatchedReference() {

		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		Source<Object> removed = sequence.removeAll(new Object());

		Assert.assertEquals(2, sequence.size());

		Assert.assertEquals(0, removed.size());

	}

	@Test
	public void removeAll_matchedReference() {

		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		Source<Object> removed = sequence.removeAll(ARRAY[1]);

		Assert.assertEquals(1, sequence.size());
		Assert.assertSame(ARRAY[0], sequence.getFirst());

		Assert.assertEquals(1, removed.size());
		Assert.assertSame(ARRAY[1], removed.getFirst());

	}

	@Test(expected = IllegalArgumentException.class)
	public void removeAllMatches_nullPredicate() {

		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		sequence.removeAllMatches(null);

	}

	@Test
	public void removeAllMatches_unmatchedPredicate() {

		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		Source<Object> removed = sequence.removeAllMatches(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return false;
			}

		});

		Assert.assertEquals(2, sequence.size());

		Assert.assertEquals(0, removed.size());

	}

	@Test
	public void removeAllMatches_matchedPredicate() {

		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		Source<Object> removed = sequence.removeAllMatches(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return subject == ARRAY[1];
			}

		});

		Assert.assertEquals(1, sequence.size());
		Assert.assertSame(ARRAY[0], sequence.getFirst());

		Assert.assertEquals(1, removed.size());
		Assert.assertSame(ARRAY[1], removed.getFirst());
	}

	@Test
	public void retainAll_unmatchedReference() {

		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		Source<Object> retaind = sequence.retainAll(new Object());

		Assert.assertEquals(0, sequence.size());

		Assert.assertEquals(2, retaind.size());

	}

	@Test
	public void retainAll_matchedReference() {

		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		Source<Object> retaind = sequence.retainAll(ARRAY[0]);

		Assert.assertEquals(1, sequence.size());
		Assert.assertSame(ARRAY[0], sequence.getFirst());

		Assert.assertEquals(1, retaind.size());
		Assert.assertSame(ARRAY[1], retaind.getFirst());

	}

	@Test(expected = IllegalArgumentException.class)
	public void retainAllMatches_nullPredicate() {

		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		sequence.retainAllMatches(null);

	}

	@Test
	public void retainAllMatches_unmatchedPredicate() {

		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		Source<Object> retaind = sequence.retainAllMatches(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return false;
			}

		});

		Assert.assertEquals(0, sequence.size());

		Assert.assertEquals(2, retaind.size());

	}

	@Test
	public void retainAllMatches_matchedPredicate() {

		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		Source<Object> retaind = sequence.retainAllMatches(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return subject == ARRAY[0];
			}

		});

		Assert.assertEquals(1, sequence.size());
		Assert.assertSame(ARRAY[0], sequence.getFirst());

		Assert.assertEquals(1, retaind.size());
		Assert.assertSame(ARRAY[1], retaind.getFirst());
	}

	@Test
	public void clear() {

		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		Source<Object> cleared = sequence.clear();
		Iterator<Object> iterator = cleared.iterator();

		Assert.assertEquals(0, sequence.size());

		Assert.assertEquals(2, cleared.size());
		Assert.assertTrue(iterator.hasNext());
		Assert.assertSame(ARRAY[0], iterator.next());
		Assert.assertTrue(iterator.hasNext());
		Assert.assertSame(ARRAY[1], iterator.next());
		Assert.assertFalse(iterator.hasNext());

	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void replace_negativeIndex() {

		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		sequence.replace(-1, new Object());

	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void replace_tooLargeIndex() {

		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		sequence.replace(2, new Object());

	}

	@Test
	public void replace_first() {

		Object replacement = new Object();
		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		Object replaced = sequence.replace(0, replacement);

		Assert.assertEquals(2, sequence.size());
		Assert.assertSame(replacement, sequence.get(0));
		Assert.assertSame(ARRAY[1], sequence.get(1));

		Assert.assertSame(ARRAY[0], replaced);

	}

	@Test
	public void replace_last() {

		Object replacement = new Object();
		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		Object replaced = sequence.replace(1, replacement);

		Assert.assertEquals(2, sequence.size());
		Assert.assertSame(ARRAY[0], sequence.get(0));
		Assert.assertSame(replacement, sequence.get(1));

		Assert.assertSame(ARRAY[1], replaced);

	}

	@Test(expected = NoSuchElementException.class)
	public void replaceFirst_empty() {

		Sequence<Object> sequence = new ListSequence<Object>();

		sequence.replaceFirst(new Object());

	}

	@Test
	public void replaceFirst_nonEmpty() {

		Object replacment = new Object();
		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		Object replaced = sequence.replaceFirst(replacment);

		Assert.assertEquals(2, sequence.size());
		Assert.assertSame(replacment, sequence.get(0));
		Assert.assertSame(ARRAY[1], sequence.get(1));

		Assert.assertSame(ARRAY[0], replaced);

	}

	@Test
	public void replaceFirst_unmatchedReference() {

		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		Optional<Object> replaced = sequence.replaceFirst(new Object(), new Object());

		Assert.assertEquals(2, sequence.size());
		Assert.assertSame(ARRAY[0], sequence.get(0));
		Assert.assertSame(ARRAY[1], sequence.get(1));

		Assert.assertFalse(replaced.hasValue());

	}

	@Test
	public void replaceFirst_matchedReference() {

		Object replacment = new Object();
		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		Optional<Object> replaced = sequence.replaceFirst(ARRAY[1], replacment);

		Assert.assertEquals(2, sequence.size());
		Assert.assertSame(ARRAY[0], sequence.get(0));
		Assert.assertSame(replacment, sequence.get(1));

		Assert.assertSame(ARRAY[1], replaced.getValue());

	}

	@Test(expected = IllegalArgumentException.class)
	public void replaceFirstMatch_nullPredicate() {

		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		sequence.replaceFirstMatch(null, new Object());

	}

	@Test
	public void replaceFirstMatch_unmatchedPredicate() {

		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		Optional<Object> replaced = sequence.replaceFirstMatch(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return false;
			}

		}, new Object());

		Assert.assertEquals(2, sequence.size());
		Assert.assertSame(ARRAY[0], sequence.get(0));
		Assert.assertSame(ARRAY[1], sequence.get(1));

		Assert.assertFalse(replaced.hasValue());

	}

	@Test
	public void replaceFirstMatch_matchedPredicate() {

		Object replacment = new Object();
		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		Optional<Object> replaced = sequence.replaceFirstMatch(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return subject == ARRAY[1];
			}

		}, replacment);

		Assert.assertEquals(2, sequence.size());
		Assert.assertSame(ARRAY[0], sequence.get(0));
		Assert.assertSame(replacment, sequence.get(1));

		Assert.assertSame(ARRAY[1], replaced.getValue());

	}

	@Test(expected = NoSuchElementException.class)
	public void replaceLast_empty() {

		Sequence<Object> sequence = new ListSequence<Object>();

		sequence.replaceLast(new Object());

	}

	@Test
	public void replaceLast_nonEmpty() {

		Object replacment = new Object();
		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		Object replaced = sequence.replaceLast(replacment);

		Assert.assertEquals(2, sequence.size());
		Assert.assertSame(ARRAY[0], sequence.get(0));
		Assert.assertSame(replacment, sequence.get(1));

		Assert.assertSame(ARRAY[1], replaced);

	}

	@Test
	public void replaceLast_unmatchedReference() {

		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		Optional<Object> replaced = sequence.replaceLast(new Object(), new Object());

		Assert.assertEquals(2, sequence.size());
		Assert.assertSame(ARRAY[0], sequence.get(0));
		Assert.assertSame(ARRAY[1], sequence.get(1));

		Assert.assertFalse(replaced.hasValue());

	}

	@Test
	public void replaceLast_matchedReference() {

		Object replacment = new Object();
		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		Optional<Object> replaced = sequence.replaceLast(ARRAY[0], replacment);

		Assert.assertEquals(2, sequence.size());
		Assert.assertSame(replacment, sequence.get(0));
		Assert.assertSame(ARRAY[1], sequence.get(1));

		Assert.assertSame(ARRAY[0], replaced.getValue());

	}

	@Test(expected = IllegalArgumentException.class)
	public void replaceLastMatch_nullPredicate() {

		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		sequence.replaceLastMatch(null, new Object());

	}

	@Test
	public void replaceLastMatch_unmatchedPredicate() {

		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		Optional<Object> replaced = sequence.replaceLastMatch(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return false;
			}

		}, new Object());

		Assert.assertEquals(2, sequence.size());
		Assert.assertSame(ARRAY[0], sequence.get(0));
		Assert.assertSame(ARRAY[1], sequence.get(1));

		Assert.assertFalse(replaced.hasValue());

	}

	@Test
	public void replaceLastMatch_matchedPredicate() {

		Object replacment = new Object();
		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		Optional<Object> replaced = sequence.replaceLastMatch(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return subject == ARRAY[0];
			}

		}, replacment);

		Assert.assertEquals(2, sequence.size());
		Assert.assertSame(replacment, sequence.get(0));
		Assert.assertSame(ARRAY[1], sequence.get(1));

		Assert.assertSame(ARRAY[0], replaced.getValue());

	}

	@Test(expected = IllegalArgumentException.class)
	public void replaceAll_nullProvider() {

		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		sequence.replaceAll(new Object(), null);

	}

	@Test
	public void replaceAll_unmatchedReference() {

		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		Source<Object> replaced = sequence.replaceAll(new Object(), new Provider<Object>() {

			@Override
			public Object provide() throws ProvisioningException {
				return new Object();
			}

		});

		Assert.assertEquals(2, sequence.size());
		Assert.assertSame(ARRAY[0], sequence.get(0));
		Assert.assertSame(ARRAY[1], sequence.get(1));

		Assert.assertEquals(0, replaced.size());

	}

	@Test
	public void replaceAll_matchedReference() {

		final Object replacment = new Object();
		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		Source<Object> replaced = sequence.replaceAll(ARRAY[1], new Provider<Object>() {

			@Override
			public Object provide() throws ProvisioningException {
				return replacment;
			}

		});

		Assert.assertEquals(2, sequence.size());
		Assert.assertSame(ARRAY[0], sequence.get(0));
		Assert.assertSame(replacment, sequence.get(1));

		Assert.assertEquals(1, replaced.size());
		Assert.assertSame(ARRAY[1], replaced.getFirst());

	}

	@Test(expected = IllegalArgumentException.class)
	public void replaceAllMatches_nullPredicate() {

		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		sequence.replaceAllMatches(null, new Provider<Object>() {

			@Override
			public Object provide() throws ProvisioningException {
				return new Object();
			}

		});

	}

	@Test(expected = IllegalArgumentException.class)
	public void replaceAllMatches_nullProvider() {

		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		sequence.replaceAllMatches(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return false;
			}

		}, null);

	}

	@Test
	public void replaceAllMatches_unmatchedPredicate() {

		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		Source<Object> replaced = sequence.replaceAllMatches(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return false;
			}

		}, new Provider<Object>() {

			@Override
			public Object provide() throws ProvisioningException {
				return new Object();
			}

		});

		Assert.assertEquals(2, sequence.size());
		Assert.assertSame(ARRAY[0], sequence.get(0));
		Assert.assertSame(ARRAY[1], sequence.get(1));

		Assert.assertEquals(0, replaced.size());

	}

	@Test
	public void replaceAllMatches_matchedPredicate() {

		final Object replacment = new Object();
		Sequence<Object> sequence = new ListSequence<Object>(new LinkedList<Object>(LIST));

		Source<Object> replaced = sequence.replaceAllMatches(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return subject == ARRAY[1];
			}

		}, new Provider<Object>() {

			@Override
			public Object provide() throws ProvisioningException {
				return replacment;
			}

		});

		Assert.assertEquals(2, sequence.size());
		Assert.assertSame(ARRAY[0], sequence.get(0));
		Assert.assertSame(replacment, sequence.get(1));

		Assert.assertEquals(1, replaced.size());
		Assert.assertSame(ARRAY[1], replaced.getFirst());
	}

}
