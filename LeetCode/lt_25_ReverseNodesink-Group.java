/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */

class Solution {
    /**
     * 方法一：迭代法（推薦）
     * 分組反轉鏈表，每k個節點為一組進行反轉
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || k == 1) {
            return head;
        }
        
        // 建立虛擬頭節點，簡化邊界處理
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        // prev指向每組的前一個節點，start指向每組的第一個節點，end指向每組的最後一個節點
        ListNode prev = dummy;
        
        while (true) {
            // 檢查是否有足夠的k個節點需要反轉
            ListNode start = prev.next;
            ListNode end = getKthNode(start, k);
            
            if (end == null) {
                // 剩餘節點不足k個，不需要反轉，結束處理
                break;
            }
            
            // 保存下一組的第一個節點
            ListNode nextGroupStart = end.next;
            
            // 反轉當前組的k個節點
            end.next = null;  // 暫時斷開與後面的連接
            ListNode newStart = reverseList(start);
            
            // 重新連接反轉後的組
            prev.next = newStart;    // 前一組連接到反轉後的頭部
            start.next = nextGroupStart;  // 反轉後的尾部連接到下一組
            
            // 更新prev指針為下一組的前一個節點（現在是start）
            prev = start;
        }
        
        return dummy.next;
    }
    
    /**
     * 獲取從start開始的第k個節點
     * @param start 起始節點
     * @param k 步數
     * @return 第k個節點，如果不足k個則返回null
     */
    private ListNode getKthNode(ListNode start, int k) {
        ListNode current = start;
        for (int i = 1; i < k && current != null; i++) {
            current = current.next;
        }
        return current;
    }
    
    /**
     * 反轉鏈表（經典演算法）
     * @param head 要反轉的鏈表頭節點
     * @return 反轉後的頭節點
     */
    private ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode current = head;
        
        while (current != null) {
            ListNode next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        
        return prev;
    }
}
/*
解題思路：
1. 使用虛擬頭節點簡化邊界處理
2. 檢查是否有足夠的k個節點
3. 暫時斷開當前組與後面的連接
4. 反轉當前組的k個節點
5. 重新連接反轉後的組與前後部分
6. 重複直到處理完所有完整的k組
核心技巧：
1. 虛擬頭節點：簡化邊界處理
2. 分組處理：每k個節點為一組
3. 反轉鏈表：經典的三指針技巧
4. 重連操作：正確處理反轉前後的連接關係

時間複雜度：O(n) - 每個節點最多被訪問兩次
空間複雜度：
- 迭代法/遞歸法：O(1) / O(n/k) 遞歸深度
- 棧輔助法：O(k) - 棧的大小

關鍵步驟解析：
1. 檢查節點數量：確保有k個節點可以反轉
2. 斷開連接：暫時切斷當前組與後續的連接
3. 反轉當前組：使用標準的鏈表反轉演算法
4. 重新連接：將反轉後的組與前後部分正確連接

邊界情況：
1. 空鏈表或k=1：直接返回原鏈表
2. 鏈表長度小於k：不進行任何反轉
3. 最後一組不足k個節點：保持原順序

測試範例：
輸入：head = [1,2,3,4,5], k = 2
輸出：[2,1,4,3,5]

輸入：head = [1,2,3,4,5], k = 3  
輸出：[3,2,1,4,5]

演算法流程示例（k=3）：
原鏈表：1->2->3->4->5
第一組：1->2->3 反轉為 3->2->1
結果：  3->2->1->4->5
第二組：4->5 不足3個節點，保持原樣
最終：  3->2->1->4->5

注意事項：
1. 正確處理指針操作，避免丟失節點
2. 注意反轉後的重連順序
3. 遞歸解法要考慮棧溢出
4. 確保不足k個節點的組保持原順序
*/