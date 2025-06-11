import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

public class Book implements Serializable {
    private String title;
    private String author;

    public Book (String title, String author) {
        this.title = title;
        this.author = author;
    }

    public String getTtitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}
