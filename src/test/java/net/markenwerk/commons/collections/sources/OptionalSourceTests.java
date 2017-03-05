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

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Test;

import net.markenwerk.commons.datastructures.Optional;
import net.markenwerk.commons.interfaces.Predicate;
import net.markenwerk.commons.iterables.ArrayIterable;
import net.markenwerk.commons.iterables.EmptyIterable;

@SuppressWarnings("javadoc")
public class OptionalSourceTests {

	private static final Object OBJECT = new Object();

	private static final Optional<Object> OPTIONAL = new Optional<Object>(OBJECT);

	private static final Optional<Object> EMPTY = new Optional<Object>();

	/* OPTIONAL SOURCE */
	
	@Test(expected = IllegalArgumentException.class)
	public void create_nullArray() {

		new OptionalSource<Object>(null);

	}

	@Test
	public void getOptional() {

		OptionalSource<Object> source = new OptionalSource<Object>(OPTIONAL);

		Assert.assertSame(OPTIONAL, source.getOptional());

	}

	/* SOURCE */
	
	@Test
	public void isEmpty_empty() {

		Source<Object> source = new OptionalSource<Object>(EMPTY);

		Assert.assertTrue(source.isEmpty());

	}

	@Test
	public void isEmpty_nonEmpty() {

		Source<Object> source = new OptionalSource<Object>(OPTIONAL);

		Assert.assertFalse(source.isEmpty());

	}

	@Test
	public void size_empty() {

		Source<Object> source = new OptionalSource<Object>(EMPTY);

		Assert.assertEquals(0, source.size());

	}

	@Test
	public void size_nonEmpty() {

		Source<Object> source = new OptionalSource<Object>(OPTIONAL);

		Assert.assertEquals(1, source.size());

	}

	@Test(expected = NoSuchElementException.class)
	public void getFirst_empty() {

		Source<Object> source = new OptionalSource<Object>(EMPTY);

		source.getFirst();

	}

	@Test
	public void getFirst_nonEmpty() {

		Source<Object> source = new OptionalSource<Object>(OPTIONAL);

		Assert.assertEquals(OBJECT, source.getFirst());

	}

	@Test
	public void getFirst_unmatchedReference() {

		Source<Object> source = new OptionalSource<Object>(OPTIONAL);

		Assert.assertFalse(source.getFirst(new Object()).hasValue());

	}

	@Test
	public void getFirst_matchedReference() {

		Source<Object> source = new OptionalSource<Object>(OPTIONAL);

		Assert.assertSame(OBJECT, source.getFirst(OBJECT).getValue());

	}

	@Test(expected = IllegalArgumentException.class)
	public void getFirstMatch_nullPredicate() {

		Source<Object> source = new OptionalSource<Object>(OPTIONAL);

		source.getFirstMatch(null);

	}

	@Test
	public void getFirstMatch_unmatchedPredicate() {

		Source<Object> source = new OptionalSource<Object>(OPTIONAL);

		Assert.assertFalse(source.getFirstMatch(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return false;
			}

		}).hasValue());

	}

	@Test
	public void getFirstMatch_matchedPredicate() {

		Source<Object> source = new OptionalSource<Object>(OPTIONAL);

		Assert.assertSame(OBJECT, source.getFirstMatch(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return subject == OBJECT;
			}

		}).getValue());

	}

	@Test
	public void getAll_unmatchedReference() {

		Source<Object> source = new OptionalSource<Object>(OPTIONAL);

		Assert.assertTrue(source.getAll(new Object()).isEmpty());

	}

	@Test
	public void getAll_matchedReference() {

		Source<Object> source = new OptionalSource<Object>(OPTIONAL);

		Source<Object> matches = source.getAll(OBJECT);
		Assert.assertSame(1, matches.size());
		Assert.assertTrue(matches.containsAll(OBJECT));

	}

	@Test(expected = IllegalArgumentException.class)
	public void getAllMatches_nullPredicate() {

		Source<Object> source = new OptionalSource<Object>(OPTIONAL);

		source.getAllMatches(null);

	}

	@Test
	public void getAllMatches_unmatchedPredicate() {

		Source<Object> source = new OptionalSource<Object>(OPTIONAL);

		Assert.assertTrue(source.getAllMatches(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return false;
			}

		}).isEmpty());

	}

	@Test
	public void getAllMatches_matchedPredicate() {

		Source<Object> source = new OptionalSource<Object>(OPTIONAL);

		Source<Object> matches = source.getAllMatches(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return subject == OBJECT;
			}

		});
		Assert.assertSame(1, matches.size());
		Assert.assertTrue(matches.containsAll(OBJECT));

	}

	@Test
	public void contains_uncontainedNull() {

		Source<Object> source = new OptionalSource<Object>(OPTIONAL);

		Assert.assertFalse(source.contains((Object) null));

	}

	@Test
	public void contains_containedNull() {

		Source<Object> source = new OptionalSource<Object>(new Optional<Object>(null));

		Assert.assertTrue(source.contains(null));

	}

	@Test
	public void contains_uncontainedObject() {

		Source<Object> source = new OptionalSource<Object>(OPTIONAL);

		Assert.assertFalse(source.contains(new Object()));

	}

	@Test
	public void contains_containedObject() {

		Source<Object> source = new OptionalSource<Object>(OPTIONAL);

		Assert.assertTrue(source.contains(OBJECT));

	}

	@Test(expected = IllegalArgumentException.class)
	public void containsAll_nullArray() {

		new OptionalSource<Object>(EMPTY).containsAll((Object[]) null);
	}

	@Test
	public void containsAll_emptyArray() {

		Source<Object> source = new OptionalSource<Object>(OPTIONAL);

		Assert.assertTrue(source.containsAll());

	}

	@Test
	public void containsAll_allContainedArray() {

		Source<Object> source = new OptionalSource<Object>(OPTIONAL);

		Assert.assertTrue(source.containsAll(OBJECT));

	}

	@Test
	public void containsAll_notAllContainedArray() {

		Source<Object> source = new OptionalSource<Object>(OPTIONAL);

		Assert.assertFalse(source.containsAll(OBJECT, new Object()));

	}

	@Test(expected = IllegalArgumentException.class)
	public void containsAll_nullIterable() {

		new OptionalSource<Object>(EMPTY).containsAll((Iterable<Object>) null);
	}

	@Test
	public void containsAll_emptyIterable() {

		Source<Object> source = new OptionalSource<Object>(OPTIONAL);

		Assert.assertTrue(source.containsAll(new EmptyIterable<Object>()));

	}

	@Test
	public void containsAll_allContainedIterable() {

		Source<Object> source = new OptionalSource<Object>(OPTIONAL);

		Assert.assertTrue(source.containsAll(new ArrayIterable<Object>(OBJECT)));

	}

	@Test
	public void containsAll_notAllContainedIterable() {

		Source<Object> source = new OptionalSource<Object>(OPTIONAL);

		Assert.assertFalse(source.containsAll(new ArrayIterable<Object>(OBJECT, new Object())));

	}

	@Test
	public void iterator_iterate() {

		Source<Object> source = new OptionalSource<Object>(OPTIONAL);
		Iterator<Object> iterator = source.iterator();

		Assert.assertTrue(iterator.hasNext());
		Assert.assertSame(OBJECT, iterator.next());
		Assert.assertFalse(iterator.hasNext());

	}

	@Test(expected = UnsupportedOperationException.class)
	public void iterator_remove() {

		Source<Object> source = new OptionalSource<Object>(OPTIONAL);
		Iterator<Object> iterator = source.iterator();

		iterator.remove();

	}

}
