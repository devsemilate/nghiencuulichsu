package s3.lamphan.nghiencuulichsu.mvp.views;

import s3.lamphan.nghiencuulichsu.mvp.models.Book;
import s3.lamphan.nghiencuulichsu.mvp.models.Topic;

/**
 * Created by lam.phan on 11/4/2016.
 */
public interface IMainView {
    void presentContentView(Topic topic);
    void presentDownloadBookView(Book book);
    void openBook(Book book);
}
