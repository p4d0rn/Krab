SELECT dish_id, cate_id, dish_name, description, dish_price, dish_img, dish_status, sales, remark
FROM dish
WHERE cate_id = <@p name="cateId"/>