package s3.lamphan.nghiencuulichsu.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by lam.phan on 11/7/2016.
 */
public class RecyclerViewScrollBehavior {
    public static boolean isScrollingOverMiddle(LinearLayoutManager layoutManager,
                                                int totalItems)
    {
        int curFirstPos = layoutManager.findFirstVisibleItemPosition();
        return curFirstPos >= totalItems/2;
    }
}
