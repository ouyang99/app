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

	// ��ѯ������
	public int count(@Param("appname") String appname, @Param("Status") int Status, @Param("FlatformId") int FlatformId,
			@Param("CategoryLevel1") int CategoryLevel1, @Param("level2list") int level2list,
			@Param("CategoryLevel3") int CategoryLevel3);

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

	// Ajax�����Ƿ��Ѵ���
	public AppInfo getAPK(@Param("apkname") String apkname);

	// ����App
	public int Appinsert(AppInfo appinfo);

	// ͨ��id��ѯ
	public AppInfo selectid(@Param("id") int id);

	// ͨ��id�޸�
	public int updataid(AppInfo appinfo);

	// ͨ��״̬��ѯValueId
	public DataDictionary sele(@Param("name") String name);

	// ͨ��APPid��ѯ�汾��Ϣ
	public List<AppVersion> versionlist(@Param("id") int id);

	// ��Ӱ汾��Ϣ
	public int versionadd(AppVersion appversion);

	// ͨ��version��ѯapp_info
	public AppInfo seleAppInfo(@Param("id") int id);

	// �޸�info�汾
	public int updateversion(@Param("id") int id, @Param("versionid") int versionid);

	// ͨ��id��ѯVersion
	public AppVersion selectversion(@Param("id") int id);

	// �޸İ汾��Ϣ
	public int appmodify(AppVersion appversion);

	// �޸İ汾·����Ϣ
	public int appvermodify(AppVersion appversion);

	// ��Ӱ汾·��
	public int updatever(AppVersion appversion);

	// �鿴��Ϣ
	public AppInfo viewapp(@Param("id") int id);

	// ɾ��App
	public int delapp(@Param("id") int id);

	// ���¼�
	public int appajaxupdate(@Param("id") int id, @Param("statusid") int statusid);
}
