public class QueueUser implements Runnable {
    private final QueueCAS<Integer> queue;
    private UseFunction useFunction;

    public QueueUser(QueueCAS<Integer> queue, int times) {
        this.queue = queue;
        useFunction = (q) -> {
            for (int i = 0; i < times; i++) {
                q.offer(i);
            }
        };
    }

    public void setUseFunction(UseFunction useFunction) {
        this.useFunction = useFunction;
    }

    @Override
    public void run() {
        useFunction.use(queue);
    }
}
