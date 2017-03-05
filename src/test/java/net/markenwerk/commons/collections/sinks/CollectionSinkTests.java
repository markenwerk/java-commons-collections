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
package net.markenwerk.commons.collections.sinks;

import java.util.Collection;
import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Test;

import net.markenwerk.commons.collections.Nullity;

@SuppressWarnings("javadoc")
public class CollectionSinkTests {

	@Test
	public void getCollection() {

		Collection<Object> stack = new LinkedList<Object>();
		CollectionSink<Object> sink = new CollectionSink<Object>(stack);

		Assert.assertSame(stack, sink.getCollection());

	}

	@Test
	public void getNullity() {

		CollectionSink<Object> sink = new CollectionSink<Object>(Nullity.IGNORE, new LinkedList<Object>());

		Assert.assertSame(Nullity.IGNORE, sink.getNullity());

	}

	@Test
	public void add() {

		Object object = new Object();
		CollectionSink<Object> sink = new CollectionSink<Object>(new LinkedList<Object>());

		sink.add(object);

		Assert.assertEquals(1, sink.getCollection().size());
		Assert.assertTrue(sink.getCollection().contains(object));

	}

	@Test
	public void add_nullAllow() {

		CollectionSink<Object> sink = new CollectionSink<Object>(Nullity.ALLOW, new LinkedList<Object>());

		sink.add(null);

		Assert.assertEquals(1, sink.getCollection().size());
		Assert.assertTrue(sink.getCollection().contains(null));

	}

	@Test
	public void add_nullIgnore() {

		CollectionSink<Object> sink = new CollectionSink<Object>(Nullity.IGNORE, new LinkedList<Object>());

		sink.add(null);

		Assert.assertTrue(sink.getCollection().isEmpty());

	}

	@Test(expected = IllegalArgumentException.class)
	public void add_rejectNull() {

		CollectionSink<Object> sink = new CollectionSink<Object>(Nullity.REJECT, new LinkedList<Object>());

		sink.add(null);

	}

	@Test
	public void addAll() {

		Object first = new Object();
		Object second = new Object();
		CollectionSink<Object> sink = new CollectionSink<Object>(new LinkedList<Object>());

		sink.addAll(first, second);

		Assert.assertEquals(2, sink.getCollection().size());
		Assert.assertTrue(sink.getCollection().contains(first));
		Assert.assertTrue(sink.getCollection().contains(second));
	}

	@Test(expected = IllegalArgumentException.class)
	public void addAll_nullArray() {

		CollectionSink<Object> sink = new CollectionSink<Object>(new LinkedList<Object>());

		sink.addAll((Object[]) null);

	}

	@Test(expected = IllegalArgumentException.class)
	public void addAll_nullIterable() {

		CollectionSink<Object> sink = new CollectionSink<Object>(new LinkedList<Object>());

		sink.addAll((Iterable<Object>) null);

	}

	@Test
	public void equals() {

		LinkedList<Object> list = new LinkedList<Object>();

		Assert.assertNotEquals(new CollectionSink<Object>(list), new CollectionSink<Object>(list));
	}

	@Test
	public void haschCode() {

		LinkedList<Object> list = new LinkedList<Object>();

		Assert.assertNotEquals(new CollectionSink<Object>(list).hashCode(), new CollectionSink<Object>(list).hashCode());
	}

}
