package net.lzzy.cinemanager.frageents;


import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.style.cityjd.JDCityPicker;

import net.lzzy.cinemanager.R;
import net.lzzy.cinemanager.models.Cinema;
import net.lzzy.sqllib.GenericAdapter;

/**
 * Created by lzzy_gxy on 2019/3/27.
 * Description:
 */
public class AddCinemasFragment extends BeseFargment {
    private GenericAdapter<Cinema> adapter;
    private TextView tvArea;
    private EditText edtName;
    private String province="广西壮族自治区";
    private String city="柳州市";
    private String area="鱼峰区";
    private OnFrgenTutoeractuibKustebt listbier;
    private OnCinemaCreatedKusteber cinemListener;

    @Override
    protected void populate() {
        tvArea=find(R.id.dialog_add_tv_area);
        edtName=find(R.id.dialog_add_cinema_edt_name);
        showDialog();
        listbier.hideSearch();


    }
    private void showDialog() {

        find(R.id.dialog_add_cinema_btn_cancel)
                .setOnClickListener(v -> {});

        find(R.id.dialog_add_cinema_layout_area).setOnClickListener(v -> {
            JDCityPicker cityPicker = new JDCityPicker();
            cityPicker.init(getActivity());
            cityPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
                @Override
                public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                    AddCinemasFragment.this.province=province.getName();
                    AddCinemasFragment.this.city=city.getName();
                    AddCinemasFragment.this.area=district.getName();
                    String loc=province.getName()+city.getName()+district.getName();
                    tvArea.setText(loc);
                }

                @Override
                public void onCancel() {
                }
            });
            cityPicker.showCityPicker();
        });
        find(R.id.dialog_add_cinema_btn_save).setOnClickListener(v -> {
            String name=edtName.getText().toString();
            Cinema cinema=new Cinema();
            cinema.setName(name);
            cinema.setArea(area);
            cinema.setCity(city);
            cinema.setProvince(province);
            cinema.setLocation(tvArea.getText().toString());
            edtName.setText("");
            cinemListener.saceCinema(cinema);
        });
        find(R.id.dialog_add_cinema_btn_cancel).setOnClickListener(v->
               cinemListener.canecelAddCinema() );
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_add_cinemas;
    }

    @Override
    public void search(String kw) {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            listbier.hideSearch();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listbier =(OnFrgenTutoeractuibKustebt)context;
            cinemListener=(OnCinemaCreatedKusteber)context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"必须实现OnFrgenTutoeractuibKustebt");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listbier=null;
        cinemListener=null;

    }

    public interface OnCinemaCreatedKusteber{
       void canecelAddCinema();
       void  saceCinema(Cinema cinema);
    }

}

