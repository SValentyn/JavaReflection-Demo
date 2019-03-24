import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author Syniuk Valentyn
 */
public class CreatorArrays {

    public static void main(String[] args) {

        int[] array = (int[]) createArray(int.class, 5);
        for (int i = 0; i < array.length; i++)
            array[i] = i;
        System.out.println(toString(array));

        array = (int[]) changeSizeArray(array, 10);
        System.out.println(toString(array));

        System.out.println("**********************");

        int[][] matrix = (int[][]) createMatrix(int.class, 5);
        for (int i = 0; i < matrix.length; i++)
            matrix[i][i] = i;
        System.out.println(Arrays.deepToString(matrix));

        matrix = (int[][]) changeSizeMatrix(matrix, 6);
        System.out.println(toString(matrix));
    }

    private static Object createArray(Class cls, int size) {
        return Array.newInstance(cls, size);
    }

    private static Object createMatrix(Class cls, int size) {
        return Array.newInstance(cls, size, size);
    }

    private static Object changeSizeArray(Object array, int size) {
        Class cls = array.getClass();
        Object newArray = Array.newInstance(cls.getComponentType(), size);

        int oldSize = Array.getLength(array);
        if (oldSize < size) size = oldSize;

        System.arraycopy(array, 0, newArray, 0, size);
        return newArray;
    }

    private static Object changeSizeMatrix(Object matrix, int size) {
        Class cls = matrix.getClass();
        Object newMatrix = createMatrix(cls.getComponentType().getComponentType(), size);

        int oldSize = Array.getLength(matrix);
        if (oldSize < size) {
            for (int i = 0; i < oldSize; i++)
                Array.set(newMatrix, i, changeSizeArray(Array.get(matrix, i), size));
            for (int i = oldSize; i < size; i++)
                Array.set(newMatrix, i, createArray(cls.getComponentType().getComponentType(), size));
        } else {
            for (int i = 0; i < size; i++)
                Array.set(newMatrix, i, changeSizeArray(Array.get(matrix, i), size));
        }
        return newMatrix;
    }

    private static String toString(Object array) {
        StringBuilder builder = new StringBuilder();
        Class cls = array.getClass();

        if (!cls.isArray()) {
            builder.append(array.toString());
        } else {
            int length = Array.getLength(array);

            for (int i = 0; i < length; i++) {
                if (i == 0) builder.append("[");
                Object value = Array.get(array, i);
                builder.append(toString(value));
                if (i != length - 1) {
                    builder.append(", ");
                } else builder.append("]");
            }
        }

        return builder.toString();
    }

}