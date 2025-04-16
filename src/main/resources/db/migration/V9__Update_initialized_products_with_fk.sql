UPDATE products
SET category_id = (SELECT c.id from categories c where c.name = 'AQEEDAH')
where id = 1;

UPDATE products
SET category_id = (SELECT c.id from categories c where c.name = 'AQEEDAH')
where id = 2;

UPDATE products
SET category_id = (SELECT c.id from categories c where c.name = 'AQEEDAH')
where id = 3;
