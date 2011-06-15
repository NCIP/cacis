package gov.nih.nci.cacis.datawarehouse.service;

import java.rmi.RemoteException;

/** 
 * TODO:I am the service side implementation class.  IMPLEMENT AND DOCUMENT ME
 * 
 * @created by Introduce Toolkit version 1.4
 * 
 */
public class ClinicalDataWarehouseImpl extends ClinicalDataWarehouseImplBase {

	
	public ClinicalDataWarehouseImpl() throws RemoteException {
		super();
	}
	
  public java.lang.String[] query(java.lang.String string) throws RemoteException {
      try {
          return new String[] {"1001","1002"};
      } catch (Exception e) {
          throw new RemoteException("Unable to query documents for " +string+ " :"  + e.getMessage());
      }
  }

}

