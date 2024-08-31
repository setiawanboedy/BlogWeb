package com.tafakkur.blogweb.navigation

sealed class Screen(val route: String){
    data object AdminHome : Screen(route = "/admin/")
    data object AdminLogin : Screen(route = "/admin/login")
}