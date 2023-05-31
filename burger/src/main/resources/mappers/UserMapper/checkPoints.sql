SELECT ((SELECT points
         FROM user
         WHERE user_id = <@p name="userId"/>)
>
<#if productType == 0>
    (SELECT score
        FROM coupon
        WHERE coupon_id = <@p name="productId"/>))
<#else>
    (SELECT score
        FROM item
        WHERE item_id = <@p name="productId"/>))
</#if>