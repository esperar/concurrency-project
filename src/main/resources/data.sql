IF NOT EXISTS (SELECT * FROM tbl_selfstudy WHERE id = 1)
BEGIN
    INSERT INTO tbl_selfstudy(id, room_count, limit)
    VALUES(1, 0, 100)
END

IF NOT EXISTS(SELECT * FROM tbl_user WHERE id = 1)
BEGIN
    INSERT INT tbl_user(id, name, password, selfstudy_id)
    VALUES(1, '김희망', '비밀번호입니다ㅎㅇ', null)

