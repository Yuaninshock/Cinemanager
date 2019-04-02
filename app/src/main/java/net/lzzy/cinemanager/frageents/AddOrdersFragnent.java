package net.lzzy.cinemanager.frageents;



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
    private onOrderCreatedKusteber orderListener;




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
       adapter = new GenericAdapter<Order>(getActivity(),R.layout.main_item, orders) {

            @Override
            public void populate(ViewHolder viewHolder, Order order) {
                String location= String.valueOf(CinemaFactory.getInstance()
                        .getById(order.getCinemaId().toString()));
                viewHolder.setTextView(R.id.main_item_movieName,order.getMovie())
                        .setTextView(R.id.main_item_area,location);
                but = viewHolder.getView(R.id.main_item_btn);
                but.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("删除确认")
                                .setMessage("要删除订单吗？")
                                .setNegativeButton("取消",null)
                                .setPositiveButton("确定", (dialog, which) -> adapter.remove(order)).show();
                    }
                });
                viewHolder.getConvertView().setOnTouchListener(new ViewUtils.AbstractTouchHandler() {
                    @Override
                    public boolean handleTouch(MotionEvent event) {
                        slideToDelete(event,order,but);
                        return true;
                    }


                });
            }

            @Override
            public boolean persistInsert(Order order) {
                return factory.addOrder(order);
            }

            @Override
            public boolean persistDelete(Order order) {
                return factory.deleteOrder(order);
            }
        };
     /*   lv.setAdapter(adapter);*/
        initDatePicker();
        find(R.id.activity_add_book_layout).setOnClickListener(v -> datePicker.show(tvDate.getText().toString()));
        addListener();
    }
    private void slideToDelete(MotionEvent event, Order order, Button but) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                touchX1=event.getX();

                break;
            case MotionEvent.ACTION_UP:
                touchX2 =event.getX();
                if (touchX1-touchX2> MIN_DISTANCE){
                    if (!isDelete){
                        but.setVisibility(View.VISIBLE);
                        isDelete=true;
                    }

                }else {
                    if (but.isShown()){
                        but.setVisibility(View.GONE);
                        isDelete=false;
                    }else {
                        clickOrder(order);
                    }
                }
                break;
            default:
                break;
        }
    }
    private void clickOrder(Order order) {
        Cinema cinema=CinemaFactory.getInstance()
                .getById(order.getCinemaId().toString().toString());
        String content="["+order.getMovie()+"]"+order.getMovieTime()+"\n"+cinema+"票价"+order.getPrice()+"元";
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.dialog_qrcode,null);
        ImageView img=view.findViewById(R.id.dialog_qrcode_img);
        img.setImageBitmap(AppUtils.createQRCodeBitmap(content,300,300));
        new AlertDialog.Builder(getActivity())
                .setView(view).show();
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
            adapter.add(order);
            edtName.setText("");
            edtprice.setText("");
           /*layoutAddmain.setVisibility(View.GONE);*/
        });

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

    /**添加数据*/

 public interface onOrderCreatedKusteber{
        void canecelAddOrder();
        void  saceOrder(Order order);
    }
}
