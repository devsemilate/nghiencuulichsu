package s3.lamphan.nghiencuulichsu.domain.persistence;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import rx.Observable;
import rx.functions.Func1;
import s3.lamphan.nghiencuulichsu.domain.persistence.models.BookRealmObject;
import s3.lamphan.nghiencuulichsu.domain.persistence.models.TopicRealmObject;
import s3.lamphan.nghiencuulichsu.domain.persistence.rx.RealmObserable;
import s3.lamphan.nghiencuulichsu.mvp.models.Book;
import s3.lamphan.nghiencuulichsu.mvp.models.Topic;

/**
 * Created by lam.phan on 11/8/2016.
 */
public class BookDbHelper {
    private Context context;

    public BookDbHelper(Context context) {
        this.context = context;
    }

    public Observable<Boolean> addNewBooks(final List<Book> bookList)
    {
        return RealmObserable.object(context, new Func1<Realm, RealmObject>() {
            @Override
            public RealmObject call(Realm realm) {
                for (Book book : bookList) {
                    BookRealmObject bookRO = BookRealmObject.convertFromBookModel(book);
                    realm.copyToRealmOrUpdate(bookRO);
                }
                return new BookRealmObject();
            }
        }).map(new Func1<RealmObject, Boolean>() {
            @Override
            public Boolean call(RealmObject realmObject) {
                return true;
            }
        });
    }

    public Observable<List<Book>> getBookList(final String branchId)
    {
        return RealmObserable.result(context, new Func1<Realm, RealmResults<BookRealmObject>>() {
            @Override
            public RealmResults<BookRealmObject> call(Realm realm) {
                return realm.where(BookRealmObject.class)
                        .equalTo("branch", branchId)
                        .findAll();
            }
        }).map(new Func1<RealmResults<BookRealmObject>, List<Book>>() {
            @Override
            public List<Book> call(RealmResults<BookRealmObject> bookRealmObjects) {
                List<Book> result = new ArrayList<Book>();
                for(BookRealmObject bro : bookRealmObjects)
                {
                    result.add(BookRealmObject.convertToBookModel(bro));
                }
                return result;
            }
        });
    }

    public Observable<Boolean> updateBookDownloadStatus(final Book book)
    {
        return RealmObserable.object(context, new Func1<Realm, RealmObject>() {
            @Override
            public RealmObject call(Realm realm) {
                BookRealmObject bookRO = realm.where(BookRealmObject.class)
                        .equalTo("id", book.getId())
                        .findFirst();
                bookRO.setStatusDownload(book.getStatusDownload());
                bookRO.setLocalDownloadPath(book.getLocalDownloadPath());
                return realm.copyToRealmOrUpdate(bookRO);
            }
        }).map(new Func1<RealmObject, Boolean>() {
            @Override
            public Boolean call(RealmObject realmObject) {
                return true;
            }
        });
    }

    public Observable<List<Book>> merBookDownloadStatus(final List<Book> books)
    {
        return RealmObserable.list(context, new Func1<Realm, RealmList<BookRealmObject>>() {
            @Override
            public RealmList<BookRealmObject> call(Realm realm) {
                RealmList<BookRealmObject> bookROList = new RealmList<BookRealmObject>();
                for (Book book : books) {
                    BookRealmObject bookROU = realm.where(BookRealmObject.class)
                            .equalTo("id", book.getId())
                            .findFirst();
                    if (bookROU != null) {
                        BookRealmObject.merBook(bookROU, book);
                        realm.copyToRealmOrUpdate(bookROU);
                        bookROList.add(bookROU);
                    } else {
                        BookRealmObject bookROA = BookRealmObject.convertFromBookModel(book);
                        realm.copyToRealmOrUpdate(bookROA);
                        bookROList.add(bookROA);
                    }
                }
                return bookROList;
            }
        }).map(new Func1<RealmList<BookRealmObject>, List<Book>>() {
            @Override
            public List<Book> call(RealmList<BookRealmObject> bookRealmObjects) {
                List<Book> result = new ArrayList<Book>();
                for (BookRealmObject bookRO : bookRealmObjects) {
                    result.add(BookRealmObject.convertToBookModel(bookRO));
                }

                return result;
            }
        });
    }
}
