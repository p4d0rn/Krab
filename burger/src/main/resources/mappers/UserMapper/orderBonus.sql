UPDATE `user`
SET points = points + CEIL((SELECT total_fee FROM `order` WHERE order_id = <@p name="orderId"/>) * 0.15)
WHERE user_id = <@p name="userId"/>
