/**
 * This class provides log wrappers for the classic system signals.
 */
public class Logger {

  public static void logSuccess() {
    System.out.println("----------------------------------------------------");
    System.out.println("Everything went well!");
    System.out.println("----------------------------------------------------");
  }
  
  public static void logNoMatch(String... args) {
    System.err.println("----------------------------------------------------");
    System.err.println("The result does not match the expected value");
    System.err.println("Expected: " + args[0]);
    System.err.println("Actual: " + args[1]);
    System.err.println("----------------------------------------------------");
  }
  
  public static void logException(String... args) {
    System.err.println("----------------------------------------------------");
    System.err.println("An error occured during runtime.");
    System.err.println("This is all your fault. Shame. SHAME!");
    System.err.println("Details:");
    System.err.println("Expected: " + args[0]);
  }
  
  public static void logException(Throwable throwable) {
    System.err.println("----------------------------------------------------");
    System.err.println("Something bad happened!");
    System.err.println(throwable.getMessage());
    throwable.printStackTrace();
    System.err.println("----------------------------------------------------");
  }
  
  
}
