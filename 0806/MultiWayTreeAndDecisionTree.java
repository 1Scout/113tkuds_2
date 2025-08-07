import java.util.*;

public class MultiWayTreeAndDecisionTree {

    static class MultiWayNode {
        String data;
        List<MultiWayNode> children;
        MultiWayNode parent;
        
        public MultiWayNode(String data) {
            this.data = data;
            this.children = new ArrayList<>();
            this.parent = null;
        }
        
        public void addChild(MultiWayNode child) {
            if (child != null) {
                this.children.add(child);
                child.parent = this;
            }
        }
        
        public void removeChild(MultiWayNode child) {
            if (child != null) {
                this.children.remove(child);
                child.parent = null;
            }
        }
        
        public boolean isLeaf() {
            return children.isEmpty();
        }
        
        public int getDegree() {
            return children.size();
        }
        
        @Override
        public String toString() {
            return data;
        }
    }

    static class DecisionNode {
        String question;     
        String answer;      
        Map<String, DecisionNode> branches;
        DecisionNode parent;
        boolean isLeaf;
        ）
        public DecisionNode(String question) {
            this.question = question;
            this.branches = new HashMap<>();
            this.isLeaf = false;
            this.parent = null;
        }

        public DecisionNode(String answer, boolean isLeaf) {
            this.answer = answer;
            this.isLeaf = isLeaf;
            this.branches = new HashMap<>();
            this.parent = null;
        }
        
        public void addBranch(String condition, DecisionNode child) {
            if (child != null && !isLeaf) {
                branches.put(condition, child);
                child.parent = this;
            }
        }
        
        public DecisionNode getBranch(String condition) {
            return branches.get(condition);
        }
        
        @Override
        public String toString() {
            return isLeaf ? answer : question;
        }
    }

    static class MultiWayTree {
        private MultiWayNode root;
        
        public MultiWayTree(String rootData) {
            this.root = new MultiWayNode(rootData);
        }
        
        public MultiWayNode getRoot() {
            return root;
        }

        public List<String> depthFirstTraversal() {
            List<String> result = new ArrayList<>();
            dfsHelper(root, result);
            return result;
        }
        
        private void dfsHelper(MultiWayNode node, List<String> result) {
            if (node == null) return;
            
            result.add(node.data);
            
            for (MultiWayNode child : node.children) {
                dfsHelper(child, result);
            }
        }
        

        public List<String> breadthFirstTraversal() {
            List<String> result = new ArrayList<>();
            if (root == null) return result;
            
            Queue<MultiWayNode> queue = new LinkedList<>();
            queue.offer(root);
            
            while (!queue.isEmpty()) {
                MultiWayNode current = queue.poll();
                result.add(current.data);
                
                for (MultiWayNode child : current.children) {
                    queue.offer(child);
                }
            }
            
            return result;
        }

        public int getHeight() {
            return getHeight(root);
        }
        
        private int getHeight(MultiWayNode node) {
            if (node == null || node.isLeaf()) {
                return 0;
            }
            
            int maxHeight = 0;
            for (MultiWayNode child : node.children) {
                maxHeight = Math.max(maxHeight, getHeight(child));
            }
            
            return maxHeight + 1;
        }
        
        public Map<String, Integer> getAllNodeDegrees() {
            Map<String, Integer> degrees = new HashMap<>();
            getAllNodeDegreesHelper(root, degrees);
            return degrees;
        }
        
        private void getAllNodeDegreesHelper(MultiWayNode node, Map<String, Integer> degrees) {
            if (node == null) return;
            
            degrees.put(node.data, node.getDegree());
            
            for (MultiWayNode child : node.children) {
                getAllNodeDegreesHelper(child, degrees);
            }
        }
        
        public MultiWayNode findNode(String data) {
            return findNodeHelper(root, data);
        }
        
