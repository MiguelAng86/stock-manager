CREATE TABLE product (
    id int NOT NULL PRIMARY KEY,
    sequence int
);

CREATE TABLE size (
    id int NOT NULL PRIMARY KEY,
    productId int,
    backSoon BOOLEAN,
    special BOOLEAN,
    FOREIGN KEY (productId) REFERENCES product
);

CREATE TABLE stock (
  sizeId int,
  quantity int,
  FOREIGN KEY (sizeId) REFERENCES size
);

INSERT INTO product SELECT * FROM CSVREAD('classpath:db/migration/product.csv', 'id, sequence');
INSERT INTO size SELECT * FROM CSVREAD('classpath:db/migration/size.csv', 'id, productId, backSoon, special');
INSERT INTO stock SELECT * FROM CSVREAD('classpath:db/migration/stock.csv', 'sizeId, quantity');



