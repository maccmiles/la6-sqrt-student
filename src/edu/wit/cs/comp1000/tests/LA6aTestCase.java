package edu.wit.cs.comp1000.tests;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.security.Permission;

import edu.wit.cs.comp1000.LA6a;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

public class LA6aTestCase {
	
	@Rule
	public Timeout globalTimeout = Timeout.seconds(20);

	@SuppressWarnings("serial")
	private static class ExitException extends SecurityException {}
	
	private static class NoExitSecurityManager extends SecurityManager 
    {
        @Override
        public void checkPermission(Permission perm) {}
        
        @Override
        public void checkPermission(Permission perm, Object context) {}
        
        @Override
        public void checkExit(int status) { super.checkExit(status); throw new ExitException(); }
    }
	
	@Before
	public void setUp() throws Exception 
    {
        System.setSecurityManager(new NoExitSecurityManager());
    }
	
	@After
    public void tearDown() throws Exception 
    {
        System.setSecurityManager(null);
    }
	
	private void _testAbsoluteValue(double val, double expected) {
		Double result = null;
		try {
			result = LA6a.absoluteValue(val);
		} catch (ExitException e) {}
		assertEquals((Object) expected, (Object) result);
	}
	
	@Test
	public void testAbsoluteValue() {
		_testAbsoluteValue(-5, 5);
		_testAbsoluteValue(-1, 1);
		_testAbsoluteValue(0, 0);
		_testAbsoluteValue(1, 1);
		_testAbsoluteValue(5, 5);
		
		_testAbsoluteValue(Math.PI, Math.PI);
		_testAbsoluteValue(-Math.PI, Math.PI);
		
		_testAbsoluteValue(Math.E, Math.E);
		_testAbsoluteValue(-Math.E, Math.E);
	}
	
	private void _testSquareRoot(double val, double initGuess, double err, double expSqRoot, double thresh) {
		Double result = null;
		try {
			result = LA6a.squareRoot(val, initGuess, err);
		} catch (ExitException e) {}
		assertEquals("Square Root Error!", expSqRoot, result, thresh);
	}

	@Test
	public void testSquareRoot() {
		_testSquareRoot(16, 4, 0.01, 4, 0.00001);
		_testSquareRoot(16, 6, 2, 4.333333, 0.0001);
		_testSquareRoot(16, 6, 0.5, 4.0128, 0.0001);
		_testSquareRoot(16, 6, 0.1, 4.00002, 0.00001);
		_testSquareRoot(16, 100, 0.1, 4.000284, 0.0001);
		
		_testSquareRoot(289, 12, 0.1, 17.000027, 0.00001);
		
		_testSquareRoot(2, 1.1, 0.01, 1.414214, 0.00001);
		
		_testSquareRoot(152.2756, 1, 0.01, 12.34, 0.001);
	}
	
	private static final String P_VAL = "Enter a value for which to take the square root: ";
	private static final String E_VAL = "Value must be positive.%n";
	
	private static final String P_GUESS = "Enter an initial guess as to the answer: ";
	private static final String E_GUESS = "Guess must be positive.%n";
	
	private static final String P_TOL = "Enter an error tolerance for the calculation: ";
	private static final String E_TOL = "Error tolerance must be positive.%n";
	
	private void _test(String[] values, String[] prompts, String result) {
		final String input = String.join(" ", values);
		
		final String output = TestSuite.stringOutput(new String[] {
			"%s%s%n" }, new Object[] {TestSuite.stringOutput(prompts, new Object[] {}), result});
		
		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		System.setOut(new PrintStream(outContent));
		
		try {
			LA6a.main(new String[] { "foo" });
		} catch (ExitException e) {}
		assertEquals(output, outContent.toString());
		
		System.setIn(null);
		System.setOut(null);
	}
	
	private void _testProg(String[] values, String[] prompts, String val, String answer) {
		_test(values, prompts, "sqrt(" + val + ") ~ " + answer);
	}

