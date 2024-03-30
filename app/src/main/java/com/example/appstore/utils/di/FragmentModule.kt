package com.example.appstore.utils.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import com.example.appstore.data.model.cart.BodyAddToCart
import com.example.appstore.data.model.detail.BodyDetailAddComment
import com.example.appstore.data.model.login.BodyResponseLogin
import com.example.appstore.data.model.profile.BodyUpdateProfile
import com.example.appstore.data.model.shipping.BodyCouponShipping
import com.example.appstore.data.model.shipping.BodySetAddress
import com.example.appstore.data.model.wallet.BodyIncreaseWallet
import com.example.appstore.data.profile_address.BodyResponseInsertOrUpdate
import com.example.appstore.ui.detail.adapters.PagerAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object FragmentModule {



@Provides
fun providePagerAdapter(fragment: Fragment)=PagerAdapter(fragment.parentFragmentManager,fragment.lifecycle)



    @Provides
    fun provideBodyAddToCart()= BodyAddToCart()

    @Provides
    fun provideBodyIncreaseWallet()= BodyIncreaseWallet()


    @Provides
    fun provideBodyInsertOrUpdate()= BodyResponseInsertOrUpdate()

    @Provides
    fun provideBodyLogin()= BodyResponseLogin()


    @Provides
    fun provideUpdateProfile()= BodyUpdateProfile()

    @Provides
    fun bodyDetailAddComment()=BodyDetailAddComment()


    @Provides
    fun bodySetAddress()=BodySetAddress()

    @Provides
    fun bodyCoupon()=BodyCouponShipping()

}