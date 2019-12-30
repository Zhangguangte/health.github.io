package com.example.HealthyCampus.common.network.api;

import com.example.HealthyCampus.common.data.form.ConsultPictureForm;
import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.network.vo.BookDetailVo;
import com.example.HealthyCampus.common.network.vo.BookVo;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.nostra13.universalimageloader.utils.L;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface ServiceApi {

    @POST("/service/library/searchBook/")
    Observable<List<BookVo>> searchBook(@Body RequestForm requestForm);

    @POST("/service/library/searchBookDetail/")
    Observable<BookDetailVo> searchBookDetail(@Body RequestForm requestForm);
}