        private MultiWayNode findNodeHelper(MultiWayNode node, String data) {
            if (node == null) return null;
            
            if (node.data.equals(data)) {
                return node;
            }
            
            for (MultiWayNode child : node.children) {
                MultiWayNode found = findNodeHelper(child, data);
                if (found != null) {
                    return found;
                }
            }
            
            return null;
        }

        public void printTree() {
            if (root == null) {
                System.out.println("空樹");
                return;
            }
            printTreeHelper(root, "", true);
        }
        
        private void printTreeHelper(MultiWayNode node, String prefix, boolean isLast) {
            if (node != null) {
                System.out.println(prefix + (isLast ? "└── " : "├── ") + node.data + 
                                 " (度數: " + node.getDegree() + ")");
                
                for (int i = 0; i < node.children.size(); i++) {
                    boolean isLastChild = (i == node.children.size() - 1);
                    printTreeHelper(node.children.get(i), 
                                  prefix + (isLast ? "    " : "│   "), 
                                  isLastChild);
                }
            }
        }
    }

    static class DecisionTree {
        private DecisionNode root;
        private Scanner scanner;
        
        public DecisionTree(DecisionNode root) {
            this.root = root;
            this.scanner = new Scanner(System.in);
        }
        

        public void executeDecision() {
            executeDecisionHelper(root);
        }
        
        private void executeDecisionHelper(DecisionNode node) {
            if (node.isLeaf) {
                System.out.println("結果：" + node.answer);
                return;
            }
            
            System.out.println(node.question);

            Set<String> options = node.branches.keySet();
            System.out.println("請選擇：" + String.join(", ", options));
            
            String userInput = scanner.nextLine().trim();
            
            DecisionNode nextNode = node.getBranch(userInput);
            if (nextNode != null) {
                executeDecisionHelper(nextNode);
            } else {
                System.out.println("無效選擇，請重新輸入。");
                executeDecisionHelper(node);
            }
        }

        public void demonstrateDecision(String[] choices) {
            demonstrateDecisionHelper(root, choices, 0);
        }
        
        private void demonstrateDecisionHelper(DecisionNode node, String[] choices, int index) {
            if (node.isLeaf) {
                System.out.println("結果：" + node.answer);
                return;
            }
            
            System.out.println("問題：" + node.question);
            
            if (index < choices.length) {
                String choice = choices[index];
                System.out.println("選擇：" + choice);
                
                DecisionNode nextNode = node.getBranch(choice);
                if (nextNode != null) {
                    demonstrateDecisionHelper(nextNode, choices, index + 1);
                } else {
                    System.out.println("無效選擇：" + choice);
                }
            } else {
                System.out.println("演示結束，沒有更多選擇。");
            }
        }

        public void printDecisionTree() {
            if (root == null) {
                System.out.println("空決策樹");
                return;
            }
            printDecisionTreeHelper(root, "", true);
        }
        
        private void printDecisionTreeHelper(DecisionNode node, String prefix, boolean isLast) {
            if (node != null) {
                String nodeInfo = node.isLeaf ? "[答案] " + node.answer : "[問題] " + node.question;
                System.out.println(prefix + (isLast ? "└── " : "├── ") + nodeInfo);
                
                if (!node.isLeaf) {
                    List<String> conditions = new ArrayList<>(node.branches.keySet());
                    Collections.sort(conditions); 
                    
                    for (int i = 0; i < conditions.size(); i++) {
                        String condition = conditions.get(i);
                        DecisionNode child = node.branches.get(condition);
                        boolean isLastChild = (i == conditions.size() - 1);
                        
                        System.out.println(prefix + (isLast ? "    " : "│   ") + 
                                         (isLastChild ? "└── " : "├── ") + 
                                         "[" + condition + "]");
                        
                        printDecisionTreeHelper(child, 
                                              prefix + (isLast ? "    " : "│   ") + 
                                              (isLastChild ? "    " : "│   "), 
                                              true);
                    }
                }
            }
        }
        
