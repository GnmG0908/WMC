package com.example.jaehyung.seniorproject;


public class Cary_algorithm {
    double pre_left = 0;
    double pre_front = 0;
    double pre_right = 0;
    double mean_left = 0;
    double mean_front = 0;
    double mean_right = 0;
    int flag3_count = 0;//4번 연속을 count 하는 flag

    public Cary_algorithm(){
    }

    public Cary_algorithm(double left, double front, double right){
        this.pre_left=left;
        this.pre_front=front;
        this.pre_right=right;
        this.mean_left=left;
        this.mean_front=front;
        this.mean_right=right;
    }

    public String max(double left, double front, double right) {//left, right , front 의 값중 max를 결정하여 방향 결정 함
        String max = "";
        if (left > front) {
            max = "L";
            if (left > right) {
                max = "L";
            } else if (left < right) {
                max = "R";
            }
        } else if (left < front) {
            max = "F";
            if (front > right) {
                max = "F";
            } else if (front < right) {
                max = "R";
            }
        }

        if(left==front||front==right||left==right){
            max="F";
        }

        return max;

    }

    public String mean(double left, double front, double right) {//누적값의 평균값
        mean_left += left;
        mean_front += front;
        mean_right += right;
        mean_left = mean_left / 2;
        mean_front = mean_front / 2;
        mean_right = mean_right / 2;
        return max(left, front, right);// max함수 호출하여 방향 제어함

    }

    public String value_refine(double left, double front, double right) {
        String max = "";
        double std = 5;//기준값에서 위아래로 3 차이나는것은 튀는 값임
        int flag = 0;//튄값이 몇개인지 count하는 flag


        if(flag3_count<4){
            if (left < mean_left - std || left > mean_left + std) {//왼쪽 값이 튀었을때
                left = pre_left;
                flag++;
            }
            if (front < mean_front - std || front > mean_front + std) {//앞쪽 값이 튀었을때
                front = pre_front;
                flag++;
            }
            if (right < mean_right - std || right > mean_right + std) {//오른쪽 값이 튀었을때
                right = pre_right;
                flag++;
            }
        }

        pre_left=left;
        pre_front=front;
        pre_right=right;

        if(flag==0){//비콘 값중 튄 값이 없음
            max = mean(left, front, right);
            flag3_count=0;
        }else if (flag == 1) {//비콘 값 중 튄 값이 1개임
            max = mean(left, front, right);
            flag3_count=0;
        } else if (flag == 2) {//비콘 값 중 튄 값이 2개임
            max = mean(left, front, right);
            flag3_count=0;
        } else if (flag == 3) {//비콘 값 중 튄값이 3개임
            if (flag3_count == 4) {//비콘 값 중 튄값이 3개로 연속 4번 나옴=>방향이 변화댐
                mean_left=0;
                mean_front=0;
                mean_right=0;
                max = mean(left, front, right);//방향 결정하기 위한 함수 호출
            } else {//비콘 값 중 튄값이 3개이지만 연속 4번이 나오지 않음=>비콘 값의 오류
                max = mean(left, front, right);
                flag3_count++;
            }
        }

        return max;
    }

    public String beacon(double left, double front, double right) {
        String max = "";
        max = this.value_refine(left, front, right);
//		System.out.println(">>>"+max);

        return max;
    }

}
