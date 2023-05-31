SELECT c.coupon_id, c.coupon_name, c.coupon_desc, c.cut, c.dish_id, uc.quantity
FROM coupon c, user_coupon uc
WHERE c.coupon_id = uc.coupon_id AND uc.user_id = <@p name="userId"/>