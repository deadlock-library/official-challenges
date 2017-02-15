/**
 * This class provides log wrappers for the classic system signals.
 */
#include "logger.h"
#include <iostream>
#include <string>

using std::string;
using std::endl;
using std::cout;
using std::cerr;
using std::exception;

void Logger::log_success() {
  cout << "----------------------------------------------------" << endl;
  cout << "Everything went well!" << endl;
  cout << "----------------------------------------------------" << endl;
}

void Logger::log_no_match(const string& expected, const string& actual) {
  cerr << "----------------------------------------------------" << endl;
  cerr << "The result does not match the expected value" << endl;
  cerr << "Expected: " << expected << endl;
  cerr << "Actual: " << actual << endl;
  cerr << "----------------------------------------------------" << endl;
}

void Logger::log_unexpected_error(const string& expected) {
  cerr << "----------------------------------------------------" << endl;
  cerr << "An error occured at runtime." << endl;
  cerr << "This is all your fault. Shame. SHAME!" << endl;
  cerr << "Details:" << endl;
  cerr << "Expected: " << expected << endl;
  cerr << "----------------------------------------------------" << endl;
}

void Logger::log_exception(const exception& e) {
  cerr << "----------------------------------------------------" << endl;
  cerr << "Something bad happened." << endl;
  cerr << "Details: " << e.what() << endl;
  cerr << "----------------------------------------------------" << endl;
}
