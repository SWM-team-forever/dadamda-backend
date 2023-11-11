-- User 데이터 삽입
INSERT INTO users (user_id, name, provider, role, email, profile_url, nickname, uuid)
VALUES (1, 'koko', 0, 'USER', '1234@naver.com', 'https://www.naver.com', '귀여운해달1', '0782ef48-a439-01');

INSERT INTO users (user_id, name, provider, role, email, profile_url, nickname, uuid)
VALUES (2, 'coco', 0, 'USER', '12345@naver.com', 'https://www.naver.com', '귀여운해달2', '0782ef48-a439-02');

INSERT INTO users (user_id, name, provider, role, email, profile_url, nickname, uuid)
VALUES (3, 'lala', 0, 'USER', '123456@naver.com', 'https://www.naver.com', '귀여운해달3', '0782ef48-a439-03');

-- Board 데이터 삽입
INSERT INTO board (user_id, board_id, title, description, uuid, tag, created_date, is_public, is_shared, modified_date, thumbnail_url, heart_cnt, view_cnt, share_cnt)
VALUES (1, 1, 'board1', 'test', '0782ef48-a439-11', 0 ,'2023-01-01 11:11:01', 1, 1, '2023-01-01 11:11:01', 'board1 thumbnail url', 10, 11, 12);

INSERT INTO board (user_id, board_id, title, description, uuid, tag, created_date, is_public, modified_date, fixed_date, contents, heart_cnt, view_cnt, share_cnt, original_board_id)
VALUES (1, 2, 'board2', 'test', '0782ef48-a439-12', 1 ,'2023-01-01 11:11:01', 0, '2023-01-02 11:11:01', '2023-01-03 11:11:01', 'test contents', 0, 0, 0, 1);

INSERT INTO board (user_id, board_id, title, description, uuid, tag, created_date, is_public, is_shared, modified_date, contents, heart_cnt, view_cnt, share_cnt)
VALUES (1, 3, 'board3', 'test', '0782ef48-a439-13', 1 ,'2023-01-01 11:11:01', 1, 1, '2023-01-03 11:11:01', 'test contents3', 10, 11, 13);

INSERT INTO board (user_id, board_id, title, description, uuid, tag, created_date, is_public, modified_date, fixed_date, heart_cnt, view_cnt, share_cnt, contents)
VALUES (1, 4, 'board4', 'test', '0782ef48-a439-14', 2 ,'2023-01-01 11:11:01', 1, '2023-01-04 11:11:01', '2023-01-04 11:11:01', 9, 11, 14, 'test contents4');

INSERT INTO board (user_id, board_id, title, description, uuid, tag, created_date, is_public, modified_date, deleted_date, heart_cnt, view_cnt, share_cnt)
VALUES (1, 5, 'board5', 'test', '0782ef48-a439-15', 0 ,'2023-01-01 11:11:01', 1, '2023-01-04 11:11:01', '2023-01-04 11:11:01', 6, 11, 12);

INSERT INTO board (user_id, board_id, title, description, uuid, tag, created_date, is_public, is_shared, modified_date, thumbnail_url, heart_cnt, view_cnt, share_cnt)
VALUES (2, 6, 'board6', 'test', '0782ef48-a439-16', 0 ,'2023-02-01 11:11:01', 1, 1, '2023-02-01 11:11:01', 'board1 thumbnail url', 11, 11, 12);

INSERT INTO board (user_id, board_id, title, description, uuid, tag, created_date, is_public, modified_date, fixed_date, contents, heart_cnt, view_cnt, share_cnt, original_board_id)
VALUES (2, 7, 'board7', 'test', '0782ef48-a439-17', 1 ,'2023-02-01 11:11:01', 0, '2023-02-02 11:11:01', '2023-01-03 11:11:01', 'test contents', 0, 0, 0, 1);

INSERT INTO board (user_id, board_id, title, description, uuid, tag, created_date, is_public, is_shared, modified_date, contents, heart_cnt, view_cnt, share_cnt)
VALUES (2, 8, 'board8', 'test', '0782ef48-a439-18', 1 ,'2023-02-01 11:11:01', 1, 1, '2023-02-03 11:11:01', 'test contents3', 10, 11, 13);

INSERT INTO board (user_id, board_id, title, description, uuid, tag, created_date, is_public, modified_date, fixed_date, heart_cnt, view_cnt, share_cnt, contents)
VALUES (2, 9, 'board9', 'test', '0782ef48-a439-19', 2 ,'2023-02-01 11:11:01', 1, '2023-02-04 11:11:01', '2023-01-04 11:11:01', 9, 11, 14, 'test contents4');

INSERT INTO board (user_id, board_id, title, description, uuid, tag, created_date, is_public, modified_date, deleted_date, heart_cnt, view_cnt, share_cnt)
VALUES (2, 10, 'board10', 'test', '0782ef48-a439-20', 0 ,'2023-03-01 11:11:01', 1, '2023-03-04 11:11:01', '2023-01-04 11:11:01', 5, 11, 12);

INSERT INTO board (user_id, board_id, title, description, uuid, tag, created_date, is_public, is_shared, modified_date, thumbnail_url, heart_cnt, view_cnt, share_cnt)
VALUES (3, 11, 'board11', 'test', '0782ef48-a439-21', 0 ,'2023-03-01 11:11:01', 1, 1, '2023-03-01 11:11:01', 'board1 thumbnail url', 10, 11, 12);

INSERT INTO board (user_id, board_id, title, description, uuid, tag, created_date, is_public, modified_date, fixed_date, contents, heart_cnt, view_cnt, share_cnt, original_board_id)
VALUES (3, 12, 'board12', 'test', '0782ef48-a439-22', 1 ,'2023-03-01 11:11:01', 0, '2023-03-02 11:11:01', '2023-01-03 11:11:01', 'test contents', 0, 0, 0, 1);

INSERT INTO board (user_id, board_id, title, description, uuid, tag, created_date, is_public, is_shared, modified_date, contents, heart_cnt, view_cnt, share_cnt)
VALUES (3, 13, 'board13', 'test', '0782ef48-a439-23', 1 ,'2023-03-01 11:11:01', 1, 1, '2023-03-03 11:11:01', 'test contents3', 10, 11, 13);

INSERT INTO board (user_id, board_id, title, description, uuid, tag, created_date, is_public, modified_date, fixed_date, heart_cnt, view_cnt, share_cnt, contents)
VALUES (3, 14, 'board14', 'test', '0782ef48-a439-24', 2 ,'2023-03-01 11:11:01', 1, '2023-03-04 11:11:01', '2023-01-04 11:11:01', 9, 11, 14, 'test contents4');

INSERT INTO board (user_id, board_id, title, description, uuid, tag, created_date, is_public, modified_date, deleted_date, heart_cnt, view_cnt, share_cnt)
VALUES (3, 15, 'board15', 'test', '0782ef48-a439-25', 0 ,'2023-03-01 11:11:01', 1, '2023-03-04 11:11:01', '2023-01-04 11:11:01', 6, 11, 12);

INSERT INTO board (user_id, board_id, title, description, uuid, tag, created_date, is_public, is_shared, modified_date, thumbnail_url, heart_cnt, view_cnt, share_cnt)
VALUES (3, 16, 'board16', 'test', '0782ef48-a439-26', 0 ,'2023-03-01 11:11:01', 1, 1, '2023-02-01 11:11:01', 'board1 thumbnail url', 1, 11, 12);
