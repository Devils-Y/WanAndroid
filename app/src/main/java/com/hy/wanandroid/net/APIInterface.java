package com.hy.wanandroid.net;


import com.hy.wanandroid.bean.BannerBean;
import com.hy.wanandroid.bean.BaseBean;
import com.hy.wanandroid.bean.CollectBean;
import com.hy.wanandroid.bean.CollectListBean;
import com.hy.wanandroid.bean.CollectOutsideTheStationBean;
import com.hy.wanandroid.bean.FriendBean;
import com.hy.wanandroid.bean.HotKeyBean;
import com.hy.wanandroid.bean.JsonBean;
import com.hy.wanandroid.bean.LoginBean;
import com.hy.wanandroid.bean.NaviBean;
import com.hy.wanandroid.bean.ProjectBean;
import com.hy.wanandroid.bean.ProjectListBean;
import com.hy.wanandroid.bean.QueryBean;
import com.hy.wanandroid.bean.RegisterBean;
import com.hy.wanandroid.bean.TreeArticleBean;
import com.hy.wanandroid.bean.TreeBean;
import com.hy.wanandroid.bean.UnCollectBean;
import com.hy.wanandroid.bean.UnCollectWithOriginIdBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    String url = "http://www.wanandroid.com/";

    /**
     * 首页文章列表
     */
    String jsonUrl = "article/list/{page}/json";

    @GET(jsonUrl)
    Observable<Response<JsonBean>> getJson(@Path("page") String page);

    /**
     * 首页banner
     */
    String bannerUrl = "banner/json";

    @GET(bannerUrl)
    Observable<Response<List<BannerBean>>> getBanner();

    /**
     * 常用网站
     */
    String friendUrl = "friend/json";

    @GET(friendUrl)
    Observable<Response<List<FriendBean>>> getFriend();

    /**
     * 搜索热词
     */
    String hotKeyUrl = "hotkey/json";
    @GET(hotKeyUrl)
    Observable<Response<List<HotKeyBean>>> getHotKey();


    /**
     * 体系数据
     */
    String treeUrl = "tree/json";

    @GET(treeUrl)
    Observable<Response<List<TreeBean>>> getTree();

    /**
     * 知识体系下的文章
     */
    String treeArticleUrl = "article/list/{page}/json";

    @GET(treeArticleUrl)
    Observable<Response<TreeArticleBean>> getTreeArticle(@Path("page") String page,
                                                         @Query("cid") String cid);

    /**
     * 导航数据
     */
    String naviUrl = "navi/json";

    @GET(naviUrl)
    Observable<Response<List<NaviBean>>> getNavi();

    /**
     * 项目分类
     */
    String projectUrl = "project/tree/json";

    @GET(projectUrl)
    Observable<Response<List<ProjectBean>>> getProject();

    /**
     * 项目列表数据
     */
    String projectListUrl = "project/list/{page}/json";

    @GET(projectListUrl)
    Observable<Response<ProjectListBean>> getProjectList(@Path("page") String page,
                                                         @Query("cid") String cid);

    /**
     * 登录
     */
    String loginUrl = "user/login";

    @POST(loginUrl)
    @FormUrlEncoded
    Observable<Response<LoginBean>> postLogin(@Field("username") String username,
                                              @Field("password") String password);

    /**
     * 注册
     */
    String registerUrl = "user/register";

    @POST(registerUrl)
    @FormUrlEncoded
    Observable<Response<RegisterBean>> postRegister(@Field("username") String username,
                                                    @Field("password") String password,
                                                    @Field("repassword") String repassword);

    /**
     * 收藏文章列表
     */
    String collectListUrl = "lg/collect/list/{page}/json";

    @GET(collectListUrl)
    Observable<Response<CollectListBean>> getCollectList(@Path("page") String page);


    /**
     * 收藏站内文章
     */
    String collectUrl = "lg/collect/{id}/json";

    @POST(collectUrl)
    Observable<Response<CollectBean>> postCollect(@Path("id") String id);

    /**
     * 收藏站外文章
     */
    String collectOutsideTheStationUrl = "lg/collect/add/json";

    @POST(collectOutsideTheStationUrl)
    @FormUrlEncoded
    Observable<Response<CollectOutsideTheStationBean>> postCollectOutsideTheStation(
            @Field("title") String title,
            @Field("author") String author,
            @Field("link") String link);

    /**
     * 取消收藏
     * 文章列表
     */
    String unCollectWithOriginIdUrl = "lg/uncollect_originId/{id}/json";

    @POST(unCollectWithOriginIdUrl)
    Observable<Response<UnCollectWithOriginIdBean>> postUnCollectWithOriginId(@Path("id") String id);

    /**
     * 取消收藏
     * 我的收藏页面
     */
    String uncollectUrl = "lg/uncollect/{id}/json";

    @POST(uncollectUrl)
    @FormUrlEncoded
    Observable<Response<UnCollectBean>> postUnCollect(@Path("id") String id,
                                                      @Field("originId") String originId);

    /**
     * 搜索
     */
    String queryUrl = "article/query/{page}/json";
    @POST(queryUrl)
    @FormUrlEncoded
    Observable<Response<QueryBean>> postQuery(@Path("page") String page,
                                              @Field("k") String keyword);
}
