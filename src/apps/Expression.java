package apps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

import structures.Stack;

/**
 * @author Urvish Patel
 * @author ru-nb-cs112
 *
 */

public class Expression {

	/**
	 * Expression to be evaluated
	 */
	String expr;                
    
	/**
	 * Scalar symbols in the expression 
	 */
	ArrayList<ScalarSymbol> scalars;   
	
	/**
	 * Array symbols in the expression
	 */
	ArrayList<ArraySymbol> arrays;
    
	/**
	 * Positions of opening brackets
	 */
	ArrayList<Integer> openingBracketIndex; 
    
	/**
	 * Positions of closing brackets
	 */
	ArrayList<Integer> closingBracketIndex; 

    /**
     * String containing all delimiters (characters other than variables and constants), 
     * to be used with StringTokenizer
     */
    public static final String delims = " \t*+-/()[]";
    
    /**
     * Initializes this Expression object with an input expression. Sets all other
     * fields to null.
     * 
     * @param expr Expression
     */
    public Expression(String expr) {
        this.expr = expr;
        scalars = null;
        arrays = null;
        openingBracketIndex = null;
        closingBracketIndex = null;
    }

    /**
     * Matches parentheses and square brackets. Populates the openingBracketIndex and
     * closingBracketIndex array lists in such a way that closingBracketIndex[i] is
     * the position of the bracket in the expression that closes an opening bracket
     * at position openingBracketIndex[i]. For example, if the expression is:
     * <pre>
     *    (a+(b-c))*(d+A[4])
     * </pre>
     * then the method would return true, and the array lists would be set to:
     * <pre>
     *    openingBracketIndex: [0 3 10 14]
     *    closingBracketIndex: [8 7 17 16]
     * </pre>
     * 
     * Square brackets are only used for subscripts (eg. the 4 in A[4]) and subscript expressions of arrays.
     * 
     * See the FAQ in project description for more details.
     * 
     * @return True if brackets are matched correctly, false if not
     */
    public boolean isLegallyMatched() {
    	// COMPLETE THIS METHOD
    	
    	// Create a Stack of Brackets
    	Stack<Bracket> openingBracketStack = new Stack<Bracket>();
    	// Initialize opening- and closing- BracketIndex ArrayLists
    	openingBracketIndex = new ArrayList<Integer>();
    	closingBracketIndex = new ArrayList<Integer>();
    	
    	// Traverse expression
    	for (int i = 0; i < expr.length(); i++) {
    		char currentChar = expr.charAt(i);
    		// If find '(' OR '['
    		if (currentChar == '(' || currentChar == '[') {
    			// Remember that it is found (put in openingBracketStack with index location)
    			openingBracketStack.push(new Bracket(currentChar, i));
    		}
    		// Else if, find ')' OR ']'
    		else if (currentChar == ')' || currentChar == ']') {
    			// If front of openingBracketStack is '(' and current char is ')
    			//		0R front of openingBracketStack is '[' and current char is ']'
    			if ((openingBracketStack.peek().ch == '(' && currentChar == ')')
    					|| (openingBracketStack.peek().ch == '[' && currentChar == ']')) {
    				// Add location of ]/) (current index) to closingBracketIndex
    				closingBracketIndex.add(i);
    				// Add index of front element in openingBracketStack to openingBracketIndex
    				openingBracketIndex.add(openingBracketStack.peek().pos);
    				// Stop remembering occurrence of [/( (pop [/( from openingBracketStack)
    				openingBracketStack.pop();
    			}
    		}
    	}
    	
    	// For debugging purposes
    	//System.out.println("openingBracketIndex: " + openingBracketIndex.toString());
    	//System.out.println("closingBracketIndex: " + closingBracketIndex.toString());
    	
    	// If openingBracketStack isEmpty()
    	if (openingBracketStack.isEmpty()) {
    		// Return true
    		return true;
    	}
        // Else, unmatched brackets in Stack
    	return false;
    }

