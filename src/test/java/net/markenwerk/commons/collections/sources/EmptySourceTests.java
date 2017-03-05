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

import net.markenwerk.commons.interfaces.Predicate;
import net.markenwerk.commons.iterables.ArrayIterable;
import net.markenwerk.commons.iterables.EmptyIterable;

@SuppressWarnings("javadoc")
public class EmptySourceTests {

	/* SOURCE */
	
	@Test
	public void isEmpty_empty() {

		Source<Object> source = new EmptySource<Object>();

		Assert.assertTrue(source.isEmpty());

	}

	@Test
	public void size_empty() {

		Source<Object> source = new EmptySource<Object>();

		Assert.assertEquals(0, source.size());

	}

	@Test(expected = NoSuchElementException.class)
	public void getFirst_empty() {

		Source<Object> source = new EmptySource<Object>();

		source.getFirst();

	}

	@Test(expected = IllegalArgumentException.class)
	public void getFirstMatch_nullPredicate() {

		Source<Object> source = new EmptySource<Object>();

		source.getFirstMatch(null);

	}

	@Test
	public void getFirstMatch_unmatchedReference() {

		Source<Object> source = new EmptySource<Object>();

		Assert.assertFalse(source.getFirst(new Object()).hasValue());

	}

	@Test
	public void getFirstMatch_unmatchedPredicate() {

		Source<Object> source = new EmptySource<Object>();

		Assert.assertFalse(source.getFirstMatch(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return false;
			}

		}).hasValue());

	}

	@Test(expected = IllegalArgumentException.class)
	public void getAllMatches_nullPredicate() {

		Source<Object> source = new EmptySource<Object>();

		source.getAllMatches(null);

	}

	@Test
	public void getAllMatches_unmatchedReference() {

		Source<Object> source = new EmptySource<Object>();

		Assert.assertTrue(source.getAll(new Object()).isEmpty());

	}

	@Test
	public void getAllMatches_unmatchedPredicate() {

		Source<Object> source = new EmptySource<Object>();

		Assert.assertTrue(source.getAllMatches(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return false;
			}

		}).isEmpty());

	}

	@Test
	public void contains_uncontainedNull() {

		Source<Object> source = new EmptySource<Object>();

		Assert.assertFalse(source.contains(null));

	}

	@Test
	public void contains_uncontainedObject() {

		Source<Object> source = new EmptySource<Object>();

		Assert.assertFalse(source.contains(new Object()));

	}

	@Test(expected = IllegalArgumentException.class)
	public void containsAll_nullArray() {

		new EmptySource<Object>().containsAll((Object[]) null);
	}

	@Test
	public void containsAll_empthArray() {

		Source<Object> source = new EmptySource<Object>();

		Assert.assertTrue(source.containsAll());

	}

	@Test
	public void containsAll_notAllContainedArray() {

		Source<Object> source = new EmptySource<Object>();

		Assert.assertFalse(source.containsAll(new Object()));

	}

	@Test(expected = IllegalArgumentException.class)
	public void containsAll_nullIterable() {

		new EmptySource<Object>().containsAll((Iterable<Object>) null);
	}

	@Test
	public void containsAll_emptyIterable() {

		Source<Object> source = new EmptySource<Object>();

		Assert.assertTrue(source.containsAll(new EmptyIterable<Object>()));

	}

	@Test
	public void containsAll_notAllContainedIterable() {

		Source<Object> source = new EmptySource<Object>();

		Assert.assertFalse(source.containsAll(new ArrayIterable<Object>(new Object())));

	}

	@Test
	public void iterator_iterate() {

		Source<Object> source = new EmptySource<Object>();
		Iterator<Object> iterator = source.iterator();

		Assert.assertFalse(iterator.hasNext());

	}

}
