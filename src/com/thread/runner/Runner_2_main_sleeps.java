package com.thread.runner;
import com.thread.reader.Reader;
import com.thread.workerThread.WorkerThread;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * here we have forced the main thread to sleep for 1 second
 * so that the print sequence is not corrupted
 * and all thread msgs come before the result[][] is printed
 *
 *
 * however this is inefficient way of doing it
 *
 *
 *
 */


public class Runner_2_main_sleeps {

    //matrix1 dimensions = AxB
    //matrix2 dimensions = BxC
    private int[][] inputMatrix1;
    private int[][] inputMatrix2;
    private int[][] outputMatrix;


    public static void main(String[] args) {
        Runner_2_main_sleeps r = new Runner_2_main_sleeps();
        r.initialize();
        r.multiply();

        try {
            Thread.sleep(1000);
        } catch (Exception e) {

        }

        r.print("Result matrix");
        r.showMatrix(r.outputMatrix);
    }


    void initialize() {
        Reader reader = new Reader();
        this.print("Enter rows and columns of 1st matrix respectively : ");

        int a = reader.nextInt();
        int b = reader.nextInt();

        this.print("Enter columns of 2nd matrix : ");

        int c = reader.nextInt();

        this.inputMatrix1 = new int[a][b];
        this.inputMatrix2 = new int[b][c];
        this.outputMatrix = new int[a][c];

        this.print("Enter " + (a * b) + " elements of 1st matrix : ");
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < b; j++) {
                this.inputMatrix1[i][j] = reader.nextInt();
            }
        }


        this.print("Enter " + (b * c) + " elements of 2nd matrix : ");

        for (int i = 0; i < b; i++) {
            for (int j = 0; j < c; j++) {
                this.inputMatrix2[i][j] = reader.nextInt();
            }
        }

        this.print("Matrix-1 : ");
        showMatrix(inputMatrix1);
        this.print("Matrix-2 : ");
        showMatrix(inputMatrix2);
        this.print("Press any key to continue");
        reader.nextLine();
    }

    void multiply() {
        int outputRows = this.outputMatrix.length;
        int outputCols = this.outputMatrix[0].length;
        List<WorkerThread> threads = new LinkedList<>();

        for (int i = 0; i < outputRows; i++) {
            for (int j = 0; j < outputCols; j++) {
                threads.add(new WorkerThread(i, j, inputMatrix1, inputMatrix2, outputMatrix));
                threads.get(threads.size() - 1).start();
            }
        }

    }


    void showMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }


    void print(String msg) {
        System.out.println();
        System.out.println(msg);
    }
}
