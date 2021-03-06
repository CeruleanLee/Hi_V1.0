/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.activity.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.don.tools.BpiHttpHandler;

import net.duohuo.dhroid.activity.BaseFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.AddressModifyAct;
import cn.hi028.android.highcommunity.activity.SearchActivity;
import cn.hi028.android.highcommunity.bean.AddressModifyBean;
import cn.hi028.android.highcommunity.bean.CitysBean;
import cn.hi028.android.highcommunity.bean.DistrictBean;
import cn.hi028.android.highcommunity.bean.VallageBean;
import cn.hi028.android.highcommunity.utils.Constacts;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.utils.RegexValidateUtil;
import cn.hi028.android.highcommunity.view.ShowListUtils;

/**
 * @功能：修改地址界面<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016/1/21<br>
 */
@EFragment(resName = "frag_addressmodify")
public class AddressModifyFrag extends BaseFragment {
    static final String Tag = "AddressModifyFrag";
    public static final String FRAGMENTTAG = "AddressModifyFrag";
    @ViewById(R.id.tv_AddressModify_SetDefult)
    TextView isDefult;
    @ViewById(R.id.et_addressModify_name)
    EditText mName;
    @ViewById(R.id.et_addressModify_phone)
    EditText mPhone;
    @ViewById(R.id.et_addressModify_city)
    TextView mCity;
    @ViewById(R.id.et_addressModify_quxian)
    TextView mQuxian;
    @ViewById(R.id.et_addressModify_xiaoqu)
    TextView mXiaoqu;
    @ViewById(R.id.et_addressModify_build)
    EditText mBuild;
    @ViewById(R.id.et_addressModify_unit)
    EditText mUnit;
    @ViewById(R.id.et_addressModify_doorNumber)
    EditText mDoorNumber;
    @ViewById(R.id.ll_AddressModify_DefultLayout)
    LinearLayout mDefultLayout;
    @ViewById(R.id.tv_AddressModify_SetDefult)
    TextView mIsDefult;

    @ViewById(R.id.progress_addressModify)
    View mProgress;
    @ViewById(R.id.tv_addressModify_Nodata)
    TextView mNodata;
    @ViewById(R.id.et_addressModify_area)
    TextView mArea;
    @ViewById(R.id.et_addressModify_detailAdress)
    EditText mDetailAdress;
    @ViewById(R.id.vMasker)
    View vMasker;


    boolean defult = false;
    String defulttag = "0";
    String aid = "";
    PopupWindow mWaitingWindow, mListWindow;
    AddressModifyBean mModifyBean;
    ArrayList<CitysBean> mCityBean;
    ArrayList<DistrictBean> mDistrictBean;
    ArrayList<VallageBean> mVillageBean = new ArrayList<VallageBean>();
    String CityId, QuxianId, XiaoquId;
    /**
     * 城市区县小区选择器
     **/
    OptionsPickerView pvOptions;
    private ArrayList<CitysBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<DistrictBean>> options2Items = new ArrayList<>();
    ArrayList<DistrictBean> options2Items_01 = new ArrayList<DistrictBean>();

    @AfterViews
    void initView() {
        aid = getActivity().getIntent().getStringExtra(AddressModifyAct.INTENTTAG);
        if (!TextUtils.isEmpty(aid)) {
            mProgress.setVisibility(View.VISIBLE);
            HTTPHelper.getAddressDetail(mIbpi, aid);
        } else {
            mProgress.setVisibility(View.GONE);
            HTTPHelper.getAddressCity(mCityIbpi);
        }
        mDefultLayout.setOnClickListener(mClick);
        mCity.setOnClickListener(mClick);
        mQuxian.setOnClickListener(mClick);
        mXiaoqu.setOnClickListener(mClick);

        mArea.setOnClickListener(mClick);
        mDetailAdress.setOnClickListener(mClick);
        //选项选择器
        pvOptions = new OptionsPickerView(getActivity());
        //选项1
        options1Items.add(new CitysBean("成都市", "5101"));
//        //选项2
//        options2Items.add(options2Items_01);
//        //三级联动效果
//        pvOptions.setPicker(options1Items, options2Items, true);
////        //设置选择的三级单位
////        pvOptions.setLabels("城市", "区县", "小区");
//
//        pvOptions.setTitle("选择所在地区");
//        pvOptions.setCyclic(false,false,false);
//        //设置默认选中的三级项目
//        pvOptions.setSelectOptions(0, 0, 0);
        //监听确定选择按钮
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getCity() +" "+ options2Items.get(options1).get(option2).getDistrict();
                mArea.setText(tx);
                vMasker.setVisibility(View.GONE);
            }
        });

    }

    View.OnClickListener mClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ll_AddressModify_DefultLayout:
                    if (defult) {
                        defult = false;
                        mIsDefult.setSelected(false);
                        defulttag = "0";
                    } else {
                        defult = true;
                        defulttag = "1";
                        mIsDefult.setSelected(true);
                    }
                    break;
                case R.id.et_addressModify_city:
                    mListWindow = ShowListUtils.GetInstantiation().ShowCityList(getActivity(), mCity, mBack, mCityBean);
                    break;
                case R.id.et_addressModify_quxian:
                    mListWindow = ShowListUtils.GetInstantiation().ShowDistrictList(getActivity(), mQuxian, mBack, mDistrictBean);
                    break;
                case R.id.et_addressModify_xiaoqu:
                    if (TextUtils.isEmpty(CityId)) {
                        HighCommunityUtils.GetInstantiation().ShowToast("先选择城市", 0);
                        return;
                    }
                    if (TextUtils.isEmpty(QuxianId)) {
                        HighCommunityUtils.GetInstantiation().ShowToast("先选择区县=", 0);
                        return;
                    }
                    SearchActivity.toSearch(AddressModifyFrag.this, mXiaoqu, mXiaoqu.getHeight(), QuxianId);
                    break;
                case R.id.et_addressModify_area:
                    pvOptions.show();


                    break;
                case R.id.et_addressModify_detailAdress:


                    break;
            }
        }
    };

    ShowListUtils.OnItemClickBack mBack = new ShowListUtils.OnItemClickBack() {
        @Override
        public void onClick(Object mBack) {
            if (mBack instanceof CitysBean) {
                mCity.setText(((CitysBean) mBack).getCity());
                CityId = ((CitysBean) mBack).getCity_code();
                HTTPHelper.getAddressDistrist(mDistristIbpi, CityId);
            } else if (mBack instanceof DistrictBean) {
                mQuxian.setText(((DistrictBean) mBack).getDistrict());
                QuxianId = ((DistrictBean) mBack).getDistrict_code();
            }

        }
    };

