package com.lovemyweather.lovemyweather;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    ImageButton btnChangeByGeo;
    ImageButton btnChangeByPlace;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        btnChangeByGeo = (ImageButton) rootView.findViewById(R.id.btnChangeByGeoLocation);
        btnChangeByPlace = (ImageButton) rootView.findViewById(R.id.btnChangeByPlace);
        //initial for button geo dan place
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButtonClick(btnChangeByGeo,"geo");
        ButtonClick(btnChangeByPlace,"place");
    }
    private void ButtonClick(ImageButton button, final String method)
    {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeLayoutDetail(method);
            }
        });
    }
    private void ChangeLayoutDetail(String method){

      DetailActivity detailActivity = new DetailActivity();
        Bundle b = new Bundle();
        b.putString("method",method);//method is initial to know if btnGeo or btnPlace is pressed
        detailActivity.setArguments(b);
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        fragmentTransaction.replace(R.id.fragment_container, detailActivity);
        fragmentTransaction.addToBackStack(method);
        fragmentTransaction.commit();
        //change fragment to detail
    }
}
