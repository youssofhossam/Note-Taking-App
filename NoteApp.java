import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class NoteApp {
    private JFrame frame;
    private JTextArea textArea;
    private JTextField titleField;
    private JPanel sidebar, imageDisplayPanel, titlePanel, imagePanel, buttonsPanel;//notes-area
    private JButton saveButton, deleteButton, newNoteButton, addImageButton, logOut, sketchButton;
    private DefaultListModel<Note> noteListModel;//sidebar
    private JList<Note> noteList;//for the previous notes
    private String currentUser;
    private User currUser;
    private Note currentNote;

    public NoteApp(User user) {
        this.currentNote = new Note();
        start();
        this.currUser = user;
        loadUserNotes();
    }

    public NoteApp() {
        currentNote = new Note();
        start();
        selectUser();
    }

    private void start() {
        Color backgroundColor = new Color(255, 255, 255, 255);
        Color titleBarColor = new Color(40, 42, 54);
        Color buttonColor = new Color(76, 176, 80);
        Color buttonTextColor = new Color(0, 0, 0);
        Color text = new Color(0, 0, 0);

        frame = new JFrame("Note App");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(backgroundColor);
        ImageIcon appIcon = new ImageIcon("D:/Faculty/Second Year/1st Semester/OOP/Note Taking App/icon.jpg");
        frame.setIconImage(appIcon.getImage());

        sidebar = new JPanel(new BorderLayout());
        sidebar.setBackground(titleBarColor);
        sidebar.setPreferredSize(new Dimension(120, frame.getHeight()));

        noteListModel = new DefaultListModel<>();
        noteList = new JList<>(noteListModel);
        noteList.setBackground(backgroundColor);
        noteList.setForeground(text);
        noteList.setFont(new Font("Arial", Font.BOLD, 14));
        sidebar.add(new JScrollPane(noteList), BorderLayout.CENTER);

        noteList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = noteList.locationToIndex(e.getPoint());
                if (index == -1) {
                    return;
                }
                noteList.setSelectedIndex(index);
                Note selectedNote = noteList.getSelectedValue();
                if (e.getClickCount() == 2) {
                    openNoteForEditing(selectedNote);
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    showNotePopupMenu(e);
                }
            }
        });
        frame.add(sidebar, BorderLayout.WEST);

        // Title Panel
        titlePanel = new JPanel(new BorderLayout());
        titleField = new JTextField();
        titlePanel.add(titleField, BorderLayout.CENTER);
        titleField.setPreferredSize(new Dimension(frame.getWidth(), 30));
        titleField.setText("Title");
        titleField.setFont(new Font("Arial", Font.BOLD, 18));
        titleField.setBackground(backgroundColor);
        titleField.setForeground(text);
        titleField.setCaretColor(text);
        frame.add(titlePanel, BorderLayout.NORTH);

        // Text Area
        textArea = new JTextArea();
        textArea.setBackground(backgroundColor);
        textArea.setText("Content");
        textArea.setFont(new Font("Arial", Font.BOLD, 16));
        textArea.setForeground(text);
        textArea.setCaretColor(text);
        JScrollPane textScrollPane = new JScrollPane(textArea);
        frame.add(textScrollPane, BorderLayout.CENTER);

        // Image Display Panel
        imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBackground(backgroundColor);
        imageDisplayPanel = new JPanel();
        imageDisplayPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        imageDisplayPanel.setPreferredSize(new Dimension(280, frame.getHeight()));
        imageDisplayPanel.setBackground(backgroundColor);
        imagePanel.add(new JScrollPane(imageDisplayPanel), BorderLayout.CENTER);
        frame.add(imagePanel, BorderLayout.EAST);

        // Buttons Panel
        buttonsPanel = new JPanel();
        buttonsPanel.setBackground(backgroundColor);
        saveButton = new JButton("Save");
        deleteButton = new JButton("Delete");
        newNoteButton = new JButton("New Note");
        addImageButton = new JButton("Attach Photo");
        sketchButton = new JButton("Add Sketch");

        logOut = new JButton("Log Out");
        styleButton(saveButton, buttonColor, buttonTextColor);
        styleButton(deleteButton, buttonColor, buttonTextColor);
        styleButton(newNoteButton, buttonColor, buttonTextColor);
        styleButton(addImageButton, buttonColor, buttonTextColor);
        styleButton(sketchButton, buttonColor, buttonTextColor);

        styleButton(logOut, buttonColor, buttonTextColor);
        buttonsPanel.add(saveButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(newNoteButton);
        buttonsPanel.add(addImageButton);
        buttonsPanel.add(sketchButton);
        buttonsPanel.add(logOut);
        frame.add(buttonsPanel, BorderLayout.SOUTH);

        // Add Listeners
        addImageButton.addActionListener(e -> chooseImage());
        saveButton.addActionListener(e -> saveNote());
        deleteButton.addActionListener(e -> deleteNote());
        newNoteButton.addActionListener(e -> createNewNote());
        sketchButton.addActionListener(e -> {
            try {
                writeSketch();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        logOut.addActionListener(e -> goToLoginScreen());

        frame.setVisible(true);
    }

    private void styleButton(JButton button, Color background, Color foreground) {
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
    }

    private void goToLoginScreen() {
        frame.dispose();
        new LoginScreen();
    }

    private void chooseImage() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String imagePath = selectedFile.getAbsolutePath();
            currentNote.addImagePath(imagePath);
            addImageToPanel(imagePath);
        }
    }

    private void writeSketch() throws IOException {
        Runtime.getRuntime().exec("mspaint");
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String imagePath = selectedFile.getAbsolutePath();
            currentNote.addImagePath(imagePath);
            addImageToPanel(imagePath);
        }
    }

    private void addImageToPanel(String imagePath) {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image scaledImage = imageIcon.getImage().getScaledInstance(250, 200, Image.SCALE_SMOOTH);

        imagePanel = new JPanel();
        imagePanel.setLayout(new BorderLayout());
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imagePanel.add(imageLabel, BorderLayout.CENTER);

        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteMenuItem = new JMenuItem("Delete Image");
        popupMenu.add(deleteMenuItem);

        deleteMenuItem.addActionListener(e -> {
            imageDisplayPanel.remove(imagePanel);
            currentNote.getImagePaths().remove(imagePath);
            imageDisplayPanel.revalidate();
            imageDisplayPanel.repaint();
        });

        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) popupMenu.show(imageLabel, e.getX(), e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) popupMenu.show(imageLabel, e.getX(), e.getY());
            }
        });

        imageDisplayPanel.add(imagePanel);
        imageDisplayPanel.revalidate();
        imageDisplayPanel.repaint();
    }

    private void saveNote() {
        currentNote.setTitle(titleField.getText());
        currentNote.setText(textArea.getText());
        try {
            FileManager.saveNoteToFile(currentNote, currUser);
            // Check if the note already exists in the list
            if (!noteListModel.contains(currentNote)) {
                noteListModel.addElement(currentNote);
            }

            JOptionPane.showMessageDialog(frame, "Note saved successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Failed to save note: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteNote() {
        Note selectedNote = noteList.getSelectedValue();
        if (selectedNote == null) {
            JOptionPane.showMessageDialog(frame, "No note selected.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this note?");
        if (confirm == JOptionPane.YES_OPTION) {
            FileManager.deleteNote(currUser, selectedNote);
            noteListModel.removeElement(selectedNote);

            if (currentNote.equals(selectedNote)) {
                createNewNote();
            }
        }
    }

    private void createNewNote() {
        String[] options = {"Standard Note", "Secure Note"};
        int choice = JOptionPane.showOptionDialog(frame, "What type of note do you want to create?", "New Note",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        if (choice == 0) {
            currentNote = new Note();
        } else if (choice == 1) {
            JPasswordField passwordField = new JPasswordField();
            Object[] message = {"Enter password for the secure note:", passwordField};
            int option = JOptionPane.showConfirmDialog(frame, message, "Secure Note Password", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String password = new String(passwordField.getPassword());
                if (!password.trim().isEmpty()) currentNote = new SecureNote("", password);
                else
                    JOptionPane.showMessageDialog(frame, "Password cannot be empty for a secure note.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        titleField.setText("Title");
        textArea.setText("Content");
        imageDisplayPanel.removeAll();
        imageDisplayPanel.revalidate();
        imageDisplayPanel.repaint();
    }

    private void openNoteForEditing(Note note) {
        try {
            String noteTitle = note.getTitle();
            File noteFile = new File(FileManager.main_folder + currUser.getUsername() + "/" + noteTitle + ".txt");
            Note notee = FileManager.loadNoteFromFile(noteFile);

            if (notee instanceof SecureNote) {
                JPasswordField passwordField = new JPasswordField();
                Object[] message = {"Enter The Password", passwordField};
                int option = JOptionPane.showConfirmDialog(frame, message, "Secure Note Password", JOptionPane.OK_CANCEL_OPTION);
                String inputPassword = new String(passwordField.getPassword());
                if (option == JOptionPane.OK_OPTION && ((SecureNote) notee).checkPassword(inputPassword)) {
                    loadNoteIntoEditor(notee);
                } else {
                    JOptionPane.showMessageDialog(frame, "Incorrect password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                loadNoteIntoEditor(notee);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Failed to load note: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadNoteIntoEditor(Note note) {
        currentNote = note;
        titleField.setText(note.getTitle());
        textArea.setText(note.getText());
        imageDisplayPanel.removeAll();

        for (String imagePath : note.getImagePaths()) addImageToPanel(imagePath);

        imageDisplayPanel.revalidate();
        imageDisplayPanel.repaint();
    }

    private void showNotePopupMenu(MouseEvent e) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteMenuItem = new JMenuItem("Delete Note");
        deleteMenuItem.addActionListener(actionEvent -> deleteNote());
        popupMenu.add(deleteMenuItem);
        popupMenu.show(noteList, e.getX(), e.getY());
    }

    private void selectUser() {
        String username = JOptionPane.showInputDialog(frame, "Enter your username:");
        while (username == null || username.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Username cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            username = JOptionPane.showInputDialog(frame, "Enter your username:");
        }
        currentUser = username.trim();
        loadUserNotes();
    }

    private void loadUserNotes() {
        noteListModel.clear();

        var userNotes1 = FileManager.getNotesList(currUser);//////////////

        for (Note n : userNotes1) {
            noteListModel.addElement(n);
        }
    }

}



