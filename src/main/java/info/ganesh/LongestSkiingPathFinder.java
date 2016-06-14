package info.ganesh;

import java.io.InputStream;
import java.util.Scanner;

/**
 * I am greedy and I want make best out of money spent on skiing. Find me the
 * longest route.
 * 
 * 
 * 
 */
public class LongestSkiingPathFinder {
    private int[][] skiiPointHeights;
    public String heights = "";

    public LongestSkiingPathFinder() {
    }

    public void readInput(Scanner inputScanner) {
        String line = inputScanner.nextLine();
        String[] split = line.split(" ");
        int width = Integer.parseInt(split[0]);
        int length = Integer.parseInt(split[1]);

        skiiPointHeights = new int[width][length];
        for (int i = 0; i < length; i++) {
            String[] heights = inputScanner.nextLine().split(" ");
            for (int j = 0; j < heights.length; j++) {
                String height = heights[j];
                skiiPointHeights[i][j] = Integer.parseInt(height);
            }
        }
    }

    String findLongestFromACell(int i, int j, String path) {
        int n = skiiPointHeights.length;
        String pathFromLeft = null;
        String pathFromRight = null;
        String pathFromTop = null;
        String pathFromBottom = null;
        String longestpath = ""; 
        int max = -1;
        if (j < n - 1 && (skiiPointHeights[i][j] > skiiPointHeights[i][j + 1])) {
            pathFromRight =  findLongestFromACell(i, j + 1, path + " > "+ skiiPointHeights[i][j + 1]);
            if (pathFromRight.split(">").length > max) {
                max = pathFromRight.split(">").length;
                longestpath = pathFromRight;
            }
        }

        if (j > 0 && (skiiPointHeights[i][j] > skiiPointHeights[i][j - 1])) {
            pathFromLeft = findLongestFromACell(i, j - 1, path + " > "+ skiiPointHeights[i][j - 1]);
            if (pathFromLeft.split(">").length  > max) {
                max = pathFromLeft.split(">").length ;
                longestpath = pathFromLeft;
            }
        }

        if (i > 0 && (skiiPointHeights[i][j] > skiiPointHeights[i - 1][j])) {
            pathFromTop =  findLongestFromACell(i - 1, j, path + " > "+ skiiPointHeights[i-1][j]);
            if (pathFromTop.split(">").length  > max) {
                max = pathFromTop.split(">").length ;
                longestpath = pathFromTop;
            }
        }

        if (i < n - 1 && (skiiPointHeights[i][j] > skiiPointHeights[i + 1][j])) {
            pathFromBottom =  findLongestFromACell(i + 1, j, path + " > "+ skiiPointHeights[i+1][j]);
            if (pathFromBottom.split(">").length  > max) {
                max = pathFromBottom.split(">").length ;
                longestpath = pathFromBottom;
            }
        }

        if (max == -1) {
            return path;
        } else {
            return longestpath;
        }
    }

   

    public static void main(String[] args) {
        InputStream resourceAsStream = LongestSkiingPathFinder.class.getClassLoader().getResourceAsStream("input.txt");
        Scanner scanner = new Scanner(resourceAsStream);
        LongestSkiingPathFinder longestSkiingPathFinder = new LongestSkiingPathFinder();
        longestSkiingPathFinder.readInput(scanner);
        int maxLength = -1;
        String longestPath = "";
        int maxDescent = -1;
        for (int i = 0; i < longestSkiingPathFinder.skiiPointHeights.length; i++) {
            for (int j = 0; j < longestSkiingPathFinder.skiiPointHeights[0].length; j++) {
                String path = longestSkiingPathFinder.skiiPointHeights[i][j]+"";
                String findLongestFromACell = longestSkiingPathFinder.findLongestFromACell(i, j, path );
                String[] split = findLongestFromACell.split(">");
                int descent = Integer.parseInt(split[0].trim()) - Integer.parseInt(split[split.length -1].trim());
                if(split.length > maxLength || (split.length == maxLength && descent > maxDescent)){
                    longestPath = findLongestFromACell;
                    maxLength = split.length;
                    maxDescent = descent;
                }
            }
        }
        System.out.println("Longest path : " + longestPath);
        System.out.println("Drop :" + maxDescent);
        System.out.println("Length :" + longestPath.split(">").length);
    }
}
