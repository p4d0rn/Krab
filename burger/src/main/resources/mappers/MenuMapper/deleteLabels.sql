DELETE FROM label
WHERE label_id IN
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