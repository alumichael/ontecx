package com.ontecxmedia.ontecx.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;

import com.ontecxmedia.ontecx.NetworkConnection;
import com.ontecxmedia.ontecx.R;
import com.ontecxmedia.ontecx.UserPreferences;
import com.ontecxmedia.ontecx.adapters.GeneralNewsListAdapter;
import com.ontecxmedia.ontecx.model.GeneralNewsModel;



import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Fragment_Dashboard extends Fragment implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.dash_layout)
    FrameLayout dash_layout;
    @BindView(R.id.greeting)
    TextView greeting;






    private GeneralNewsListAdapter GeneralNewsListAdapter;
    private List<GeneralNewsModel> GeneralNewsModelList;
    UserPreferences userPreferences;

    Fragment fragment;



   /* @BindView(R.id.slider)
    SliderLayout mSlider;*/

    @BindView(R.id.advert_banner)
    ImageView advert_banner;

    @BindView(R.id.advert_content)
    TextView advert_content;


    //List<History> policy_item;
/*
    public Fragment_Dashboard() {
        // Required empty public constructor
    }*/

    NetworkConnection networkConnection = new NetworkConnection();
    //ApiInterface client = ServiceGenerator.createService(ApiInterface.class);

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Dashboard.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Dashboard newInstance(String param1, String param2) {
        Fragment_Dashboard fragment = new Fragment_Dashboard();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment__dashboard, container, false);
        ButterKnife.bind(this, view);

        userPreferences = new UserPreferences(getActivity());

        GeneralNewsModelList = new ArrayList<>();

        GeneralNewsListAdapter = new GeneralNewsListAdapter(getContext(), GeneralNewsModelList);


        insertElement();

        greeting.setText("Hello, \nMichael Boluwaji.");




        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(GeneralNewsListAdapter);


       // setAction();
       // getHistory();

       // SLide();
