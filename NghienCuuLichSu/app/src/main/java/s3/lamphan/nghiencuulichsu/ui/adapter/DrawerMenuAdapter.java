package s3.lamphan.nghiencuulichsu.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import s3.lamphan.nghiencuulichsu.R;
import s3.lamphan.nghiencuulichsu.mvp.models.Branch;

/**
 * Created by lam.phan on 11/3/2016.
 */
public class DrawerMenuAdapter extends RecyclerView.Adapter<DrawerMenuAdapter.DrawerItemViewHolder>{
    private Context context;
    private List<Branch> menuData;
    protected int selectedPosition = -1;
    private IDrawerItemClickListener itemClickListener;

    public DrawerMenuAdapter(Context context, List<Branch> _menuData, IDrawerItemClickListener itemClickListener) {
        this.context = context;
        this.menuData = _menuData;
        if(menuData == null){
            menuData = new ArrayList<>();
        }
        this.itemClickListener = itemClickListener;
    }

    @Override
    public DrawerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_item, parent, false);
        return new DrawerItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DrawerItemViewHolder holder, final int position) {
        if(position < menuData.size()) {
            holder.bindData(menuData.get(position), itemClickListener);
            if(position == selectedPosition){
                holder.itemView.setBackgroundResource(R.drawable.drawer_item_selected_bg);
            } else {
                holder.itemView.setBackgroundResource(R.drawable.drawer_item_normal_bg);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedPosition = position;
                    notifyDataSetChanged();
                    if(itemClickListener != null)
                    {
                        itemClickListener.onSelected(menuData.get(selectedPosition));
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return menuData.size();
    }

    public static class DrawerItemViewHolder extends RecyclerView.ViewHolder
    {
        @Bind(R.id.rlDrawerItem)
        RelativeLayout rlDrawerItem;
        @Bind(R.id.tvBranchName)
        TextView tvBranchName;

        public DrawerItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(Branch data, IDrawerItemClickListener itemClickListener)
        {
            if(data != null) {
                tvBranchName.setText(data.getName());
            }
        }
    }

    public interface IDrawerItemClickListener
    {
        void onSelected(Branch branchSelected);
    }
}
