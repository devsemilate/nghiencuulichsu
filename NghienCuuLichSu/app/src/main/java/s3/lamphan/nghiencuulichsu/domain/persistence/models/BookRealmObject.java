package s3.lamphan.nghiencuulichsu.domain.persistence.models;

import android.app.DownloadManager;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import s3.lamphan.nghiencuulichsu.mvp.models.Book;

/**
 * Created by lam.phan on 11/8/2016.
 */
public class BookRealmObject extends RealmObject{
    @PrimaryKey
    String id;
    String branch;
    String name;
    String author;
    String description;
    String cover;
    String downloadUrl;
    int statusDownload;
    String localDownloadPath;

    public BookRealmObject() {
    }

    public BookRealmObject(String id, String branch, String name,
                           String author, String description,
                           String cover, String downloadUrl) {
        this.id = id;
        this.branch = branch;
        this.name = name;
        this.author = author;
        this.description = description;
        this.cover = cover;
        this.downloadUrl = downloadUrl;
        statusDownload = DownloadManager.STATUS_PAUSED;
        this.localDownloadPath = "";
    }

    public BookRealmObject(String id, String branch, String name, String author,
                           String description, String cover, String downloadUrl,
                           int statusDownload, String localDownloadPath) {
        this.id = id;
        this.branch = branch;
        this.name = name;
        this.author = author;
        this.description = description;
        this.cover = cover;
        this.downloadUrl = downloadUrl;
        this.statusDownload = statusDownload;
        this.localDownloadPath = localDownloadPath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public int getStatusDownload() {
        return statusDownload;
    }

    public void setStatusDownload(int statusDownload) {
        this.statusDownload = statusDownload;
    }

    public String getLocalDownloadPath() {
        return localDownloadPath;
    }

    public void setLocalDownloadPath(String localDownloadPath) {
        this.localDownloadPath = localDownloadPath;
    }

    public static BookRealmObject convertFromBookModel(Book book)
    {
        return new BookRealmObject(book.getId(), book.getBranch(), book.getName(),
                book.getAuthor(), book.getDescription(),
                book.getCover(), book.getDownloadUrl(),
                book.getStatusDownload(), book.getLocalDownloadPath());
    }

    public static Book convertToBookModel(BookRealmObject bookRealmObject)
    {
        return new Book(bookRealmObject.getId(), bookRealmObject.getBranch(),
                bookRealmObject.getName(), bookRealmObject.getAuthor(),
                bookRealmObject.getDescription(), bookRealmObject.getCover(),
                bookRealmObject.getDownloadUrl(), bookRealmObject.getStatusDownload(),
                bookRealmObject.getLocalDownloadPath());
    }

    public static void merBook(BookRealmObject bookRO, Book book)
    {
        bookRO.setBranch(book.getBranch());
        bookRO.setName(book.getName());
        bookRO.setAuthor(book.getAuthor());
        bookRO.setDescription(book.getDescription());
        bookRO.setCover(book.getCover());
    }
}
