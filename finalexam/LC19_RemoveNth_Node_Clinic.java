import java.util.*;

/**
 * LC19_RemoveNth_Node_Clinic
 * 題目：刪除單向鏈結串列的倒數第 k 個節點
 * 思路：雙指標 (fast, slow)，一次遍歷完成。
 */
public class LC19_RemoveNth_Node_Clinic {

    // 定義鏈結節點
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();   // 節點數量
        ListNode dummy = new ListNode(0); // 假頭節點，方便處理刪除頭節點的情況
        ListNode cur = dummy;

        // 讀入鏈結串列
        for (int i = 0; i < n; i++) {
            cur.next = new ListNode(sc.nextInt());
            cur = cur.next;
        }
        int k = sc.nextInt(); // 倒數第 k 個要刪除
        sc.close();

        // 刪除倒數第 k 個
        ListNode newHead = removeNthFromEnd(dummy.next, k);

        // 輸出刪除後序列
        cur = newHead;
        boolean first = true;
        while (cur != null) {
            if (!first) System.out.print(" ");
            System.out.print(cur.val);
            first = false;
            cur = cur.next;
        }
    }

    // 刪除倒數第 k 個節點
    static ListNode removeNthFromEnd(ListNode head, int k) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode fast = dummy;
        ListNode slow = dummy;

        // fast 先走 k+1 步，使 slow 停在待刪前一節點
        for (int i = 0; i <= k; i++) {
            fast = fast.next;
        }

        // fast 和 slow 同步前進
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        // 刪除 slow 後的節點
        slow.next = slow.next.next;

        return dummy.next;
    }
}
