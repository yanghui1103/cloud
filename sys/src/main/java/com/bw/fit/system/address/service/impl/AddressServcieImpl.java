package com.bw.fit.system.address.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bw.fit.system.account.model.Account;
import com.bw.fit.system.account.service.AccountService;
import com.bw.fit.system.address.mapper.AddressMapper;
import com.bw.fit.system.address.entity.VAddress;
import com.bw.fit.system.address.model.Address;
import com.bw.fit.system.address.service.AddressService;
import com.bw.fit.system.dict.service.DictService;
import com.bw.fit.system.organization.service.OrganizationService;
import com.bw.fit.system.position.service.PositionService;

import javax.annotation.Resource;

@Service
public class AddressServcieImpl implements AddressService{
	@Autowired
	private PositionService positionService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private DictService dictService;
	@Resource
	private AddressMapper addressMapper;
	
	@Override
	public List<Address> getSelectedAddr(String ids) {
		List<Address> list = new ArrayList<Address>();
		String ids2[] = ids.split(",");
		if(ids2.length>0) {
			for(String id : ids2) {
				VAddress va = new VAddress();
				Address address = new Address();
				if(id.indexOf("_")>-1) {
					String tId = id.split("_")[0];
					String pId = id.split("_")[1];
					va.setId(tId);
					va.setUnderOrgId(pId);
				}else {
					va.setId(id);
				}
				va = addressMapper.get(va);
				if(null!=va) {
					address.setText(va.getName());
					if("position".equalsIgnoreCase(va.getAddressType())) {
						address.setValue(va.getId()+"_"+va.getUnderOrgId());
					}else {
						address.setValue(va.getId());
					}
					address.setTmp(va.getId()+"_"+va.getUnderOrgId());
					list.add(address);
				}
			}
		}
		return list;
	}

	@Override
	public List<Address> getSelectAddr(boolean o,boolean p,boolean a,String keyWords,boolean type) {
		List<Address> list = new ArrayList<Address>();
		List<VAddress> orgList = new ArrayList<>();
		List<VAddress> positionList = new ArrayList<>();
		List<VAddress> accountList = new ArrayList<>();
		
		if(type) {
			orgList = o? addressMapper.getAddressByKey("organization", keyWords):null;
			positionList = p? addressMapper.getAddressByKey("position", keyWords):null;
			accountList = a? addressMapper.getAddressByKey("account", keyWords):null;
		}else {
			orgList = o? addressMapper.getAddressByOrgId("organization", keyWords):null;
			positionList = p? addressMapper.getAddressByOrgId("position", keyWords):null;
			accountList = a? addressMapper.getAddressByOrgId("account", keyWords):null;
		}
		
		if(orgList!=null&&orgList.size()>0) {
			for(VAddress va : orgList) {
				Address address = new Address();
				address.setText(va.getName());
				address.setValue(va.getId());
				address.setTmp(va.getId()+"_"+va.getUnderOrgId());
				list.add(address);
			}
		}
		if(positionList!=null&&positionList.size()>0) {
			for(VAddress va : positionList) {
				Address address = new Address();
				address.setText(va.getName());
				address.setValue(va.getId()+"_"+va.getUnderOrgId());
				address.setTmp(va.getId()+"_"+va.getUnderOrgId());
				list.add(address);
			}
		}
		if(accountList!=null&&accountList.size()>0) {
			for(VAddress va : accountList) {
				Address address = new Address();
				address.setText(va.getName());
				address.setValue(va.getId());
				address.setTmp(va.getId()+"_"+va.getUnderOrgId());
				list.add(address);
			}
		}
		return list;
	}

	@Override
	public String getDetali(String id, String underOrgId) {
		VAddress addr = new VAddress();
		addr.setId(id);
		addr.setUnderOrgId(underOrgId);
		addr = addressMapper.get(addr);
		String detali =null;
		if (addr!=null) {
			if("organization".equals(addr.getAddressType())) {//组织
				detali = organizationService.get(id).getName()
						+"("+dictService.getDictsByParentValue(organizationService.get(id).getType()).getDictName()+")";
				detali += "["+organizationService.getParentOrgByCurtOrgId(underOrgId)+"]";
			}
			if("position".equals(addr.getAddressType())){//岗位
				detali = positionService.get(id).getName()+"(岗位)";
				detali += "["+organizationService.getParentOrgByCurtOrgId(underOrgId)+"]";
			}
			if("account".equals(addr.getAddressType())){//账号
				Account account = accountService.get(id);
				detali = account.getName()+","+account.getLogName()+","
						+account.getPhone()+","+"(账号)";
				detali += "[所属岗位:"+accountService.getPositionStrOfTheAccount(id)+
						",所属组织:"+organizationService.getParentOrgByCurtOrgId(underOrgId)+"]";
			}
		}
		return detali;
	}

	@Override
	public String[] getNames(String[] ids) {
		return addressMapper.getNames(ids);
	}

}
