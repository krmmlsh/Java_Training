CREATE TABLE user_roles (
  user_role_id int(11) NOT NULL AUTO_INCREMENT,
  username VARCHAR(45) NOT NULL ,
  role varchar(45) NOT NULL,
  PRIMARY KEY (user_role_id)
);


CREATE  TABLE user (
  username VARCHAR(45) NOT NULL ,
  password VARCHAR(45) NOT NULL ,
  enabled TINYINT NOT NULL DEFAULT 1 ,
  PRIMARY KEY (username)
);



INSERT INTO user_roles (username, role)
VALUES ("karim", 'ROLE_USER');
INSERT INTO user_roles (username, role)
VALUES ("user", 'ROLE_USER');
