import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;

public class Note {
    private String title;
    private String text;
    private String dateCreated;
    private String dateModified;
    private List<String> imagePaths;

    public Note() {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("E, dd-MMM-yyyy HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        this.dateCreated = formattedDate;
        this.dateModified = formattedDate;
        this.imagePaths = new ArrayList<>();
    }

    public Note(String title) {
        this.title = title;
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("E, dd-MMM-yyyy HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        this.dateCreated = formattedDate;
        this.dateModified = formattedDate;
        this.imagePaths = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getDateModified() {
        return dateModified;
    }

    public List<String> getImagePaths() {
        return imagePaths;
    }

    public void setTitle(String title) {
        this.title = title;
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("E, dd-MMM-yyyy HH:mm:ss");
        this.dateModified = myDateObj.format(myFormatObj);
    }

    public void setText(String text) {
        this.text = text;
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("E, dd-MMM-yyyy HH:mm:ss");
        this.dateModified = myDateObj.format(myFormatObj);
    }

    public void addImagePath(String imagePath) {
        this.imagePaths.add(imagePath);
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("E, dd-MMM-yyyy HH:mm:ss");
        this.dateModified = myDateObj.format(myFormatObj);
    }

    public void setImagePaths(List<String> imagePaths) {
        this.imagePaths = imagePaths;
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("E, dd-MMM-yyyy HH:mm:ss");
        this.dateModified = myDateObj.format(myFormatObj);
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return this.title;
    }
}



