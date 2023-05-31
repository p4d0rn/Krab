UPDATE coupon
SET coupon_name=<@p name="c.couponName"/>,
    coupon_img=<@p name="c.couponImg"/>,
    cut=<@p name="c.cut"/>,
    coupon_desc=<@p name="c.couponDesc"/>,
    dish_id=<@p name="c.dishId"/>,
    score=<@p name="c.score"/>
WHERE coupon_id=<@p name="c.couponId"/>