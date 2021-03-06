package miage.parisnanterre.fr.walkandwork;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewEmployeurAdapter extends RecyclerView.Adapter<RecyclerViewEmployeurAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewEmployeurAd";
    private List<String> mImageNames = new ArrayList<>();
    private List<String> mImages = new ArrayList<>();
    private Context mContext;
    private List<String> phone;
    private List<String> data;

    public RecyclerViewEmployeurAdapter(List<String> mImageNames, List<String> mImages, List<String>phone , List<String>data, Context mContext) {
        this.mImageNames = mImageNames;
        this.mImages = mImages;
        this.mContext = mContext;
        this.phone = phone;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_employeur, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        Log.d(TAG, "onBindViewHolder: called.");
        Glide.with(mContext).asBitmap().load(mImages.get(i)).into(viewHolder.image);
        viewHolder.imageName.setText(mImageNames.get(i));

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on :"+mImageNames.get(i));
                Toast.makeText(mContext,mImageNames.get(i),Toast.LENGTH_SHORT).show();
                Intent toProfil = new Intent(mContext,Profil.class);
                toProfil.putExtra("phoneNumber", phone.get(i));
                toProfil.putExtra("data",data.get(i));
                toProfil.putExtra("name",mImageNames.get(i));
                mContext.startActivity(toProfil);
            }
        });

       viewHolder.bCall.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               dialContactPhone(phone.get(i));
           }
       });

    }

    @Override
    public int getItemCount() {
        return mImageNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //On crée un attribut pour chaque élément du layout à adapter (ici layout_list_employeur)
        CircleImageView image;
        TextView imageName;
        RelativeLayout parentLayout;
        ImageButton bCall;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            imageName = itemView.findViewById(R.id.image_name);
            bCall = itemView.findViewById(R.id.bCall);
            parentLayout = itemView.findViewById(R.id.parent_layout_employeur);
        }
    }
    public void dialContactPhone(final String phoneNumber) {
        mContext.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }
}
