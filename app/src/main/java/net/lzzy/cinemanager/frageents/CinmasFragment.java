package net.lzzy.cinemanager.frageents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import net.lzzy.cinemanager.R;

/**
 * Created by lzzy_gxy on 2019/3/26.
 * Description:
 */
public class CinmasFragment extends Fragment {
     public CinmasFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_cinemas,null);
        TextView tv=view.findViewById(R.id.fragment_cinemas_tv);
         return view;
    }
}
