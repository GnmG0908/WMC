package com.example.jaehyung.seniorproject;

public class algo implements RssiFilter{
    double total;
    int counter;

    algo() {
        total = 0;
        counter = 0;
    }

    public double applyFilter(double insert){
        if (counter<25){
            counter++;
        }
        else{
            counter=25;
        }
        total=(total*(counter-1)/counter)+(insert/counter);
        return  Double.parseDouble(String.format("%.2f",total));
    }
}
