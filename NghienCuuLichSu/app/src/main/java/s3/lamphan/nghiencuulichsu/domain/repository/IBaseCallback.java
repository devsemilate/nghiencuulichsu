package s3.lamphan.nghiencuulichsu.domain.repository;

import java.util.List;

import s3.lamphan.nghiencuulichsu.mvp.presenters.BasePresenter;

/**
 * Created by lam.phan on 11/2/2016.
 */
public interface IBaseCallback<T> {
    void success(List<T> results);
    void error(String message);
    void error(BasePresenter.Error error);
}
