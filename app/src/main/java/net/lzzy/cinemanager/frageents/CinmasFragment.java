package net.lzzy.cinemanager.frageents;



import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import net.lzzy.cinemanager.R;
import net.lzzy.cinemanager.models.Cinema;
import net.lzzy.cinemanager.models.CinemaFactory;
import net.lzzy.sqllib.GenericAdapter;
import net.lzzy.sqllib.ViewHolder;

import java.util.List;

/**
 * Created by lzzy_gxy on 2019/3/26.
 * Description:
 */
public class CinmasFragment extends BeseFargment {
 private ListView lv;
 private List<Cinema>cinemas;

 private CinemaFactory factory = CinemaFactory.getInstance();
    @Override
    protected void populate() {
        lv= find(R.id.activity_cinema_lv);
        View empty=find(R.id.activity_cinemas_tv_none);
        lv.setEmptyView(empty);
        cinemas=factory.get();
        GenericAdapter<Cinema> adtpter=new GenericAdapter<Cinema>(getActivity(),
                R.layout.cinemas_item,cinemas) {
            @Override
            public void populate(ViewHolder viewHolder, Cinema cinema) {

            }

            @Override
            public boolean persistInsert(Cinema cinema) {
                return false;
            }

            @Override
            public boolean persistDelete(Cinema cinema) {
                return false;
            }
        };
        lv.setAdapter(adtpter);
    }


    @Override
    public int getLayoutRes() {
        return R.layout.fragment_cinemas;
    }
}
