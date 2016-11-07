package s3.lamphan.nghiencuulichsu.mvp.presenters;

import android.content.Context;

import java.util.concurrent.atomic.AtomicBoolean;

import s3.lamphan.nghiencuulichsu.domain.repository.BookRepository;

/**
 * Created by lam.phan on 11/7/2016.
 */
public class BookPresenter extends BasePresenter{
    private static Object lock = new Object();
    private BookRepository bookRepository;
    private int pageSize;
    private int curPage;
    private AtomicBoolean isGettingBook = new AtomicBoolean(); // false

    public BookPresenter(Context context, int pageSize, int curPage) {
        super(context);
        bookRepository = new BookRepository();
        addRepository(bookRepository);
        this.pageSize = pageSize;
        this.curPage = curPage;
    }



}
