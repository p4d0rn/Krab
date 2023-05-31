UPDATE `order`
SET total_fee = (SELECT ((SELECT SUM(d.dish_price * od.quantity)
                 FROM dish d
                          JOIN order_dish od ON d.dish_id = od.dish_id
                 WHERE d.dish_id IN
  <#list dishes as d>
  <#if d_index == 0>
  (
  </#if>
    ${d.dish.dishId}
  <#if d_has_next>
  ,
  <#else>
    )
  </#if>
  </#list>
    AND od.order_id = <@p name="orderId"/>) - IFNULL((
        SELECT c.cut
        FROM `coupon` c, `user_coupon` uc
        WHERE uc.coupon_id = <@p name="couponId"/> AND uc.user_id = <@p name="userId"/> AND uc.coupon_id = c.coupon_id
    ),0)))
WHERE order_id = <@p name="orderId"/>;

UPDATE `user_coupon`
SET `quantity` = `quantity` - 1
WHERE `coupon_id` = <@p name="couponId"/>;

DELETE FROM `user_coupon`
WHERE `quantity` = 0 AND user_id = <@p name="userId"/>;