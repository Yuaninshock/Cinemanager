package net.lzzy.cinemanager.frageents;



import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import net.lzzy.cinemanager.R;
import net.lzzy.cinemanager.models.Cinema;
import net.lzzy.cinemanager.models.CinemaFactory;
import net.lzzy.simpledatepicker.CustomDatePicker;
import net.lzzy.sqllib.GenericAdapter;
import net.lzzy.sqllib.ViewHolder;

import java.util.List;

/**
 * Created by lzzy_gxy on 2019/3/26.
 * Description:
 */
public class CinmasFragment extends BeseFargment  {
    private static final String ARG_NEW_ORDER ="argNewOrder" ;
    public static final String CINEMA = "cinema";
    private ListView lv;
    private List<Cinema> cinemas;
    private GenericAdapter<Cinema> adtpter;
    private Cinema cinema;
     private OnCinemaSelectedLiseener oncinemnaselected;





    public static CinmasFragment newInstance(Cinema cinema){
        CinmasFragment fragment=new CinmasFragment();
        Bundle arqs=new Bundle();
        arqs.putParcelable(CINEMA,cinema);
        fragment.setArguments(arqs);
        return fragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            cinema=getArguments().getParcelable(CINEMA);
        }
    }

    public void save(Cinema cinema) {
        adtpter.add(cinema);
    }

    private CinemaFactory factory = CinemaFactory.getInstance();

    @Override
    protected void populate() {

        lv = find(R.id.activity_cinema_lv);
        View empty = find(R.id.activity_cinemas_tv_none);
        lv.setEmptyView(empty);
        cinemas = factory.get();
        adtpter = new GenericAdapter<Cinema>(getActivity(),
                R.layout.cinemas_item, cinemas) {
            @Override
            public void populate(ViewHolder viewHolder, Cinema cinema) {
                viewHolder.setTextView(R.id.cinemas_items_name, cinema.getName())
                        .setTextView(R.id.cinemas_items_location, cinema.getLocation());
            }

            @Override
            public boolean persistInsert(Cinema cinema) {
                return factory.addCinema(cinema);
            }

            @Override
            public boolean persistDelete(Cinema cinema) {
                return factory.deleteCinema(cinema);
            }
        };
        lv.setAdapter(adtpter);
        lv.setOnItemClickListener((parent, view, position, id) ->
                oncinemnaselected.onCinemaSelected(cinemas.get(position).getId()/**获取电影院的对象和它的ID**/
        .toString()));

        if (cinema != null) {
            save(cinema);
        }

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            oncinemnaselected=(OnCinemaSelectedLiseener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"必须实现OnCinemaSelectedLiseener");
        }
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_cinemas;
    }

    @Override
    public void search(String kw) {
        cinemas.clear();
        if (TextUtils.isEmpty(kw)) {
            cinemas.addAll(factory.get());
        } else {
            cinemas.addAll(factory.searchCinemas(kw));
        }
        adtpter.notifyDataSetChanged();
    }

    /***
     * 销毁
     * */
    @Override
    public void onDetach() {
        super.onDetach();
        oncinemnaselected=null;
    }

    public interface OnCinemaSelectedLiseener{
        void onCinemaSelected(String cinemaId);
   }

}
