package com.example.myapplication.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;

public class GalleryFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private GridView mGridView;

    public GalleryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // 리스너 연결
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Bundle 받을꺼야
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 뷰 가져오기
        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }

    // 뷰 가져온 이후 할 것
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 뷰
        mGridView = (GridView) view.findViewById(R.id.grid_view);

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            // 설명을 보여줄 것인가
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // 사용자 응답을 기다리는 설명을 비동기로 보여주기
                // 권한 체크를 안 하면 이 기능을 사용할 수 없다고 어필하고

                // 다이얼로그 표시
                // 이 권한을 수락하지 않으면 이 기능을 사용할 수 없습니다
                // 권한을 설정하시려면 설정 > 애플리케이션 > 앱이름 가서 설정하세요

                // 다시 권한 요청

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1000);

            } else {

                // No explanation needed, we can request the permission.
                // 권한을 요청

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1000);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {

            // 이미 권한이 있을 때
            getPicture();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case 1000: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    // 승인 됨
                    Toast.makeText(getActivity(), "권한 승인됨", Toast.LENGTH_SHORT).show();


                    getPicture();


                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.


                    // 앱을 종료합니다
                    Toast.makeText(getActivity(), "권한 거부됨", Toast.LENGTH_SHORT).show();

                    getActivity().finish();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void getPicture() {
        // 사진 정보
        // 미디어(사진, 동영상, 음악) media db
        // provider로 media db 정보를 가져와야 됨
        Cursor cursor = getActivity().getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                null
        );

        // 사진을 뿌릴 어댑터
        MyCursorAdapter adapter = new MyCursorAdapter(getActivity(), cursor);
        mGridView.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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


    private static class MyCursorAdapter extends CursorAdapter {

        public MyCursorAdapter(Context context, Cursor c) {
            super(context, c, false);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_gallery, parent, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.image_view);
            convertView.setTag(viewHolder);

            return convertView;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            ViewHolder viewHolder = (ViewHolder) view.getTag();

            String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));

            Glide.with(context).load(path).into(viewHolder.imageView);
        }

    }

    private static class ViewHolder {
        ImageView imageView;
    }
}
