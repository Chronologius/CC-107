package com.bigo143.budgettracker;

import java.util.*;

public class ExpressionEvaluator {

    public static double eval(String expr) {
        if (expr == null || expr.trim().isEmpty()) return 0;
        return evaluate(infixToRPN(tokenize(expr)));
    }

    // Tokenize numbers and operators
    private static List<String> tokenize(String s) {
        List<String> out = new ArrayList<>();
        StringBuilder num = new StringBuilder();
        for (int i=0;i<s.length();i++){
            char c = s.charAt(i);
            if (Character.isDigit(c) || c=='.') { num.append(c); continue; }
            if (num.length()>0){ out.add(num.toString()); num.setLength(0);}
            if (c=='+'||c=='-'||c=='*'||c=='/'||c=='('||c==')') out.add(Character.toString(c));
            // ignore spaces
        }
        if (num.length()>0) out.add(num.toString());
        return out;
    }

    private static int prec(String op){
        if (op.equals("+")||op.equals("-")) return 1;
        if (op.equals("*")||op.equals("/")) return 2;
        return 0;
    }

    private static List<String> infixToRPN(List<String> tokens) {
        List<String> out = new ArrayList<>();
        Deque<String> stack = new ArrayDeque<>();
        for (String t: tokens) {
            if (t.matches("\\d+(\\.\\d+)?")) out.add(t);
            else if (t.equals("(")) stack.push(t);
            else if (t.equals(")")) {
                while(!stack.isEmpty() && !stack.peek().equals("(")) out.add(stack.pop());
                if (!stack.isEmpty() && stack.peek().equals("(")) stack.pop();
            } else { // operator
                while(!stack.isEmpty() && prec(stack.peek())>=prec(t)) out.add(stack.pop());
                stack.push(t);
            }
        }
        while(!stack.isEmpty()) out.add(stack.pop());
        return out;
    }

    private static double evaluate(List<String> rpn) {
        Deque<Double> st = new ArrayDeque<>();
        for (String t: rpn) {
            if (t.matches("\\d+(\\.\\d+)?")) st.push(Double.parseDouble(t));
            else {
                double b = st.pop();
                double a = st.pop();
                switch (t) {
                    case "+": st.push(a+b); break;
                    case "-": st.push(a-b); break;
                    case "*": st.push(a*b); break;
                    case "/": st.push(a/b); break;
                }
            }
        }
        return st.pop();
    }
}
