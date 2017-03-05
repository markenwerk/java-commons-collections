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
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Test;

import net.markenwerk.commons.interfaces.Predicate;
import net.markenwerk.commons.iterables.ArrayIterable;
import net.markenwerk.commons.iterables.EmptyIterable;

@SuppressWarnings("javadoc")
public class CollectionSourceTests {

	private static final Object[] ARRAY = new Object[] { new Object(), new Object() };

	private static final Collection<Object> COLLECTION = Collections.unmodifiableCollection(Arrays.asList(ARRAY));

	private static final Collection<Object> EMPTY = Collections.unmodifiableCollection(new LinkedList<Object>());

	/* COLLECTION SOURCE */

	@Test(expected = IllegalArgumentException.class)
	public void create_nullCollection() {

		new CollectionSource<Object>(null);

	}

	@Test
	public void getCollection() {

		CollectionSource<Object> source = new CollectionSource<Object>(COLLECTION);

		Assert.assertSame(COLLECTION, source.getCollection());

	}

	/* SOURCE */

	@Test
	public void isEmpty_empty() {

		Source<Object> source = new CollectionSource<Object>(EMPTY);

		Assert.assertTrue(source.isEmpty());

	}

	@Test
	public void isEmpty_nonEmpty() {

		Source<Object> source = new CollectionSource<Object>(COLLECTION);

		Assert.assertFalse(source.isEmpty());

	}

	@Test
	public void size_empty() {

		Source<Object> source = new CollectionSource<Object>(EMPTY);

		Assert.assertEquals(0, source.size());

	}

	@Test
	public void size_nonEmpty() {

		Source<Object> source = new CollectionSource<Object>(COLLECTION);

		Assert.assertEquals(2, source.size());

	}

	@Test(expected = NoSuchElementException.class)
	public void getFirst_empty() {

		Source<Object> source = new CollectionSource<Object>(EMPTY);

		source.getFirst();

	}

	@Test
	public void getFirst_nonEmpty() {

		Source<Object> source = new CollectionSource<Object>(COLLECTION);

		Assert.assertEquals(ARRAY[0], source.getFirst());

	}

	@Test
	public void getFirst_unmatchedReference() {

		Source<Object> source = new CollectionSource<Object>(COLLECTION);

		Assert.assertFalse(source.getFirst(new Object()).hasValue());

	}

	@Test
	public void getFirst_matchedReference() {

		Source<Object> source = new CollectionSource<Object>(COLLECTION);

		Assert.assertSame(ARRAY[1], source.getFirst(ARRAY[1]).getValue());

	}

	@Test(expected = IllegalArgumentException.class)
	public void getFirstMatch_nullPredicate() {

		Source<Object> source = new CollectionSource<Object>(COLLECTION);

		source.getFirstMatch(null);

	}

	@Test
	public void getFirstMatch_unmatchedPredicate() {

		Source<Object> source = new CollectionSource<Object>(COLLECTION);

		Assert.assertFalse(source.getFirstMatch(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return false;
			}

		}).hasValue());

	}

	@Test
	public void getFirstMatch_matchedPredicate() {

		Source<Object> source = new CollectionSource<Object>(COLLECTION);

		Assert.assertSame(ARRAY[1], source.getFirstMatch(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return subject == ARRAY[1];
			}

		}).getValue());

	}

	@Test
	public void getAll_unmatchedReference() {

		Source<Object> source = new CollectionSource<Object>(COLLECTION);

		Assert.assertTrue(source.getAll(new Object()).isEmpty());

	}

	@Test
	public void getAll_matchedReference() {

		Source<Object> source = new CollectionSource<Object>(COLLECTION);

		Source<Object> matches = source.getAll(ARRAY[1]);
		Assert.assertSame(1, matches.size());
		Assert.assertTrue(matches.containsAll(ARRAY[1]));

	}

	@Test(expected = IllegalArgumentException.class)
	public void getAllMatches_nullPredicate() {

		Source<Object> source = new CollectionSource<Object>(COLLECTION);

		source.getAllMatches(null);

	}

	@Test
	public void getAllMatches_unmatchedPredicate() {

		Source<Object> source = new CollectionSource<Object>(COLLECTION);

		Assert.assertTrue(source.getAllMatches(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return false;
			}

		}).isEmpty());

	}

	@Test
	public void getAllMatches_matchedPredicate() {

		Source<Object> source = new CollectionSource<Object>(COLLECTION);

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

		Source<Object> source = new CollectionSource<Object>(COLLECTION);

		Assert.assertFalse(source.contains(null));

	}

	@Test
	public void contains_containedNull() {

		Source<Object> source = new CollectionSource<Object>(Arrays.asList(ARRAY[0], ARRAY[1], null));

		Assert.assertTrue(source.contains(null));

	}

	@Test
	public void contains_uncontainedObject() {

		Source<Object> source = new CollectionSource<Object>(COLLECTION);

		Assert.assertFalse(source.contains(new Object()));

	}

	@Test
	public void contains_containedObject() {

		Source<Object> source = new CollectionSource<Object>(COLLECTION);

		Assert.assertTrue(source.containsAll(ARRAY[1], ARRAY[0]));

	}

	@Test(expected = IllegalArgumentException.class)
	public void containsAll_nullArray() {

		new CollectionSource<Object>(EMPTY).containsAll((Object[]) null);
	}

	@Test
	public void containsAll_emptyArray() {

		Source<Object> source = new CollectionSource<Object>(COLLECTION);

		Assert.assertTrue(source.containsAll());

	}

	@Test
	public void containsAll_allContainedArray() {

		Source<Object> source = new CollectionSource<Object>(COLLECTION);

		Assert.assertTrue(source.containsAll(ARRAY));

	}

	@Test
	public void containsAll_notAllContainedArray() {

		Source<Object> source = new CollectionSource<Object>(COLLECTION);

		Assert.assertFalse(source.containsAll(ARRAY[0], new Object()));

	}

	@Test(expected = IllegalArgumentException.class)
	public void containsAll_nullIterable() {

		new CollectionSource<Object>(EMPTY).containsAll((Iterable<Object>) null);
	}

	@Test
	public void containsAll_emptyIterable() {

		Source<Object> source = new CollectionSource<Object>(COLLECTION);

		Assert.assertTrue(source.containsAll(new EmptyIterable<Object>()));

	}

	@Test
	public void containsAll_allContainedIterable() {

		Source<Object> source = new CollectionSource<Object>(COLLECTION);

		Assert.assertTrue(source.containsAll(new ArrayIterable<Object>(ARRAY)));

	}

	@Test
	public void containsAll_notAllContainedIterable() {

		Source<Object> source = new CollectionSource<Object>(COLLECTION);

		Assert.assertFalse(source.containsAll(new ArrayIterable<Object>(ARRAY[0], new Object())));

	}

	@Test
	public void iterator_iterate() {

		Source<Object> source = new CollectionSource<Object>(COLLECTION);
		Iterator<Object> iterator = source.iterator();

		Assert.assertTrue(iterator.hasNext());
		Assert.assertSame(ARRAY[0], iterator.next());
		Assert.assertTrue(iterator.hasNext());
		Assert.assertSame(ARRAY[1], iterator.next());
		Assert.assertFalse(iterator.hasNext());

	}

	@Test(expected = UnsupportedOperationException.class)
	public void iterator_remove() {

		Source<Object> source = new CollectionSource<Object>(COLLECTION);
		Iterator<Object> iterator = source.iterator();

		iterator.remove();

	}

}
