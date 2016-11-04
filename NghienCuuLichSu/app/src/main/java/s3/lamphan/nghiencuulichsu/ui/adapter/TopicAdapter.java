package s3.lamphan.nghiencuulichsu.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import s3.lamphan.nghiencuulichsu.R;
import s3.lamphan.nghiencuulichsu.mvp.models.Topic;
import s3.lamphan.nghiencuulichsu.utils.StringUtil;

/**
 * Created by lam.phan on 11/4/2016.
 */
public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder>{
    private Context context;
    private List<Topic> data;
    private ITopicItemClickListener iTopicItemClickListener;

    public TopicAdapter(Context context, List<Topic> data, ITopicItemClickListener _iTopicItemClickListener) {
        this.context = context;
        this.data = data;
        if(this.data == null)
        {
            this.data = new ArrayList<>();
        }
        this.iTopicItemClickListener = _iTopicItemClickListener;
    }

    @Override
    public TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_item, parent, false);
        return new TopicViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TopicViewHolder holder, final int position) {
        if(position < data.size())
        {
            holder.bindData(this.context, data.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(iTopicItemClickListener != null)
                    {
                        iTopicItemClickListener.onClick(data.get(position));
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class TopicViewHolder extends RecyclerView.ViewHolder
    {
        @Bind(R.id.tvTitle)
        TextView tvTitle;
        @Bind(R.id.imvCover)
        ImageView imvCover;
        @Bind(R.id.tvDescription)
        TextView tvDescription;

        public TopicViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(Context context, Topic topic)
        {
            tvTitle.setText(topic.getName());
            if(!StringUtil.isStringEmpty(topic.getCover())){
                Picasso.with(context).load(topic.getCover())
                        .error(R.drawable.phoenix)
                        .fit().centerCrop()
                        .into(imvCover);
            }
            tvDescription.setText(topic.getDescription());
        }
    }

    public interface ITopicItemClickListener
    {
        void onClick(Topic selectedTopic);
    }
}
