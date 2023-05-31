SELECT label_id, label_name, label_color
FROM label
WHERE label_id IN (SELECT label_id
                   FROM dish_label
                   WHERE dish_id = <@p name="dishId"/>)