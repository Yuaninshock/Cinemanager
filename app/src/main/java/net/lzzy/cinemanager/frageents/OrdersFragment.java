package net.lzzy.cinemanager.frageents;


import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import net.lzzy.cinemanager.R;
import net.lzzy.cinemanager.models.Cinema;
import net.lzzy.cinemanager.models.CinemaFactory;
import net.lzzy.cinemanager.models.Order;
import net.lzzy.cinemanager.models.OrderFactory;
import net.lzzy.cinemanager.utils.AppUtils;
import net.lzzy.cinemanager.utils.ViewUtils;
import net.lzzy.simpledatepicker.CustomDatePicker;
import net.lzzy.sqllib.GenericAdapter;
import net.lzzy.sqllib.ViewHolder;

import java.util.List;

/**
 * Created by lzzy_gxy on 2019/3/26.
 * Description:
 */
public class OrdersFragment extends BeseFargment {
    private static GenericAdapter<Order> adapter;
    private List<Order> orders;
    private Button but;
    public static final int MIN_DISTANCE = 100;
    private OrderFactory factory=OrderFactory.getInstance();
    private float touchX1;
    private float touchX2;
    private boolean isDelete;
    private AddOrdersFragnent.OnOrderCreatedKusteber orderListener;

    public OrdersFragment(Order order) {

    }
    public OrdersFragment(){

    }

    @Override
    protected void populate() {
        ListView orderList=find(R.id.fragment_orders_tv);
        orders=factory.get();
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
        orderList.setAdapter(adapter);
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
    public int getLayoutRes() {
        return R.layout.fragment_orders;
    }

    @Override
    public void search(String kw) {

    }
    public void save(Order order){
        adapter.add(order);
    };
}
