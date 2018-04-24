DELETE FROM sale;
DELETE FROM supply;
DELETE FROM composition;
DELETE FROM product;
DELETE FROM stuff;
-- stuff
INSERT INTO stuff (id, name, cost, amount) VALUES (1000001, 'stuff1', 10, 0);
INSERT INTO stuff (id, name, cost, amount) VALUES (1000002, 'stuff2', 20, 0);
INSERT INTO stuff (id, name, cost, amount) VALUES (1000003, 'stuff3', 30, 0);
-- products
INSERT INTO product (id, name) VALUES (1000001, 'prod1');
INSERT INTO product (id, name) VALUES (1000002, 'prod2');
INSERT INTO composition (product, stuff, amount) VALUES (1000001, 1000001, 1);
INSERT INTO composition (product, stuff, amount) VALUES (1000001, 1000002, 1);
INSERT INTO composition (product, stuff, amount) VALUES (1000002, 1000002, 1);
INSERT INTO composition (product, stuff, amount) VALUES (1000002, 1000003, 2);

