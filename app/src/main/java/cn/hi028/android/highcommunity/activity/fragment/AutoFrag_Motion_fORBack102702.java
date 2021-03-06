//package cn.hi028.android.highcommunity.activity.fragment;
//
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageButton;
//import android.widget.ListView;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.TextView;
//
//import com.don.tools.BpiHttpHandler;
//
//import net.duohuo.dhroid.activity.BaseFragment;
//import net.duohuo.dhroid.util.LogUtil;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import cn.hi028.android.highcommunity.R;
//import cn.hi028.android.highcommunity.activity.AutonomousAct_Third;
//import cn.hi028.android.highcommunity.adapter.AutoMoitionAdapter;
//import cn.hi028.android.highcommunity.bean.Autonomous.Auto_MotionBean;
//import cn.hi028.android.highcommunity.utils.HTTPHelper;
//import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
//import cn.hi028.android.highcommunity.view.MyCustomViewPager;
//
///**
// * @功能：自治大厅 提案<br>
// * @作者： Lee_yting<br>
// * @时间：2016/10/11<br>
// */
//
//public class AutoFrag_Motion_fORBack102702 extends BaseFragment {
//    public static final String Tag = "~~~AutoFrag_Motion~~~";
//    public static final String FRAGMENTTAG = "AutoFrag_Motion";
//    /**
//     * 创建提案
//     **/
//    public static final int TAG_CREAT_MOTION = 7;
//    AutoMoitionAdapter mAdapter;
//    List<Auto_MotionBean.MotionDataEntity> mList;
//    @Bind(R.id.tv_Automotion_Nodata)
//    TextView tv_Nodata;
//    @Bind(R.id.frag_Automotion_listview)
//    ListView mListview;
//    @Bind(R.id.img_Automotion_creat)
//    ImageButton but_CreatMotion;
//
//
//
//    @Bind(R.id.frag_motion_motionlist)
//    RadioButton motionList;
//    @Bind(R.id.frag_motion_mymotion)
//    RadioButton mMymotion;
//    @Bind(R.id.frag_motion_Radiogroup)
//    RadioGroup mRadiogroup;
//    @Bind(R.id.frag_motion_ViewPager)
//    MyCustomViewPager mViewPager;
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        LogUtil.d(Tag + "onCreateView");
//        View view = inflater.inflate(R.layout.frag_auto_motion_twopage, null);
//        ButterKnife.bind(this, view);
//        initView();
//        return view;
//    }
//
//    void initView() {
//        LogUtil.d(Tag + "initView");
//        mList = new ArrayList<Auto_MotionBean.MotionDataEntity>();
//        mAdapter = new AutoMoitionAdapter(mList, getActivity(), getActivity().getWindow().getDecorView());
//        mListview.setEmptyView(tv_Nodata);
//        mListview.setAdapter(mAdapter);
//        initDatas();
//    }
//
//    private void initDatas() {
//
//        HTTPHelper.GetAutoMotionList(mIbpi);
//    }
//
//
//    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
//        @Override
//        public void onError(int id, String message) {
//            LogUtil.d(Tag + "---~~~onError");
//            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
//        }
//
//        @Override
//        public void onSuccess(Object message) {
//            mList = (List<Auto_MotionBean.MotionDataEntity>) message;
//            mAdapter.AddNewData(mList);
//            mListview.setAdapter(mAdapter);
//        }
//
//        @Override
//        public Object onResolve(String result) {
//            return HTTPHelper.ResolveMotionDataEntity(result);
//        }
//
//        @Override
//        public void setAsyncTask(AsyncTask asyncTask) {
//
//        }
//
//        @Override
//        public void cancleAsyncTask() {
//        }
//    };
//
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        LogUtil.d(Tag + "onResume");
//        initDatas();
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        ButterKnife.unbind(this);
//    }
//
//    @OnClick(R.id.img_Automotion_creat)
//    public void onClick() {
//
//        ceratMotion();
//
//    }
//
//    private void ceratMotion() {
//
//        Intent mIntent_report = new Intent(getActivity(), AutonomousAct_Third.class);
//        mIntent_report.putExtra("title", TAG_CREAT_MOTION);
//        startActivity(mIntent_report);
//
//    }
//
//
//}
