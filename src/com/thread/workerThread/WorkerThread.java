package com.thread.workerThread;

public class WorkerThread extends Thread {

    private int rowId;
    private int colId;
    private int[][] input1;
    private int[][] input2;
    private int[][] output;


    public WorkerThread(int row, int col, int[][] i1, int[][] i2, int[][] out) {
        this.rowId = row;
        this.colId = col;
        this.input1 = i1;
        this.input2 = i2;
        this.output = out;
    }


    @Override
    public void run() {
        int value = 0;
        int ptr1 = 0;
        int ptr2 = 0;

        while (ptr1 < input1[0].length && ptr2 < input2.length) {
            value += input1[this.rowId][ptr1] * input2[ptr2][this.colId];
            ptr1++;
            ptr2++;
        }

        output[this.rowId][this.colId] = value;
        System.out.println(Thread.currentThread().getName() + " calculated (" + this.rowId + "," + this.colId + ") : " + value);
    }


}
