package cn.appsys.service.DevUser;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.pojo.DevUser;

public interface DevUserService {

	public DevUser getUser(@Param("Username") String Username, @Param("Password") String Password);

	// 查询总数量
	public int count(@Param("appname") String appname, @Param("Status") int Status, @Param("FlatformId") int FlatformId,
			@Param("CategoryLevel1") int CategoryLevel1, @Param("level2list") int level2list,
			@Param("CategoryLevel3") int CategoryLevel3);

	// 初始化加载
	public List<AppInfo> list(@Param("appname") String appname, @Param("appstatic") int appstatic,
			@Param("FlatformId") int FlatformId, @Param("CategoryLevel1") int CategoryLevel1,
			@Param("level2list") int level2list, @Param("CategoryLevel3") int CategoryLevel3,
			@Param("pageindex") int pageidex, @Param("pageSize") int pageSize);

	// 查询App状态
	public List<DataDictionary> statuslist();

	// 查询所属平台
	public List<DataDictionary> flatform();

	// 查询一级分类
	public List<AppCategory> Categorylist1();

	// 查询二级分类
	public List<AppCategory> Categorylist2(@Param("id") int id);

	// 查询三级分类
	public List<AppCategory> Categorylist3(@Param("id") int id);

	// Ajax请求是否已存在
	public AppInfo getAPK(@Param("apkname") String apkname);

	// 新增App
	public int Appinsert(AppInfo appinfo);

	// 通过id查询
	public AppInfo selectid(@Param("id") int id);

	// 通过id修改
	public int updataid(AppInfo appinfo);

	// 通过状态查询ValueId
	public DataDictionary sele(@Param("name") String name);

	// 通过APPid查询版本信息
	public List<AppVersion> versionlist(@Param("id") int id);

	// 添加版本信息
	public int versionadd(AppVersion appversion);

	// 通过version查询app_info
	public AppInfo seleAppInfo(@Param("id") int id);

	// 修改info版本
	public int updateversion(@Param("id") int id, @Param("versionid") int versionid);

	// 通过id查询Version
	public AppVersion selectversion(@Param("id") int id);

	// 修改版本信息
	public int appmodify(AppVersion appversion);

	// 修改版本路径信息
	public int appvermodify(AppVersion appversion);

	// 添加版本路径
	public int updatever(AppVersion appversion);

	// 查看信息
	public AppInfo viewapp(@Param("id") int id);

	// 删除App
	public int delapp(@Param("id") int id);

	// 上下架
	public int appajaxupdate(@Param("id") int id, @Param("statusid") int statusid);
}
