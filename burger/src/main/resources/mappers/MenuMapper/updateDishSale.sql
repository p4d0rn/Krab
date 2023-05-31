UPDATE dish d
JOIN (
    SELECT od.dish_id, SUM(od.quantity) as total_quantity
    FROM `order` o
    JOIN order_dish od ON o.order_id = od.order_id
    WHERE o.order_time >= DATE_SUB(NOW(), INTERVAL 3 HOUR)
    GROUP BY od.dish_id
) q ON d.dish_id = q.dish_id
SET d.sales = d.sales + q.total_quantity;