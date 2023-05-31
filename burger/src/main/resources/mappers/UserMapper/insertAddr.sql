INSERT INTO address(
    addr_id,
    user_id,
    consignee,
    gender,
    consign_phone,
    address,
    complete_address,
    detail,
    is_default
) VALUES (
     null,
     <@p name="addr.userId"/>,
     <@p name="addr.consignee"/>,
     <@p name="addr.gender"/>,
     <@p name="addr.consignPhone"/>,
     <@p name="addr.address"/>,
     <@p name="addr.completeAddress"/>,
     <@p name="addr.detail"/>,
     <@p name="addr.isDefault"/>
)