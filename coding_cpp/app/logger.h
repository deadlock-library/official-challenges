#include <string>
using std::string;
using std::exception;

class Logger {
public:
  static void log_success();
  static void log_no_match(const string& expected, const string& actual);
  static void log_unexpected_error(const string& expected);
  static void log_exception(const exception& e);
};
