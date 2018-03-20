# From Java to Kotlin. 

While we’ll be looking at many basic examples

# 1. Overview
Here, we’ll look at basic examples of migrating our Java code to Kotlin, like simple print statements, defining variables, managing nullability.
Then, we’ll move towards inner areas like control statements like if-else and switch statements.
Finally, we’re moving to defining classes and working with collections.

# 2. Basic Migrations
Let’s get started with simple examples on how to migrate simple statements.

## 2.1. Print Statements
To start, let’s see how printing works. 

In Java:
```Java
System.out.print("Hello World!");
System.out.println("Hello World!");
```
In Kotlin:
```
print("Hello World!")
println("Hello World!")
```

## 2.2. Defining Variables
In Java:
```Java
final int a;
final int b = 21;
int c;
int d = 25;
d = 23;
c = 21;
```
In Kotlin:
```Java
val a: Int
val b = 21
var c: Int
var d = 25
d = 23
c = 21
```
As we can see, semi-colons in Kotlin are optional. Kotlin also utilizes enhanced type inference, and we do not need to define types explicitly.

Whenever we want to create a final variable, we can simply use `val` instead of `var`.

## 2.3. Casting
In Java, we need to perform unnecessary casting in situations like:
```Java
if(str instanceof String){
    String result = ((String) str).substring(1);
}
```
In Kotlin, smart casting allows us to skip a redundant cast:
```Java
if (str is String) {
    val result = str.substring(1)
}
```
## 2.4. Bit Operations
Bit operations in Kotlin are much more intuitive.

Let’s see this in action, with Java:
```Java
int orResult   = a | b;
int andResult  = a & b;
int xorResult  = a ^ b;
int rightShift = a >> 2;
int leftShift  = a << 2;
```
And in Kotlin:
```Java
var orResult   = a or b
var andResult  = a and b
var xorResult  = a xor b
var rightShift = a shr 2
var leftShift  = a shl 2
```

# 3. Null-Safety
In Java:
```Java
final String name = null;
 
String text;
text = null;
 
if(text != null){
    int length = text.length();
}
```
So, there’s no restriction in Java to assign null to variables and use them. While using any variable, we’re usually forced to make a null check as well.

This is not the case with Kotlin:
```Java
val name: String? = null
 
var lastName: String?
lastName = null
 
var firstName: String
firstName = null // Compilation error!!
```
By default, Kotlin assumes that values cannot be null.

We cannot assign null to the reference firstName, and if we try to, it’ll cause a compiler error. If we want to create a nullable reference, we need to append the question mark(?) to the type definition, as we did in the first line.

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
# 6. Classes
In Java, we define a model class and accompany them with standard setters and getters:
```Java
package com.opengiineer;
 
public class Person {
 
    private long id;
    private String name;
    private String brand;
    private long price;
 
    // setters and getters
}
```
In Kotlin, getters and setters are autogenerated:
```Java
package com.opengiineer
 
class Person {
  var id: Long = 0
  var name: String? = null
  var brand: String? = null
  var price: Long = 0
}
```
#### Modification of getter/setter visibility can also be changed, but keep in mind that the getter’s visibility must be the same as the property’s visibility.

In Kotlin, every class comes with the following methods (can be overridden):

- toString (readable string representation for an object)
- hashCode (provides a unique identifier for an object)
- equals (used to compare two objects from the same class to see if they are the same)

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
