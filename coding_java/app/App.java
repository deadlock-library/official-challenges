import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This is a wrapper class to prevent exceptions and stacktraces to include the main code.
 * The target code will be ran on a different thread.
 */
public class App {

  public static void main(String[] args) throws Exception {

    // Wrap execution in runnable
    Integer result = CompletableFuture.supplyAsync(() -> {
      String randomLine = readBackwards(getRandomLine());
      String reference = readBackwards(randomLine);
      try {
        String target = SpellingBackwards.readBackwards(randomLine);
        if (!reference.equals(target)) {
          Logger.logNoMatch(reference, target);
          return 1;
        } else {
          Logger.logSuccess();
          return 0;
        }
      } catch (RuntimeException e) {
        Logger.logException(randomLine);
        throw e;
      }
    }).exceptionally(throwable -> {
      Logger.logException(throwable);
      return 1;
    }).get();

    // Exit app with result code
    System.exit(result);
  }

  /**
   * This method reads one random line from the seeds.txt file.
   * If you are using a file to feed random values for your challenge,
   * you may adapt your code accordingly.
   */
  private static String getRandomLine() {
    InputStream res = App.class.getClassLoader().getResourceAsStream("seeds.txt");
    // Read content in a stream, collect in a list
    Stream<String> lines = new BufferedReader(new InputStreamReader(res, StandardCharsets.UTF_8)).lines();
    List<String> messages = lines.collect(Collectors.toList());
    // Return random line
    return messages.get((int) (Math.random() * messages.size()));
  }

  /**
   * This method provides an example of reading a string backwards.
   * It is used to compare with the code uploaded by the candidate.
   */
  private static String readBackwards(String phrase) {
    char[] reversePhrase = phrase.toCharArray();
    for (int i = 0; i < Math.floor(reversePhrase.length / 2); i++) {
      char c = reversePhrase[i];
      reversePhrase[i] = reversePhrase[reversePhrase.length - 1 - i];
      reversePhrase[reversePhrase.length - 1 - i] = c;
    }
    return new String(reversePhrase);
  }

}
