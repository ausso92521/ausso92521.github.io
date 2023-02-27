package tech.leftii.animaltracker.shared;

import tech.leftii.animaltracker.note.NoteDto;

import java.util.*;

public class RadixSort {
    private static NoteDto[] notes;
    /**
     * Radix LSD sort method loops for each digit and calls count sort each iteration
     * Time complexity 0(N*M) | N = notes.size() | M = 12 (digits)
     * This should be faster than comparison sorts like merge sort because time complexity is linear
     */
    public static List<NoteDto> sortNotes(List<NoteDto> unsortedNotes, boolean isAscending){
        notes = unsortedNotes.toArray(new NoteDto[unsortedNotes.size()]);
        // Ensures we go through all 12 digits
        final long max = 100000000000L;

        // Loop through each digit of each date
        for (long place = 1L; max/place > 0; place *= 10){
            countingSort(place);
        }

        // Return sorted list (ASC or DESC) for endpoint consumption
        List<NoteDto> orderedNotes = new ArrayList<>(Arrays.asList(notes));
        if(!isAscending) {
            Collections.reverse(orderedNotes);
        }
        return orderedNotes;
    }


    /*
     * Counting sort for each radix, customized to index by object reference rather than date value
     * */
    private static void countingSort(long place){
        int[] countArray = new int[10];
        for(NoteDto note : notes){
            countArray[(int)((note.noteDateAsLong() / place) % 10)]++;
        }

        for(int i = 1; i < 10; i++){
            countArray[i] += countArray[i - 1];
        }

        NoteDto[] output = new NoteDto[notes.length];
        for(int i = notes.length - 1; i >=0; i--){
            long current = notes[i].noteDateAsLong();
            int positionInArray = countArray[(int)((current / place) % 10)] - 1;
            output[positionInArray] = notes[i];
            countArray[(int)((current / place) % 10)]--;
        }
        System.arraycopy(output, 0, notes, 0, notes.length);
    }
}
