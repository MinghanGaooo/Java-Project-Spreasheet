package cs2110;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Scanner;

public class CsvJoin {

    /**
     * Load a table from a Simplified CSV file and return a row-major list-of-lists representation.
     * The CSV file is assumed to be in the platform's default encoding. Throws an IOException if
     * there is a problem reading the file.
     */
    public static Seq<Seq<String>> csvToList(String file) throws IOException {
        Seq<Seq<String>> table = new LinkedSeq<>();
        try(Reader read = new FileReader(file)){
            Scanner scan = new Scanner(read);
            while(scan.hasNextLine()){
                String nextLine = scan.nextLine();
                Seq<String> row = new LinkedSeq<>();
                String[] array = nextLine.split(",", -1);
                for(int i=0; i<array.length; i++){
                    row.append(array[i]);
                }
                table.append(row);
            }
        }catch(IOException e){
            throw new IOException();
        }
        return table;
    }

    /**
     * Return true of table is rectangular and false otherwise
     */
    private static boolean rectangular(Seq<Seq<String>> table){
        int size = table.get(0).size();
        for(int i = 0; i < table.size(); i++){
            if(table.get(i).size() != size){
                return false;
            }
        }
        return true;
    }

    /**
     * Return the left outer join of tables `left` and `right`, joined on their first column. Result
     * will represent a rectangular table, with empty strings filling in any columns from `right`
     * when there is no match. Requires that `left` and `right` represent rectangular tables with at
     * least 1 column.
     */
    public static Seq<Seq<String>> join(Seq<Seq<String>> left, Seq<Seq<String>> right){
        assert left instanceof Seq<Seq<String>>;
        assert right instanceof Seq<Seq<String>>;
        assert left.size() >= 1;
        assert right.size() >= 1;
        assert rectangular(left);
        assert rectangular(right);

        Seq<Seq<String>> combined = new LinkedSeq<>();
        for(int i = 0; i < left.size(); i++){
            Seq<String> combinedRow = new LinkedSeq<>();
            for(int j = 0; j < right.size(); j++){
                if(left.get(i).get(0).equals(right.get(j).get(0))){
                    combinedRow.append(left.get(i).get(0));
                    for(int z = 1; z < left.get(i).size(); z++){
                        combinedRow.append(left.get(i).get(z));
                    }
                    for(int g = 1; g < right.get(j).size(); g++){
                        combinedRow.append(right.get(j).get(g));
                    }
                }
            }
            if(combinedRow.size()==0){
                for(int m = 0; m < left.get(i).size(); m++){
                    combinedRow.append(left.get(i).get(m));
                }
                for(int g = 1; g < right.get(0).size(); g++){
                    combinedRow.append("");
                }
                combined.append(combinedRow);
            }else{
                combined.append(combinedRow);
            }
        }
        return combined;
    }


    private static void print(Seq<Seq<String>> combined){
        for(int i = 0; i < combined.size(); i++){
            String list = "";
            for(int j = 0; j < combined.get(i).size(); j++) {
                if (j < combined.get(i).size() - 1) {
                    list += combined.get(i).get(j) + ",";
                } else {
                    list += combined.get(i).get(j);
                }
            }
            System.out.println(list);
        }
    }


    public static void main(String[] args){
        if (args.length != 2) {
            System.err.println("Usage: CsvJoin <leftCsvFile> <rightCsvFile>");
            System.exit(1);
        }
        String leftCsvFile = args[0];
        String rightCsvFile = args[1];
        try{
            if((!rectangular(csvToList(leftCsvFile)))||
                    (!rectangular(csvToList(rightCsvFile)))){
                System.err.println("Error: Input tables are not rectangular.");
                System.exit(1);
            }
            Seq<Seq<String>> combined = join(csvToList(leftCsvFile), csvToList(rightCsvFile));
            print(combined);
        }catch(IOException e){
            System.err.println("Error: Could not read input tables.");
            System.exit(1);
        }
    }
}
