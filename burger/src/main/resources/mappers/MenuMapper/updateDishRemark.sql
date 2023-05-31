UPDATE dish d
    JOIN (
    SELECT dish_id, NTILE(10) OVER (ORDER BY sales) AS `ntile`
    FROM dish
    ) q ON d.dish_id = q.dish_id
    SET d.remark = CASE
        WHEN q.ntile >= 9 THEN '爆中爆款'
        WHEN q.ntile >= 5 THEN '小有名气'
        WHEN q.ntile <= 1 THEN '小众冷门'
        ELSE '凡人榜'
END;