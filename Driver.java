import java.util.*;

public class Driver{
    public static void main(String[] args) {
        Storage drive = new Storage();

        BufferCache cache = new BufferCache();
        BufferCache.FreeBuffer free_head = cache.getHead();

        /* Generating random block number to be read from the memory & 
        applying buffer cache algorithm */
        Random rand = new Random(); //Used for random number generation
        BufferCache.HashQueue[] queue = cache.getInitial();
        for(int i=0;i<10;i++){
            int blk_no = rand.nextInt(10);
            int data = drive.getBlock(blk_no);
            System.out.println(blk_no+" "+data);
            queue[blk_no%10].addtoHash(blk_no,data); // add data to corresponding hashqueue
            
        }
        System.out.println(" ");

        for(int i=0;i<10;i++){
            BufferCache.FreeBuffer temp = queue[i].next;
            if(temp==null){
                continue;
            }
            System.out.print(i+"-->");
            System.out.print(temp.data+" ");
            while(temp.next!=queue[i].next){
                temp = temp.next;
                System.out.print(temp.data+" ");
            }
            System.out.println(" ");
            
        }
    }
}