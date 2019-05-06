# JCProj
Action Calculator

Overview:
The ActionCalc class is a small thread-safe class library that accepts JSON-formatted entries of action/time pairs and, on demand, supplies JSON-formatted output containing averages for each action.

ActionCalc.java may be compiled into any executable or test program with no external dependencies. 

To use:

Option 1: Compile into your executable 

Copy ActionCalc.java into your executable.  Change the package name as desired.
Instantiate an ActionCalc() object by calling the no argument constructor.
Call ActionCalc.addAction(String) with a formatted input string.
Call ActionCalc.getStats() to retrieve JSON-formatted output with containing the average action times.

Option 2: Compile and test using the included TestRunner.java in Eclipse.
Clone the JCProj repository.
Open as a project in Eclipse.
Modify the test cases in Tester.java and the number of threads in TestRunner.java as desired.
Execute TestRunner.main() and view the console output to track the proress of additions and requests for stats.

Option 3: Build, Package, add to your Classpath
To be added.

Assumptions: 
Action times must be integers.  
Floating point averages were not necessary.
Rounded integer averages were sufficient.
It is assumed that an action and a time must be one character in length at minimum.
Variations in JSON spacing are not fully supported (in the interest of time as I wrote my own parsing routine to avoid dependencies on external libraries).
It is assumed that the number of threads accessing a single instance of ActionCalc will be low, in general.  Otherwise, an alternative solution is suggested.  See the Strategies section below for more information.
If there is an error finding or converting the value of the time entry, an error is printed and the action is not added.

Error Conditions: 
An error will be printed if the input time is not an integer.
An error will occur if the input string is badly formatted.
If addAction() succeeds, 0 will be returned;
If addAction() detects a badly formatted entry, an error will be printed to System.out and  -1 will be returned.
If an unexpected error occurs during addAction(), an error will be printed to System.out and the method will return -2;
If an unexpected error occurs during getStats, an error will be returned in place of the JSON string.
If a the addition of a new action causes a calculation to fail with a math error, an error is printed to System.out and the calculation is skipped.  The action’s average stays as it was

Strategies:
A larger load would require a more robust solution such as a web service.  The approach to the more robust solution would depend upon minimum and maximum expected load and whether one of the two methods in the api would be called more frequently than the other.

For example, I imagined that if it was a priority to ingest a lot of data from multiple sources quickly, that a separately threaded consumer started by ActionCalc would be a possible solution.  Input JSON would be immediately put in a Queue and the consumer in the  separate thread could pop items off and run calculations separately.  (An experiment along these lines is partially coded here in a separate class).

Tests:
TestRunner.java contains 16 threads running the following tests:

Happy path
Time value is missing
Time = -1
Time = 0
Time value is long
Time value is float
JSON missing a quote
JSON missing an expected tag
