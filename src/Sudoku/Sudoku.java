package Sudoku;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Sudoku {

    static int N = 9;
    static int[][] grid =new  int[N][N];
    static int propositional_variables = N*N*N;
    public static boolean variablesEval(int i, int j, int cellValue){ return grid[i][j] == cellValue;}

    /** Individual cell clauses
     * Clauses have to be included to indicate that a cell contains exactly one value in the range 1 to 9
     * 123 indicates that row 1, column 2 contains 3.
     * -456 indicates that row 4, column 5 does not contain 6.
     * The first line of the problem is of the form "p cnf num-of-variables num-of-clauses".
     */
    public static String contains_value(int row, int col, int value){
        String clause = "";
        clause += row+col+value;
        clause+="0";
        System.out.println("row "+row+" column "+col+" contains "+value);
        return clause;
    }
    public static String does_not_contain_value(int row, int col, int value){
        String clause = "-";
        clause += row+col+value;
        clause+="0";
        System.out.println("row "+row+" column "+col+" does not contain "+value);
        return clause;
    }
    public static String individual_cell_clauses(int row, int col){
        String clauses = "";
        for(int i=1; i <=N; i++){
            clauses += String.valueOf(row)+String.valueOf(col)+i+" ";
        }
        clauses+="0";
        System.out.println("a cell contains exactly one value in the range 1 to 9");
        return clauses;
    }

    //These have to be repeated for each of the 81 cells.
    public static String contains_one_only(int row, int col, int value1){
        String clauses ="";
        String part1 = "-"+String.valueOf(row)+String.valueOf(col)+row+" ";
        String part2 = "-"+String.valueOf(row)+String.valueOf(row)+value1+" 0";
        clauses+=part1+part2;
       // System.out.println("cell in row "+row+" column "+col+" can't be both "+value1+" and "+value2);
        return clauses;

    }

    /** Row clauses */
    //repeated for values 1->9
    public static String row_x_contains_v(int row, int value){//to show that row 1 contains value 1 : 111 121 131 141 151 161 171 181 191 0
        String clauses = "";
        for(int i=0; i < N; i++){
            clauses += row+String.valueOf(i)+String.valueOf(value)+" ";
        }
        clauses += "0";
        System.out.println("row "+row+" contains "+value);
        return clauses;
    }

    public static String does_not_contain_more_than(int row, int col, int value){
        String clauses = "";
        String part1 ="-"+String.valueOf(row)+String.valueOf(row)+String.valueOf(value)+" ";
        String part2 ="-"+String.valueOf(row)+String.valueOf(col)+String.valueOf(value);
        clauses+=part1+part2;
        clauses+=" 0";
        System.out.println("the square "+row+" and "+col+" aren't both"+value);
        return clauses;
    }

    /** Column clauses */
    //to show that column 1 contains a 1
    public static String column_x_contains_v(int col, int value){
        String clauses = "";
        for(int i=0; i <= N; i++){
            clauses += String.valueOf(i)+col+String.valueOf(value)+" ";
        }
        clauses += "0";
        System.out.println("col "+col+" contains "+value);
        return clauses;
    }
    public static String does_not_contain_more_thanc(int row, int col, int value){
        String clauses = "";
        String part1 ="-"+String.valueOf(row)+String.valueOf(row)+String.valueOf(value)+" ";
        String part2 ="-"+String.valueOf(col)+String.valueOf(row)+String.valueOf(value);
        clauses+=part1+part2;
        clauses+=" 0";
        System.out.println("the square "+row+" and "+col+" aren't both"+value);
        return clauses;
    }
    /** Block clauses */
    //to show that the top left block contains a certain value (for example 1)
    public static String block_clauses_contains_v(int start_row, int end_row, int start_col, int end_col  ,int value){
        String clauses="";
        for(int i=start_row; i <= end_row; i++){
            for(int j=start_col; j <= end_col; j++){
                clauses+=String.valueOf(i)+String.valueOf(j)+value+" ";
            }
        }
        clauses+="0";
        //System.out.println("to show that the block : "+block_row+","+block_col+" contains a "+value);
        return clauses;
    }

    /** Pre-filled cells
     *  For example if cell <2,3> has to contain 4, we add the clause
     * 234 0
     * */
    public static String specify_initial_cells(int row, int col, int value){
        String clause="";
        clause+=String.valueOf(row)+String.valueOf(col)+value+" 0";
        System.out.println("the cell ("+row+","+col+")"+" has to contain "+value);
        return clause;

    }

    public static File reduction_to_sat() throws IOException {
        File outFile = new File("src/Sudoku/cnf.txt");
        outFile.getParentFile().mkdirs();
        Writer writer = new FileWriter(outFile, StandardCharsets.UTF_8);
        BufferedWriter bufferedWriter = new BufferedWriter(writer,16384);
        bufferedWriter.write("p cnf num-of-variables num-of-clauses");
        bufferedWriter.newLine();
        //Individual cell clauses
        for(int i=0; i<N;i++){
            for(int j=0; j<N;j++){
                bufferedWriter.write(individual_cell_clauses(i,j));
                bufferedWriter.newLine();
            }
        }
        bufferedWriter.flush();

        for(int i=0; i < N; i++){
            for(int j=1; j<N;j++) {
                if(i != j) {
                    bufferedWriter.write(contains_one_only(i, i, j));
                    bufferedWriter.newLine();
                }
            }
        }
        bufferedWriter.flush();


        //Row clauses
        for(int i=0; i < N; i++){
            for (int j=1; j <= N; j++){
                bufferedWriter.write(row_x_contains_v(i,j));
                bufferedWriter.newLine();
            }

        }
        bufferedWriter.flush();
        for(int k=1; k<=N;k++){
            for(int i=0; i < N; i++){
                for (int j=1; j < N; j++) {
                    if(i != j) {
                        bufferedWriter.write(does_not_contain_more_than(i, j, k));
                        bufferedWriter.newLine();
                    }

                }
            }
        }
        bufferedWriter.flush();
        //Column clauses
        for(int i=0; i < N; i++){
            for (int j=1; j <= N; j++) {
                bufferedWriter.write(column_x_contains_v(i, j));
                bufferedWriter.newLine();
            }
        }
        bufferedWriter.flush();
        for(int k=1; k<=N;k++){
            for(int i=0; i < N; i++){
                for (int j=1; j < N; j++) {
                    if(i != j) {
                        bufferedWriter.write(does_not_contain_more_thanc(i, j, k));
                        bufferedWriter.newLine();
                    }

                }
            }
        }
        //Block clauses
        // block top left
        for(int k=1; k <= N; k++){
            bufferedWriter.write(block_clauses_contains_v(0,2,0,2,k));
            bufferedWriter.newLine();
        }
        //block top middle
        for(int k=1; k <= N; k++){
            bufferedWriter.write(block_clauses_contains_v(0,2,3,5,k));
            bufferedWriter.newLine();
        }
        //block top right
        for(int k=1; k <= N; k++){
            bufferedWriter.write(block_clauses_contains_v(0,2,6,8,k));
            bufferedWriter.newLine();
        }
        // block middle left
        for(int k=1; k <= N; k++){
            bufferedWriter.write(block_clauses_contains_v(3,5,0,2,k));
            bufferedWriter.newLine();
        }
        //block middle
        for(int k=1; k <= N; k++){
            bufferedWriter.write(block_clauses_contains_v(3,5,3,5,k));
            bufferedWriter.newLine();
        }
        //block middle right
        for(int k=1; k <= N; k++){
            bufferedWriter.write(block_clauses_contains_v(3,5,6,8,k));
            bufferedWriter.newLine();
        }
        // block bottom left
        for(int k=1; k <= N; k++){
            bufferedWriter.write(block_clauses_contains_v(6,8,0,2,k));
            bufferedWriter.newLine();
        }
        //block bottom middle
        for(int k=1; k <= N; k++){
            bufferedWriter.write(block_clauses_contains_v(6,8,3,5,k));
            bufferedWriter.newLine();
        }
        //block bottom right
        for(int k=1; k <= N; k++){
            bufferedWriter.write(block_clauses_contains_v(6,8,6,8,k));
            bufferedWriter.newLine();
        }

        //Pre-filled cells
        for(int i=0; i<N;i++){
            for(int j=0; j<N;j++){
                for(int k=1; k<=9;k++) {
                    bufferedWriter.write(specify_initial_cells(i, j, k));
                    bufferedWriter.newLine();
                }
            }
        }
        /*
        for(int i=0; i<N;i++){
            for(int j=0; j<N;j++){
                for(int k=1; k<=9;k++) {
                    bufferedWriter.write("-"+specify_initial_cells(i, j, k));
                    bufferedWriter.newLine();
                }
            }
        }

         */





        bufferedWriter.close();
        return outFile;

    }
    public static void main(String[] args) throws IOException {
        System.out.println(individual_cell_clauses(1,1));
        //System.out.println(contains_one_only(1,1,1,2));
        System.out.println(row_x_contains_v(1,2));
        System.out.println(does_not_contain_more_than(1,2,1));
        System.out.println(does_not_contain_more_than(1,3,1));
        System.out.println(column_x_contains_v(1,1));
        //System.out.println(block_clauses_contains_v(3,5,1));
        System.out.println(specify_initial_cells(2,3,4));
        reduction_to_sat();
    }


}
