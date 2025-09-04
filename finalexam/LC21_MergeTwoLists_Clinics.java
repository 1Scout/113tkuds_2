import java.util.*;

/**
 * LC21_MergeTwoLists_Clinics
 * 題意：合併兩條已排序的單向鏈結串列，輸出合併後的升序串列。
 * 方法：雙指針逐步合併，Dummy + Tail 方便處理。
 * 複雜度：O(n+m)
 */
public class LC21_MergeTwoLists_Clinics {

    // 定義鏈結節點
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int v) { val = v; }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();

        // 讀取第一條串列
        ListNode dummy1 = new ListNode(0);
        ListNode cur = dummy1;
        for (int i = 0; i < n; i++) {
            cur.next = new ListNode(sc.nextInt());
            cur = cur.next;
        }

        // 讀取第二條串列
        ListNode dummy2 = new ListNode(0);
        cur = dummy2;
        for (int i = 0; i < m; i++) {
            cur.next = new ListNode(sc.nextInt());
            cur = cur.next;
        }
        sc.close();

        ListNode merged = mergeTwoLists(dummy1.next, dummy2.next);

        // 輸出合併後序列
        StringBuilder sb = new StringBuilder();
        while (merged != null) {
            sb.append(merged.val);
            if (merged.next != null) sb.append(" ");
            merged = merged.next;
        }
        System.out.println(sb.toString());
    }

    // 合併兩條升序鏈結串列
    static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;

        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                tail.next = l1;
                l1 = l1.next;
            } else {
                tail.next = l2;
                l2 = l2.next;
            }
            tail = tail.next;
        }

        // 直接掛上剩餘部分
        if (l1 != null) tail.next = l1;
        if (l2 != null) tail.next = l2;

        return dummy.next;
    }
}
