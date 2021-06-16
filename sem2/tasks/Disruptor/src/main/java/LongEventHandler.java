import com.lmax.disruptor.EventHandler;

public class LongEventHandler implements EventHandler<LongEvent>
{
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws InterruptedException {
        System.out.println("Event: " + event + " handled");
    }
}