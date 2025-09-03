class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // pre 與 cur 分別指向鏈結串列的頭節點
        ListNode pre = head, cur = head;

        // 先讓 cur 往前移動 n 步
        for (int i = 0; i < n; ++i) {
            cur = cur.next;
        }

        // 如果 cur 為 null，表示要刪除的是「頭節點」
        if (cur == null) return head.next;

        // 同時移動 pre 和 cur，直到 cur 到達最後一個節點
        // 這樣 pre 剛好會停在要刪除節點的前一個位置
        while (cur.next != null) {
            cur = cur.next;
            pre = pre.next;
        }

        // 跳過要刪除的節點
        pre.next = pre.next.next;

        // 回傳新的頭節點
        return head;
    }
}
