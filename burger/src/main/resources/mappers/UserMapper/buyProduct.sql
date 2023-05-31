UPDATE user
SET points = points -
    <#if productType == 0>
        (SELECT score
            FROM coupon
            WHERE coupon_id = <@p name="productId"/>)
    <#else>
        (SELECT score
            FROM item
            WHERE item_id = <@p name="productId"/>)
    </#if>
WHERE user_id = <@p name="userId"/>;

INSERT INTO `deal` (
    deal_id,
    user_id,
    addr_id,
    deal_type,
    product_id,
    deal_time
) VALUES (
    null,
    <@p name="userId"/>,
    <@p name="addrId"/>,
    <@p name="productType"/>,
    <@p name="productId"/>,
    NOW()
);
