import java.util.Scanner;
public class TicTacToeBoard {
    private char[][] board;
    private static final int SIZE = 3;
    private static final char EMPTY = ' ';
    private static final char PLAYER_X = 'X';
    private static final char PLAYER_O = 'O';
    private char currentPlayer;
    private boolean gameOver;
    private char winner;
    
    public TicTacToeBoard() {
        initializeBoard();
        currentPlayer = PLAYER_X;
        gameOver = false;
        winner = EMPTY;
    }
    
    public void initializeBoard() {
        board = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = EMPTY;
            }
        }
    }
    
    public boolean placePiece(int row, int col, char player) {
        if (gameOver) {
            System.out.println("遊戲已結束！");
            return false;
        }
        
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            System.out.println("位置超出棋盤範圍！請輸入 0-2 之間的數字。");
            return false;
        }
        
        if (board[row][col] != EMPTY) {
            System.out.println("該位置已被佔用！");
            return false;
        }
        
        if (player != PLAYER_X && player != PLAYER_O) {
            System.out.println("無效的玩家！只能是 X 或 O。");
            return false;
        }
        
        board[row][col] = player;
        
        checkGameStatus();
        
        return true;
    }
    
    public boolean placePiece(int row, int col) {
        if (placePiece(row, col, currentPlayer)) {
            switchPlayer();
            return true;
        }
        return false;
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == PLAYER_X) ? PLAYER_O : PLAYER_X;
    }
    
    public char checkWinner() {
        for (int row = 0; row < SIZE; row++) {
            if (board[row][0] != EMPTY && 
                board[row][0] == board[row][1] && 
                board[row][1] == board[row][2]) {
                return board[row][0];
            }
        }
        
        for (int col = 0; col < SIZE; col++) {
            if (board[0][col] != EMPTY && 
                board[0][col] == board[1][col] && 
                board[1][col] == board[2][col]) {
                return board[0][col];
            }
        }
        
        if (board[0][0] != EMPTY && 
            board[0][0] == board[1][1] && 
            board[1][1] == board[2][2]) {
            return board[0][0];
        }
        
        if (board[0][2] != EMPTY && 
            board[0][2] == board[1][1] && 
            board[1][1] == board[2][0]) {
            return board[0][2];
        }
        
        return EMPTY;
    }
    
    public boolean isBoardFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public void checkGameStatus() {
        winner = checkWinner();
        
        if (winner != EMPTY) {
            gameOver = true;
            System.out.printf("遊戲結束！玩家 %c 獲勝！%n", winner);
        } else if (isBoardFull()) {
            gameOver = true;
            System.out.println("遊戲結束！平手！");
        }
    }
    
    public boolean isGameOver() {
        return gameOver;
    }
    
    public char getWinner() {
        return winner;
    }
    
    public char getCurrentPlayer() {
        return currentPlayer;
    }
    
    public char getPiece(int row, int col) {
        if (row >= 0 && row < SIZE && col >= 0 && col < SIZE) {
            return board[row][col];
        }
        return EMPTY;
    }
    
    public void resetGame() {
        initializeBoard();
        currentPlayer = PLAYER_X;
        gameOver = false;
        winner = EMPTY;
        System.out.println("遊戲已重置！");
    }
    
    public void displayBoard() {
        System.out.println("\n當前棋盤狀態：");
        System.out.println("  0   1   2");
        
        for (int i = 0; i < SIZE; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j]);
                if (j < SIZE - 1) {
                    System.out.print(" | ");
                }
            }
            System.out.println();
            
            if (i < SIZE - 1) {
                System.out.println("  ---------");
            }
        }
        System.out.println();
    }
    
    public void displayGameInfo() {
        System.out.println("遊戲狀態資訊：");
        System.out.println("當前玩家：" + currentPlayer);
        System.out.println("遊戲是否結束：" + (gameOver ? "是" : "否"));
        
        if (gameOver) {
            if (winner != EMPTY) {
                System.out.println("獲勝者：" + winner);
            } else {
                System.out.println("結果：平手");
            }
        }
        
        int xCount = 0, oCount = 0, emptyCount = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == PLAYER_X) xCount++;
                else if (board[i][j] == PLAYER_O) oCount++;
                else emptyCount++;
            }
        }
        
        System.out.printf("棋子統計：X = %d, O = %d, 空位 = %d%n", xCount, oCount, emptyCount);
    }
    
    public void showAvailablePositions() {
        System.out.println("可用位置：");
        boolean hasAvailable = false;
        
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == EMPTY) {
                    System.out.printf("(%d, %d) ", i, j);
                    hasAvailable = true;
                }
            }
        }
        
        if (!hasAvailable) {
            System.out.print("無可用位置");
        }
        System.out.println();
    }
    
    public void playGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("歡迎來到井字遊戲！");
        System.out.println("輸入行和列的座標來放置棋子（0-2）");
        System.out.println("輸入 -1 -1 來重置遊戲");
        System.out.println("輸入 -2 -2 來退出遊戲");
        
        while (true) {
            displayBoard();
            
            if (!gameOver) {
                displayGameInfo();
                showAvailablePositions();
                
                System.out.printf("玩家 %c 的回合，請輸入位置 (行 列): ", currentPlayer);
                
                int row = scanner.nextInt();
                int col = scanner.nextInt();
                
                if (row == -1 && col == -1) {
                    resetGame();
                    continue;
                } else if (row == -2 && col == -2) {
                    System.out.println("謝謝遊玩！");
                    break;
                }
                
                placePiece(row, col);
                
            } else {
                displayGameInfo();
                System.out.println("輸入 -1 -1 重新開始，或 -2 -2 退出遊戲：");
                
                int choice1 = scanner.nextInt();
                int choice2 = scanner.nextInt();
                
                if (choice1 == -1 && choice2 == -1) {
                    resetGame();
                } else if (choice1 == -2 && choice2 == -2) {
                    System.out.println("謝謝遊玩！");
                    break;
                }
            }
        }
        
        scanner.close();
    }
    
    public static void main(String[] args) {
        System.out.println("════════════════════════════════════════");
        System.out.println("            井字遊戲測試");
        System.out.println("════════════════════════════════════════");
        
        TicTacToeBoard game = new TicTacToeBoard();
        
        System.out.println("1. 自動測試模式：");
        System.out.println("----------------------------------------");
        
        game.displayBoard();
        
        System.out.println("測試放置棋子：");
        game.placePiece(0, 0);
        game.placePiece(0, 1); 
        game.placePiece(1, 1);
        game.placePiece(0, 2);
        game.placePiece(2, 2);
        
        game.displayBoard();
        game.displayGameInfo();
        
        System.out.println("\n----------------------------------------");
        System.out.println("2. 錯誤處理測試：");
        System.out.println("----------------------------------------");
        
        game.resetGame();
        game.placePiece(0, 0);
        game.placePiece(0, 0);
        game.placePiece(5, 5);
        game.placePiece(-1, 0);
        
        System.out.println("\n----------------------------------------");
        System.out.println("3. 平手測試：");
        System.out.println("----------------------------------------");
        
        game.resetGame();
        game.placePiece(0, 0, PLAYER_X);
        game.placePiece(0, 1, PLAYER_O);
        game.placePiece(0, 2, PLAYER_X);
        game.placePiece(1, 0, PLAYER_O);
        game.placePiece(1, 1, PLAYER_O);
        game.placePiece(1, 2, PLAYER_X);
        game.placePiece(2, 0, PLAYER_X);
        game.placePiece(2, 1, PLAYER_X);
        game.placePiece(2, 2, PLAYER_O);
        
        game.displayBoard();
        game.displayGameInfo();
        
        System.out.println("\n----------------------------------------");
        System.out.println("4. 各種獲勝條件測試：");
        System.out.println("----------------------------------------");
        
        System.out.println("測試第一行獲勝：");
        game.resetGame();
        game.placePiece(0, 0, PLAYER_X);
        game.placePiece(0, 1, PLAYER_X);
        game.placePiece(0, 2, PLAYER_X);
        game.displayBoard();
        
        System.out.println("測試第一列獲勝：");
        game.resetGame();
        game.placePiece(0, 0, PLAYER_O);
        game.placePiece(1, 0, PLAYER_O);
        game.placePiece(2, 0, PLAYER_O);
        game.displayBoard();
        
        System.out.println("測試反對角線獲勝：");
        game.resetGame();
        game.placePiece(0, 2, PLAYER_X);
        game.placePiece(1, 1, PLAYER_X);
        game.placePiece(2, 0, PLAYER_X);
        game.displayBoard();
        
        System.out.println("\n════════════════════════════════════════");
        System.out.println("自動測試完成！");
        System.out.println("════════════════════════════════════════");
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("\n是否要開始互動式遊戲？(y/n): ");
        String choice = scanner.nextLine().toLowerCase();
        
        if (choice.equals("y") || choice.equals("yes")) {
            game.resetGame();
            game.playGame();
        } else {
            System.out.println("謝謝使用！");
        }
        
        scanner.close();
    }
}