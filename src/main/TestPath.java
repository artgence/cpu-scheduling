package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TestPath {
    public static void main(String[] args) throws IOException {
        System.out.println(System.getProperty("user.dir"));
        File file = new File("/home/circleci/project/src/main/resources/CPU Scheduling.fxml");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = "";
        String allcase = "";
        while ((line = br.readLine())!= null){
            allcase = allcase + line;
        };
        br.close();
        System.out.println(allcase);
    }
}
