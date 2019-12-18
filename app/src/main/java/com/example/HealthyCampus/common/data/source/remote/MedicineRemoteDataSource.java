package com.example.HealthyCampus.common.data.source.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.MedicineDataSource;
import com.example.HealthyCampus.common.network.NetworkManager;
import com.example.HealthyCampus.common.network.vo.MedicineDetailVo;
import com.example.HealthyCampus.common.network.vo.MedicineListVo;
import com.example.HealthyCampus.common.network.vo.MedicineVo;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MedicineRemoteDataSource implements MedicineDataSource {

    private static MedicineRemoteDataSource INSTANCE = null;


    public MedicineRemoteDataSource() {
    }

    public static MedicineRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MedicineRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getAllClassify(@NonNull MedicineAllClassify callback) {
        NetworkManager.getInstance().getMedicineApi()
                .getAllClassify()
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e("MedicineRemoteD" + "123456", "getAllClassify:7");
                    }
                })
                .subscribe(new Action1<List<MedicineVo>>() {
                    @Override
                    public void call(List<MedicineVo> medicineVos) {
                        try {
                            Log.e("MedicineRemoteD" + "123456", "getAllClassify success");
                            callback.onDataAvailable(medicineVos);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        try {
                            Log.e("MedicineRemoteD" + "123456", "getAllClassify error");
                            callback.onDataNotAvailable(throwable);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void getAllMedicine(@NonNull RequestForm requestForm, @NonNull MedicineGetAllMedicine callback) {
        NetworkManager.getInstance().getMedicineApi()
                .getAllMedicine(requestForm)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e("MedicineRemoteD" + "123456", "getAllMedicine:7");
                    }
                })
                .subscribe(new Action1<List<MedicineListVo>>() {
                    @Override
                    public void call(List<MedicineListVo> medicineVos) {
                        try {
                            Log.e("MedicineRemoteD" + "123456", "getAllMedicine success");
                            callback.onDataAvailable(medicineVos);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        try {
                            Log.e("MedicineRemoteD" + "123456", "getAllMedicine error");
                            callback.onDataNotAvailable(throwable);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void getMedicineDetail(@NonNull RequestForm requestForm, @NonNull MedicineGetMedicineDetail callback) {
        NetworkManager.getInstance().getMedicineApi()
                .getMedicineDetail(requestForm)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e("MedicineRemoteD" + "123456", "getMedicineDetail:7");
                    }
                })
                .subscribe(new Action1<MedicineDetailVo>() {
                    @Override
                    public void call(MedicineDetailVo medicineVos) {
                        try {
                            Log.e("MedicineRemoteD" + "123456", "getMedicineDetail success");
                            callback.onDataAvailable(medicineVos);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        try {
                            Log.e("MedicineRemoteD" + "123456", "getMedicineDetail error");
                            callback.onDataNotAvailable(throwable);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void getAllMedicineByKey(@NonNull RequestForm requestForm, @NonNull MedicineGetAllMedicine callback) {
        NetworkManager.getInstance().getMedicineApi()
                .getAllMedicineByKey(requestForm)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e("MedicineRemoteD" + "123456", "getAllMedicineByKey:7");
                    }
                })
                .subscribe(new Action1<List<MedicineListVo>>() {
                    @Override
                    public void call(List<MedicineListVo> medicineVos) {
                        try {
                            Log.e("MedicineRemoteD" + "123456", "getAllMedicineByKey success");
                            callback.onDataAvailable(medicineVos);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        try {
                            Log.e("MedicineRemoteD" + "123456", "getAllMedicineByKey error");
                            callback.onDataNotAvailable(throwable);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


}
