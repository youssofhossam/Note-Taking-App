import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class FileManager {
    public static final String main_folder = "D:/Notes/";

    public static void saveNoteToFile(Note note, User user) throws IOException {
        String username = user.getUsername();
        String user_Directory_Path = main_folder + username;
        File userDirectory = new File(user_Directory_Path);

        if (!userDirectory.exists()) {
            userDirectory.mkdirs();
        }

        File file = new File(user_Directory_Path + "/" + note.getTitle() + ".txt");
        if (file.exists()) file.delete();


        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(note.getText() + "\n");
            for (String imagePath : note.getImagePaths()) {
                writer.write("IMG:" + imagePath + "\n");
            }
            if (note instanceof SecureNote) {
                writer.write("PWD:" + ((SecureNote) note).getPassword() + "\n");
            }
            writer.write("Date:" + note.getDateCreated());
            writer.newLine();
            writer.write("Date:" + note.getDateModified());
            writer.newLine();
        }
    }

    public static Note loadNoteFromFile(File file) throws IOException {
        Note note = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            StringBuilder textContent = new StringBuilder();
            note = new Note(file.getName().replace(".txt", ""));
            String password = null, dateCreated = null, dateModified = null;
            List<String> paths = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("IMG:")) {
                    paths.add(line.substring(4));
                } else if (line.startsWith("PWD:")) {
                    password = line.substring(4);
                    note = new SecureNote(note.getTitle(), password);
                } else if (line.startsWith("Date:")) {
                    dateCreated = line.substring(5).trim();
                    dateModified = reader.readLine().substring(5).trim();
                } else textContent.append(line).append("\n");
            }

            if (password != null)
                ((SecureNote) note).setPassword(password);
            if (!paths.isEmpty())
                note.setImagePaths(paths);
            if (!textContent.isEmpty()) {
                note.setText(textContent.toString().trim());
            }
            note.setDateCreated(dateCreated);
            note.setDateModified(dateModified);
        }
        return note;
    }

    public static boolean deleteNote(User curr, Note note) {
        File file = new File(main_folder + curr.getUsername() + "/" + note.getTitle() + ".txt");
        return file.delete();
    }

    public static List<Note> getNotesList(User user) {
        List<Note> notesList = new ArrayList<>();
        File userFolder = new File(main_folder + user.getUsername());

        if (userFolder.exists() && userFolder.isDirectory()) {
            File[] noteFiles = userFolder.listFiles((dir, name) -> name.endsWith(".txt"));
            if (noteFiles != null) {
                for (File noteFile : noteFiles) {
                    Note note = new Note();
                    try {
                        note = loadNoteFromFile(noteFile);
                    } catch (IOException e) {
                        System.err.println();
                    }
//                    notesList.add(noteFile.getName().replace(".txt", ""));
                    notesList.add(note);
                }
            }
        }

        return notesList;
    }
}