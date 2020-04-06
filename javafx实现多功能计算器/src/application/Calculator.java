package application;

import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

public class Calculator {
	ArrayList<String> arrayList = new ArrayList<String>();// ԭʼ�ָ����ŵĶ�̬����
	Stack<String> operationStack = new Stack<String>();// ����ջ
	ArrayList<String> niBrolandArrayList = new ArrayList<String>();// �沨�����ʽ
	Stack<String> stack = new Stack<String>();
	public String calculate(String expression) {
		// ȥ�����еĿո�
		// ��ʽ���ж�
		String regex = "[\\(]*\\-?[\\d]+[\\.]?[\\d]*([\\)]*[\\+\\-\\*\\/]([\\(]*[\\-]?[\\d]+[\\.]?[\\d]*))*[//)]*";
		if (!expression.matches(regex)) {
			System.out.println("FORMAT ERROR!");
			System.exit(0);
		} else {
			expression = expression.replaceAll(" ", "");
			// ������ �� - ��
			// -2.5+3.2*(-5+4)/3/(-2)*9 ---> 0-2.5+3.2*(0-5+4)/3/(0-2)*9
			expression = addZero(expression);
			// �ָ�ԭ���ʽ
			getPartOperation(expression);
			for (int i = 0; i < arrayList.size(); i++) {
				// �ж��Ƿ��������
				if (isOperation(arrayList.get(i))) {
					symbolStack(arrayList.get(i));
				}
				// ������ֱ�����沨�����ʽ����,�����пո�
				else if (!isOperation(arrayList.get(i)) && !(" ".equals(arrayList.get(i)))) {
					niBrolandArrayList.add(arrayList.get(i));
				}
			}
			// �����ջ��Ϊ��
			// ���ѷ���ջ�ķ��ų�ջ���ӵ��沨�����ʽ��
			while (!operationStack.isEmpty()) {
				niBrolandArrayList.add(operationStack.pop());
			}
			for (int i = 0; i < niBrolandArrayList.size(); i++) {
				// �Ƿ�Ϊ����,����ջ
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
			// ���ջ�е������ǽ��
		}
		return stack.pop();
	}

	// �� "- ��"ǰ����� 0 ����������� ( - ����ǰ����0
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

	// ջ����������һ��������ļ���
	// ���ؼ������ַ���
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
				System.out.println("Error��Divided by zero��");
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

	// �ָ�ԭ���ʽ
	public void getPartOperation(String expression) {
		// ��+-*/����Ϊ�ָ������ԭʼ���ʽ���ַ��������зָ�����طָ��
		StringTokenizer stringTokenizer = new StringTokenizer(expression, "\\+|\\-|\\*|\\/|\\(|\\)|\\!|\\^| ", true);
		// boolean hasMoreElements()�������Ƿ��зָ���
		while (stringTokenizer.hasMoreTokens()) {
			// stringTokenizer.nextToken()��ָ���ķָ������ؽ��
			arrayList.add(stringTokenizer.nextToken());
		}
	}

	// �жϷ������
	public boolean isOperation(String string) {
		boolean isflag = "+-*/()%".indexOf(string) >= 0;
		if (isflag) {
			return true;
		} else {
			return false;
		}
	}

	// �жϷ��ŵ����ȼ�����ջ
	public void symbolStack(String string) {
		// ջΪ��,����ֱ����ջ
		if (operationStack.isEmpty()) {
			operationStack.push(string);
		}
		// ����������ֱ����ջ
		else if (string.equals("(")) {
			operationStack.push(string);
		}
		// ����������,�������ڵ�ȫ����ջ�����������,������������֮ǰ
		else if (string.equals(")")) {
			String str;
			str = operationStack.pop();
			while (!str.equals("(")) {
				niBrolandArrayList.add(str);
				str = operationStack.pop();
			}
		}
		// ջ�������ȼ���󣬷�����ջ
		else if (compelstr(string, operationStack.peek())) {
			operationStack.push(string);
		}
		// ջ�������ȼ�С��ջ�ڵķ��ų�ջ�������沨�����ʽ�����У�һֱ�Ƚ�
		else if (!compelstr(string, operationStack.peek())) {
			niBrolandArrayList.add(operationStack.pop());
			symbolStack(string);
		}
		// ������ջ�������Ƿ�Ϊ ( ,�����ֱ����ջ
		else if (operationStack.peek().equals("(")) {
			operationStack.push(string);
		}
	}

	// �������ȼ���С, + - ���ȼ���ȣ�����ҪС�� * /
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

	// �Ƚ����ȼ���С
	public boolean compelstr(String string1, String string2) {
		boolean flag = false;
		if (getPriority(string1) > getPriority(string2)) {
			flag = true;
		}
		return flag;
	}
}