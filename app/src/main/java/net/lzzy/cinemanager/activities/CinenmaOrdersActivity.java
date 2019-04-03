package net.lzzy.cinemanager.activities;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import net.lzzy.cinemanager.R;
import net.lzzy.cinemanager.activities.MainActivity;
import net.lzzy.cinemanager.frageents.CinenaOrdersFragment;

/**
 * Created by lzzy_gxy on 2019/4/2.
 * Description:
 */
public class CinenmaOrdersActivity extends AppCompatActivity {
   public static final String EXTRA_CINEMA_ID = "cinemaId";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_ciema_order);
       String cinemaId  =getIntent().getStringExtra(EXTRA_CINEMA_ID);
        FragmentManager manager=getSupportFragmentManager();
        Fragment fragment =manager.findFragmentById(R.id.cinema_order_contauner);
      if (fragment==null){
          fragment=new CinenaOrdersFragment();
          manager.beginTransaction().add(R.id.cinema_order_contauner,fragment).commit();
      }





    }
}
