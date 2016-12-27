package ks;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Streaming;
import rx.Observable;

/**
 * Created by azkf-XT on 2016/12/19.
 */

public interface API {
    @POST("/api/def")
    Observable<ResponseBody> getComments(@Body RequestBody request);

    @POST("/api/def")
    Observable<A.MobadsResponse> getComments(@Body A.MobadsRequest request);
}
