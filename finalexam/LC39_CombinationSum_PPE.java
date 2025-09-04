import java.util.*;

public class LC39_CombinationSum_PPE {
    static List<List<Integer>> res = new ArrayList<>();
    static List<Integer> path = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(), target = sc.nextInt();
        int[] candidates = new int[n];
        for (int i = 0; i < n; i++) candidates[i] = sc.nextInt();
        Arrays.sort(candidates); // 排序，方便組合遞增
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
            if (candidates[i] > remain) break; // 剪枝
            path.add(candidates[i]);
            backtrack(candidates, remain - candidates[i], i); // I 版可重複，所以傳 i
            path.remove(path.size() - 1);
        }
    }
}