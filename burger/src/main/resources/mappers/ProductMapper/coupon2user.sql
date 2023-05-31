SELECT user_id
FROM user_coupon
WHERE coupon_id IN
<#list ids as id>
  <#if id_index == 0>
  (
  </#if>
    ${id}
    <#if id_has_next>
    ,
    <#else>
    )
    </#if>
</#list>