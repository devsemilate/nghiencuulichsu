package s3.lamphan.nghiencuulichsu.mvp.presenters;

import s3.lamphan.nghiencuulichsu.mvp.models.Branch;

/**
 * Created by lam.phan on 11/8/2016.
 */
public class AppFlowPresenter {
    public static final String TOPIC_BRANCH_TYPE = "topic";
    public static final String BOOK_BRANCH_TYPE = "book";

    public AppFlowPresenter() {
    }

    public static boolean isTopicBranchType(Branch branch)
    {
        if(branch == null)
            return false;
        return branch.getType().equalsIgnoreCase(TOPIC_BRANCH_TYPE);
    }

    public static boolean isBookBranchType(Branch branch)
    {
        if(branch == null)
            return false;
        return branch.getType().equalsIgnoreCase(BOOK_BRANCH_TYPE);
    }
}
