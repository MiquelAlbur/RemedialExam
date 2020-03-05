package com.example.remedialexam;



import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<Product> products;
    private OnNoteListener mOnNoteListener;
    private Context _context;
    private Cursor _cu;

    public RecyclerAdapter(ArrayList<Product> products, OnNoteListener mOnNoteListener, Context _context, Cursor _cu) {
        this.products = products;
        this.mOnNoteListener = mOnNoteListener;
        this._context = _context;
        this._cu = _cu;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(_context);
        View v = li.inflate(R.layout.row, parent, false);
        return new ViewHolder(v,mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(products.get(position));
    }

    @Override
    public int getItemCount() {
        return _cu.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private OnNoteListener onNoteListener;
        private TextView _tv1;
        private ImageButton _edit, _delete;

        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            this._tv1 = itemView.findViewById(R.id.productName);
            this._edit = itemView.findViewById(R.id.edit);
            this._delete = itemView.findViewById(R.id.delete);
            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
        }

        public void bind(Product p){
            this._tv1.setText(p.getName());
            this._edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (_context instanceof MainActivity) {
                        ((MainActivity) _context).editProduct(getAdapterPosition());
                    }
                }
            });
            this._delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (_context instanceof MainActivity) {
                        ((MainActivity) _context).deleteProduct(getAdapterPosition());
                    }
                }
            });
        }
        @Override
        public void onClick(View v) {

        }
    }
        public interface OnNoteListener {
        void onNoteClick(int position);
    }
}
