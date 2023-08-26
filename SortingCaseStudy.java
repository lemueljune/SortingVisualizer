import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SortingCaseStudy extends JFrame {

    private int[] array;
    private boolean isSorted;
    private int gridSize;
    private int gridSpacing;
    private int swapCount;
    private int firstIndex;
    private int secondIndex;
    private JLabel swapCountLabel;
    private JLabel timeLabel;
    private long startTime;
    private JPanel sortingPanel;
    private JPanel leftPanel;
    private JTextField inputTextField;
    private JButton generateButton;
    private JButton bubbleSortButton;
    private JButton pancakeSortButton;
    private JButton selectionSortButton;
    private JButton cocktailSortButton;
    private JButton radixSortButton;
    private boolean isGenerated;

    public SortingCaseStudy() {
        this.isSorted = false;
        this.gridSize = 0;
        this.gridSpacing = 5;
        isGenerated = false;

        setTitle("Sorting Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLayout(new BorderLayout());

        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel valueLabel = new JLabel("Value:");
        leftPanel.add(valueLabel);
        inputTextField = new JTextField(10);
        inputTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, inputTextField.getPreferredSize().height));
        leftPanel.add(inputTextField);
        leftPanel.add(Box.createVerticalStrut(10));

        generateButton = new JButton("Generate");
        generateButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, generateButton.getPreferredSize().height));
        generateButton.setBackground(new Color(0x33CCFF));
        generateButton.addActionListener(e -> generateUnsortedCircles());
        leftPanel.add(generateButton);
        leftPanel.add(Box.createVerticalStrut(50));

        bubbleSortButton = new JButton("Bubble Sort");
        bubbleSortButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, bubbleSortButton.getPreferredSize().height));
        bubbleSortButton.setBackground(new Color(0x33CCFF));
        bubbleSortButton.addActionListener(e -> sort("Bubble Sort", this::bubbleSort));
        leftPanel.add(bubbleSortButton);
        leftPanel.add(Box.createVerticalStrut(10));

        pancakeSortButton = new JButton("Pancake Sort");
        pancakeSortButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, pancakeSortButton.getPreferredSize().height));
        pancakeSortButton.setBackground(new Color(0x33CCFF));
        pancakeSortButton.addActionListener(e -> sort("Pancake Sort", this::pancakeSort));
        leftPanel.add(pancakeSortButton);
        leftPanel.add(Box.createVerticalStrut(10));

        selectionSortButton = new JButton("Selection Sort");
        selectionSortButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, selectionSortButton.getPreferredSize().height));
        selectionSortButton.setBackground(new Color(0x33CCFF));
        selectionSortButton.addActionListener(e -> sort("Selection Sort", this::selectionSort));
        leftPanel.add(selectionSortButton);
        leftPanel.add(Box.createVerticalStrut(10));

        cocktailSortButton = new JButton("Cocktail Sort");
        cocktailSortButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, cocktailSortButton.getPreferredSize().height));
        cocktailSortButton.setBackground(new Color(0x33CCFF));
        cocktailSortButton.addActionListener(e -> sort("Cocktail Sort", this::cocktailSort));
        leftPanel.add(cocktailSortButton);
        leftPanel.add(Box.createVerticalStrut(10));

        radixSortButton = new JButton("Radix Sort");
        radixSortButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, radixSortButton.getPreferredSize().height));
        radixSortButton.setBackground(new Color(0x33CCFF));
        radixSortButton.addActionListener(e -> sort("Radix Sort", this::radixSort));
        leftPanel.add(radixSortButton);
        leftPanel.add(Box.createVerticalStrut(50));

        swapCountLabel = new JLabel("Swap Count: 0");
        leftPanel.add(swapCountLabel);

        timeLabel = new JLabel("Time Elapsed: 00:00");
        leftPanel.add(timeLabel);

        add(leftPanel, BorderLayout.WEST);

        sortingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
        
                if (array == null || array.length == 0) {
                    return; 
                }
        
                Graphics2D g2d = (Graphics2D) g;
        
                int radius = 5;
                int startX = (getWidth() - (gridSize * gridSpacing)) / 2;
                int startY = (getHeight() - (gridSize * gridSpacing)) / 2;
        
                for (int i = 0; i < array.length; i++) {
                    int x = startX + (array[i] * gridSpacing);
                    int y = startY + (i * gridSpacing);
        
                    if (isSorted) {
                        g2d.setColor(new Color(0xCCFF33));
                    } else if (i == firstIndex || i == secondIndex) {
                        g2d.setColor(new Color(0xFF33CC));
                    } else {
                        g2d.setColor(new Color(0x33CCFF));
                    }
        
                    g2d.fillOval(x - radius, y - radius, radius * 2, radius * 2);
                    g2d.setColor(Color.BLACK);
                    g2d.drawOval(x - radius, y - radius, radius * 2, radius * 2);
                }
            }

        };

        add(sortingPanel, BorderLayout.CENTER);
        bubbleSortButton.setEnabled(false);
        pancakeSortButton.setEnabled(false);
        selectionSortButton.setEnabled(false);
        cocktailSortButton.setEnabled(false);
        radixSortButton.setEnabled(false);
        setVisible(true);
        
    }

    private void generateUnsortedCircles() {
        String input = inputTextField.getText();
        try {
            int numCircles = Integer.parseInt(input);
            if (numCircles > 0) {
                gridSize = numCircles;
                array = new int[numCircles];

                for (int i = 0; i < numCircles; i++) {
                    array[i] = i + 1;
                }
                List<Integer> list = Arrays.asList(Arrays.stream(array).boxed().toArray(Integer[]::new));
                Collections.shuffle(list);
                array = list.stream().mapToInt(Integer::intValue).toArray();

                isGenerated = true;
                isSorted = false; 
                sortingPanel.repaint();
                bubbleSortButton.setEnabled(true);
                pancakeSortButton.setEnabled(true);
                selectionSortButton.setEnabled(true);
                cocktailSortButton.setEnabled(true);
                radixSortButton.setEnabled(true);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a positive integer.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void sort(String sortName, Runnable sortingAlgorithm) {
        if(isGenerated){
            if (array != null) {
            bubbleSortButton.setEnabled(false);
            pancakeSortButton.setEnabled(false);
            selectionSortButton.setEnabled(false);
            cocktailSortButton.setEnabled(false);
            radixSortButton.setEnabled(false);
            generateButton.setEnabled(false);
            startTime = System.currentTimeMillis();
            new Thread(() -> {
                sortingAlgorithm.run();
                generateButton.setEnabled(true);
            }).start();
        }
        }
        
    }

    private void bubbleSort() {
        int n = array.length;
        swapCount = 0;
        startTime = System.currentTimeMillis();

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                firstIndex = j;
                secondIndex = j + 1;

                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    swapCount++;

                    sortingPanel.repaint();

                    updateSwapCountAndTime();

                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        isGenerated = false;
        isSorted = true;
        sortingPanel.repaint();
    }

    

    private void pancakeSort() {
        int n = array.length;
        swapCount = 0;
        startTime = System.currentTimeMillis();

        for (int i = n - 1; i > 0; i--) {
            int maxIndex = findMaxIndex(i);
            if (maxIndex != i) {
                flip(maxIndex);
                flip(i);
            }
        }
        isGenerated = false;
        isSorted = true;
        sortingPanel.repaint();
    }

    private int findMaxIndex(int end) {
        int maxIndex = 0;
        for (int i = 1; i <= end; i++) {
            if (array[i] > array[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    private void flip(int end) {
        int start = 0;
        while (start < end) {
            int temp = array[start];
            array[start] = array[end];
            array[end] = temp;

            firstIndex = start;
            secondIndex = end;
            swapCount++;

            sortingPanel.repaint();

            updateSwapCountAndTime();

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            start++;
            end--;
        }
    }

   

    private void selectionSort() {
        int n = array.length;
        swapCount = 0;
        startTime = System.currentTimeMillis();

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                firstIndex = j;
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                int temp = array[i];
                array[i] = array[minIndex];
                array[minIndex] = temp;
                swapCount++;
                sortingPanel.repaint();
                updateSwapCountAndTime();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        isGenerated = false;
        isSorted = true;
        sortingPanel.repaint();
    }

    

    private void cocktailSort() {
        int n = array.length;
        swapCount = 0;
        startTime = System.currentTimeMillis();

        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < n - 1; i++) {
                firstIndex = i;
                secondIndex = i + 1;

                if (array[i] > array[i + 1]) {
                    int temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    swapped = true;
                    swapCount++;

                    sortingPanel.repaint();

                    updateSwapCountAndTime();

                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            if (!swapped) {
                break;
            }

            swapped = false;
            for (int i = n - 2; i >= 0; i--) {
                firstIndex = i;
                secondIndex = i + 1;

                if (array[i] > array[i + 1]) {
                    int temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    swapped = true;
                    swapCount++;

                    sortingPanel.repaint();

                    updateSwapCountAndTime();

                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } while (swapped);
        isGenerated = false;
        isSorted = true;
        sortingPanel.repaint();
    }
    

    private void radixSort() {
        int maxVal = getMaxValue();

        for (int exp = 1; maxVal / exp > 0; exp *= 10) {
            countingSort(exp);
        }
        isGenerated = false;
        isSorted = true;
        sortingPanel.repaint();
    }

    private void countingSort(int exp) {
        int n = array.length;
        int[] output = new int[n];
        int[] count = new int[10];
        Arrays.fill(count, 0);

        for (int i = 0; i < n; i++) {
            count[(array[i] / exp) % 10]++;
        }

        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            output[count[(array[i] / exp) % 10] - 1] = array[i];
            count[(array[i] / exp) % 10]--;
        }

        for (int i = 0; i < n; i++) {
            array[i] = output[i];
            firstIndex = i;
            swapCount++;
            sortingPanel.repaint();
            updateSwapCountAndTime();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private int getMaxValue() {
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

    private void updateSwapCountAndTime() {
        swapCountLabel.setText("Swap Count: " + swapCount);
        swapCountLabel.setForeground(Color.RED); 
    
        long currentTime = System.currentTimeMillis();
        long elapsedTimeMillis = currentTime - startTime;
    
        long seconds = (elapsedTimeMillis / 1000) % 60;
        long minutes = (elapsedTimeMillis / (1000 * 60)) % 60;
    
        
        String timeString = String.format("%02d:%02d", minutes, seconds);
        timeLabel.setText("Time Elapsed: " + timeString);
        timeLabel.setForeground(Color.RED); 
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SortingCaseStudy().setVisible(true);
        });
    }
}