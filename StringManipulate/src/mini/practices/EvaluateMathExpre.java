package mini.practices;

import java.util.Iterator;
import java.util.Stack;

import javax.xml.stream.events.Characters;

/*
 * Problem 3. (Evaluating Mathematical Expressions.)
Create a class MathExpression that has a single constructor that takes a String representing 
a correctly parenthesized mathematical expression. The expression consists of three 
types of charac- ters:
• Parentheses:([{[]}]) 
• Operators:*-+/
• Integers:0123456789
Your program should have two methods: (1) isBalanced, which verifies that the 
expression is cor- rectly parenthesized, i.e., every open-parenthesis has a matching
close-parenthesis of the same type; (2) calculate, which returns a double that
  represents the result of calculating the mathematical expression. (For the sake of 
  calculation, you may assume that the expression is fully parenthesized, and each 
  operator takes exactly two operands. For example: ((3+2)*(7/2)) is a valid 
  expression; however (3+2+4) is not; nor is (3+2)*7. (You do not need to deal with 
  operator-priority, since the expression is fully parenthesized.)
Hint: For calculation, use two stacks, one for the operators, and one for the values.

*/


public class EvaluateMathExpre implements Iterable<String> {
	String expression = null;
	Stack<String> stack = new Stack<String>();
	
	public Iterator<String> iterator(){
		return new MyIterator();
	}
	
	private class MyIterator implements Iterator<String>{
		int nextIndex;
		int size = 0;
		String[] expressionArr;
		
		MyIterator(){
			expressionArr = expression.split("(?<=\\D)|(?=\\D)");
			size = expressionArr.length;
			nextIndex = 0;
		}
		
		public boolean hasNext(){
			return nextIndex<size;
		}
		
		public String next(){
			
			return expressionArr[nextIndex++];
		}
	}
	
	public boolean isBalanced(String exp){
		expression = exp;
		Iterator<String> iterator = this.iterator();
		//String iterator
		
		while(iterator.hasNext()){
			String current = iterator.next();
			if(isOpenBracket(current)){
				stack.push(current);
			}
			
			if(isCloseBracket(current)){
				if(!match(current,stack.peek())) return false;
			}
		}
		
		return true;

	}
	
	public int calculate(String exp){
		Stack<String> intStack = new Stack<String>();
		Stack<String> operatorStack = new Stack<String>(); 
		String currentChar;
		
		Iterator<String> iterator = this.iterator();
		 
		while(iterator.hasNext()){
			
			currentChar = iterator.next();
			
			if(Character.isDigit(currentChar.charAt(0)) || isOpenBracket(currentChar)){
				intStack.push(currentChar);
			}
			else if(isCloseBracket(currentChar)){
				intStack.push(String.valueOf(calculate(intStack, operatorStack)));
			}
			else
				operatorStack.push(currentChar);
		}
		
		return Integer.parseInt(intStack.pop());
	}
	
	private int calculate(Stack<String> intStack,Stack<String> operatorStack){
		String operator = operatorStack.pop();
		int b = Integer.parseInt(intStack.pop());
		int a = Integer.parseInt(intStack.pop());
		if(operator.equals("*")) return a*b;
		if(operator.equals("+")) return a+b;
		if(operator.equals("-")) return a-b;
		if(operator.equals("/")) return a/b;
		
		//if the code reaches here, should throw an exception.
		return 0;
		
	}
	
	private boolean isOpenBracket(String ch){
		return ch.equals("(") || ch.equals("{") || ch.equals("[");
	}
	
	private boolean isCloseBracket(String ch){
		return ch.equals(")") || ch.equals("}") || ch.equals("]");
	}
	
	private boolean match(String closeBracket, String openBracket){
		if(closeBracket.equals(")")) return openBracket.equals("(");
		if(closeBracket.equals("}")) return openBracket.equals("{");
		if(closeBracket.equals("]")) return openBracket.equals("[");
		//error, closeBracket is not either of the bracket. Omit error handling here since the question specifies it will be correctly parathensized. 
		
		return false;
	}
	
	public static void main(String[] args) {
		EvaluateMathExpre ev = new EvaluateMathExpre();
		
		System.out.println(ev.isBalanced("(7*5)"));
		System.out.println(ev.calculate("7*5"));
	}
}
