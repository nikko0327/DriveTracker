# DO NOT USE IN PRODUCTION YET, A FEW ROWS IN THE SCHEMA WERE MOVED AROUND TO KEEP CONSISTENCY AND HAS YET TO BE TESTED.


DELIMITER //
CREATE TRIGGER make_history_create
AFTER INSERT ON drive_info FOR EACH ROW
BEGIN
INSERT INTO drive_history(pp_asset_tag,manufacturer_model,serial_number, property,customer_name,cts,jira,label,drive_location,drive_state,encrypted,box,usb,power,rack,shelf,notes,created,last_updated,updated_by,sent_date, shipping_carrier_sent,shipping_tracking_number_sent,received_date,return_media_to_customer,essential)
VALUES(new.pp_asset_tag,new.manufacturer_model,new.serial_number,new. property,new.customer_name,new.cts,new.jira,new.label,new.drive_location,new.drive_state,new.encrypted,new.box,new.usb,new.power,new.rack,new.shelf,new.notes,new.created,new.last_updated,new.updated_by,new.sent_date,new. shipping_carrier_sent,new.shipping_tracking_number_sent,new.received_date,new.return_media_to_customer,new.essential);
END; //
DELIMITER ;

DELIMITER //
CREATE TRIGGER make_history_update
AFTER UPDATE ON drive_info FOR EACH ROW
BEGIN
IF NOT EXISTS (
SELECT * FROM drive_history WHERE pp_asset_tag=new.pp_asset_tag AND last_updated=new.last_updated
) THEN
INSERT INTO drive_history (pp_asset_tag, manufacturer_model, serial_number, property, customer_name, cts, jira, label, drive_location, drive_state, encrypted, box, usb, power, rack, shelf, notes, received_date, sent_date, shipping_carrier_sent, shipping_tracking_number_sent, created, last_updated, updated_by, return_media_to_customer, essential)
VALUES(new.pp_asset_tag, new.manufacturer_model, new.serial_number, new.property, new.customer_name, new.cts, new.jira, new.label, new.drive_location, new.drive_state, new.encrypted, new.box, new.usb, new.power, new.rack, new.shelf, new.notes, new.received_date, new.sent_date, new.shipping_carrier_sent, new.shipping_tracking_number_sent, new.created, new.last_updated, new.updated_by, new.return_media_to_customer, new.essential);
END IF;
END; //
DELIMITER ;

