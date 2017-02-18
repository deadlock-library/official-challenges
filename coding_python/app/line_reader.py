import random

def read_random_line():
    line_count =  __count_lines("seeds.txt")
    line_to_read = random.randint(1, line_count)
    fo = open("seeds.txt", "r")
    for i, line in enumerate(fo):
        if (i != line_to_read):
            pass
        else:
            break
    # Close opend file
    fo.close()
    return line.rstrip()

def __count_lines(fname):
    with open(fname) as f:
        for i, l in enumerate(f):
            pass
    return i + 1
