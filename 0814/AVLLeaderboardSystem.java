/**
 * AVLLeaderboardSystem.java
 * 基於 AVL 樹的遊戲排行榜系統
 * 支援玩家分數管理、排名查詢、前K名查詢等功能
 */

import java.util.*;

// 玩家信息類
class Player {
    String username;
    int score;
    long timestamp; // 用於處理相同分數的排序
    
    public Player(String username, int score) {
        this.username = username;
        this.score = score;
        this.timestamp = System.currentTimeMillis();
    }
    
    @Override
    public String toString() {
        return username + "(" + score + ")";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Player player = (Player) obj;
        return username.equals(player.username);
    }
    
    @Override
    public int hashCode() {
        return username.hashCode();
    }
}

// 擴展的 AVL 節點類
class LeaderboardNode {
    Player player;
    LeaderboardNode left, right;
    int height;
    int size; // 子樹大小（包含當前節點）
    
    public LeaderboardNode(Player player) {
        this.player = player;
        this.height = 1;
        this.size = 1;
        this.left = null;
        this.right = null;
    }
}

public class AVLLeaderboardSystem {
    private LeaderboardNode root;
    private Map<String, Player> playerMap; // 快速查找玩家
    
    public AVLLeaderboardSystem() {
        this.root = null;
        this.playerMap = new HashMap<>();
    }
    
    // 獲取節點高度
    private int getHeight(LeaderboardNode node) {
        return (node == null) ? 0 : node.height;
    }
    
    // 獲取子樹大小
    private int getSize(LeaderboardNode node) {
        return (node == null) ? 0 : node.size;
    }
    
    // 更新節點的高度和大小
    private void updateNode(LeaderboardNode node) {
        if (node != null) {
            int leftHeight = getHeight(node.left);
            int rightHeight = getHeight(node.right);
            node.height = Math.max(leftHeight, rightHeight) + 1;
            
            int leftSize = getSize(node.left);
            int rightSize = getSize(node.right);
            node.size = leftSize + rightSize + 1;
        }
    }
    
    // 計算平衡因子
    private int getBalanceFactor(LeaderboardNode node) {
        return (node == null) ? 0 : getHeight(node.left) - getHeight(node.right);
    }
    
    // 比較兩個玩家（按分數降序，分數相同時按時間戳升序）
    private int comparePlayer(Player p1, Player p2) {
        if (p1.score != p2.score) {
            return Integer.compare(p2.score, p1.score); // 分數高的排前面
        }
        return Long.compare(p1.timestamp, p2.timestamp); // 時間早的排前面
    }
    
    // 右旋轉
    private LeaderboardNode rotateRight(LeaderboardNode y) {
        LeaderboardNode x = y.left;
        LeaderboardNode T2 = x.right;
        
        x.right = y;
        y.left = T2;
        
        updateNode(y);
        updateNode(x);
        
        return x;
    }
    
    // 左旋轉
    private LeaderboardNode rotateLeft(LeaderboardNode x) {
        LeaderboardNode y = x.right;
        LeaderboardNode T2 = y.left;
        
        y.left = x;
        x.right = T2;
        
        updateNode(x);
        updateNode(y);
        
        return y;
    }
    
    // ========================================
    // 核心功能實現
    // ========================================
    
    /**
     * 1. 添加玩家分數
     */
    public boolean addPlayer(String username, int score) {
        System.out.println("添加玩家: " + username + " (分數: " + score + ")");
        
        if (playerMap.containsKey(username)) {
            System.out.println("玩家已存在，請使用更新功能");
            return false;
        }
        
        Player newPlayer = new Player(username, score);
        root = insertNode(root, newPlayer);
        playerMap.put(username, newPlayer);
        
        System.out.println("添加成功！當前排名: " + getRank(username));
        return true;
    }
    
    /**
     * 2. 更新玩家分數
     */
    public boolean updatePlayer(String username, int newScore) {
        System.out.println("更新玩家: " + username + " (新分數: " + newScore + ")");
        
        Player existingPlayer = playerMap.get(username);
        if (existingPlayer == null) {
            System.out.println("玩家不存在");
            return false;
        }
        
        int oldScore = existingPlayer.score;
        
        // 先刪除舊記錄
        root = deleteNode(root, existingPlayer);
        
        // 更新分數並重新插入
        existingPlayer.score = newScore;
        existingPlayer.timestamp = System.currentTimeMillis(); // 更新時間戳
        root = insertNode(root, existingPlayer);
        
        System.out.println("更新成功！分數從 " + oldScore + " 更新為 " + newScore);
        System.out.println("當前排名: " + getRank(username));
        return true;
    }
    
