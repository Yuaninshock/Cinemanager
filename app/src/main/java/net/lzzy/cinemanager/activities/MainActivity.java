package net.lzzy.cinemanager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.SearchView;
import android.widget.TextView;

import net.lzzy.cinemanager.R;

/**
 * @author Administrator
 */
public class MainActivity extends AppCompatActivity  implements View.OnClickListener{
      private View layoutMenu;
      private SearchView searoh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        setTitleMenu();
    }
    private void setTitleMenu(){

        layoutMenu =findViewById(R.id.bar_title_layout_menu);
        layoutMenu.setVisibility(View.GONE);
        findViewById(R.id.bar_title_img_menu).setOnClickListener(v->{
            int visible =layoutMenu.getVisibility()==View.VISIBLE?View.GONE:View.VISIBLE;
               layoutMenu.setVisibility(visible);
        });
        TextView teTitle= findViewById(R.id.bar_title_tv_title);
        teTitle.setText("我的订单");
        searoh =findViewById(R.id.main_sv_search);
        findViewById(R.id.bar_title_tv_add_cinema).setOnClickListener(this);
        findViewById(R.id.bar_title_tv_view_cinema).setOnClickListener(this);
        findViewById(R.id.bar_title_tv_add_order).setOnClickListener(this);
        findViewById(R.id.bar_title_tv_view_order).setOnClickListener(this);
        findViewById(R.id.bar_title_tv_exit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }

}
