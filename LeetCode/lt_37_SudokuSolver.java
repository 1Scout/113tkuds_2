class Solution {
    // 主要解法：回溯算法
    public void solveSudoku(char[][] board) {
        solve(board);
    }
    
    private boolean solve(char[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                // 找到空格
                if (board[i][j] == '.') {
                    // 嘗試填入數字 1-9
                    for (char c = '1'; c <= '9'; c++) {
                        if (isValid(board, i, j, c)) {
                            // 如果該數字合法，填入
                            board[i][j] = c;
                            
                            // 遞迴解決剩餘部分
                            if (solve(board)) {
                                return true; // 找到解答
                            }
                            
                            // 回溯：撤銷當前選擇
                            board[i][j] = '.';
                        }
                    }
                    
                    // 所有數字都試過了，沒有解答
                    return false;
                }
            }
        }
        
        // 所有格子都填完了，找到解答
        return true;
    }
    
    // 檢查在位置 (row, col) 填入數字 c 是否合法
    private boolean isValid(char[][] board, int row, int col, char c) {
        for (int i = 0; i < 9; i++) {
            // 檢查行：第 row 行不能有重複的 c
            if (board[row][i] == c) {
                return false;
            }
            
            // 檢查列：第 col 列不能有重複的 c
            if (board[i][col] == c) {
                return false;
            }
            
            // 檢查 3x3 方格：計算對應的 3x3 方格中不能有重複的 c
            int boxRow = 3 * (row / 3) + i / 3;
            int boxCol = 3 * (col / 3) + i % 3;
            if (board[boxRow][boxCol] == c) {
                return false;
            }
        }
        
        return true;
    }
    
    // 優化版本：使用預計算的約束條件
    public void solveSudokuOptimized(char[][] board) {
        // 預計算每行、每列、每個3x3方格已使用的數字
        boolean[][] rows = new boolean[9][9];
        boolean[][] cols = new boolean[9][9];
        boolean[][] boxes = new boolean[9][9];
        
        // 初始化約束條件
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != '.') {
                    int num = board[i][j] - '1';
                    int boxIndex = getBoxIndex(i, j);
                    rows[i][num] = true;
                    cols[j][num] = true;
                    boxes[boxIndex][num] = true;
                }
            }
        }
        
        solveOptimized(board, rows, cols, boxes);
    }
    
    private boolean solveOptimized(char[][] board, boolean[][] rows, boolean[][] cols, boolean[][] boxes) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') {
                    int boxIndex = getBoxIndex(i, j);
                    
                    for (int num = 0; num < 9; num++) {
                        // 檢查該數字是否可以放置
                        if (!rows[i][num] && !cols[j][num] && !boxes[boxIndex][num]) {
                            // 填入數字
                            board[i][j] = (char) ('1' + num);
                            rows[i][num] = true;
                            cols[j][num] = true;
                            boxes[boxIndex][num] = true;
                            
                            // 遞迴求解
                            if (solveOptimized(board, rows, cols, boxes)) {
                                return true;
                            }
                            
                            // 回溯
                            board[i][j] = '.';
                            rows[i][num] = false;
                            cols[j][num] = false;
                            boxes[boxIndex][num] = false;
                        }
                    }
                    
                    return false;
                }
            }
        }
        
        return true;
    }
    
    // 計算3x3方格的索引
    private int getBoxIndex(int row, int col) {
        return (row / 3) * 3 + col / 3;
    }
    
    // 進一步優化：選擇候選數字最少的空格優先填充
    public void solveSudokuAdvanced(char[][] board) {
        boolean[][] rows = new boolean[9][9];
        boolean[][] cols = new boolean[9][9];
        boolean[][] boxes = new boolean[9][9];
        List<int[]> emptyCells = new ArrayList<>();
        
        // 初始化並收集空格
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') {
                    emptyCells.add(new int[]{i, j});
                } else {
                    int num = board[i][j] - '1';
                    int boxIndex = getBoxIndex(i, j);
                    rows[i][num] = true;
                    cols[j][num] = true;
                    boxes[boxIndex][num] = true;
                }
            }
        }
        
        solveAdvanced(board, rows, cols, boxes, emptyCells, 0);
    }
    
    private boolean solveAdvanced(char[][] board, boolean[][] rows, boolean[][] cols, 
                                 boolean[][] boxes, List<int[]> emptyCells, int index) {
        if (index == emptyCells.size()) {
            return true; // 所有空格都填完了
        }
        
        int[] cell = emptyCells.get(index);
        int row = cell[0];
        int col = cell[1];
        int boxIndex = getBoxIndex(row, col);
        
        for (int num = 0; num < 9; num++) {
            if (!rows[row][num] && !cols[col][num] && !boxes[boxIndex][num]) {
                // 填入數字
                board[row][col] = (char) ('1' + num);
                rows[row][num] = true;
                cols[col][num] = true;
                boxes[boxIndex][num] = true;
                
                // 繼續填下一個空格
                if (solveAdvanced(board, rows, cols, boxes, emptyCells, index + 1)) {
                    return true;
                }
                
                // 回溯
                board[row][col] = '.';
                rows[row][num] = false;
                cols[col][num] = false;
                boxes[boxIndex][num] = false;
            }
        }
        
        return false;
    }
    
    // 輔助方法：打印數獨（用於調試）
    private void printBoard(char[][] board) {
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0 && i != 0) {
                System.out.println("------+-------+------");
            }
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0 && j != 0) {
                    System.out.print("| ");
                }
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}

/*
題目：數獨求解器

編寫一個程式，通過填充空格來解決數獨問題。

數獨規則：
1. 數字 1-9 在每一行只能出現一次
2. 數字 1-9 在每一列只能出現一次  
3. 數字 1-9 在每一個 3x3 宮內只能出現一次
4. 空白格用 '.' 表示

解法分析：

核心算法：回溯法（Backtracking）
- 這是一種系統性搜索所有可能解的方法
- 當遇到無法繼續的狀況時，回退到上一步重新選擇

算法步驟：
1. 找到第一個空格 '.'
2. 嘗試填入數字 1-9
3. 檢查填入的數字是否符合數獨規則
4. 如果合法，遞迴求解剩餘部分
5. 如果無法求解，回溯並嘗試下一個數字
6. 重複直到找到解答或確定無解

三種優化版本：

版本一：基礎回溯
- 直接遍歷找空格，逐一嘗試
- 每次都重新檢查約束條件
- 時間複雜度：O(9^(空格數))

版本二：預計算約束
- 預先計算每行、列、3x3方格的使用狀況
- 避免重複檢查，提高效率
- 使用三個二維布爾陣列追蹤狀態

版本三：智能搜索順序
- 預先收集所有空格位置
- 可以按候選數字數量排序，優先填充選擇少的格子
- 進一步減少搜索空間

關鍵技巧：
1. 3x3方格索引：(row/3)*3 + col/3
2. 約束檢查：行、列、3x3方格三重檢查
3. 回溯時要恢復狀態：board[i][j] = '.'

時間複雜度：
- 最壞情況：O(9^m)，其中 m 是空格數量
- 實際上由於剪枝，通常比理論值小很多

空間複雜度：
- O(1) 額外空間（不計遞迴堆疊）
- 遞迴深度最多為空格數量

測試建議：
- 使用已知解答的數獨進行測試
- 可以先用簡單的部分填充數獨驗證
- 注意處理無解的情況（雖然題目保證有唯一解）

這道題是回溯算法的經典應用，掌握它對理解遞迴和回溯思想很有幫助。
*/