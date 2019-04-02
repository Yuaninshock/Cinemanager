package net.lzzy.cinemanager.frageents;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import net.lzzy.cinemanager.R;

/**
 * Created by lzzy_gxy on 2019/4/2.
 * Description:
 */
public class CinenmaOrdersActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_ciema_orders);
        ListView lv=findViewById(R.id.fragment_cinema_orders_lv);
        View empty=findViewById(R.id.fragment_cinema_orders_none);
        lv.setEmptyView(empty);
        /*String cinemaId=getIntent().getStringExtra(DI)*/

    }
}
