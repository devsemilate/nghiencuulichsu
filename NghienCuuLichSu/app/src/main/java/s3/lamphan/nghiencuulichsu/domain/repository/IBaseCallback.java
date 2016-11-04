package s3.lamphan.nghiencuulichsu.domain.repository;

import java.util.List;

/**
 * Created by lam.phan on 11/2/2016.
 */
public interface IBaseCallback<T> {
    void success(List<T> results);
    void error(String message);
}
