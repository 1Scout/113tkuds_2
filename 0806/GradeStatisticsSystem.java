
public class GradeStatisticsSystem {
    private double[] grades;
    private double average;
    private double highest;
    private double lowest;
    private int[] gradeDistribution;
    private int aboveAverageCount;
    
    private static final int A_THRESHOLD = 90;
    private static final int B_THRESHOLD = 80;
    private static final int C_THRESHOLD = 70;
    private static final int D_THRESHOLD = 60;
    
    public GradeStatisticsSystem(double[] grades) {
        this.grades = grades.clone();
        this.gradeDistribution = new int[5]; // A, B, C, D, F
        calculateStatistics();
    }
    
    private void calculateStatistics() {
        if (grades.length == 0) {
            return;
        }
        
        calculateBasicStatistics();
        calculateGradeDistribution();
        calculateAboveAverageCount();
    }
    
    private void calculateBasicStatistics() {
        double sum = 0;
        highest = grades[0];
        lowest = grades[0];
        
        for (double grade : grades) {
            sum += grade;
            if (grade > highest) {
                highest = grade;
            }
            if (grade < lowest) {
                lowest = grade;
            }
        }
        
        average = sum / grades.length;
    }
    
    private void calculateGradeDistribution() {
        for (double grade : grades) {
            if (grade >= A_THRESHOLD) {
                gradeDistribution[0]++; // A
            } else if (grade >= B_THRESHOLD) {
                gradeDistribution[1]++; // B
            } else if (grade >= C_THRESHOLD) {
                gradeDistribution[2]++; // C
            } else if (grade >= D_THRESHOLD) {
                gradeDistribution[3]++; // D
            } else {
                gradeDistribution[4]++; // F
            }
        }
    }
    
    private void calculateAboveAverageCount() {
        aboveAverageCount = 0;
        for (double grade : grades) {
            if (grade > average) {
                aboveAverageCount++;
            }
        }
    }
    
    private char getGradeLetter(double score) {
        if (score >= A_THRESHOLD) return 'A';
        else if (score >= B_THRESHOLD) return 'B';
        else if (score >= C_THRESHOLD) return 'C';
        else if (score >= D_THRESHOLD) return 'D';
        else return 'F';
    }
    
    public double getAverage() {
        return average;
    }
    
    public double getHighest() {
        return highest;
    }
    
    public double getLowest() {
        return lowest;
    }
    
    public int[] getGradeDistribution() {
        return gradeDistribution.clone();
    }
    
    public int getAboveAverageCount() {
        return aboveAverageCount;
    }
    
    public void printReport() {
        System.out.println("════════════════════════════════════════");
        System.out.println("              成績統計報表");
        System.out.println("════════════════════════════════════════");
        
        System.out.println("學生成績清單：");
        for (int i = 0; i < grades.length; i++) {
            System.out.printf("學生 %2d: %5.1f (%c)%n", 
                (i + 1), grades[i], getGradeLetter(grades[i]));
        }
        
        System.out.println("\n----------------------------------------");
        System.out.println("基本統計資料：");
        System.out.println("----------------------------------------");
        System.out.println("學生總數：   " + grades.length + " 人");
        System.out.printf("平均成績：   %.2f 分%n", average);
        System.out.printf("最高成績：   %.1f 分%n", highest);
        System.out.printf("最低成績：   %.1f 分%n", lowest);
        System.out.printf("成績範圍：   %.1f 分%n", (highest - lowest));
        
        System.out.println("\n----------------------------------------");
        System.out.println("等第分布統計：");
        System.out.println("----------------------------------------");
        char[] letters = {'A', 'B', 'C', 'D', 'F'};
        String[] ranges = {"90-100", "80-89", "70-79", "60-69", "0-59"};
        
        for (int i = 0; i < gradeDistribution.length; i++) {
            double percentage = (double) gradeDistribution[i] / grades.length * 100;
            System.out.printf("%c 等第 (%s): %2d 人 (%.1f%%)%n", 
                letters[i], ranges[i], gradeDistribution[i], percentage);
        }
        
        System.out.println("\n----------------------------------------");
        System.out.println("進階分析：");
        System.out.println("----------------------------------------");
        System.out.println("高於平均分人數： " + aboveAverageCount + " 人");
        System.out.println("低於平均分人數： " + (grades.length - aboveAverageCount) + " 人");
        
        int passCount = gradeDistribution[0] + gradeDistribution[1] + 
                       gradeDistribution[2] + gradeDistribution[3];
        double passRate = (double) passCount / grades.length * 100;
        System.out.printf("及格率：        %.1f%% (%d/%d)%n", 
            passRate, passCount, grades.length);
        
        int excellentCount = gradeDistribution[0] + gradeDistribution[1];
        double excellentRate = (double) excellentCount / grades.length * 100;
        System.out.printf("優秀率：        %.1f%% (%d/%d)%n", 
            excellentRate, excellentCount, grades.length);
        
        System.out.println("════════════════════════════════════════");
    }
    
    public static void main(String[] args) {
        double[] testGrades = {85, 92, 78, 96, 87, 73, 89, 94, 82, 90};
        GradeStatisticsSystem system = new GradeStatisticsSystem(testGrades);
        
        system.printReport();
        
        System.out.println("\n個別功能測試：");
        System.out.println("─────────────────────────");
        System.out.printf("平均分：%.2f%n", system.getAverage());
        System.out.printf("最高分：%.1f%n", system.getHighest());
        System.out.printf("最低分：%.1f%n", system.getLowest());
        System.out.println("高於平均分人數：" + system.getAboveAverageCount() + " 人");
        
        int[] distribution = system.getGradeDistribution();
        System.out.print("等第分布：");
        char[] letters = {'A', 'B', 'C', 'D', 'F'};
        for (int i = 0; i < distribution.length; i++) {
            System.out.print(letters[i] + ":" + distribution[i] + "人 ");
        }
        System.out.println();
    }
}