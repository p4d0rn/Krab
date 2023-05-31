SELECT dish.*, category.cate_name
FROM dish
         JOIN category
              ON dish.cate_id = category.cate_id