    /**
     * 3. 查詢玩家排名（從1開始）
     */
    public int getRank(String username) {
        Player player = playerMap.get(username);
        if (player == null) {
            System.out.println("玩家 " + username + " 不存在");
            return -1;
        }
        
        int rank = getRankHelper(root, player);
        System.out.println("玩家 " + username + " 的排名: " + rank);
        return rank;
    }
    
    private int getRankHelper(LeaderboardNode node, Player targetPlayer) {
        if (node == null) {
            return 0;
        }
        
        int cmp = comparePlayer(targetPlayer, node.player);
        
        if (cmp == 0) {
            // 找到目標玩家，排名 = 左子樹大小 + 1
            return getSize(node.left) + 1;
        } else if (cmp < 0) {
            // 目標玩家排名更靠前，在左子樹中
            return getRankHelper(node.left, targetPlayer);
        } else {
            // 目標玩家排名更靠後，在右子樹中
            // 排名 = 左子樹大小 + 1 + 右子樹中的排名
            return getSize(node.left) + 1 + getRankHelper(node.right, targetPlayer);
        }
    }
    
    /**
     * 4. 查詢前 K 名玩家
     */
    public List<Player> getTopK(int k) {
        System.out.println("查詢前 " + k + " 名玩家");
        
        if (k <= 0) {
            return new ArrayList<>();
        }
        
        List<Player> result = new ArrayList<>();
        getTopKHelper(root, k, result);
        
        System.out.println("前 " + k + " 名玩家:");
        for (int i = 0; i < result.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + result.get(i));
        }
        
