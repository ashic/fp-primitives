"Advanced" FP for the Beginner
========================================================
author: ashic mahtab [@ashic]
date: 9 May, 2016
autosize: true

"Advanced"?
========================================================

Think of what you know....

- Classes
- Interfaces
- Abstract Classes
- GoF Patterns
- Services, DI...

What are Semigroups, Monoids, Functors, Monads, Applicatives?
========================================================

- Names of things

Semigroup
========================================================

- Types that have a "combiner" function.
- Combiner: (A, A) => A
- Operators: <> (Haskell), |+| (scala cats)

What does this mean?
========================================================

1. Define functions for things that can be "combined"
1. Define ways things can be combined.
1. Use 2 with 1.

[Demo]

Demo
========================================================
- We calculated something very specific.
- We defined the calculation in terms of a Semigroup.
- We passed different instances for different results.
- ... But that initial value...still have to be specified explicitly.

Monoid
========================================================
- Extend Semigroup with that pesky initial value.

[Demo]

Demo
========================================================
- We used a Monoid to abstract the "empty" value.
- This let us carry out a more interesting calculation (discounted price with an offset).

Functor
========================================================
The function this time is fmap

- fmap:: (A => B) => (F[A] => F[B])
      ~= F[A] => (A => B) => F[B]
- F is a type that takes a single type parameter...type with a "hole"...a "box"...
- .. e.g. Option, List, Future, etc.

[Demo]

Demo
========================================================
- We "mapped" a "normal" function to work with Lists, and Options.
- We "lifted" a function to work with lists using a default Functor.
- We created a custom Functor, and used it to "lift" a function to work with lists in our own way.

Monad
========================================================

# The M Word!!

- Just as before.
- Two functions: unit (pure) and bind
- pure:: A => F[A]
- bind:: F[A] => (A => F[B]) => F[B]

- So...like a Functor, but the function takes in a type and returns the "boxed" type.
  A => F[B] instead of A => B
  
- Option, List, Future, etc. are all monads

[Demo]

Demo
========================================================
- We saw for comprehensions in action
- How they can "short circuit"
- Created our own monad, and used it to lift a function
