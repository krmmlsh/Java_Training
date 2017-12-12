CREATE TABLE user_roles (
  user_role_id int(11) NOT NULL AUTO_INCREMENT,
  username VARCHAR(45) NOT NULL ,
  role varchar(45) NOT NULL,
  PRIMARY KEY (user_role_id)
);


CREATE  TABLE user (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  username VARCHAR(45) NOT NULL ,
  password VARCHAR(45) NOT NULL ,
  PRIMARY KEY (id)
);



INSERT INTO user_roles (username, role)
VALUES ("karim", 'ROLE_USER');
INSERT INTO user_roles (username, role)
VALUES ("user", 'ROLE_USER');
