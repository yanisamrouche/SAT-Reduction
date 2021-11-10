package Vérificateur_déterministe_pour_SAT;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DeterministicVerificationSAT {

    public static boolean evaluateCNF(String cnfFile, String evalFile){
        boolean isSat = true;
        List<List<Integer>> clauses = ParserCNF.cnfFileParser(cnfFile);
        List<Integer> eval = ParserEval.evalFileParser(evalFile);
        int nbrOfCl = ParserCNF.numberOfClauses;
        for (int i = 0 ; i < nbrOfCl ; i++){
            List<Integer> clause = clauses.get(i);
            for (Integer integer : clause){
                if(!eval.contains(integer)){
                    isSat = false;
                }else {
                    isSat = true;
                    break;
                }
            }
        }
        return isSat;
    }

    public static void main(String[] args) {
        System.out.println(evaluateCNF("src/Vérificateur_déterministe_pour_SAT/DIMACS_CNF_FILES/file2.txt","src/Vérificateur_déterministe_pour_SAT/DIMACS_CNF_FILES/file2_eval.txt"));
    }
}
