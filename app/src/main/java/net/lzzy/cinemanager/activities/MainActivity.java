package net.lzzy.cinemanager.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.Window;
import android.widget.SearchView;
import android.widget.TextView;

import net.lzzy.cinemanager.R;
import net.lzzy.cinemanager.frageents.AddCinemasFragment;
import net.lzzy.cinemanager.frageents.AddOrdersFragnent;
import net.lzzy.cinemanager.frageents.BeseFargment;
import net.lzzy.cinemanager.frageents.CinmasFragment;
import net.lzzy.cinemanager.frageents.OnFrgenTutoeractuibKustebt;
import net.lzzy.cinemanager.frageents.OrdersFragment;
import net.lzzy.cinemanager.models.Cinema;
import net.lzzy.cinemanager.models.CinemaFactory;
import net.lzzy.cinemanager.models.Order;
import net.lzzy.cinemanager.utils.ViewUtils;


/**
 * @author Administrator
 */
public class MainActivity extends AppCompatActivity  implements View.OnClickListener ,
        OnFrgenTutoeractuibKustebt ,AddCinemasFragment.OnCinemaCreatedKusteber, AddOrdersFragnent.OnOrderCreatedKusteber {

    private FragmentManager manager= getSupportFragmentManager();
      private View layoutMenu;
      private SearchView searoh;
    private TextView tvTitle;
    private SparseArray<String>titleArray=new SparseArray<>();
    private SparseArray<Fragment>fragmentArray=new SparseArray<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        setTitleMenu();
        searoh.setOnQueryTextListener(new ViewUtils.AbstractQueryHandler() {
            @Override
            public boolean handleQuery(String kw) {
               Fragment fragment=manager.findFragmentById(R.id.fraqnebt_container);
               if (fragment!=null){
                   if (fragment instanceof BeseFargment){
                       ((BeseFargment)fragment).search(kw);
                   }
               }
                return false;
            }
        });
        OrdersFragment ordersFragment=new OrdersFragment();
        fragmentArray.put(R.id.bar_title_tv_view_order,ordersFragment);
        manager.beginTransaction().add(R.id.fraqnebt_container,ordersFragment).commit();
    }
    private void setTitleMenu(){
          titleArray.put(R.id.bar_title_tv_add_cinema,"添加影院");
          titleArray.put(R.id.bar_title_tv_view_cinema,"影院列表");
          titleArray.put(R.id.bar_title_tv_add_order,"添加订单");
          titleArray.put(R.id.bar_title_tv_view_order,"我的订单");
        layoutMenu =findViewById(R.id.bar_title_layout_menu);
        layoutMenu.setVisibility(View.GONE);
        findViewById(R.id.bar_title_img_menu).setOnClickListener(v->{
            int visible =layoutMenu.getVisibility()==View.VISIBLE?View.GONE:View.VISIBLE;
               layoutMenu.setVisibility(visible);
        });
        tvTitle = findViewById(R.id.bar_title_tv_title);
        tvTitle.setText("我的订单");
        searoh =findViewById(R.id.main_sv_search);
        findViewById(R.id.bar_title_tv_add_cinema).setOnClickListener(this);
        findViewById(R.id.bar_title_tv_view_cinema).setOnClickListener(this);
        findViewById(R.id.bar_title_tv_add_order).setOnClickListener(this);
        findViewById(R.id.bar_title_tv_view_order).setOnClickListener(this);
        findViewById(R.id.bar_title_tv_exit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        layoutMenu.setVisibility(View.GONE);
        tvTitle.setText(titleArray.get(v.getId()));
        FragmentTransaction transaction=manager.beginTransaction();
        Fragment fragment=fragmentArray.get(v.getId());
        if (fragment ==null){
        fragment =createFragment(v.getId());
        fragmentArray.put(v.getId(),fragment);
        transaction.add(R.id.fraqnebt_container,fragment);
        }
        for (Fragment f:manager.getFragments()){
            transaction.hide(f);
        }
        transaction.show(fragment).commit();
    }
   private Fragment createFragment(int id){
       switch (id){
           case R.id.bar_title_tv_add_cinema:
               return new AddCinemasFragment();
           case R.id.bar_title_tv_view_cinema:
               return new CinmasFragment();
           case R.id.bar_title_tv_add_order:
               return new AddOrdersFragnent();
           case R.id.bar_title_tv_view_order:
               return new OrdersFragment();

           default:
               break;

       }
       return null;
   }

    @Override
    public void hideSearch() {
        searoh.setVisibility(View.VISIBLE);
    }

    @Override
    public void canecelAddCinema() {
   Fragment addConemaFragent=fragmentArray.get(R.id.bar_title_tv_view_cinema);
       if (addConemaFragent==null){
           return;
       }
       Fragment cinemasFragent=fragmentArray.get(R.id.bar_title_tv_view_cinema);
       FragmentTransaction transaction=manager.beginTransaction();
         if (cinemasFragent==null){
            cinemasFragent =new CinmasFragment();
             fragmentArray.put(R.id.bar_title_tv_view_cinema,cinemasFragent);
             transaction.add(R.id.fraqnebt_container,cinemasFragent);
         }
         transaction.hide(addConemaFragent).show(cinemasFragent);
         tvTitle.setText(titleArray.get(R.id.bar_title_tv_view_cinema));
    }

    @Override
    public void saceCinema(Cinema cinema) {

        Fragment addCinmenaFragemnet=fragmentArray.get(R.id.bar_title_tv_add_cinema);
        if (addCinmenaFragemnet==null){
            return;
        }

    Fragment cinemasFragment=fragmentArray.get(R.id.bar_title_tv_view_cinema);
   FragmentTransaction transaction=manager.beginTransaction();
    if (cinemasFragment==null){
        cinemasFragment =new CinmasFragment(cinema);
        fragmentArray.put(R.id.bar_title_tv_view_cinema,cinemasFragment);
    transaction.add(R.id.fraqnebt_container,cinemasFragment);
    }else {
        ((CinmasFragment)cinemasFragment).save(cinema);
    }
    transaction.hide(addCinmenaFragemnet).show(cinemasFragment).commit();
     tvTitle.setText(titleArray.get(R.id.bar_title_tv_view_cinema));
    }

/**
 * 保存fragment数据
 * **/
    @Override
    public void canecelAddOrder() {
        Fragment addOedernFragent=fragmentArray.get(R.id.bar_title_tv_view_order);
        if (addOedernFragent==null){
            return;
        }
        Fragment ordersFragent=fragmentArray.get(R.id.bar_title_tv_view_order);
        FragmentTransaction transaction=manager.beginTransaction();
        if (ordersFragent==null){
            ordersFragent =new OrdersFragment();
           fragmentArray.put(R.id.bar_title_tv_view_order,ordersFragent);
            transaction.add(R.id.fraqnebt_container,ordersFragent);
        }
        transaction.hide(addOedernFragent).show(ordersFragent);
        tvTitle.setText(titleArray.get(R.id.bar_title_tv_view_order));
    }



    @Override
    public void saceOrder(Order order) {
        Fragment addOrderFragemnet=fragmentArray.get(R.id.bar_title_tv_add_order);
        if (addOrderFragemnet==null){
            return;
        }

        Fragment ordersFragment=fragmentArray.get(R.id.bar_title_tv_view_order);
        FragmentTransaction transaction=manager.beginTransaction();
        if (ordersFragment==null){
           ordersFragment=new OrdersFragment(order);
            fragmentArray.put(R.id.bar_title_tv_view_order,ordersFragment);
            transaction.add(R.id.fraqnebt_container,ordersFragment);
        }else {
            ((OrdersFragment)ordersFragment).save(order);
        }
        transaction.hide(addOrderFragemnet).show(ordersFragment).commit();
        tvTitle.setText(titleArray.get(R.id.bar_title_tv_view_order));
    }
}
