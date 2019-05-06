package com.robynn;

import java.util.concurrent.*;

public class ActionCalc {
	
	// JSON PIECES
	static String D_QUOTE = "\"";
	static String COLON = ":";
	static String L_BRACE = "{";
	static String R_BRACE = "}";
	static String L_SQUARE = "[";
	static String R_SQUARE = "]";
	static String COMMA = ", ";
	static String ACTION = "action";
	static String TIME="time";
	static String AVG = "avg";
	static String JSON_LEFT = L_BRACE + D_QUOTE + ACTION + D_QUOTE + COLON + D_QUOTE;
	static String JSON_IN_MID = D_QUOTE + COMMA + D_QUOTE + TIME + D_QUOTE + COLON;
	static String JSON_OUT_MID = D_QUOTE + COMMA + D_QUOTE + AVG + D_QUOTE+ COLON;
	static String JSON_RIGHT = R_BRACE;
	
	// Class Action holds the running average.
	private class Action {
		String name;
		int count;
		int average;
	}

	// Map to hold averages
    ConcurrentHashMap<String, Action> actionMap;
	
	public ActionCalc() {
		actionMap = new ConcurrentHashMap<String, Action>();
	}
	
	public int addAction(String actionStr) {
		Boolean formatOK = checkFormat(actionStr);
		if(!formatOK){
			System.out.println("Unable to add action: " + actionStr + " .  Errors found in string.");
			return -1;
		}
		
		// Add the item to the collection
		try{
			synchronized (new Object()) {
				updateActionMap(actionStr);
			}
			
		}
		catch (Exception e){
			System.out.println(e.getMessage());
			return -2;
		}
		
		return 0;
	}
	
	
	public String getStats() {
		try{
			// Lock the collection and create the output.
			synchronized (new Object()) {
				StringBuilder statsOut = makeJson();
				return statsOut.toString();	
			}	
		}
		catch (Exception e) {

			String msg = e.getMessage();
			System.out.println(e.getMessage());
			return msg;
		}
	}
	
	private void updateActionMap(String actionStr) {
		
		    // Get the important information from the input String
			String actionName = getNameFromInput(actionStr);
			int timeVal = getTimeValFromInput(actionStr);
			
			// If something went wrong getting the value from the input string,
			// print an error and continue;
			if(actionName.isEmpty() || timeVal <0 ) {
				System.out.print("An error occurred parsing input string: " + actionStr);
				System.out.println(". Item not added");
				return;
			}
			
			// Check to see if the map already has the
			// action.
			Action action = actionMap.get(actionName);
			if(action == null){
				action = new Action();
				action.name = actionName;
			    action.average = timeVal;
			    action.count =1;
			    actionMap.put(actionName, action);
			}
			else {
				// Remove the action from the map (and preserve)
				Action saveAction = actionMap.remove(actionName);
				
				// Calculate the new average
				int currentAverage = action.average;
				int currentCount = action.count;
				int sum = (currentAverage * currentCount) + timeVal;
				action.average = Math.round(sum/(currentCount+1));
				
				// If a math error occurs, skip the calc and put the old item back.
				if(action.average == Integer.MIN_VALUE || 
			       action.average == Integer.MAX_VALUE) {
					System.out.print("Error calculating average for item: " + actionStr);
					System.out.println(". Item omitted.");
					actionMap.put(actionName, saveAction);
				}
				else {
					// Put the action back with new avg
					action.count = action.count+1;
					actionMap.put(actionName, action);	
				}
				
			}
	}
	
	// Create the output string for the call to getStats()
	private StringBuilder makeJson() {
		StringBuilder json = new StringBuilder();
		json.append(L_SQUARE);
		Boolean firstEntry = true;
		
		// Loop through hashmap & serialize
		for(String key : actionMap.keySet())
		{
			Action a = actionMap.get(key);
			if(a != null)
			{
				// Start with a comma 
				// unless it's the first
				String entry = ",";
				if(firstEntry)
				{
					entry = "";
					firstEntry= false;
				}
				Integer avg = a.average;
				entry += JSON_LEFT;
				entry += key;
				entry += JSON_OUT_MID;
				entry += avg;
				entry += JSON_RIGHT;
				json.append(entry);
			}
		}
		json.append(R_SQUARE);
		return json;
	}
	
	private String getNameFromInput(String inputStr) {
		String actionName = "";
		
		// Calculate the position of the action name.
		int startIndex = inputStr.indexOf(JSON_LEFT) + JSON_LEFT.length();
		int endIndex   = inputStr.indexOf(JSON_IN_MID);
		
		// If the position looks ok, pull out the action name
		if(startIndex >=0 && endIndex >0  && startIndex < endIndex ) {

			try {
				actionName = inputStr.substring(startIndex, endIndex);
			}
			catch(IndexOutOfBoundsException e){
				System.out.println("Error parsing action name from input string: " + inputStr);
			}
		}

		return actionName;
	}
	
	// Get the int value for time from the JSON
	// input string.
	private int getTimeValFromInput(String inputStr) {
		
		// Initialize return error
		int val = -1;
		
		// Isolate the position of the time value
		int startIndex = inputStr.indexOf(JSON_IN_MID) + JSON_IN_MID.length();
		int endIndex = inputStr.indexOf(JSON_RIGHT);
		
		// If indexes OK, pull out the value
		if(startIndex >=0 && endIndex >0  && startIndex < endIndex ) {

     		String valStr = "";		
			try
			{
				// Get the value and convert to Integer
				valStr = inputStr.substring(startIndex, endIndex);
				val = Integer.valueOf(valStr.toString());
			}
			catch (IndexOutOfBoundsException oob) {
				System.out.println("Error parsing action time from input: " + inputStr);
				val = -1;
			}
			catch (NumberFormatException nf)
			{
				System.out.println("Error converting time to integer from input: " + inputStr);
				val = -1;
			}
		}
		
		// Auto-unbox
		return val;
	}	
	
	// JSON Validation Method
	private Boolean checkFormat(String actionString) { 
		Boolean formatOK = false;
		
		// Calculate positions of expected strings
		int leftFormatStartPos = actionString.indexOf(JSON_LEFT);
		int midFormatStartPos = actionString.indexOf(JSON_IN_MID);
		int rightFormatStartPos = actionString.indexOf(R_BRACE);	
		int leftFormatEndPos = JSON_LEFT.length()-1;
		int midFormatEndPos = midFormatStartPos + JSON_IN_MID.length()-1;

		// Check positions for validity
		if(leftFormatStartPos == 0 &&
		   midFormatStartPos   > leftFormatEndPos + 1 &&
	       rightFormatStartPos > midFormatEndPos  + 1) {
			formatOK = true;
		}
		
		return formatOK;
	}
}
