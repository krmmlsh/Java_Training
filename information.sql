CREATE TABLE description (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  email VARCHAR(45),
  information varchar(200),
  lastname VARCHAR(45),
  firstname VARCHAR(45),
  company_id bigint(20),
  user_id bigint(20),
  FOREIGN KEY (company_id) references company(id), 
  FOREIGN KEY (user_id) references user(id), 
  PRIMARY KEY (id)
);

