package com.robynn;

import java.io.*;
import java.util.concurrent.*;
import java.util.*;

// This was the beginning of an experimental ActionCalc
// that started a separate thread to process the input


public class ActionCalcWithQueue {
//	
//	class ActionProcessor implements Runnable{
//		
//		public volatile boolean exit = false;
//		
//		private BlockingQueue<String> inputQueue;
//		private ConcurrentHashMap<String,ArrayList<Integer>> rawData = new ConcurrentHashMap<String, ArrayList<Integer>>();
//		private ArrayList<String> statsOut;
//		
//		public ActionProcessor(BlockingQueue<String> inputQueue, ArrayList<String> statsOut){
//			this.inputQueue = inputQueue;
//			this.statsOut = statsOut;
//		}
//		
//		private void popAndParse(){
//			System.out.print("Processor thread peeking ");
//			String actionType = "";
//			try
//			{
//				if(inputQueue.peek() == null){
//					System.out.println("and there's nothing to see. ");
//					return;
//				}
//				String inputString = inputQueue.poll();
//
//				System.out.println("and found: " + inputString);
//				// todo: implement this instead
//				if(inputString != null){
//					actionType = "jump";
//					calcAndRecord(actionType);
//				}
//			}
//			catch (Exception e)
//			{
//				System.out.println("error");
//			}
//			return;
//		}
//		
//		private void calcAndRecord(String actionType){
//			synchronized (this)
//			{
//				System.out.println("Processor thread adding row to output");
//				statsOut.add("{some stuff}");		
//			}
//		}
//		
//		public void run(){
//			System.out.println("Processor thread started");
//			while(!exit)
//			{
//				popAndParse();
//			}
//		}
//		
//	    public void stop(){
//	    	exit = true;
//	    }
//	}
//
//	private BlockingQueue<String> inputQueue;
//	private ArrayList<String> statsOut;
//	private ActionProcessor ap;
//	
//	public ActionCalcWithQueue(){
//		inputQueue = new LinkedBlockingQueue<String>();
//		statsOut = new ArrayList<String>();  // Change to linked list
//		ap = new ActionProcessor(inputQueue, statsOut);
//		new Thread(ap).start();
//	}
//	
//	public void addAction(String actionStr){
//		
//		inputQueue.add(actionStr);
//		
//	}
//	
//	public String getStats(){
//		return statsOut.toString();
//	}
//	
//	public void finalize(){
//		if(ap != null)
//		{
//			ap.stop();			
//		}
//	}
//	
}
