import java.util.*;

public class LC25_ReverseKGroup_Shifts {
    // 定義鏈結串列節點
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int k = sc.nextInt();
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;

        // 讀取序列建立鏈結串列
        while (sc.hasNextInt()) {
            tail.next = new ListNode(sc.nextInt());
            tail = tail.next;
        }

        // 反轉
        ListNode head = reverseKGroup(dummy.next, k);

        // 輸出結果
        while (head != null) {
            System.out.print(head.val);
            if (head.next != null) System.out.print(" ");
            head = head.next;
        }
    }

    // 主函式：以 k 為單位反轉
    public static ListNode reverseKGroup(ListNode head, int k) {
        if (k <= 1 || head == null) return head;

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prevGroupEnd = dummy;

        while (true) {
            // 檢查是否還有足夠 k 個節點
            ListNode kth = prevGroupEnd;
            for (int i = 0; i < k && kth != null; i++) {
                kth = kth.next;
            }
            if (kth == null) break; // 不足 k，不反轉

            // 區段頭尾
            ListNode groupStart = prevGroupEnd.next;
            ListNode groupEnd = kth.next;

            // 反轉區段
            reverse(groupStart, kth);

            // 接回鏈結
            prevGroupEnd.next = kth;
            groupStart.next = groupEnd;

            // 移動 prevGroupEnd
            prevGroupEnd = groupStart;
        }
        return dummy.next;
    }

    // 輔助函式：反轉 [start, end] 區段
    private static void reverse(ListNode start, ListNode end) {
        ListNode prev = null;
        ListNode curr = start;
        ListNode stop = end.next;

        while (curr != stop) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
    }
}
