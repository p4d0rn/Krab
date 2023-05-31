INSERT INTO dish(
    dish_id,
    cate_id,
    dish_name,
    description,
    dish_price,
    dish_img,
    dish_status,
    sales,
    remark
)
VALUES (
    null,
    <@p name="d.cateId"/>,
    <@p name="d.dishName"/>,
    <@p name="d.description"/>,
    <@p name="d.dishPrice"/>,
    <@p name="d.dishImg"/>,
    <@p name="d.dishStatus"/>,
    <@p name="d.sales"/>,
    <@p name="d.remark"/>
)