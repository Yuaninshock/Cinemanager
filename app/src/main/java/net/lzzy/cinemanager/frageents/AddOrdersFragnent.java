package net.lzzy.cinemanager.frageents;



import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.style.cityjd.JDCityPicker;

import net.lzzy.cinemanager.R;
import net.lzzy.cinemanager.activities.MainActivity;
import net.lzzy.cinemanager.models.Cinema;
import net.lzzy.cinemanager.models.CinemaFactory;
import net.lzzy.cinemanager.models.Order;
import net.lzzy.cinemanager.models.OrderFactory;
import net.lzzy.cinemanager.utils.AppUtils;
import net.lzzy.cinemanager.utils.ViewUtils;
import net.lzzy.simpledatepicker.CustomDatePicker;
import net.lzzy.sqllib.GenericAdapter;
import net.lzzy.sqllib.ViewHolder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by lzzy_gxy on 2019/3/27.
 * Description:
 */
public class AddOrdersFragnent extends BeseFargment implements View.OnClickListener {
    public static final int MIN_DISTANCE = 100;
    private LinearLayout layoutMenu;
    private TextView tvTitle;
    private SearchView search;
    public static final String EXTRA_NEW_CINEMA = "newCinema";
    private LinearLayout layoutAddmain;
  /*  private ListView lv;*/
    private Spinner tvArea;
    private EditText edtName;
    private OrderFactory factory=OrderFactory.getInstance();
    private List<Order> orders;
    private EditText edtprice;
    private TextView tvDate;
    private long publishDate;
    private CustomDatePicker datePicker;
    private View dialog;
    private Spinner spCinema;
    private ImageView imgQRCode;
    private GenericAdapter<Order> adapter;
    private List<Cinema> cinemas;
    private SearchView search1;
    private Button but;
    private float touchX1;
    private float touchX2;
    private boolean isDelete;
    private OnOrderCreatedKusteber orderListener;




