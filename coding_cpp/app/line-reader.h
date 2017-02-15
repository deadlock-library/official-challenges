#include <string>
using std::string;
using std::exception;

class LineReader {
public:
  LineReader(const string filename);
  string get_random_line();
private:
  string filename;
  int count_lines();
};
