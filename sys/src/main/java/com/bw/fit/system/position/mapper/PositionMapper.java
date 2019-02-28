package com.bw.fit.system.position.mapper;

import java.util.List;
import java.util.Map;

import com.bw.fit.system.common.model.RbackException;
import com.bw.fit.system.organization.model.Organization;
import com.bw.fit.system.position.entity.TOrganization2Position;
import com.bw.fit.system.position.model.Position;
import com.bw.fit.system.position.model.PositionGrade;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PositionMapper {
	/*****
	 * 根据岗位字段条件获取岗位
	 * @param position
	 * @return
	 */
	public Page<Position> getPositions(Position position);
	/****
	 * 新增岗位
	 * @param position
	 * @throws RbackException
	 */
	public void insert(Position position ) throws RbackException ;

	/****
	 * 新增岗位和组织得关系
	 * @param organization2Position
	 * @throws RbackException
	 */
	public void insertOrg2Position(TOrganization2Position organization2Position) throws RbackException ;
	/****
	 * 修改岗位
	 * @param position
	 * @throws RbackException
	 */
	public void update(Position position ) throws RbackException ;
	/****
	 * 删除岗位
	 * @param id
	 * @throws RbackException
	 */
	public void delete(String id) throws RbackException ;

	/****
	 * 删除岗位和组织关联信息
	 * @param map
	 * @throws RbackException
	 */
	public void deleteO2P(Map<String,String> map) throws RbackException ;

	/****
	 * 根据岗位id删除岗位和组织关联信息(修改岗位使用)
	 * @param id
	 * @throws RbackException
	 */
	public void deleteO2PByPid(String id) throws RbackException ;

	/****
	 * 根据岗位Id获取所属组织列表
	 * @param positionId
	 */
	public List<Organization> getOrgByPositionId(String positionId) ;

	/****
	 * 根据组织Id获岗位列表
	 * @param orgId
	 */
	public List<Position> getPositionByOrgId(String orgId) ;
	/****
	 * 根据Id获岗位级别列表
	 * @param id
	 */
	public List<PositionGrade> getPositionGrade(String id) ;
	/****
	 * 根据Id获岗位
	 * @param id
	 */
	public Position get(String id) ;
	/****
	 * 根据岗位Id检查是否还存在该岗位和组织的关系
	 * @param id 岗位id
	 */
	public List<TOrganization2Position> checkO2P(String id) ;
}
