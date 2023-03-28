public class BufferCache{
    private FreeBuffer head;                                                                                 
    private HashQueue initial[];

    public class FreeBuffer{
        FreeBuffer next;
        FreeBuffer prev;
        boolean delayed_wr;
        boolean available;
        public int blk_no;
        public int data;

        FreeBuffer(){
            delayed_wr = false;
            available = true;
            blk_no = -1;
            data =-1;
            next = null;
            prev = null;
        }

        public FreeBuffer getNext(){
            return next;
        }

        public void setNext(FreeBuffer next){
            this.next = next;
        }

        public FreeBuffer getPrev(){
            return prev;
        }

        public void setPrev(FreeBuffer prev){
            this.prev = prev;
        }

        public boolean isDelayed_wr(){
            return delayed_wr;
        }

        public void setDelayed_wr(boolean delayed_wr){
            this.delayed_wr = delayed_wr;
        }

        public void addBuffer(FreeBuffer ref){
            if(this.next == null){
                this.next = ref;
                ref.setPrev(this);
            }else{
                this.next.setPrev(ref);
                ref.setNext(this.next);
                this.next = ref;
                ref.setPrev(this);
            }
        }

    }

    public class HashQueue{
        FreeBuffer next;

        HashQueue(){
            next = null;
        }

        public void setNext(FreeBuffer next){
            this.next = next;
        }

        public FreeBuffer getNext(){
            return next;
        }

        private boolean find(int blk_no){
            FreeBuffer temp = this.next;
            if(temp == null){
                return false;
            }
            while(temp.next!=this.next){
                if(temp.blk_no == blk_no){
                    return true;
                }
                temp = temp.next;
            }
            if(temp.blk_no==blk_no){
                return true;
            }
            return false;
        }

        private int addLink(HashQueue ref,int data,int blk_no){
            FreeBuffer temp = head;
            if(head==null){
                return -1;
            }
            
            while(temp.delayed_wr){
                if(temp.next==head){
                    return -2;
                }
                temp = temp.next;
            }
            if(ref.next==null){
                ref.next = temp;
                temp.prev.next = temp.next;
                temp.next.prev = temp.prev;
                head = temp.next;
                temp.next = temp;
                temp.prev = temp;
                
            }else{
                FreeBuffer temp2 = head;
                FreeBuffer hash_next = ref.next;
                ref.next = temp2;
                temp2.prev.next = temp2.next;
                temp2.next.prev = temp2.prev;
                head = temp2.next;
                temp2.next = hash_next;
                temp2.prev = hash_next.prev;
                temp2.next.prev = temp2;
                temp2.prev.next = temp2;
            }
            //ref.setNext(temp);
            ref.next.data = data;
            ref.next.blk_no = blk_no;
            return 1;
        }
        public boolean addtoHash(int blk_no,int data){
            boolean isPresent = find(blk_no);
          //  System.out.println("BLK:"+blk_no+" present:"+isPresent);
            if(isPresent){
                System.out.println("The block is already in the Buffer Cache...");
                return false;
            }

            int status = addLink(this,data,blk_no);
            if(status == -1){
                System.out.println("FreeList is empty");
                return false;
            }
            if(status == -2){
                System.out.println("Delayed write is pending...");
                return false;
            }

            System.out.println("Added to HashQueue");
            return true;
        }

    }

    public BufferCache(){
        /*Initializing FreeBuffer */
        head = new FreeBuffer();
        FreeBuffer iterator = head;
        FreeBuffer temp=null;
        for(int i = 0; i < 25; i++){
            temp = new FreeBuffer();
            iterator.setNext(temp);
            temp.setPrev(iterator);
            iterator.blk_no = i;
            iterator = iterator.next;
        }

        temp.setNext(head);
        head.setPrev(temp);
        FreeBuffer temp2 = new FreeBuffer();
        temp2.blk_no = 100;
        head.addBuffer(temp2);

        /*Initializing HashQueue */
        initial = new HashQueue[10];
        for(int i = 0; i < 10; i++){
            initial[i] = new HashQueue();
        }

    }
                
    public FreeBuffer getHead(){
        return head;
    }

    public HashQueue[] getInitial() {
        return initial;
    }
}