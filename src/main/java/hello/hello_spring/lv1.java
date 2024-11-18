package hello.hello_spring;

import java.util.Scanner;

class Solution {
    static boolean solution(String s) {
        boolean answer = true;

        int count_p = 0;
        int count_y = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'p' || s.charAt(i) == 'P') {
                count_p += 1;
            } else if (s.charAt(i) == 'y' || s.charAt(i) == 'Y') {
                count_y += 1;
            }
        }

        if (count_p == count_y) {
            answer = true;
        } else if (count_p != count_y) {
            answer = false;
        } else if (count_p == 0 && count_y == 0) {
            answer = true;
        }

        return answer;
    }
}

public class lv1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.next();
        boolean result = Solution.solution(str);
        System.out.println(result);
    }
}
