### Code Quality Score: 3/3

While `this.foo.bind(this)` is occasionally unavoidable, for instance, in older
versions of JS, in newer versions of JS such as ours, you can define a member
function as a variable using lambda syntax, which automatically binds `this` to
it, as in:
```
foo = (arg1, arg2) => {
  // ...
};
```

You're comparing a string (from `event.target.value`) to an integer
directly using `<` or `>`. This code will run in Javascript, but it won't do
what you expect. Javascript will do its best to convert the string to a number
before it does the comparison (look up "Javascript type coercion") but
if it can't, you may get NaN back and cause an error. It's probably best to
do the parseInt (or some other conversion to a Number) and error check
before doing other comparisons.

In MainContainer, you do a parseInto to get the size into an integer. Then in Grid, you do
another parseInt on the same value. This will work, but is unnecessary.

-0: Small Note: It's traditional to have the render() method be the last method inside
a React component.

#### Graded By: Andrew Gies
