package com.company;

public class Main {

    public static void main(String[] args) {

        String name = "해쉬";
        name = name.concat(" 입니다"); // new String()
        System.out.println(name);

        System.out.println("===========================");

        StringBuilder builder = new StringBuilder("에이든");
        System.out.println("builder1 : " + builder.hashCode());
        builder.append(" 입니다");
        System.out.println("builder2 : " + builder.hashCode());
        System.out.println(builder);

    }
}
