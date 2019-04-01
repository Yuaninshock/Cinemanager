package net.lzzy.cinemanager.frageents;



import android.widget.TextView;

import net.lzzy.cinemanager.R;

/**
 * Created by lzzy_gxy on 2019/3/27.
 * Description:
 */
public class AddOrdersFragnent extends BeseFargment {

    @Override
    protected void populate() {
        TextView tv=find(R.id.fragment_add_orders_tv);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_add_orders;
    }

    @Override
    public void search(String kw) {

    }
}
