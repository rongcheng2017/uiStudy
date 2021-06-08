package com.rong.cheng.nestedscroll.recyclerview;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.rong.cheng.nestedscroll.titleview.TitleView;
import com.rong.cheng.nestedscroll.titleview.TitleViewViewModel;


import java.util.List;

/**
 * @author: frc
 * @description:
 * @date: 2021/6/8 10:25 上午
 */
public class RecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    public List<String> data;

    public RecyclerAdapter(List<String> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BaseViewHolder(new TitleView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.bind(new TitleViewViewModel(data.get(position)));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
