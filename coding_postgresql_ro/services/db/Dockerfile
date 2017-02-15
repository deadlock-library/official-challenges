FROM postgres:latest
ENV POSTGRES_USER root
ENV POSTGRES_PASSWORD toor
ENV POSTGRES_DB thedb
COPY entries /docker-entrypoint-initdb.d
