package com.ontecxmedia.ontecx.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ontecxmedia.ontecx.Constant;
import com.ontecxmedia.ontecx.R;
import com.ontecxmedia.ontecx.model.CategoryModel;
import com.ontecxmedia.ontecx.retrofit_interface.ItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryGridListAdapter extends BaseAdapter {
    private Context context;
    private List<CategoryModel> cardList;
    int[] images;

    LayoutInflater inflter;


    public CategoryGridListAdapter(Context context, List<CategoryModel> cardList) {
        this.context = context;
        this.cardList = cardList;
       // this.images = images;

        inflter = (LayoutInflater.from(context));
    }




    @Override
    public int getCount() {
        return cardList.size();
    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflter.inflate(R.layout.category_list, null); // inflate the layout
        CategoryModel cardOption = cardList.get(position);



        ImageView catImage = convertView.findViewById(R.id.catImage);// get the reference of ImageView
        TextView mCategoryTiTle = convertView.findViewById(R.id.category_title);
        View mViewOver = convertView.findViewById(R.id.viewOver);

        catImage.setImageResource(cardOption.getThumbnail()); // set logo images
        mCategoryTiTle.setText(cardOption.getTitle());
        mViewOver.setBackgroundColor(Color.BLACK);
       // mViewOver.setAlpha((float) 0.8);



        return convertView;
    }



}
