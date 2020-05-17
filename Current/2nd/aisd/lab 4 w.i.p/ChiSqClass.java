public class ChiSqClass {
    private int[][] dataArray;
    private String[][] stringArray;
    private String[][] outputStringTabInner;
    private int expectedValueTable;

    public ChiSqClass(String[][] inputArray){
        this.stringArray = new String[inputArray.length][inputArray[0].length];
        this.dataArray = new int[stringArray.length - 1][stringArray[0].length - 1];

        for(int i = 0; i < stringArray.length; i++){
            for(int j = 0; j < stringArray[0].length; j++){
                stringArray[i][j] = inputArray[i][j];
            }
        }
        for(int i = 0; i < dataArray.length; i++){
            for(int j = 0; j < dataArray[0].length; j++){
                dataArray[i][j] = Integer.parseInt(stringArray[i+1][j+1]);
            }
        }
    }

    private int[] calculatePartSums(){
        int[] resultArr = new int[dataArray.length + dataArray[0].length + 1];
        int sum;
        for(int i = 0; i < dataArray.length; i++){
            sum = 0;
            for (int j = 0; j < dataArray[0].length; j++){
                sum += dataArray[i][j];
            }
            resultArr[i] = sum;
        }
        for(int j = 0; j < dataArray[0].length; j++){
            sum = 0;
            for (int i = 0; i < dataArray.length; i++){
                sum += dataArray[i][j];
            }
            resultArr[j + dataArray.length] = sum;
        }
        sum = 0;
        for(int i = 0; i < dataArray.length; i++){
            sum += resultArr[i];
        }
        resultArr[dataArray.length + dataArray[0].length] = sum;
        return resultArr;
    }

    public void printPartSumTable(){
        int[] partSumTab = calculatePartSums();
        String[][] outputStringTab = new String[stringArray.length+1][stringArray[0].length+1];
        for(int i = 0; i < stringArray.length; i++){
            for(int j = 0; j < stringArray[0].length; j++){
                outputStringTab[i][j] = stringArray[i][j];
            }
        }
        outputStringTab[0][stringArray[0].length] = "SUMA";
        outputStringTab[stringArray.length][0] = "SUMA";
        for(int i = 1; i < stringArray.length; i++){
            outputStringTab[i][stringArray[0].length] = Integer.toString(partSumTab[i-1]);
        }
        for(int j = 1; j < stringArray[0].length; j++){
            outputStringTab[stringArray.length][j] = Integer.toString(partSumTab[j + dataArray.length - 1]);
        }
        outputStringTab[stringArray.length][stringArray[0].length] = Integer.toString(partSumTab[dataArray.length + dataArray[0].length]);

        for(int i = 0; i < outputStringTab.length; i++){
            for (int j = 0; j < outputStringTab[0].length; j++){
                System.out.print(String.format("%-16s", outputStringTab[i][j]));
            }
            System.out.println();
        }
        outputStringTabInner = outputStringTab;
    }

    private void makeExpectedValueTable(){
        int[][] tempTab = new int[outputStringTabInner.length][outputStringTabInner[0].length];
        
    }




}
