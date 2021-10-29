import java.util.Arrays;

public class ThreadHomeWork {
    static final int size = 10_000_000;
    static final int h = size / 2;

    public static void main(String[] args) {
        firstMethod();
        secondMethod();
    }

    public static void firstMethod() {
        float[] arr = new float[size];
        Arrays.fill(arr, 1.0f);

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println("One thread time: " + (System.currentTimeMillis() - startTime) + " ms.");
    }

    public static void secondMethod() {
        float[] arr = new float[size];

        Arrays.fill(arr, 1.0f);

        long startTime = System.currentTimeMillis();
        float[] leftHalf = new float[h];
        float[] rightHalf = new float[h];

        System.arraycopy(arr,0,leftHalf,0,h);
        System.arraycopy(arr,h,rightHalf,0,h);

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < h; i++) {
                leftHalf[i] = (float)(leftHalf[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < h; i++) {
                rightHalf[i] = (float)(rightHalf[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(leftHalf,0,arr,0,h);
        System.arraycopy(rightHalf,0,arr,h,h);

        System.out.println("Two thread time: " + (System.currentTimeMillis() - startTime) + " ms.");
    }

}
