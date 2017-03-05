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

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import net.markenwerk.commons.interfaces.Predicate;
import net.markenwerk.commons.iterables.ArrayIterable;
import net.markenwerk.commons.iterables.EmptyIterable;

@SuppressWarnings("javadoc")
public class MapValueSourceTests {

	private static final Object[] ARRAY = new Object[] { new Object(), new Object() };

	private static Map<Object, Object> MAP;

	static {

		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put(new Object(), ARRAY[0]);
		map.put(new Object(), ARRAY[1]);

		MAP = Collections.unmodifiableMap(map);

	}

	private static Map<Object, Object> EMPTY = Collections.unmodifiableMap(new HashMap<Object, Object>());

	/* MAP VALUE SOURCE */

	@Test(expected = IllegalArgumentException.class)
	public void create_nullArray() {

		new MapValueSource<Object>(null);

	}

	@Test
	public void getMap() {

		MapValueSource<Object> source = new MapValueSource<Object>(MAP);

		Assert.assertSame(MAP, source.getMap());

	}

	/* SOURCE */

	@Test
	public void isEmpty_empty() {

		Source<Object> source = new MapValueSource<Object>(EMPTY);

		Assert.assertTrue(source.isEmpty());

	}

	@Test
	public void isEmpty_nonEmpty() {

		Source<Object> source = new MapValueSource<Object>(MAP);

		Assert.assertFalse(source.isEmpty());

	}

	@Test
	public void size_empty() {

		Source<Object> source = new MapValueSource<Object>(EMPTY);

		Assert.assertEquals(0, source.size());

	}

	@Test
	public void size_nonEmpty() {

		Source<Object> source = new MapValueSource<Object>(MAP);

		Assert.assertEquals(2, source.size());

	}

	@Test(expected = NoSuchElementException.class)
	public void getFirst_empty() {

		Source<Object> source = new MapValueSource<Object>(EMPTY);

		source.getFirst();

	}

	@Test
	public void getFirst_nonEmpty() {

		Source<Object> source = new MapValueSource<Object>(MAP);

		Assert.assertTrue(MAP.containsValue(source.getFirst()));

	}

	@Test
	public void getFirst_unmatchedReference() {

		Source<Object> source = new MapValueSource<Object>(MAP);

		Assert.assertFalse(source.getFirst(new Object()).hasValue());

	}

	@Test
	public void getFirst_matchedReference() {

		Source<Object> source = new MapValueSource<Object>(MAP);

		Assert.assertSame(ARRAY[1], source.getFirst(ARRAY[1]).getValue());

	}

	@Test(expected = IllegalArgumentException.class)
	public void getFirstMatch_nullPredicate() {

		Source<Object> source = new MapValueSource<Object>(MAP);

		source.getFirstMatch(null);

	}

	@Test
	public void getFirstMatch_unmatchedPredicate() {

		Source<Object> source = new MapValueSource<Object>(MAP);

		Assert.assertFalse(source.getFirstMatch(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return false;
			}

		}).hasValue());

	}

	@Test
	public void getFirstMatch_matchedPredicate() {

		Source<Object> source = new MapValueSource<Object>(MAP);

		Assert.assertSame(ARRAY[1], source.getFirstMatch(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return subject == ARRAY[1];
			}

		}).getValue());

	}

	@Test
	public void getAll_unmatchedReference() {

		Source<Object> source = new MapValueSource<Object>(MAP);

		Assert.assertTrue(source.getAll(new Object()).isEmpty());

	}

	@Test
	public void getAll_matchedReference() {

		Source<Object> source = new MapValueSource<Object>(MAP);

		Source<Object> matches = source.getAll(ARRAY[1]);
		Assert.assertSame(1, matches.size());
		Assert.assertTrue(matches.containsAll(ARRAY[1]));

	}

	@Test(expected = IllegalArgumentException.class)
	public void getAllMatches_nullPredicate() {

		Source<Object> source = new MapValueSource<Object>(MAP);

		source.getAllMatches(null);

	}

	@Test
	public void getAllMatches_unmatchedPredicate() {

		Source<Object> source = new MapValueSource<Object>(MAP);

		Assert.assertTrue(source.getAllMatches(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return false;
			}

		}).isEmpty());

	}

	@Test
	public void getAllMatches_matchedPredicate() {

		Source<Object> source = new MapValueSource<Object>(MAP);

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

		Source<Object> source = new MapValueSource<Object>(MAP);

		Assert.assertFalse(source.contains(null));

	}

	@Test
	public void contains_containedNull() {

		Map<Object, Object> map = new HashMap<Object, Object>(MAP);
		map.put(new Object(), null);

		Source<Object> source = new MapValueSource<Object>(map);

		Assert.assertTrue(source.contains(null));

	}

	@Test
	public void contains_uncontainedObject() {

		Source<Object> source = new MapValueSource<Object>(MAP);

		Assert.assertFalse(source.contains(new Object()));

	}

	@Test
	public void contains_containedObject() {

		Source<Object> source = new MapValueSource<Object>(MAP);

		Assert.assertTrue(source.containsAll(ARRAY[1], ARRAY[0]));

	}

	@Test(expected = IllegalArgumentException.class)
	public void containsAll_nullArray() {

		new MapValueSource<Object>(EMPTY).containsAll((Object[]) null);
	}

	@Test
	public void containsAll_emptyArray() {

		Source<Object> source = new MapValueSource<Object>(MAP);

		Assert.assertTrue(source.containsAll());

	}

	@Test
	public void containsAll_allContainedArray() {

		Source<Object> source = new MapValueSource<Object>(MAP);

		Assert.assertTrue(source.containsAll(ARRAY));

	}

	@Test
	public void containsAll_notAllContainedArray() {

		Source<Object> source = new MapValueSource<Object>(MAP);

		Assert.assertFalse(source.containsAll(ARRAY[0], new Object()));

	}

	@Test(expected = IllegalArgumentException.class)
	public void containsAll_nullIterable() {

		new MapValueSource<Object>(EMPTY).containsAll((Iterable<Object>) null);
	}

	@Test
	public void containsAll_emptyIterable() {

		Source<Object> source = new MapValueSource<Object>(MAP);

		Assert.assertTrue(source.containsAll(new EmptyIterable<Object>()));

	}

	@Test
	public void containsAll_allContainedIterable() {

		Source<Object> source = new MapValueSource<Object>(MAP);

		Assert.assertTrue(source.containsAll(new ArrayIterable<Object>(ARRAY)));

	}

	@Test
	public void containsAll_notAllContainedIterable() {

		Source<Object> source = new MapValueSource<Object>(MAP);

		Assert.assertFalse(source.containsAll(new ArrayIterable<Object>(ARRAY[0], new Object())));

	}

	@Test
	public void iterator_iterate() {

		Source<Object> source = new MapValueSource<Object>(MAP);
		Iterator<Object> iterator = source.iterator();

		Set<Object> values = new HashSet<Object>(MAP.values());

		Assert.assertTrue(iterator.hasNext());
		Assert.assertTrue(values.remove(iterator.next()));
		Assert.assertTrue(iterator.hasNext());
		Assert.assertTrue(values.remove(iterator.next()));
		Assert.assertFalse(iterator.hasNext());

	}

	@Test(expected = UnsupportedOperationException.class)
	public void iterator_remove() {

		Source<Object> source = new MapValueSource<Object>(MAP);
		Iterator<Object> iterator = source.iterator();

		iterator.remove();

	}

}
