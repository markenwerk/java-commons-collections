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
package net.markenwerk.commons.collections.sources;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Test;

import net.markenwerk.commons.interfaces.Predicate;
import net.markenwerk.commons.iterables.ArrayIterable;
import net.markenwerk.commons.iterables.EmptyIterable;
import net.markenwerk.commons.iterators.BidirectionalIterator;

@SuppressWarnings("javadoc")
public class ListSourceTests {

	private static final Object[] ARRAY = new Object[] { new Object(), new Object() };

	private static final List<Object> LIST = Collections.unmodifiableList(Arrays.asList(ARRAY));

	private static final List<Object> EMPTY = Collections.unmodifiableList(new LinkedList<Object>());

	/* LIST SOURCE */

	@Test(expected = IllegalArgumentException.class)
	public void create_nullList() {

		new ListSource<Object>(null);

	}

	@Test
	public void getList() {

		ListSource<Object> source = new ListSource<Object>(LIST);

		Assert.assertSame(LIST, source.getList());

	}

	/* SOURCE */

	@Test
	public void isEmpty_empty() {

		Source<Object> source = new ListSource<Object>(EMPTY);

		Assert.assertTrue(source.isEmpty());

	}

	@Test
	public void isEmpty_nonEmpty() {

		Source<Object> source = new ListSource<Object>(LIST);

		Assert.assertFalse(source.isEmpty());

	}

	@Test
	public void size_empty() {

		Source<Object> source = new ListSource<Object>(EMPTY);

		Assert.assertEquals(0, source.size());

	}

	@Test
	public void size_nonEmpty() {

		Source<Object> source = new ListSource<Object>(LIST);

		Assert.assertEquals(2, source.size());

	}

	@Test(expected = NoSuchElementException.class)
	public void getFirst_empty() {

		Source<Object> source = new ListSource<Object>(EMPTY);

		source.getFirst();

	}

	@Test
	public void getFirst_nonEmpty() {

		Source<Object> source = new ListSource<Object>(LIST);

		Assert.assertEquals(ARRAY[0], source.getFirst());

	}

	@Test(expected = IllegalArgumentException.class)
	public void getFirstMatch_nullPredicate() {

		Source<Object> source = new ListSource<Object>(LIST);

		source.getFirstMatch(null);

	}

	@Test
	public void getFirst_unmatchedReference() {

		Source<Object> source = new ListSource<Object>(LIST);

		Assert.assertFalse(source.getFirst(new Object()).hasValue());

	}

	@Test
	public void getFirst_matchedReference() {

		Source<Object> source = new ListSource<Object>(LIST);

		Assert.assertSame(ARRAY[1], source.getFirst(ARRAY[1]).getValue());

	}

	@Test(expected = IllegalArgumentException.class)
	public void getAllMatches_nullPredicate() {

		Source<Object> source = new ListSource<Object>(LIST);

		source.getAllMatches(null);

	}

