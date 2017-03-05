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
package net.markenwerk.commons.collections.stacks;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Test;

import net.markenwerk.commons.collections.Nullity;
import net.markenwerk.commons.collections.sources.Source;
import net.markenwerk.commons.interfaces.Predicate;
import net.markenwerk.commons.iterables.ArrayIterable;
import net.markenwerk.commons.iterables.EmptyIterable;

@SuppressWarnings("javadoc")
public class LinkedStackTests {

	private static final Object[] ARRAY = new Object[] { new Object(), new Object() };

	/* LINKED STACK */

	@Test(expected = IllegalArgumentException.class)
	public void create_nullNullity() {

		new LinkedStack<Object>((Nullity) null);

	}


	@Test
	public void getNullity() {

		LinkedStack<Object> source = new LinkedStack<Object>(Nullity.ALLOW);

		Assert.assertSame(Nullity.ALLOW, source.getNullity());

	}

	/* SOURCE */

	@Test
	public void isEmpty_empty() {

		Source<Object> source = new LinkedStack<Object>();

		Assert.assertTrue(source.isEmpty());

	}

	@Test
	public void isEmpty_nonEmpty() {

		Source<Object> source = new LinkedStack<Object>().pushAll(ARRAY);

		Assert.assertFalse(source.isEmpty());

	}

	@Test
	public void size_empty() {

		Source<Object> source = new LinkedStack<Object>();

		Assert.assertEquals(0, source.size());

	}

	@Test
	public void size_nonEmpty() {

		Source<Object> source = new LinkedStack<Object>().pushAll(ARRAY);

		Assert.assertEquals(2, source.size());

	}

	@Test(expected = NoSuchElementException.class)
	public void getFirst_empty() {

		Source<Object> source = new LinkedStack<Object>();

		source.getFirst();

	}

	@Test
	public void getFirst_nonEmpty() {

		Source<Object> source = new LinkedStack<Object>().pushAll(ARRAY);

		Assert.assertEquals(ARRAY[1], source.getFirst());

	}

	@Test
	public void getFirst_unmatchedReference() {

		Source<Object> source = new LinkedStack<Object>().pushAll(ARRAY);

		Assert.assertFalse(source.getFirst(new Object()).hasValue());

	}

	@Test
	public void getFirst_matchedReference() {

		Source<Object> source = new LinkedStack<Object>().pushAll(ARRAY);

		Assert.assertSame(ARRAY[1], source.getFirst(ARRAY[1]).getValue());

	}

	@Test(expected = IllegalArgumentException.class)
	public void getFirstMatch_nullPredicate() {

		Source<Object> source = new LinkedStack<Object>().pushAll(ARRAY);

		source.getFirstMatch(null);

	}

