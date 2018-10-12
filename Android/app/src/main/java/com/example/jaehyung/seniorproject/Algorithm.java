package com.example.jaehyung.seniorproject;

//-35~-125
public class Algorithm {
    //회전 반경 설정 플래그
    boolean flag = true;
    double weight = 0;

    //출력 값 알고리즘
    public String algorithm(double f, double l, double r) {
        //디폴트 값
        String result = "F";

        //비콘과 모바일 간격이 가까울 경우
        if ((r + f + l) / 3 > (-71)) {
            result = "stop";
            return result;
        }
        //R과 L의 값 차이가 x이상 날 경우
        if ((r - l > -2.8 - weight && r - l < 2.8 + weight && flag)) {
            result = "F";
            if (weight > 0)
                weight -= 0.2;
            else
                weight = 0;
        } else {
            flag = false;
            //R 신호가 강할시
            if (r > l) {
                if (r - l > 6.3) {
                    result = "stop";
                    return result;
                }
                if (r - l > 2.8) {
                    result = "R";
                    weight += 0.2;
                    flag = true;
                }
            }
            //L 신호가 강할시
            if (r < l) {
                if (l - r > 6.3) {
                    result = "stop";
                    return result;
                }
                if (l - r > 2.8) {
                    result = "L";
                    weight += 0.2;
                    flag = true;
                }
            }
        }
        return result;
    }
}