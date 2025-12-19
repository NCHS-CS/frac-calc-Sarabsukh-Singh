// Sarabsukh Singh Batra
// Period 6
// Fraction Calculator Project

import java.util.*;

// This program is a fraction calculator, taking input of fractions, considering the operator
//and returning a suitable and correct answer. It accepts mixed fractions, improper fractions, negative fractions,
//and all 4 of the arithmetic operators.
public class FracCalc {

   // It is best if we have only one console object for input
   public static Scanner console = new Scanner(System.in);
   
   // This main method will loop through user input and then call the
   // correct method to execute the user's request for help, test, or
   // the mathematical operation on fractions. or, quit.
   // DO NOT CHANGE THIS METHOD!!
   public static void main(String[] args) {
   
      // initialize to false so that we start our loop
      boolean done = false;
      
      // When the user types in "quit", we are done.
      while (!done) {
         // prompt the user for input
         String input = getInput();
         
         // special case the "quit" command
         if (input.equalsIgnoreCase("quit")) {
            done = true;
         } else if (!UnitTestRunner.processCommand(input, FracCalc::processCommand)) {
        	   // We allowed the UnitTestRunner to handle the command first.
            // If the UnitTestRunner didn't handled the command, process normally.
            String result = processCommand(input);
            
            // print the result of processing the command
            System.out.println(result);
         }
      }
      
      System.out.println("Goodbye!");
      console.close();
   }

   // Prompt the user with a simple, "Enter: " and get the line of input.
   // Return the full line that the user typed in.
   public static String getInput() 
   {
      System.out.print("Enter: ");
      String answer = console.nextLine();
      return answer;
   }
   
   // processCommand will process every user command except for "quit".
   // It will return the String that should be printed to the console.
   // This method won't print anything.
   // DO NOT CHANGE THIS METHOD!!!
   public static String processCommand(String input) {

      if (input.equalsIgnoreCase("help")) {
         return provideHelp();
      }
      
      // if the command is not "help", it should be an expression.
      // Of course, this is only if the user is being nice.
      return processExpression(input);
   }
   
   // Lots work for this project is handled in here.
   // Of course, this method will call LOTS of helper methods
   // so that this method can be shorter.
   // This will calculate the expression and RETURN the answer.
   // This will NOT print anything!
   // Input: an expression to be evaluated
   //    Examples: 
   //        1/2 + 1/2
   //        2_1/4 - 0_1/8
   //        1_1/8 * 2
   // Return: the fully reduced mathematical result of the expression
   //    Value is returned as a String. Results using above examples:
   //        1
   //        2 1/8
   //        2 1/4
   public static String processExpression(String input) 
   {
      //splits user input into 2 fractions or operands
      Scanner userInput = new Scanner(input);
      String equation = userInput.nextLine();
      String[] eqParts = equation.split(" ");
      String fraction1 = eqParts[0];
      String fraction2 = eqParts[2];

      //method call for fetching what operator it is
      String operator = fetchOperator(equation);

      //method call for fetching the important parts of the fraction
      //operand 1
      String frac1Num = fetchNum(fraction1);
      String frac1Den = fetchDen(fraction1);
      String frac1Whole = fetchWhole(fraction1);

      //method call for fetching the important parts of the fraction
      //operand 2
      String frac2Num = fetchNum(fraction2);
      String frac2Den = fetchDen(fraction2);
      String frac2Whole = fetchWhole(fraction2);

      //convert to int for simplicity in other methods.
      int whole1Int = Integer.parseInt(frac1Whole);
      int whole2Int = Integer.parseInt(frac2Whole);

      int num1Int = Integer.parseInt(frac1Num);
      int num2Int = Integer.parseInt(frac2Num);

      int den1Int = Integer.parseInt(frac1Den);
      int den2Int = Integer.parseInt(frac2Den);
      userInput.close();
      //checks if user is trying to create a fraction with 0 as denominator
      if(den1Int == 0 || den2Int == 0)
      {
         return "Cannot divide by zero";
      }

      //call each method based on what operator it is
      if(operator.equals("+"))
      {
         return addFrac(whole1Int, num1Int, den1Int, whole2Int, num2Int, den2Int);
      }
      else if(operator.equals("-"))
      {
         return subtractFrac(whole1Int, num1Int, den1Int, whole2Int, num2Int, den2Int);
      }
      else if(operator.equals("*"))
      {
         return multFrac(whole1Int, num1Int, den1Int, whole2Int, num2Int, den2Int);
      }
      else
      {
         //checks if user is trying to divide by 0
         if(whole2Int == 0 && num2Int == 0)
         {
            return "Cannot divide by zero";
         }
         else
         {
            return divideFrac(whole1Int, num1Int, den1Int, whole2Int, num2Int, den2Int);
         }
      }   
   }

