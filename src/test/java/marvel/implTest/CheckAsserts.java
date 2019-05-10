/*
 * Copyright ©2019 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Spring Quarter 2019 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package marvel.implTest;

import org.junit.Test;

/** Checks that Java Asserts are enabled. */
public class CheckAsserts {

  @Test
  public void testAssertEnabled() {
    checkAssertsEnabled();
  }

  /**
   * Checks that Java Asserts are enabled. If they are not, an error message is printed, and the
   * system exits.
   */
  public static void checkAssertsEnabled() {
    try {
      assert false;

      // assertions are not enabled
      System.err.println(
          "Java Asserts are not currently enabled. Follow homework writeup instructions to enable asserts on all JUnit Test files.");
      System.exit(1);

    } catch (AssertionError e) {
      // do nothing
      // assertions are enabled
    }
  }
}