        // 計算決策樹的深度
        public int getDepth() {
            return getDepth(root);
        }
        
        private int getDepth(DecisionNode node) {
            if (node == null || node.isLeaf) {
                return 0;
            }
            
            int maxDepth = 0;
            for (DecisionNode child : node.branches.values()) {
                maxDepth = Math.max(maxDepth, getDepth(child));
            }
            
            return maxDepth + 1;
        }
    }

    public static MultiWayTree createSampleMultiWayTree() {
        MultiWayTree tree = new MultiWayTree("根節點");
        MultiWayNode root = tree.getRoot();

        MultiWayNode child1 = new MultiWayNode("子節點1");
        MultiWayNode child2 = new MultiWayNode("子節點2");
        MultiWayNode child3 = new MultiWayNode("子節點3");
        
        root.addChild(child1);
        root.addChild(child2);
        root.addChild(child3);

        MultiWayNode child1_1 = new MultiWayNode("子節點1-1");
        MultiWayNode child1_2 = new MultiWayNode("子節點1-2");
        child1.addChild(child1_1);
        child1.addChild(child1_2);
        
        MultiWayNode child2_1 = new MultiWayNode("子節點2-1");
        MultiWayNode child2_2 = new MultiWayNode("子節點2-2");
        MultiWayNode child2_3 = new MultiWayNode("子節點2-3");
        child2.addChild(child2_1);
        child2.addChild(child2_2);
        child2.addChild(child2_3);
        
        MultiWayNode child1_1_1 = new MultiWayNode("子節點1-1-1");
        MultiWayNode child1_1_2 = new MultiWayNode("子節點1-1-2");
        child1_1.addChild(child1_1_1);
        child1_1.addChild(child1_1_2);
        
        return tree;
    }
    

    public static DecisionTree createNumberGuessingGame() {

        DecisionNode root = new DecisionNode("你想的數字是在1-100之間嗎？");

        DecisionNode range1_100 = new DecisionNode("數字是大於50嗎？");
        
        DecisionNode greater50 = new DecisionNode("數字是大於75嗎？");
        DecisionNode range51_75 = new DecisionNode("你想的數字是在51-75之間！", true);
        DecisionNode range76_100 = new DecisionNode("你想的數字是在76-100之間！", true);
        
        greater50.addBranch("是", range76_100);
        greater50.addBranch("否", range51_75);
        
        DecisionNode lessEqual50 = new DecisionNode("數字是大於25嗎？");
        DecisionNode range26_50 = new DecisionNode("你想的數字是在26-50之間！", true);
        DecisionNode range1_25 = new DecisionNode("你想的數字是在1-25之間！", true);
        
        lessEqual50.addBranch("是", range26_50);
        lessEqual50.addBranch("否", range1_25);
        
        range1_100.addBranch("是", greater50);
        range1_100.addBranch("否", lessEqual50);

        DecisionNode outOfRange = new DecisionNode("請重新想一個1-100之間的數字！", true);
        
        root.addBranch("是", range1_100);
        root.addBranch("否", outOfRange);
        
        return new DecisionTree(root);
    }
    
