# Test Cases

### Testing buildSymbols()

Note: The "_" in the test cases are spaces: "_" is one space and "__" is two spaces.

There is no return value for this expression. Instead, it is used to extract the variables/arrays from the expresssion. Its correctness
was tested using the printScalars() and printArrays() methods provided by the coursework.

|    | Expression    | Expression           |
|:--:|:-------------:|:--------------------:|
|:--:| Variables Only| Variables and Arrays |
|:--:|:-------------:|:--------------------:|
| 1  |a              |var[i]                |
| 2  |a+b            |vara[i]+varb[p]       |  
| 3  |a_+_b          |vara[i]_+_varb[p]     |
| 4  |a_+__b         |vara[i]_+__varb[p]    |
| 5  |a__+_b         |vara[i]__+_varb[p]    |
| 6  |a__+__b        |vara[i]__+__varb[p]   |
| 7  |a_+b           |vara[i]_+varb[p]      |
| 8  |a+_b           |vara[i]+_varb[p]      |
| 9  |               |var[_i_]              |
| 10 |               |var[__i__]            |
| 11 |               |var[i_]               |
| 12 |               |var[_i]               |