	@Test
	public void testProg() {
		_testProg(
			new String[] { "16", "4", "0.01" },
			new String[] { P_VAL, P_GUESS, P_TOL },
			"16.00000", "4.00000"
		);
		
		_testProg(
			new String[] { "16", "6", "2" },
			new String[] { P_VAL, P_GUESS, P_TOL },
			"16.00000", "4.33333"
		);
		
		_testProg(
			new String[] { "16", "6", "0.5" },
			new String[] { P_VAL, P_GUESS, P_TOL },
			"16.00000", "4.01282"
		);
		
		_testProg(
			new String[] { "16", "6", "0.1" },
			new String[] { P_VAL, P_GUESS, P_TOL },
			"16.00000", "4.00002"
		);
		
		_testProg(
			new String[] { "16", "100", "0.1" },
			new String[] { P_VAL, P_GUESS, P_TOL },
			"16.00000", "4.00028"
		);
		
		_testProg(
			new String[] { "289", "12", "0.1" },
			new String[] { P_VAL, P_GUESS, P_TOL },
			"289.00000", "17.00003"
		);
		
		_testProg(
			new String[] { "2", "1.1", "0.01" },
			new String[] { P_VAL, P_GUESS, P_TOL },
			"2.00000", "1.41421"
		);
		
		_testProg(
			new String[] { "152.2756", "1", "0.01" },
			new String[] { P_VAL, P_GUESS, P_TOL },
			"152.27560", "12.34000"
		);
	}

	@Test
	public void testProgVal() {
		_testProg(
			new String[] { "-16", "0", "16", "4", "0.01" },
			new String[] { P_VAL, E_VAL, P_VAL, E_VAL, P_VAL, P_GUESS, P_TOL },
			"16.00000", "4.00000"
		);
		
		_testProg(
			new String[] { "0", "16", "4", "0.01" },
			new String[] { P_VAL, E_VAL, P_VAL, P_GUESS, P_TOL },
			"16.00000", "4.00000"
		);
	}

	@Test
	public void testProgGuess() {
		_testProg(
			new String[] { "16", "-7", "0", "4", "0.01" },
			new String[] { P_VAL, P_GUESS, E_GUESS, P_GUESS, E_GUESS, P_GUESS, P_TOL },
			"16.00000", "4.00000"
		);
		
		_testProg(
			new String[] { "16", "0", "4", "0.01" },
			new String[] { P_VAL, P_GUESS, E_GUESS, P_GUESS, P_TOL },
			"16.00000", "4.00000"
		);
	}

	@Test
	public void testProgTolerance() {
		_testProg(
			new String[] { "16", "4", "-1", "-3", "0", "0.01" },
			new String[] { P_VAL, P_GUESS, P_TOL, E_TOL, P_TOL, E_TOL, P_TOL, E_TOL, P_TOL },
			"16.00000", "4.00000"
		);
		
		_testProg(
			new String[] { "16", "4", "0", "-3", "0.01" },
			new String[] { P_VAL, P_GUESS, P_TOL, E_TOL, P_TOL, E_TOL, P_TOL },
			"16.00000", "4.00000"
		);
	}

	@Test
	public void testProgMix() {
		_testProg(
			new String[] { "-21", "0", "-3", "152.2756", "0", "0", "-7", "-1", "1", "-17", "0", "0", "-0.01", "0.01" },
			new String[] { P_VAL, E_VAL, P_VAL, E_VAL, P_VAL, E_VAL, P_VAL, 
							P_GUESS, E_GUESS, P_GUESS, E_GUESS, P_GUESS, E_GUESS, P_GUESS, E_GUESS, P_GUESS, 
							P_TOL, E_TOL, P_TOL, E_TOL, P_TOL, E_TOL, P_TOL, E_TOL, P_TOL },
			"152.27560", "12.34000"
		);
	}

}
