package com.baidu.ocr.demo;

import android.util.Log;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Calculator {
    public static int ConvertToInteger(Queue<Character> tmp) {
        int res = 0;
        while (!tmp.isEmpty()) {
            res = res * 10 + tmp.poll() -'0';
        }
        return res;
    }

    public static boolean Precede(char ch1, char ch2) {
        if ((ch1 == '+' || ch1 == '-') && (ch2 == '+' || ch2 == '-' || ch2 == ')')) return true;
        else if ((ch1 == '*' || ch1 == '/' || ch1 == '%') && ch2 != '(') return true;
        else return ch1 == ')' && ch2 != '(';
    }

    public static String InfixToSuffix(String exp) {
        StringBuilder output = new StringBuilder();
        Stack<Character> opr = new Stack<Character>();
        Queue<Character> tmp = new LinkedList<>();
        int bracket = 0;
        int numSub = 0;
        for (int i = 0; i < exp.length(); ++i) {
            char ch = exp.charAt(i);
            if ('0' <= ch && ch <= '9') {
                tmp.offer(ch);
            }
            else if (ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '(' || ch == ')' || ch == '%') {
                if (!tmp.isEmpty()) {
                    ++numSub;
                    output.append(ConvertToInteger(tmp));
                }
                while (!tmp.isEmpty()) {
                    tmp.poll();
                }
                while (!opr.empty() && Precede(opr.peek(), ch)) {
                    output.append(opr.peek());
                    opr.pop();
                }
                if (ch == ')') {
                    --bracket;
                    if (bracket < 0)
                        return new String("括号不匹配");
                    opr.pop();
                }
                else {
                    if (ch == '(') {
                        ++bracket;
                        ++numSub;
                }
                    --numSub;
                    opr.push(ch);
                }
            }
        }
        if (bracket != 0)
            return new String("括号不匹配");
        if (!tmp.isEmpty()) {
            ++numSub;
            output.append(ConvertToInteger(tmp));
        }
        if (numSub != 1)
            return new String("表达式错误");
        while (!opr.empty()) {
            output.append(opr.peek());
            opr.pop();
        }
        return String.valueOf(output);
    }

    public static String EvaluateSuffixExpression(String exp) {
        if (exp.equals("括号不匹配") || exp.equals("表达式错误"))
            return exp;
        Stack<Integer> num = new Stack<Integer>();
        for (int i = 0; i < exp.length(); ++i) {
            char ch = exp.charAt(i);
            if ('0' <= ch && ch <= '9') {
                num.push((int) ch - '0');
            }
            else if (ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '%') {
                int y = num.peek();
                num.pop();
                int x = num.peek();
                num.pop();
                if (ch == '+') {
                    num.push(x + y);
                }
                else if (ch == '-') {
                    num.push(x - y);
                }
                else if (ch == '*') {
                    num.push(x * y);
                }
                else if (ch == '/') {
                    if (y == 0)
                        return new String("不能除以0");
                    num.push(x / y);
                }
                else {
                    num.push(x % y);
                }
            }
        }
        return String.valueOf(num.peek());
    }

    public static String EvaluateInfixExpression(String exp) {
        Stack<Integer> num = new Stack<>();
        Stack<Character> opr = new Stack<>();
        Queue<Character> tmp = new LinkedList<>();
        int bracket = 0;
        int numSub = 0;
        for (int i = 0; i < exp.length(); ++i) {
            char ch = exp.charAt(i);
            if ('0' <= ch && ch <= '9') {
                tmp.offer(ch);
            }
            else if (ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '(' || ch == ')' || ch == '%') {
                if (!tmp.isEmpty()) {
                    ++numSub;
                    num.push(ConvertToInteger(tmp));
                }
                while (!tmp.isEmpty()) {
                    tmp.poll();
                }
                while (!opr.empty() && Precede(opr.peek(), ch)) {
                    int y = num.peek();
                    num.pop();
                    int x = num.peek();
                    num.pop();
                    char tmpOpr = opr.peek();
                    opr.pop();
                    if (tmpOpr == '+') {
                        num.push(x + y);
                    }
                    else if (tmpOpr == '-') {
                        num.push(x - y);
                    }
                    else if (tmpOpr == '*') {
                        num.push(x * y);
                    }
                    else if (tmpOpr == '/') {
                        if (y == 0)
                            return new String("不能除以0");
                        num.push(x / y);
                    }
                    else if (tmpOpr == '%') {
                        num.push(x % y);
                    }
                }
                if (ch == ')') {
                    --bracket;
                    if (bracket < 0)
                        return new String("括号不匹配");
                    opr.pop();
                }
                else {
                    if (ch == '(') {
                        ++bracket;
                        ++numSub;
                    }
                    --numSub;
                    opr.push(ch);
                }
            }
            Log.v("staack",opr.toString());
            Log.v("numsatck",num.toString());
        }
        if (bracket != 0)
            return new String("括号不匹配");
        if (!tmp.isEmpty()) {
            ++numSub;
            num.push(ConvertToInteger(tmp));
        }
        if (numSub != 1)
            //System.out.println(numSub);
            return new String("表达式错误");
        while (!opr.empty()) {
            int y = num.peek();
            num.pop();
            int x = num.peek();
            num.pop();
            char tmpOpr = opr.peek();
            opr.pop();
            if (tmpOpr == '+') {
                num.push(x + y);
            }
            else if (tmpOpr == '-') {
                num.push(x - y);
            }
            else if (tmpOpr == '*') {
                num.push(x * y);
            }
            else if (tmpOpr == '/') {
                if (y == 0)
                    return new String("不能除以0");
                num.push(x / y);
            }
            else if (tmpOpr == '%') {
                num.push(x % y);
            }
            Log.v("staack",opr.toString());
            Log.v("numsatck",num.toString());
        }
        return String.valueOf(num.peek());
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String str = in.nextLine();
        System.out.println(EvaluateInfixExpression(str));
        System.out.println(InfixToSuffix(str));
        System.out.println(EvaluateSuffixExpression(InfixToSuffix(str)));
    }
}
