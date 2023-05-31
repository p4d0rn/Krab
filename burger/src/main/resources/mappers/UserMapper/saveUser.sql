UPDATE `user`
SET
    <#if (u.nickName)??>
        `user_name` = <@p name="u.nickName"/>,
    </#if>
    <#if (u.avatarUrl)??>
        `avatar` = <@p name="u.avatarUrl"/>,
    </#if>
    `phone` = <@p name="u.phone"/>
WHERE `user_id` = <@p name="userId"/>