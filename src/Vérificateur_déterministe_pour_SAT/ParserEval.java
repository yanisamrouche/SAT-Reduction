package Vérificateur_déterministe_pour_SAT;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ParserEval {
    public static List<Integer> evalFileParser(String evalFile){
        List<Integer> eval = new ArrayList<>();
        try {
            Stream<String> file = Files.lines(Paths.get(evalFile));
            String fileLine = file.toList().get(0);
            String[] evalLine = fileLine.split(" ");
            for(String evl : evalLine){
                eval.add(Integer.valueOf(evl));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return eval;
    }

}
