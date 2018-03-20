# 7. Collections

Well, we know that Collections are a powerful concept with any programming language; simply put, we can collect similar kind of objects and perform operations with/on them. Let’s have a glimpse of those in Java:

```Java
final List<Integer> numbers = Arrays.asList(1, 2, 3);

final Map<Integer, String> map = new HashMap<Integer, String>();
map.put(1, "One");
map.put(2, "Two");
map.put(3, "Three");

// Java 9
final List<Integer> numbers = List.of(1, 2, 3);

final Map<Integer, String> map = Map.of(
  1, "One",
  2, "Two",
  3, "Three");
```

Now, in Kotlin, we can have similar collections:

```Java
val numbers = listOf(1, 2, 3)

val map = mapOf(
  1 to "One",
  2 to "Two",
  3 to "Three")
```

Performing operations is interesting as well, like in Java:

```Java
for (int number : numbers) {
    System.out.println(number);
}

for (int number : numbers) {
    if(number > 5) {
        System.out.println(number);
    }
}
```

Next, we can perform the same operations in Kotlin in a much simpler way:

```Java
numbers.forEach {
    println(it)
}

numbers
  .filter  { it > 5 }
  .forEach { println(it) }
```

Let’s study a final example on collecting even and odd numbers in a Map of String as keys, and List of Integers as their value. In Java, we’ll have to write:

```Java
final Map<String, List<Integer>> groups = new HashMap<>();
for (int number : numbers) {
    if((number & 1) == 0) {
        if(!groups.containsKey("even")) {
            groups.put("even", new ArrayList<>());
        }

        groups.get("even").add(number);
        continue;
    }

    if(!groups.containsKey("odd")){
        groups.put("odd", new ArrayList<>());
    }

    groups.get("odd").add(number);
}
```

In Kotlin:

```Java
val groups = numbers.groupBy {
  if (it and 1 == 0) "even" else "odd"
}
```



