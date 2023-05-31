UPDATE `order`
SET `status` = 1
WHERE `order_time` < DATE_SUB(NOW(), INTERVAL 1 HOUR) AND `user_id` = <@p name="userId"/>