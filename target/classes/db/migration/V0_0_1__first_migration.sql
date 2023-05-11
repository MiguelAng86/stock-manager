CREATE TABLE product (
    id int NOT NULL PRIMARY KEY,
    sequence int
);

CREATE TABLE size (
    id int NOT NULL PRIMARY KEY,
    product_id int,
    backsoon BOOLEAN,
    special BOOLEAN,
    FOREIGN KEY (product_id) REFERENCES product
);

CREATE TABLE stock (
  size_id int,
  quantity int,
  FOREIGN KEY (size_id) REFERENCES size
);

INSERT INTO product SELECT * FROM CSVREAD('classpath:db/migration/product.csv', 'id, sequence');
INSERT INTO size SELECT * FROM CSVREAD('classpath:db/migration/size.csv', 'id, product_id, backsoon, special');
INSERT INTO stock SELECT * FROM CSVREAD('classpath:db/migration/stock.csv', 'size_id, quantity');



