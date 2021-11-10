package Vérificateur_déterministe_pour_SAT;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ParserCNF {
    static int numberOfVariables ;
    static int numberOfClauses ;


    public static List<List<Integer>> cnfFileParser(String cnfFile){
        List<String> fileStrings = new ArrayList<>();
        List<Integer> clause = new ArrayList<>();
        List<List<Integer>> clauses = new ArrayList<>();
        try {
            Stream<String> file = Files.lines(Paths.get(cnfFile));
            for(String line : file.toList()){
                String[] strings = line.split(" ");
                for (String s : strings){
                    fileStrings.add(s);
                }
            }
            numberOfVariables = Integer.parseInt(fileStrings.get(2));
            numberOfClauses = Integer.parseInt(fileStrings.get(3));
           fileStrings = fileStrings.subList(4,fileStrings.size());
            for (int i=0; i<fileStrings.size(); i++){
                if(!fileStrings.get(i).equals("0")){
                    int l = Integer.parseInt(fileStrings.get(i));
                    clause.add(l);
                }else {
                    clauses.add(clause);
                    clause = new ArrayList<>();
                }
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
        return clauses;

    }
}
