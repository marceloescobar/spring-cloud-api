CREATE TABLE IF NOT EXISTS t_orders (
  id bigint NOT NULL AUTO_INCREMENT,
  order_number varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS order_line_items (
 id bigint NOT NULL AUTO_INCREMENT,
 sku_code varchar(255) DEFAULT NULL,
 price double  DEFAULT NULL,
 quantity int NOT NULL,
 order_id bigint,
 PRIMARY KEY (id),
  INDEX idx_order_item (order_id),
    FOREIGN KEY (order_id) REFERENCES t_orders(id)
);