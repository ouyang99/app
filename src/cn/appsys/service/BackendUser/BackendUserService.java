package cn.appsys.service.BackendUser;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.BackendUser;
import cn.appsys.pojo.DataDictionary;

public interface BackendUserService {
	// �˺ŵ�¼
	public BackendUser getUser(@Param("Username") String Username, @Param("Password") String Password);

	// ��ѯ������
	public int count(@Param("appname") String appname, @Param("appstatic") int appstatic,
			@Param("FlatformId") int FlatformId, @Param("CategoryLevel1") int CategoryLevel1,
			@Param("level2list") int level2list, @Param("CategoryLevel3") int CategoryLevel3);

	// ��ʼ������
	public List<AppInfo> list(@Param("appname") String appname, @Param("appstatic") int appstatic,
			@Param("FlatformId") int FlatformId, @Param("CategoryLevel1") int CategoryLevel1,
			@Param("level2list") int level2list, @Param("CategoryLevel3") int CategoryLevel3,
			@Param("pageindex") int pageidex, @Param("pageSize") int pageSize);

	// ��ѯApp״̬
	public List<DataDictionary> statuslist();

	// ��ѯ����ƽ̨
	public List<DataDictionary> flatform();

	// ��ѯһ������
	public List<AppCategory> Categorylist1();

	// ��ѯ��������
	public List<AppCategory> Categorylist2(@Param("id") int id);

	// ��ѯ��������
	public List<AppCategory> Categorylist3(@Param("id") int id);

	// �鿴��Ϣ
	public AppInfo viewapp(@Param("id") int id);

	// ͨ��id��ѯVersion
	public AppVersion selectversion(@Param("id") int id);

	// ���
	public int updatesh(@Param("id") int id, @Param("statusid") int statusid);
}
