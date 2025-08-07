import java.util.*;

public class RecursiveTreePreview {
    
    static class FileNode {
        String name;
        boolean isFile;
        List<FileNode> children;
        
        public FileNode(String name, boolean isFile) {
            this.name = name;
            this.isFile = isFile;
            this.children = new ArrayList<>();
        }
        
        public void addChild(FileNode child) {
            this.children.add(child);
        }
    }
    
    public static int countFiles(FileNode node) {
        if (node.isFile) {
            return 1; 
        }
        
        int count = 0;
        for (FileNode child : node.children) {
            count += countFiles(child); 
        }
        return count;
    }
    
    public static void printFileSystem(FileNode node, String prefix) {
        System.out.println(prefix + (node.isFile ? " " : " ") + node.name);
        
        if (!node.isFile) {
            for (FileNode child : node.children) {
                printFileSystem(child, prefix + "  ");
            }
        }
    }
    
    static class MenuItem {
        String name;
        List<MenuItem> subMenus;
        
        public MenuItem(String name) {
            this.name = name;
            this.subMenus = new ArrayList<>();
        }
        
        public void addSubMenu(MenuItem item) {
            this.subMenus.add(item);
        }
    }
    
    public static void printMenu(MenuItem menu, int level) {
        String prefix = "  ".repeat(level);
        String symbol = level == 0 ? " " : 
                       level == 1 ? "├─ " : 
                       level == 2 ? "│ ├─ " : "│ │ ├─ ";
        
        System.out.println(prefix + symbol + menu.name);
        
        for (MenuItem subMenu : menu.subMenus) {
            printMenu(subMenu, level + 1);
        }
    }

    public static void flattenArray(Object[] array, List<Object> result) {
        for (Object element : array) {
            if (element instanceof Object[]) {

                flattenArray((Object[]) element, result);
            } else {            
                result.add(element);
            }
        }
    }
    
    public static List<Object> flattenArray(Object[] array) {
        List<Object> result = new ArrayList<>();
        flattenArray(array, result);
        return result;
    }
    
    public static int calculateMaxDepth(List<?> list) {
        if (list == null || list.isEmpty()) {
            return 0;
        }
        
        int maxDepth = 1;
        
        for (Object item : list) {
            if (item instanceof List<?>) {
                int childDepth = 1 + calculateMaxDepth((List<?>) item);
                maxDepth = Math.max(maxDepth, childDepth);
            }
        }
        
        return maxDepth;
    }
    
    public static void printNestedList(List<?> list, int level) {
        String prefix = "  ".repeat(level);
        
        for (int i = 0; i < list.size(); i++) {
            Object item = list.get(i);
            
            if (item instanceof List<?>) {
                System.out.println(prefix + " [巢狀清單 " + (i + 1) + "]");
                printNestedList((List<?>) item, level + 1);
            } else {
                System.out.println(prefix + "• " + item);
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println(" 遞迴樹狀結構處理範例");
        System.out.println("=" + "=".repeat(50));
        
        System.out.println("\n 1. 檔案系統模擬");
        System.out.println("-".repeat(30));
        
        FileNode root = new FileNode("專案資料夾", false);
        FileNode src = new FileNode("src", false);
        FileNode docs = new FileNode("docs", false);
        
        src.addChild(new FileNode("Main.java", true));
        src.addChild(new FileNode("Utils.java", true));
        
        FileNode controller = new FileNode("controller", false);
        controller.addChild(new FileNode("UserController.java", true));
        controller.addChild(new FileNode("OrderController.java", true));
        src.addChild(controller);
        
        docs.addChild(new FileNode("README.md", true));
        docs.addChild(new FileNode("API.md", true));
        
        root.addChild(src);
        root.addChild(docs);
        root.addChild(new FileNode("pom.xml", true));
        
        printFileSystem(root, "");
        System.out.println(" 總檔案數量: " + countFiles(root) + " 個");
        
        System.out.println("\n 2. 多層選單結構");
        System.out.println("-".repeat(30));
        
        MenuItem mainMenu = new MenuItem("主選單");
        
        MenuItem fileMenu = new MenuItem("檔案");
        fileMenu.addSubMenu(new MenuItem("新建"));
        fileMenu.addSubMenu(new MenuItem("開啟"));
        MenuItem saveMenu = new MenuItem("儲存");
        saveMenu.addSubMenu(new MenuItem("儲存"));
        saveMenu.addSubMenu(new MenuItem("另存新檔"));
        fileMenu.addSubMenu(saveMenu);
        
        MenuItem editMenu = new MenuItem("編輯");
        editMenu.addSubMenu(new MenuItem("復原"));
        editMenu.addSubMenu(new MenuItem("重做"));
        editMenu.addSubMenu(new MenuItem("複製"));
        editMenu.addSubMenu(new MenuItem("貼上"));
        
        MenuItem viewMenu = new MenuItem("檢視");
        viewMenu.addSubMenu(new MenuItem("縮放"));
        viewMenu.addSubMenu(new MenuItem("全螢幕"));
        
        mainMenu.addSubMenu(fileMenu);
        mainMenu.addSubMenu(editMenu);
        mainMenu.addSubMenu(viewMenu);
        
        printMenu(mainMenu, 0);
        
        System.out.println("\n 3. 巢狀陣列展平");
        System.out.println("-".repeat(30));
        
        Object[] nestedArray = {
            1, 2,
            new Object[]{3, 4, new Object[]{5, 6}},
            7,
            new Object[]{8, new Object[]{9, 10, new Object[]{11, 12}}},
            13
        };
        
        System.out.println("原始巢狀陣列結構:");
        System.out.println(Arrays.deepToString(nestedArray));
        
        List<Object> flattened = flattenArray(nestedArray);
        System.out.println("展平後的陣列:");
        System.out.println(flattened);
        
        System.out.println("\n📏 4. 巢狀清單最大深度");
        System.out.println("-".repeat(30));
        
        List<Object> nestedList = new ArrayList<>();
        nestedList.add("層級1-項目1");
        nestedList.add("層級1-項目2");
        
        List<Object> level2 = new ArrayList<>();
        level2.add("層級2-項目1");
        level2.add("層級2-項目2");
        
        List<Object> level3 = new ArrayList<>();
        level3.add("層級3-項目1");
        
        List<Object> level4 = new ArrayList<>();
        level4.add("層級4-項目1");
        level4.add("層級4-項目2");
        level3.add(level4);
        
        level2.add(level3);
        nestedList.add(level2);
        nestedList.add("層級1-項目3");
        
        System.out.println("巢狀清單結構:");
        printNestedList(nestedList, 0);
        
        int maxDepth = calculateMaxDepth(nestedList);
        System.out.println(" 最大深度: " + maxDepth + " 層");
        
        System.out.println("\n 額外測試案例");
        System.out.println("-".repeat(30));
        
        List<Object> emptyList = new ArrayList<>();
        System.out.println("空清單深度: " + calculateMaxDepth(emptyList));
        
        List<Object> singleLevel = Arrays.asList("A", "B", "C");
        System.out.println("單層清單深度: " + calculateMaxDepth(singleLevel));
        
        Object[] complexArray = {
            "外層",
            new Object[]{
                "第二層",
                new Object[]{
                    "第三層",
                    new Object[]{"第四層", "項目"}
                }
            }
        };
        
        List<Object> complexFlattened = flattenArray(complexArray);
        System.out.println("複雜陣列展平結果: " + complexFlattened);
    
        System.out.println("\n 所有測試完成！");
    }
}