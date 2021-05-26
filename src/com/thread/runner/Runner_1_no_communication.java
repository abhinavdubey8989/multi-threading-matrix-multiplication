package com.thread.runner;

import com.thread.reader.Reader;
import com.thread.workerThread.WorkerThread;

import java.util.LinkedList;
import java.util.List;

/**
 * the below logic takes 2 matrix as input
 *
 * dimensions of matrix 1 : AxB
 * dimensions of matrix 2 : BxC
 * dimensions of output matrix : AxC
 *
 * we start AxC threads ,
 * each thread will calculate the value of 1 cell for the output matrix
 *
 *
 * ============
 * IMPORTANT :
 * ============
 * however print sequence may get corrupted
 *
 * ie Thread-xyz calculated (i,j) : xx : may come in between the ouput matrix
 *
 * as after starting the last thread , the main thread prints the result matrix
 * without waiting for all previous threads to complete
 *
 *
 * ===========
 * Scenario :
 * ===========
 * i tried to check if output can be wrong , given above
 * by providing 50x50 matrix
 *
 * logic of being wrong : if matrices are very big ,
 * after starting the last thread , the main thread goes to print result ,
 * but what if last thread has not yet completed its calculation
 *
 *
 *
 * =======
 * NOTE :
 * =======
 *
 * here there is no thread communication
 */


public class Runner_1_no_communication {

    //matrix1 dimensions = AxB
    //matrix2 dimensions = BxC
    private int[][] inputMatrix1;
    private int[][] inputMatrix2;
    private int[][] outputMatrix;


    public static void main(String[] args) {
        Runner_1_no_communication r = new Runner_1_no_communication();
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
