package com.gurpster.facapemobile.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.gurpster.facapemobile.R;
import com.gurpster.facapemobile.data.entity.Debt;
import com.gurpster.facapemobile.listener.OnDebtClickListener;
import com.gurpster.facapemobile.view.CheckBoxImageView;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import me.grantland.widget.AutofitHelper;
import me.grantland.widget.AutofitTextView;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

/**
 * Created by Williaan Lopes (d3x773r) on 25/11/2017.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public class DebtAdapter extends RecyclerView.Adapter<DebtAdapter.ViewHolder> {

    private Context context;
    private List<Debt> mArrayList;
    private OnDebtClickListener clickListener;

    public DebtAdapter(Context context, OnDebtClickListener clickListener) {
        this.context = context;
        mArrayList = new ArrayList<>();
        this.clickListener = clickListener;
    }

    public DebtAdapter(OnDebtClickListener clickListener, List<Debt> arrayList) {
        this.clickListener = clickListener;
        mArrayList = arrayList;
    }

    @Override
    public DebtAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_debt_2, viewGroup, false);
        return new ViewHolder(view);
    }

    public void setList(List<Debt> list) {
        mArrayList = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(DebtAdapter.ViewHolder viewHolder, int i) {
        Debt debt = mArrayList.get(i);
        viewHolder.type.setText(debt.getType());
        viewHolder.value.setText(String.valueOf(debt.getValue()));
        viewHolder.discount.setText(String.valueOf(debt.getDiscount()));
        viewHolder.expiryDate.setText(debt.getExpiryDate());
        viewHolder.fine.setText(String.valueOf(debt.getFine()));
        viewHolder.barcodeNumber.setText(debt.getBarcode());
        AutofitHelper.create(viewHolder.type);

        Bitmap bitmap = encodeAsBitmap(debt.getBarcode(), BarcodeFormat.CODE_128, 600, 300);
        viewHolder.barcode.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView type;
        private AppCompatTextView value;
        private AppCompatTextView discount;
        private AppCompatTextView fine;
        private AppCompatTextView expiryDate;
        private CheckBoxImageView barcode;
        private AutofitTextView barcodeNumber;

        ViewHolder(final View view) {
            super(view);

            type = view.findViewById(R.id.debt_type);
            value = view.findViewById(R.id.debt_value);
            discount = view.findViewById(R.id.debt_discount);
            fine = view.findViewById(R.id.debt_fine);
            expiryDate = view.findViewById(R.id.debt_expiry_date);
            barcode = view.findViewById(R.id.debt_barcode);
            barcodeNumber = view.findViewById(R.id.debt_barcode_number);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Debt debt = mArrayList.get(getLayoutPosition());
                    clickListener.onItemClick(debt);
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Debt debt = mArrayList.get(getLayoutPosition());
                    clickListener.onLongItemClick(debt);
                    return true;
                }
            });

            view.findViewById(R.id.debt_download).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Debt debt = mArrayList.get(getLayoutPosition());
                    clickListener.onDownloadClick(debt);
                }
            });

            barcode.setOnCheckedChangeListener(new CheckBoxImageView.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(View buttonView, boolean isChecked) {
                    if(isChecked) {
                        buttonView.setVisibility(View.GONE);
                        barcodeNumber.setVisibility(View.VISIBLE);
                    } else {
                        buttonView.setVisibility(View.VISIBLE);
                        barcodeNumber.setVisibility(View.GONE);
                    }
                }
            });
            barcodeNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    barcode.setChecked(false);
                    Debt debt = mArrayList.get(getLayoutPosition());
                    Bitmap bitmap = encodeAsBitmap(debt.getBarcode(), BarcodeFormat.CODE_128, 600, 300);
                    barcode.setImageBitmap(bitmap);
                    barcode.setScaleType(ImageView.ScaleType.FIT_XY);
                    barcode.setVisibility(View.VISIBLE);
                    view.setVisibility(View.GONE);
                }
            });

        }
    }

    private Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int imgWidth, int imgHeight) {

        String contentsToEncode = contents;

        if (contentsToEncode == null) {
            return null;
        }

        Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(contentsToEncode);

        if (encoding != null) {
            hints = new EnumMap<>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }

        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result;

        try {
            result = writer.encode(contentsToEncode, format, imgWidth, imgHeight, hints);
        } catch (IllegalArgumentException | WriterException e ) {
            // Unsupported format
            return null;
        }

        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];

        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

        return bitmap;
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }

}