# Expression Evaluation

This project is solely written in Java. Please run it using Java 8.

## Overview

The focus of this project is to evaluate algebraic/numeric/algebraic-numeric 
expressions that have variables and/or constants of integer value. The expressions were to be solved using Stacks and Recursion.

## Code & Test Cases

The code written can be found in the [src](https://github.com/urvishp13/Expression-Evaluation/tree/main/src) folder. Look for the 
`@author Urvish Patel` tag before Classes to see the code I have written.
The code was tested using self-generated test cases that can be found in [this](https://github.com/urvishp13/Expression-Evaluation/blob/main/docs/testcases.md) 
file. Expressions can be inputted when prompted in the (eg. terminal) after running the program. 
Only use variables that are in your variable-value text file--this file is basically like a database; 
if variables other than the ones listed in this file are used, you will
get an evaluation but it will be incorrect (just a note if you're trying to test an edge case). If you want to create your own text file, 
simply create a text file and follow the the instructions on 
how to format the content under the "Running the evaluator" section in [problem_specs](https://github.com/urvishp13/Expression-Evaluation/blob/main/docs/problem_specs.pdf) 
file. The files for the text files I used are in the [data](https://github.com/urvishp13/Expression-Evaluation/tree/main/data) folder. 

## How to Test

To test the program, follow the instructions presented to you in the 
(eg. command line, console in Eclipse, etc.) when running the program. 

If running the program using the command line, go into the `bin` 
folder of this repo and type `java apps.Evaluator`--as the .class files are already there.

To access the text files when running the program, you will have to backout/follow-directory-paths until you are 
in the directory of that file (eg. in the command line, you'll have to use `../data/<file_name>.txt`; in Eclipse, you'll have to use 
`data/<file_name>.txt`, etc.).

### Input Restrictions

`KEY POINT #1`: There may be any number of spaces or tabs BETWEEN ANY PAIR of tokens in the expression. Tokens are variable names, constants, 
parentheses, square brackets, and operators.

`KEY POINT #2`: In the variable-value file, don't have a scalar and an array with the same name (eg.
don't have a scalar named "a" and an array named "a"). Doing so will throw the loadSymbolValues() into confusion.

`KEY POINT #3`: The minus sign in the expression is constricted to subtraction only; it cannot be used to change the 
sign of a variable or integer i.e. it cannot change the value from positive (or negative) to negative (or positive).
Example: -1 and -a are invalid but 2-1 and 1-a are valid.

`KEY POINT #4`: For arrays, there cannot be a space between the array name and its opening square bracket.