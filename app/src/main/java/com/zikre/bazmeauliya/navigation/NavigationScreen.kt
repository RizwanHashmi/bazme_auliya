package com.zikre.bazmeauliya.navigation


sealed class NavigationScreen(val route: String){

    object Splash : NavigationScreen("splash")
    object Login : NavigationScreen("login_screen")
    object OTP : NavigationScreen("otp_screen")
    object Dashboard : NavigationScreen("dashboard_screen")

    object TodayVisitors : NavigationScreen("today_visitors_screen/{tabType}/{leadData}"){
        fun createRoute(tabType: String?, leadData:String?)= "today_visitors_screen/$tabType/$leadData"
    }

    object AllVisitors : NavigationScreen("{tabType}/{dayType}/{startDate}/{endDate}/visitors_screen"){
        fun createRoute(tabType:String?,dayType:String?,startDate:String?,endDate:String?)= "$tabType/$dayType/$startDate/$endDate/visitors_screen"
    }

    object AddDetails : NavigationScreen("add_details/{tabType}/{leadData}"){
        fun createRoute(tabType: String?, leadData: String?)= "add_details/$tabType/$leadData"
    }

    object Scanner : NavigationScreen("scanner")

}
