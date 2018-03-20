# 4. String Operations

Strings work the same way as in Java. We can do similar operations like append and get a part of a String as well.

In Java:

```Java
String name = "John";
String lastName = "Smith";
String text = "My name is: " + name + " " + lastName;
String otherText = "My name is: " + name.substring(2);

String text = "First Line\n" +
  "Second Line\n" +
  "Third Line";
```

In Kotlin:

```Java
val name = "John"
val lastName = "Smith"
val text = "My name is: $name $lastName"
val otherText = "My name is: ${name.substring(2)}"

val text = """
  First Line
  Second Line
  Third Line
""".trimMargin()
```

That looked quite easy:

We can interpolate Strings by using the `$` character, and the expressions will be evaluated at runtime. In Java, we could achieve something similar by using `String.format()`  
No need for breaking multiline Strings as in Java. Kotlin supports them out-of-the-box using. We just need to remember to use triple quotation marks  
There is no symbol for line continuation in Kotlin. As its grammar allows to have spaces between almost all symbols, we can just break the statement:

```Java
val text = "This " + "is " + "a " +
  "long " + "long " + "line"
```

However, if the first line of the statement is a valid statement, it won’t work:

```Java
val text = "This " + "is " + "a "
  + "long " + "long " + "line" // syntax error
```

To avoid such issues when breaking long statements across multiple lines, we can use parentheses:

```Java
val text = ("This " + "is " + "a "
  + "long " + "long " + "line") // no syntax error
```



