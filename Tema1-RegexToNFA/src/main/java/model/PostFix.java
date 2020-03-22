package model;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class PostFix {

    /** operators precedence map. */
    private static final Map<Character, Integer> precedenceMap = new HashMap<>();

    static {
        precedenceMap.put('(', 1);
        precedenceMap.put('|', 2);
        precedenceMap.put('.', 3); // explicit concatenation operator
        precedenceMap.put('*', 4);
        precedenceMap.put('^', 5);
    };

    /** get character precedence */
    private static Integer getPrecedence(Character c) {
        Integer precedence = precedenceMap.get(c);
        if (precedence == null)
            return 6;
        return precedence;
    }

    /** adding '.' as symbol for concatenation */
    private static String formatRegEx(String regex) {
        StringBuilder res = new StringBuilder();
        List<Character> allOperators = Arrays.asList('|', '*', '^');
        List<Character> binaryOperators = Arrays.asList('^', '|');

        for (int i = 0; i < regex.length(); i++) {
            Character c1 = regex.charAt(i);
            if (i + 1 < regex.length()) {
                Character c2 = regex.charAt(i + 1);
                res.append(c1);
                if (!c1.equals('(') && !c2.equals(')') && !allOperators.contains(c2) && !binaryOperators.contains(c1)) {
                    res.append('.');
                }
            }
        }
        res.append(regex.charAt(regex.length() - 1));

        return res.toString();
    }

    /** convert regular expression from infix to postfix notation using Shunting-yard algorithm */
    public static String infixToPostfix(String regex) {
        StringBuilder postfix = new StringBuilder();

        Stack<Character> stack = new Stack<Character>();

        String formattedRegEx = formatRegEx(regex);

        for (Character c : formattedRegEx.toCharArray()) {
            switch (c) {
                case '(':
                    stack.push(c);
                    break;

                case ')':
                    while (!stack.peek().equals('(')) {
                        postfix.append(stack.pop());
                    }
                    stack.pop();
                    break;

                default:
                    while (stack.size() > 0) {
                        Character peekedChar = stack.peek();

                        Integer peekedCharPrecedence = getPrecedence(peekedChar);
                        Integer currentCharPrecedence = getPrecedence(c);

                        if (peekedCharPrecedence >= currentCharPrecedence) {
                            postfix.append(stack.pop());
                        } else {
                            break;
                        }
                    }
                    stack.push(c);
                    break;
            }

        }

        while (stack.size() > 0)
            postfix.append(stack.pop());

        return postfix.toString();
    }

    /** convert regular expression from prefix to postfix*/
    public static String prefixToPostfix(String regex){
        Stack<String> s= new Stack<String>();
        int length = regex.length();
        for (int i = length - 1; i >= 0; i--)
        {
            // check if symbol is operator
            if (precedenceMap.containsKey(regex.charAt(i)))
            {
                if (i == 0){
                    String op1 = s.peek(); s.pop();
                    String temp = op1 + regex.charAt(i);
                    s.push(temp);
                }
                else {
                    String op1 = s.pop();
                    String op2 = s.pop();
                    String temp = op1 + op2 + regex.charAt(i);
                    s.push(temp);
                }
            }
            else
                s.push( regex.charAt(i)+"");

        }
        return s.peek();
    }

    public static Map<Character, Integer> getPrecedenceMap() {
        return precedenceMap;
    }
}
