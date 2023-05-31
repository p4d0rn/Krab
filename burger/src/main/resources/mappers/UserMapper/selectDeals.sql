SELECT
    d.deal_id deal_id,
    CASE
        WHEN d.deal_type = 0 THEN c.coupon_img
        WHEN d.deal_type = 1 THEN i.item_img
        END AS product_img,
    CASE
        WHEN d.deal_type = 0 THEN c.score
        WHEN d.deal_type = 1 THEN i.score
        END AS score,
    d.deal_time deal_time,
    d.product_id product_id,
    d.addr_id addr_id
FROM deal d
         LEFT JOIN coupon c ON d.product_id = c.coupon_id AND d.deal_type = 0
         LEFT JOIN item i ON d.product_id = i.item_id AND d.deal_type = 1
WHERE d.user_id = <@p name="userId"/>;