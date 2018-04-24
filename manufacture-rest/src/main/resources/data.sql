-- stuff
INSERT INTO stuff (id, name, cost, amount) VALUES (1, 'stu1', 10, 0);
INSERT INTO stuff (id, name, cost, amount) VALUES (2, 'stu2', 20, 0);
INSERT INTO stuff (id, name, cost, amount) VALUES (3, 'stu3', 30, 0);
-- products
INSERT INTO product (id, name) VALUES (1, 'pr1');
INSERT INTO product (id, name) VALUES (2, 'pr2');
INSERT INTO composition (product, stuff, amount) VALUES (1, 1, 1);
INSERT INTO composition (product, stuff, amount) VALUES (1, 2, 1);
INSERT INTO composition (product, stuff, amount) VALUES (2, 2, 1);
INSERT INTO composition (product, stuff, amount) VALUES (2, 3, 2);

