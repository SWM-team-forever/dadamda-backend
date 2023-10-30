-- User 데이터 삽입
INSERT INTO users (user_id, name, provider, role, email, profile_url, nickname, uuid)
VALUES (1, 'koko', 0, 'USER', '1234@naver.com', 'https://www.naver.com', '귀여운해달1', '0782ef48-a439-01');

-- Product 데이터 삽입
INSERT INTO scrap (user_id, scrap_id, page_url, title, description, site_name, d_type, created_date)
VALUES (1, 1, 'https://www.coupang.com', 'Coupang의 맥북 상품', 'MacBook Pro 16인치 2019년형', 'Coupang', 'Product', '2023-01-10 09:11:01');

-- Video 데이터 삽입
INSERT INTO scrap (user_id, scrap_id, page_url, title, description, site_name, d_type, watched_cnt, play_time, published_date, created_date)
VALUES (1, 2, 'https://www.youtube.com/123', '오늘의 일기 1', 'Today is sunny', 'Youtube', 'Video', 10, 100, '2020-01-01', '2023-01-01 11:11:01');

INSERT INTO scrap (user_id, scrap_id, page_url, title, description, site_name, d_type, watched_cnt, play_time, published_date, created_date)
VALUES (1, 3, 'https://www.youtube.com/1234', '오늘의 일기 2', 'Today is rainy', 'Youtube', 'Video', 100, 1000, '2020-01-01', '2023-01-02 11:11:01');

-- Article 데이터 삽입
INSERT INTO scrap (user_id, scrap_id, page_url, title, description, site_name, d_type, published_date, created_date)
VALUES (1, 4, 'https://www.velog.com/1234', '오늘의 일기 3', 'Today is rainy', 'Velog', 'Article', '2020-01-01', '2023-01-03 11:11:01');

INSERT INTO scrap (user_id, scrap_id, page_url, title, description, site_name, d_type, published_date, created_date, deleted_date)
VALUES (1, 5, 'https://www.velog.com/1234', '내일의 날씨', 'Tomorrow is rainy', 'Velog', 'Article', '2020-01-01', '2023-01-04 11:11:01', '2023-01-05 11:11:01');

-- 메모 데이터 삽입
INSERT INTO memo (scrap_id, memo_id, memo_text, created_date)
VALUES (1, 1, 'Hello 1', '2023-01-01 11:11:01');

INSERT INTO memo (scrap_id, memo_id, memo_text, created_date)
VALUES (1, 2, 'Hello 2', '2023-01-03 12:11:01');

INSERT INTO memo (scrap_id, memo_id, memo_text, created_date, deleted_date)
VALUES (1, 3, 'Hello 3', '2023-01-04 12:11:01', '2023-01-05 12:11:01');

INSERT INTO memo (scrap_id, memo_id, memo_text, created_date)
VALUES (4, 4, 'Hello 4', '2023-01-05 11:11:01');

INSERT INTO memo (scrap_id, memo_id, memo_text, created_date, deleted_date)
VALUES (4, 5, 'Hello 5', '2023-01-06 12:11:01', '2023-01-07 12:11:01');

INSERT INTO memo (scrap_id, memo_id, memo_text, created_date)
VALUES (2, 6, 'Hello 6', '2023-01-05 11:11:01');

INSERT INTO memo (scrap_id, memo_id, memo_text, created_date, deleted_date)
VALUES (2, 7, 'Hello 7', '2023-01-06 12:11:01', '2023-01-07 12:11:01');
