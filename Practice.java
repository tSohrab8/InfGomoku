> ListIterator<String> iter = l.listIterator();
> String s = iter.next()
> s
"A"
> iter.next()
"B"
> iter.previous()
"B"
> iter.previous()
"A"
> iter.hasPrevious()
false
> iter.hasNext()
true
> iter.next()
"A"
> l
[A, B, C, D, E]
> iter.add("1th")
> l
[A, 1th, B, C, D, E]
> iter.next()
"B"
> iter.add("3rd")
> l
[A, 1th, B, 3rd, C, D, E]
> iter.next()
"C"
> iter.next()
"D"
> iter.next()
"E"
> iter.add("Last")
> l
[A, 1th, B, 3rd, C, D, E, Last]
> iter = null;
> l.undo()
> l
[A, 1th, B, 3rd, C, D, E]
> l.undo()
> l
[A, 1th, B, C, D, E]
> l.undo()
> l
[A, B, C, D, E]
> l.redo()
> l
[A, 1th, B, C, D, E]
> l.add(1,"1th-2")
> l
[A, 1th-2, 1th, B, C, D, E]
> iter = l.listIterator();
> iter.next()
"A"
> iter.next()
"1th-2"
> l.add("last")
true
> iter.next()
java.util.ConcurrentModificationException
        at AdditiveList$AListIterator.hasNext(AdditiveList.java:377)
        at AdditiveList$AListIterator.next(AdditiveList.java:383)
// Any change not made by the iterator invalidates the iterator
> l
[A, 1th-2, 1th, B, C, D, E, last]