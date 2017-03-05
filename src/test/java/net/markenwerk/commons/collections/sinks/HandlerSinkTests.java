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
import org.junit.Before;
import org.junit.Test;

import net.markenwerk.commons.collections.Nullity;
import net.markenwerk.commons.exceptions.HandlingException;
import net.markenwerk.commons.interfaces.Handler;

@SuppressWarnings("javadoc")
public class HandlerSinkTests {

	private final Collection<Object> collection = new LinkedList<Object>();

	private Handler<Object> handler;

	@Before
	public void prepareHandler() {

		collection.clear();
		handler = new Handler<Object>() {

			@Override
			public void handle(Object value) throws HandlingException {
				collection.add(value);
			}
		};

	}

	@Test
	public void getNullity() {

		HandlerSink<Object> sink = new HandlerSink<Object>(Nullity.IGNORE, handler);

		Assert.assertSame(Nullity.IGNORE, sink.getNullity());

	}

	@Test
	public void add() {

		Object object = new Object();
		HandlerSink<Object> sink = new HandlerSink<Object>(handler);

		sink.add(object);

		Assert.assertEquals(1, collection.size());
		Assert.assertTrue(collection.contains(object));

	}

	@Test
	public void add_nullAllow() {

		HandlerSink<Object> sink = new HandlerSink<Object>(Nullity.ALLOW, handler);

		sink.add(null);

		Assert.assertEquals(1, collection.size());
		Assert.assertTrue(collection.contains(null));

	}

	@Test
	public void add_nullIgnore() {

		HandlerSink<Object> sink = new HandlerSink<Object>(Nullity.IGNORE, handler);

		sink.add(null);

		Assert.assertTrue(collection.isEmpty());

	}

	@Test(expected = IllegalArgumentException.class)
	public void add_rejectNull() {

		HandlerSink<Object> sink = new HandlerSink<Object>(Nullity.REJECT, handler);

		sink.add(null);

	}

	@Test
	public void addAll() {

		Object first = new Object();
		Object second = new Object();
		HandlerSink<Object> sink = new HandlerSink<Object>(handler);

		sink.addAll(first, second);

		Assert.assertEquals(2, collection.size());
		Assert.assertTrue(collection.contains(first));
		Assert.assertTrue(collection.contains(second));
	}

	@Test(expected = IllegalArgumentException.class)
	public void addAll_nullArray() {

		HandlerSink<Object> sink = new HandlerSink<Object>(handler);

		sink.addAll((Object[]) null);

	}

	@Test(expected = IllegalArgumentException.class)
	public void addAll_nullIterable() {

		HandlerSink<Object> sink = new HandlerSink<Object>(handler);

		sink.addAll((Iterable<Object>) null);

	}

	@Test
	public void equals() {

		Assert.assertNotEquals(new HandlerSink<Object>(handler), new HandlerSink<Object>(handler));
	}

	@Test
	public void haschCode() {

		Assert.assertNotEquals(new HandlerSink<Object>(handler).hashCode(), new HandlerSink<Object>(handler).hashCode());
	}

}
