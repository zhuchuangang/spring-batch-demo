CREATE TABLE user (
  id       INT,
  username VARCHAR(100),
  password VARCHAR(100)
);

CREATE TABLE my_user (
  id       INT,
  username VARCHAR(100),
  password VARCHAR(100)
);


INSERT INTO user (id, username, password) VALUES
  (1, 'tomcat', '123456'),
  (2, 'nginx', '123456'),
  (3, 'spring', '123456'),
  (4, 'dubbo', '123456'),
  (5, 'mycat', '123456'),
  (6, 'docker', '123456'),
  (7, 'mysql', '123456'),
  (8, 'mariadb', '123456'),
  (9, 'mongodb', '123456'),
  (10, 'rabbitmq', '123456'),
  (11, 'hadoop', '123456'),
  (12, 'spark', '123456'),
  (13, 'elk', '123456'),
  (14, 'rancher', '123456');
