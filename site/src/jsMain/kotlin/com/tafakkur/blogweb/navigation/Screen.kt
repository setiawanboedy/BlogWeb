package com.tafakkur.blogweb.navigation

import com.tafakkur.blogweb.models.Category
import com.tafakkur.blogweb.models.Constants.CATEGORY_PARAM
import com.tafakkur.blogweb.models.Constants.POST_ID_PARAM
import com.tafakkur.blogweb.models.Constants.QUERY_PARAM
import com.tafakkur.blogweb.models.Constants.UPDATED_PARAM

sealed class Screen(val route: String){
    data object AdminHome : Screen(route = "/admin/")
    data object AdminLogin : Screen(route = "/admin/login")
    data object AdminCreate : Screen(route = "/admin/create"){
        fun passPostId(id: Long) = "/admin/create?${POST_ID_PARAM}=$id"
    }

    data object AdminMyPosts : Screen(route = "/admin/my-posts"){
        fun searchByTitle(query: String) = "/admin/my-posts?${QUERY_PARAM}=$query"
    }

    data object AdminSuccess : Screen(route = "/admin/success"){
        fun postUpdated() = "/admin/success?${UPDATED_PARAM}=true"
    }

    data object HomePage : Screen(route = "/")
    data object SearchPage : Screen(route = "/search/query"){
        fun searchByCategory(category: Category) =
            "/search/query?${CATEGORY_PARAM}=${category.name}"
        fun searchByTitle(query: String) = "/search/query?${QUERY_PARAM}=$query"
    }

    data object PostPage : Screen(route = "/posts/post"){
        fun getPosts(id: Long) = "/posts/post?${POST_ID_PARAM}=$id"
    }
}