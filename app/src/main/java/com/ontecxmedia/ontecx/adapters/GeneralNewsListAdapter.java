package com.ontecxmedia.ontecx.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ontecxmedia.ontecx.Constant;
import com.ontecxmedia.ontecx.R;
import com.ontecxmedia.ontecx.model.GeneralNewsModel;
import com.ontecxmedia.ontecx.retrofit_interface.ItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GeneralNewsListAdapter  extends RecyclerView.Adapter<GeneralNewsListAdapter.MyViewHolder>{
    private Context context;
    private List<GeneralNewsModel> cardList;


    public GeneralNewsListAdapter(Context context, List<GeneralNewsModel> cardList) {
        this.context = context;
        this.cardList = cardList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list, parent, false);
        ButterKnife.bind(this, view);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        GeneralNewsModel cardOption = cardList.get(i);

        holder.mTitle.setText(cardOption.getTitle());
        holder.mNewsBanner.setImageResource(cardOption.getThumbnail());
        holder.mContent.setText(cardOption.getDesc());
        holder.mAuthor.setText(cardOption.getAuthor());
        holder.mCategory.setText(cardOption.getCategory());

        holder.setItemClickListener(pos -> {


        });
    }

    private void nextActivity(String title, Class productActivityClass) {
        Intent i = new Intent(context, productActivityClass);
        i.putExtra(Constant.CARD_TITLE, title);
        context.startActivity(i);
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /** ButterKnife Code **/
        @BindView(R.id.news_layout)
        LinearLayout mNewsLayout;
        @BindView(R.id.news_banner)
        ImageView mNewsBanner;
        @BindView(R.id.category)
        TextView mCategory;
        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.content)
        TextView mContent;
        @BindView(R.id.author)
        TextView mAuthor;
        /** ButterKnife Code **/

        ItemClickListener itemClickListener;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClick(this.getLayoutPosition());
        }

    }
}