    /**
     * Populates the scalars and arrays lists with symbols for scalar and array
     * variables in the expression. For every variable, a SINGLE symbol is created and stored,
     * even if it appears more than once in the expression.
     * At this time, values for all variables are set to
     * zero - they will be loaded from a file in the loadSymbolValues method.
     * 
     * KEY POINT: There may be any number of spaces or tabs BETWEEN ANY PAIR of tokens in the expression. 
     * Tokens are variable names, constants, parentheses, square brackets, and operators.
     * 
     * 
     */
    public void buildSymbols() {
    	// COMPLETE THIS METHOD
    	
    	scalars = new ArrayList<ScalarSymbol>();
    	arrays = new ArrayList<ArraySymbol>();
    	
    	StringTokenizer st = new StringTokenizer(expr, delims, true);
    	
    	String currentToken = null;
    	// Create a scalarOrIntegerRemaining flag, and set it equal to false
		boolean scalarOrIntegerRemaining = false;
    	// while there are tokens left in the Tokenizer
    	while (st.hasMoreTokens()) {
    		// Capture the next (or first) token, and Store it in currentToken
    		currentToken = st.nextToken();
    		// while the currentToken is a delimiter
    		//		AND more tokens remain
    		while (delims.indexOf(currentToken) != -1 && st.hasMoreTokens()) {
    			// Go on to the next token
    			currentToken = st.nextToken();
    		}
    		
    		/* At this point, the currentToken is a variable name (scalar), array name, or integer */
    	
    		String nextToken;
    		// If there are more tokens
    		if (st.hasMoreTokens()) {
    			// Get the next token (will be a delimiter), and Store it in nextToken
    			nextToken = st.nextToken();
    		}
    		// Else, (there are no more tokens left but there is a hanging currentToken that has a delimiter or
    		// 		scalar/integer value--no array-name value because, if currentToken was an array name, it would have a
    		//		delimiter (specifically, a square bracket) following it)
    		else {
    			// If the currentToken is not a delimiter
    			if (delims.indexOf(currentToken) == -1) {
    				// Update the scalarOrIntegerRemaining flag = true
    				scalarOrIntegerRemaining = true;
    			}
    			// There is no next token to analyze, so break
    			nextToken = null;
    			break;
    		}
    	
    		// If the nextToken is an opening square bracket
    		if (nextToken.equals("[")) {
    			// The currentToken is an array name, so Store it in arrays, if it does not exist already
    			String arrayName = currentToken;
    			ArraySymbol as = new ArraySymbol(arrayName);
    			if (arrays.indexOf(as) == -1) {
    				arrays.add(as);
    			}
    		}
    		// Else, (the nextToken is any remaining delimiter option--doesn't matter which)
    		else {
    			/* The currentToken is a scalar/integer */
    			// If the currentToken is not an integer
    			if (!currentToken.matches("[0-9]+")) {
    				// The currentToken is a scalar, so Store it in scalars, if it does not exist already
    				String scalarName = currentToken;
    				ScalarSymbol ss = new ScalarSymbol(scalarName);
    				if (scalars.indexOf(ss) == -1) {
    					scalars.add(ss);
    				}
    			}
    		}
    	}
    	
    	/* Reached the end of the expression */
    	
    	// If scalarOrIntegerRemaining
    	if (scalarOrIntegerRemaining) {
	    	/* The currentToken is a scalar/integer */
			// If the currentToken is not an integer
    		if (!currentToken.matches("[0-9]+")) {
				// The currentToken is a scalar, so Store it in scalars, if it does not exist already
				String scalarName = currentToken;
				ScalarSymbol ss = new ScalarSymbol(scalarName);
				if (scalars.indexOf(ss) == -1) {
					scalars.add(ss);
				}
			}
    	}
    	
    	//printScalars();
    	//printArrays();
    }
    
    /**
     * Loads values for symbols in the expression
     * 
     * KEY POINT: In the file to load values from, don't have a variable (scalar) and an array have the same name (eg.
     * don't have a variable named "a" and an array named "a"). Doing so will throw the loadSymbolValues() into
     * confusion.
     * 
     * @param sc Scanner for values input
     * @throws IOException If there is a problem with the input 
     */
    public void loadSymbolValues(Scanner sc) 
    throws IOException {
        while (sc.hasNextLine()) {
            StringTokenizer st = new StringTokenizer(sc.nextLine().trim());
            int numTokens = st.countTokens();
            String sym = st.nextToken();
            ScalarSymbol ssymbol = new ScalarSymbol(sym);
            ArraySymbol asymbol = new ArraySymbol(sym);
            int ssi = scalars.indexOf(ssymbol);
            int asi = arrays.indexOf(asymbol);
            if (ssi == -1 && asi == -1) {
            	continue; // skip this token because it is a variable that is in the file but not in the expression
            }
            int num = Integer.parseInt(st.nextToken());
            if (numTokens == 2) { // scalar symbol
                scalars.get(ssi).value = num; // give the scalar variable its value
            } else { // array symbol
            	asymbol = arrays.get(asi);
            	asymbol.values = new int[num];
                // following are (index,val) pairs
                while (st.hasMoreTokens()) {
                    String tok = st.nextToken();
                    StringTokenizer stt = new StringTokenizer(tok," (,)");
                    int index = Integer.parseInt(stt.nextToken());
                    int val = Integer.parseInt(stt.nextToken());
                    asymbol.values[index] = val; // give the array variable its valueS    
                }
            }
        }
    }
    
