UPDATE label
SET label_name=<@p name="l.labelName"/>,
    label_color=<@p name="l.labelColor"/>
WHERE label_id=<@p name="l.labelId"/>