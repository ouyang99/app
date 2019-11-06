package cn.appsys.service.DevUser;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import cn.appsys.dao.DevUser.DevUserMapper;
import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.pojo.DevUser;

@Service
public class DevUserServiceimpl implements DevUserService {
	@Resource
	private DevUserMapper davUserMapper;

	@Override
	public DevUser getUser(String Username, String Password) {
		DevUser devuser = new DevUser();
		devuser = davUserMapper.getUser(Username, Password);
		return devuser;
	}

	@Override
	public List<DataDictionary> statuslist() {
		List<DataDictionary> list = null;
		list = davUserMapper.statuslist();
		return list;
	}

	@Override
	public List<DataDictionary> flatform() {
		List<DataDictionary> list = null;
		list = davUserMapper.flatform();
		return list;
	}

	@Override
	public List<AppCategory> Categorylist1() {
		List<AppCategory> list = null;
		list = davUserMapper.Categorylist1();
		return list;
	}

	@Override
	public List<AppCategory> Categorylist2(int id) {
		List<AppCategory> list = null;
		list = davUserMapper.Categorylist2(id);
		return list;
	}

	@Override
	public List<AppCategory> Categorylist3(int id) {
		List<AppCategory> list = null;
		list = davUserMapper.Categorylist3(id);
		return list;
	}

	@Override
	public int count(String appname, int appstatic, int FlatformId, int CategoryLevel1, int level2list,
			int CategoryLevel3) {
		int i = 0;
		i = davUserMapper.count(appname, appstatic, FlatformId, CategoryLevel1, level2list, CategoryLevel3);
		return i;
	}

	@Override
	public List<AppInfo> list(String appname, int Status, int FlatformId, int CategoryLevel1, int level2list,
			int CategoryLevel3, int pageidex, int pageSize) {
		List<AppInfo> list = null;
		list = davUserMapper.list(appname, Status, FlatformId, CategoryLevel1, level2list, CategoryLevel3, pageidex,
				pageSize);
		return list;
	}

	@Override
	public AppInfo getAPK(String apkname) {
		AppInfo appinfo = davUserMapper.getAPK(apkname);
		return appinfo;
	}

	@Override
	public int Appinsert(AppInfo appinfo) {
		int i = 0;
		i = davUserMapper.Appinsert(appinfo);
		return i;
	}

	@Override
	public AppInfo selectid(int id) {
		AppInfo appinfo = davUserMapper.selectid(id);
		return appinfo;
	}

	@Override
	public int updataid(AppInfo appinfo) {
		int i = 0;
		i = davUserMapper.updataid(appinfo);
		return i;
	}

	@Override
	public DataDictionary sele(String name) {
		System.err.println(name);
		DataDictionary dataDictionary = davUserMapper.sele(name);
		return dataDictionary;
	}

	@Override
	public List<AppVersion> versionlist(int id) {
		List<AppVersion> list=null;
		list=davUserMapper.versionlist(id);
		return list;
	}

	@Override
	public int versionadd(AppVersion appversion) {
		int i=0;
		i=davUserMapper.versionadd(appversion);
		return i;
	}

	@Override
	public AppInfo seleAppInfo(int id) {
		AppInfo appinfo=davUserMapper.seleAppInfo(id);
		return appinfo;
	}

	@Override
	public int updateversion(int id, int versionid) {
		int i=0;
		i=davUserMapper.updateversion(id, versionid);
		return i;
	}

	@Override
	public AppVersion selectversion(int id) {
		AppVersion appversion=davUserMapper.selectversion(id);
		return appversion;
	}

	@Override
	public int appmodify(AppVersion appversion) {
		int i=davUserMapper.appmodify(appversion);
		return i;
	}

	@Override
	public int appvermodify(AppVersion appversion) {
		int i=davUserMapper.appvermodify(appversion);
		return i;
	}

	@Override
	public int updatever(AppVersion appversion) {
		int i=davUserMapper.updatever(appversion);
		return i;
	}

	@Override
	public AppInfo viewapp(int id) {
		AppInfo appinfo=davUserMapper.viewapp(id);
		return appinfo;
	}

	@Override
	public int delapp(int id) {
		int i=davUserMapper.delapp(id);
		return i;
	}

	@Override
	public int appajaxupdate(int id, int statusid) {
		int i=davUserMapper.appajaxupdate(id, statusid);
		return i;
	}
	
	

}