	@Test
	public void getFirstMatch_unmatchedPredicate() {

		Source<Object> source = new LinkedStack<Object>().pushAll(ARRAY);

		Assert.assertFalse(source.getFirstMatch(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return false;
			}

		}).hasValue());

	}

	@Test
	public void getFirstMatch_matchedPredicate() {

		Source<Object> source = new LinkedStack<Object>().pushAll(ARRAY);

		Assert.assertSame(ARRAY[1], source.getFirstMatch(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return subject == ARRAY[1];
			}

		}).getValue());

	}

	@Test
	public void getAll_unmatchedReference() {

		Source<Object> source = new LinkedStack<Object>().pushAll(ARRAY);

		Assert.assertTrue(source.getAll(new Object()).isEmpty());

	}

	@Test
	public void getAll_matchedReference() {

		Source<Object> source = new LinkedStack<Object>().pushAll(ARRAY);

		Source<Object> matches = source.getAll(ARRAY[1]);

		Assert.assertSame(1, matches.size());
		Assert.assertTrue(matches.containsAll(ARRAY[1]));

	}

	@Test(expected = IllegalArgumentException.class)
	public void getAllMatches_nullPredicate() {

		Source<Object> source = new LinkedStack<Object>().pushAll(ARRAY);

		source.getAllMatches(null);

	}

	@Test
	public void getAllMatches_unmatchedPredicate() {

		Source<Object> source = new LinkedStack<Object>().pushAll(ARRAY);

		Assert.assertTrue(source.getAllMatches(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return false;
			}

		}).isEmpty());

	}

	@Test
	public void getAllMatches_matchedPredicate() {

		Source<Object> source = new LinkedStack<Object>().pushAll(ARRAY);

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

		Source<Object> source = new LinkedStack<Object>().pushAll(ARRAY);

		Assert.assertFalse(source.contains(null));

	}

	@Test
	public void contains_containedNull() {

		Source<Object> source = new LinkedStack<Object>().push(null).pushAll(ARRAY);

		Assert.assertTrue(source.contains(null));

	}

	@Test
	public void contains_uncontainedObject() {

		Source<Object> source = new LinkedStack<Object>().pushAll(ARRAY);

		Assert.assertFalse(source.contains(new Object()));

	}

	@Test
	public void contains_containedObject() {

		Source<Object> source = new LinkedStack<Object>().pushAll(ARRAY);

		Assert.assertTrue(source.containsAll(ARRAY[1], ARRAY[0]));

	}

	@Test(expected = IllegalArgumentException.class)
	public void containsAll_nullArray() {

		new LinkedStack<Object>().containsAll((Object[]) null);
	}

	@Test
	public void containsAll_emptyArray() {

		Source<Object> source = new LinkedStack<Object>().pushAll(ARRAY);

		Assert.assertTrue(source.containsAll());

	}

	@Test
	public void containsAll_allContainedArray() {

		Source<Object> source = new LinkedStack<Object>().pushAll(ARRAY);

		Assert.assertTrue(source.containsAll(ARRAY));

	}

	@Test
	public void containsAll_notAllContainedArray() {

		Source<Object> source = new LinkedStack<Object>().pushAll(ARRAY);

		Assert.assertFalse(source.containsAll(ARRAY[0], new Object()));

	}

	@Test(expected = IllegalArgumentException.class)
	public void containsAll_nullIterable() {

		new LinkedStack<Object>().containsAll((Iterable<Object>) null);
	}

	@Test
	public void containsAll_emptyIterable() {

		Source<Object> source = new LinkedStack<Object>().pushAll(ARRAY);

		Assert.assertTrue(source.containsAll(new EmptyIterable<Object>()));

	}

	@Test
	public void containsAll_allContainedIterable() {

		Source<Object> source = new LinkedStack<Object>().pushAll(ARRAY);

		Assert.assertTrue(source.containsAll(new ArrayIterable<Object>(ARRAY)));

	}

	@Test
	public void containsAll_notAllContainedIterable() {

		Source<Object> source = new LinkedStack<Object>().pushAll(ARRAY);

		Assert.assertFalse(source.containsAll(new ArrayIterable<Object>(ARRAY[0], new Object())));

	}

	@Test
	public void iterator_iterate() {

		Source<Object> source = new LinkedStack<Object>().pushAll(ARRAY);
		Iterator<Object> iterator = source.iterator();

		Assert.assertTrue(iterator.hasNext());
		Assert.assertSame(ARRAY[1], iterator.next());
		Assert.assertTrue(iterator.hasNext());
		Assert.assertSame(ARRAY[0], iterator.next());
		Assert.assertFalse(iterator.hasNext());

	}

	@Test(expected = UnsupportedOperationException.class)
	public void iterator_remove() {

		Source<Object> source = new LinkedStack<Object>().push(new Object());

		Iterator<Object> iterator = source.iterator();

		iterator.remove();

	}

	@Test(expected = ConcurrentModificationException.class)
	public void iterator_failFast() {

		Stack<Object> stack = new LinkedStack<Object>();
		Iterator<Object> iterator = stack.iterator();

		stack.push(new Object());

		iterator.next();

	}

	/* STACK */

	@Test
	public void push_once() {

		Stack<Object> stack = new LinkedStack<Object>();

		stack.push(new Object());

		Assert.assertFalse(stack.isEmpty());
		Assert.assertEquals(1, stack.size());

	}

	@Test
	public void push_twice() {

		Stack<Object> stack = new LinkedStack<Object>();

		stack.push(new Object());
		stack.push(new Object());

		Assert.assertFalse(stack.isEmpty());
		Assert.assertEquals(2, stack.size());

	}

	@Test
	public void push_allowNull() {

		Stack<Object> stack = new LinkedStack<Object>(Nullity.ALLOW);

		stack.push(null);

		Assert.assertFalse(stack.isEmpty());
		Assert.assertNull(stack.getFirst());

	}

	@Test
	public void push_ignoreNull() {

		Stack<Object> stack = new LinkedStack<Object>(Nullity.IGNORE);

		stack.push(null);

		Assert.assertTrue(stack.isEmpty());

	}

	@Test(expected = IllegalArgumentException.class)
	public void push_rejectNull() {

		Stack<Object> stack = new LinkedStack<Object>(Nullity.REJECT);

		stack.push(null);

	}

	@Test(expected = IllegalArgumentException.class)
	public void pushAll_nullArray() {

		Stack<Object> stack = new LinkedStack<Object>();

		stack.pushAll((Object[]) null);

	}

	@Test
	public void pushAll_array() {

		Stack<Object> stack = new LinkedStack<Object>();

		stack.pushAll(new Object(), new Object());

		Assert.assertFalse(stack.isEmpty());
		Assert.assertEquals(2, stack.size());

	}

	@Test(expected = IllegalArgumentException.class)
	public void pushAll_nullIterable() {

		Stack<Object> stack = new LinkedStack<Object>();

		stack.pushAll((Iterable<Object>) null);

	}

	@Test
	public void pushAll_iterable() {

		Stack<Object> stack = new LinkedStack<Object>();

		stack.pushAll(new ArrayIterable<Object>(new Object(), new Object()));

		Assert.assertFalse(stack.isEmpty());
		Assert.assertEquals(2, stack.size());

	}

	@Test
	public void pushAll_allowNull() {

		Stack<Object> stack = new LinkedStack<Object>(Nullity.ALLOW);

		stack.pushAll((Object) null);

		Assert.assertFalse(stack.isEmpty());
		Assert.assertNull(stack.getFirst());

	}

	@Test
	public void pushAll_ignoreNull() {

		Stack<Object> stack = new LinkedStack<Object>(Nullity.IGNORE);

		stack.pushAll((Object) null);

		Assert.assertTrue(stack.isEmpty());

	}

	@Test(expected = IllegalArgumentException.class)
	public void pushAll_rejectNull() {

		Stack<Object> stack = new LinkedStack<Object>(Nullity.REJECT);

		stack.pushAll((Object) null);

	}

	@Test(expected = NoSuchElementException.class)
	public void set_empty() {

		Stack<Object> stack = new LinkedStack<Object>();

		stack.replace(new Object());

	}

	@Test
	public void replace() {

		Object object = new Object();
		Stack<Object> stack = new LinkedStack<Object>();

		stack.push(new Object());
		stack.replace(object);

		Assert.assertEquals(object, stack.getFirst());
		Assert.assertEquals(1, stack.size());

	}

	@Test
	public void replace_allowNull() {

		Stack<Object> stack = new LinkedStack<Object>(Nullity.ALLOW);

		stack.push(new Object());
		stack.replace(null);

		Assert.assertFalse(stack.isEmpty());
		Assert.assertNull(stack.getFirst());

	}

	@Test(expected = IllegalArgumentException.class)
	public void replace_ignoreNull() {

		Stack<Object> stack = new LinkedStack<Object>(Nullity.IGNORE);

		stack.push(new Object());
		stack.replace(null);

	}

	@Test(expected = IllegalArgumentException.class)
	public void replace_rejectNull() {

		Stack<Object> stack = new LinkedStack<Object>(Nullity.REJECT);

		stack.push(new Object());
		stack.replace(null);
	}

	@Test(expected = NoSuchElementException.class)
	public void pop_empty() {

		Stack<Object> stack = new LinkedStack<Object>();

		stack.pop();

	}

	@Test
	public void pop_once() {

		Object object = new Object();
		Stack<Object> stack = new LinkedStack<Object>();

		stack.push(object);

		Assert.assertEquals(object, stack.pop());
		Assert.assertTrue(stack.isEmpty());

	}

	@Test
	public void pop_twice() {

		Object first = new Object();
		Object second = new Object();
		Stack<Object> stack = new LinkedStack<Object>();

		stack.push(first);
		stack.push(second);

		Assert.assertEquals(second, stack.pop());
		Assert.assertEquals(first, stack.pop());
		Assert.assertTrue(stack.isEmpty());

	}

	@Test(expected = IllegalArgumentException.class)
	public void popAll_negativeIndex() {

		Object object = new Object();
		Stack<Object> stack = new LinkedStack<Object>();

		stack.push(object);
		stack.popAll(-1);

	}

	@Test(expected = IllegalArgumentException.class)
	public void popAll_tooLargeIndex() {

		Object object = new Object();
		Stack<Object> stack = new LinkedStack<Object>();

		stack.push(object);
		stack.popAll(2);

	}

	@Test
	public void popAll_many() {

		Object first = new Object();
		Object second = new Object();
		Stack<Object> stack = new LinkedStack<Object>();

		stack.push(first);
		stack.push(second);

		Iterator<Object> iterator = stack.popAll(2).iterator();

		Assert.assertTrue(stack.isEmpty());
		Assert.assertTrue(iterator.hasNext());
		Assert.assertSame(second, iterator.next());
		Assert.assertTrue(iterator.hasNext());
		Assert.assertSame(first, iterator.next());
		Assert.assertFalse(iterator.hasNext());

	}

	@Test(expected = IllegalArgumentException.class)
	public void popAll_nullPredicate() {

		Object object = new Object();
		Stack<Object> stack = new LinkedStack<Object>();

		stack.push(object);
		stack.popAllMatches(null);

	}

	@Test
	public void popAll_perdicate() {

		final Object first = new Object();
		final Object second = new Object();
		Stack<Object> stack = new LinkedStack<Object>();

		stack.push(first);
		stack.push(second);

		Iterator<Object> iterator = stack.popAllMatches(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return second == subject;
			}
		}).iterator();

		Assert.assertEquals(1, stack.size());
		Assert.assertTrue(iterator.hasNext());
		Assert.assertSame(second, iterator.next());
		Assert.assertFalse(iterator.hasNext());

	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void get_negativeIndex() {

		Stack<Object> stack = new LinkedStack<Object>().pushAll(ARRAY);

		stack.get(-1);

	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void get_tooLargeIndex() {

		Stack<Object> stack = new LinkedStack<Object>().pushAll(ARRAY);

		stack.get(2);

	}

	@Test
	public void get_first() {

		Stack<Object> stack = new LinkedStack<Object>().pushAll(ARRAY);

		Assert.assertSame(ARRAY[1], stack.get(0));

	}

	@Test
	public void get_last() {

		Stack<Object> stack = new LinkedStack<Object>().pushAll(ARRAY);

		Assert.assertSame(ARRAY[0], stack.get(1));

	}

	@Test
	public void clear() {

		Object first = new Object();
		Object second = new Object();
		Stack<Object> stack = new LinkedStack<Object>();

		stack.push(first);
		stack.push(second);

		Source<Object> source = stack.clear();
		Iterator<Object> iterator = source.iterator();

		Assert.assertTrue(stack.isEmpty());
		Assert.assertSame(2, source.size());

		Assert.assertTrue(iterator.hasNext());
		Assert.assertSame(second, iterator.next());
		Assert.assertTrue(iterator.hasNext());
		Assert.assertSame(first, iterator.next());
		Assert.assertFalse(iterator.hasNext());

	}

	@Test
	public void firstIndexOf_unmatchedReference() {

		Stack<Object> stack = new LinkedStack<Object>().pushAll(ARRAY);

		Assert.assertFalse(stack.firstIndexOf(new Object()).hasValue());

	}

	@Test
	public void firstIndexOf_matchedReference() {

		Stack<Object> stack = new LinkedStack<Object>().pushAll(ARRAY);

		Assert.assertEquals(Integer.valueOf(1), stack.firstIndexOf(ARRAY[0]).getValue());

	}

	@Test(expected = IllegalArgumentException.class)
	public void firstIndexOfMatch_nullPredicate() {

		Stack<Object> stack = new LinkedStack<Object>().pushAll(ARRAY);

		stack.firstIndexOfMatch(null);

	}

	@Test
	public void firstIndexOfMatch_unmatchedPredicate() {

		Stack<Object> stack = new LinkedStack<Object>().pushAll(ARRAY);

		Assert.assertFalse(stack.firstIndexOfMatch(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return false;
			}

		}).hasValue());

	}

	@Test
	public void firstIndexOfMatch_matchedPredicate() {

		Stack<Object> stack = new LinkedStack<Object>().pushAll(ARRAY);

		Assert.assertEquals(Integer.valueOf(1), stack.firstIndexOfMatch(new Predicate<Object>() {

			@Override
			public boolean test(Object subject) {
				return subject == ARRAY[0];
			}

		}).getValue());

	}

	@Test
	public void equals_null() {

		Stack<Object> stack = new LinkedStack<Object>();

		Assert.assertFalse(stack.equals(null));

	}

	@Test
	public void equals_object() {

		Stack<Object> stack = new LinkedStack<Object>();

		Assert.assertFalse(stack.equals(new Object()));

	}

	@Test
	public void equals_same() {

		Stack<Object> stack = new LinkedStack<Object>();

		Assert.assertTrue(stack.equals(stack));

	}

	@Test
	public void equals_equal() {

		Object object = new Object();
		Stack<Object> stack = new LinkedStack<Object>();

		stack.push(object);

		Stack<Object> other = new LinkedStack<Object>();
		other.push(object);

		Assert.assertTrue(stack.equals(other));

	}

	@Test
	public void equals_notEqual() {

		Stack<Object> stack = new LinkedStack<Object>();

		stack.push(new Object());

		Stack<Object> other = new LinkedStack<Object>();
		other.push(new Object());

		Assert.assertFalse(stack.equals(other));

	}

	@Test
	public void hash_emptyStack() {

		Stack<Object> stack = new LinkedStack<Object>();

		Assert.assertEquals(1, stack.hashCode());

	}

	@Test
	public void hash_nonEmptyStack() {

		Object object = new Object();
		Stack<Object> stack = new LinkedStack<Object>();

		stack.push(object);

		Assert.assertEquals(31 * 1 + object.hashCode(), stack.hashCode());

	}

	@Test
	public void toString_emptyStack() {

		Stack<Object> stack = new LinkedStack<Object>();

		Assert.assertEquals("[]", stack.toString());

	}

	@Test
	public void toString_nonEmptyStack() {

		Stack<Object> stack = new LinkedStack<Object>();

		stack.push("foo");
		stack.push("bar");

		Assert.assertEquals("[bar, foo]", stack.toString());

	}

}
