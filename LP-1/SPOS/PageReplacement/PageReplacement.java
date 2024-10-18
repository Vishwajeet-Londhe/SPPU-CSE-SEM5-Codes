import java.util.*;

public class PageReplacement {

    private static Scanner sc = new Scanner(System.in);

    static abstract class CommonUtils {
        protected int[][] result;
        protected LinkedList<Integer> pagesReference;
        protected int hits;
        protected int faults;

        public CommonUtils(){
            hits = faults = 0;
            pagesReference = new LinkedList<>();
            System.out.print("Enter the no.of numbers in the page reference string: ");
            int prs = sc.nextInt();
            for(int x = 0; x < prs; x++){
                System.out.print("Enter the number: ");
                int n = sc.nextInt();
                pagesReference.add(n);
            }
            System.out.print("Enter the no.of frames: ");
            int f = sc.nextInt();
            result = new int[f][prs];
            for(int x = 0; x < result.length; x++){
                for(int y= 0; y < result[0].length; y++){
                    result[x][y] = -1;
                }
            }
        }

        public abstract void performAlgorithm();

        public void showResult(){
            for(int x = 0; x < result.length; x++){
                for(int y = 0; y < result[0].length; y++){
                    System.out.print(result[x][y] + " ");
                }
                System.out.println();
            }
            System.out.println("No.of hits: " + hits);
            System.out.println("No.of faults: " + faults);
        }
    }

    static class FIFO extends CommonUtils {
        private Queue<Integer> numQueue;

        public FIFO(){
            numQueue = new LinkedList<>();
        }

        public void performAlgorithm(){
            int j = 0;
            while(!pagesReference.isEmpty()){
                int num = pagesReference.removeFirst();
                boolean isFault = true;
                boolean isInserted = false;
                for(int x = 0; x < result.length; x++){
                    if(result[x][j] == -1){
                        isFault = true;
                        faults++;
                        result[x][j] = num;
                        isInserted = true;
                        break;
                    }
                    else if(result[x][j] == num){
                        isFault = false;
                        hits++;
                        break;
                    }
                }
                if(isFault && isInserted){
                    numQueue.add(num);
                }
                else if(isFault){
                    faults++;
                    numQueue.add(num);
                    int n = numQueue.remove();
                    for(int x = 0; x < result.length; x++){
                        if(result[x][j] == n){
                            result[x][j] = num;
                            break;
                        }
                    }
                }
                j++;
                if(j < result[0].length) {
                    for (int x = 0; x < result.length; x++) {
                        result[x][j] = result[x][j-1];
                    }
                }
            }
        }
    }

    static class LRU extends CommonUtils {
        private Deque<Integer> pageSequenceRecord;

        public LRU(){
            pageSequenceRecord = new LinkedList<>();
        }


        @Override
        public void performAlgorithm() {
            int j = 0;
            while(!pagesReference.isEmpty()){
                int num = pagesReference.remove();
                boolean isFault = true;
                boolean isInserted = false;
                for(int x = 0; x < result.length; x++){
                    if(result[x][j] == -1){
                        isFault = true;
                        faults++;
                        result[x][j] = num;
                        isInserted = true;
                        break;
                    }
                    else if(result[x][j] == num){
                        isFault = false;
                        hits++;
                        break;
                    }
                }
                if(isFault && isInserted){
                    pageSequenceRecord.addLast(num);
                }
                else if(!isFault){
                    pageSequenceRecord.remove(num);
                    pageSequenceRecord.addLast(num);
                }
                else {       // isFault and !isInserted.
                    int n = pageSequenceRecord.removeFirst();
                    for(int x = 0; x < result.length; x++){
                        if(result[x][j] == n){
                            result[x][j] = num;
                            break;
                        }
                    }
                    pageSequenceRecord.addLast(num);
                    faults++;
                }
                j++;
                if(j < result[0].length) {
                    for (int x = 0; x < result.length; x++) {
                        result[x][j] = result[x][j-1];
                    }
                }
            }
        }
    }

    static class OPT extends CommonUtils {

        @Override
        public void performAlgorithm() {
            int j = 0;
            while(!pagesReference.isEmpty()) {
                int num = pagesReference.remove();
                boolean isFault = true;
                boolean isInserted = false;
                for (int x = 0; x < result.length; x++) {
                    if (result[x][j] == -1) {
                        isFault = true;
                        faults++;
                        result[x][j] = num;
                        isInserted = true;
                        break;
                    } else if (result[x][j] == num) {
                        isFault = false;
                        hits++;
                        break;
                    }
                }
                if (isFault && !isInserted) {
                    int maxPageRefEleIdx = -1;
                    int resultEleIdx = -1;
                    for (int x = 0; x < result.length; x++) {
                        int ele = result[x][j];
                         if(!pagesReference.contains(ele)){
                             result[x][j] = num;
                             break;
                         }
                         else{
                             int i = pagesReference.indexOf(ele);
                             if(maxPageRefEleIdx <= i){
                                 maxPageRefEleIdx = i;
                                 resultEleIdx = x;
                             }
                        }
                    }
                    if(resultEleIdx != -1){
                        result[resultEleIdx][j] = num;
                    }
                    faults++;
                }
                j++;
                if (j < result[0].length) {
                    for (int x = 0; x < result.length; x++) {
                        result[x][j] = result[x][j - 1];
                    }
                }
            }
        }
    }

    public static void main(String[] args){

        System.out.println("*********************************** FIFO *******************************************");
        FIFO f = new FIFO();
        f.performAlgorithm();
        f.showResult();

        System.out.println("*********************************** LRU *******************************************");
        LRU l = new LRU();
        l.performAlgorithm();
        l.showResult();

        System.out.println("*********************************** OPT *******************************************");
        OPT o = new OPT();
        o.performAlgorithm();
        o.showResult();

    }

}