    /**添加数据*/
    private void addListener() {
        find(R.id.dialog_add_main_btn_cancel)
                .setOnClickListener(v -> layoutAddmain.setVisibility(View.GONE));
        find(R.id.dialog_add_main_btn_ok).setOnClickListener(v -> {
            String name=edtName.getText().toString();
            String strPrice=edtprice.getText().toString();
            if (TextUtils.isEmpty(name)||TextUtils.isEmpty(strPrice)){
                Toast.makeText(getActivity(),"信息不完整，请重新输入",Toast.LENGTH_SHORT).show();
                return;
            }
            float price;
            try{
                price=Float.parseFloat(strPrice);
            }catch (NumberFormatException e){
                Toast.makeText(getActivity(),"票价格式不正确，请重新输入",Toast.LENGTH_SHORT).show();
                return;
            }

            Order order=new Order();
            Cinema cinema=cinemas.get(spCinema.getSelectedItemPosition());
            order.setCinemaId(cinema.getId());
            order.setMovie(name);
            order.setPrice(price);
            order.setMovieTime(tvDate.getText().toString());
            adapter.add(order);
            edtName.setText("");
            edtprice.setText("");
            layoutAddmain.setVisibility(View.GONE);

        });
        find(R.id.dialog_add_main_btn_qrCode).setOnClickListener(v -> {
            String name=edtName.getText().toString();
            String price=edtprice.getText().toString();
            String location=spCinema.getSelectedItem().toString();
            String time=tvDate.getText().toString();
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(price)) {
                Toast.makeText(getActivity(),"信息不完整",Toast.LENGTH_SHORT).show();
                return;
            }
            String content="["+name+"]"+time+"\n"+location+"票价"+price+"元";
            imgQRCode.setImageBitmap(AppUtils.createQRCodeBitmap(content,200,200));
        });
        imgQRCode.setOnLongClickListener(v -> {
            Bitmap bitmap=((BitmapDrawable)imgQRCode.getDrawable()).getBitmap();
            Toast.makeText(getActivity(),AppUtils.readQRCode(bitmap),Toast.LENGTH_SHORT).show();
            return true;
        });

    }

    /**适配器*/
    private void showAndAddOrders() {
        cinemas = CinemaFactory.getInstance().get();
        orders=factory.get();
        spCinema.setAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, cinemas));

     /*   lv.setAdapter(adapter);*/
        initDatePicker();
        find(R.id.activity_add_book_layout).setOnClickListener(v -> datePicker.show(tvDate.getText().toString()));
        addListener();
    }


    @Override
    protected void populate() {
        initViews();/*setTitleMenu();*/
        showAndAddOrders();

        //region 无数据视图
        TextView tvNone=find(R.id.activity_main_tv_none);

        find(R.id.dialog_add_main_btn_cancel)
                .setOnClickListener(v -> {});

        find(R.id.dialog_add_main_edt_name).setOnClickListener(v -> {

        });


        find(R.id.dialog_add_main_btn_ok).setOnClickListener(v -> {
            String name=edtName.getText().toString();
            String strPrice=edtprice.getText().toString();
            if (TextUtils.isEmpty(name)||TextUtils.isEmpty(strPrice)){
                Toast.makeText(getActivity(),"信息不完整，请重新输入",Toast.LENGTH_SHORT).show();
                return;
            }
            float price;
            try{
                price=Float.parseFloat(strPrice);
            }catch (NumberFormatException e){
                Toast.makeText(getActivity(),"票价格式不正确，请重新输入",Toast.LENGTH_SHORT).show();
                return;
            }

            Order order=new Order();
            Cinema cinema=cinemas.get(spCinema.getSelectedItemPosition());
            order.setCinemaId(cinema.getId());
            order.setMovie(name);
            order.setPrice(price);
            order.setMovieTime(tvDate.getText().toString());
          /*  adapter.add(order);
            edtName.setText("");
            edtprice.setText("");*/
          orderListener.saceOrder(order);
           /*layoutAddmain.setVisibility(View.GONE);*/
        });
      find(R.id.dialog_add_main_btn_cancel).setOnClickListener(v->
         orderListener.canecelAddOrder());
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            orderListener=(OnOrderCreatedKusteber) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"必须实现OnOrderCreatedKusteber");
        }
    }
  /***
   * 保存数据
   * */
    @Override
    public void onDetach() {
        super.onDetach();
        orderListener=null;
    }
    private void initViews() {
        layoutAddmain = find(R.id.dialog_add_main_layout);
        tvArea = find(R.id.dialog_add_main_sp_area);
        edtName = find(R.id.dialog_add_main_edt_name);
        edtprice = find(R.id.dialog_add_main_edt_price);
        tvDate = find(R.id.activity_add_book_tv_date);
        spCinema = find(R.id.dialog_add_main_sp_area);
        imgQRCode = find(R.id.dialog_add_main_imv);
        search1 = find(R.id.main_sv_search);
    }
    @Override
    public int getLayoutRes() {
        return R.layout.fragement_orders;
    }
    /**日期*/
    public void initDatePicker() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now=sdf.format(new Date());
        tvDate.setText(now);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH,1);
        String end=sdf.format(calendar.getTime());
        datePicker=new CustomDatePicker(getActivity(),s -> tvDate.setText(s),now,end);
        datePicker.showSpecificTime(true);
        datePicker.setIsLoop(true);

    }
    @Override
    public void search(String kw) {

    }
    /** 对标题栏的点击监听 **/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bar_title_img_menu:
                int visible=layoutMenu.getVisibility()==View.VISIBLE ? View.GONE : View.VISIBLE;
                layoutMenu.setVisibility(visible);
                break;
            case R.id.bar_title_tv_exit:
                System.exit(0);
                break;

            case R.id.bar_title_tv_add_cinema:


                break;

            case R.id.bar_title_tv_view_cinema:

                break;
            case R.id.bar_title_tv_add_order:
                tvTitle.setText(R.string.bar_title_menu_add_orders);
                layoutMenu.setVisibility(View.GONE);
                layoutAddmain.setVisibility(View.VISIBLE);
                break;
            case R.id.bar_title_tv_view_order:
                layoutMenu.setVisibility(View.GONE);
                layoutAddmain.setVisibility(View.GONE);
                break;


            default:
                break;
        }
    }

    /**数据接口*/

 public interface OnOrderCreatedKusteber{
        void canecelAddOrder();
        void  saceOrder(Order order);
    }
}
