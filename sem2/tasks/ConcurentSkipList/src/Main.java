public class Main {
    public static void main(String[]args){
        ConcurentSkipList skipList = new ConcurentSkipList(5);
        for (int i = 0; i < 5; i++){
            skipList.add(i);
        }
    }
}
