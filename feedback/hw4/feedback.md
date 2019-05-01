### Total Score: 24/36

### Answers Score: 16/26
- Pseudocode (Problem 0): 7/8

In 0b, the loop invariant is not well-formed (i.e., doesn't make sense).

- Altered Rep Invs (Problems 1a, 2b, and 2c): 5/6

In 1a, `checkRep` would also need to change.

In 2b, `getExpt` and `hashCode` must also change, and the constructor would not
have to change.

- Mutability (Problem 1b): 0/2

The hypothetical change would violate the methods' specifications, which lack
@modifies and @effects clauses, and the class specification, which claims
immutability.

- checkRep Usage (Problems 1c, 2a, and 3a): 2/6

For both 1c and 2a, you only need `checkRep` calls at the end of constructors
because the fields are `final`, with the Java compiler therefore guaranteeing
immutability of the representation.

For 3a, `RatPoly` needs a `checkRep` call on enter/exit of each public method
because the representation is mutable; a `List<T>` can be modified by its
mutators even if the field reference is `final.`

- RatPoly Design (Problem 3b): 4/4

### Code Style Score: 8/10

#### Specific Feedback

The implementation of the `degree` method in `RatPoly` is unnecessarily complex,
because it doesn't use the representation invariant's guarantee of descending
term order.

In the `getNthFromTop` method of `RatPolyStack`, an indexed look-up into the
stack would have been much clearer and faster than iterating through the stack
contents.

#### General Feedback

Initializing an immutable object by mutation is messy and a bit dangerous; it's
better to construct immutable objects in their intended state (e.g., by calling
a private constructor with the complete representation).

Using boolean flags to determine later control flow is messy; it's better to
handle the condition immediately or design the control-flow structure to match
the program's logical flow.
