-- User 데이터 삽입
INSERT INTO users (user_id, name, provider, role, email, profile_url)
VALUES (1, 'koko', 0, 'USER', '1234@naver.com', 'https://www.naver.com');

-- Other 데이터 삽입
INSERT INTO scrap (user_id, scrap_id, page_url, title, description, d_type, created_date)
VALUES (1, 1, 'https://www.google.com/1', 'Google Main Page 1', '구글 메인 페이지 1 입니다.', 'Other', '2023-01-01 11:11:01');

INSERT INTO scrap (user_id, scrap_id, page_url, title, description, d_type, created_date)
VALUES (1, 2, 'https://www.google.com/2', 'Google Main Page 2', '구글 메인 페이지 2 입니다.', 'Other', '2023-01-02 11:11:01');

INSERT INTO scrap (user_id, scrap_id, page_url, title, description, d_type, created_date)
VALUES (1, 3, 'https://www.google.com/3', 'Google Main Page 3', '구글 메인 페이지 3 입니다.', 'Other', '2023-01-03 11:11:01');

INSERT INTO scrap (user_id, scrap_id, page_url, title, description, d_type, created_date)
VALUES (1, 4, 'https://www.google.com/4', 'Google Main Page 4', '구글 메인 페이지 4 입니다.', 'Other', '2023-01-04 11:11:01');

-- 메모 데이터 삽입
INSERT INTO memo (scrap_id, memo_id, memo_text, created_date)
VALUES (1, 1, 'Hello 1', '2023-01-01 11:11:01');

INSERT INTO memo (scrap_id, memo_id, memo_text, created_date, deleted_date)
VALUES (1, 2, 'Hello 2', '2023-01-04 12:11:01', '2023-01-05 12:11:01');