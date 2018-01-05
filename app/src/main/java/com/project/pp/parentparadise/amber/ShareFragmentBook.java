package com.project.pp.parentparadise.amber;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.pp.parentparadise.R;

/**
 * Created by Amber on 2017/12/5.
 */

public class ShareFragmentBook extends Fragment {

    FloatingActionButton fabAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.share_recyclerview, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(
                        1, StaggeredGridLayoutManager.VERTICAL));
//        List<ShareData> dataList = getDataList();
//        recyclerView.setAdapter(new DataAdapter(getActivity(), dataList));

        fabAdd = view.findViewById(R.id.fabAdd);
        return view;
    }

//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        fabAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //  從1 Activity的fragment跳轉到 2 Activity，要取得getActivity()
//                Intent intent = new Intent(getActivity(), ShareAddArticle.class);
//                startActivity(intent);
//            }
//        });
//    }
//
//
//    class DataAdapter extends RecyclerView.Adapter<ShareFragmentBook.DataAdapter.MyViewHolder> {
//        private Context context;
//        private List<ShareData> dataList;
//
//        DataAdapter(Context context, List<ShareData> dataList) {
//            this.context = context;
//            this.dataList = dataList;
//        }
//
//        class MyViewHolder extends RecyclerView.ViewHolder {
//            ImageView ivHead;
//            TextView tvName;
//            TextView tvTime;
//            ImageView ivPhoto;
//            TextView tvBookName;
//            TextView tvAuthor;
//            TextView tvPublisher;
//            TextView tvISBN;
//            TextView tvDescribe;
//            TextView tvGCount;
//            TextView tvMCount;
//            TextView tvMore;
//
//
//            MyViewHolder(View itemView) {
//                super(itemView);
//                ivHead = itemView.findViewById(R.id.ivHead);
//                tvName = itemView.findViewById(R.id.tvName);
//                tvTime = itemView.findViewById(R.id.tvTime);
//                ivPhoto = itemView.findViewById(R.id.ivPhoto);
//                tvBookName = itemView.findViewById(R.id.tvBookName);
//                tvAuthor = itemView.findViewById(R.id.tvAuthor);
//                tvPublisher = itemView.findViewById(R.id.tvPublisher);
//                tvISBN = itemView.findViewById(R.id.tvISBN);
//                tvDescribe = itemView.findViewById(R.id.tvDescribe);
//                tvMCount = itemView.findViewById(R.id.tvMCount);
//                tvGCount = itemView.findViewById(R.id.tvGCount);
//                tvMore = itemView.findViewById(R.id.tvMore);
//            }
//        }
//
//        @Override
//        public int getItemCount() {
//            return dataList.size();
//        }
//
//        @Override
//        public ShareFragmentBook.DataAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
//            LayoutInflater layoutInflater = LayoutInflater.from(context);
//            View itemView = layoutInflater.inflate(R.layout.share_itemview_book, viewGroup, false);
//            return new MyViewHolder(itemView);
//        }
//
//        @Override
//        public void onBindViewHolder(final ShareFragmentBook.DataAdapter.MyViewHolder holder, int position) {
//            final ShareData data = dataList.get(position);
//            holder.ivHead.setImageResource(data.getHead());
//            holder.tvName.setText(data.getName());
//            holder.tvTime.setText(data.getTime());
//            holder.tvBookName.setText(data.getBookName());
//            holder.tvAuthor.setText(data.getAuthor());
//            holder.tvPublisher.setText(data.getPublisher());
//            holder.tvISBN.setText(data.getIsbn());
//            if(holder.tvDescribe.length()>11){
//                holder.tvMore.setVisibility(View.VISIBLE);
//                holder.tvDescribe.setText(data.getDescribe().substring(0,10));
//            } else{
//                holder.tvDescribe.setText(data.getDescribe());
//            }
//            holder.tvGCount.setText(String.valueOf(data.getGoodCount()));
//            holder.tvMCount.setText(String.valueOf(data.getMessage()));
//
//            holder.tvMore.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    holder.tvMore.setVisibility(View.GONE);
//                    LinearLayout.LayoutParams ll =
//                            new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                                    LinearLayout.LayoutParams.WRAP_CONTENT);
//                    holder.tvDescribe.setText(data.getDescribe());
//                    holder.tvDescribe.setLayoutParams(ll);
//                }
//            });
//        }
//    }
//
//    public List<ShareData> getDataList() {
//        List<ShareData> dateList = new ArrayList<>();
//        int [] photos1 = new int[]{}
    //        dateList.add(new ShareData(R.drawable.amber_icon_head01, "Amily", "10分鐘前",  "猜猜我有多愛你", "山姆．麥克布雷尼", "上誼文化", "9789577620682", "小兔子以各種比喻向大兔子挑釁，要比比看誰的愛比較多......在簡短但純真的對話中，展露無邊的親情，溫馨感人。淡雅流暢的圖畫，生動的勾畫出親子間的生活情態，引人會心微笑。", 104, 4));
//        dateList.add(new ShareData(R.drawable.amber_icon_head02, "Carol", "20分鐘前",  "刺蝟溫迪琪的故事", " 碧雅翠絲．波特", "青林", "9789862740361", "碧雅翠絲根據自己童年在蘇格蘭度假時遇見的一位洗衣婦人，來創作「刺蝟溫迪琪太太」這個角色，動物模特兒是她自己的寵物刺蝟，牠真是最不合作的模特兒了，碧雅翠斯曾寫信和她的編輯諾曼說，「她先是可憐兮兮的打呵欠，然後又咬我。」", 99, 3));
//        dateList.add(new ShareData(R.drawable.amber_icon_head03, "Julie", "30分鐘前",  "小藍和小黃", "李歐．李奧尼", "上誼文化", "9789577625694", "《小藍和小黃》，是一部世人公認的抽象派作品。在這本圖畫書裡，作者完全摒棄了我們常見的具象，用一藍一黃兩個近乎圓形的抽象的色塊，象徵兩個孩子，講述了一個關於愛與融合的故事。", 67, 6));
//        dateList.add(new ShareData(R.drawable.amber_icon_head04, "Bonny", "40分鐘前",  "這是我的！這是我的！", " 米歇爾．凡．傑弗恩", "上誼文化", "9789577625571", "在《這是我的！這是我的！》一書中，故事的背景不是兒童遊戲場，而是險惡的叢林。故事一開始，一隻小青蛙撿到一顆白色的蛋，他開心地抱著蛋說：「這是我的！」。然而，蛋真的是沒有主人的嗎？誰撿到，就歸誰嗎？", 98, 7));
//        return dateList;
//    }

}
