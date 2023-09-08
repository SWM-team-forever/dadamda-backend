-- User 데이터 삽입
INSERT INTO users (user_id, name, provider, role, email, profile_url)
VALUES (1, 'koko', 0, 'USER', '1234@naver.com', 'https://www.naver.com');

-- Product 데이터 삽입
INSERT INTO scrap (user_id, scrap_id, page_url, title, description, site_name, d_type)
VALUES (1, 1, 'https://www.coupang.com', 'Coupang의 맥북 상품', 'MacBook Pro 16인치 2019년형', 'Coupang', 'Product');

INSERT INTO scrap (user_id, scrap_id, page_url, title, description, site_name, d_type, watched_cnt, play_time, published_date)
VALUES (1, 2, 'https://www.youtube.com/123', '오늘의 일기 1', 'Today is sunny', 'Youtube', 'Video', 10, 100, '2020-01-01');

INSERT INTO scrap (user_id, scrap_id, page_url, title, description, site_name, d_type, watched_cnt, play_time, published_date)
VALUES (1, 3, 'https://www.youtube.com/1234', '오늘의 일기 2', 'Today is rainy', 'Youtube', 'Video', 100, 1000, '2020-01-01');

INSERT INTO scrap (user_id, scrap_id, page_url, title, description, site_name, d_type, published_date)
VALUES (1, 4, 'https://www.velog.com/1234', '오늘의 일기 3', 'Today is rainy', 'Velog', 'Article', '2020-01-01');
