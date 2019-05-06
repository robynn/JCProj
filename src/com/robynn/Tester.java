package com.robynn;

import java.util.ArrayList;
import java.util.Random;

import com.robynn.ActionCalc;

public class Tester implements Runnable  {
		
	    public volatile boolean exit = false;	
	
		ActionCalc calc;
		ArrayList<String> inputData;
		
		public Tester(ActionCalc calculator, ArrayList<String> inputData){
			this.calc = calculator;
			this.inputData = inputData;
		}
		
		public void run(){
		       insertAndAsk();
		}

		public void insertAndAsk(){
			Random random = new Random();			
			for(String inputString : inputData){
				calc.addAction(inputString);
				System.out.println(Thread.currentThread() + " added action " + inputString);
				if(random.nextBoolean())
				{
					askForAvgs();
				}
			}
			
		}
		
		public void askForAvgs(){
			String result = calc.getStats();
			System.out.println(Thread.currentThread() + " asked for averages: " + result);
		}
	}
