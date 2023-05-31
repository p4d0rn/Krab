INSERT INTO `order_dish` (order_id, dish_id, quantity) VALUES
    <#list dishes as d>
(<@p name="orderId"/>, ${d.dish.dishId}, ${d.quantity})
    <#if d_has_next>
    ,
    </#if>
    </#list>