    public static DecisionTree createAnimalClassificationTree() {
        DecisionNode root = new DecisionNode("這個動物有毛嗎？");
        
        DecisionNode hasFur = new DecisionNode("這個動物會飛嗎？");
        DecisionNode mammalFlying = new DecisionNode("這是蝙蝠！", true);
        
        DecisionNode mammalNotFlying = new DecisionNode("這個動物很大嗎？");
        DecisionNode largeMammal = new DecisionNode("這可能是大象或熊！", true);
        DecisionNode smallMammal = new DecisionNode("這可能是貓或狗！", true);
        
        mammalNotFlying.addBranch("是", largeMammal);
        mammalNotFlying.addBranch("否", smallMammal);
        
        hasFur.addBranch("是", mammalFlying);
        hasFur.addBranch("否", mammalNotFlying);
        
        DecisionNode noFur = new DecisionNode("這個動物會游泳嗎？");
        DecisionNode swimmer = new DecisionNode("這可能是魚或海豚！", true);
        
        DecisionNode nonSwimmer = new DecisionNode("這個動物會飛嗎？");
        DecisionNode flyingNonFur = new DecisionNode("這可能是鳥！", true);
        DecisionNode crawling = new DecisionNode("這可能是爬蟲類！", true);
        
        nonSwimmer.addBranch("是", flyingNonFur);
        nonSwimmer.addBranch("否", crawling);
        
        noFur.addBranch("是", swimmer);
        noFur.addBranch("否", nonSwimmer);
        
        root.addBranch("是", hasFur);
        root.addBranch("否", noFur);
        
        return new DecisionTree(root);
    }
    
    public static void main(String[] args) {
        System.out.println("=== 多路樹和決策樹操作測試 ===\n");
        
        System.out.println("1. 多路樹基本操作測試：");
        MultiWayTree multiTree = createSampleMultiWayTree();
        
        System.out.println("多路樹結構：");
        multiTree.printTree();
        
        System.out.println("\n深度優先走訪：" + multiTree.depthFirstTraversal());
        System.out.println("廣度優先走訪：" + multiTree.breadthFirstTraversal());
        System.out.println("樹的高度：" + multiTree.getHeight());
        
        Map<String, Integer> degrees = multiTree.getAllNodeDegrees();
        System.out.println("各節點度數：");
        degrees.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> System.out.println("  " + entry.getKey() + ": " + entry.getValue()));
        System.out.println();
        
        System.out.println("2. 決策樹測試 - 猜數字遊戲：");
        DecisionTree numberGame = createNumberGuessingGame();
        
        System.out.println("決策樹結構：");
        numberGame.printDecisionTree();
        System.out.println("決策樹深度：" + numberGame.getDepth());
        
        System.out.println("\n自動演示（選擇：是 -> 是 -> 否）：");
        String[] choices1 = {"是", "是", "否"};
        numberGame.demonstrateDecision(choices1);
        
        System.out.println("\n自動演示（選擇：是 -> 否 -> 是）：");
        String[] choices2 = {"是", "否", "是"};
        numberGame.demonstrateDecision(choices2);
        System.out.println();

        System.out.println("3. 決策樹測試 - 動物分類：");
        DecisionTree animalTree = createAnimalClassificationTree();
        
        System.out.println("動物分類決策樹結構：");
        animalTree.printDecisionTree();
        System.out.println("決策樹深度：" + animalTree.getDepth());
        
        System.out.println("\n自動演示1（有毛 -> 不會飛 -> 很大）：");
        String[] animalChoices1 = {"是", "否", "是"};
        animalTree.demonstrateDecision(animalChoices1);
        
        System.out.println("\n自動演示2（沒有毛 -> 會游泳）：");
        String[] animalChoices2 = {"否", "是"};
        animalTree.demonstrateDecision(animalChoices2);
        
        System.out.println("\n自動演示3（沒有毛 -> 不會游泳 -> 會飛）：");
        String[] animalChoices3 = {"否", "否", "是"};
        animalTree.demonstrateDecision(animalChoices3);
        System.out.println();
        
        System.out.println("4. 多路樹搜尋功能測試：");
        MultiWayNode foundNode = multiTree.findNode("子節點2-2");
        if (foundNode != null) {
            System.out.println("找到節點：" + foundNode.data);
            System.out.println("父節點：" + (foundNode.parent != null ? foundNode.parent.data : "無"));
            System.out.println("子節點數量：" + foundNode.getDegree());
        }
        
        MultiWayNode notFoundNode = multiTree.findNode("不存在的節點");
        System.out.println("搜尋不存在的節點結果：" + notFoundNode);
        
        System.out.println("\n=== 測試完成 ===");
            
    }
}