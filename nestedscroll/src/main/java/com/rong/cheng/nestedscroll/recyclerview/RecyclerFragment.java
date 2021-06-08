package com.rong.cheng.nestedscroll.recyclerview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rong.cheng.nestedscroll.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: frc
 * @description:
 * @date: 2021/6/8 10:22 上午
 */
public class RecyclerFragment extends Fragment {

    private RecyclerView mRecyclerView;


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        final RecyclerAdapter adapter = new RecyclerAdapter(getData());
        mRecyclerView.setAdapter(adapter);

        return view;
    }
    private int i = 0;
    private List<String> getData() {
        List<String> data = new ArrayList<>();
        for (int tempI = i; i < tempI + 10; i++) {
            data.add("ChildView item " + i);
        }
        return data;
    }
}