    /**
     * Evaluates the expression, using RECURSION to evaluate subexpressions and to evaluate array 
     * subscript expressions.
     * 
     * KEY POINT #1: The minus sign is constricted to subtraction only; it cannot be used to change the 
     * sign of a variable or integer i.e. change it from positive (or negative) to negative (or positive).
     * Example: -1 and -a is invalid but 2-1 and a-1 is valid.
     * 
     * KEY POINT #2: There may any number of spaces or tabs BETWEEN ANY PAIR of tokens in the expression. 
     * Tokens are variable names, constants, parentheses, square brackets, and operators. EXCEPTION: For arrays, there
     * cannot be a space between the array name and its opening square bracket.
     * 
     * KEY POINT #3: The expression can have arrays of integers, indexed with a constant (integer)
     * or a subexpression.
     * 
     * @return Result of evaluation
     */
    public float evaluate() {
    	// COMPLETE THIS METHOD
    	
    	Stack<Float> operands = new Stack<Float>();
    	Stack<Character> operators = new Stack<Character>();
    	
    	// Save the expr in originalExpr
    	String originalExpr = expr;
    	// Save the original opening- and closing-BracketIndex ArrayLists
    	ArrayList<Integer> originalOpeningBracketIndex = openingBracketIndex;
    	ArrayList<Integer> originalClosingBracketIndex = closingBracketIndex;
    	//System.out.println("current expression: " + originalExpr);
    	
    	// Set the index = 0
    	int currentIndex = 0;
    	// Go through each index in the originalExpr
    	while (currentIndex < originalExpr.length()) {
    		/* Get the next term in the originalExpr 
    		 *		Term can be: integer/scalar (variable)/subexpression/array with integer or subexpression
    		 *		as subscript
    		 */
    		// Set termIsSubexpression = false
    		boolean termIsSubexpression = false;
    		// Set termIsArray = false
    		boolean termIsArray = false;
	    	// If the current index is a whitespace or a tab, (removes whitespaces/tabs in the beginning
    		//		of the term)
    		if (originalExpr.charAt(currentIndex) == ' ' || originalExpr.charAt(currentIndex) == '\t') {
				// Go on to the next index
    			currentIndex = currentIndex+1;
    			// Continue
    			continue;
    		}
    		/* Find the index of the next operator from the current index.
    		 * The current index is that of a term.
    		 */
    		int nextOperatorIndex;
    		// If an opening parenthesis is at the current index, the term is a subexpression
    		if (originalExpr.charAt(currentIndex) == '(') {
    			// Find the opening parenthesis' associated closing parenthesis' index
    			int openingParenIndexInOriginalExpr = currentIndex;
    			//System.out.println("openingParenIndexInOriginalExpr: " + openingParenIndexInOriginalExpr);
    			int openingParenIndexInBracketArrayList = originalOpeningBracketIndex.indexOf(openingParenIndexInOriginalExpr);
    			//System.out.println("openingParenIndexInBracketArrayList: " + openingParenIndexInBracketArrayList);
    			int closingParenIndexInOriginalExpr = originalClosingBracketIndex.get(openingParenIndexInBracketArrayList);
    			// Find the index of the next operator from the closing parenthesis' index
    			nextOperatorIndex = getNextOperatorIndex(originalExpr, closingParenIndexInOriginalExpr);
    			// Set termIsSubexpression = true
    			termIsSubexpression = true;
    		}
    		// Else if, the term has an opening square bracket 
    		//		AND the opening bracket's index < the index of the next operator, the term is an array
    		else if ( (originalExpr.indexOf('[', currentIndex) != -1)
    				&& (originalExpr.indexOf('[', currentIndex) < getNextOperatorIndex(originalExpr, currentIndex)) ) { 
    			// Find the opening square bracket's associated closing square bracket index
    			int openingSquareBracketIndexInOriginalExpr = originalExpr.indexOf('[', currentIndex);
    			int openingSquareBracketIndexInBracketArrayList = originalOpeningBracketIndex.indexOf(openingSquareBracketIndexInOriginalExpr);
    			int closingSquareBracketIndexInOriginalExpr = originalClosingBracketIndex.get(openingSquareBracketIndexInBracketArrayList);
				// Find the index of the next operator from the closing square bracket's index
    			nextOperatorIndex = getNextOperatorIndex(originalExpr, closingSquareBracketIndexInOriginalExpr);
				// Set termIsArray = true
    			termIsArray = true;
    		}
    		// Else, (the current index is of an integer/scalar)
    		else {
    			// Find the index of the next operator from the current index
    			nextOperatorIndex = getNextOperatorIndex(originalExpr, currentIndex);
    		}
    	
    		/* Get the term */
    		float termValue;
    		// Get the term (operand) from the current index to the next operator index
    		String term = originalExpr.substring(currentIndex, nextOperatorIndex);
    		// Trim the term of its whitespaces/tabs (removes whitespaces/tabs at the end of the term)
    		term = term.trim();
    		// If termsIsSubexpression (RECURSION CASE)
    		if (termIsSubexpression) {
    			// Extract the content between the term's opening paren and its associated closing paren 
    			// 		from the current index using the original opening and closing bracket indexes, 
    			//		and Store in expr
    			int openingParenIndexInOriginalExpr = currentIndex;
    			int openingParenIndexInBracketArrayList = originalOpeningBracketIndex.indexOf(openingParenIndexInOriginalExpr);
    			int closingParenIndexInOriginalExpr = originalClosingBracketIndex.get(openingParenIndexInBracketArrayList);
    			expr = originalExpr.substring(openingParenIndexInOriginalExpr+1, closingParenIndexInOriginalExpr);
    			// Trim expr of its whitespaces/tabs
    			expr = expr.trim();
    			//System.out.println("encountered subexpression: " + expr + ", recursing");
    			// Find the opening and closing brackets of the new expr using isLegallyMatched()
    			isLegallyMatched();
    			// evaluate() the subexpression, and Store in termValue
    			termValue = evaluate();
    		}
    		// Else if, termIsArray (RECURSION CASE)
    		else if (termIsArray) {
    			int openingSquareBracketIndexInOriginalExpr = originalExpr.indexOf('[', currentIndex);
    			int openingSquareBracketIndexInBracketArrayList = originalOpeningBracketIndex.indexOf(openingSquareBracketIndexInOriginalExpr);
    			int closingSquareBracketIndexInOriginalExpr = originalClosingBracketIndex.get(openingSquareBracketIndexInBracketArrayList);
		    	// Extract the name of the array
    			String arrayName = originalExpr.substring(currentIndex, openingSquareBracketIndexInOriginalExpr);
				// Extract the subscript (an expression) of the array by making the content in between the brackets 
				// 		the new expr
    			expr = originalExpr.substring(openingSquareBracketIndexInOriginalExpr+1, closingSquareBracketIndexInOriginalExpr);
    			//System.out.println("encountered subexpression: " + expr + ", recursing");
    			// Find the opening and closing brackets of the new expr using isLegallyMatched()
    			isLegallyMatched();
    			// evaluate() the content, and Store the returned value in arrayIndex.
    			int arrayIndex = (int) evaluate();
    			//System.out.println("subscript value: " + arrayIndex);
				// Find the array name in arrays and use the arrayIndex to find the termValue in the array
    			ArraySymbol array = new ArraySymbol(arrayName);
    			int arrayi = arrays.indexOf(array);
    			try {
    				termValue = arrays.get(arrayi).values[arrayIndex];
    			}
    			catch (NullPointerException e) { // in case when no values file is entered
    				termValue = 0;
    			}
//    			catch (ArrayIndexOutOfBoundsException e) { // in case when arrayIndex < 0 or > array length
//    				termValue = Float.NaN;
//    			}
    		}
    		// Else, (the term is an integer/scalar) (BASE CASE)
    		else {
    			// get-the-TermValue() of the term, and Store in termValue
    			termValue = getTermValue(term); 
    		}
    		// Push the termValue into the operands Stack
    		operands.push(termValue);
    		//System.out.println("pushed term: " + term + "=" + termValue);
    		
    		/* Get the operator */
    		// If the operator exists i.e. its index doesn't equal originalExpr's length()
    		if (nextOperatorIndex != originalExpr.length()) {
    			// Push it into the operators Stack
    			char operator = originalExpr.charAt(nextOperatorIndex);
    			operators.push(operator);
    			// Go on to the index of the content after the operator i.e. the next term or 1 + the 
    			// 		operator's index
    			currentIndex = nextOperatorIndex + 1;
    			//System.out.println("pushed operator: " + operator);
    		}
    		// Else, (reached the end of the expression)
    		else {
    			// Set index equal to the next operator index
    			currentIndex = nextOperatorIndex;
    		}
    	
			/* peek() at what the most recent (top) operator in the operators Stack is; 
			 * while the operators Stack is not empty AND while the top (next) operator is a * or /
			 */
    		while (!operators.isEmpty() &&
    				(operators.peek() == '*' || operators.peek() == '/')) {
				/* Get the next term in the originalExpr */
    			// Set termIsSubexpression = false
    			termIsSubexpression = false;
    			// Set termIsArray = false
    			termIsArray = false;
    			
    			// while the content at the current index is a space or a tab (removing the whitespaces/tabs
    			//		at the beginning of the term)
    			while (originalExpr.charAt(currentIndex) == ' ' || originalExpr.charAt(currentIndex) == '\t') {
    				// Skip it
    				currentIndex = currentIndex + 1; 
    			}
    			
		    	/* Find the index of the next operator from the current index.
				 * The current index is that of a term.
				 */
	    		// If an opening parenthesis is at the current index, the term is a subexpression
	    		if (originalExpr.charAt(currentIndex) == '(') {
	    			// Find the opening parenthesis' associated closing parenthesis' index
	    			int openingParenIndexInOriginalExpr = currentIndex;
	    			int openingParenIndexInBracketArrayList = originalOpeningBracketIndex.indexOf(openingParenIndexInOriginalExpr);
	    			int closingParenIndexInOriginalExpr = originalClosingBracketIndex.get(openingParenIndexInBracketArrayList);
	    			// Find the index of the next operator from the closing parenthesis' index
	    			nextOperatorIndex = getNextOperatorIndex(originalExpr, closingParenIndexInOriginalExpr);
	    			// Set termIsSubexpression = true
	    			termIsSubexpression = true;
	    		}
	    		// Else if, the term has an opening square bracket 
	    		//		AND the opening bracket's index < the index of the next operator, the term is an array
	    		else if ( (originalExpr.indexOf('[', currentIndex) != -1)
	    				&& (originalExpr.indexOf('[', currentIndex) < getNextOperatorIndex(originalExpr, currentIndex)) ) { 
	    			// Find the opening square bracket's associated closing square bracket index
	    			int openingSquareBracketIndexInOriginalExpr = originalExpr.indexOf('[', currentIndex);
	    			int openingSquareBracketIndexInBracketArrayList = originalOpeningBracketIndex.indexOf(openingSquareBracketIndexInOriginalExpr);
	    			int closingSquareBracketIndexInOriginalExpr = originalClosingBracketIndex.get(openingSquareBracketIndexInBracketArrayList);
					// Find the index of the next operator from the closing square bracket's index
	    			nextOperatorIndex = getNextOperatorIndex(originalExpr, closingSquareBracketIndexInOriginalExpr);
					// Set termIsArray = true
	    			termIsArray = true;
	    		}
	    		// Else, (the current index is of an integer/scalar)
	    		else {
	    			// Find the index of the next operator from the current index
	    			nextOperatorIndex = getNextOperatorIndex(originalExpr, currentIndex);
	    		}
	    	
	    		/* Get the next term */
	    		float nextTermValue;
				// Get the next term (operand) from the current index to the next operator index
	    		String nextTerm = originalExpr.substring(currentIndex, nextOperatorIndex);
		    	// Trim the next term of its whitespaces/tabs (removes whitespaces/tabs at the end of the term)
	    		nextTerm = nextTerm.trim();
				// If the next termsIsSubexpression
	    		if (termIsSubexpression) {
	    			// Extract the content between the next term's opening paren and its associated closing paren 
	    			// 		from the current index using the original opening and closing bracket indexes, 
	    			//		and Store in expr
	    			int openingParenIndexInOriginalExpr = currentIndex;
	    			int openingParenIndexInBracketArrayList = originalOpeningBracketIndex.indexOf(openingParenIndexInOriginalExpr);
	    			int closingParenIndexInOriginalExpr = originalClosingBracketIndex.get(openingParenIndexInBracketArrayList);
	    			expr = originalExpr.substring(openingParenIndexInOriginalExpr+1, closingParenIndexInOriginalExpr);
	    			// Trim expr of its whitespaces/tabs
	    			expr = expr.trim();
	    			//System.out.println("encountered subexpression: " + expr + ", recursing");
	    			// Find the opening and closing brackets of the new expr using isLegallyMatched()
	    			isLegallyMatched();
	    			// evaluate() the subexpression, and Store in nextTermValue
	    			nextTermValue = evaluate();
	    		}
	    		// Else if, the next termIsArray
	    		else if (termIsArray) {
	    			int openingSquareBracketIndexInOriginalExpr = originalExpr.indexOf('[', currentIndex);
	    			int openingSquareBracketIndexInBracketArrayList = originalOpeningBracketIndex.indexOf(openingSquareBracketIndexInOriginalExpr);
	    			int closingSquareBracketIndexInOriginalExpr = originalClosingBracketIndex.get(openingSquareBracketIndexInBracketArrayList);
			    	// Extract the name of the array
	    			String arrayName = originalExpr.substring(currentIndex, openingSquareBracketIndexInOriginalExpr);
					// Extract the subscript (an expression) of the array by making the content in between the brackets 
					// 		the new expr
	    			expr = originalExpr.substring(openingSquareBracketIndexInOriginalExpr+1, closingSquareBracketIndexInOriginalExpr);
	    			//System.out.println("encountered subexpression: " + expr + ", recursing");
	    			// Find the opening and closing brackets of the new expr using isLegallyMatched()
	    			isLegallyMatched();
	    			// evaluate() the content, and Store the returned value in arrayIndex.
	    			int arrayIndex = (int) evaluate();
	    			//System.out.println("subscript value: " + arrayIndex);
					// Find the array name in arrays and use the arrayIndex to find the nextTermValue in that array
	    			ArraySymbol array = new ArraySymbol(arrayName);
	    			int arrayi = arrays.indexOf(array);
	    			try {
	    				nextTermValue = arrays.get(arrayi).values[arrayIndex];
	    			}
	    			catch (NullPointerException e) { // in case when no values file is entered
	    				nextTermValue = 0;
	    			}
//	    			catch (ArrayIndexOutOfBoundsException e) { // in case when arrayIndex < 0
//	    				nextTermValue = Float.NaN;
//	    			}
	    		}
	    		// Else, (the next term is an integer/scalar)
	    		else {
	    			// get-the-TermValue() of the next term, and Store in nextTermValue
	    			nextTermValue = getTermValue(nextTerm); 
	    		}
	    		
	    		//System.out.println("got nextTerm: " + nextTerm + "=" + nextTermValue);
				// Pop the top (previous) term in the operands Stack
	    		float previousTermValue = operands.pop();
				// Pop the top operator (* or /)
	    		char operator = operators.pop();
				// Perform the operation on previous and next terms
	    		float result;
	    		if (operator == '*') {
	    			result = previousTermValue * nextTermValue;
	    		}
	    		else { // else the top operator is a division sign
	    			result = previousTermValue / nextTermValue;
	    		}
				// Push the result into the operands Stack 
	    		operands.push(result);
    			//System.out.println("operation: " + previousTermValue + " " + operator + " " + nextTermValue);
    			//System.out.println("result: " + result + ", pushed result");
    			
		    	/* Get the next operator */
	    		// If the operator exists i.e. its index doesn't equal originalExpr's length()
	    		if (nextOperatorIndex != originalExpr.length()) {
	    			// Push it into the operators Stack
	    			operator = originalExpr.charAt(nextOperatorIndex);
	    			operators.push(operator);
	    			// Go on to the index of the content after the operator i.e. the next term or 1 + the 
	    			// 		operator's index
	    			currentIndex = nextOperatorIndex + 1;
	    			//System.out.println("pushed operator: " + operator);
	    		}
	    		// Else, (reached the end of the expression)
	    		else {
	    			// Set index equal to the next operator index
	    			currentIndex = nextOperatorIndex;
	    		}
	    		
    		}
    			
    	}
    	
    	// Only + and - signs left in the operator Stack so start operating on the operands
		// While the operator Stack is not empty
		while (!operators.isEmpty()) {
			//System.out.println("adding/subtracting terms");
			//System.out.println("remaining operators: " + operators.size());
			// Pop the first term in the operand Stack
			float secondTerm = operands.pop(); // called "second" because it appears later in the expression
			// If the next operator is -
			if (!operators.isEmpty() && operators.peek() == '-') {
				// Make the firstTerm negative
				secondTerm = -1 * secondTerm;
				// The minus sign (negative) has been accounted for, so pop it and replace it with a +
				operators.pop();
				operators.push('+');
			}
			// Pop the top operator in the operator Stack (a plus sign)
			char operator = operators.pop();
			// Pop the second term in the operand Stack
			float firstTerm = operands.pop();
			// If the next operator is -
			if (!operators.isEmpty() && operators.peek() == '-') {
				// Make the firstTerm negative
				firstTerm = -1 * firstTerm;
				// The minus sign (negative) has been accounted for, so pop it and replace it with a +
				operators.pop();
				operators.push('+');
			}
			// Perform the operation
			//System.out.println("secondTerm: " + secondTerm);
			//System.out.println("firstTerm: " + firstTerm);
			//System.out.println("operation: " + firstTerm + " " + operator + " " + secondTerm);
			
			// Accounted for the negative signs, so only plus signs remain
			float result = firstTerm + secondTerm;
			
			// Push the result in the operand Stack
			operands.push(result);
			//System.out.println("pushed result: " + result);
			//System.out.println("remaining operators: " + operators.size());
		}
		
		// Pop the value in the operand Stack (only 1 left) and return it
		//System.out.println("evaluated (sub)expression: " + originalExpr + ", returning result: " + operands.peek());  
		
		// Set the current opening- and closing-BracketIndex ArrayLists equal to the original opening- and 
    	// 		closing-BracketIndex ArrayLists
    	openingBracketIndex = originalOpeningBracketIndex;
    	closingBracketIndex = originalClosingBracketIndex;
    	// Print the opening- and closing-BrackedIndexes, for verification purposes
    	//System.out.println("openingBracketIndex: " + openingBracketIndex.toString());
    	//System.out.println("closingBracketIndex: " + closingBracketIndex.toString());
    	
    	return operands.pop();
    }
    
