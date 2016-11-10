package s3.lamphan.nghiencuulichsu.mvp.models;

/**
 * Created by lam.phan on 11/8/2016.
 */
public class DownloadBook {
    Book book;
    long downloadReference;
    int state;
    int progress;

    public DownloadBook(Book book) {
        this.book = book;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public long getDownloadReference() {
        return downloadReference;
    }

    public void setDownloadReference(long downloadReference) {
        this.downloadReference = downloadReference;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
