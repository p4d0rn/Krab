UPDATE address
SET
    consignee = <@p name="addr.consignee"/>,
    gender = <@p name="addr.gender"/>,
    consign_phone = <@p name="addr.consignPhone"/>,
    address = <@p name="addr.address"/>,
    complete_address = <@p name="addr.completeAddress"/>,
    detail = <@p name="addr.detail"/>,
    is_default = <@p name="addr.isDefault"/>
WHERE addr_id = <@p name="addr.addrId"/>