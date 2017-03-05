# Some common iterators for Java

[![Build Status](https://travis-ci.org/markenwerk/java-commons-collections.svg?branch=master)](https://travis-ci.org/markenwerk/java-commons-iterators)
[![Coverage Status](https://coveralls.io/repos/markenwerk/java-commons-collections/badge.svg?branch=master&service=github)](https://coveralls.io/github/markenwerk/java-commons-collections?branch=master)
[![Dependency Status](https://www.versioneye.com/user/projects/564dd82a771e41003f000001/badge.svg)](https://www.versioneye.com/user/projects/564dd82a771e41003f000001)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/net.markenwerk/commons-collections/badge.svg)](https://maven-badges.herokuapp.com/maven-central/net.markenwerk/commons-iterators)
[![Java SE 6](https://img.shields.io/badge/java-SE_6-brightgreen.svg)](http://docs.oracle.com/javase/8/docs/api/)
[![MIT License](https://img.shields.io/badge/license-MIT-brightgreen.svg)](https://github.com/markenwerk/java-commons-iterators/blob/master/LICENSE)

## Overview

This is a collection of some collection interfaces and implementations, mainly intended for clear-cut API design:

- [Sink](#sink)
- [Source](#source)
- [Stack](#stack)
- [Sequence](#sequence)

This library is hosted in the [Maven Central Repository](https://maven-badges.herokuapp.com/maven-central/net.markenwerk/commons-collections). You can use it with the following coordinates:

```xml
<dependency>
	<groupId>net.markenwerk</groupId>
	<artifactId>commons-collections</artifactId>
	<version>1.0.0</version>
</dependency>
```

Consult the [usage description](#usage) and [Javadoc](https://markenwerk.github.io/java-commons-collections/index.html) for further information.

## Motivation

This library provides collections for clear-cut API design. Most libraries just use JAVA's collection API. Although this has the important advantage, that the API is well known and understood, it is mostly intended as a general purpose API and not for clear-cut API. A clear-cut API should convey as much usage description as possible through the type system and names of public methods, interfaces and classes. if, for example, an API allows a read-only access to one of its properties, there should only be a getter-method for that property and no setter-method. If the API would have a setter-method in such a scenario, it would need to throw an [`UnsupportedOperationException`][UnsupportedOperationException], to protect its internal state, should the method be invoked by the API user and inform the API user about this fact in the API documentation. Not to include an unwanted setter in an APi is easy, but if the API designer wants to allow a read-only access to a collection, he has several options (All of which we observed in the public APIs of several libraries):

1. Include a getter-method for an unmodifiable JAVA collections to the API interface, i.e. by returning a [`Collections.unmodifiableCollection()`](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#unmodifiableCollection(java.util.Collection)) and convey this fact in the API documentation. This is undesirable, because the API user receives a [`Collection`][Collection] object that is going to throw exception at runtime, if the API user doesn't read the API documentation carefully.
2. Add methods that mimic the desired subset of the behavior of the JAVA collection class to the API interface, i.e. by adding `add()`, `addAll()`, ... instead of a getter-method for an unmodifiable JAVA collection. This is undesirable because it pollutes the API interface with a lot of methods, especially if the API interface has more than one such collection (and the method names need to be suffixed accordingly), it will easily become inconsistent, complicate testing and generally defeats the purpose of OOP.
3. Add just the minimum subset of methods that mimic the behavior of a collection class, i.e. by adding `add()`, ..., but no convenience methods like `addAll()`. This has all the disadvantages of the previous option, but with added inconvenience.

This is where this library comes in. It provides interfaces for collections with read-only-access, write-only-access and for common data-structures. If, for example, an API wants to publish a stack, it can't use JAVA collections, because ther is no pure stack. A [`List`][List] and even a [`Stack`](http://docs.oracle.com/javase/8/docs/api/index.html?java/util/List.html) offer a lot of methods, that allow "un-stack-like" access. 

## Interfaces

### Sink

The [`Sink`][Sink] interface should be used by components that allow a write-only-access. 

A [`Sink`][Sink] must implement the following methods:

```java
public Sink<Payload> add(Payload payload);

public Sink<Payload> addAll(Payload... payloads);

public Sink<Payload> addAll(Iterable<? extends Payload> payloads);
```

A [`Sink`][Sink] is forbidden to reveal information about the content of an underlying component through its `hashCode`-, `equals()`- or `toString()`-methods.

This library provides [`AbstractSink`][AbstractSink] as a base implementation and [`CollectionSink`][CollectionSink],  [`HandlerSink`][HandlerSink], [`SequenceSink`][SequenceSink] and [`StackSink`][StackSink] as ready-to-use implementations that are backed by the corresponding components.

### Source

The [`Source`][Source] interface should be used by components that allow a read-only-access. 

A [`Source`][Source] must, in essence, implement the following methods:

```java
public boolean isEmpty();

public int size();

public Payload getFirst() throws NoSuchElementException;

public boolean contains(Object reference);
```

There are several other methods that can be used to query or filter the [`Source`][Source] using a reference object or a [`Predicate`][Predicate]. 

The [`IndexedSource`][IndexedSource] interface should be used by components that allow a read-only-access to a linear data-structure. 

An [`IndexedSource`][IndexedSource] is a [`Source`][Source] that must, in essence, implement the following additional methods:

```java
public Payload get(int index) throws IndexOutOfBoundsException;

public boolean isFirst(Payload payload) throws NoSuchElementException;

public boolean isLast(Payload payload) throws NoSuchElementException;

public Payload getLast() throws NoSuchElementException;

public Optional<Integer> firstIndexOf(Payload payload);

public Optional<Integer> lastIndexOf(Payload payload);
```

There are several other methods that can be used to query or filter the [`IndexedSource`][IndexedSource] using a reference object or a [`Predicate`][Predicate]. 

This library provides [`AbstractSource`][AbstractSource] and [`AbstractIndexedSource`][AbstractIndexedSource] as a base implementation and [`ArraySource`][ArraySource],  [`CollectionSource`][CollectionSource], [`EmptySource`][EmptySource],  [`ListSource`][ListSource], [`MapKeySource`][MapKeySource], [`MapValueSource`][MapValueSource], [`ObjectSource`][ObjectSource] and [`OptionalSource`][OptionalSource] as ready-to-use implementations that are backed by the corresponding components.

### Stack

The [`Stack`][Stack] interface should be used by components that need a pure stack. 

A [`Stack`][Stack] is a [`Source`][Source] that must, in essence, implement the following additional methods:

```java
public Stack<Payload> push(Payload payload);

public Stack<Payload> pushAll(Payload... payloads);

public Payload pop() throws NoSuchElementException;

public Source<Payload> popAll(int number) throws IllegalArgumentException;

public Payload get(int index) throws IndexOutOfBoundsException;

public Payload getFirst() throws NoSuchElementException;

public replace(Payload payload) NoSuchElementException;

public Source<Payload> clear();

public ProtectedIterator<Payload> iterator();
```

There are several other methods that can be used to query or filter the [`Stack`][Stack] using a reference object or a [`Predicate`][Predicate]. 

This library provides [`AbstractStack`][AbstractStack] as a base implementation and [`LinkedStack`][LinkedStack] as a ready-to-use implementation.

### Sequence

The [`Sequence`][Sequence] interface should be used by components that need a pure linear data-structure. 

A [`Sequence`][Sequence] is an [`IndexedSource`][IndexedSource] that must, in essence, implement the following additional methods:

```java
public Sequence<Payload> insert(int index, Payload payload) throws IndexOutOfBoundsException;

public Sequence<Payload> insertAll(int index, Payload... payloads) throws IndexOutOfBoundsException;

public Sequence<Payload> prepend(Payload payload);

public Sequence<Payload> prependAll(Payload... payloads);

public Sequence<Payload> append(Payload payload);

public Sequence<Payload> appendAll(Payload... payloads);

public Payload remove(int index) throws IndexOutOfBoundsException;

public Payload removeFirst() throws NoSuchElementException;

public Payload removeLast() throws NoSuchElementException;

public Source<Payload> removeAll(Payload reference);

public Source<Payload> retainAll(Payload reference);

public Source<Payload> clear();

public Payload replace(int index, Payload replacement) IndexOutOfBoundsException;
```

There are several other methods that can be used to query or filter the [`Sequence`][Sequence] using a reference object or a [`Predicate`][Predicate]. 

This library provides [`AbstractSequence`][AbstractSequence] as a base implementation and [`Listsequence`][Listsequence] as a ready-to-use implementation.

[AbstractSink]: https://markenwerk.github.io/java-commons-collections/index.html?net/markenwerk/commons/collections/sinks/AbstractSink.html
[CollectionSink]: https://markenwerk.github.io/java-commons-collections/index.html?net/markenwerk/commons/collections/sinks/CollectionSink.html
[HandlerSink]: https://markenwerk.github.io/java-commons-collections/index.html?net/markenwerk/commons/collections/sinks/HandlerSink.html
[SequenceSink]: https://markenwerk.github.io/java-commons-collections/index.html?net/markenwerk/commons/collections/sinks/Sink.html
[SequenceSink]: https://markenwerk.github.io/java-commons-collections/index.html?net/markenwerk/commons/collections/sinks/Sink.html
[Sink]: https://markenwerk.github.io/java-commons-collections/index.html?net/markenwerk/commons/collections/sinks/Sink.html
[StackSink]: https://markenwerk.github.io/java-commons-collections/index.html?net/markenwerk/commons/collections/sinks/StackSink.html

[AbstractIndexedSource]: https://markenwerk.github.io/java-commons-collections/index.html?net/markenwerk/commons/collections/sources/AbstractIndexedSource.html
[AbstractSource]: https://markenwerk.github.io/java-commons-collections/index.html?net/markenwerk/commons/collections/sources/AbstractSource.html
[ArraySource]: https://markenwerk.github.io/java-commons-collections/index.html?net/markenwerk/commons/collections/sources/ArraySource.html
[CollectionSource]: https://markenwerk.github.io/java-commons-collections/index.html?net/markenwerk/commons/collections/sources/CollectionSource.html
[EmptySource]: https://markenwerk.github.io/java-commons-collections/index.html?net/markenwerk/commons/collections/sources/EmptySource.html
[IndexedSource]: https://markenwerk.github.io/java-commons-collections/index.html?net/markenwerk/commons/collections/sources/IndexedSource.html
[ListSource]: https://markenwerk.github.io/java-commons-collections/index.html?net/markenwerk/commons/collections/sources/ListSource.html
[MapKeySource]: https://markenwerk.github.io/java-commons-collections/index.html?net/markenwerk/commons/collections/sources/Source.html
[MapKeySource]: https://markenwerk.github.io/java-commons-collections/index.html?net/markenwerk/commons/collections/sources/Source.html
[MapValueSource]: https://markenwerk.github.io/java-commons-collections/index.html?net/markenwerk/commons/collections/sources/Source.html
[MapValueSource]: https://markenwerk.github.io/java-commons-collections/index.html?net/markenwerk/commons/collections/sources/Source.html
[ObjectSource]: https://markenwerk.github.io/java-commons-collections/index.html?net/markenwerk/commons/collections/sources/ObjectSource.html
[OptionalSource]: https://markenwerk.github.io/java-commons-collections/index.html?net/markenwerk/commons/collections/sources/OptionalSource.html
[Source]: https://markenwerk.github.io/java-commons-collections/index.html?net/markenwerk/commons/collections/sources/Source.html

[AbstractStack]: https://markenwerk.github.io/java-commons-collections/index.html?net/markenwerk/commons/collections/stacks/AbstractStack.html
[LinkedStack]: https://markenwerk.github.io/java-commons-collections/index.html?net/markenwerk/commons/collections/stacks/LinkedStack.html
[Stack]: https://markenwerk.github.io/java-commons-collections/index.html?net/markenwerk/commons/collections/stacks/Stack.html

[AbstractSequence]: https://markenwerk.github.io/java-commons-collections/index.html?net/markenwerk/commons/collections/sequences/AbstractSequence.html
[ListSequence]: https://markenwerk.github.io/java-commons-collections/index.html?net/markenwerk/commons/collections/sequences/ListSequence.html
[Sequence]: https://markenwerk.github.io/java-commons-collections/index.html?net/markenwerk/commons/collections/sequences/Sequence.html

[Handler]: https://markenwerk.github.io/java-commons-interfaces/index.html?net/markenwerk/commons/interfaces/Handler.html
[Predicate]: https://markenwerk.github.io/java-commons-interfaces/index.html?net/markenwerk/commons/interfaces/Predicate.html

[Collection]: https://docs.oracle.com/javase/8/docs/api/index.html?java/util/Collection.html
[List]: http://docs.oracle.com/javase/8/docs/api/index.html?java/util/List.html
[UnsupportedOperationException]: https://docs.oracle.com/javase/8/docs/api/index.html?java/lang/UnsupportedOperationException.html