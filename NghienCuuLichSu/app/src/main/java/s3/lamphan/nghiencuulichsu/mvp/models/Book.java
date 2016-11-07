package s3.lamphan.nghiencuulichsu.mvp.models;

import java.util.ArrayList;
import java.util.List;

import s3.lamphan.nghiencuulichsu.domain.repository.restModels.BookData;
import s3.lamphan.nghiencuulichsu.domain.repository.restModels.BookRestModel;

/**
 * Created by lam.phan on 11/7/2016.
 */
public class Book {
    String id;
    String branch;
    String name;
    String author;
    String description;
    String cover;
    String downloadUrl;

    public Book(String id, String branch, String name, String author, String description, String cover, String downloadUrl) {
        this.id = id;
        this.branch = branch;
        this.name = name;
        this.author = author;
        this.description = description;
        this.cover = cover;
        this.downloadUrl = downloadUrl;
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

    public static List<Book> convertFromRestModel(BookRestModel bookRestModel)
    {
        List<Book> bookList = new ArrayList<>();
        if(bookRestModel != null)
        {
            List<BookData> bookDatas = bookRestModel.getData();
            for(BookData bookData : bookDatas)
            {
                bookList.add(new Book(bookData.getId(), bookData.getBranch(), bookData.getAuthor(),
                        bookData.getName(), bookData.getDescription(),
                        bookData.getCover(), bookData.getDownloadUrl()));
            }
        }
        return bookList;
    }
}
