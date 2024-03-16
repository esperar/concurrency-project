IF NOT EXISTS (SELECT * FROM tbl_selfstudy WHERE id = 1)
BEGIN
    INSERT INTO tbl_selfstudy(id, room_count, limit)
    VALUES(1, 0, 100)
END

