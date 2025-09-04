import java.util.*;

/**
 * LC24_SwapPairs_Shifts
 * 題意：將鏈結串列中相鄰兩節點成對交換。
 * 方法：Dummy 節點 + while 迴圈，每次處理一對 (a, b)。
 * 複雜度：O(n)，空間 O(1)
 */
public class LC24_SwapPairs_Shifts {

    // 定義單向鏈結節點
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int v) { val = v; }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 讀取整行輸入，若空則直接輸出空
        if (!sc.hasNextLine()) {
            return;
        }
        String line = sc.nextLine().trim();
        if (line.isEmpty()) {
            return;
        }

        String[] toks = line.split("\\s+");
        ListNode dummy = new ListNode(0), cur = dummy;
        for (String t : toks) {
            cur.next = new ListNode(Integer.parseInt(t));
            cur = cur.next;
        }
        sc.close();

        ListNode newHead = swapPairs(dummy.next);

        // 輸出結果
        StringBuilder sb = new StringBuilder();
        cur = newHead;
        while (cur != null) {
            sb.append(cur.val);
            if (cur.next != null) sb.append(" ");
            cur = cur.next;
        }
        System.out.println(sb.toString());
    }

    // 成對交換鏈結串列節點
    static ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;

        while (prev.next != null && prev.next.next != null) {
            ListNode a = prev.next;
            ListNode b = a.next;

            // 交換
            a.next = b.next;
            b.next = a;
            prev.next = b;

            // 移動 prev 到下一對之前
            prev = a;
        }
        return dummy.next;
    }
}
