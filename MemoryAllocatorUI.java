import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class NextFitMemoryAllocator {
    // Block class to represent a memory block
    static class Block {
        int size; // Size of the block
        boolean isAllocated; // Whether the block is allocated or not

        public Block(int size) {
            this.size = size;
            this.isAllocated = false;
        }
    }

    // Memory pool (array of blocks)
    private ArrayList<Block> memoryPool;
    private int nextFitIndex;

    public NextFitMemoryAllocator() {
        memoryPool = new ArrayList<>();
        nextFitIndex = 0; // Start at the beginning of memory pool
    }

    // Method to initialize memory with predefined block sizes
    public void initializeMemory(int[] blockSizes) {
        for (int size : blockSizes) {
            memoryPool.add(new Block(size));
        }
    }

    // Method to manually add a new memory block
    public void addMemoryBlock(int blockSize) {
        memoryPool.add(new Block(blockSize));
    }

    // Function to allocate memory using Next Fit
    public boolean allocateMemory(int processSize) {
        int start = nextFitIndex;
        boolean allocated = false;

        // Search for a suitable block starting from the next fit index
        for (int i = 0; i < memoryPool.size(); i++) {
            int currentIndex = (start + i) % memoryPool.size(); // Wrap around using modulo
            Block block = memoryPool.get(currentIndex);
            if (!block.isAllocated && block.size >= processSize) {
                block.isAllocated = true;
                int remainingSize = block.size - processSize;
                block.size = processSize;
                nextFitIndex = currentIndex; // Update next fit index to the last used block
                allocated = true;
                // Split the block if there's remaining space
                if (remainingSize > 0) {
                    memoryPool.add(currentIndex + 1, new Block(remainingSize));
                }
                break;
            }
        }
        return allocated;
    }

    // Function to deallocate memory
    public void deallocateMemory(int blockIndex) {
        if (blockIndex < memoryPool.size()) {
            Block block = memoryPool.get(blockIndex);
            if (block.isAllocated) {
                block.isAllocated = false;
            }
        }
    }

    // Get memory status as a string array for display
    public String[] getMemoryStatus() {
        String[] status = new String[memoryPool.size()];
        for (int i = 0; i < memoryPool.size(); i++) {
            Block block = memoryPool.get(i);
            String currentMarker = (i == nextFitIndex) ? " [Current Block]" : "";
            status[i] = "Block " + i + ": Size " + block.size + " - " + (block.isAllocated ? "Allocated" : "Free")
                    + currentMarker;
        }
        return status;
    }
}

public class MemoryAllocatorUI {
    private NextFitMemoryAllocator allocator;
    private JFrame frame;
    private DefaultListModel<String> memoryListModel;

    public MemoryAllocatorUI() {
        allocator = new NextFitMemoryAllocator();
        memoryListModel = new DefaultListModel<>();
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Next Fit Memory Allocator");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Memory list display
        JList<String> memoryList = new JList<>(memoryListModel);
        JScrollPane scrollPane = new JScrollPane(memoryList);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Control panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(3, 2));

        JTextField blockSizeField = new JTextField();
        JButton addBlockButton = new JButton("Add Block");
        addBlockButton.addActionListener(e -> {
            int size = Integer.parseInt(blockSizeField.getText());
            allocator.addMemoryBlock(size);
            updateMemoryList();
        });

        JTextField processSizeField = new JTextField();
        JButton allocateButton = new JButton("Allocate");
        allocateButton.addActionListener(e -> {
            int size = Integer.parseInt(processSizeField.getText());
            allocator.allocateMemory(size);
            updateMemoryList();
        });

        JTextField deallocateIndexField = new JTextField();
        JButton deallocateButton = new JButton("Deallocate");
        deallocateButton.addActionListener(e -> {
            int index = Integer.parseInt(deallocateIndexField.getText());
            allocator.deallocateMemory(index);
            updateMemoryList();
        });

        controlPanel.add(new JLabel("Block Size:"));
        controlPanel.add(blockSizeField);
        controlPanel.add(addBlockButton);
        controlPanel.add(new JLabel("Process Size:"));
        controlPanel.add(processSizeField);
        controlPanel.add(allocateButton);
        controlPanel.add(new JLabel("Deallocate Index:"));
        controlPanel.add(deallocateIndexField);
        controlPanel.add(deallocateButton);

        panel.add(controlPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);

        // Initialize memory for demonstration
        allocator.initializeMemory(new int[] { 100, 50, 200, 300 });
        updateMemoryList();
    }

    private void updateMemoryList() {
        memoryListModel.clear();
        String[] status = allocator.getMemoryStatus();
        for (String s : status) {
            memoryListModel.addElement(s);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MemoryAllocatorUI::new);
    }
}
