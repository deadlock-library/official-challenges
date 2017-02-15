CFLAGS=-W -Wall -ansi -pedantic -std=c++11

app: spelling-backwards.o logger.o main.o line-reader.o
	g++ -o app spelling-backwards.o logger.o main.o line-reader.o

spelling-backwards.o: spelling-backwards.cpp
	g++ -c spelling-backwards.cpp -o spelling-backwards.o ${CFLAGS}

line-reader.o: line-reader.cpp
	g++ -c line-reader.cpp -o line-reader.o ${CFLAGS}

logger.o: logger.cpp
	g++ -c logger.cpp -o logger.o ${CFLAGS}

main.o: main.cpp logger.h line-reader.h spelling-backwards.h
	g++ -c main.cpp -o main.o ${CFLAGS}
