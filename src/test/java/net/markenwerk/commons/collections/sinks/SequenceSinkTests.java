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
import net.markenwerk.commons.collections.sequences.ListSequence;
import net.markenwerk.commons.collections.sequences.Sequence;

@SuppressWarnings("javadoc")
public class SequenceSinkTests {

	@Test
	public void getSequence() {

		Sequence<Object> sequence = new ListSequence<Object>();
		SequenceSink<Object> sink = new SequenceSink<Object>(sequence);

		Assert.assertSame(sequence, sink.getSequence());

	}

	@Test
	public void getNullity() {

		SequenceSink<Object> sink = new SequenceSink<Object>(Nullity.IGNORE, new ListSequence<Object>());

		Assert.assertSame(Nullity.IGNORE, sink.getNullity());

	}

	@Test
	public void add() {

		Object object = new Object();
		SequenceSink<Object> sink = new SequenceSink<Object>(new ListSequence<Object>());

		sink.add(object);

		Assert.assertEquals(1, sink.getSequence().size());
		Assert.assertTrue(sink.getSequence().contains(object));

	}

	@Test
	public void add_nullAllow() {

		SequenceSink<Object> sink = new SequenceSink<Object>(Nullity.ALLOW, new ListSequence<Object>());

		sink.add(null);

		Assert.assertEquals(1, sink.getSequence().size());
		Assert.assertTrue(sink.getSequence().contains((Object) null));

	}

	@Test
	public void add_nullIgnore() {

		SequenceSink<Object> sink = new SequenceSink<Object>(Nullity.IGNORE, new ListSequence<Object>());

		sink.add(null);

		Assert.assertTrue(sink.getSequence().isEmpty());

	}

	@Test(expected = IllegalArgumentException.class)
	public void add_rejectNull() {

		SequenceSink<Object> sink = new SequenceSink<Object>(Nullity.REJECT, new ListSequence<Object>());

		sink.add(null);

	}

	@Test
	public void addAll() {

		Object first = new Object();
		Object second = new Object();
		SequenceSink<Object> sink = new SequenceSink<Object>(new ListSequence<Object>());

		sink.addAll(first, second);

		Assert.assertEquals(2, sink.getSequence().size());
		Assert.assertTrue(sink.getSequence().containsAll(first, second));
	}

	@Test(expected = IllegalArgumentException.class)
	public void addAll_nullArray() {

		SequenceSink<Object> sink = new SequenceSink<Object>(new ListSequence<Object>());

		sink.addAll((Object[]) null);

	}

	@Test(expected = IllegalArgumentException.class)
	public void addAll_nullIterable() {

		SequenceSink<Object> sink = new SequenceSink<Object>(new ListSequence<Object>());

		sink.addAll((Iterable<Object>) null);

	}

	@Test
	public void equals() {

		ListSequence<Object> sequence = new ListSequence<Object>();

		Assert.assertNotEquals(new SequenceSink<Object>(sequence), new SequenceSink<Object>(sequence));

	}

	@Test
	public void haschCode() {

		ListSequence<Object> sequence = new ListSequence<Object>();

		Assert.assertNotEquals(new SequenceSink<Object>(sequence).hashCode(),
				new SequenceSink<Object>(sequence).hashCode());

	}

}
