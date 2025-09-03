class Solution {
    /**
     * 方法一：迭代法（推薦）
     * 使用虛擬頭節點和雙指針來合併兩個有序鏈表
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        // 建立虛擬頭節點，簡化邊界處理
        ListNode dummy = new ListNode(-1);
        ListNode current = dummy;
        
        // 當兩個鏈表都不為空時，比較節點值並選擇較小的
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                // list1的值較小或相等，將其連接到結果鏈表
                current.next = list1;
                list1 = list1.next;  // list1指針向前移動
            } else {
                // list2的值較小，將其連接到結果鏈表
                current.next = list2;
                list2 = list2.next;  // list2指針向前移動
            }
            current = current.next;  // 結果鏈表指針向前移動
        }
        
        // 將剩餘的節點直接連接到結果鏈表末尾
        // 因為兩個鏈表都是有序的，剩餘部分一定都比已處理的部分大
        if (list1 != null) {
            current.next = list1;
        } else {
            current.next = list2;
        }
        
        // 返回真正的頭節點（跳過虛擬頭節點）
        return dummy.next;
    }
}
/*
解題思路：
1. 使用虛擬頭節點簡化邊界處理
2. 用雙指針分別遍歷兩個鏈表
3. 每次選擇較小的節點連接到結果鏈表
4. 處理完一個鏈表後，直接連接另一個鏈表的剩餘部分

核心技巧：
1. 虛擬頭節點：統一處理，避免特殊情況判斷
2. 雙指針：同時遍歷兩個鏈表
3. 有序性利用：剩餘部分可以直接連接

時間複雜度：O(m + n) - m和n分別是兩個鏈表的長度
空間複雜度：
- 迭代法：O(1) - 只使用常數額外空間
- 遞歸法：O(m + n) - 遞歸調用棧的深度

邊界情況處理：
1. 其中一個或兩個鏈表為空
2. 兩個鏈表長度差異很大
3. 所有節點值相等的情況

測試範例：
輸入：list1 = [1,2,4], list2 = [1,3,4]
輸出：[1,1,2,3,4,4]

演算法流程示例：
list1: 1->2->4
list2: 1->3->4
步驟：
1. 比較1和1，選1(list1)，結果：1
2. 比較2和1，選1(list2)，結果：1->1
3. 比較2和3，選2(list1)，結果：1->1->2
4. 比較4和3，選3(list2)，結果：1->1->2->3
5. 比較4和4，選4(list1)，結果：1->1->2->3->4
6. list1空，直接連接list2剩餘：1->1->2->3->4->4
*/