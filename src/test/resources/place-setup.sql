-- User 데이터 삽입
INSERT INTO users (user_id, name, provider, role, email, profile_url)
VALUES (1, 'koko', 0, 'USER', '1234@naver.com', 'https://www.naver.com');

-- Place 데이터 삽입
INSERT INTO scrap (user_id, scrap_id, page_url, latitude, longitude, title, d_type, created_date)
VALUES (1, 1, 'https://www.kakaomap.com/1', 37.00000000000000, 127.02969594506668, '서울 빌딩', 'Place', '2023-01-01 11:11:01');

INSERT INTO scrap (user_id, scrap_id, page_url, latitude, longitude, title, d_type, created_date)
VALUES (1, 2, 'https://www.kakaomap.com/1', 38.496490, 126.296959, '인천 빌딩', 'Place', '2023-01-02 11:11:01');

-- 메모 데이터 삽입
INSERT INTO memo (scrap_id, memo_id, memo_text, created_date)
VALUES (2, 1, 'Hello 1', '2023-01-03 11:11:01');

INSERT INTO memo (scrap_id, memo_id, memo_text, created_date, deleted_date)
VALUES (2, 2, 'Hello 2', '2023-01-04 12:11:01', '2023-01-05 12:11:01');
