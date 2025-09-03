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
     * 使用虛擬頭節點和指針操作來交換相鄰節點
     */
    public ListNode swapPairs(ListNode head) {
        // 建立虛擬頭節點，簡化邊界處理
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        // prev指向每對節點的前一個節點
        ListNode prev = dummy;
        
        // 當存在至少兩個節點時才進行交換
        while (prev.next != null && prev.next.next != null) {
            // 定義要交換的兩個節點
            ListNode first = prev.next;      // 第一個節點
            ListNode second = prev.next.next; // 第二個節點
            
            // 執行交換操作
            // 原來的連接：prev -> first -> second -> next
            // 交換後連接：prev -> second -> first -> next
            prev.next = second;        // prev指向second
            first.next = second.next;  // first指向原來second的下一個節點
            second.next = first;       // second指向first
            
            // 移動prev指針到下一對節點的前一個位置（現在是first）
            prev = first;
        }
        
        // 返回新的頭節點（跳過虛擬頭節點）
        return dummy.next;
    }
}
/*
解題思路：
1. 使用虛擬頭節點簡化邊界處理
2. 維護一個prev指針，指向每對節點的前一個節點
3. 對於每對節點(first, second)，執行交換操作：
   - prev -> second
   - first -> second.next
   - second -> first
4. 移動prev指針到下一對的前一個位置
核心技巧：
1. 虛擬頭節點：統一處理邊界情況
2. 指針操作：正確管理節點間的連接關係
3. 交換順序：先斷開舊連接，再建立新連接

時間複雜度：O(n) - 需要遍歷整個鏈表
空間複雜度：
- 迭代法：O(1) - 只使用常數額外空間
- 遞歸法：O(n) - 遞歸調用棧的深度

交換過程示例：
原鏈表：1 -> 2 -> 3 -> 4 -> 5
目標：   2 -> 1 -> 4 -> 3 -> 5

步驟分解（以迭代法為例）：
1. dummy -> 1 -> 2 -> 3 -> 4 -> 5
   prev=dummy, first=1, second=2

2. 交換1和2：
   dummy -> 2 -> 1 -> 3 -> 4 -> 5
   prev=1

3. prev=1, first=3, second=4
   交換3和4：
   dummy -> 2 -> 1 -> 4 -> 3 -> 5
   prev=3

4. prev=3, first=5, second=null
   不滿足交換條件，結束

最終結果：2 -> 1 -> 4 -> 3 -> 5

注意事項：
1. 正確處理鏈表長度為奇數的情況
2. 注意指針操作的順序，避免丟失節點
3. 遞歸解法要注意棧溢出風險
4. 虛擬頭節點的使用讓程式碼更簡潔

測試案例：
- 空鏈表：[]
- 單個節點：[1]
- 兩個節點：[1,2] -> [2,1]
- 奇數個節點：[1,2,3] -> [2,1,3]
- 偶數個節點：[1,2,3,4] -> [2,1,4,3]
*/