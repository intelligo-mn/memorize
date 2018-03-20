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

We cannot assign null to the reference firstName, and if we try to, it’ll cause a compiler error. If we want to create a nullable reference, we need to append the question mark\(?\) to the type definition, as we did in the first line.

