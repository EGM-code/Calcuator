package application;

import javafx.fxml.FXML;

import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javafx.event.ActionEvent;

public class MysceneController {
	@FXML
	private DatePicker date2;

	@FXML
	private DatePicker date1;
	@FXML
	private DatePicker date;
	@FXML
	private TextField text3;// 第二个日期

	@FXML
	private TextField text4;// 计算的日期差

	@FXML
	private TextField text2;// 第一个日期

	@FXML
	private TextField text5;// 计算差天数

	@FXML
	private TextField text;// 显示计算结果

	@FXML
	void eventButton_CE(ActionEvent event) {
		text.setText("");
	}

	@FXML
	void eventButton_C(ActionEvent event) {
		String text1 = text.getText();
		if (text1.length() != 0) {
			text1 = text1.substring(0, text1.length() - 1);
			text.setText("");
			text.appendText(text1);
		}
	}

	@FXML // %按键
	void eventButton(ActionEvent event) {
//	    	String ss2 = ((Button) event.getSource()).getText();
//	    	text.appendText(ss2);
	}

	@FXML
	void Div_eventButton(ActionEvent event) {
		getDate(event);
	}

	@FXML
	void Eigth_eventButton(ActionEvent event) {
		getDate(event);
	}
	public void getDate(ActionEvent event) {
		String ss2 = ((Button) event.getSource()).getText();
		text.appendText(ss2);
	}
	@FXML
	void Seventh_eventButton(ActionEvent event) {
		getDate(event);
	}

	@FXML
	void Add_eventButton(ActionEvent event) {
		getDate(event);
	}

	@FXML
	void Mul_eventButton(ActionEvent event) {
		getDate(event);
	}

	@FXML
	void Ninth_eventButton(ActionEvent event) {
		getDate(event);
	}

	@FXML
	void One_eventButton(ActionEvent event) {
		getDate(event);
	}

	@FXML
	void Fourth_eventButton(ActionEvent event) {
		getDate(event);
	}

	@FXML
	void Two_eventButton(ActionEvent event) {
		getDate(event);
	}

	@FXML
	void Fiveth_eventButton(ActionEvent event) {
		getDate(event);
	}

	@FXML
	void Three_eventButton(ActionEvent event) {
		getDate(event);
	}

	@FXML
	void Sixth_eventButton(ActionEvent event) {
		getDate(event);
	}

	@FXML
	void Sub_eventButton(ActionEvent event) {
		getDate(event);
	}

	@FXML
	void EQ_eventButton(ActionEvent event) {
		String expression = text.getText();
		expression = expression.replaceAll("×", "*");
		expression = expression.replaceAll("÷", "/");
		text.setText("");
		Calculator calculator = new Calculator();
		String string = calculator.calculate(expression);
		text.appendText(string);
	}

	@FXML
	void point_eventButton(ActionEvent event) {
		getDate(event);
	}

	@FXML
	void Zero_eventButton(ActionEvent event) {
		getDate(event);
	}

	// System.out.println(date.getValue().toString());
	@FXML
	void Cal_event(ActionEvent event) throws ParseException {
		text4.setText("");
		text5.setText("");
		String string1 = date1.getValue().toString();
		String string2 = date2.getValue().toString();
		if (string1.length() != 0 && string2.length() != 0) {
			// 计算天数
			text5.appendText(simpleTime(string1, string2));
			// 计算相差 年 月 周 天
			text4.appendText(getDate(string1, string2));
		}

	}

	public String simpleTime(String date1, String date2) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date begTime = dateFormat.parse(date1);
		Date endTime = dateFormat.parse(date2);
		long time = (endTime.getTime() - begTime.getTime()) / (1000 * 60 * 60 * 24);
		return Math.abs(time)+"";
	}

	public String getDate(String date1, String date2) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date begTime = dateFormat.parse(date1);
		Date endTime = dateFormat.parse(date2);
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(begTime);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(endTime);
		int year1 = calendar1.get(Calendar.YEAR);
		int month1 = calendar1.get(Calendar.MONTH);
		int day1 = calendar1.get(Calendar.DAY_OF_MONTH);
		int year2 = calendar2.get(Calendar.YEAR);
		int month2 = calendar2.get(Calendar.MONTH);
		int day2 = calendar2.get(Calendar.DAY_OF_MONTH);
		/*
		  DAY_OF_WEEK 这个周第几天 DAY_OF_YEAR 这一年第几天 1/3/5/7/8/10/12月都是31天， 4,6,9,11都是30天。
		  2月闰年为29天，否则为28天
		*/
		int year = year2 - year1;
		int month = month2 - month1;
		if (month < 0) {
			month = month + 12;
			year = year - 1;
		}
		int day = day2 - day1;
		if (day < 0) {
			month = month - 1;
			if ((month2 - 1) % 2 == 0) {
				day = day + 30;
			} else {
				day = day + 31;
			}
		}
		int week = day / 7;
		day = day - week * 7;
		String temp = "";
		temp = year + " 年 " + month + " 月 " + week + " 周  " + day + " 天";
		if (month < 0) {
			temp = "ERROR";
		}
		return temp;
	}

}
