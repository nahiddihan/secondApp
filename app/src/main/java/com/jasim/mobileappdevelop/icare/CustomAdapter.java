package com.jasim.mobileappdevelop.icare;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomAdapter extends BaseAdapter{


    String [] result;
    Context context;
    Integer [] imageId;
    private static LayoutInflater inflater=null;
    public CustomAdapter(Context context, String[] prgmNameList, Integer[] prgmImages) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        this.context=context;
        imageId=prgmImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.homescreen_grid_item, null);
        holder.tv=(TextView) rowView.findViewById(R.id.iconTitle);
        holder.img=(ImageView) rowView.findViewById(R.id.icon);

        holder.tv.setText(result[position]);
        holder.img.setImageResource(imageId[position]);

//        rowView.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                Toast.makeText(context, "You Clicked "+result[position], Toast.LENGTH_LONG).show();
//            }
//        });

        return rowView;
    }

}