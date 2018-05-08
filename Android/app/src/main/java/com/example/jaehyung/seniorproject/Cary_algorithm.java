package com.example.jaehyung.seniorproject;

//-35~-125
public class Cary_algorithm {
    double pre_left = 0;
    double pre_front = 0;
    double pre_right = 0;
    double mean_left = 0;
    double mean_front = 0;
    double mean_right = 0;
    int flag3_count = 0;
    String pre_max_str1="";
    String pre_max_str2="";
    String pre_max_str3="";

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

    public String max(double left, double front, double right) {
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
        System.out.println(left+" "+front+" "+right);
        if(left==front||left==right||right==front){
            max="F";
        }
        return max;

    }

    public String mean(double left, double front, double right) {
        mean_left += left;
        mean_front += front;
        mean_right += right;
        mean_left = mean_left / 2;
        mean_front = mean_front / 2;
        mean_right = mean_right / 2;
        System.out.println(mean_left+" "+mean_front+" "+mean_right);
        return max(left, front, right);// max함수 호출하여 방향 제어함

    }

    public String value_refine(double left, double front, double right) {
        String max = "";
        double std = 5;//기준값에서 위아래로 3 차이나는것은 튀는 값임
        int flag = 0;

        if(flag3_count<3){
            if (left < mean_left - std || left > mean_left + std) {
                left = pre_left;
                flag++;
            }
            if (front < mean_front - std || front > mean_front + std) {
                front = pre_front;
                flag++;
            }
            if (right < mean_right - std || right > mean_right + std) {
                right = pre_right;
                flag++;
            }
        }

        pre_left=left;
        pre_front=front;
        pre_right=right;

        if(flag==0){
            max = mean(left, front, right);
            flag3_count=0;
        }else if (flag == 1) {
            max = mean(left, front, right);
            flag3_count=0;
        } else if (flag == 2) {
            max = mean(left, front, right);
            flag3_count=0;
        } else if (flag == 3) {
            if (flag3_count == 5) {
                mean_left=0;
                mean_front=0;
                mean_right=0;
                max = mean(left, front, right);
            } else {
                max = mean(left, front, right);
                flag3_count++;
            }

        }

        return max;
    }

    public String beacon(double left, double front, double right) {
        String max = "";
        max = this.value_refine(left, front, right);
        System.out.println(">>>"+max);

        return max;
    }

}