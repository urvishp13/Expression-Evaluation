# Test Cases

### Testing buildSymbols()

Note: Spaces were put in the test cases to see if non-letter/non-numeric characters are properly skipped when parsing the expression.

There is no return value for this expression. Instead, it is used to extract the variables/arrays from the expresssion. Its correctness
was tested using the printScalars() and printArrays() methods provided by the coursework.

|    | Expression    | Expression           |
|:--:|:-------------:|:--------------------:|
|    | **Variables Only** | **Variables and Arrays** |
| 1  |a              |var[i]                |
| 2  |a+b            |vara[i]+varb[p]       |
| 3  |a +b           |vara[i] +varb[p]      |
| 4  |a+ b           |vara[i]+ varb[p]      |  
| 5  |a + b          |vara[i] + varb[p]     |
| 6  |a +  b         |vara[i] +  varb[p]    |
| 7  |a + b          |vara[i]  + varb[p]    |
| 8  |a  +  b        |vara[i]  +  varb[p]   |
| 9  |               |var[ i ]              |
| 10 |               |var[  i  ]            |
| 11 |               |var[i ]               |
| 12 |               |var[ i]               |