FROM gcc:latest
WORKDIR /usr/src/app
COPY app/* ./
COPY run.sh ./
RUN chmod +x run.sh
CMD ["sh", "run.sh"]
