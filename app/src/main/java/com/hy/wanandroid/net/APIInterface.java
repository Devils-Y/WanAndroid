package com.hy.wanandroid.net;


import com.hy.wanandroid.bean.BaseBean;
import com.hy.wanandroid.bean.FriendBean;
import com.hy.wanandroid.bean.JsonBean;
import com.hy.wanandroid.bean.NaviBean;
import com.hy.wanandroid.bean.ProjectBean;
import com.hy.wanandroid.bean.ProjectListBean;
import com.hy.wanandroid.bean.TreeArticleBean;
import com.hy.wanandroid.bean.TreeBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    String url = "http://www.wanandroid.com/";

    String jsonUrl = "article/list/{page}/json";
    @GET(jsonUrl)
    Observable<JsonBean> getJson(@Path("page") String page);

    String bannerUrl = "banner/json";
    @GET(bannerUrl)
    Call<BaseBean<String>> getBanner();

    String friendUrl = "friend/json";
    @GET(friendUrl)
    Observable<List<FriendBean>> getFriend();

    String treeUrl = "tree/json";
    @GET(treeUrl)
    Observable<List<TreeBean>> getTree();

    String treeArticleUrl ="article/list/{page}/json";
    @GET(treeArticleUrl)
    Observable<TreeArticleBean> getTreeArticle(@Path("page") String page,
                                               @Query("cid") String cid);

    String naviUrl = "navi/json";
    @GET(naviUrl)
    Observable<NaviBean> getNavi();

    String projectUrl = "project/tree/json";
    @GET(projectUrl)
    Observable<List<ProjectBean>> getProject();

    String projectListUrl = "project/list/{page}/json";
    @GET(projectListUrl)
    Observable<ProjectListBean> getProjectList(@Path("page") String page,
                                               @Query("cid") String cid);
}
