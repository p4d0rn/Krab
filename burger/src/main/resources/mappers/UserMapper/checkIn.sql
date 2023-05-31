UPDATE `user`
SET `check` = NOW(), `points` = `points` + 1
WHERE `user_id` = <@p name="userId"/> AND DATE(`check`) <> CURDATE();