package com.test.mateflick.utils.network;


import com.test.mateflick.utils.network.request.response.ChatItem;
import com.test.mateflick.utils.network.request.response.CreateEventResponse;
import com.test.mateflick.utils.network.request.response.CreateWishListResponse;
import com.test.mateflick.utils.network.request.response.GetMyChatsResponse;
import com.test.mateflick.utils.network.request.response.GetMyEventsResponse;
import com.test.mateflick.utils.network.request.response.LoginResponse;
import com.test.mateflick.utils.network.request.response.UpdateAboutResponse;
import com.test.mateflick.utils.network.request.response.UpdateCoverResponse;
import com.test.mateflick.utils.network.request.response.UpdateNameResponse;
import com.test.mateflick.utils.network.request.response.UpdateProfilePicResponse;
import com.test.mateflick.utils.network.request.response.UserRegistrationResponse;
import com.test.mateflick.utils.network.request.response.model.Event;
import com.test.mateflick.utils.network.request.response.model.User;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface BirthDayNetworkInterface {

    @Multipart
    @POST("register")
    Call<UserRegistrationResponse> registerUser(@Part("name") RequestBody name,
                                                @Part("email") RequestBody email,
                                                @Part("password") RequestBody password,
                                                @Part("dob") RequestBody dob,
                                                @Part("country") RequestBody country,
                                                @Part MultipartBody.Part image,
                                                @Part("latitude") RequestBody latitude,
                                                @Part("longitude") RequestBody longitude

    );


    @GET("login/{email}/{password}")
    Call<LoginResponse> login(@Path("email") String email, @Path("password") String password);

    @FormUrlEncoded
    @POST("user_update_name/{id}")
    Call<UpdateNameResponse> updateMyName(@Path("id") String id, @Field("name") String name);

    @FormUrlEncoded
    @POST("user_update_about/{id}")
    Call<UpdateAboutResponse> updateMyAbout(@Path("id") String id, @Field("about") String about);

    @Multipart
    @POST("user_update_coverpic/{id}")
    Call<UpdateCoverResponse> updateMyCover(@Path("id") String id,
                                            @Part MultipartBody.Part image);

    @Multipart
    @POST("user_update_profilepic/{id}")
    Call<UpdateProfilePicResponse> updateMyProfilePic(@Path("id") String id,
                                                      @Part MultipartBody.Part cover);


    @FormUrlEncoded
    @POST("create_event/{id}")
    Call<CreateEventResponse> createEvent(@Path("id") String id,
                                          @FieldMap HashMap<String, String> params);

    @GET("list_event/{id}")
    Call<GetMyEventsResponse> getMyEvents(@Path("id") String id);

    @GET("list_othersevent/{id}")
    Call<Event[]> getOthersEvents(@Path("id") String id);

    @POST("like_events/{user_id}/{event_id}")
    Call<ResponseBody> likeOrUnlikeEvent(@Path("user_id") String userId,
                                         @Path("event_id") String eventId);

    @POST("comments_events/{user_id}/{event_id}")
    Call<ResponseBody> postComment(@Path("user_id") String userId, @Path("event_id") String eventId,
                                   @Field("comment") String comment);

    @GET("user_sort_by_distance/{id}/{lat}/{long1}")
    Call<User[]> sortUserByDistance(@Path("id") String id, @Path("lat") String latitude,
                                    @Path("long1") String longitude);


    @GET("like_events_count/{event_id}")
    Call<ResponseBody> getLikeCountsForEvents(@Path("event_id") String eventId);

    @GET("/list_my_chat/{myid}")
    Call<GetMyChatsResponse[]> listMyChat(@Path("myid") String myId);

    @GET("get_chat/{conversation_id}")
    Call<ChatItem[]> getChatMessagesForChat(@Path("conversation_id") String conversationId);

    @GET("get_unread/{conversation_id}/{read_ids}")
    Call<ChatItem[]> getUnreadMessagesForChat(@Path("conversation_id") String conversationId,
                                              @Path("read_ids") String readIds);

    @Multipart
    @POST("create_wishlist/{user_id}")
    Call<CreateWishListResponse> createWishList(@Path("user_id") String userId,
                                                @Part("text") RequestBody text,
                                                @Part("heading") RequestBody heading,
                                                @Part("link") RequestBody link,
                                                @Part("latitude") RequestBody latitude,
                                                @Part("longitude") RequestBody longitude,
                                                @Part MultipartBody.Part image);

//text,heading,link,latitude,longitude,image
    @GET("listmy_wishlist/{user_id}")
    Call<ResponseBody> getMyWishLists(@Path("user_id") String userId);

    @GET("listothers_wishlist/{user_id}")
    Call<ResponseBody> getOthersWishList(@Path("user_id") String userId);


    @GET("wish_sort_by_distance/{id}/{lat}/{long1}")
    Call<ResponseBody> sortWishListByDistance(@Path("id") String id, @Path("lat") String latitude,
                                              @Path("long") String longitude);


    @GET("current_month_birthdays")
    Call<ResponseBody> getCurrentMonthBirthdays();

    @FormUrlEncoded
    @POST("chat/{sender_id}/{receiver_id}")
    Call<String> doChat(@Path("sender_id") String senderId,
                        @Path("receiver_id") String receiverId,
                        @Field("date") String dt,
                        @Field("time") String time,
                        @Field("msg") String message);
}
