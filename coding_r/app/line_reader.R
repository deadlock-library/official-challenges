
LineReader.read_random_line <- function(file, n) {
  seeds <- read.table(file = file, header = FALSE, sep = ':')
  n = runif(1, 0, nrow(seeds))
  as.character(seeds[n,])
}
