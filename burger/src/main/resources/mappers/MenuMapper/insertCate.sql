INSERT INTO category(
    cate_id,
    cate_name,
    cate_img
) VALUES (
    null,
    <@p name="c.cateName"/>,
    <@p name="c.cateImg"/>
);
SELECT LAST_INSERT_ID();