/*
        mTrasactnNotifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new TransactionHistoryFragment();
                showFragment(fragment);
            }
        });*/


        return view;
    }


    private void insertElement() {
//        referencing drawable for the logo
        int[] images = new int[]{
                R.drawable.im_1,
                R.drawable.im_2,
                R.drawable.im_3,
                R.drawable.im_4,
                R.drawable.im_5
        };

        GeneralNewsModel m = new GeneralNewsModel("Tech enthusiasm travel to Germany", images[0],
                "The recent published update about coronal virus reveals...","Michael Gregory","LifeStyle");
        GeneralNewsModelList.add(m);

         m = new GeneralNewsModel("Tech enthusiasm travel to Germany", images[1],
                "The recent published update about coronal virus reveals...","James Bog","Tech");
        GeneralNewsModelList.add(m);

         m = new GeneralNewsModel("Tech enthusiasm travel to Germany", images[2],
                "The recent published update about coronal virus reveals...","Frank Jod","Events");
        GeneralNewsModelList.add(m);

         m = new GeneralNewsModel("Tech enthusiasm travel to Germany", images[3],
                "The recent published update about coronal virus reveals...","Tony Gregory","Technology and Gadget");
        GeneralNewsModelList.add(m);

         m = new GeneralNewsModel("Tech enthusiasm travel to Germany", images[4],
                "The recent published update about coronal virus reveals...","Antony Joshua","StartUp");
        GeneralNewsModelList.add(m);



    }

   /* private void getNews() {


        //get client and call object for request

        Call<TransactionHead> call = client.transaction_hist("Token " + userPreferences.getUserToken());
        call.enqueue(new Callback<TransactionHead>() {
            @Override
            public void onResponse(Call<TransactionHead> call, Response<TransactionHead> response) {

                if (!response.isSuccessful()) {
                    try {
                        APIError apiError = ErrorUtils.parseError(response);

                        showMessage("Fetch Failed: " + apiError.getErrors());
                        Log.i("Invalid Fetch", String.valueOf(apiError.getErrors()));
                        //Log.i("Invalid Entry", response.errorBody().toString());

                    } catch (Exception e) {
                        Log.i("Fetch Failed", e.getMessage());
                        showMessage("Fetch Failed");

                    }

                    return;
                }

                policy_item = response.body().getHistory();


                int count = policy_item.size();

                Log.i("Re-SuccessSize", String.valueOf(policy_item.size()));

                if (count == 0) {
                    mTransactnNotify.setVisibility(View.GONE);

                } else {

                    for (int i = 0; i < count; i++) {
                        checkIncomplete = policy_item.get(i).getStatus();
                        if (checkIncomplete.equals("Pending") || checkIncomplete.equals("Initiated")) {
                            mTransactnNotify.setVisibility(View.VISIBLE);
                            String name = userPreferences.getAgentLastName();
                            String full_note = "Hi! " + name + ", you have an incomplete transaction";
                            mTrasactnNote.setText(full_note);
                            return;
                        }
                    }

                    Log.i("SuccessChecked", checkIncomplete);
                }

            }

            @Override
            public void onFailure(Call<TransactionHead> call, Throwable t) {
                showMessage("Fetch failed, check your internet " + t.getMessage());
                Log.i("GEtError", t.getMessage());
            }
        });


    }*/

   /* @SuppressLint("CheckResult")
    private void SLide(){

        ArrayList<Integer> listImage = new ArrayList<>();
        ArrayList<String> listName = new ArrayList<>();

        listImage.add(R.drawable.im_3);
        listName.add("Tech enthusiasm travel to Germany");
        listImage.add(R.drawable.im_4);
        listName.add("Tech enthusiasm travel to Germany");

        listImage.add(R.drawable.im_5);
        listName.add("Tech enthusiasm travel to Germany");



        RequestOptions requestOptions = new RequestOptions();
        requestOptions.fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.img_empty);



        for (int i = 0; i < listImage.size(); i++) {
            TextSliderView sliderView = new TextSliderView(getContext());
            // initialize SliderLayout
            sliderView
                    .image(listImage.get(i))
                    .description(listName.get(i))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                    .setOnSliderClickListener(this);

            //add your extra information
            sliderView.bundle(new Bundle());
            sliderView.getBundle().putString("extra", listName.get(i));
            mSlider.addSlider(sliderView);
        }

        // set Slider Transition Animation
        // mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mSlider.setPresetTransformer(SliderLayout.Transformer.Tablet);

        mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.setDuration(4000);
        mSlider.addOnPageChangeListener(this);

    }*/


  /*  private void getWalletHistroy() {
        if (networkConnection.isNetworkConnected(getContext())) {

            progressbar.setVisibility(View.VISIBLE);
            Call<WalletObj> call = client.wallet_history("Token " + userPreferences.getUserToken());

            call.enqueue(new Callback<WalletObj>() {
                @Override
                public void onResponse(Call<WalletObj> call, Response<WalletObj> response) {
                    if (!response.isSuccessful()) {

                        try {
                            APIError apiError = ErrorUtils.parseError(response);

                            showMessage("Invalid Fetch: " + apiError.getErrors());
                            Log.i("Invalid Fetch", apiError.getErrors().toString());
                            Log.i("Invalid FetchErrorBody", response.errorBody().toString());
                            timeout_alert();

                        } catch (Exception e) {
                            Log.i("InvalidEntry", e.getMessage());
                            showMessage("Failed to fetch wallet history");
                            timeout_alert();

                        }
                        progressbar.setVisibility(View.GONE);
                        showMessage("Failed to fetch wallet history");

                    }

                    wallet_histories = response.body().getWallet_History();
                    progressbar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<WalletObj> call, Throwable t) {
                    showMessage("Failed to fetch wallet history " + t.getMessage());
                    Log.i("GEtError", t.getMessage());

                    progressbar.setVisibility(View.GONE);
                }
            });
        } else {
            showMessage("No Internet Connection");
        }


    }*/

    @Override
    public void onClick(View v) {

    }


    private void showMessage(String s) {
        Snackbar.make(dash_layout, s, Snackbar.LENGTH_SHORT).show();
    }



    /*@Override
    public void onStop() {
        mSlider.stopAutoCycle();
        super.onStop();
    }*/

/*    @Override
    public void onSliderClick(BaseSliderView slider) {
        nextActivity("Quote and Buy", "Select insurance product", QuoteBuyActivity.class);


    }*/



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }


/*    private void timeout_alert() {

        new AlertDialog.Builder(getContext())
                .setTitle("Error !")
                .setIcon(R.drawable.ic_error_outline_black_24dp)
                .setMessage("Session Time-Out, Click Okay to re-login")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                        startActivity(new Intent(getActivity(), SignIn.class));
                        getActivity().finish();
                    }
                })
                .show();

    }*/
}
