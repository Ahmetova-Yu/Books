import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

class Book implements Serializable {
    private String title;
    private String author;

    public Book (String title, String author) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}

public class Library extends JFrame {
    private List<Book> library = new ArrayList<>();
    private final String DATA_FILE = "library.dat";

    private JTextField titleField;
    private JTextField authorField;
    private JTextField findField;
    private DefaultTableModel tableModel;

    public Library() {

        setTitle("Библиотека управления книгами");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel addPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        addPanel.setBorder(BorderFactory.createTitledBorder("Добавить книгу"));
        titleField = new JTextField();
        authorField = new JTextField();
        JButton addBut = new JButton("+ Добавить");

        addPanel.add(new JLabel("Название"));
        addPanel.add(titleField);
        addPanel.add(new JLabel("Автор"));
        addPanel.add(authorField);
        addPanel.add(new JLabel());
        addPanel.add(addBut);

        addBut.addActionListener(e -> {
            String title = titleField.getText().trim();
            String author = titleField.getText().trim();

            if (!title.isEmpty() && !author.isEmpty()) {
                Book book = new Book(title, author);
                library.add(book);

            } else {
                JOptionPane.showMessageDialog(this, "Заполните все поля!");
            }
        });

        JPanel findPanel = new JPanel(new BorderLayout());
        findField = new JTextField();
        JButton findBut = new JButton("Поиск");

        findPanel.setBorder(BorderFactory.createTitledBorder("Поиск по названию"));
        findPanel.add(findField, BorderLayout.CENTER);
        findPanel.add(findBut, BorderLayout.EAST);

        findBut.addActionListener(e -> {
            String query = findField.getText().toLowerCase().trim();
            List<Book> res = new ArrayList<>();

            for (Book book : library) {
                if (book.getTitle().toLowerCase().contains(query)) {
                    res.add(book);
                }
            }

        });

        String[] cols = {"Название", "Автор"};
        tableModel = new DefaultTableModel(cols, 0);
        JTable table = new JTable(tableModel);


    }

    private void updateTable(List<Book> books) {
        tableModel.setRowCount(0);

        for (Book book : library) {
            tableModel.addRow(new Object[]{book.getTitle(), book.getAuthor()});
        }
    }

    private void loadLibrary() {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            library = (List<Book>) ois.readObject();
        } catch (Exception e) {
            library = new ArrayList<>();
        }
    }

    private void saveLibrary() {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(library);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Ошибка при сохранении данных");
        }
    }

}
