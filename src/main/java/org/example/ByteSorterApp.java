package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Arrays;

public class ByteSorterApp extends JFrame {

    private JButton sortButton;
    private JFileChooser fileChooser;

    public ByteSorterApp() {
        setTitle("Сортировка массива байтов");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        sortButton = new JButton("Выбрать файл и выполнить сортировку");
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseFileAndSort();
            }
        });

        getContentPane().setLayout(new FlowLayout());
        getContentPane().add(sortButton);

        fileChooser = new JFileChooser();
    }

    private void chooseFileAndSort() {
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            File sortedFile = new File(selectedFile.getParent(), "sorted_" + selectedFile.getName());
            try {
                sortFile(selectedFile, sortedFile);
                JOptionPane.showMessageDialog(this, "Сортировка выполнена успешно. Результат сохранен в файле: " + sortedFile.getAbsolutePath());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Ошибка при сортировке: " + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void sortFile(File inputFile, File outputFile) throws IOException {
        try (InputStream inputStream = new FileInputStream(inputFile);
             OutputStream outputStream = new FileOutputStream(outputFile)) {
            byte[] bytes = inputStream.readAllBytes();
            Arrays.sort(bytes);
            outputStream.write(bytes);
        }
    }
}