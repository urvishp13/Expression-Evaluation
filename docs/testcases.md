# Test Cases

### buildSymbols()

Note: The underscores in the test cases are spaces: "_" is one space and "__" is two spaces. Spaces were put in the test cases to see if 
non-letter/non-numeric characters are properly skipped when parsing the expression.

There is no return value for this expression. Instead, it is used to extract the variables/arrays from the expresssion. Its correctness
was tested using the printScalars() and printArrays() methods provided by the coursework.

|    | Expression    | Expression           |
|:--:|:-------------:|:--------------------:|
|    | **Variables Only** | **Variables and Arrays** |
| 1  |`a`              |`var[i]`                |
| 2  |`a+b`            |`vara[i]+varb[p]`       |  
| 3  |`a_+_b`          |`vara[i]_+_varb[p]`     |
| 4  |`a_+__b`         |`vara[i]_+__varb[p]`    |
| 5  |`a__+_b`         |`vara[i]__+_varb[p]`    |
| 6  |`a__+__b`        |`vara[i]__+__varb[p]`   |
| 7  |`a_+b`           |`vara[i]_+varb[p]`      |
| 8  |`a+_b`           |`vara[i]+_varb[p]`      |
| 9  |               |`var[_ i_]`              |
| 10 |               |`var[__ i__]`            |
| 11 |               |`var[i_]`               |
| 12 |               |`var[_i]`               |

Note: In #9 and #10, the `i`'s have a non-"_"/non-"__" space in front of them to circumvent them being styled; the spaces used in the test cases
are still one/two, respectively.

### evaluate()

The database (.txt file used) with the variables/arrays values is `etest3.txt`.

#### Guide

`#6 through #11`  ==> all combinations of operations work on integer/scalar terms</br>
`#21`             ==> subexpression with 1 integer/scalar</br>
`#20`             ==> subexpression with expression</br>
`#12 through #22` ==> identify subexpressions in the front, middle, and end of expression with all operators

`#23`             ==> identify an array</br>
`#24`             ==> identify an array with a longer name</br>
`#23 through #31` ==> identify arrays in the front, middle, and end of expression with all operators</br>


`#32 through #35` ==> identify expressions with arrays/subexpressions nested inside array/subexpression</br>
`#33 and #35`     ==> can extract content in between opening and closing square brackets</br>

`#36 through #38` ==> everything together</br>

`#42 through #47` ==> edge cases</br>
`#42`             ==> array subscript < 0</br>
`#43`             ==> divide by 0</br>
`#44`             ==> divide 0 by any number</br>
`#45`             ==> try expression with multidigit integers</br>
`#46`             ==> try expression with spaces & tabs</br>
`#47`             ==> try expression with scalar of negative value

|    | Expression    | Value |
|:--:|:-------------:|:-----:|
|1|`a`|`1`|
|2|`a+b`|`3`|
|3|`a-b`|`-1`|
|4|`a*b`|`2`|
|5|`a/b`|`0.5`|
|6|`a+b-c`|`0`|
|7|`a+b*c`|`7`|
|8|`a+b/c`|`1.67`|
|9|`a*b+c`|`5`|
|10|`a/b-c`|`-2.5`|
|11|`a*b/c`|`0.67`|
|12|`(a+b)+c`|`6`|
|13|`a+(b+c)`|`6`|
|14|`a-(b+c)+d`|`0`|
|15|`(a*b)*c`|`6`|
|16|`a*(b*c)`|`6`|
|17|`a/(b/c)*d`|`0.67`|
|18|`(a*b)+(c/d)`|`2.75`|
|19|`(a+b)/(c-d)`|`-3`|
|20|`(a+b)`|`3|
|21|`(a)`|`1`|
|22|`(a+b)*(c-d)-(e/f)`|`-3.83`|
|23|`A[4]`|`5`|
|24|`arraya[4]`|`5`|
|25|`b+A[4]`|`7`|
|26|`A[4]+a`|`6`|
|27|`a-A[4]+b`|`-2`|
|28|`a*B[4]`|`11`|
|29|`B[4]/b`|`5.5`|
|30|`a/A[4]*b`|`0.4`|
|31|`arraya[3]*A[4]-B[4]`|`9`|
|32|`((a*b)-c)`|`-1`|
|33|`A[arraya[4]]`|`6`|
|34|`(a+((b-c(*d)/e)`|`0.2`|
|35|`B[arraya[A[1]]]`|`10`|
|36|`a+B[4]*(d-e)`|`-10`|
|37|`(b/c-arraya[1]*A[5]`|`-8`|
|38|`A[a+(b*c)/d-e]`|`NaN`|
|39|`A[a+b]+B[c+d]`|`18`|
|40|`A[j]`|`NaN`|
|41|`A[10]`|`NaN (index too large)`|
|1|`a+b`|3|
|1|`a+b`|3|
|1|`a+b`|3|
|1|`a+b`|3|
|1|`a+b`|3|
|1|`a+b`|3|
|1|`a+b`|3|
|1|`a+b`|3|
|1|`a+b`|3|
|1|`a+b`|3|
|1|`a+b`|3|
|1|`a+b`|3|
|1|`a+b`|3|
|1|`a+b`|3|
|1|`a+b`|3|
|1|`a+b`|3|
|1|`a+b`|3|
|1|`a+b`|3|
|1|`a+b`|3|
|1|`a+b`|3|
|1|`a+b`|3|
|1|`a+b`|3|
|1|`a+b`|3|
|1|`a+b`|3|
|1|`a+b`|3|
|1|`a+b`|3|
|1|`a+b`|3|
|1|`a+b`|3|
|1|`a+b`|3|
|1|`a+b`|3|
|1|`a+b`|3|
|1|`a+b`|3|
|1|`a+b`|3|
|1|`a+b`|3|
|1|`a+b`|3|
|1|`a+b`|3|
|1|`a+b`|3|
|1|`a+b`|3|
|1|`a+b`|3|
|1|`a+b`|3|
|1|`a+b`|3|
|1|`a+b`|3|
|1|`a+b`|3|
|1|`a+b`|3|
|1|`a+b`|3|
|1|`a+b`|3|