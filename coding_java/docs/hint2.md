# [Remove When Done]
TODO this hint file should contain the **second hint** you might give your candidate when he seeks for help and still weren't able to solve your challenge after the **first hint** was given away.
This hint should help him get closer to the solution without giving up all of it.

The following lines provide an actual sample of briefing that we find satisfying.

Alright. Let's try to break it down together.

1. You have converted your input String to an array of characters.
2. You implement your algorithm to invert the order of all characters.
3. You convert your resulting array of characters back to a String.


Let's walk you through the content of the method:
```java
public static String readBackwards(String randomLine) {
// 1)
char[] myInputChars = randomLine.toCharArray();
// 2)
char[] myReversedLine = reverseChars(myInputChars);
// 3)
return new String(myReversedLine);
}
```

Now, you only need to create a static method named **reverseChars** to implement the algorithm!  
Good luck!
# [/Remove When Done]
