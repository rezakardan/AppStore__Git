package com.example.appstore.data.server

import com.example.appstore.data.model.SimpleResponse
import com.example.appstore.data.model.cart.*
import com.example.appstore.data.model.category.ResponseCategory
import com.example.appstore.data.model.comment.ResponseDeleteComments
import com.example.appstore.data.model.comment.ResponseGetComment
import com.example.appstore.data.model.detail.*
import com.example.appstore.data.model.favorite.ResponseDeleteFavorite
import com.example.appstore.data.model.favorite.ResponseGetFavorite
import com.example.appstore.data.model.home.ResponseBanners
import com.example.appstore.data.model.home.ResponseDiscount
import com.example.appstore.data.model.home.ResponseProducts
import com.example.appstore.data.model.login.BodyResponseLogin
import com.example.appstore.data.model.login.ResponseLogin
import com.example.appstore.data.model.login.ResponseVerify
import com.example.appstore.data.model.product.ResponseProductCategory
import com.example.appstore.data.model.profile.ResponseProfile
import com.example.appstore.data.model.profile.BodyUpdateProfile
import com.example.appstore.data.model.profile_order.ResponseProfileOrders
import com.example.appstore.data.model.search.ResponseSearch
import com.example.appstore.data.model.shipping.*
import com.example.appstore.data.model.wallet.ResponseGetWallet
import com.example.appstore.data.model.wallet.BodyIncreaseWallet
import com.example.appstore.data.model.wallet.ResponseIncreaseWallet
import com.example.appstore.data.profile_address.BodyResponseInsertOrUpdate
import com.example.appstore.data.profile_address.ResponseInsertOrUpdateAddress
import com.example.appstore.data.profile_address.ResponseProfileAddresses
import com.example.appstore.data.profile_address.ResponseProvinceOrCity
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {


    @POST("auth/login")
    suspend fun postLogin(@Body body: BodyResponseLogin): Response<ResponseLogin>

    @POST("auth/login/verify")
    suspend fun postVerifyLogin(@Body body: BodyResponseLogin): Response<ResponseVerify>


    @GET("auth/detail")
    suspend fun getProfile(): Response<ResponseProfile>


    @GET("ad/swiper/{slug}")
    suspend fun getBannersListHome(@Path("slug") slug: String): Response<ResponseBanners>


    @GET("offers/discount/{slug}")
    suspend fun getDiscount(@Path("slug") slug: String): Response<ResponseDiscount>

    @GET("category/pro/{slug}")
    suspend fun getMobilesList(
        @Path("slug") slug: String,
        @Query("sort") sort: String
    ): Response<ResponseProducts>

    @GET("category/pro/{slug}")
    suspend fun getShoesList(
        @Path("slug") slug: String,
        @Query("sort") sort: String
    ): Response<ResponseProducts>

    @GET("category/pro/{slug}")
    suspend fun getLapTopList(
        @Path("slug") slug: String,
        @Query("sort") sort: String
    ): Response<ResponseProducts>

    @GET("category/pro/{slug}")
    suspend fun getStationaryList(
        @Path("slug") slug: String,
        @Query("sort") sort: String
    ): Response<ResponseProducts>


    @GET("search")
    suspend fun getSearch(
        @Query("q") q: String,
        @Query("sort") sort: String
    ): Response<ResponseSearch>

    @GET("menu")
    suspend fun getCategory(): Response<ResponseCategory>

    @GET("category/pro/{slug}")
    suspend fun getProductCategory(
        @Path("slug") slug: String,
        @Query("search") search: String? = null,
        @Query("sort") sort: String? = null,
        @Query("minPrice") minPrice: String? = null,
        @Query("maxPrice") maxPrice: String? = null,
        @Query("selectedBrands") selectedBrands: String? = null,
        @Query("selectedColors") selectedColors: String? = null,
        @Query("page ") page: String? = "1",
        @Query("onlyAvailable") onlyAvailable: Boolean? = null,
    ): Response<ResponseProductCategory>


    @GET("auth/wallet")
    suspend fun getWallet(): Response<ResponseGetWallet>


    @POST("auth/avatar")
    suspend fun postUploadAvatar(@Body body: RequestBody): Response<Unit>


    @PUT("auth/update")
    suspend fun updateProfile(@Body body: BodyUpdateProfile): Response<ResponseProfile>


    @POST("auth/wallet")
    suspend fun postIncreaseWallet(@Body body: BodyIncreaseWallet): Response<ResponseIncreaseWallet>


    @GET("auth/comments")
    suspend fun getComment(): Response<ResponseGetComment>

    @DELETE("auth/comment/{commentId}")
    suspend fun deleteComments(@Path("commentId") commentId: Int): Response<ResponseDeleteComments>

    @GET("auth/favorites")
    suspend fun getFavorites(): Response<ResponseGetFavorite>

    @DELETE("auth/favorite/{favoriteId}")
    suspend fun deleteFavorites(@Path("favoriteId") favoriteId: Int): Response<ResponseDeleteFavorite>


    @GET("auth/addresses")
    suspend fun profileAddresses(): Response<ResponseProfileAddresses>


    @GET("auth/address/provinces")
    suspend fun getProvinceAddress(): Response<ResponseProvinceOrCity>


    @GET("auth/address/provinces")
    suspend fun getCityAddress(@Query("provinceId") provinceId: Int): Response<ResponseProvinceOrCity>


    @POST("auth/address")
    suspend fun insertOrUpdateAddress(@Body body: BodyResponseInsertOrUpdate): Response<ResponseInsertOrUpdateAddress>

    @DELETE("auth/address/remove/{address_Id}")
    suspend fun deleteAddress(@Path("address_Id") address_Id: Int): Response<ResponseDeleteComments>
@GET("auth/orders")
suspend fun profileOrders(@Query("status")status:String):Response<ResponseProfileOrders>


@GET("product/{id}")
suspend fun getDetailProducts(@Path("id")id:Int):Response<ResponseDetail>

@POST("product/{id}/like")
suspend fun postFavoriteDetail(@Path("id")id:Int):Response<ResponseFavoriteDetail>
@POST("product/{id}/add_to_cart")
suspend fun addToCartDetail(@Path("id")id:Int,@Body body:BodyAddToCart):Response<SimpleResponse>



@GET("product/{id}/features")
suspend fun detailFeatures(@Path("id")id:Int):Response<ResponseDetailFeatures>

@GET("product/{id}/comments")
suspend fun getDetailComment(@Path("id")id:Int):Response<ResponseGetDetailComment>

@POST("product/{id}/comments")
suspend fun postDetailAddComment(@Path("id")id:Int,@Body body:BodyDetailAddComment):Response<SimpleResponse>


@GET("product/{id}/price-chart")
suspend fun detailPriceChart(@Path("id")id:Int):Response<ResponseDetailPriceChart>

@GET("cart")
suspend fun getCartList():Response<ResponseGetCartList>

@PUT("cart/{id}/increment")
suspend fun putIncrementCart(@Path("id")id:Int):Response<ResponseUpdateCart>

@PUT("cart/{id}/decrement")
suspend fun putDecrementCart(@Path("id")id:Int):Response<ResponseUpdateCart>

@DELETE("cart/{id}")
suspend fun deleteProductFromCart(@Path("id")id:Int):Response<ResponseUpdateCart>


@GET("cart/continue")
suspend fun cartContinue():Response<ResponseCartContinue>

@GET("shipping")
suspend fun getShippingList():Response<ResponseShippingList>

@PUT("shipping/set/address")
suspend fun setAddress(@Body body:BodySetAddress):Response<ResponseSetAddress>

@POST("check/coupon")
suspend fun callCouponShippingApi(@Body body:BodyCouponShipping):Response<ResponseCouponShipping>

    @POST("payment")
    suspend fun postPayment(@Body body: BodyCouponShipping): Response<ResponseIncreaseWallet>
}