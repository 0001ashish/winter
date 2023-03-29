import java.util.*;

public class Driver{
    public static void main(String[] args) {
        Storage drive = new Storage(); // drive is where the data is stored

        BufferCache cache = new BufferCache();
        BufferCache.FreeBuffer free_head = cache.getHead();

        /* Generating random block number to be read from the memory & 
        applying buffer cache algorithm */
        Random rand = new Random(); //Used for random number generation
        BufferCache.HashQueue[] queue = cache.getInitial();//returns an array of HashQueues
        for(int i=0;i<10;i++){
            int blk_no = rand.nextInt(10);
            int data = drive.getBlock(blk_no);
            System.out.println(blk_no+" "+data);

            /* getBlock is used to get block from disk into BufferCache
                This method returns a response of type Response which contains
                the following interfaces: 
                                        1. getResponse_type() 
                                        2. getResponse()
                                        3. get_block() */
            BufferCache.HashQueue.Response response = queue[blk_no%10].getBlock(blk_no,data); 
            System.out.println("Output:"+response.getResponse_type()+" "+response.getResponse());
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