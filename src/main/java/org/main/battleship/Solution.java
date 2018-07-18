package org.main.battleship;

import java.util.*;

public class Solution {

    public String solution(int N, String S, String T) {
        //Build the battlefield map
        int[][] map = buildMap(N);

        // Place ships and get the number of squares occupied by each ship
        Map<Integer, Integer> totalCounts = placeShips(map, S);

        // Place hits and get the number of squares hit per ship
        Map<Integer, Integer> hitCounts = placeHits(map, T);

        // compare and get [sunks, hits]
        int[] counts = getCounts(totalCounts, hitCounts);

        //Stringify and return
        return counts[0] + "," + counts[1];
    }

    /**
     * Builds a 2D map of required dimensions
     * @param N: The required dimensions
     * @return: a 2D matrix
     */
    private int[][] buildMap(int N) {
        int[][] map = new int[N][N];
        return map;
    }

    /**
     * Places ships on the given map(Mutates the given map). Identifies each ship by an index and places the index
     * in its position.
     * @param map: The map
     * @param S: The layout of the ship
     * @return: A map containing the number of squares occupied by a given ship.
     */
    private Map<Integer, Integer> placeShips(int[][] map, String S) {
        String[] ships = S.split(",");
        Map<Integer, Integer> shipSizeMap = new HashMap<Integer, Integer>();
        int shipIndex = 1;
        for (String ship : ships) {
            String[] shipPosition = ship.split(" ");
            if(shipPosition.length<2) continue;
            String start = shipPosition[0];
            String end = shipPosition[1];
            int[] index1 = getIndexFromPosition(start);
            int[] index2 = getIndexFromPosition(end);
            int row1 = index1[0], column1 = index1[1], row2 = index2[0], column2 = index2[1];
            int shipSize = 0;
            for (int i = row1; i <= row2; i++) {
                for (int j = column1; j <= column2; j++) {
                    map[i][j] = shipIndex;
                    shipSize++;
                }
            }
            shipSizeMap.put(shipIndex, shipSize);
            shipIndex++;
        }
        return shipSizeMap;
    }

    /**
     * Given a character, it returns the 0 based index based on its position in the alphabets.
     * @param character: the character to be converted to int
     * @return: The corresponding index.
     */
    private int indexFromChar(char character) {
        final int index = character - 65;
        return index;
    }

    /**
     * Given an input string, it returns the position in the 2D matrix. For 1A, returs [0,0]
     * @param position: The string position
     * @return: a 1D matrix
     */
    private int[] getIndexFromPosition(String position) {
        if(position.length() < 1) return null;
        int row = Integer.parseInt(position.substring(0, position.length() - 1));
        int column = indexFromChar(position.charAt(position.length() - 1));
        return new int[]{row - 1, column};
    }

    /**
     * Given a map and the hit positions, if the hit position happens to be in the position of the ship,
     * changes the value in the position from the ship index to 0. It also identifies how many ship positions
     * were hit by taking the ship index at the position and generates a map, which has the number of ship
     * positions hit.
     * @param map: the map
     * @param T: hit positions
     * @return: a map containing number of ship positions that were hit.
     */
    private Map<Integer, Integer> placeHits(int[][] map, String T) {
        Map<Integer, Integer> hitCounts = new HashMap<Integer, Integer>();
        String[] hits = T.split(" ");

        for (String hit : hits) {
            int[] index = getIndexFromPosition(hit);
            if(index == null) continue;
            int row = index[0], col = index[1];
            int current = map[row][col];
            if (current > 0) {
                Integer value = hitCounts.containsKey(current) ? hitCounts.get(current) : 0;
                hitCounts.put(current, value + 1);
                map[row][col] = 0;
            }
        }
        return hitCounts;
    }

    /**
     * Compares the ship size with hit size and gives an array with number of sunks and hits
     * @param totalCounts: a map containing size of the ship
     * @param hitCounts: a map containing number of positions that were hit.
     * @return: an array: [sunks, hits]
     */
    private int[] getCounts(Map<Integer, Integer> totalCounts, Map<Integer, Integer> hitCounts) {
        int[] counts = new int[2];

        for (Map.Entry<Integer, Integer> hit : hitCounts.entrySet()) {
            int ship = hit.getKey();
            int count = hit.getValue();
            int difference = totalCounts.get(ship) - count;
            if (difference < 1) counts[0] = counts[0] + 1;
            else counts[1] = counts[1] + count;
        }
        return counts;
    }



    /*
    private void printArray(int[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                System.out.print(map[i][j] + "   ");
            }
            System.out.println();
        }
    }
     */

}
