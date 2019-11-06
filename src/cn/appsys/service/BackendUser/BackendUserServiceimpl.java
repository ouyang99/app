package cn.appsys.service.BackendUser;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.BackendUser.BackendUserMapper;
import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.BackendUser;
import cn.appsys.pojo.DataDictionary;

@Service
public class BackendUserServiceimpl implements BackendUserService {
	@Resource
	private BackendUserMapper backendUserMapper;

	@Override
	public BackendUser getUser(String Username, String Password) {
		BackendUser User = null;
		User = backendUserMapper.getUser(Username, Password);
		return User;
	}

	@Override
	public int count(String appname, int appstatic, int FlatformId, int CategoryLevel1, int level2list,
			int CategoryLevel3) {
		int i=backendUserMapper.count(appname, appstatic, FlatformId, CategoryLevel1, level2list, CategoryLevel3);
		return i;
	}

	@Override
	public List<AppInfo> list(String appname, int appstatic, int FlatformId, int CategoryLevel1, int level2list,
			int CategoryLevel3, int pageidex, int pageSize) {
		List<AppInfo> list=backendUserMapper.list(appname, appstatic, FlatformId, CategoryLevel1, level2list, CategoryLevel3, pageidex, pageSize);
		return list;
	}

	@Override
	public List<DataDictionary> statuslist() {
		List<DataDictionary> list=backendUserMapper.statuslist();
		return list;
	}

	@Override
	public List<DataDictionary> flatform() {
		List<DataDictionary> list=backendUserMapper.flatform();
		return list;
	}

	@Override
	public List<AppCategory> Categorylist1() {
		List<AppCategory> list=backendUserMapper.Categorylist1();
		return list;
	}

	@Override
	public List<AppCategory> Categorylist2(int id) {
		List<AppCategory> list=backendUserMapper.Categorylist2(id);
		return list;
	}

	@Override
	public List<AppCategory> Categorylist3(int id) {
		List<AppCategory> list=backendUserMapper.Categorylist3(id);
		return list;
	}

	@Override
	public AppInfo viewapp(int id) {
		AppInfo appinfo=backendUserMapper.viewapp(id);
		return appinfo;
	}

	@Override
	public AppVersion selectversion(int id) {
		AppVersion appvresion=backendUserMapper.selectversion(id);
		return appvresion;
	}

	@Override
	public int updatesh(int id, int statusid) {
		int i=backendUserMapper.updatesh(id, statusid);
		return i;
	}

}
