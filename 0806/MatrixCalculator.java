public class MatrixCalculator {
    
    public static int[][] addMatrix(int[][] matrix1, int[][] matrix2) {
        if (matrix1.length != matrix2.length || matrix1[0].length != matrix2[0].length) {
            System.out.println("錯誤：矩陣維度不同，無法進行加法運算！");
            return null;
        }
        
        int rows = matrix1.length;
        int cols = matrix1[0].length;
        int[][] result = new int[rows][cols];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }
        
        return result;
    }
    
    public static int[][] multiplyMatrix(int[][] matrix1, int[][] matrix2) {
        if (matrix1[0].length != matrix2.length) {
            System.out.println("錯誤：矩陣無法相乘！第一個矩陣的列數必須等於第二個矩陣的行數。");
            return null;
        }
        
        int rows1 = matrix1.length;
        int cols1 = matrix1[0].length;
        int cols2 = matrix2[0].length;
        int[][] result = new int[rows1][cols2];
        
        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < cols2; j++) {
                result[i][j] = 0;
                for (int k = 0; k < cols1; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
        
        return result;
    }
    
    public static int[][] transposeMatrix(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] result = new int[cols][rows];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[j][i] = matrix[i][j];
            }
        }
        
        return result;
    }
    
    public static int findMaxValue(int[][] matrix) {
        int max = matrix[0][0];
        
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] > max) {
                    max = matrix[i][j];
                }
            }
        }
        
        return max;
    }
    
    public static int findMinValue(int[][] matrix) {
        int min = matrix[0][0];
        
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] < min) {
                    min = matrix[i][j];
                }
            }
        }
        
        return min;
    }
    
    public static void findMaxMinPositions(int[][] matrix) {
        int max = matrix[0][0];
        int min = matrix[0][0];
        int maxRow = 0, maxCol = 0;
        int minRow = 0, minCol = 0;
        
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] > max) {
                    max = matrix[i][j];
                    maxRow = i;
                    maxCol = j;
                }
                if (matrix[i][j] < min) {
                    min = matrix[i][j];
                    minRow = i;
                    minCol = j;
                }
            }
        }
        
        System.out.printf("最大值：%d，位置：第 %d 行第 %d 列%n", max, maxRow + 1, maxCol + 1);
        System.out.printf("最小值：%d，位置：第 %d 行第 %d 列%n", min, minRow + 1, minCol + 1);
    }
    
    public static void printMatrix(int[][] matrix, String title) {
        System.out.println(title + "：");
        for (int i = 0; i < matrix.length; i++) {
            System.out.print("[ ");
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.printf("%4d ", matrix[i][j]);
            }
            System.out.println("]");
        }
        System.out.println();
    }
    
    public static void printMatrixInfo(int[][] matrix, String name) {
        System.out.printf("%s 維度：%d × %d%n", name, matrix.length, matrix[0].length);
    }
    
    public static void main(String[] args) {
        System.out.println("════════════════════════════════════════");
        System.out.println("            矩陣計算器測試");
        System.out.println("════════════════════════════════════════");
        
        int[][] matrix1 = {
            {1, 2, 3},
            {4, 5, 6}
        };
        
        int[][] matrix2 = {
            {7, 8, 9},
            {10, 11, 12}
        };
        
        int[][] matrix3 = {
            {1, 2},
            {3, 4},
            {5, 6}
        };
        
        printMatrix(matrix1, "矩陣 A");
        printMatrixInfo(matrix1, "矩陣 A");
        
        printMatrix(matrix2, "矩陣 B");
        printMatrixInfo(matrix2, "矩陣 B");
        
        printMatrix(matrix3, "矩陣 C");
        printMatrixInfo(matrix3, "矩陣 C");
        
        System.out.println("----------------------------------------");
        System.out.println("1. 矩陣加法測試（A + B）：");
        System.out.println("----------------------------------------");
        
        int[][] addResult = addMatrix(matrix1, matrix2);
        if (addResult != null) {
            printMatrix(addResult, "A + B 結果");
        }
        
        System.out.println("----------------------------------------");
        System.out.println("2. 矩陣乘法測試（A × C）：");
        System.out.println("----------------------------------------");
        
        int[][] multiplyResult = multiplyMatrix(matrix1, matrix3);
        if (multiplyResult != null) {
            printMatrix(multiplyResult, "A × C 結果");
            printMatrixInfo(multiplyResult, "結果矩陣");
        }
        
        System.out.println("----------------------------------------");
        System.out.println("3. 矩陣轉置測試：");
        System.out.println("----------------------------------------");
        
        int[][] transposeA = transposeMatrix(matrix1);
        printMatrix(transposeA, "A 的轉置矩陣");
        printMatrixInfo(transposeA, "A 轉置");
        
        int[][] transposeC = transposeMatrix(matrix3);
        printMatrix(transposeC, "C 的轉置矩陣");
        printMatrixInfo(transposeC, "C 轉置");
        
        System.out.println("----------------------------------------");
        System.out.println("4. 最大值與最小值測試：");
        System.out.println("----------------------------------------");
        
        System.out.println("矩陣 A 的最大值與最小值：");
        System.out.println("最大值：" + findMaxValue(matrix1));
        System.out.println("最小值：" + findMinValue(matrix1));
        findMaxMinPositions(matrix1);
        
        System.out.println("矩陣 C 的最大值與最小值：");
        System.out.println("最大值：" + findMaxValue(matrix3));
        System.out.println("最小值：" + findMinValue(matrix3));
        findMaxMinPositions(matrix3);
        
        System.out.println("----------------------------------------");
        System.out.println("5. 錯誤處理測試：");
        System.out.println("----------------------------------------");
        
        System.out.println("嘗試將不同維度的矩陣 A 和 C 相加：");
        addMatrix(matrix1, matrix3);
        
        System.out.println("嘗試將無法相乘的矩陣 A 和 B 相乘：");
        multiplyMatrix(matrix1, matrix2);
        
        System.out.println("----------------------------------------");
        System.out.println("6. 方陣測試：");
        System.out.println("----------------------------------------");
        
        int[][] squareMatrix = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        
        printMatrix(squareMatrix, "方陣 D");
        
        int[][] squareTranspose = transposeMatrix(squareMatrix);
        printMatrix(squareTranspose, "方陣 D 的轉置");
        
        System.out.println("方陣 D 的統計：");
        System.out.println("最大值：" + findMaxValue(squareMatrix));
        System.out.println("最小值：" + findMinValue(squareMatrix));
        findMaxMinPositions(squareMatrix);
        
        System.out.println("════════════════════════════════════════");
        System.out.println("            測試完成");
        System.out.println("════════════════════════════════════════");
    }
}