import java.util.Arrays;
import java.util.Stack;

public class CustomStringBuilder {
    private char[] value;
    private int count;
    private final Stack<Snapshot> snapshots;

    public CustomStringBuilder() {
        this(16);
    }

    public CustomStringBuilder(int capacity) {
        value = new char[capacity];
        count = 0;
        snapshots = new Stack<>();
    }

    public CustomStringBuilder(String str) {
        this(str.length() + 16);
        append(str);
    }

    private void ensureCapacity(int minimumCapacity) {
        if (minimumCapacity - value.length > 0) {
            expandCapacity(minimumCapacity);
        }
    }

    private void expandCapacity(int minimumCapacity) {
        int newCapacity = value.length * 2 + 2;
        if (newCapacity - minimumCapacity < 0) {
            newCapacity = minimumCapacity;
        }
        value = Arrays.copyOf(value, newCapacity);
    }
    public CustomStringBuilder append(String str) {
        saveSnapshot();
        if (str == null) {
            str = "null";
        }
        int len = str.length();
        ensureCapacity(count + len);
        str.getChars(0, len, value, count);
        count += len;
        return this;
    }

    public CustomStringBuilder append(char c) {
        saveSnapshot();
        ensureCapacity(count + 1);
        value[count++] = c;
        return this;
    }

    public CustomStringBuilder delete(int start, int end) {
        saveSnapshot();
        if (start < 0) {
            throw new StringIndexOutOfBoundsException(start);
        }
        if (end > count) {
            end = count;
        }
        if (start > end) {
            throw new StringIndexOutOfBoundsException();
        }
        int len = end - start;
        if (len > 0) {
            System.arraycopy(value, start + len, value, start, count - end);
            count -= len;
        }
        return this;
    }

    public String toString() {
        return new String(value, 0, count);
    }

    public void undo() {
        if (!snapshots.isEmpty()) {
            Snapshot snapshot = snapshots.pop();
            value = snapshot.getValue();
            count = snapshot.getCount();
        }
    }

    private void saveSnapshot() {
        snapshots.push(new Snapshot(Arrays.copyOf(value, value.length), count));
    }


    private static class Snapshot {
        private final char[] value;
        private final int count;

        public Snapshot(char[] value, int count) {
            this.value = value;
            this.count = count;
        }

        public char[] getValue() {
            return value;
        }

        public int getCount() {
            return count;
        }
    }
}
