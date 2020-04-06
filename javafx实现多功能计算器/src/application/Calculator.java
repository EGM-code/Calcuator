package application;

import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

public class Calculator {
	ArrayList<String> arrayList = new ArrayList<String>();// 原始分割存符号的动态数组
	Stack<String> operationStack = new Stack<String>();// 符号栈
	ArrayList<String> niBrolandArrayList = new ArrayList<String>();// 逆波兰表达式
	Stack<String> stack = new Stack<String>();
	public String calculate(String expression) {
		// 去除所有的空格
		// 格式的判断
		String regex = "[\\(]*\\-?[\\d]+[\\.]?[\\d]*([\\)]*[\\+\\-\\*\\/]([\\(]*[\\-]?[\\d]+[\\.]?[\\d]*))*[//)]*";
		if (!expression.matches(regex)) {
			System.out.println("FORMAT ERROR!");
			System.exit(0);
		} else {
			expression = expression.replaceAll(" ", "");
			// 加上零 在 - 号
			// -2.5+3.2*(-5+4)/3/(-2)*9 ---> 0-2.5+3.2*(0-5+4)/3/(0-2)*9
			expression = addZero(expression);
			// 分割原表达式
			getPartOperation(expression);
			for (int i = 0; i < arrayList.size(); i++) {
				// 判断是否是运算符
				if (isOperation(arrayList.get(i))) {
					symbolStack(arrayList.get(i));
				}
				// 是数字直接如逆波兰表达式数组,这里有空格
				else if (!isOperation(arrayList.get(i)) && !(" ".equals(arrayList.get(i)))) {
					niBrolandArrayList.add(arrayList.get(i));
				}
			}
			// 如符号栈不为空
			// 最后把符号栈的符号出栈，加到逆波兰表达式中
			while (!operationStack.isEmpty()) {
				niBrolandArrayList.add(operationStack.pop());
			}
			for (int i = 0; i < niBrolandArrayList.size(); i++) {
				// 是否为符号,数入栈
				if (!(isOperation(niBrolandArrayList.get(i)))) {
					stack.push(niBrolandArrayList.get(i));
				} else if ("+".equals(niBrolandArrayList.get(i)) || "-".equals(niBrolandArrayList.get(i))) {
					String str1 = stack.pop();
					String str2 = stack.pop();
					stack.push(operCal(str1, str2, niBrolandArrayList.get(i)));
				} else if ("*".equals(niBrolandArrayList.get(i)) || "/".equals(niBrolandArrayList.get(i))) {
					String str1 = stack.pop();
					String str2 = stack.pop();
					stack.push(operCal(str1, str2, niBrolandArrayList.get(i)));
				}
			}
			// 最后栈中的数就是结果
		}
		return stack.pop();
	}

	// 在 "- 数"前面加上 0 解决，并且在 ( - 负号前加上0
	public String addZero(String expression) {
		String expresson_1 = "";
		char[] temp = new char[expression.length()];
		for (int i = 0; i < expression.length(); i++) {
			temp[i] = expression.charAt(i);
		}
		for (int i = 0; i < temp.length; i++) {
			if (temp[i] == '-' && (i == 0 || temp[i - 1] == '(')) {
				expresson_1 += "0";
			}
			expresson_1 += temp[i];
		}
		return expresson_1;
	}

	// 栈出两个数，一个运算符的计算
	// 返回计算后的字符串
	public String operCal(String numStr1, String numStr2, String operStr) {
		String result2 = "";
		double num1 = Double.parseDouble(numStr1);
		double num2 = Double.parseDouble(numStr2);
		if (operStr.equals("+")) {
			num1 = num1 + num2;
			result2 = num1 + "";
		} else if (operStr.equals("-")) {
			num1 = num2 - num1;
			result2 = num1 + "";
		} else if (operStr.equals("*")) {
			num1 = num2 * num1;
			result2 = num1 + "";
		} else if (operStr.equals("/")) {
			if (num1 == 0) {
				System.out.println("Error：Divided by zero！");
				System.exit(0);
			} else {
				num1 = num2 / num1;
				result2 = num1 + "";
			}
		} else if (operStr.equals("%")) {
			num1 = num2 % num1;
			result2 = num1 + "";
		}
		return result2;
	}

	// 分割原表达式
	public void getPartOperation(String expression) {
		// 以+-*/（）为分割符，对原始表达式（字符串）进行分割，并返回分割符
		StringTokenizer stringTokenizer = new StringTokenizer(expression, "\\+|\\-|\\*|\\/|\\(|\\)|\\!|\\^| ", true);
		// boolean hasMoreElements()：返回是否还有分隔符
		while (stringTokenizer.hasMoreTokens()) {
			// stringTokenizer.nextToken()以指定的分隔符返回结果
			arrayList.add(stringTokenizer.nextToken());
		}
	}

	// 判断符号与否
	public boolean isOperation(String string) {
		boolean isflag = "+-*/()%".indexOf(string) >= 0;
		if (isflag) {
			return true;
		} else {
			return false;
		}
	}

	// 判断符号的优先级并入栈
	public void symbolStack(String string) {
		// 栈为空,符号直接入栈
		if (operationStack.isEmpty()) {
			operationStack.push(string);
		}
		// 遇到左括号直接入栈
		else if (string.equals("(")) {
			operationStack.push(string);
		}
		// 遇到右括号,把括号内的全部出栈存放在数组里,再遇到左括号之前
		else if (string.equals(")")) {
			String str;
			str = operationStack.pop();
			while (!str.equals("(")) {
				niBrolandArrayList.add(str);
				str = operationStack.pop();
			}
		}
		// 栈顶的优先级最大，符号入栈
		else if (compelstr(string, operationStack.peek())) {
			operationStack.push(string);
		}
		// 栈顶的优先级小，栈内的符号出栈，存入逆波兰表达式数组中，一直比较
		else if (!compelstr(string, operationStack.peek())) {
			niBrolandArrayList.add(operationStack.pop());
			symbolStack(string);
		}
		// 最后就是栈顶符号是否为 ( ,运算符直接入栈
		else if (operationStack.peek().equals("(")) {
			operationStack.push(string);
		}
	}

	// 设置优先级大小, + - 优先级相等，但是要小于 * /
	public int getPriority(String str) {
		switch (str) {
		case "+":
		case "-":
			return 1;
		case "/":
		case "*":
		case "%":
			return 2;
		default:
			break;
		}
		return 0;
	}

	// 比较优先级大小
	public boolean compelstr(String string1, String string2) {
		boolean flag = false;
		if (getPriority(string1) > getPriority(string2)) {
			flag = true;
		}
		return flag;
	}
}