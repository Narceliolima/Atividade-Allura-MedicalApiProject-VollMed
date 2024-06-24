ALTER TABLE medics ADD active_account TINYINT;
UPDATE medics SET active_account = 1;