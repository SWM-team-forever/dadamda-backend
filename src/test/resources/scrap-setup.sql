-- User 데이터 삽입
INSERT INTO users (user_id, name, provider, role, email, profile_url)
VALUES (1, 'koko', 0, 'USER', '1234@naver.com', 'https://www.naver.com');

-- Product 데이터 삽입
INSERT INTO scrap (user_id, scrap_id, page_url, title, description, site_name, d_type, created_date)
VALUES (1, 1, 'https://www.coupang.com', 'Coupang의 맥북 상품', 'MacBook Pro 16인치 2019년형', 'Coupang', 'Product', '2023-01-01 10:11:01');

-- Video 데이터 삽입
INSERT INTO scrap (user_id, scrap_id, page_url, title, description, site_name, d_type, watched_cnt, play_time, published_date, created_date)
VALUES (1, 2, 'https://www.youtube.com/123', '오늘의 일기 1', 'Today is sunny', 'Youtube', 'Video', 10, 100, '2020-01-01', '2023-01-02 11:11:01');

INSERT INTO scrap (user_id, scrap_id, page_url, title, description, site_name, d_type, watched_cnt, play_time, published_date, created_date)
VALUES (1, 3, 'https://www.youtube.com/1234', '오늘의 일기 2', 'Today is rainy', 'Youtube', 'Video', 100, 1000, '2020-01-01', '2023-01-03 11:11:01');

-- Article 데이터 삽입
INSERT INTO scrap (user_id, scrap_id, page_url, title, description, site_name, d_type, published_date, created_date)
VALUES (1, 4, 'https://www.velog.com/1234', '오늘의 일기 3', 'Today is rainy', 'Velog', 'Article', '2020-01-01', '2023-01-04 11:11:01');

-- Place 데이터 삽입
INSERT INTO scrap (user_id, scrap_id, page_url, latitude, longitude, title, d_type, created_date)
VALUES (1, 5, 'https://www.kakaomap.com/1', 37.00000000000000, 127.02969594506668, '서울 빌딩', 'Place', '2023-01-05 11:11:01');

INSERT INTO scrap (user_id, scrap_id, page_url, latitude, longitude, title, d_type, created_date)
VALUES (1, 6, 'https://www.kakaomap.com/1', 38.496490, 126.296959, '인천 빌딩', 'Place', '2023-01-06 11:11:01');

-- 메모 데이터 삽입
INSERT INTO memo (scrap_id, memo_id, memo_text, created_date)
VALUES (6, 1, 'Hello 1', '2023-01-01 11:11:01');

INSERT INTO memo (scrap_id, memo_id, memo_text, created_date)
VALUES (6, 2, 'Hello 2', '2023-01-03 12:11:01');
