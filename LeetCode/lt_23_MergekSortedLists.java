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
     * 方法一：分治法（推薦）
     * 使用分治思想，兩兩合併鏈表，直到只剩一個鏈表
     */
    public ListNode mergeKLists(ListNode[] lists) {
        // 邊界情況：空陣列或null
        if (lists == null || lists.length == 0) {
            return null;
        }
        
        // 使用分治法合併
        return mergeHelper(lists, 0, lists.length - 1);
    }
    
    /**
     * 分治合併輔助函數
     * @param lists 鏈表陣列
     * @param left 左邊界
     * @param right 右邊界
     * @return 合併後的鏈表頭節點
     */
    private ListNode mergeHelper(ListNode[] lists, int left, int right) {
        // 遞歸終止條件：只有一個鏈表
        if (left == right) {
            return lists[left];
        }
        
        // 只有兩個鏈表時，直接合併
        if (left + 1 == right) {
            return mergeTwoLists(lists[left], lists[right]);
        }
        
        // 分治：找到中點，分別合併左半部分和右半部分
        int mid = left + (right - left) / 2;
        ListNode leftMerged = mergeHelper(lists, left, mid);
        ListNode rightMerged = mergeHelper(lists, mid + 1, right);
        
        // 合併左右兩部分的結果
        return mergeTwoLists(leftMerged, rightMerged);
    }
    
    /**
     * 合併兩個有序鏈表（來自LeetCode 21題）
     */
    private ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(-1);
        ListNode current = dummy;
        
        // 比較兩個鏈表的節點值，選擇較小的連接到結果鏈表
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                current.next = list1;
                list1 = list1.next;
            } else {
                current.next = list2;
                list2 = list2.next;
            }
            current = current.next;
        }
        
        // 連接剩餘部分
        current.next = (list1 != null) ? list1 : list2;
        
        return dummy.next;
    }
}
/*
解題思路：

1. 將k個鏈表分成兩半，遞歸合併左半部分和右半部分
2. 最終合併兩個已排序的鏈表
3. 時間複雜度：O(N log k)，其中N是所有節點總數，k是鏈表數量
4. 空間複雜度：O(log k) - 遞歸調用棧

關鍵技巧：
1. 複用合併兩個有序鏈表的演算法
2. 分治思想：將大問題分解為小問題
3. 優先隊列維護當前最小值
4. 虛擬頭節點簡化邊界處理

時間複雜度分析：
- 分治法：每層合併O(N)個節點，共log k層，總計O(N log k)
- 優先隊列：每個節點進出堆一次，每次操作O(log k)，總計O(N log k)
- 順序合併：第i次合併需要O(i*avg_length)時間，總計O(k²*avg_length) = O(N*k)

空間複雜度分析：
- 分治法：遞歸深度為O(log k)
- 優先隊列：堆大小為O(k)
- 順序合併：O(1)額外空間

適用場景：
- 分治法：通用性強，效率高
- 優先隊列：適合動態添加鏈表的場景
- 順序合併：簡單場景，鏈表數量少

測試範例：
輸入：lists = [[1,4,5],[1,3,4],[2,6]]
輸出：[1,1,2,3,4,4,5,6]

分治過程示例（3個鏈表）：
1. 分治：[1,4,5] 和 [1,3,4] 合併 -> [1,1,3,4,4,5]
2. 分治：[1,1,3,4,4,5] 和 [2,6] 合併 -> [1,1,2,3,4,4,5,6]
*/