package com.zikre.bazmeauliya.api

import com.zikre.bazmeauliya.model.login.LoginResponse
import com.zikre.bazmeauliya.model.login.MobileNoRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/*import com.atlas.vms.models.CapturePhotoRequest
import com.atlas.vms.models.CommonResponse
import com.atlas.vms.models.add_details.AddUnScheduledRequest
import com.atlas.vms.models.add_details.AddVendorRequest
import com.atlas.vms.models.login.LoginResponse
import com.atlas.vms.models.login.MobileNoRequest
import com.atlas.vms.models.login.OTPRequest
import com.atlas.vms.models.login.OTPResponse
import com.atlas.vms.models.login.UserDataResponse
import com.atlas.vms.models.main_view.LeadRequest
import com.atlas.vms.models.main_view.LeadResponse
import com.atlas.vms.models.masters.*
import com.atlas.vms.models.qr_checkin.CheckInQRRequest
import com.atlas.vms.models.qr_checkin.CheckInQRResponse
import com.atlas.vms.models.visitors_detail.CapturePhotoResponse
import com.atlas.vms.models.visitors_detail.ResendPinRequest
import com.atlas.vms.models.visitors_detail.UpdateCheckInOutRequest
import com.atlas.vms.models.visitors_detail.VisitorRequest*/

interface ApiInterface {


    @POST("/walkinform/api/loginOTP")
    suspend fun loginWithMobile(@Body body: MobileNoRequest): Response<LoginResponse>
    /*
        @POST("/walkinform/api/qrCheckIn")
        suspend fun checkInQR(@Body body : CheckInQRRequest): Response<CheckInQRResponse>

        @POST("/walkinform/api/uploadImage")
        suspend fun uploadCapturePhoto(@Body body : CapturePhotoRequest): Response<CapturePhotoResponse>
    */

}
