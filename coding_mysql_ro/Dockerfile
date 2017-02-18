FROM maven:latest
WORKDIR /
COPY app/ /app/
COPY run.sh /
COPY wait-for-it.sh /
COPY expected-query.sql /
RUN chmod +x run.sh
RUN chmod +x wait-for-it.sh
WORKDIR /app
RUN mvn clean compile assembly:single
WORKDIR /
CMD ["sh", "run.sh"]
