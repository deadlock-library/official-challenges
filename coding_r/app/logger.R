
oprint <- function(s) {
  write(s, stdout())
}

eprint <- function(s) {
  write(s, stderr())
}

log_success <- function() {
  oprint("----------------------------------------------------")
  oprint("Everything went well!")
  oprint("----------------------------------------------------")
}

log_no_match <- function(expected, actual) {
  eprint("----------------------------------------------------")
  eprint("The result does not match the expected value")
  eprint(paste("Expected: ", expected))
  eprint(paste("  Actual: ", actual))
  eprint("----------------------------------------------------")
}

log_exception <- function(expected, error) {
  eprint("----------------------------------------------------")
  eprint("An error occured during runtime.")
  eprint("This is all your fault. Shame. SHAME!")
  eprint("Details:")
  eprint(paste("Expected: ", expected))
  eprint(paste("   Error: ", error))
  eprint("----------------------------------------------------")
}
