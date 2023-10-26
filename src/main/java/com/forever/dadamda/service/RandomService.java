package com.forever.dadamda.service;

public class RandomService {

    public static final int adjectivesLength = 30;
    public static final int animalsLength = 30;
    public static final int numberLength = 1000;

    private static final String[] adjectives = {
            "졸린", "귀여운", "용감한", "화난", "흥겨운", "우등생", "달리는", "거대한", "심심한", "더운",
            "추운", "시원한", "쿨한", "슬픈", "예민한", "편안한", "행복한", "즐거운", "아늑한", "빛나는",
            "명량한", "야생의", "똑똑한", "성공한", "출세한", "현명한", "성실한", "정직한", "야심찬", "엉뚱한"
    };

    private static final String[] animals = {
            "쿼카", "악어", "기린", "코끼리", "오리", "너구리", "호랑이", "강아지", "토끼", "사자",
            "원숭이", "고양이", "여우", "수달", "곰", "부엉이", "뱀", "하마", "고래", "판다",
            "사슴", "염소", "다람쥐", "알파카", "낙타", "두더지", "앵무새", "얼룩말", "해달", "코뿔소"
    };

    public static String generateRandomNickname() {
        String adjective = adjectives[(int) (Math.random() * adjectivesLength)];
        String animal = animals[(int) (Math.random() * animalsLength)];
        String number = String.valueOf((int) (Math.random() * numberLength));
        return adjective + animal + number;
    }
}
