FROM mysql/mysql-server:8.0

ENV MYSQL_DATABASE=odaneko \
  MYSQL_USER=mysql \
  MYSQL_PASSWORD=mysql \
  MYSQL_ROOT_PASSWORD=mysql \
  TZ=Asia/Tokyo

COPY ./my.cnf /etc/my.cnf
RUN chmod 644 /etc/my.cnf