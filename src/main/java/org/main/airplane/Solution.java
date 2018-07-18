package org.main.airplane;

import java.util.*;

/**
 * Created by vinodm1986 on 7/7/18.
 */
public class Solution {

    private static final int SKIP_SEAT = 8;
    private static final int ASCII_ALPHABET = 65;
    private static final int FAMILY_SIZE = 3;

    public int solution(int N, String S) {
        final Map<Character, Integer> seatLabels = generateSeatLabels(SKIP_SEAT, ASCII_ALPHABET);
        final List<Row> rows = generateRows(N);
        preSelectSeats(seatLabels, rows, S);
        System.out.println(rows);
        return familyCount(rows,FAMILY_SIZE);
    }

    /**
     * Maps seat labels to Integer values to be identified with in an array.
     * @param skipSeat: The index to be skipped. For ex: "I"
     * @param startIndex: Starting index offset of ASCII characters we are interested in.
     * @return A Map containing seat labels mapped to an integer identifier.
     */
    private Map<Character, Integer> generateSeatLabels(int skipSeat, int startIndex) {
        final Map<Character, Integer> rowLabels = new HashMap<Character, Integer>();
        for (int i = 0; i < 10; i++) {
            final int position = i >= skipSeat ? startIndex + i + 1 : startIndex + i;
            final char seatLabel = (char) position;
            rowLabels.put(seatLabel, i);

        }
        return rowLabels;
    }

    /**
     * Generates the given number of rows.
     * @param N: Number of rows to be generated
     * @return: A list of Rows.
     */

    private List<Row> generateRows(int N) {
        final List<Row> rows = new ArrayList<Row>();
        for (int i = 0; i < N; i++) {
            rows.add(new Row());
        }
        return rows;
    }

    /**
     * Populates each row with the list of seats selected. This function mutates the given rows.
     * @param seatLabels: The map containing a mapping between seat labels and integers
     * @param rows: List of rows we are interested in pre-populating
     * @param S: pre-selected seats.
     *
     */

    private void preSelectSeats(Map<Character, Integer> seatLabels, List<Row> rows, String S) {
        String[] selectedSeats = S.split(" ");
        for (String selectedSeat : selectedSeats) {
            if (selectedSeat.length() < 2) continue;
            final int row = Integer.parseInt(selectedSeat.substring(0, selectedSeat.length()-1));
            final char seat = selectedSeat.charAt(selectedSeat.length()-1);
            rows.get(row - 1).selectSeat(seatLabels, seat);
        }
    }

    /**
     * Given a set of rows, finds how many more families can be accomodated.
     * @param rows: List of rows in the airplane.
     * @return
     */
    private int familyCount(List<Row> rows, int familySize) {
        int familyCount = 0;
        for (Row row : rows)
            familyCount += row.canAccomodateFamilies(familySize);

        return familyCount;
    }

    class Row {
        final int[] left = new int[3];
        final int[] middle = new int[4];
        final int[] right = new int[3];

        /**
         * Selects the given seat in the row
         * @param seatLabels: Seat label to array index mapping
         * @param seat: The seat we are interested
         * @return: flag to identify success or failure
         */
        public boolean selectSeat(Map<Character, Integer> seatLabels, Character seat) {
            if (!seatLabels.containsKey(seat)) return false;
            final int section = seatLabels.get(seat);
            if (section < left.length) {
                left[section] = 1;
                return true;
            }
            if (section < left.length + middle.length) {
                middle[section - left.length] = 1;
                return true;
            }
            if (section < left.length + middle.length + right.length) {
                right[section - left.length - middle.length] = 1;
                return true;
            }
            return false;
        }

        /**
         * Function to find how many families can be accomodated in a given row
         * @param familySize: The size of the family we are interested in.
         * @return: number of families.
         */
        public int canAccomodateFamilies(int familySize) {
            int families = findFamilyCountInSection(left, familySize) +
                    findFamilyCountInSection(middle, familySize) +
                    findFamilyCountInSection(right, familySize);

            return families;
        }

        private int findFamilyCountInSection(int[] section, int familySize) {
            int familyCount = 0;
            int emptyCount = 0;
            for (int i = 0; i < section.length; i++) {
                if (section[i] == 1) {
                    if (emptyCount >= familySize) familyCount++;
                    emptyCount = 0;
                } else emptyCount++;
            }
            if(emptyCount >= familySize) familyCount++;
            return familyCount;
        }

        public String toString() {
            return Arrays.toString(left) + Arrays.toString(middle) + Arrays.toString(right)+ "\n";
        }
    }
}


