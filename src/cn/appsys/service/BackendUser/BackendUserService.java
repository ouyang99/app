package cn.appsys.service.BackendUser;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.BackendUser;
import cn.appsys.pojo.DataDictionary;

public interface BackendUserService {
	// 账号登录
	public BackendUser getUser(@Param("Username") String Username, @Param("Password") String Password);

	// 查询总数量
	public int count(@Param("appname") String appname, @Param("appstatic") int appstatic,
			@Param("FlatformId") int FlatformId, @Param("CategoryLevel1") int CategoryLevel1,
			@Param("level2list") int level2list, @Param("CategoryLevel3") int CategoryLevel3);

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

	// 查看信息
	public AppInfo viewapp(@Param("id") int id);

	// 通过id查询Version
	public AppVersion selectversion(@Param("id") int id);

	// 审核
	public int updatesh(@Param("id") int id, @Param("statusid") int statusid);
}
