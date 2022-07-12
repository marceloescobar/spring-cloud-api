drop table inventory;

CREATE TABLE IF NOT EXISTS inventory (
  id bigint NOT NULL AUTO_INCREMENT,
  sku_code varchar(255) DEFAULT NULL,
  stock int DEFAULT NULL,
  PRIMARY KEY (id)
);