   //makes the input into an array and fetches the operator
   public static String fetchOperator(String input)
   {
      Scanner userInput = new Scanner(input);
      String operator = userInput.nextLine();
      String[] eqParts = operator.split(" ");
      operator = eqParts[1];
      userInput.close();
      return operator;
   }

   //makes the input into an array and fetches the whole
   //only if a '_' is detected, or if no "/" is detected
   public static String fetchWhole(String fractionEq)
   {
      String whole = "0";

      if(fractionEq.contains("_"))
      {
         String[] fraction = fractionEq.split("_");
         whole = fraction[0];
      }

      else if(!fractionEq.contains("/"))
      {
         whole = fractionEq; 
      }

      return whole;
   }

   //makes the input into an array and fetches the numerator
   //but it also carefully splits the numerator and the whole
   // at the '_'
   public static String fetchNum(String fractionEq)
   {
      String num = "0";

      if(fractionEq.contains("_"))
      {
         String[] fraction = fractionEq.split("_");
         String[] numDen = fraction[1].split("/");
         num = numDen[0];
      }
      else if(fractionEq.contains("/"))
      {
         String[] numDen = fractionEq.split("/");
         num = numDen[0];
      }

      return num;

   }

   //makes the input into an array and fetches the denominator
   // if a denominator isn't present, it is assumed as 1
   public static String fetchDen(String fractionEq)
   {
      String[] fraction = fractionEq.split("/");
      String den;

      if(fraction.length == 2)
      {
         den = fraction[1];
      }
      else
      {
         den = "1";
      }

     return den;
   }

//method for adding the fractions together, with integer parameters that include basic fraction parts
public static String addFrac(int whole1Int, int num1Int, int den1Int, int whole2Int, int num2Int, int den2Int)
{
   //converting to improper fraction and fixing whether it adds or subtracts the whole.
   int imp1;
   if(whole1Int < 0)
   {
      imp1 = ((whole1Int * den1Int) - num1Int);
   }
   else
   {
      imp1 = ((whole1Int * den1Int) + num1Int);
   }
   int imp2;
   if(whole2Int < 0)
   {
      imp2 = ((whole2Int * den2Int) - num2Int);
   }
   else
   {
      imp2 = ((whole2Int * den2Int) + num2Int);
   }

   //calculate total numerator and denominator then FULLY simplifying them
   int totalNum = (imp1 * den2Int) + (imp2 * den1Int);
   int totalDen = (den1Int * den2Int);
   int gcf = calculateGCF(totalNum, totalDen);
   totalNum = simplify(totalNum, gcf);
   totalDen = simplify(totalDen, gcf);
   totalNum = normalize(totalNum, totalDen);
   totalDen = normalize(totalDen, totalDen);
   //recreating the improper into a fixed number
   int whole = totalNum / totalDen;
   int remainder = Math.abs(totalNum % totalDen);
   //method call to do the math and format properly
   return doMath(totalNum, totalDen, whole, remainder, den1Int, den2Int, whole2Int) + "";
}

//method for subtracting the fractions together, with integer parameters that include basic fraction parts
public static String subtractFrac(int whole1Int, int num1Int, int den1Int, int whole2Int, int num2Int, int den2Int)
{
   //converting to improper fraction and fixing whether it adds or subtracts the whole.
   int imp1;
   if(whole1Int < 0)
   {
      imp1 = ((whole1Int * den1Int) - num1Int);
   }
   else
   {
      imp1 = ((whole1Int * den1Int) + num1Int);
   }
   int imp2;
   if(whole2Int < 0)
   {
      imp2 = ((whole2Int * den2Int) - num2Int);
   }
   else
   {
      imp2 = ((whole2Int * den2Int) + num2Int);
   }

   //calculate total numerator and denominator then FULLY simplifying them
   int totalNum = (imp1 * den2Int) - (imp2 * den1Int);
   int totalDen = (den1Int * den2Int);
   int gcf = calculateGCF(totalNum, totalDen);
   totalNum = simplify(totalNum, gcf);
   totalDen = simplify(totalDen, gcf);
   totalNum = normalize(totalNum, totalDen);
   totalDen = normalize(totalDen, totalDen);
   //recreating the improper into a fixed number
   int whole = totalNum / totalDen;
   int remainder = Math.abs(totalNum % totalDen);
   //method call to do the math and format properly
   return doMath(totalNum, totalDen, whole, remainder, den1Int, den2Int, whole2Int) + "";
}

//method for multiplying the fractions together, with integer parameters that include basic fraction parts
public static String multFrac(int whole1Int, int num1Int, int den1Int, int whole2Int, int num2Int, int den2Int)
{
   //converting to improper fraction and fixing whether it adds or subtracts the whole.
   int imp1;
   if(whole1Int < 0)
   {
      imp1 = ((whole1Int * den1Int) - num1Int);
   }
   else
   {
      imp1 = ((whole1Int * den1Int) + num1Int);
   }
   int imp2;
   if(whole2Int < 0)
   {
      imp2 = ((whole2Int * den2Int) - num2Int);
   }
   else
   {
      imp2 = ((whole2Int * den2Int) + num2Int);
   }

   //calculate total numerator and denominator then FULLY simplifying them
   int totalNum = imp1 * imp2;
   int totalDen = den1Int * den2Int;
   int gcf = calculateGCF(totalNum, totalDen);
   totalNum = simplify(totalNum, gcf);
   totalDen = simplify(totalDen, gcf);
   totalNum = normalize(totalNum, totalDen);
   totalDen = normalize(totalDen, totalDen);
   //recreating the improper into a fixed number
   int whole = totalNum / totalDen;
   int remainder = Math.abs(totalNum % totalDen);
   //method call to do the math and format properly
   return doMath(totalNum, totalDen, whole, remainder, den1Int, den2Int, whole2Int) + "";
}

//method for dividing the fractions together, with integer parameters that include basic fraction parts
public static String divideFrac(int whole1Int, int num1Int, int den1Int, int whole2Int, int num2Int, int den2Int)
{
   //converting to improper fraction and fixing whether it adds or subtracts the whole.
   int imp1;
   if(whole1Int < 0)
   {
      imp1 = ((whole1Int * den1Int) - num1Int);
   }
   else
   {
      imp1 = ((whole1Int * den1Int) + num1Int);
   }
   int imp2;
   if(whole2Int < 0)
   {
      imp2 = ((whole2Int * den2Int) - num2Int);
   }
   else
   {
      imp2 = ((whole2Int * den2Int) + num2Int);
   }
   // doing Keep change flip in order to flip the second fraction so multiplication can be done
   int temp = imp2;
   imp2 = den2Int;
   den2Int = temp;
   //calculate total numerator and denominator then FULLY simplifying them
   int totalNum = imp1 * imp2;
   int totalDen = den1Int * den2Int;
   int gcf = calculateGCF(totalNum, totalDen);
   totalNum = simplify(totalNum, gcf);
   totalDen = simplify(totalDen, gcf);
   totalNum = normalize(totalNum, totalDen);
   totalDen = normalize(totalDen, totalDen);
   //recreating the improper into a fixed number
   int whole = totalNum / totalDen;
   int remainder = Math.abs(totalNum % totalDen);
   //method call to do the math and format properly
   return doMath(totalNum, totalDen, whole, remainder, den1Int, den2Int, whole2Int) + "";
}

//this method does some of the math and formats things properly, taking only fully simplified
//and answer-worthy integer parameters
public static String doMath(int totalNum, int totalDen, int whole, int remainder, int den1Int, int den2Int, int whole2Int)
{
   //checks if the denominator can be turned to 1 and return a whole number.
   if(totalNum % totalDen == 0)
   {
      return (totalNum/totalDen) + "";
   }
   //checks if no whole is present and formats it to not return any whole
   else if(whole == 0)
   {
      return totalNum + "/" + totalDen; 
   }
   //checks if remainder is greater than 0, then formats it into a mixed fraction
   else if(remainder > 0)
   {
      return whole + " " + remainder + "/" + totalDen;
   }   
   //format for a basic fraction with no whole if all other conditions are gone through.
   else
   {
      return totalNum + "/" + totalDen;
   }
}

//method for simplifying using integer parameters
//num = parameter where the number to be simplified is input
//gcf = parameter where gcf is input to test
public static int simplify(int num, int gcf)
{
   if (gcf != 0) {
      num = num / gcf;
   }
   return num;
}

//method for normalizing negative denominators, using integer parameters
//fractionPart = parameter that takes the numerator/denominator and flips 
//its value (*-1) if the totalDen is less than 0
//totalDen = parameter which is used to test if normalization is needed
public static int normalize(int fractionPart, int totalDen)
{
   if(totalDen < 0)
   {
      fractionPart = fractionPart * -1;
   }
   return fractionPart;
}

//method for finding the GCF, with integer parameters
//totalNum = parameter for the total numerator, used to test.
//gcfDen = parameter for the greatest common factor, used to find if remainders are present.
public static int calculateGCF(int totalNum, int gcfDen)
{
   while (gcfDen != 0)
   {
      int remainder = gcfDen;
      gcfDen = totalNum % gcfDen;
      totalNum = remainder;
   }
   return totalNum;
}



   // Returns a string that is helpful to the user about how
   // to use the program. These are instructions to the user.
   public static String provideHelp() {
     
      String help = "This is a fraction calculator that calculates arithmetic operations!\n";
      help += "Input equation as <whole>_<numerator>/<denominator> <operator> <whole2>_<numerator2>/<denominator2>";
      
      return help;
   }
   
}

//Website used for help:
//https://www.geeksforgeeks.org/java/split-string-java-examples/