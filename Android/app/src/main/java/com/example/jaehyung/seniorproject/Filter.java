package com.example.jaehyung.seniorproject;

public class Filter implements RssiFilter{
    double total;
    int counter;

    Filter() {
        total = 0;
        counter = 0;
    }

    public double applyFilter(double insert){
        if (counter<10){
            counter++;
        }
        else{
            counter=10;
        }
        total=((total*(counter-1))/counter)+(insert/counter);
        return  total;
    }
}
