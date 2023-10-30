-- User 데이터 삽입
INSERT INTO users (user_id, name, provider, role, email, profile_url, nickname, uuid)
VALUES (1, 'koko', 0, 'USER', '1234@naver.com', 'https://www.naver.com', '귀여운해달1', '0782ef48-a439-01');

INSERT INTO users (user_id, name, provider, role, email, profile_url)
VALUES (2, 'coco', 0, 'USER', '12345@naver.com', 'https://www.naver.com');

-- Board 데이터 삽입
INSERT INTO board (user_id, board_id, title, description, uuid, tag, created_date, is_public, is_shared, modified_date, authorship_id)
VALUES (1, 1, 'board1', 'test', '0782ef48-a439-11', 0 ,'2023-01-01 11:11:01', 1, 1, '2023-01-01 11:11:01', 1);

INSERT INTO board (user_id, board_id, title, description, uuid, tag, created_date, is_public, modified_date, fixed_date, contents, authorship_id)
VALUES (1, 2, 'board2', 'test', '0782ef48-a439-12', 1 ,'2023-01-01 11:11:01', 1, '2023-01-02 11:11:01', '2023-01-03 11:11:01', 'test contents', 1);

INSERT INTO board (user_id, board_id, title, description, uuid, tag, created_date, is_public, is_shared, modified_date, contents, authorship_id)
VALUES (1, 3, 'board3', 'test', '0782ef48-a439-13', 0 ,'2023-01-01 11:11:01', 1, 1, '2023-01-03 11:11:01', 'test contents3', 1);

INSERT INTO board (user_id, board_id, title, description, uuid, tag, created_date, is_public, modified_date, fixed_date, authorship_id)
VALUES (1, 4, 'board4', 'test', '0782ef48-a439-14', 0 ,'2023-01-01 11:11:01', 1, '2023-01-04 11:11:01', '2023-01-04 11:11:01', 1);

INSERT INTO board (user_id, board_id, title, description, uuid, tag, created_date, is_public, modified_date, deleted_date, authorship_id)
VALUES (1, 5, 'board5', 'test', '0782ef48-a439-15', 0 ,'2023-01-01 11:11:01', 1, '2023-01-04 11:11:01', '2023-01-04 11:11:01', 1);