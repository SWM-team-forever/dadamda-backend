-- User 데이터 삽입
INSERT INTO users (user_id, name, provider, role, email, profile_url)
VALUES (1, 'koko', 0, 'USER', '1234@naver.com', 'https://www.naver.com');

-- Other 데이터 삽입
INSERT INTO scrap (user_id, scrap_id, page_url, title, description, d_type)
VALUES (1, 1, 'https://www.google.com/1', 'Google Main Page 1', '구글 메인 페이지 1 입니다.', 'Other');

INSERT INTO scrap (user_id, scrap_id, page_url, title, description, d_type)
VALUES (1, 2, 'https://www.google.com/2', 'Google Main Page 2', '구글 메인 페이지 2 입니다.', 'Other');

INSERT INTO scrap (user_id, scrap_id, page_url, title, description, d_type)
VALUES (1, 3, 'https://www.google.com/3', 'Google Main Page 3', '구글 메인 페이지 3 입니다.', 'Other');

INSERT INTO scrap (user_id, scrap_id, page_url, title, description, d_type)
VALUES (1, 4, 'https://www.google.com/4', 'Google Main Page 4', '구글 메인 페이지 4 입니다.', 'Other');
