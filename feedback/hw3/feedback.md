### Total Score: 20/25

### Answers Score: 10/15
- Fibonacci: 3/5

* Did not identify that the issue in problem 2
  was fixed by the fix in problem 1 (-1)

* Identified returning 1 on n = 2 as an issue in problem 2 (-1)

- Ball: 1/1
- BallContainer: 2/4

* Failing to note the advantages of one of the approaches (-2)

- Box: 4/5

* Failing to note the advantages of one of the approaches (-1)


### Style Score: 10/10

* Not passing the length of the array of greetings into Random.nextInt (-0)

RANDOM_ARRAY_ELT
When selecting a greeting in `RandomHello`, the best style would use the length
of the array to specify the maximum value for the random integer generation:
```
String nextGreeting = greetings[rand.nextInt(greetings.length)];
```
Notice how this benefits us later on if we wanted to change the number of
possible greetings in the array.

* Explicitly checking that a ball is not already in the set before adding/ball
  is in the set before removing (-0)

REDUNDANT_ADD_REMOVE
Your BallContainer add/remove methods are more complicated than they need to be.
Look at the documentation for Set.add and Set.remove and see whether you need to
explicitly handle cases the cases of adding something that already exists in the
set and removing something that doesn't exist in the set.
