FROM java:8
WORKDIR /
COPY app/*.java /
COPY run.sh /
RUN chmod +x run.sh
CMD ["sh", "run.sh"]
