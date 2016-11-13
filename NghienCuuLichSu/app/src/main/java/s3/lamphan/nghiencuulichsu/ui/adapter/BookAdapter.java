package s3.lamphan.nghiencuulichsu.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import s3.lamphan.nghiencuulichsu.R;
import s3.lamphan.nghiencuulichsu.mvp.models.Book;
import s3.lamphan.nghiencuulichsu.utils.StringUtil;

/**
 * Created by lam.phan on 11/8/2016.
 */
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder>{
    private Context context;
    private List<Book> data;
    private IBookItemClickListener iBookItemClickListener;

    public BookAdapter(Context context, List<Book> data, IBookItemClickListener _iBookItemClickListener) {
        this.context = context;
        this.data = data;
        if(this.data == null){
            this.data = new ArrayList<>();
        }
        this.iBookItemClickListener = _iBookItemClickListener;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item,
                parent, false);
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        if(position < data.size()) {
            Book book = data.get(position);
            holder.bindData(context, book, iBookItemClickListener);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder
    {
        @Bind(R.id.tvTitle)
        TextView tvTitle;
        @Bind(R.id.tvAuthor)
        TextView tvAuthor;
        @Bind(R.id.imvCover)
        ImageView imvCover;
        @Bind(R.id.tvDescription)
        TextView tvDescription;
        @Bind(R.id.btGetBook)
        Button btGetBook;

        public BookViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(Context context, final Book book, final IBookItemClickListener listener)
        {
            tvTitle.setText(book.getName());
            tvAuthor.setText(book.getAuthor());
            if(!StringUtil.isStringEmpty(book.getCover())){
                Picasso.with(context).load(book.getCover())
                        .error(R.drawable.phoenix)
                        .fit().centerCrop()
                        .into(imvCover);
            }
            tvDescription.setText(book.getDescription());
            btGetBook.setText(book.getStatusDownloadDes());
            btGetBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null)
                    {
                        listener.onBookItemClick(book);
                    }
                }
            });
        }
    }

    public interface IBookItemClickListener
    {
        void onBookItemClick(Book book);
    }
}
