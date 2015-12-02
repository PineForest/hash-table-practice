package algorithms.practice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HashTable {
    private int buckets;
    private ArrayList<Integer>[] table;

    public HashTable(int buckets) {
        this.buckets = buckets;
        table = new ArrayList[buckets];
    }

    public void add(Integer value) {
        int hash = value.hashCode();
        int bucket = hash % buckets;
        if (table[bucket] == null) {
            table[bucket] = new ArrayList<Integer>(16);
        }
        insert(table[bucket], value);
    }

    private void insert(List buckets, Integer value) {
        insert(buckets, value, 0, buckets.size());
    }

    private void insert(List buckets, Integer value, int start, int end) {
        final int size = end - start;
        if (size <= 0) {
            buckets.add(end, value);
            return;
        }
        final int valueHash = value.hashCode();
        if (size == 1) {
            buckets.add((buckets.get(start).hashCode() < valueHash ? end : start), value);
            return;
        }
        int middle = size / 2;
        int middleHash = buckets.get(start + middle).hashCode();
        if (middleHash < valueHash) {
            insert(buckets, value, start + middle, end);
        } else {
            insert(buckets, value, start, start + middle);
        }
    }

    private static int[] convert(String[] stringArray) {
        int[] result = new int[stringArray.length];
        for (int i = 0; i < stringArray.length; ++i) {
            result[i] = Integer.parseInt(stringArray[i]);
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            line = line.trim();
            String[] stringArray = line.split(",");
            int[] array = convert(stringArray);
            int buckets = array[0];
            int[] adaptedArray = new int[array.length - 1];
            System.arraycopy(array, 1, adaptedArray, 0, adaptedArray.length);
            System.out.println(Arrays.toString(adaptedArray));
            HashTable hashTable = new HashTable(buckets);
            for (int value : adaptedArray) {
                hashTable.add(value);
            }
            System.out.println();
            for (int i = 0; i < array[0]; ++i) {
                System.out.println(String.format("%d. ", i + 1) + (hashTable.table[i] == null ? "[ ]" : Arrays.toString(hashTable.table[i].toArray())));
            }
            System.out.println();
        }
/*
        for (int j = 0; j < 5; ++j) {
            int quantity = ((int) (Math.random() * 1000)) + 1;
            int upperBound = ((int) (Math.random() * 10000)) + quantity;
            int buckets = ((int) (Math.random() * quantity)) + 1;
            System.out.print(buckets);
            for (int i = 0; i < quantity; ++i) {
                int value = ((int) (Math.random() * upperBound));
                System.out.print("," + String.valueOf(value));
            }
            System.out.println();
        }
*/
    }
}