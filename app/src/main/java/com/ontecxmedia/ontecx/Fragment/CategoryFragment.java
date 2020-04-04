package com.ontecxmedia.ontecx.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ontecxmedia.ontecx.R;
import com.ontecxmedia.ontecx.adapters.CategoryGridListAdapter;
import com.ontecxmedia.ontecx.adapters.GeneralNewsListAdapter;
import com.ontecxmedia.ontecx.model.CategoryModel;
import com.ontecxmedia.ontecx.model.CategoryModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CategoryFragment extends Fragment {



    @BindView(R.id.grid_layout)
    GridView grid_layout;

    private CategoryGridListAdapter categoryGridListAdapter;
    private List<CategoryModel> categoryModelList;
    
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, view);


        categoryModelList = new ArrayList<>();
        insertElement();


        categoryGridListAdapter = new CategoryGridListAdapter(getContext(),categoryModelList);
        grid_layout.setAdapter(categoryGridListAdapter);




        return view;
    }


    private void insertElement() {
//        referencing drawable for the logo
        int[] images = new int[]{
                R.drawable.im_1,
                R.drawable.im_2,
                R.drawable.im_3,
                R.drawable.im_4,
                R.drawable.im_5,
                R.drawable.wakanow_banner
        };

        CategoryModel m = new CategoryModel("StartUp", images[0]);
        categoryModelList.add(m);

        m = new CategoryModel("Technology and Gadget", images[1]);
        categoryModelList.add(m);

        m = new CategoryModel("Funding and Legal", images[2]);
        categoryModelList.add(m);

        m = new CategoryModel("Government in Tech", images[3]);
        categoryModelList.add(m);

        m = new CategoryModel("LifeStyle", images[4]);
        categoryModelList.add(m);

        m = new CategoryModel("Reviews and Deals", images[5]);
        categoryModelList.add(m);

        m = new CategoryModel("Events", images[4]);
        categoryModelList.add(m);

        m = new CategoryModel("Technology Job", images[5]);
        categoryModelList.add(m);

    }


}
