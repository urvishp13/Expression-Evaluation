# Test Cases

## buildSymbols()

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

## evaluate()

The database (.txt file) used was [etest3.txt](https://github.com/urvishp13/Expression-Evaluation/blob/main/data/etest3.txt). Its contents
are below.

```
a 1
b 2
c 3
d 4
e 5
f 6
g 7
h 8
i 0
j -1
k -2
arraya 6 (0,1) (1,2) (2,3) (3,4) (4,5) (5,6)
A 6 (0,1) (1,2) (2,3) (3,4) (4,5) (5,6)
B 8 (0,7) (1,8) (2,9) (3,10) (4,11) (5,12) (6,13) (7,14)
```

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
|20|`(a+b)`|`3`|
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
|38|`A[a+(b*c)/d-i]`|`3`|
|39|`A[a+b]+B[c+d]`|`18`|
|40|`A[-1]`|`NaN`|
|41|`A[10]`|`NaN`|
|42|`a/0`|`Infinity`|
|43|`0/1`|`0`|
|44|`a+123+567`|`691`|
|45|`a+\tb+_c`|`6`|
|46|`A[j]`|`NaN`|
|47|`j-4`|`-5`|
|48|`a-a-a`|`-1`|

#### Guide through above Test Cases

`#6 through #11`  &rarr; all combinations of operations work on integer/scalar terms

`#21`             &rarr; subexpression with 1 integer/scalar</br>
`#20`             &rarr; subexpression with expression</br>
`#12 through #22` &rarr; identify and evaluate subexpressions in the front, middle, and end of expression with all operators

`#23`             &rarr; identify and evaluate an array</br>
`#24`             &rarr; identify and evaluate an array with a longer name</br>
`#23 through #31` &rarr; identify and evaluate arrays in the front, middle, and end of expression with all operators

`#32 through #35` &rarr; identify and evaluate expressions with arrays/subexpressions nested inside array/subexpression</br>
`#33 and #35`     &rarr; can extract content in between opening and closing square brackets</br>

`#36 through #38` &rarr; everything together

`#40 through #47` &rarr; edge cases</br>
`#40`             &rarr; array index < 0</br>
`#41`             &rarr; array index >= array size</br>
`#42`             &rarr; divide by 0</br>
`#43`             &rarr; divide 0 by any number</br>
`#44`             &rarr; try expression with multidigit integers</br>
`#45`             &rarr; try expression with spaces & tabs (the "\t" is replaced by an actual tab in the expression)</br>
`#46 and 47`      &rarr; try expression with scalar of negative value</br>
`#48`		      &rarr; try expression with multiple of the same variable

The `NaN`'s are used to circumvent an error message and make the usage of the app more fluid.