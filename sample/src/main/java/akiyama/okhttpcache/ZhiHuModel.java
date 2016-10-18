package akiyama.okhttpcache;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/17.
 */
public class ZhiHuModel {
   /*    {
        "followersCount": 1947,
        "name": "好奇的星星",
        "url": "/againststigma",
        "postsCount": 13,
        "description": "本专栏会写一些我读书之后的感想。会写一些关于心理疾病的问题（尤其写一些我康复过程中的感悟和走过的弯路）~希望我的文字能够帮助到更多的人。",
        "slug": "againststigma",
        "avatar": {
            "id": "973ab4cf681688decb6f8dfa9390ee4d",
            "template": "https://pic2.zhimg.com/{id}_{size}.jpeg"
        }
    }*/
    private static final String WEB_URL = "https://zhuanlan.zhihu.com";
    public long followersCount;
    public String name;
    public String url;
    public String postsCount;
    public String description;
    public String slug;
    public Avatar mAvatar;

    /**
     * {
     "followersCount": 1947,
     "name": "好奇的星星",
     "url": "/againststigma",
     "postsCount": 13,
     "description": "本专栏会写一些我读书之后的感想。会写一些关于心理疾病的问题（尤其写一些我康复过程中的感悟和走过的弯路）~希望我的文字能够帮助到更多的人。",
     "slug": "againststigma",
     "avatar": {
     "id": "973ab4cf681688decb6f8dfa9390ee4d",
     "template": "https://pic2.zhimg.com/{id}_{size}.jpeg"
     }
     }
     * @param response
     * @return
     */

    public static List<ZhiHuModel> getZhihuModelS(String response){
        List<ZhiHuModel> zhiHuModels = new ArrayList<>();
        if(!TextUtils.isEmpty(response)){
            try {
                JSONArray jsonArray = new JSONArray(response);
                for(int i=0;i<jsonArray.length();i++){
                    String item = jsonArray.get(i).toString();
                    JSONObject jsonObject = new JSONObject(item);
                    ZhiHuModel zhiHuModel = new ZhiHuModel();
                    zhiHuModel.followersCount = jsonObject.getInt("followersCount");
                    zhiHuModel.name = jsonObject.getString("name");
                    zhiHuModel.description = jsonObject.getString("description");
                    zhiHuModel.url = WEB_URL+jsonObject.getString("url");
                    zhiHuModel.postsCount = jsonObject.getString("postsCount");
                    zhiHuModel.slug = jsonObject.getString("slug");
                    String avatarStr = jsonObject.getString("avatar");
                    Avatar avatar = new Avatar();
                    JSONObject avatarJson = new JSONObject(avatarStr);
                    avatar.id = avatarJson.getString("id");
                    avatar.template = avatarJson.getString("template");
                    zhiHuModel.mAvatar = avatar;

                    zhiHuModels.add(zhiHuModel);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return zhiHuModels;
    }

}