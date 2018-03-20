# 5. Loops and Control Statements

Just like any other programming language, in Kotlin as well we’ve got control statements and loops for repetitive tasks.

## 5.1. For loop

In Java, we have various kinds of loops for iterating over a collection, or a Map, like:

```Java
for (int i = 1; i < 11 ; i++) { }

for (int i = 1; i < 11 ; i+=2) { }

for (String item : collection) { }

for (Map.Entry<String, String> entry: map.entrySet()) { }
```

In Kotlin, we have something similar, but simpler. As we’re already familiar with, Kotlin’s syntax is trying to mimic the natural language as much as possible:

```Java
for (i in 1 until 11) { }

for (i in 1..10 step 2) { }

for (item in collection) { }

for ((index, item) in collection.withIndex()) { }

for ((key, value) in map) { }
```

## 5.2. Switch and When

We can use switch statements in Java to make selective decisions:

```Java
final int x = ...; // some value
final String xResult;

switch (x) {
    case 0:
    case 11:
        xResult = "0 or 11";
        break;
    case 1:
    case 2:
    //...
    case 10:
        xResult = "from 1 to 10";
        break;
    default:
        if(x < 12 && x > 14) {
        xResult = "not from 12 to 14";
        break;
    }

    if(isOdd(x)) {
        xResult = "is odd";
        break;
    }

    xResult = "otherwise";
}

final int y = ...; // some value;
final String yResult;

if(isNegative(y)){
    yResult = "is Negative";
} else if(isZero(y)){
    yResult = "is Zero";
} else if(isOdd(y)){
    yResult = "is Odd";
} else {
    yResult = "otherwise";
}
```

In Kotlin, instead of a switch statement, we use a when statement to make selective decisions:

```Java
val x = ... // some value
val xResult = when (x) {
  0, 11 -> "0 or 11"
  in 1..10 -> "from 1 to 10"
  !in 12..14 -> "not from 12 to 14"
  else -> if (isOdd(x)) { "is odd" } else { "otherwise" }
}
```

The when statement can act as an expression or a statement, with or without an argument:

```Java
val y = ... // some value
val yResult = when {
  isNegative(y) -> "is Negative"
  isZero(y) -> "is Zero"
  isOdd(y) -> "is odd"
  else -> "otherwise"
}
```



