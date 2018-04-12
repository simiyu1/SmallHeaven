package simiyu.com.smallheaven;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView myloc, myhours, mycont;
    Button inviter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_main2, container, false);
        myloc= view.findViewById(R.id.myloc);
       myhours= view.findViewById(R.id.myhours);
        mycont= view.findViewById(R.id.mycont);
        inviter= view.findViewById(R.id.inviter);

        myloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q=-1.2050293,36.8951237");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        mycont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int REQUEST_CODE_ASK_PERMISSION=123;
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:0700000000"));
                if(ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE_ASK_PERMISSION);
                    }

                    return;
                }
                startActivity(callIntent);
            }
        });

        inviter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                final int REQUEST_CODE_ASK_PERMISSIONS =123;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    int hasWriteStorage = getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if(hasWriteStorage != PackageManager.PERMISSION_GRANTED){
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
                        return;
                    }
                }


                Bitmap bitmap;
                OutputStream output;
                bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.buymecake);
                //InputStream inputStream= getApplicationContext().getResources().openRawResource(R.drawable.findme);
                File filepath = Environment.getExternalStorageDirectory();
                File dir = new File(filepath.getAbsolutePath()+"/Klabu/");
                dir.mkdirs();
                File file = new File(dir,"joinme.png");
                try{
                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setType("image/*");
                    output=new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,output);
                    output.flush();
                    output.close();
                    Uri uri = Uri.fromFile(file);
                    share.putExtra(Intent.EXTRA_STREAM, uri);
                    startActivity(Intent.createChooser(share,"Invite Friends!!"));
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                return;

                /*Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/*");
                String imagePath= Environment.getRootDirectory()+"findme.png";
                //etExternalStorageDirectory() + "/findme.png";
                File imageFileToShare = new File(imagePath);
                Uri uri= Uri.fromFile(imageFileToShare);
                share.putExtra(Intent.EXTRA_STREAM, uri);

                startActivity(Intent.createChooser(share,"Invite Friends!!"));*/
            }
        });

       return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
