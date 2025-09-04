import java.util.*;

public class LC40_CombinationSum2_Procurement {
    static List<List<Integer>> res = new ArrayList<>();
    static List<Integer> path = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(), target = sc.nextInt();
        int[] candidates = new int[n];
        for (int i = 0; i < n; i++) candidates[i] = sc.nextInt();
        Arrays.sort(candidates); // 排序，方便去重 + 遞增
        backtrack(candidates, target, 0);
        for (List<Integer> comb : res) {
            for (int i = 0; i < comb.size(); i++) {
                if (i > 0) System.out.print(" ");
                System.out.print(comb.get(i));
            }
            System.out.println();
        }
    }

    static void backtrack(int[] candidates, int remain, int start) {
        if (remain == 0) {
            res.add(new ArrayList<>(path));
            return;
        }
        for (int i = start; i < candidates.length; i++) {
            if (i > start && candidates[i] == candidates[i - 1]) continue; // 同層去重
            if (candidates[i] > remain) break; // 剪枝
            path.add(candidates[i]);
            backtrack(candidates, remain - candidates[i], i + 1); // II 版只能用一次，所以傳 i+1
            path.remove(path.size() - 1);
        }
    }
}