        return result;
    }
    
    private void getTopKHelper(LeaderboardNode node, int k, List<Player> result) {
        if (node == null || result.size() >= k) {
            return;
        }
        
        // 中序遍歷（按排名順序）
        getTopKHelper(node.left, k, result);
        
        if (result.size() < k) {
            result.add(node.player);
        }
        
        getTopKHelper(node.right, k, result);
    }
    
    /**
     * 根據排名選擇玩家（Select操作）
     */
    public Player selectByRank(int rank) {
        System.out.println("查詢第 " + rank + " 名玩家");
        
        if (rank <= 0 || rank > getSize(root)) {
            System.out.println("無效的排名: " + rank);
            return null;
        }
        
        Player result = selectByRankHelper(root, rank);
        if (result != null) {
            System.out.println("第 " + rank + " 名玩家: " + result);
        }
        
        return result;
    }
    
    private Player selectByRankHelper(LeaderboardNode node, int rank) {
        if (node == null) {
            return null;
        }
        
        int leftSize = getSize(node.left);
        int currentRank = leftSize + 1;
        
        if (rank == currentRank) {
            return node.player;
        } else if (rank < currentRank) {
            return selectByRankHelper(node.left, rank);
        } else {
            return selectByRankHelper(node.right, rank - currentRank);
        }
    }
    
    /**
     * 查詢分數範圍內的玩家
     */
    public List<Player> getPlayersByScoreRange(int minScore, int maxScore) {
        System.out.println("查詢分數範圍 [" + minScore + ", " + maxScore + "] 的玩家");
        
        List<Player> result = new ArrayList<>();
        getPlayersByScoreRangeHelper(root, minScore, maxScore, result);
        
        System.out.println("找到 " + result.size() + " 名玩家在此分數範圍內");
        result.forEach(player -> System.out.println("  " + player));
        
        return result;
    }
    
    private void getPlayersByScoreRangeHelper(LeaderboardNode node, int minScore, int maxScore, List<Player> result) {
        if (node == null) {
            return;
        }
        
        // 利用 BST 性質進行剪枝
        if (node.player.score < minScore) {
            // 當前節點分數太低，只搜索右子樹
            getPlayersByScoreRangeHelper(node.right, minScore, maxScore, result);
        } else if (node.player.score > maxScore) {
            // 當前節點分數太高，只搜索左子樹
            getPlayersByScoreRangeHelper(node.left, minScore, maxScore, result);
        } else {
            // 當前節點在範圍內，搜索兩個子樹
            getPlayersByScoreRangeHelper(node.left, minScore, maxScore, result);
            result.add(node.player);
            getPlayersByScoreRangeHelper(node.right, minScore, maxScore, result);
        }
    }
    
    // ========================================
    // AVL 樹基本操作
    // ========================================
    
    private LeaderboardNode insertNode(LeaderboardNode node, Player player) {
        // 1. 標準 BST 插入
        if (node == null) {
            return new LeaderboardNode(player);
        }
        
        int cmp = comparePlayer(player, node.player);
        if (cmp < 0) {
            node.left = insertNode(node.left, player);
        } else if (cmp > 0) {
            node.right = insertNode(node.right, player);
        } else {
            // 相同的玩家，不應該發生
            return node;
        }
        
        // 2. 更新節點信息
        updateNode(node);
        
        // 3. 檢查平衡並旋轉
        int balance = getBalanceFactor(node);
        
        // LL
        if (balance > 1 && comparePlayer(player, node.left.player) < 0) {
            return rotateRight(node);
        }
        
        // RR
        if (balance < -1 && comparePlayer(player, node.right.player) > 0) {
            return rotateLeft(node);
        }
        
        // LR
        if (balance > 1 && comparePlayer(player, node.left.player) > 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        
        // RL
        if (balance < -1 && comparePlayer(player, node.right.player) < 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        
        return node;
    }
    
    private LeaderboardNode deleteNode(LeaderboardNode node, Player player) {
        if (node == null) {
            return null;
        }
        
        int cmp = comparePlayer(player, node.player);
        if (cmp < 0) {
            node.left = deleteNode(node.left, player);
        } else if (cmp > 0) {
            node.right = deleteNode(node.right, player);
        } else {
            // 找到要刪除的節點
            if (node.left == null || node.right == null) {
                LeaderboardNode temp = (node.left != null) ? node.left : node.right;
                
                if (temp == null) {
                    temp = node;
                    node = null;
                } else {
                    node = temp;
                }
            } else {
                // 兩個子節點的情況，找中序後繼
                LeaderboardNode temp = findMin(node.right);
                node.player = temp.player;
                node.right = deleteNode(node.right, temp.player);
            }
        }
        
        if (node == null) {
            return node;
        }
        
        // 更新節點信息
        updateNode(node);
        
        // 檢查平衡
        int balance = getBalanceFactor(node);
        
        // Left Heavy
        if (balance > 1) {
            if (getBalanceFactor(node.left) >= 0) {
                return rotateRight(node);
            } else {
                node.left = rotateLeft(node.left);
                return rotateRight(node);
            }
        }
        
        // Right Heavy
        if (balance < -1) {
            if (getBalanceFactor(node.right) <= 0) {
                return rotateLeft(node);
            } else {
                node.right = rotateRight(node.right);
                return rotateLeft(node);
            }
        }
        
        return node;
    }
    
    private LeaderboardNode findMin(LeaderboardNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
    
    // ========================================
    // 輔助和顯示方法
    // ========================================
    
    public int getTotalPlayers() {
        return getSize(root);
    }
    
    public boolean isEmpty() {
        return root == null;
    }
    
    // 顯示排行榜
    public void displayLeaderboard() {
        System.out.println("=== 排行榜 ===");
        if (isEmpty()) {
            System.out.println("排行榜為空");
            return;
        }
        
        System.out.println("總玩家數: " + getTotalPlayers());
        List<Player> allPlayers = getTopK(getTotalPlayers());
        
        System.out.println("完整排行榜:");
        for (int i = 0; i < allPlayers.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + allPlayers.get(i));
        }
        System.out.println();
    }
    
    // 顯示樹結構（調試用）
    public void displayTreeStructure() {
        System.out.println("=== 樹結構 ===");
        if (root == null) {
            System.out.println("空樹");
            return;
        }
        displayTreeHelper(root, "", true);
        System.out.println();
    }
    
    private void displayTreeHelper(LeaderboardNode node, String prefix, boolean isLast) {
        if (node != null) {
            System.out.println(prefix + (isLast ? "└── " : "├── ") + 
                             node.player + " (h:" + node.height + 
                             ", s:" + node.size + ", bf:" + getBalanceFactor(node) + ")");
            
            if (node.left != null || node.right != null) {
                if (node.right != null) {
                    displayTreeHelper(node.right, prefix + (isLast ? "    " : "│   "), node.left == null);
                }
                if (node.left != null) {
                    displayTreeHelper(node.left, prefix + (isLast ? "    " : "│   "), true);
                }
            }
        }
    }
    
    // 測試方法
    public static void main(String[] args) {
        AVLLeaderboardSystem leaderboard = new AVLLeaderboardSystem();
        
        System.out.println("========================================");
        System.out.println("       遊戲排行榜系統測試");
        System.out.println("========================================");
        
        // 測試1: 添加玩家
        System.out.println("\n【測試1: 添加玩家】");
        leaderboard.addPlayer("Alice", 1500);
        leaderboard.addPlayer("Bob", 1200);
        leaderboard.addPlayer("Charlie", 1800);
        leaderboard.addPlayer("David", 1300);
        leaderboard.addPlayer("Eve", 1600);
        leaderboard.addPlayer("Frank", 1400);
        leaderboard.addPlayer("Grace", 1700);
        
        leaderboard.displayLeaderboard();
        
        // 測試2: 查詢排名
        System.out.println("\n【測試2: 查詢排名】");
        leaderboard.getRank("Alice");
        leaderboard.getRank("Charlie");
        leaderboard.getRank("Bob");
        leaderboard.getRank("NonExistent");
        
        // 測試3: 前K名查詢
        System.out.println("\n【測試3: 前K名查詢】");
        leaderboard.getTopK(3);
        leaderboard.getTopK(5);
        
        // 測試4: 按排名選擇
        System.out.println("\n【測試4: 按排名選擇】");
        leaderboard.selectByRank(1);
        leaderboard.selectByRank(3);
        leaderboard.selectByRank(7);
        leaderboard.selectByRank(10); // 無效排名
        
        // 測試5: 更新分數
        System.out.println("\n【測試5: 更新分數】");
        System.out.println("更新前排行榜:");
        leaderboard.displayLeaderboard();
        
        leaderboard.updatePlayer("Bob", 1900); // Bob 從最後變成第一
        leaderboard.updatePlayer("Charlie", 1100); // Charlie 從第一變成最後
        
        System.out.println("更新後排行榜:");
        leaderboard.displayLeaderboard();
        
        // 測試6: 分數範圍查詢
        System.out.println("\n【測試6: 分數範圍查詢】");
        leaderboard.getPlayersByScoreRange(1400, 1600);
        leaderboard.getPlayersByScoreRange(1000, 1200);
        
        // 測試7: 處理相同分數
        System.out.println("\n【測試7: 處理相同分數】");
        leaderboard.addPlayer("Henry", 1500);
        leaderboard.addPlayer("Iris", 1500);
        
        System.out.println("添加相同分數玩家後:");
        leaderboard.displayLeaderboard();
        
        // 測試8: 大規模數據
        System.out.println("\n【測試8: 大規模數據測試】");
        AVLLeaderboardSystem bigLeaderboard = new AVLLeaderboardSystem();
        
        System.out.println("添加 1000 名玩家...");
        Random random = new Random(42);
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < 1000; i++) {
            String username = "Player" + i;
            int score = random.nextInt(5000); // 0-4999 分數範圍
            bigLeaderboard.addPlayer(username, score);
        }
        
        long endTime = System.currentTimeMillis();
        System.out.println("添加完成，耗時: " + (endTime - startTime) + " 毫秒");
        
        // 測試大規模查詢性能
        System.out.println("\n大規模排行榜性能測試:");
        System.out.println("總玩家數: " + bigLeaderboard.getTotalPlayers());
        
        startTime = System.currentTimeMillis();
        bigLeaderboard.getTopK(10);
        endTime = System.currentTimeMillis();
        System.out.println("查詢前10名耗時: " + (endTime - startTime) + " 毫秒");
        
        startTime = System.currentTimeMillis();
        bigLeaderboard.getRank("Player500");
        endTime = System.currentTimeMillis();
        System.out.println("查詢排名耗時: " + (endTime - startTime) + " 毫秒");
        
        startTime = System.currentTimeMillis();
        bigLeaderboard.selectByRank(100);
        endTime = System.currentTimeMillis();
        System.out.println("按排名選擇耗時: " + (endTime - startTime) + " 毫秒");
        
        // 測試9: 邊界情況
        System.out.println("\n【測試9: 邊界情況】");
        AVLLeaderboardSystem emptyBoard = new AVLLeaderboardSystem();
        
        emptyBoard.getRank("Nobody");
        emptyBoard.getTopK(5);
        emptyBoard.selectByRank(1);
        emptyBoard.updatePlayer("Nobody", 1000);
        
        System.out.println("\n========================================");
        System.out.println("           測試完成");
        System.out.println("========================================");
        
        // 總結
        System.out.println("\n【功能總結】");
        System.out.println("✅ 添加玩家: O(log n)");
        System.out.println("✅ 更新分數: O(log n)");
        System.out.println("✅ 查詢排名: O(log n)");
        System.out.println("✅ 前K名查詢: O(log n + k)");
        System.out.println("✅ 按排名選擇: O(log n)");
        System.out.println("✅ 分數範圍查詢: O(log n + k)");
        System.out.println("✅ 支援相同分數處理（時間戳排序）");
        System.out.println("✅ 大規模數據高效處理");
    }
}