INSERT INTO tlb_invoices (id, number_invoice, description, customer_id, create_at, state)
VALUES(1, '0001', 'factura de articulos de oficina', 1, NOW(),'CREATED');

INSERT INTO tlb_invoices (id, number_invoice, description, customer_id, create_at, state)
VALUES(2, '0002', 'facturas de equipos de computo', 1, NOW(),'CREATED');

INSERT INTO tbl_invoce_items ( invoice_id, product_id, quantity, price ) VALUES(1, 1 , 1, 178.89);
INSERT INTO tbl_invoce_items ( invoice_id, product_id, quantity, price)  VALUES(1, 2 , 2, 12.5);
INSERT INTO tbl_invoce_items ( invoice_id, product_id, quantity, price)  VALUES(1, 3 , 1, 40.06);

INSERT INTO tbl_invoce_items ( invoice_id, product_id, quantity, price)  VALUES(2, 4 , 5, 35.06);