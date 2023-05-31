SELECT order_id, addr_id, coupon_id, status, order_time, pickup_no, remarks, total_fee
FROM `order`
WHERE user_id = <@p name="userId"/>