package net.lzzy.cinemanager.frageents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import net.lzzy.cinemanager.R;

import java.util.Objects;

/**
 * Created by lzzy_gxy on 2019/3/27.
 * Description:
 */
public abstract class BeseFargment extends Fragment {


    public BeseFargment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutRes(), container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        populate();
    }

    /*执行*/
    protected abstract void populate();


    public abstract int getLayoutRes();

    <T extends View>T find(@IdRes int id){
        return Objects.requireNonNull(getView()).findViewById(id);

    }
  public abstract void search(String kw);
}