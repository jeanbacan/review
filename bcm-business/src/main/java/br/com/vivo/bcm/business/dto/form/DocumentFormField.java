package br.com.vivo.bcm.business.dto.form;

import br.com.vivo.bcm.business.vo.MyDocumentVO;
import br.com.vivo.bcm.business.vo.VivoTaskFormItem;

public class DocumentFormField extends VivoTaskFormItem {
	private MyDocumentVO myDocumentVO;

	/**
	 * @return the myDocumentVO
	 */
	public MyDocumentVO getMyDocumentVO() {
		return myDocumentVO;
	}

	/**
	 * @param myDocumentVO
	 *            the myDocumentVO to set
	 */
	public void setMyDocumentVO(MyDocumentVO myDocumentVO) {
		this.myDocumentVO = myDocumentVO;
	}
}