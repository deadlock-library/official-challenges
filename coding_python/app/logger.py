"""
 This class provides log wrappers for the classic system signals.
"""
import sys

def eprint(*args, **kwargs):
    print(*args, file=sys.stderr, **kwargs)
def log_success():
    print("----------------------------------------------------")
    print("Everything went well!")
    print("----------------------------------------------------")
def log_no_match(*args):
    eprint("----------------------------------------------------")
    eprint("The result does not match the expected value")
    eprint("Expected: " + str(args[0]))
    eprint("Actual: " + str(args[1]))
    eprint("----------------------------------------------------")
def log_exception(expected, e):
    eprint("----------------------------------------------------")
    eprint("An error occured during runtime.")
    eprint("This is all your fault. Shame. SHAME!")
    eprint("Details:")
    eprint("Expected: " + str(expected))
    eprint("Error: " + e.strerror)
    eprint("----------------------------------------------------")
