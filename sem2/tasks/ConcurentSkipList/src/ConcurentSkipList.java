import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurentSkipList {
    static class Node {
        int key;
        int value;
        int level;
        Node[] forward;

        ReentrantLock lock;
        boolean fullyLinked;
        boolean markedForRemoval;

        public Node(int key, int value, int level, int maxLevel) {
            this.key = key;
            this.value = value;
            this.level = level;
            this.forward = new Node[maxLevel];
            this.fullyLinked = true;
            this.markedForRemoval = false;
            this.lock = new ReentrantLock();
        }

        public String toString() {
            return Integer.toString(value);
        }
    }

    private final Node header;
    private AtomicInteger currentLevels;
    private final int maxLevel;
    private AtomicInteger size;
    private final float p = 0.5f;

    public ConcurentSkipList(int maxLevel) {
        this.currentLevels = new AtomicInteger(0);
        this.size = new AtomicInteger(0);
        this.maxLevel = maxLevel;
        header = new Node(Integer.MAX_VALUE, Integer.MAX_VALUE, 0, maxLevel);
        for (int i = 0; i < maxLevel; i += 1) {
            header.forward[i] = header;
        }
    }

    public boolean add(Integer value) {
        return insert(value, value);
    }

    private boolean insert(int searchKey, int value) {
        Node[] predecessors = new Node[maxLevel];
        Node[] successors = new Node[maxLevel];

        while (true) {
            int foundNodeLevel = find(searchKey, predecessors, successors);

            if (foundNodeLevel != -1) {
                Node foundNode = successors[foundNodeLevel];
                if (!foundNode.markedForRemoval) {
                    while (!foundNode.fullyLinked);
                    return false;
                }
            }

            int levels = currentLevels.get();
            int newLevel = chooseRandomLevel();

            if (newLevel > levels) {
                newLevel = currentLevels.incrementAndGet();
                levels = newLevel;
            }

            int highestLockedLevel = -1;

            try {
                boolean valid = true;
                Node predecessor;
                Node successor;
                Node previousPredecessor = null;

                for (int level = 0; (valid && (level <= newLevel)); level += 1) {
                    predecessor = predecessors[level];
                    successor = successors[level];

                    if (predecessor != previousPredecessor) {
                        predecessor.lock.lock();
                        highestLockedLevel = level;
                        previousPredecessor = predecessor;
                    }

                    valid = !predecessor.markedForRemoval
                            && !successor.markedForRemoval
                            && predecessor.forward[level] == successor;
                }

                if (!valid) {
                    continue;
                }

                Node newNode = new Node(searchKey, value, newLevel, maxLevel);

                for (int level = 0; level <= newLevel; level += 1) {
                    newNode.forward[level] = successors[level];
                    predecessors[level].forward[level] = newNode;
                }

                newNode.fullyLinked = true;
                size.incrementAndGet();

                return true;
            }
            finally {
                for (int level = 0; level <= highestLockedLevel; level += 1) {
                    if (predecessors[level].lock.isHeldByCurrentThread()) {
                        predecessors[level].lock.unlock();
                    }
                }
            }
        }
    }

    public boolean remove(Object value) {
        if (! (value instanceof Integer)) {
            return false;
        }

        Node[] predecessors = new Node[maxLevel];
        Node[] successors = new Node[maxLevel];
        Node nodeToRemove = null;
        boolean inProcessOfRemoving = false;
        int highestLevelFound = -1;

        while (true) {
            int foundNodeLevel = find(value, predecessors, successors);

            if (inProcessOfRemoving
                    || (foundNodeLevel != -1
                    && canDelete(successors[foundNodeLevel], foundNodeLevel))) {

                if (!inProcessOfRemoving) {
                    nodeToRemove = successors[foundNodeLevel];
                    highestLevelFound = nodeToRemove.level;
                    nodeToRemove.lock.lock();

                    if (nodeToRemove.markedForRemoval) {
                        nodeToRemove.lock.unlock();
                        return false;
                    }

                    nodeToRemove.markedForRemoval = true;
                    inProcessOfRemoving = true;
                }

                int highestLockedLevel = -1;

                try {
                    boolean valid = true;
                    Node predecessor;
                    Node successor;
                    Node previousPredecessor = null;

                    for (int level = 0; (valid && (level <= highestLevelFound)); level += 1) {
                        predecessor = predecessors[level];
                        successor = successors[level];

                        if (predecessor != previousPredecessor) {
                            predecessor.lock.lock();
                            highestLockedLevel = level;
                            previousPredecessor = predecessor;
                        }

                        valid = !predecessor.markedForRemoval
                                && predecessor.forward[level] == successor;
                    }

                    if (!valid) {
                        continue;
                    }

                    for (int level = highestLevelFound; level >= 0; level -= 1) {
                        predecessors[level].forward[level] = nodeToRemove.forward[level];
                    }

                    nodeToRemove.lock.unlock();
                    size.decrementAndGet();

                    return true;
                }
                finally {
                    for (int level = 0; level <= highestLockedLevel; level += 1) {
                        if (predecessors[level].lock.isHeldByCurrentThread()) {
                            predecessors[level].lock.unlock();
                        }
                    }
                }
            }
            else {
                return false;
            }
        }
    }

    public boolean canDelete(Node node, int highestLevelFound) {
        return !node.markedForRemoval
                && node.fullyLinked
                && node.level == highestLevelFound;
    }

    public boolean contains(Object value) {
        Node[] predecessors = new Node[maxLevel];
        Node[] successors = new Node[maxLevel];

        int foundNodeLevel = find(value, predecessors, successors);

        return foundNodeLevel != -1
                && successors[foundNodeLevel].fullyLinked
                && !successors[foundNodeLevel].markedForRemoval;
    }

    public int find(Object value, Node[] predecessors, Node[] successors) {
        if (! (value instanceof Integer)) {
            return -1;
        }

        int highestLevel = -1;
        int searchKey = (Integer) value;
        Node predecessor = this.header;
        Node current;

        for (int level = maxLevel - 1; level >= 0; level -= 1) {
            current = predecessor.forward[level];

            while (current.key < searchKey) {
                predecessor = current;
                current = predecessor.forward[level];
            }

            if (highestLevel == -1 && current.key == searchKey) {
                highestLevel = level;
            }

            predecessors[level] = predecessor;
            successors[level] = current;
        }

        return highestLevel;
    }

    private Random levelRandom = new Random(0);

    private int chooseRandomLevel() {
        int newLevel = 0;
        while (newLevel < maxLevel - 1 && levelRandom.nextFloat() < this.p) {
            newLevel += 1;
        }
        return newLevel;
    }

    public int size() {
        return size.get();
    }

}