package net.lzzy.cinemanager.frageents;


import android.widget.TextView;

import net.lzzy.cinemanager.R;

/**
 * Created by lzzy_gxy on 2019/3/26.
 * Description:
 */
public class OrdersFragment extends BeseFargment {
    @Override
    protected void populate() {
        TextView tv=find(R.id.fragment_orders_tv);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_orders;
    }

    @Override
    public void search(String kw) {

    }

}
