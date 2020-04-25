package com.shockdee.studqr.utilities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shockdee.studqr.R;
import com.shockdee.studqr.adapters.AreaAdapter;
import com.shockdee.studqr.models.Area;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TabOption.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TabOption#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabOption extends Fragment {

    public ArrayList<Area> areaTabList;
    public SearchView svOptionTab;
    public RecyclerView rvOptionTab;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TabOption() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabOption.
     */
    // TODO: Rename and change types and number of parameters
    public static TabOption newInstance(String param1, String param2) {
        TabOption fragment = new TabOption();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
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

        View rootView = inflater.inflate(R.layout.fragment_tab_option, container, false);
        rvOptionTab = rootView.findViewById(R.id.rv_tab_option);
        svOptionTab = rootView.findViewById(R.id.sv_tab_option);

        areaTabList = Utilities.initAreaFromDB(getActivity());
        rvOptionTab.setLayoutManager(new LinearLayoutManager(getActivity()));

        final AreaAdapter areaAdapter = new AreaAdapter(getActivity(), areaTabList);
        rvOptionTab.setAdapter(areaAdapter);

        svOptionTab.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();
                ArrayList<Area> newList = new ArrayList<>();
                for (Area area : areaTabList){
                    String name = area.area_name.toLowerCase();
                    if (name.contains(newText)){
                        newList.add(area);
                    }
                }
                areaAdapter.setAreaFilter(newList);
                return false;
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}