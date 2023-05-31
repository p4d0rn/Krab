UPDATE dish
SET cate_id = <@p name="d.cateId"/>,
    dish_name = <@p name="d.dishName"/>,
    description = <@p name="d.description"/>,
    dish_price = <@p name="d.dishPrice"/>,
    dish_img = <@p name="d.dishImg"/>,
    dish_status = <@p name="d.dishStatus"/>,
    sales = <@p name="d.sales"/>,
    remark = <@p name="d.remark"/>
WHERE dish_id = <@p name="d.dishId"/>