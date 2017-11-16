package edu.wit.cs.comp1000;

import java.util.Scanner;

public class LA6a {
	
	/**
	 * Error to show if value is not positive.
	 */
	static final String E_VALUE = "Value must be positive.";
	
	/**
	 * Error to show if guess is not positive.
	 */
	static final String E_GUESS = "Guess must be positive.";
	
	/**
	 * Error to show if error tolerance is not positive.
	 */
	static final String E_TOLERANCE = "Error tolerance must be positive.";
	
	/**
	 * Computes the absolute value of an input
	 * 
	 * @param value input value
	 * @return absolute value of input
	 */
	public static double absoluteValue(double value) {
		if(value < 0) {
			return value * -1;
		}else {
			return value;
		}
	}
	
	/**
	 * Computes the square root of a value via
	 * the Babylonian method:
	 * 
	 * 0) lastGuess := initialGuess
	 * 1) Loop
	 *  a) nextGuess := (lastGuess + value/lastGuess) / 2
	 *  b) difference := | lastGuess - nextGuess |
	 *  c) lastGuess := nextGuess
	 *  d) if difference < errorTolerance, quit loop
	 * 2) return nextGuess
	 * 
	 * @param x value for which to take the square root
	 * @param initialGuess initial guess as to the answer
	 * @param err error tolerance
	 * @return square root
	 */
	public static double squareRoot(double x, double initialGuess, double errTolerance) {
		double diff, next, last;
		last = initialGuess;
		do {
			next = (last + (x/last))/2;
			diff = absoluteValue(last - next);
			last = next;
		}while(diff > errTolerance);
		return next;
	}

	/**
	 * Program execution point:
	 * inputs value, initial guess, tolerance,
	 * outputs sqrt(value) via the Babylonian method
	 * 
	 * @param args command-line arguments (ignored)
	 */
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		final Scanner kbInput = new Scanner(System.in);
		// X , to calc sqrt
		double val;
		do {
			System.out.printf("Enter a value for which to take the square root: ");
			val = kbInput.nextDouble();
			if (val <= 0) {
				System.out.printf("%s%n", E_VALUE);
			}
		} while (val <= 0);
		
		//initial Guess
		double initGuess;
		do {
			System.out.printf("Enter an initial guess as to the answer: ");
			initGuess = kbInput.nextDouble();
			if (initGuess <= 0) {
				System.out.printf("%s%n", E_GUESS);
			}
		} while (initGuess <= 0);
		
		//Error Tolerance
				double percentErr;
				do {
					System.out.printf("Enter an error tolerance for the calculation: ");
					percentErr = kbInput.nextDouble();
					if (percentErr <= 0) {
						System.out.printf("%s%n", E_TOLERANCE);
					}
				} while (percentErr <= 0);
				
			double ans;
			ans = squareRoot(val, initGuess, percentErr);
			
			System.out.printf("sqrt(%.5f) ~ %.5f%n", val, ans);
	}
	

}
