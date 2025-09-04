import java.io.*;
import java.util.*;

/**
 * LC23_MergeKLists_Hospitals
 * 題意：合併 k 條已排序的單向鏈結串列，輸出合併後的升序序列。
 * 方法：最小堆維持每條串列的當前頭節點；每次彈出最小並推進該串列。
 * 時間複雜度：O(N log k)；空間複雜度：O(k)
 */
public class LC23_MergeKLists_Hospitals {

    // 單向鏈結節點
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int v) { val = v; }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String first = br.readLine();
        if (first == null || first.trim().isEmpty()) {
            // 沒有輸入 → 視為空輸出
            return;
        }
        int k = Integer.parseInt(first.trim());
        ListNode[] lists = new ListNode[Math.max(k, 0)];

        // 讀取 k 行：每行是升序序列，以 -1 結尾；可能是空串列（直接 -1）
        for (int i = 0; i < k; i++) {
            String line = br.readLine();
            // 保守處理：若遇到空行，嘗試再讀一行
            while (line != null && line.trim().isEmpty()) {
                line = br.readLine();
            }
            if (line == null) { // 若輸入行不足，直接視為空串列
                lists[i] = null;
                continue;
            }
            String[] toks = line.trim().split("\\s+");
            ListNode dummy = new ListNode(0), cur = dummy;
            for (String t : toks) {
                if (t.equals("-1")) break; // 本行結束
                // 解析節點值
                int v = Integer.parseInt(t);
                cur.next = new ListNode(v);
                cur = cur.next;
            }
            lists[i] = dummy.next; // 可能為 null（空串列 or 直接 -1）
        }

        // 以最小堆合併 k 串列
        PriorityQueue<ListNode> pq = new PriorityQueue<>(Math.max(k, 1), Comparator.comparingInt(a -> a.val));
        for (int i = 0; i < k; i++) {
            if (lists[i] != null) pq.offer(lists[i]);
        }

        ListNode dummy = new ListNode(0), tail = dummy;
        while (!pq.isEmpty()) {
            ListNode node = pq.poll();     // 取目前最小
            tail.next = node;              // 接到結果尾端
            tail = tail.next;
            if (node.next != null) pq.offer(node.next); // 推進該串列
        }
        tail.next = null; // 收尾

        // 輸出合併後序列（同一行，以空白分隔）；若為空則不輸出任何數字
        StringBuilder sb = new StringBuilder();
        ListNode it = dummy.next;
        boolean firstOut = true;
        while (it != null) {
            if (!firstOut) sb.append(' ');
            sb.append(it.val);
            firstOut = false;
            it = it.next;
        }
        if (sb.length() > 0) {
            System.out.println(sb.toString());
        }
        // 若合併結果為空，根據題意「k=0 / 全空 → 輸出空」，此處不印任何內容即可。
    }
}