	@Test
	public void getFirstMatch_unmatchedPredicate() {

		Source<Object> source = new ListSource<Object>(LIST);

		Assert.assertFalse(source.getFirstMatch(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return false;
			}

		}).hasValue());

	}

	@Test
	public void getFirstMatch_matchedPredicate() {

		Source<Object> source = new ListSource<Object>(LIST);

		Assert.assertSame(ARRAY[1], source.getFirstMatch(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return subject == ARRAY[1];
			}

		}).getValue());

	}

	@Test
	public void getAll_unmatchedReference() {

		Source<Object> source = new ListSource<Object>(LIST);

		Assert.assertTrue(source.getAll(new Object()).isEmpty());

	}

	@Test
	public void getAll_matchedReference() {

		Source<Object> source = new ListSource<Object>(LIST);

		Source<Object> matches = source.getAll(ARRAY[1]);
		Assert.assertSame(1, matches.size());
		Assert.assertTrue(matches.containsAll(ARRAY[1]));

	}

	@Test
	public void getAllMatches_unmatchedPredicate() {

		Source<Object> source = new ListSource<Object>(LIST);

		Assert.assertTrue(source.getAllMatches(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return false;
			}

		}).isEmpty());

	}

	@Test
	public void getAllMatches_matchedPredicate() {

		Source<Object> source = new ListSource<Object>(LIST);

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

		Source<Object> source = new ListSource<Object>(LIST);

		Assert.assertFalse(source.contains(null));

	}

	@Test
	public void contains_containedNull() {

		Source<Object> source = new ListSource<Object>(Arrays.asList(ARRAY[0], ARRAY[1], null));

		Assert.assertTrue(source.contains(null));

	}

	@Test
	public void contains_uncontainedObject() {

		Source<Object> source = new ListSource<Object>(LIST);

		Assert.assertFalse(source.contains(new Object()));

	}

	@Test
	public void contains_containedObject() {

		Source<Object> source = new ListSource<Object>(LIST);

		Assert.assertTrue(source.containsAll(ARRAY[1], ARRAY[0]));

	}

	@Test(expected = IllegalArgumentException.class)
	public void containsAll_nullArray() {

		new ListSource<Object>(EMPTY).containsAll((Object[]) null);
	}

	@Test
	public void containsAll_emptyArray() {

		Source<Object> source = new ListSource<Object>(LIST);

		Assert.assertTrue(source.containsAll());

	}

	@Test
	public void containsAll_allContainedArray() {

		Source<Object> source = new ListSource<Object>(LIST);

		Assert.assertTrue(source.containsAll(ARRAY));

	}

	@Test
	public void containsAll_notAllContainedArray() {

		Source<Object> source = new ListSource<Object>(LIST);

		Assert.assertFalse(source.containsAll(ARRAY[0], new Object()));

	}

	@Test(expected = IllegalArgumentException.class)
	public void containsAll_nullIterable() {

		new ListSource<Object>(EMPTY).containsAll((Iterable<Object>) null);
	}

	@Test
	public void containsAll_emptyIterable() {

		Source<Object> source = new ListSource<Object>(LIST);

		Assert.assertTrue(source.containsAll(new EmptyIterable<Object>()));

	}

	@Test
	public void containsAll_allContainedIterable() {

		Source<Object> source = new ListSource<Object>(LIST);

		Assert.assertTrue(source.containsAll(new ArrayIterable<Object>(ARRAY)));

	}

	@Test
	public void containsAll_notAllContainedIterable() {

		Source<Object> source = new ListSource<Object>(LIST);

		Assert.assertFalse(source.containsAll(new ArrayIterable<Object>(ARRAY[0], new Object())));

	}

	@Test
	public void iterator_iterate() {

		Source<Object> source = new ListSource<Object>(LIST);
		Iterator<Object> iterator = source.iterator();

		Assert.assertTrue(iterator.hasNext());
		Assert.assertSame(ARRAY[0], iterator.next());
		Assert.assertTrue(iterator.hasNext());
		Assert.assertSame(ARRAY[1], iterator.next());
		Assert.assertFalse(iterator.hasNext());

	}

	@Test(expected = UnsupportedOperationException.class)
	public void iterator_remove() {

		Source<Object> source = new ListSource<Object>(LIST);
		Iterator<Object> iterator = source.iterator();

		iterator.remove();

	}

	/* INDEXED SOURCE */

	@Test(expected = IndexOutOfBoundsException.class)
	public void get_negativeIndex() {

		IndexedSource<Object> source = new ListSource<Object>(LIST);

		source.get(-1);

	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void get_tooLargeIndex() {

		IndexedSource<Object> source = new ListSource<Object>(LIST);

		source.get(2);

	}

	@Test
	public void get_first() {

		IndexedSource<Object> source = new ListSource<Object>(LIST);

		Assert.assertSame(ARRAY[0], source.get(0));

	}

	@Test
	public void get_last() {

		IndexedSource<Object> source = new ListSource<Object>(LIST);

		Assert.assertSame(ARRAY[1], source.get(1));

	}

	@Test(expected = NoSuchElementException.class)
	public void getLast_empty() {

		IndexedSource<Object> source = new ListSource<Object>(EMPTY);

		source.getLast();

	}

	@Test
	public void getLast_nonEmpty() {

		IndexedSource<Object> source = new ListSource<Object>(LIST);

		Assert.assertEquals(ARRAY[1], source.getLast());

	}

	@Test
	public void getLast_unmatchedReference() {

		IndexedSource<Object> source = new ListSource<Object>(LIST);

		Assert.assertFalse(source.getLast(new Object()).hasValue());

	}

	@Test
	public void getLast_matchedReference() {

		IndexedSource<Object> source = new ListSource<Object>(LIST);

		Assert.assertSame(ARRAY[0], source.getLast(ARRAY[0]).getValue());

	}

	@Test(expected = IllegalArgumentException.class)
	public void getLastMatch_nullPredicate() {

		IndexedSource<Object> source = new ListSource<Object>(LIST);

		source.getLastMatch(null);

	}

	@Test
	public void getLastMatch_unmatchedPredicate() {

		IndexedSource<Object> source = new ListSource<Object>(LIST);

		Assert.assertFalse(source.getLastMatch(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return false;
			}

		}).hasValue());

	}

	@Test
	public void getLastMatch_matchedPredicate() {

		IndexedSource<Object> source = new ListSource<Object>(LIST);

		Assert.assertSame(ARRAY[0], source.getLastMatch(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return subject == ARRAY[0];
			}

		}).getValue());

	}

	@Test
	public void firstIndexOf_unmatchedReference() {

		IndexedSource<Object> source = new ListSource<Object>(LIST);

		Assert.assertFalse(source.firstIndexOf(new Object()).hasValue());

	}

	@Test
	public void firstIndexOf_matchedReference() {

		IndexedSource<Object> source = new ListSource<Object>(LIST);

		Assert.assertEquals(Integer.valueOf(1), source.firstIndexOf(ARRAY[1]).getValue());

	}

	@Test(expected = IllegalArgumentException.class)
	public void firstIndexOfMatch_nullPredicate() {

		IndexedSource<Object> source = new ListSource<Object>(LIST);

		source.firstIndexOfMatch(null);

	}

	@Test
	public void firstIndexOfMatch_unmatchedPredicate() {

		IndexedSource<Object> source = new ListSource<Object>(LIST);

		Assert.assertFalse(source.firstIndexOfMatch(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return false;
			}

		}).hasValue());

	}

	@Test
	public void firstIndexOfMatch_matchedPredicate() {

		IndexedSource<Object> source = new ListSource<Object>(LIST);

		Assert.assertEquals(Integer.valueOf(1), source.firstIndexOfMatch(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return subject == ARRAY[1];
			}

		}).getValue());

	}

	@Test
	public void lastIndexOf_unmatchedReference() {

		IndexedSource<Object> source = new ListSource<Object>(LIST);

		Assert.assertFalse(source.lastIndexOf(new Object()).hasValue());

	}

	@Test
	public void lastIndexOf_matchedReference() {

		IndexedSource<Object> source = new ListSource<Object>(LIST);

		Assert.assertEquals(Integer.valueOf(0), source.lastIndexOf(ARRAY[0]).getValue());

	}

	@Test(expected = IllegalArgumentException.class)
	public void lastIndexOfMatch_nullPredicate() {

		IndexedSource<Object> source = new ListSource<Object>(LIST);

		source.lastIndexOfMatch(null);

	}

	@Test
	public void lastIndexOfMatch_unmatchedPredicate() {

		IndexedSource<Object> source = new ListSource<Object>(LIST);

		Assert.assertFalse(source.lastIndexOfMatch(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return false;
			}

		}).hasValue());

	}

	@Test
	public void lastIndexOfMatch_matchedPredicate() {

		IndexedSource<Object> source = new ListSource<Object>(LIST);

		Assert.assertEquals(Integer.valueOf(0), source.lastIndexOfMatch(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return subject == ARRAY[0];
			}

		}).getValue());

	}

	@Test(expected = NoSuchElementException.class)
	public void isFirst_empty() {

		IndexedSource<Object> source = new ListSource<Object>(EMPTY);

		source.isFirst(new Object());

	}

	@Test
	public void isFirst_unmatchedReference() {

		IndexedSource<Object> source = new ListSource<Object>(LIST);

		Assert.assertFalse(source.isFirst(new Object()));

	}

	@Test
	public void isFirst_matchedReference() {

		IndexedSource<Object> source = new ListSource<Object>(LIST);

		Assert.assertTrue(source.isFirst(ARRAY[0]));

	}

	@Test(expected = NoSuchElementException.class)
	public void isLast_empty() {

		IndexedSource<Object> source = new ListSource<Object>(EMPTY);

		source.isLast(new Object());

	}

	@Test
	public void isLast_unmatchedReference() {

		IndexedSource<Object> source = new ListSource<Object>(LIST);

		Assert.assertFalse(source.isLast(new Object()));

	}

	@Test
	public void isLast_matchedReference() {

		IndexedSource<Object> source = new ListSource<Object>(LIST);

		Assert.assertTrue(source.isLast(ARRAY[1]));

	}

	@Test
	public void iterator_nonReverse() {

		IndexedSource<Object> source = new ListSource<Object>(LIST);
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

		IndexedSource<Object> source = new ListSource<Object>(LIST);
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

}