/**选择城市区县  城市已经固定只能选成都 **/
    BpiHttpHandler.IBpiHttpHandler mDistristIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            if (null == message)
                return;
            mDistrictBean = (ArrayList<DistrictBean>) message;
            options2Items_01 = mDistrictBean;
            //选项2
            options2Items.add(mDistrictBean);
            pvOptions.setPicker(options1Items, options2Items, true);
            pvOptions.setTitle("请选择所在区县");
            pvOptions.setCyclic(false, false, false);
            //设置默认选中的三级项目
            pvOptions.setSelectOptions(0, 0, 0);
        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveDistrict(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {

        }
    };
    /**
     * 修改地址
     **/
    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mProgress.setVisibility(View.GONE);
        }

        @Override
        public void onSuccess(Object message) {
            mProgress.setVisibility(View.GONE);
            if (null == message)
                return;
            mModifyBean = (AddressModifyBean) message;
            mCityBean = mModifyBean.getCities();

            mDistrictBean = mModifyBean.getDistricts();
            mName.setText(mModifyBean.getAddress().getReal_name());
            mPhone.setText(mModifyBean.getAddress().getTel());
            mXiaoqu.setText(mModifyBean.getAddress().getVillage());
            mBuild.setText(mModifyBean.getAddress().getBuilding().substring(0, mModifyBean.getAddress().getBuilding().length() - 1));
            if (mModifyBean.getAddress().getUnit() != null && mModifyBean.getAddress().getUnit().length() > 2)
                mUnit.setText(mModifyBean.getAddress().getUnit().substring(0, mModifyBean.getAddress().getUnit().length() - 2));
            mDoorNumber.setText(mModifyBean.getAddress().getDoorNum().substring(0, mModifyBean.getAddress().getDoorNum().length() - 1));
            if (mModifyBean.getAddress().getIsDefault().equals("1")) {
                mIsDefult.setSelected(true);
                defulttag = "1";
                mDefultLayout.setClickable(false);
            } else {
                mIsDefult.setSelected(false);
                defulttag = "0";
                mDefultLayout.setClickable(true);
            }
            CityId = mModifyBean.getAddress().getCity_code();
            QuxianId = mModifyBean.getAddress().getDistrict_code();
            XiaoquId = mModifyBean.getAddress().getVid();
            for (int i = 0; i < mModifyBean.getCities().size(); i++) {
                if (mModifyBean.getCities().get(i).getCity_code().equals(mModifyBean.getAddress().getCity_code())) {
                    tv_city = mModifyBean.getCities().get(i).getCity();
                    mCity.setText(mModifyBean.getCities().get(i).getCity());
                }
            }
            for (int i = 0; i < mModifyBean.getDistricts().size(); i++) {
                if (mModifyBean.getDistricts().get(i).getDistrict_code().equals(mModifyBean.getAddress().getDistrict_code())) {
                    tv_quxian = mModifyBean.getDistricts().get(i).getDistrict();
                    mQuxian.setText(mModifyBean.getDistricts().get(i).getDistrict());
                }
            }

            mArea.setText(tv_city + " " + tv_quxian);
        }

        String tv_city, tv_quxian;

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveAddressModify(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {

        }
    };

    BpiHttpHandler.IBpiHttpHandler mCityIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {

        }

        @Override
        public void onSuccess(Object message) {
            if (null == message)
                return;
            mCityBean = (ArrayList<CitysBean>) message;

        }

        @Override
        public Object onResolve(String result) {
            return HTTPHelper.ResolveCityList(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {

        }
    };

    @Click(R.id.tv_addressModify_submit)
    void submit() {
        String name = mName.getText().toString().trim();
        String phone = mPhone.getText().toString().trim();
        String build = mBuild.getText().toString().trim();
        String unit = mUnit.getText().toString().trim();
        String doornumber = mDoorNumber.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            HighCommunityUtils.GetInstantiation().ShowToast("姓名不能为空", 0);
            return;
        } else if (TextUtils.isEmpty(phone)) {
            HighCommunityUtils.GetInstantiation().ShowToast("联系方式不能为空", 0);

            return;
        } else if (TextUtils.isEmpty(CityId)) {
            HighCommunityUtils.GetInstantiation().ShowToast("城市不能为空", 0);
            return;
        } else if (TextUtils.isEmpty(QuxianId)) {
            HighCommunityUtils.GetInstantiation().ShowToast("区县不能为空", 0);
            return;
        } else if (TextUtils.isEmpty(XiaoquId)) {
            HighCommunityUtils.GetInstantiation().ShowToast("小区不能为空", 0);
            return;
        } else if (TextUtils.isEmpty(build)) {
            HighCommunityUtils.GetInstantiation().ShowToast("楼栋不能为空", 0);
            return;
        } else if (TextUtils.isEmpty(doornumber)) {
            HighCommunityUtils.GetInstantiation().ShowToast("门牌号不能为空", 0);
            return;
        } else if (!RegexValidateUtil.checkMobileNumber(mPhone.getText().toString())) {
            HighCommunityUtils.GetInstantiation().ShowToast("请输入正确的电话号码", 0);
            return;
        }
        if (!TextUtils.isEmpty(unit)) {
            unit = unit + "单元";
        }
        mWaitingWindow = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(getActivity(), mDoorNumber, Gravity.CENTER);
        if (TextUtils.isEmpty(aid)) {
            HTTPHelper.CreateAddress(mOpereteIbpi, name, phone, CityId, QuxianId, XiaoquId, build + "栋", unit, doornumber + "号", defulttag, HighCommunityApplication.mUserInfo.getId() + "");
        } else {
            HTTPHelper.ModifyAddress(mOpereteIbpi, aid, name, phone, CityId, QuxianId, XiaoquId, build + "栋", unit, doornumber + "号", defulttag, HighCommunityApplication.mUserInfo.getId() + "");
        }
    }

    BpiHttpHandler.IBpiHttpHandler mOpereteIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mWaitingWindow.dismiss();
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            mWaitingWindow.dismiss();
            if (null == message)
                return;
            HighCommunityUtils.GetInstantiation().ShowToast("数据提交成功", 0);
            getActivity().finish();

        }

        @Override
        public Object onResolve(String result) {
            return null;
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {
            mWaitingWindow.dismiss();
        }
    };

    public void onRight() {
        if (HighCommunityUtils.isLogin(getActivity())) {
            mWaitingWindow = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(getActivity(), mDoorNumber, Gravity.CENTER);
            HTTPHelper.DeleteAddress(mDeteleIbpi, aid);
        }
    }

    BpiHttpHandler.IBpiHttpHandler mDeteleIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mWaitingWindow.dismiss();
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }

        @Override
        public void onSuccess(Object message) {
            mWaitingWindow.dismiss();
            if (message == null)
                return;
            HighCommunityUtils.GetInstantiation().ShowToast("删除成功", 0);
            getActivity().finish();
        }

        @Override
        public Object onResolve(String result) {
            return null;
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {
            mWaitingWindow.dismiss();
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x22 && resultCode == 0x22 && data != null) {
            VallageBean mBean = (VallageBean) data.getSerializableExtra(Constacts.SEARCH_RESULT);
            if (mBean != null) {
                mXiaoqu.setText(mBean.getName());
                XiaoquId = mBean.getId() + "";
            }
        }

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if (pvOptions.isShowing()) {
//                pvOptions.dismiss();
//                return true;
//            }
//        }
        Log.e(Tag,"pvOptions.isShowing()--->"+pvOptions.isShowing());
        if (pvOptions!=null&&pvOptions.isShowing()) {
            pvOptions.dismiss();
            return true;
        }
        if (mListWindow != null && mListWindow.isShowing()) {
            mListWindow.dismiss();
            System.out.println("mlist window has dismissed");
            return true;
        } else if (mWaitingWindow != null && mWaitingWindow.isShowing()) {
            mWaitingWindow.dismiss();
            return true;
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
//        CityId = ((CitysBean) mBack).getCity_code();
        HTTPHelper.getAddressDistrist(mDistristIbpi, "5101");
    }
}
