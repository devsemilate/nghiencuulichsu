package s3.lamphan.nghiencuulichsu.mvp.models;

import android.app.DownloadManager;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

import s3.lamphan.nghiencuulichsu.domain.repository.restModels.BookData;
import s3.lamphan.nghiencuulichsu.domain.repository.restModels.BookRestModel;

/**
 * Created by lam.phan on 11/7/2016.
 */
@Parcel
public class Book {
    String id;
    String branch;
    String name;
    String author;
    String description;
    String cover;
    String downloadUrl;
    int statusDownload;
    long downloadReference;

    public Book() {
    }

    public Book(String id, String branch, String name, String author, String description, String cover, String downloadUrl) {
        this.id = id;
        this.branch = branch;
        this.name = name;
        this.author = author;
        this.description = description;
        this.cover = cover;
        this.downloadUrl = downloadUrl;
        this.statusDownload = DownloadManager.STATUS_PAUSED;
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

    public long getDownloadReference() {
        return downloadReference;
    }

    public void setDownloadReference(long downloadReference) {
        this.downloadReference = downloadReference;
    }

    public String getStatusDownloadDes()
    {
        String statusDes = "";
        switch (statusDownload)
        {
            case DownloadManager.STATUS_FAILED:
            case DownloadManager.STATUS_PAUSED:
            case DownloadManager.STATUS_PENDING:
                statusDes = "Tải";
                break;
            case DownloadManager.STATUS_RUNNING:
                statusDes = "Đang Tải";
                break;
            case DownloadManager.STATUS_SUCCESSFUL:
                statusDes = "Mở Xem";
                break;
            default:
                break;
        }
        return statusDes;
    }

    public static List<Book> convertFromRestModel(BookRestModel bookRestModel)
    {
        List<Book> bookList = new ArrayList<>();
        if(bookRestModel != null)
        {
            List<BookData> bookDatas = bookRestModel.getData();
            for(BookData bookData : bookDatas)
            {
                bookList.add(new Book(bookData.getId(), bookData.getBranch(), bookData.getName(),
                        bookData.getAuthor(), bookData.getDescription(),
                        bookData.getCover(), bookData.getDownloadUrl()));
            }
        }
        return bookList;
    }
}
