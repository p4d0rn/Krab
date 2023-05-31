SELECT d.dish_name, d.dish_price, d.dish_img, od.quantity
FROM dish d,
     order_dish od
WHERE d.dish_id = od.dish_id
  AND od.order_id = <@p name="orderId"/>
