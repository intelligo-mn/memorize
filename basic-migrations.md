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



