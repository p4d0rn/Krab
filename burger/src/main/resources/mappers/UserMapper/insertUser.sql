INSERT INTO user(
    user_id,
    user_name,
    phone,
    avatar,
    points,
    `check`
) VALUES (
    <@p name="userId"/>,
    '微信用户',
    null,
     "https://thirdwx.qlogo.cn/mmopen/vi_32/POgEwh4mIHO4nibH0KlMECNjjGxQUq24ZEaGT4poC6icRiccVGKSyXwibcPq4BWmiaIGuG1icwxaQX6grC9VemZoJ8rg/132",
    0,
    DATE_SUB(NOW(),INTERVAL 1 DAY)
)