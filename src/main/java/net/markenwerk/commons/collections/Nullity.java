/*
 * Copyright (c) 2017 Torsten Krause, Markenwerk GmbH
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
package net.markenwerk.commons.collections;

/**
 * A {@link Nullity} indicates the intended handling of {@literal null} values
 * that are added to a data structure.
 * 
 * @author Torsten Krause (tk at markenwerk dot net)
 * @since 1.0.0
 */
public enum Nullity {

	/**
	 * A {@literal null} should be allowed. It can be inserted and can be used
	 * to replace another value.
	 */
	ALLOW() {

		@Override
		public boolean proceedAdd(String what, int position) {
			return true;
		}

		@Override
		public boolean proceedReplace(String what, int position) throws IllegalArgumentException {
			return true;
		}

	},

	/**
	 * A {@literal null} should be ignored. It won't be inserted and can't be
	 * used to replace another value.
	 */
	IGNORE() {

		@Override
		public boolean proceedAdd(String what, int position) {
			return false;
		}

		@Override
		public boolean proceedReplace(String what, int position) throws IllegalArgumentException {
			return fail(what, position);
		}

	},

	/**
	 * A {@literal null} should be rejected. It can't be inserted and can't be
	 * used to replace another value.
	 */
	REJECT() {

		@Override
		public boolean proceedAdd(String what, int position) {
			return fail(what, position);
		}

		@Override
		public boolean proceedReplace(String what, int position) throws IllegalArgumentException {
			return fail(what, position);
		}

	};

	/**
	 * Constant to indicate, that a {@literal null} doesn't originate from an
	 * array or {@link Iterable}.
	 */
	public static final int NO_POSITION = -1;

	private static boolean fail(String what, int position) {
		if (NO_POSITION == position) {
			throw new IllegalArgumentException("The " + what + " is null");
		} else {
			throw new IllegalArgumentException("The " + what + " at position '" + position + "' is null");
		}
	}

	/**
	 * Returns whether to allow adding a {@literal null} value to a data
	 * structure.
	 * 
	 * @param what
	 *            Textual representation of the kind of The position of the
	 *            {@literal null} value to be used in an appropriate exception
	 *            message.
	 * @param position
	 *            The position of the {@literal null} value inside of an array
	 *            or {@link Iterable} that is added to the data structure, or
	 *            {@link Nullity#NO_POSITION}.
	 * @return Whether to allow the intended operation.
	 * @throws IllegalArgumentException
	 *             If adding a {@literal null} value to a data structure is
	 *             considered to be a {@link Nullity#REJECT failure}.
	 */
	public abstract boolean proceedAdd(String what, int position) throws IllegalArgumentException;

	/**
	 * Returns whether to allow replacing a non-{@literal null} value with a
	 * {@literal null} value.
	 * 
	 * @param what
	 *            Textual representation of the kind of The position of the
	 *            {@literal null} value to be used in an appropriate exception
	 *            message.
	 * @param position
	 *            The position of the {@literal null} value inside of an array
	 *            or {@link Iterable} that is added to the data structure, or
	 *            {@link Nullity#NO_POSITION}.
	 * @return Whether to allow the intended operation.
	 * @throws IllegalArgumentException
	 *             If adding a {@literal null} value to a data structure is
	 *             considered to be a {@link Nullity#REJECT failure}.
	 */
	public abstract boolean proceedReplace(String what, int position) throws IllegalArgumentException;

}
