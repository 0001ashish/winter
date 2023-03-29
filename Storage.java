import java.util.*;

public class Storage{
    int[][] storage;
    Random random;
    Storage(){
        storage = new int[10][10];
        random = new Random();
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                storage[i][j] = random.nextInt(20);
            }
        }
    }

    public int getBlock(int blk_no){
        return storage[blk_no/10][blk_no%10];
    }
}