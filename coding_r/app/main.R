
source("logger.R")
source("spelling_backwards.R")
source("line_reader.R")


read_backwards <- function(s) {
    paste(rev(strsplit(s, NULL)[[1]]), collapse = '')
}

str <- LineReader.read_random_line("seeds.txt")
expected <- read_backwards(str)

tryCatch(
{
    actual <- SpellingBackwards.read_backwards(str)
    if (expected == actual) {
        log_success()
        q(save = "no", status = 0, runLast = FALSE)
    } else {
        log_no_match(expected, actual)
        q(save = "no", status = - 1, runLast = FALSE)
    }
},
error = function(e) {
    log_exception(expected, e)
    q(save = "no", status = 1, runLast = FALSE)
}
)
