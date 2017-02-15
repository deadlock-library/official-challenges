#include "line-reader.h"
#include <string>
#include <fstream>
#include <iostream>
#include <stdlib.h>     /* srand, rand */
#include <time.h>       /* time */

using namespace std;

LineReader::LineReader(const string filename) {
  this->filename = filename;
  srand(time(NULL));
}

int LineReader::count_lines() {
  int line_count = 0;
  std::string line;
  std::ifstream file(this->filename);
  while (std::getline(file, line)) {
    ++line_count;
  }
  return line_count;
}

string LineReader::get_random_line() {
  int count = this->count_lines();
  int randomLine = rand() % count;
  std::ifstream file(this->filename);
  for (int i = 0; i < randomLine; i++) {
    std::string line;
    std::getline(file, line);
  }
  std::string result;
  std::getline(file, result);
  return result;
}
