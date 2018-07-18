package org.main;


//import org.main.airplane.Solution;
import org.main.battleship.Solution;

/**
 * Created by vinodm1986 on 7/4/18.
 */
public class MainClass {

    public static void main(String[] args) {
        /*Solution s = new Solution();
        int count = s.solution(50,"1A 2B 3C 11A");
        System.out.println("count = " + count);*/

        Solution s = new Solution();
        //System.out.println(s.solution(26, "1B 2C,2D 4D,25A 25D", ""));
        System.out.println(s.solution(26, "", "1A"));


    }
}
