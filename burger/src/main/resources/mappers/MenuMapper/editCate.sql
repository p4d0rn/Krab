UPDATE category
SET cate_name=<@p name="c.cateName"/>,
    cate_img=<@p name="c.cateImg"/>
WHERE cate_id=<@p name="c.cateId"/>