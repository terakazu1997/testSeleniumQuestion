package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Test {

    public static void main(String[] args) {
        try {
            // FileWriterクラスのオブジェクトを生成する
            FileWriter file = new FileWriter("./log/java.txt", true);
            // PrintWriterクラスのオブジェクトを生成する
            PrintWriter pw = new PrintWriter(new BufferedWriter(file));
            //ファイルに追記する
            pw.println("pineapple");
            pw.println("banana");
            file = new FileWriter("./log/java2.txt", true);
            pw = new PrintWriter(new BufferedWriter(file));
            pw.println("2pineapple");
            pw.println("3banana");
            //ファイルを閉じる
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
