-- User 데이터 삽입
INSERT INTO users (user_id, name, provider, role, email, profile_url)
VALUES (1, 'koko', 0, 'USER', '1234@naver.com', 'https://www.naver.com');

-- Board 데이터 삽입
INSERT INTO board (user_id, board_id, name, description, uuid, tag, created_date, is_public, modified_date)
VALUES (1, 1, 'board1', 'test', '0782ef48-a439-11', 0 ,'2023-01-01 11:11:01', 1, '2023-01-01 11:11:01');

INSERT INTO board (user_id, board_id, name, description, uuid, tag, created_date, is_public, modified_date, fixed_date)
VALUES (1, 2, 'board1', 'test', '0782ef48-a439-12', 0 ,'2023-01-01 11:11:01', 1, '2023-01-01 11:11:01', '2023-01-01 11:11:01');