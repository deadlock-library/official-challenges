FROM mysql:latest
ENV MYSQL_ROOT_PASSWORD toor
ENV MYSQL_DATABASE thedb
COPY entries /docker-entrypoint-initdb.d
