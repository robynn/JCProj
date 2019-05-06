package com.robynn;

import java.util.ArrayList;

import com.robynn.Tester;

public class TestRunner {

	public static void main(String[] args) {
		
		ActionCalc calculator = new ActionCalc();
		
		ArrayList<String> inputStrings = new ArrayList<String>();
		inputStrings.add("{\"action\":\"jump\", \"time\":100}");// Happy path
		inputStrings.add("{\"action\":\"run\", \"time\":50}");  // Happy path
		inputStrings.add("{\"action\":\"jump\", \"time\":33}"); // Happy path
		inputStrings.add("{\"action\":\"run\", \"time\":22}");  // Happy path
		inputStrings.add("{\"action\":\"swim\", \"time\":40}"); // Happy path
		inputStrings.add("{\"action\":\"run\", \"time\":}");    // Time value missing
		inputStrings.add("{\"action\":\"swim\", \"time\":-1}"); // Time = -1
		inputStrings.add("{\"action\":\"run\", \"time\":0}");   // Time = 0
 		inputStrings.add("{\"action\":\"bike\", \"time\":3555555555500}");  // Time value long
		inputStrings.add("{\"action:\"run\", \"time\":7.5}");   // Time value is float
		inputStrings.add("{\"action\":\"\", \"time\":2}");      // Missing quote
		inputStrings.add("{\"action\":\"run\", \"time\":gg}");  // Time value alpha
		inputStrings.add("{\"\":\"jump\", \"time\":100}");      // Missing "action"
		inputStrings.add("{\"action\":\"run\", \"\":75}");      // Missing "time"
		
		int numThreads = 16;
		for(int i = 0; i< numThreads; i++)
		{
			new Thread(new Tester(calculator, inputStrings)).start();	
		}
		
		calculator.getStats();

	}

}
