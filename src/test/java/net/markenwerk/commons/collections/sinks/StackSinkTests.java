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

import org.junit.Assert;
import org.junit.Test;

import net.markenwerk.commons.collections.Nullity;
import net.markenwerk.commons.collections.stacks.LinkedStack;
import net.markenwerk.commons.collections.stacks.Stack;

@SuppressWarnings("javadoc")
public class StackSinkTests {

	@Test
	public void getStack() {

		Stack<Object> stack = new LinkedStack<Object>();
		StackSink<Object> sink = new StackSink<Object>(stack);

		Assert.assertSame(stack, sink.getStack());

	}

	@Test
	public void getNullity() {

		StackSink<Object> sink = new StackSink<Object>(Nullity.IGNORE, new LinkedStack<Object>());

		Assert.assertSame(Nullity.IGNORE, sink.getNullity());

	}

	@Test
	public void add() {

		Object object = new Object();
		StackSink<Object> sink = new StackSink<Object>(new LinkedStack<Object>());

		sink.add(object);

		Assert.assertEquals(1, sink.getStack().size());
		Assert.assertTrue(sink.getStack().contains(object));

	}

	@Test
	public void add_nullAllow() {

		StackSink<Object> sink = new StackSink<Object>(Nullity.ALLOW, new LinkedStack<Object>());

		sink.add(null);

		Assert.assertEquals(1, sink.getStack().size());
		Assert.assertTrue(sink.getStack().contains((Object) null));

	}

	@Test
	public void add_nullIgnore() {

		StackSink<Object> sink = new StackSink<Object>(Nullity.IGNORE, new LinkedStack<Object>());

		sink.add(null);

		Assert.assertTrue(sink.getStack().isEmpty());

	}

	@Test(expected = IllegalArgumentException.class)
	public void add_rejectNull() {

		StackSink<Object> sink = new StackSink<Object>(Nullity.REJECT, new LinkedStack<Object>());

		sink.add(null);

	}

	@Test
	public void addAll() {

		Object first = new Object();
		Object second = new Object();
		StackSink<Object> sink = new StackSink<Object>(new LinkedStack<Object>());

		sink.addAll(first, second);

		Assert.assertEquals(2, sink.getStack().size());
		Assert.assertTrue(sink.getStack().containsAll(first, second));
	}

	@Test(expected = IllegalArgumentException.class)
	public void addAll_nullArray() {

		StackSink<Object> sink = new StackSink<Object>(new LinkedStack<Object>());

		sink.addAll((Object[]) null);

	}

	@Test(expected = IllegalArgumentException.class)
	public void addAll_nullIterable() {

		StackSink<Object> sink = new StackSink<Object>(new LinkedStack<Object>());

		sink.addAll((Iterable<Object>) null);

	}

	@Test
	public void equals() {

		LinkedStack<Object> stack = new LinkedStack<Object>();

		Assert.assertNotEquals(new StackSink<Object>(stack), new StackSink<Object>(stack));

	}

	@Test
	public void haschCode() {

		LinkedStack<Object> stack = new LinkedStack<Object>();

		Assert.assertNotEquals(new StackSink<Object>(stack).hashCode(), new StackSink<Object>(stack).hashCode());

	}

}
