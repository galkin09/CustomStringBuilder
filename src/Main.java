public class Main {
    public static void main(String[] args) {
        CustomStringBuilder csb = new CustomStringBuilder("Hello");
        System.out.println(csb.toString());
        csb.append(" world!");
        System.out.println(csb.toString());
        csb.undo();
        System.out.println(csb.toString());
        System.out.println(csb.delete(2,4));
    }
}
