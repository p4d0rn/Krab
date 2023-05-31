INSERT INTO coupon(
    coupon_id,
    coupon_name,
    coupon_img,
    cut,
    coupon_desc,
    dish_id,
    score
) VALUES (
    null,
    <@p name="c.couponName"/>,
    <@p name="c.couponImg"/>,
    <@p name="c.cut"/>,
    <@p name="c.couponDesc"/>,
    <@p name="c.dishId"/>,
    <@p name="c.score"/>
)