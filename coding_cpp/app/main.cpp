#include <iostream>
#include <math.h>
#include <algorithm>
#include "logger.h"
#include "line-reader.h"
#include "spelling-backwards.h"

using namespace std;

string read_backwards(string phrase) {
  std::reverse(phrase.begin(), phrase.end());
  return phrase;
}

int main() {
    try {
      LineReader* reader = new LineReader("seeds.txt");
      string line = read_backwards(reader->get_random_line());
      string expected = read_backwards(line);
      string actual = SpellingBackwards::read_backwards(line);
      if (expected.compare(actual) == 0) {
        Logger::log_success();
        return 0;
      } else {
        Logger::log_no_match(expected, actual);
        return -1;
      }
    } catch (exception& e) {
        Logger::log_exception(e);
        return 1;
    }
    return -1;
}
