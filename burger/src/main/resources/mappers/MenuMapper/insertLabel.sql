INSERT INTO label(
    label_id,
    label_name,
    label_color
) VALUES (
     null,
     <@p name="l.labelName"/>,
     <@p name="l.labelColor"/>
 );