    /**
     * 
     * @param expr Expression to search for an operator in
     * @param i index at and after which to look for an operator
     * @return the index of the next operator (can also be the index of an operator in a subexpression)
     */
    private int getNextOperatorIndex(String expr, int i) {
    	// Find the operator that EXISTS in the expr
    	// And find the operator that comes BEFORE all the EXISTING operators
    	
    	int doesntExist = expr.length(); // used expr's length() as the initial value because at the 
    									 // end of the expression, there is no operator present 
    									 // (only a term)
    	
    	int plusOperatorIndex = doesntExist;
    	int minusOperatorIndex = doesntExist;
    	int multOperatorIndex = doesntExist;
    	int diviOperatorIndex = doesntExist;
    	
    	if (expr.indexOf("+", i) != -1) { // if plus operator exists
    		plusOperatorIndex = expr.indexOf("+", i);
    	}
    	if (expr.indexOf("-", i) != -1) { // if minus operator exists
    		minusOperatorIndex = expr.indexOf("-", i);
    	}
    	if (expr.indexOf("*", i) != -1) { // if multiplication operator exists
    		multOperatorIndex = expr.indexOf("*", i);
    	}
    	if (expr.indexOf("/", i) != -1) { // if division operator exists
    		diviOperatorIndex = expr.indexOf("/", i);
    	}
    	
    	// Only one operator can come before all the other operators
    	
    	// If no operators exist
    	if (plusOperatorIndex == doesntExist && minusOperatorIndex == doesntExist
    			&& multOperatorIndex == doesntExist && diviOperatorIndex == doesntExist) {
    		return doesntExist;
    	}
    	
    	// If three of the operators exist
    	if (plusOperatorIndex == doesntExist) {
    		// If two of the operators exist
    		if (minusOperatorIndex == doesntExist) {
    			// If one operator exists
    			if (multOperatorIndex == doesntExist) {
    				return diviOperatorIndex;
    			}
    			else if (diviOperatorIndex == doesntExist) {
    				return multOperatorIndex;
    			}
    			// Else, mult, and divi operators exist
        		return Math.min(diviOperatorIndex, multOperatorIndex);
    		}
    		else if (multOperatorIndex == doesntExist) {
    			if (minusOperatorIndex == doesntExist) {
    				return diviOperatorIndex;
    			}
    			else if (diviOperatorIndex == doesntExist) {
    				return minusOperatorIndex;
    			}
    			// Else, minus and divi operators exist
        		return Math.min(diviOperatorIndex,minusOperatorIndex);
    		}
    		else if (diviOperatorIndex == doesntExist) {
    			if (minusOperatorIndex == doesntExist) {
    				return multOperatorIndex;
    			}
    			else if (multOperatorIndex == doesntExist) {
    				return minusOperatorIndex;
    			}
    			// Else, minus and mult operators exist
        		return Math.min(multOperatorIndex,minusOperatorIndex);
    		}
    		// Else, minus, mult, and divi operators exist
    		return Math.min(diviOperatorIndex,Math.min(multOperatorIndex,minusOperatorIndex));
    	}
    	// If three of the operators exist
    	else if (minusOperatorIndex == doesntExist) {
    		// If two of the operators exist
    		if (plusOperatorIndex == doesntExist) {
    			// If one operator exists
    			if (multOperatorIndex == doesntExist) {
    				return diviOperatorIndex;
    			}
    			else if (diviOperatorIndex == doesntExist) {
    				return multOperatorIndex;
    			}
    			// Else, mult, and divi operators exist
        		return Math.min(diviOperatorIndex, multOperatorIndex);
    		}
    		else if (multOperatorIndex == doesntExist) {
    			if (plusOperatorIndex == doesntExist) {
    				return diviOperatorIndex;
    			}
    			else if (diviOperatorIndex == doesntExist) {
    				return plusOperatorIndex;
    			}
    			// Else, plus and divi operators exist
        		return Math.min(diviOperatorIndex,plusOperatorIndex);
    		}
    		else if (diviOperatorIndex == doesntExist) {
    			if (plusOperatorIndex == doesntExist) {
    				return multOperatorIndex;
    			}
    			else if (multOperatorIndex == doesntExist) {
    				return plusOperatorIndex;
    			}
    			// Else, plus and mult operators exist
        		return Math.min(multOperatorIndex,plusOperatorIndex);
    		}
    		// Else, plus, mult, and divi operators exist
    		return Math.min(diviOperatorIndex,Math.min(multOperatorIndex,plusOperatorIndex));
    	}
    	// If three of the operators exist
    	else if (multOperatorIndex == doesntExist) {
    		// If two of the operators exist
    		if (minusOperatorIndex == doesntExist) {
    			// If one operator exists
    			if (plusOperatorIndex == doesntExist) {
    				return diviOperatorIndex;
    			}
    			else if (diviOperatorIndex == doesntExist) {
    				return plusOperatorIndex;
    			}
    			// Else, plus, and divi operators exist
        		return Math.min(diviOperatorIndex, plusOperatorIndex);
    		}
    		else if (plusOperatorIndex == doesntExist) {
    			if (minusOperatorIndex == doesntExist) {
    				return diviOperatorIndex;
    			}
    			else if (diviOperatorIndex == doesntExist) {
    				return minusOperatorIndex;
    			}
    			// Else, minus and divi operators exist
        		return Math.min(diviOperatorIndex,minusOperatorIndex);
    		}
    		else if (diviOperatorIndex == doesntExist) {
    			if (minusOperatorIndex == doesntExist) {
    				return plusOperatorIndex;
    			}
    			else if (plusOperatorIndex == doesntExist) {
    				return minusOperatorIndex;
    			}
    			// Else, minus and plus operators exist
        		return Math.min(plusOperatorIndex,minusOperatorIndex);
    		}
    		// Else, minus, plus, and divi operators exist
    		return Math.min(diviOperatorIndex,Math.min(plusOperatorIndex,minusOperatorIndex));
    	}
    	// If three of the operators exist
    	else if (diviOperatorIndex == doesntExist) {
    		// If two of the operators exist
    		if (minusOperatorIndex == doesntExist) {
    			// If one operator exists
    			if (multOperatorIndex == doesntExist) {
    				return plusOperatorIndex;
    			}
    			else if (plusOperatorIndex == doesntExist) {
    				return multOperatorIndex;
    			}
    			// Else, mult, and plus operators exist
        		return Math.min(plusOperatorIndex, multOperatorIndex);
    		}
    		else if (multOperatorIndex == doesntExist) {
    			if (minusOperatorIndex == doesntExist) {
    				return plusOperatorIndex;
    			}
    			else if (plusOperatorIndex == doesntExist) {
    				return minusOperatorIndex;
    			}
    			// Else, minus and plus operators exist
        		return Math.min(plusOperatorIndex,minusOperatorIndex);
    		}
    		else if (plusOperatorIndex == doesntExist) {
    			if (minusOperatorIndex == doesntExist) {
    				return multOperatorIndex;
    			}
    			else if (multOperatorIndex == doesntExist) {
    				return minusOperatorIndex;
    			}
    			// Else, minus and mult operators exist
        		return Math.min(multOperatorIndex,minusOperatorIndex);
    		}
    		// Else, minus, mult, and plus operators exist
    		return Math.min(plusOperatorIndex,Math.min(multOperatorIndex,minusOperatorIndex));
    	}
    	// Else, If all the operators exist
    	return Math.min(diviOperatorIndex,Math.min(multOperatorIndex,Math.min(plusOperatorIndex,minusOperatorIndex)));
    }
    
    /**
     * 
     * @param term The integer/scalar to get the integer value of
     * @return the int value of the term that is currently a String
     */
    private int getTermValue(String term) {
    	int termValue;
		// If the term in an integer
		if (term.matches("[0-9]+")) {
			termValue = Integer.parseInt(term);
		}
		// Else, the term is a scalar
		else {
			termValue = getScalarValue(term);
		}
		return termValue;
    }
    
    /**
     * 
     * @param variable The String representation of the variable to get the value of
     * @return the int value of the variable
     */
    private int getScalarValue(String variable) {
		ScalarSymbol ss = new ScalarSymbol(variable);
		int ssi = scalars.indexOf(ss);
		
		return scalars.get(ssi).value;
    }

    /**
     * Utility method, prints the symbols in the scalars list
     */
    public void printScalars() {
        for (ScalarSymbol ss: scalars) {
            System.out.println(ss);
        }
    }
    
    /**
     * Utility method, prints the symbols in the arrays list
     */
    public void printArrays() {
    	for (ArraySymbol as: arrays) {
    		System.out.println(as);
    	}
    }

}
