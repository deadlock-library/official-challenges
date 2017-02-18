import logger
import line_reader
import spelling_backwards
import sys

def __read_backwards(phrase):
    return phrase[::-1]

def main(args=None):
    random_line = __read_backwards(line_reader.read_random_line())
    reference = __read_backwards(random_line)
    try:
        target = spelling_backwards.read_backwards(random_line)
        if (reference == target):
            logger.log_success()
            sys.exit(0)
        else:
            logger.log_no_match(reference, target)
            sys.exit(-1)
    except ValueError as e:
        logger.log_exception(reference, e)
        sys.exit(1)

if __name__ == "__main__":
    main()
