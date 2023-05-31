INSERT INTO `order`(
    user_id,
    order_id,
    addr_id,
    coupon_id,
    status,
    order_time,
    pickup_no,
    remarks,
    total_fee
) VALUES (
    <@p name="o.userId"/>,
    null,
    <@p name="o.addrId"/>,
    <@p name="o.couponId"/>,
    0,
    NOW(),
     (SELECT p.pickup FROM
         (SELECT IFNULL(MAX(o.pickup_no)+1, 0) pickup FROM `order` o
          WHERE DATE(o.order_time) = CURDATE()) p),
    <@p name="o.remarks"/>,
    <@p name="o.totalFee"/>
);
SELECT LAST_INSERT_ID();