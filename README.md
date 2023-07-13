
# ERD
![dadamda-erd](https://github.com/SWM-team-forever/dadamda-backend/assets/91049936/75739e5c-feea-4c44-9a79-23ecbdf30fd1)

| **ARTICLE** |  |  |  |  |  |  |  |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 키 | 논리 | 물리 | 도메인 | 타입 | Null 허용 | 기본값 | 코멘트 |
| PK, FK | 아이템ID | ITEM_ID |  | BIGINT | N |  | PK, FK |
|  | 저자 | AUTHOR |  | text | Y |  |  |
|  | 저자 프로필 이미지 url | AUTHOR_IMAGE_URL |  | VARCHAR(2083) | Y |  |  |
|  | 사이트명 | SITE_NAME |  | VARCHAR(100) | Y |  |  |
|  | 게시일 | PUBLISHED_DT |  | timestamp | Y |  |  |
|  | 블로그명 | BLOG_NAME |  | VARCHAR(100) | Y |  |  |
|  |  |  |  |  |  |  |  |
| **USER** |  |  |  |  |  |  |  |
| 키 | 논리 | 물리 | 도메인 | 타입 | Null 허용 | 기본값 | 코멘트 |
| PK | 유저ID | USER_ID |  | BIGINT | N |  | AI |
|  | 이름 | NAME |  | VARCHAR(100) | N |  |  |
|  | 이메일 | EMAIL |  | VARCHAR(320) | N |  |  |
|  | 프로필 이미지 URL | PROFILE_URL |  | VARCHAR(2083) | N |  |  |
|  | 소셜 정보 | PROVIDER |  | enum | N |  |  |
|  | 권한 | ROLE |  | enum | N |  |  |
|  | 유저 uuid | UUID |  | VARCHAR(36) | N |  |  |
|  |  |  |  |  |  |  |  |
| **VIDEO** |  |  |  |  |  |  |  |
| 키 | 논리 | 물리 | 도메인 | 타입 | Null 허용 | 기본값 | 코멘트 |
| PK, FK | ITEM_ID | ITEM_ID |  | BIGINT | N |  | PK, FK |
|  | 영상 재생 url | EMBED_URL |  | VARCHAR(2083) | Y |  |  |
|  | 채널명 | CHANNEL_NAME |  | VARCHAR(100) | Y |  |  |
|  | 채널 프로필 이미지 url | CHANNEL_IMAGE_URL |  | VARCHAR(2083) | Y |  |  |
|  | 조회수 | WATCHED_CNT |  | BIGINT | Y |  |  |
|  | 재생 시간 | PLAY_TIME |  | BIGINT | Y |  |  |
|  | 게시일 | PUBLISHED_AT |  | timestamp | Y |  |  |
|  | 사이트명 | SITE_NAME |  | VARCHAR(100) | Y |  |  |
|  | 영상 장르 | GENRE |  | VARCHAR(100) | Y |  |  |
|  |  |  |  |  |  |  |  |
| **PRODUCT** |  |  |  |  |  |  |  |
| 키 | 논리 | 물리 | 도메인 | 타입 | Null 허용 | 기본값 | 코멘트 |
| PK, FK | ITEM_ID | ITEM_ID |  | BIGINT | N |  | PK, FK |
|  | 가격 | PRICE |  | VARCHAR(100) | Y |  |  |
|  | 사이트명 | SITE_NAME |  | VARCHAR(100) | Y |  |  |
|  |  |  |  |  |  |  |  |
| **ITEM** |  |  |  |  |  |  |  |
| 키 | 논리 | 물리 | 도메인 | 타입 | Null 허용 | 기본값 | 코멘트 |
| PK | 아이템ID | ITEM_ID |  | BIGINT | N |  | AI |
| FK | 유저ID | USER_ID |  | BIGINT | N |  |  |
|  | 카테고리 타입 | D_TYPE |  | ENUM | N |  |  |
|  | 페이지 URL | PAGE_URL |  | VARCHAR(2083) | N |  |  |
|  | 제목 | TITLE |  | VARCHAR(200) | Y |  |  |
|  | 대표사진 URL | THUMBNAIL_URL |  | VARCHAR(2083) | Y |  |  |
|  | 설명 | DESCRIPTION |  | text | Y |  |  |
|  |  |  |  |  |  |  |  |
| **BASETIME** |  |  |  |  |  |  |  |
| 키 | 논리 | 물리 | 도메인 | 타입 | Null 허용 | 기본값 | 코멘트 |
|  | 생성 날짜 | CREATED_AT |  | timestamp | N |  |  |
|  | 수정 날짜 | UPDATED_DT |  | timestamp | Y |  |  |
|  | 삭제 날짜 | DELETED_DT |  | timestamp | Y |  |  |
|  |  |  |  |  |  |  |  |
| **MEMO** |  |  |  |  |  |  |  |
| 키 | 논리 | 물리 | 도메인 | 타입 | Null 허용 | 기본값 | 코멘트 |
| PK | 메모ID | MEMO_ID |  | BIGINT | N |  | AI |
| FK | 아이템ID | ITEM_ID |  | BIGINT | N |  |  |
|  | 메모글 | MEMO_TEXT |  | text | Y |  |  |
|  | 메모 사진 url | MEMO_IMAGE_URL |  | VARCHAR(2083) | Y |  |  |
|  |  |  |  |  |  |  |  |
| **BOARD** |  |  |  |  |  |  |  |
| 키 | 논리 | 물리 | 도메인 | 타입 | Null 허용 | 기본값 | 코멘트 |
| PK | 보드ID | BOARD_ID |  | BIGINT | N |  | AI |
|  | 보드UUID | UUID |  | VARCHAR(36) | Y |  |  |
| FK | 유저ID | USER_ID |  | BIGINT | N |  |  |
|  | 보드명 | NAME |  | VARCHAR(100) | Y |  |  |
|  | 게시 여부 | IS_PUBLIC |  | BOOLEAN | N | FALSE | T : 보드 게시 O |
|  |  |  |  |  |  |  |  |
| **ITEM_BOARD** |  |  |  |  |  |  |  |
| 키 | 논리 | 물리 | 도메인 | 타입 | Null 허용 | 기본값 | 코멘트 |
| PK, FK | 보드ID | BOARD_ID |  | BIGINT | N |  | AI |
| FK | 아이템ID | ITEM_ID |  | BIGINT | N |  |  |
| FK | 메모ID | MEMO_ID |  | BIGINT | N |  |  |
|  | x좌표 | X |  | DOUBLE | N |  |  |
|  | y좌표 | Y |  | DOUBLE | N |  |  |
|  | 가로길이 | WIDTH |  | DOUBLE | N |  |  |
|  | 세로 길이 | HEIGHT |  | DOUBLE | N |  |  |
|  |  |  |  |  |  |  |  |
| **HEART** |  |  |  |  |  |  |  |
| 키 | 논리 | 물리 | 도메인 | 타입 | Null 허용 | 기본값 | 코멘트 |
| PK | 하트ID | HEART_ID |  | BIGINT | N |  | AI |
| FK | 유저ID | USER_ID |  | BIGINT | N |  |  |
| FK | 보드ID | BOARD_ID |  | BIGINT | N |  |  |
|  |  |  |  |  |  |  |  |